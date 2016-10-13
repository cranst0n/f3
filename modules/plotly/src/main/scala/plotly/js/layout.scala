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
trait Layout extends js.Object

@js.native
trait Margin extends js.Object

sealed abstract case class HoverMode(value: String)
object HoverMode {
  object Closest extends HoverMode("closest")
  object X       extends HoverMode("x")
  object Y       extends HoverMode("y")
}

sealed abstract case class BarMode(value: String)
object BarMode {
  object Group    extends BarMode("group")
  object Relative extends BarMode("relative")
  object Stack    extends BarMode("stack")
}

case class MarginBuilder(
  top: Int,
  right: Int,
  bottom: Int,
  left: Int,
  pad: Option[Int] = None,
  autoExpand: Option[Boolean] = None
) {
  def build = {
    js.Dictionary[Any](
        "top"        -> top,
        "right"      -> right,
        "bottom"     -> bottom,
        "left"       -> left,
        "pad"        -> pad.getOrElse(js.undefined),
        "autoexpand" -> autoExpand.getOrElse(js.undefined)
      )
      .filterNot { case (k, v) => js.isUndefined(v) }
      .dict
      .asInstanceOf[Margin]
  }
}

case class LayoutBuilder(
  autoSize: Option[Boolean] = None,
  barGap: Option[Double] = None,
  barGroupGap: Option[Double] = None,
  barMode: Option[BarMode] = None,
  height: Option[Double] = None,
  hideSources: Option[Boolean] = None,
  hoverMode: Option[HoverMode] = None,
  margin: Option[Margin] = None,
  showLegend: Option[Boolean] = None,
  title: Option[String] = None,
  width: Option[Double] = None
) {

  def titled(title: String): LayoutBuilder           = copy(title = Some(title))
  def width(width: Double): LayoutBuilder            = copy(width = Some(width))
  def height(height: Double): LayoutBuilder          = copy(height = Some(height))
  def showLegend(showLegend: Boolean): LayoutBuilder = copy(showLegend = Some(showLegend))
  def hoverMode(hoverMode: HoverMode): LayoutBuilder = copy(hoverMode = Some(hoverMode))

  def clearTitle     = copy(title = None)
  def clearWidth     = copy(width = None)
  def clearHeight    = copy(height = None)
  def clearLegend    = copy(showLegend = None)
  def clearHoverMode = copy(hoverMode = None)

  def build = {
    js.Dictionary[Any](
        "autosize"    -> autoSize.getOrElse(js.undefined),
        "bargap"      -> barGap.getOrElse(js.undefined),
        "bargroupgap" -> barGroupGap.getOrElse(js.undefined),
        "barmode"     -> barMode.map(_.value).getOrElse(js.undefined),
        "height"      -> height.getOrElse(js.undefined),
        "hidesources" -> hideSources.getOrElse(js.undefined),
        "hovermode"   -> hoverMode.map(_.value).getOrElse(js.undefined),
        "margin"      -> margin.getOrElse(js.undefined),
        "showlegend"  -> showLegend.getOrElse(js.undefined),
        "title"       -> title.getOrElse(js.undefined),
        "width"       -> width.getOrElse(js.undefined)
      )
      .filterNot { case (k, v) => js.isUndefined(v) }
      .dict
      .asInstanceOf[Layout]
  }
}
