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

import cats.implicits._
import diode._
import diode.ActionResult.ModelUpdate
import diode.react.ReactConnector

object AppCircuit extends Circuit[AppModel] with ReactConnector[AppModel] {

  def initialModel = AppModel(List(), StandingsContent, none[Selection])

  override val actionHandler: HandlerFunction = { (model, action) =>
    action match {
      case LoadLeagues(leagues) => {
        model.initCache(leagues)
        ModelUpdate(model.copy(leagues = leagues, selection = none[Selection])).some
      }
      case SelectLeague(leagueIx) => {
        ModelUpdate(
          model.copy(
            selection = {
              leagueIx.flatMap { ix =>
                model.leagues.lift(ix).map { league =>
                  Selection(
                    ix,
                    league.currentSeason.year,
                    league.currentSeason.currentWeek
                  )
                }
              }
            }
          )
        ).some
      }
      case SelectSeason(season) => {
        val week =
          model.league.flatMap(_.seasons.find(_.year == season).map(_.currentWeek)).getOrElse(1)
        ModelUpdate(
          model.copy(selection = model.selection.map(_.copy(season = season, week = week)))).some
      }
      case SelectWeek(week) => {
        ModelUpdate(model.copy(selection = model.selection.map(_.copy(week = week)))).some
      }
      case SelectContent(contentType) => ModelUpdate(model.copy(activeContent = contentType)).some
      case _                          => none[ModelUpdate[AppModel]]
    }
  }
}
