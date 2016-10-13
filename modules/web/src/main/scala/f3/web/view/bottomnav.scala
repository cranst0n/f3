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

import diode.react.ModelProxy
import f3.core._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object BottomNav {

  case class Props(proxy: ModelProxy[AppModel])

  class Backend($ : BackendScope[Props, Unit]) {
    def render(p: Props) = {

      val model = p.proxy()

      model.league.map { league =>
        <.div(
          ^.cls := "ui bottom fixed two item menu small-screen-only",
          <.div(
            ^.cls := "ui scrolling dropdown item",
            seasonDropdownTitle(model),
            <.div(
              ^.cls := "upward menu",
              league.seasons.map(s => seasonDropdownItem(p.proxy, model, s))
            )
          ),
          <.div(
            ^.cls := "ui scrolling dropdown item",
            weekDropdownTitle(model),
            <.div(
              ^.cls := "upward menu",
              for {
                selection <- model.selection
                season    <- league.seasons.find(_.year == selection.season)
              } yield {
                (1 to season.regularSeasonWeeks.min(season.currentWeek)).map { week =>
                  weekDropdownItem(p.proxy, model, week)
                }
              }
            )
          )
        )
      }.getOrElse(<.div())
    }
  }

  private[this] def seasonDropdownTitle(model: AppModel) = {
    model.selection.map(s => s"${s.season}").getOrElse("Season")
  }

  private[this] def weekDropdownTitle(model: AppModel) = {
    model.selection.map(s => s"Week ${s.week}").getOrElse("Week")
  }

  def seasonDropdownItem(proxy: ModelProxy[_], model: AppModel, season: Season) = {
    val cls =
      model.selection.filter(_.season == season.year).map(_ => "active item").getOrElse("item")
    <.a(
      ^.cls := cls,
      ^.onClick --> proxy.dispatchCB(SelectSeason(season.year)),
      s"${season.year}"
    )
  }

  def weekDropdownItem(proxy: ModelProxy[_], model: AppModel, week: Int) = {
    <.a(
      ^.cls := model.selection.filter(_.week == week).map(_ => "active item").getOrElse("item"),
      ^.onClick --> proxy.dispatchCB(SelectWeek(week)),
      s"Week $week"
    )
  }

  private val component = ReactComponentB[Props]("BottomNav")
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[AppModel]) = component(Props(proxy))
}
