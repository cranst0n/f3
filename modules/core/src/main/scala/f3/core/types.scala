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

case class League(name: String, leagueType: LeagueType, owners: List[Owner], seasons: List[Season]) {
  def currentSeason: Season = seasons.sortBy(-_.year).head
}

case class Owner(id: Int, name: String)

case class Season(
  year: Int,
  conferences: List[Conference],
  regularSeasonWeeks: Int,
  games: List[Game],
  tieBreakers: List[TieBreaker],
  playoffSettings: Option[PlayoffSettings]
) {
  def teams: List[Team]                    = conferences.flatMap(_.divisions.flatMap(_.teams))
  def divisions: List[Division]            = conferences.flatMap(_.divisions)
  def team(franchiseId: Int): Option[Team] = teams.find(_.franchiseId == franchiseId)
  def currentWeek: Int =
    games
      .groupBy(_.week)
      .toList
      .sortBy(-_._1)
      .find {
        case (week, games) => games.forall(_.totalPoints != 0)
      }
      .map(_._1)
      .getOrElse(1)
}

case class Conference(id: Int, name: String, divisions: List[Division]) {
  def teams = divisions.flatMap(_.teams)
}

case class Division(id: Int, name: String, teams: List[Team])
case class Team(franchiseId: Int, ownerId: Int, conferenceId: Int, divisionId: Int, name: String)

object Team {
  val Bye = Team(-1, -1, -1, -1, "Bye")
}

case class Record(wins: Int, losses: Int, ties: Int) {

  val winPercentage = wins / (wins + losses).toDouble

  def +(other: Record): Record =
    Record(wins + other.wins, losses + other.losses, ties + other.ties)

  def gamesBehind(other: Record): Double = {
    ((other.wins - wins + losses - other.losses) / 2d).max(0)
  }
}

case class Game(
  season: Int,
  week: Int,
  awayTeamId: Int,
  homeTeamId: Int,
  awayScore: Double,
  homeScore: Double
) {
  def totalPoints = awayScore + homeScore
  def isBye       = awayTeamId == Team.Bye.franchiseId || homeTeamId == Team.Bye.franchiseId
}

case class PlayoffSettings(
  teams: Int,
  weeks: Int,
  divisionWinners: Int,
  tieBreakers: List[TieBreaker]
)

sealed trait LeagueType     extends Product with Serializable
case object FantasyFootball extends LeagueType

sealed trait TieBreaker extends Product with Serializable
case object HeadToHeadRecord extends TieBreaker
case object HeadToHeadPoints extends TieBreaker
case object ConferenceRecord extends TieBreaker
case object DivisionRecord extends TieBreaker
case object TotalPointsFor extends TieBreaker
case object BenchPoints extends TieBreaker
