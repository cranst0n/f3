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
import scala.scalajs.js.JSConverters._

object bar {

  def trace = TraceBuilder()

  // TODO: marker

  case class TraceBuilder(
    offset: Option[Double] = None,
    orientation: Option[Orientation] = None,
    width: Option[Double] = None,
    x: Option[Array[Double]] = None,
    y: Option[Array[Double]] = None
  ) {

    def offset(offset: Double): TraceBuilder                = copy(offset = Some(offset))
    def orientation(orientation: Orientation): TraceBuilder = copy(orientation = Some(orientation))
    def width(width: Double): TraceBuilder                  = copy(width = Some(width))
    def x(x: Array[Double]): TraceBuilder                   = copy(x = Some(x))
    def y(y: Array[Double]): TraceBuilder                   = copy(y = Some(y))

    def build = {
      js.Dictionary[Any](
          "offset"      -> offset.getOrElse(js.undefined),
          "orientation" -> orientation.map(_.value).getOrElse(js.undefined),
          "type"        -> "bar",
          "width"       -> width.getOrElse(js.undefined),
          "x"           -> x.map(_.toJSArray).getOrElse(js.undefined),
          "y"           -> y.map(_.toJSArray).getOrElse(js.undefined)
        )
        .filterNot { case (k, v) => js.isUndefined(v) }
        .dict
        .asInstanceOf[Trace]
    }
  }
}
