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

case class ColorScale(mapping: List[(Double, Color)]) {

  def build = {
    mapping.map {
      case (v, color) =>
        js.Array[String](v.toString, color.value)
    }.toJSArray
  }
}

object ColorScale {

  def create(colors: List[Color]): ColorScale = {
    val step = 1d / (colors.size - 1)
    ColorScale(
      colors.zipWithIndex.map { case (color, ix) => ((ix * step)) -> color }.toList
    )
  }

  val Parula: ColorScale = create(
    List(
      Color.hex("#352a87"),
      Color.hex("#0363e1"),
      Color.hex("#1485d4"),
      Color.hex("#06a7c6"),
      Color.hex("#38b99e"),
      Color.hex("#92bf73"),
      Color.hex("#d9ba56"),
      Color.hex("#fcce2e"),
      Color.hex("#f9fb0e")
    )
  )
}
