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

import scala.scalajs.js

import org.scalajs.dom._
import org.scalajs.jquery._

import cats.implicits._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import semanticui.js._

import f3.sample._
import f3.web.view._

object app extends js.JSApp {

  override def main(): Unit = {

    val appContentElem = document.getElementById("app-content")

    val conn = AppCircuit.connect(x => x)

    val renderable = conn { p =>
      val content =
        Map(
          StandingsContent     -> Standings(p),
          PowerRankingsContent -> PowerRankings(p),
          ELOContent           -> ELORankings(p)
        )

      <.div(
        TopNav(p),
        ContentNav(p),
        <.div(
          ^.cls := "ui main container",
          Content(p, content)
        ),
        BottomNav(p)
      )
    }

    // TODO: This is hardcoded for the moment...
    AppCircuit.dispatch(LoadLeagues(List(fantasy.football.league)))
    AppCircuit.dispatch(SelectLeague(0.some))

    ReactDOM.render(renderable, appContentElem)

    initSemantic()
  }

  private[this] def initSemantic() = {
    jQuery(document).ready {
      jQuery(".ui.dropdown").dropdown(DropdownSettings(observeChanges = true))
    }
  }
}
