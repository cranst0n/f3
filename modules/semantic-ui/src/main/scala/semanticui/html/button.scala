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

package semanticui.html

import scala.{ Boolean, StringContext }
import scala.Predef._

import japgolly.scalajs.react.{ ReactComponentB, ReactNode }
import japgolly.scalajs.react.vdom.prefix_<^._

object button {

  sealed abstract class ButtonState(val cls: String)
  case object NoState  extends ButtonState("")
  case object Active   extends ButtonState("active")
  case object Disabled extends ButtonState("disabled")
  case object Loading  extends ButtonState("loading")

  sealed abstract class Emphasis(val cls: String)
  case object NoEmphasis extends Emphasis("")
  case object Primary    extends Emphasis("primary")
  case object Secondary  extends Emphasis("secondary")

  case class Props(
    state: ButtonState = NoState,
    emphasis: Emphasis = NoEmphasis,
    inverted: Boolean = false
  )

  private[this] def classSet(p: Props) =
    ^.classSet1(
      s"ui button ${p.state.cls} ${p.emphasis.cls}",
      "inverted" -> (p.inverted)
    )

  private[this] def component =
    ReactComponentB[Props]("Button")
      .renderPC(
        (_, p, c) =>
          <.div(
            classSet(p),
            c
        ))
      .build

  def apply(p: Props, children: ReactNode*) = component(p, children: _*)
  def apply(text: String)                   = component(Props(), text)

}
