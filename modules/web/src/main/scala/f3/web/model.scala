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
package web

import scala.concurrent.Future

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import f3.core._
import f3.core.stats._

case class Selection(leagueIx: Int, season: Int, week: Int)

case class AppModel(
  leagues: List[League],
  activeContent: ContentType,
  selection: Option[Selection]
) {

  def league: Option[League] = selection.flatMap(s => leagues.lift(s.leagueIx))

  def basicStats(league: League): List[BasicStatistics] = {
    AppModel.BasicStatCache.getOrElseUpdate(league, stats.basic(league))
  }

  def powerStats(league: League): List[PowerStatistics] = {
    AppModel.PowerStatCache.getOrElseUpdate(league, stats.power(league))
  }

  def eloStats(league: League): List[EloStatistics] = {
    AppModel.EloStatCache.getOrElseUpdate(league, stats.elo(league))
  }

  def initCache(leagues: List[League]): Unit = {
    leagues.foreach { l =>
      Future(stats.basic(l)).map(AppModel.BasicStatCache.put(l, _))
      Future(stats.power(l)).map(AppModel.PowerStatCache.put(l, _))
      Future(stats.elo(l)).map(AppModel.EloStatCache.put(l, _))
    }
  }
}

object AppModel {
  protected val BasicStatCache = scala.collection.mutable.Map[League, List[BasicStatistics]]()
  protected val PowerStatCache = scala.collection.mutable.Map[League, List[PowerStatistics]]()
  protected val EloStatCache   = scala.collection.mutable.Map[League, List[EloStatistics]]()
}

sealed abstract case class ContentType(show: String)
object StandingsContent extends ContentType("Standings")
object PowerRankingsContent extends ContentType("Power Rankings")
object ELOContent extends ContentType("ELO")
object PlayoffsContent extends ContentType("Playoffs")
object RecordBookContent extends ContentType("Record Book")
