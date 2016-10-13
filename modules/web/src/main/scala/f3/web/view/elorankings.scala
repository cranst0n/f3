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
import scala.Predef._

import scala.scalajs.js.JSConverters._

import org.scalajs.jquery._

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import plotly.js._

object ELORankings {

  case class Props(proxy: ModelProxy[AppModel])

  class Backend(scope: BackendScope[Props, Unit]) {
    def render(p: Props) = {
      val table = renderTable(p)
      initPlot(p)
      table
    }

    private[this] def renderTable(p: Props) = {
      val model = p.proxy()

      val teamWithStats =
        for {
          league    <- model.league
          selection <- model.selection
          season    <- league.seasons.find(_.year == selection.season)
        } yield {
          val week = selection.week

          val eloStats = model.eloStats(league)
          val teamStats =
            eloStats.filter(stats => stats.season == season.year && stats.week == week)

          season.teams.map { team =>
            (team, teamStats.find(_.franchiseId == team.franchiseId))
          }.sortBy { case (team, stats) => stats.map(-_.rating).getOrElse(0d) }
        }

      <.div(
        ^.cls := "ui stackable grid",
        <.div(
          ^.cls := "center aligned five wide column",
          <.table(
            ^.cls := "ui green collapsing unstackable centered table",
            <.thead(
              <.tr(
                <.th(^.cls := "center aligned", "Rank"),
                <.th(^.cls := "center aligned", "Team"),
                <.th(^.cls := "center aligned", "Points")
              )
            ),
            <.tbody(
              teamWithStats.map { teamStats =>
                teamStats.zipWithIndex.map {
                  case ((team, stats), rank) =>
                    <.tr(
                      ^.cls := s"tier${stats.map(_.tier).getOrElse(0)}",
                      <.td(^.cls := "center aligned", s"${rank + 1}"),
                      <.td(^.cls := "center aligned", s"${team.name}"),
                      <.td(^.cls := "center aligned", f"${stats.map(_.rating).getOrElse(0d)}%.2f")
                    )
                }
              }
            )
          )
        ),
        <.div(
          ^.cls := "center aligned eleven wide column",
          <.div(^.id := "plotly-plot")
        )
      )
    }

    def initPlot(p: Props) = {
      if (jQuery("#plotly-plot").length > 0) {

        val model = p.proxy()

        for {
          league    <- model.league
          selection <- model.selection
          season    <- league.seasons.find(_.year == selection.season)
        } yield {

          val week = selection.week

          val eloStats =
            model
              .eloStats(league)
              .filter(s => s.season == season.year && s.week <= selection.week)

          val data = eloStats
            .groupBy(_.franchiseId)
            .map {
              case (franchiseId, weeklyElo) =>
                val sortedWeeks = weeklyElo.sortWith { (x, y) =>
                  if (x.season < y.season) true
                  else if (y.season < x.season) false
                  else if (x.week < y.week) true
                  else if (y.week < x.week) false
                  else true
                }

                val name =
                  season.teams.find(_.franchiseId == franchiseId).map(_.name).getOrElse("???")

                val x = List.range(0, sortedWeeks.size).map(_.toDouble + 1).toArray
                val y = sortedWeeks.map(_.rating).toArray

                import scatter._

                trace
                  .named(name)
                  .x(x)
                  .y(y)
                  .mode(scatter.Lines)
                  .line(line.shape(Spline))
            }
            .toList
            .sortWith { (a, b) =>
              (a.y, b.y) match {
                case (Some(ax), Some(bx)) => ax.last > bx.last
                case _                    => true
              }
            }
            .map(_.build)

          Plotly.newPlot(
            "plotly-plot",
            data.toJSArray,
            layout.titled("Weekly ELO Ratings").build,
            DisplayOptions(false)
          )

          plotly.js.util.maintainSize("plotly-plot", 1.0, 1.0, 400)
        }
      }
    }
  }

  private val component = ReactComponentB[Props]("EloRankings")
    .renderBackend[Backend]
    .componentDidMount(scope => CallbackTo.pure(scope.backend.initPlot(scope.props)))
    .build

  def apply(proxy: ModelProxy[AppModel]) = component(Props(proxy))

}
