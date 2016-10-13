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

object scatter {

  def line   = LineBuilder()
  def marker = MarkerBuilder()
  def trace  = TraceBuilder()

  @js.native
  trait Line extends js.Object
  @js.native
  trait Marker extends js.Object

  // values: ['lines', 'markers', 'text']
  sealed abstract case class Mode(value: String) extends Product with Serializable
  object Lines                                   extends Mode("lines")
  object Markers                                 extends Mode("markers")
  object LinesAndMarkers                         extends Mode("lines+markers")
  object Text                                    extends Mode("text")

  // values: ['points', 'fills']
  sealed abstract case class HoverOn(value: String) extends Product with Serializable
  object Points                                     extends HoverOn("points")
  object Fills                                      extends HoverOn("fills")

  // values: ['linear', 'spline', 'hv', 'vh', 'hvh', 'vhv']
  sealed abstract case class Shape(value: String) extends Product with Serializable
  object Linear                                   extends Shape("linear")
  object Spline                                   extends Shape("spline")
  object HV                                       extends Shape("hv")
  object VH                                       extends Shape("vh")
  object HVH                                      extends Shape("hvh")
  object VHV                                      extends Shape("vhv")

  // values: ['solid', 'dot', 'dash', 'longdash', 'dashdot', 'longdashdot']
  sealed abstract case class Dash(value: String) extends Product with Serializable
  object Solid                                   extends Dash("solid")
  object Dot                                     extends Dash("dot")
  object Dash                                    extends Dash("dash")
  object LongDash                                extends Dash("longdash")
  object DashDot                                 extends Dash("dashdot")
  object LongDashDot                             extends Dash("longdashdot")

  // values: ['none', 'tozeroy', 'tozerox', 'tonexty', 'tonextx', 'toself', 'tonext']
  sealed abstract case class Fill(value: String) extends Product with Serializable
  object NoFill                                  extends Fill("none")
  object ToZeroY                                 extends Fill("tozeroy")
  object ToZeroX                                 extends Fill("tozerox")
  object ToNextY                                 extends Fill("tonexty")
  object ToNextX                                 extends Fill("tonextx")
  object ToSelf                                  extends Fill("toself")
  object ToNext                                  extends Fill("tonext")

  case class MarkerBuilder(
    maxDisplayed: Option[Double] = None,
    opacity: Option[Double] = None,
    size: Option[Double] = None,
    symbol: Option[Symbol] = None
  ) {

    def maxDisplayed(maxDisplayed: Double): MarkerBuilder = copy(maxDisplayed = Some(maxDisplayed))
    def opacity(opacity: Double): MarkerBuilder           = copy(opacity = Some(opacity))
    def size(size: Double): MarkerBuilder                 = copy(size = Some(size))
    def symbol(symbol: Symbol): MarkerBuilder             = copy(symbol = Some(symbol))

    def build = {
      js.Dictionary[Any](
          "maxDisplayed" -> maxDisplayed.getOrElse(js.undefined),
          "opacity"      -> opacity.getOrElse(js.undefined),
          "size"         -> size.getOrElse(js.undefined),
          "symbol"       -> symbol.map(_.value).getOrElse(js.undefined)
        )
        .filterNot { case (k, v) => js.isUndefined(v) }
        .dict
        .asInstanceOf[Marker]
    }
  }

  case class LineBuilder(
    dash: Option[Dash] = None,
    shape: Option[Shape] = None,
    simplify: Option[Boolean] = None,
    smoothing: Option[Double] = None
  ) {

    def dash(dash: Dash): LineBuilder             = copy(dash = Some(dash))
    def shape(shape: Shape): LineBuilder          = copy(shape = Some(shape))
    def simplify(simplify: Boolean): LineBuilder  = copy(simplify = Some(simplify))
    def smoothing(smoothing: Double): LineBuilder = copy(smoothing = Some(smoothing))

    def build = {
      js.Dictionary[Any](
          "dash"      -> dash.map(_.value).getOrElse(js.undefined),
          "shape"     -> shape.map(_.value).getOrElse(js.undefined),
          "simplify"  -> simplify.getOrElse(js.undefined),
          "smoothing" -> smoothing.getOrElse(js.undefined)
        )
        .filterNot { case (k, v) => js.isUndefined(v) }
        .dict
        .asInstanceOf[Line]
    }
  }

  case class TraceBuilder(
    connectGaps: Option[Boolean] = None,
    fill: Option[Fill] = None,
    fillColor: Option[Color] = None,
    hoverOn: Option[HoverOn] = None,
    line: Option[Line] = None,
    name: Option[String] = None,
    marker: Option[Marker] = None,
    mode: Option[Mode] = None,
    textFont: Option[Font] = None,
    textPosition: Option[TextPosition] = None,
    x: Option[Array[Double]] = None,
    y: Option[Array[Double]] = None
  ) {

    def connectGaps(connectGaps: Boolean): TraceBuilder = copy(connectGaps = Some(connectGaps))
    def fill(fill: Fill): TraceBuilder                  = copy(fill = Some(fill))
    def fillColor(fillColor: Color): TraceBuilder       = copy(fillColor = Some(fillColor))
    def hoverOn(hoverOn: HoverOn): TraceBuilder         = copy(hoverOn = Some(hoverOn))
    def line(line: LineBuilder): TraceBuilder           = copy(line = Some(line.build))
    def named(name: String): TraceBuilder               = copy(name = Some(name))
    def marker(marker: MarkerBuilder): TraceBuilder     = copy(marker = Some(marker.build))
    def mode(mode: Mode): TraceBuilder                  = copy(mode = Some(mode))
    def textFont(textFont: FontBuilder): TraceBuilder   = copy(textFont = Some(textFont.build))
    def textPosition(textPosition: TextPosition): TraceBuilder =
      copy(textPosition = Some(textPosition))
    def x(x: Array[Double]): TraceBuilder = copy(x = Some(x))
    def y(y: Array[Double]): TraceBuilder = copy(y = Some(y))

    def build = {
      js.Dictionary[Any](
          "connectgaps"  -> connectGaps.getOrElse(js.undefined),
          "fill"         -> fill.map(_.value).getOrElse(js.undefined),
          "fillcolor"    -> fillColor.map(_.value).getOrElse(js.undefined),
          "hoveron"      -> hoverOn.map(_.value).getOrElse(js.undefined),
          "line"         -> line.getOrElse(js.undefined),
          "name"         -> name.getOrElse(js.undefined),
          "marker"       -> marker.getOrElse(js.undefined),
          "mode"         -> mode.map(_.value).getOrElse(js.undefined),
          "textfont"     -> textFont.getOrElse(js.undefined),
          "textposition" -> textPosition.map(_.value).getOrElse(js.undefined),
          "type"         -> "scatter",
          "x"            -> x.map(_.toJSArray).getOrElse(js.undefined),
          "y"            -> y.map(_.toJSArray).getOrElse(js.undefined)
        )
        .filterNot { case (k, v) => js.isUndefined(v) }
        .dict
        .asInstanceOf[Trace]
    }
  }

}
