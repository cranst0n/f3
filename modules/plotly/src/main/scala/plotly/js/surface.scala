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

/**
  * Sample usage:
  *
  * {{{
  *   val surfaceData =
  *     Array.tabulate(100, 100) { (x, y) =>
  *       val a = sqrt(pow(x - 50d, 2) + pow(y - 50d, 2))
  *       sin(a) / a
  *     }
  *
  *   val testTrace =
  *     surface.trace
  *       .z(surfaceData)
  *       .colorScale(ColorScale.Parula)
  * }}}
  */
object surface {

  def trace = TraceBuilder()

  case class TraceBuilder(
    colorScale: Option[ColorScale] = None,
    lighting: Option[Lighting] = None,
    opacity: Option[Double] = None,
    reverseScale: Option[Boolean] = None,
    z: Option[Array[Array[Double]]] = None
  ) {

    def colorScale(colorScale: ColorScale): TraceBuilder  = copy(colorScale = Some(colorScale))
    def lighting(lighting: Lighting): TraceBuilder        = copy(lighting = Some(lighting))
    def opacity(opacity: Double): TraceBuilder            = copy(opacity = Some(opacity))
    def reverseScale(reverseScale: Boolean): TraceBuilder = copy(reverseScale = Some(reverseScale))
    def z(z: Array[Array[Double]]): TraceBuilder          = copy(z = Some(z))

    def build = {
      js.Dictionary[Any](
          "colorscale"   -> colorScale.map(_.build).getOrElse(js.undefined),
          "lighting"     -> lighting.map(_.build).getOrElse(js.undefined),
          "opacity"      -> opacity.getOrElse(js.undefined),
          "reversescale" -> reverseScale.getOrElse(js.undefined),
          "type"         -> "surface",
          "z"            -> z.map(_.map(_.toJSArray).toJSArray).getOrElse(js.undefined)
        )
        .filterNot { case (k, v) => js.isUndefined(v) }
        .dict
        .asInstanceOf[Trace]
    }
  }
}
