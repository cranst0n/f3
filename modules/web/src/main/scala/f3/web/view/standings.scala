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
package view

import scala.Some

import cats.implicits._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import f3.core._
import f3.core.stats._

object Standings {

  case class Props(proxy: ModelProxy[AppModel])

  class Backend($ : BackendScope[Props, Unit]) {
    def render(p: Props) = {

      val model = p.proxy()

      <.table(
        ^.cls := "ui green celled collapsing unstackable small table center",
        <.thead(
          <.tr(
            <.th(^.cls := "center aligned", "Team"),
            <.th(^.cls := "center aligned", "Wins"),
            <.th(^.cls := "center aligned", "Losses"),
            <.th(^.cls := "center aligned", "PF"),
            <.th(^.cls := "center aligned", "PA"),
            <.th(^.cls := "center aligned mini hidden", "+/-"),
            <.th(^.cls := "center aligned mini hidden", "GB")
          )
        ),
        <.tbody(

          for {
            league <- model.league
            selection <- model.selection
            season <- league.seasons.find(_.year == selection.season)
          } yield {
            val week = selection.week

            val basicStats = model.basicStats(league)
            val teamStats = basicStats.filter(stats =>
              stats.season == season.year && stats.week == week)

            val teamWithStats = season.teams.map { team =>
              (team, teamStats.find(_.franchiseId == team.franchiseId))
            }.sortWith { case ((aTeam, aStats), (bTeam, bStats)) =>
              (aStats, bStats) match {
                case (Some(as), Some(bs)) => {
                  if(as.record.wins > bs.record.wins) true
                  else if(as.record.wins < bs.record.wins) false
                  else {
                    as.pointsFor > bs.pointsFor
                  }
                }
                case _ => true
              }
            }

            teamWithStats.scanLeft(none[(Int, Team, BasicStatistics)]) {
              case (lastOpt, (team, stats)) =>
                lastOpt.map {
                  case (lastStripe, lastTeam, last) =>
                    stats.map { current =>
                      val newStripe =
                        if (last.gamesBehind != current.gamesBehind) (lastStripe + 1) % 2
                        else lastStripe
                      (newStripe, team, current)
                    }
                }.getOrElse {
                  stats.map(s => (0, team, s))
                }
            }.flatten.map { case (stripeNum, team, stats) =>

              val pointDiffString =
                if(stats.pointsFor < stats.pointsAgainst) {
                  s"${stats.pointsFor - stats.pointsAgainst}"
                } else {
                  s"+${stats.pointsFor - stats.pointsAgainst}"
                }

              val gamesBehindString =
                if(stats.gamesBehind > 0) {
                  s"${stats.gamesBehind}"
                } else {
                  "-"
                }

              <.tr(
                ^.cls := s"stripe$stripeNum",
                <.td(^.cls := "center aligned", s"${team.name}"),
                <.td(^.cls := "center aligned", s"${stats.record.wins}"),
                <.td(^.cls := "center aligned", s"${stats.record.losses}"),
                <.td(^.cls := "center aligned", s"${stats.pointsFor}"),
                <.td(^.cls := "center aligned", s"${stats.pointsAgainst}"),
                <.td(^.cls := "center aligned mini hidden", pointDiffString),
                <.td(^.cls := "center aligned mini hidden", gamesBehindString)
              )
            }
          }
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("Standings")
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[AppModel]) = component(Props(proxy))

}
