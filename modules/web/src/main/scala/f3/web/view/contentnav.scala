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

import org.scalajs.jquery._

import cats.implicits._
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import f3.core._

object ContentNav {

  case class Props(proxy: ModelProxy[AppModel])

  class Backend($ : BackendScope[Props, Unit]) {

    val menuTypes =
      List(
        StandingsContent,
        PowerRankingsContent,
        ELOContent,
        PlayoffsContent,
        RecordBookContent
      )

    def render(p: Props) = {

      val model = p.proxy()

      <.div(
        <.div(
          ^.cls := "ui left fixed vertical green inverted menu content-nav large-screen-only",
          <.div(
            ^.cls := "item",
            <.div(
              ^.cls := "menu",
              menuTypes.map(pageMenuItem(p.proxy, _, p.proxy().activeContent))
            )
          ),
          <.div(
            ^.cls := "item",
            <.div(^.cls := "header", "League"),
            <.div(
              ^.cls := "menu",
              model.leagues.zipWithIndex.map {
                case (league, ix) =>
                  leagueMenuItem(p.proxy, league, ix, model.selection.map(_.leagueIx))
              }
            )
          ),
          <.div(
            ^.cls := "item large-screen-only",
            <.div(^.cls := "header", "Season"),
            <.div(
              ^.cls := "menu",
              for {
                selection <- model.selection
                league    <- model.league
              } yield {
                league.seasons.sortBy(-_.year).map { season =>
                  seasonMenuItem(p.proxy, model, season)
                }
              }
            )
          ),
          <.div(
            ^.cls := "item large-screen-only",
            <.div(^.cls := "header", "Week"),
            <.div(
              ^.cls := "menu",
              for {
                selection <- model.selection
                league    <- model.league
                season    <- league.seasons.find(_.year == selection.season)
              } yield {
                (1 to season.regularSeasonWeeks.min(season.currentWeek)).reverse.map { week =>
                  weekMenuItem(p.proxy, model, week)
                }
              }
            )
          )
        )
      )
    }

    def pageMenuItem(proxy: ModelProxy[_], menuContent: ContentType, activeContent: ContentType) = {
      <.a(
        ^.cls := (if (menuContent == activeContent) "active item" else "item"),
        ^.onClick --> {
          hideMenu()
          proxy.dispatchCB(SelectContent(menuContent))
        },
        menuContent.show
      )
    }

    def leagueMenuItem(proxy: ModelProxy[_],
                       league: League,
                       leagueIx: Int,
                       selectedLeagueIx: Option[Int]) = {
      <.a(
        ^.cls := selectedLeagueIx.filter(_ == leagueIx).map(_ => "active item").getOrElse("item"),
        ^.onClick --> {
          hideMenu()
          proxy.dispatchCB(SelectLeague(leagueIx.some))
        },
        league.name
      )
    }

    def seasonMenuItem(proxy: ModelProxy[_], model: AppModel, season: Season) = {
      val cls =
        model.selection.filter(_.season == season.year).map(_ => "active item").getOrElse("item")
      <.a(
        ^.cls := cls,
        ^.onClick --> {
          hideMenu()
          proxy.dispatchCB(SelectSeason(season.year))
        },
        s"${season.year}"
      )
    }

    def weekMenuItem(proxy: ModelProxy[_], model: AppModel, week: Int) = {
      <.a(
        ^.cls := model.selection.filter(_.week == week).map(_ => "active item").getOrElse("item"),
        ^.onClick --> {
          hideMenu()
          proxy.dispatchCB(SelectWeek(week))
        },
        s"Week $week"
      )
    }

    def hideMenu(): Unit = {
      if (org.scalajs.dom.window.matchMedia("(max-width: 960px)").matches) {
        jQuery(".mobile-show-nav-btn").toggleClass("active")
        jQuery(".content-nav").toggleClass("large-screen-only")
      }
    }
  }

  private val component = ReactComponentB[Props]("ContentNav")
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[AppModel]) = component(Props(proxy))
}
