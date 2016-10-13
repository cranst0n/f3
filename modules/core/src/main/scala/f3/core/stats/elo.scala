/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ian McIntosh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package f3
package core
package stats

import scala.math._

import cats.implicits._

case class EloStatistics(
  season: Int,
  week: Int,
  franchiseId: Int,
  rating: Double,
  tier: Int
)

/**
  * https://github.com/xsankar/hairy-octo-hipster/blob/master/ELO-538.R
  * https://doubleclix.wordpress.com/2015/01/20/the-art-of-nfl-ranking-the-elo-algorithm-and-fivethirtyeight/
  *
  * For team I playing team J
  * ₙRᵢ = ₒRᵢ + K * MₒᵥMᵢⱼ (Sᵢⱼ - μᵢⱼ)
  *
  * newRank = oldRank + K * marginOfVictory (wld - expectedResult)
  *
  * K = 20
  * marginOfVictory = Ln(abs(pointDiff) + 1) * (2.2 / ((0.001 * (eloWinner-eloLoser)) + 2.2))
  * wld = if(teamIWin) 1.0 else if(teamILose) 0.0 else 0.5
  * expectedResult = 1 / (1 + (10 ^ (myRank - theirRank) / 400))
  *
  */
object elo {

  def apply(league: League): List[EloStatistics] = {
    apply(league.seasons.flatMap(_.games))
  }

  def apply(season: Season): List[EloStatistics] = {
    apply(season.games)
  }

  def apply(games: List[Game]): List[EloStatistics] = {

    val eloFilled =
      games
        .sortWith((a, b) => a.season < b.season || (a.season == b.season && a.week < b.week))
        .foldLeft(List[EloStatistics]()) { (eloStats, game) =>
          val awayPriorElo = priorElo(game.awayTeamId, game, eloStats)
          val homePriorElo = priorElo(game.homeTeamId, game, eloStats)

          val newAwayElo = newElo(game.awayTeamId, game, awayPriorElo, homePriorElo)
          val newHomeElo = newElo(game.homeTeamId, game, homePriorElo, awayPriorElo)

          val awayStats = EloStatistics(game.season, game.week, game.awayTeamId, newAwayElo, 0)
          val homeStats = EloStatistics(game.season, game.week, game.homeTeamId, newHomeElo, 0)

          List(awayStats, homeStats).filterNot(_.franchiseId == Team.Bye.franchiseId) ::: eloStats
        }

    // Now to calculate tiers
    eloFilled
      .groupBy(s => (s.season, s.week))
      .flatMap {
        case ((season, week), stats) =>
          val sorted = stats.sortBy(-_.rating)
          sorted.scanLeft(none[EloStatistics]) {
            case (lastOpt, current) =>
              lastOpt.map { last =>
                val powerRatio  = 1 - ((last.rating - current.rating) / current.rating)
                val currentTier = if (powerRatio < 0.99) last.tier + 1 else last.tier
                current.copy(tier = currentTier).some
              }.getOrElse(current.copy(tier = 1).some)
          }
      }
      .toList
      .flatten
  }

  private[this] def priorElo(teamId: Int, game: Game, eloStats: List[EloStatistics]): Double = {
    eloStats
      .sortWith((a, b) => a.season > b.season || (a.season == b.season && a.week > b.week))
      .find(es => es.franchiseId == teamId)
      .map { es =>
        if (es.season == game.season) es.rating
        else {
          val diff2Mean = (es.rating - 1500)
          es.rating - (diff2Mean / 2)
        }
      }
      .getOrElse(1500d)
  }

  def winProbability(teamElo: Double, oppElo: Double): Double = {
    1.0 / (pow(10, -(teamElo - oppElo) / 400.0) + 1)
  }

  private[this] def newElo(teamId: Int,
                           game: Game,
                           priorElo: Double,
                           oppPriorElo: Double): Double = {
    if (game.isBye || game.totalPoints == 0.0) {
      priorElo
    } else {

      val K = 15

      val (score, oppScore) = {
        if (game.awayTeamId == teamId) (game.awayScore, game.homeScore)
        else (game.homeScore, game.awayScore)
      }

      val (eloWinner, eloLoser) = {
        if (score > oppScore) (priorElo, oppPriorElo)
        else (oppPriorElo, priorElo)
      }

      val movMult = log(abs(game.awayScore - game.homeScore) + 1) *
          (2.2 / (0.001 * (eloWinner - eloLoser) + 2.2))

      val expectedResult = 1 / (1 + pow(10, -1 * (priorElo - oppPriorElo) / 400))
      val actualResult =
        if (score > oppScore) 1.0
        else if (oppScore > score) 0.0
        else 0.5

      priorElo + K * movMult * (actualResult - expectedResult)
    }
  }

}
