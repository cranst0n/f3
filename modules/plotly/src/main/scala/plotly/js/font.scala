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

package plotly.js

import scala._
import scala.Predef._

import scala.scalajs.js

@js.native
trait Font extends js.Object

case class FontBuilder(
  family: Option[String] = None,
  size: Option[Double] = None,
  color: Option[Color] = None
) {

  def family(family: String): FontBuilder = copy(family = Some(family))
  def size(size: Double): FontBuilder     = copy(size = Some(size))
  def color(color: Color): FontBuilder    = copy(color = Some(color))

  def build = {
    js.Dictionary[Any](
        "family" -> family.getOrElse(js.undefined),
        "size"   -> size.getOrElse(js.undefined),
        "color"  -> color.map(_.value).getOrElse(js.undefined)
      )
      .filterNot { case (k, v) => js.isUndefined(v) }
      .dict
      .asInstanceOf[Font]
  }
}
