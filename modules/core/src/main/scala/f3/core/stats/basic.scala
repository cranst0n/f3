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

case class BasicStatistics(
  season: Int,
  week: Int,
  franchiseId: Int,
  record: Record,
  divisionRecord: Record,
  conferenceRecord: Record,
  pointsFor: Double,
  pointsAgainst: Double,
  gamesBehind: Double
)

object basic {

  def apply(league: League): List[BasicStatistics] = {
    league.seasons.flatMap(apply)
  }

  def apply(season: Season): List[BasicStatistics] = {

    val recordStats =
      season.teams.flatMap { team =>
        val games =
          season.games
            .filter(g => g.homeTeamId == team.franchiseId || g.awayTeamId == team.franchiseId)

        val weekStats =
          games.sortBy(_.week).map { game =>
            val (teamScore, opponentScore, opponentId) =
              if (game.homeTeamId == team.franchiseId) {
                (game.homeScore, game.awayScore, game.awayTeamId)
              } else {
                (game.awayScore, game.homeScore, game.homeTeamId)
              }

            val wins   = if (teamScore > opponentScore) 1 else 0
            val losses = if (teamScore < opponentScore) 1 else 0

            val opponent = season.team(opponentId)

            def accumRecord(target: Int, pred: Team => Boolean) = {
              if (target > 0) {
                opponent.collect { case opp if pred(opp) => 1 }.getOrElse(0)
              } else {
                0
              }
            }

            val divisionWins     = accumRecord(wins, _.divisionId == team.divisionId)
            val divisionLosses   = accumRecord(losses, _.divisionId == team.divisionId)
            val conferenceWins   = accumRecord(wins, _.conferenceId == team.conferenceId)
            val conferenceLosses = accumRecord(losses, _.conferenceId == team.conferenceId)

            BasicStatistics(
              season.year,
              game.week,
              team.franchiseId,
              Record(wins, losses, 0),
              Record(divisionWins, divisionLosses, 0),
              Record(conferenceWins, conferenceLosses, 0),
              teamScore,
              opponentScore,
              0
            )

          }

        // Now accumulate previous weeks into latter ones
        weekStats.map { stats =>
          weekStats.filter { otherStats =>
            otherStats.franchiseId == stats.franchiseId && otherStats.week < stats.week
          }.foldLeft(stats) {
            case (acc, elem) =>
              acc.copy(
                record = acc.record + elem.record,
                divisionRecord = acc.divisionRecord + elem.divisionRecord,
                conferenceRecord = acc.conferenceRecord + elem.conferenceRecord,
                pointsFor = acc.pointsFor + elem.pointsFor,
                pointsAgainst = acc.pointsAgainst + elem.pointsAgainst
              )
          }
        }
      }

    // Now that accumlated stats are in tact, we can calculate
    // games behind for each team for each week
    recordStats.map { stats =>
      season
        .team(stats.franchiseId)
        .map(_.divisionId)
        .flatMap { divisionId =>
          val divisionalRecords =
            season.teams
              .filter(_.divisionId == divisionId)
              .flatMap(t =>
                recordStats.filter(s => s.week == stats.week && s.franchiseId == t.franchiseId))

          divisionalRecords
            .sortBy(r => 1 - (r.record.winPercentage))
            .headOption
            .map { r =>
              stats.copy(gamesBehind = stats.record.gamesBehind(r.record))
            }
        }
        .getOrElse(stats)
    }
  }
}
