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

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object TopNav {

  case class Props(proxy: ModelProxy[AppModel])

  class Backend($ : BackendScope[Props, Unit]) {
    def render(p: Props) = {

      val model = p.proxy()

      <.div(
        ^.cls := "ui fixed main menu",
        <.div(
          <.a(
            ^.cls := "header green active item large-screen-only",
            <.h3("f3")
          ),
          <.span(
            ^.cls := "launch icon item mobile-show-nav-btn small-screen-only",
            <.i(^.cls := "large content icon"),
            ^.onClick --> CallbackTo {
              jQuery(".mobile-show-nav-btn").toggleClass("active")
              jQuery(".content-nav").toggleClass("large-screen-only")
            }.void
          )
        ),
        <.div(
          ^.cls := "right menu",
          <.div(
            ^.cls := "item",
            <.a(
              ^.href := "https://github.com/cranst0n/f3",
              <.i(^.cls := "large grey github icon")
            )
          ),
          <.div(
            ^.cls := "item small-screen-only",
            <.h4(^.cls := "ui grey header", s"${model.activeContent.show}")
          )
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("TopNav")
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[AppModel]) = component(Props(proxy))

}
