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
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object Content {

  type ChildComponent = ReactComponentU[_, _, _, org.scalajs.dom.raw.Element]

  case class Props(
    proxy: ModelProxy[AppModel],
    content: Map[ContentType, ChildComponent]
  )

  class Backend($ : BackendScope[Props, Unit]) {
    def render(p: Props) = {
      p.proxy()
        .league
        .map(_ => p.content.get(p.proxy().activeContent).getOrElse(MissingContent))
        .getOrElse(SelectLeague)
    }
  }

  private val component = ReactComponentB[Props]("Content")
    .renderBackend[Backend]
    .build

  private val MissingContent = messageComponent("Coming soon...")
  private val SelectLeague   = messageComponent("")

  private def messageComponent(message: String): ChildComponent =
    ReactComponentB[Unit](message)
      .render(_ => <.div(message))
      .build()

  def apply(
    proxy: ModelProxy[AppModel],
    content: Map[ContentType, ChildComponent]
  ) = component(Props(proxy, content))

}
