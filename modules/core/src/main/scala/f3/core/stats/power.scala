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

import cats.implicits._

case class PowerStatistics(
  season: Int,
  week: Int,
  franchiseId: Int,
  power: Double,
  tier: Int
)

object power {

  def apply(league: League): List[PowerStatistics] = {
    league.seasons.flatMap(apply)
  }

  def apply(season: Season): List[PowerStatistics] = {
    apply(season, basic(season))
  }

  def apply(season: Season, basicStats: List[BasicStatistics]): List[PowerStatistics] = {
    val powerFilled =
      season.teams.flatMap { team =>
        val teamGames =
          season.games.filter(
            g =>
              (g.totalPoints > 0 || g.isBye) &&
                g.homeTeamId == team.franchiseId || g.awayTeamId == team.franchiseId)

        teamGames.flatMap { game =>
          {
            val previousGames = teamGames.filter(_.week <= game.week)
            val byeGames      = previousGames.count(_.isBye)

            basicStats.find(s => s.franchiseId == team.franchiseId && s.week == game.week).map {
              teamStats =>
                if (previousGames.isEmpty) {
                  PowerStatistics(season.year, game.week, team.franchiseId, 0d, Int.MaxValue)
                } else {
                  val gameScores =
                    previousGames.filterNot(_.isBye).map { g =>
                      if (g.homeTeamId == team.franchiseId) g.homeScore
                      else g.awayScore
                    }

                  val power =
                    ((6 * teamStats.pointsFor / (previousGames.size - byeGames)) +
                      (2 * (gameScores.max + gameScores.min)) +
                      (400 * teamStats.record.wins / (previousGames.size - byeGames))) / 10

                  PowerStatistics(season.year, game.week, team.franchiseId, power, 1)
                }
            }
          }
        }
      }

    // Now to calculate tiers
    powerFilled
      .groupBy(_.week)
      .flatMap {
        case (week, stats) =>
          val sorted = stats.sortBy(-_.power)
          sorted.scanLeft(none[PowerStatistics]) {
            case (lastOpt, current) =>
              lastOpt.map { last =>
                val powerRatio  = 1 - ((last.power - current.power) / current.power)
                val currentTier = if (powerRatio < 0.955) last.tier + 1 else last.tier
                current.copy(tier = currentTier).some
              }.getOrElse(current.copy(tier = 1).some)
          }
      }
      .toList
      .flatten
  }

}
