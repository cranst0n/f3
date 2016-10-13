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

import scala.{ Double, Int, Unit }
import scala.Predef._

import scala.scalajs.js

import org.scalajs.dom._

@js.native
object Plotly extends js.Object {

  def newPlot(
    id: String,
    data: js.Array[Trace],
    layout: js.UndefOr[Layout] = null,
    displayOptions: js.UndefOr[DisplayOptions] = null
  ): Unit = js.native

  def relayout(id: String, options: RelayoutOptions): Unit = js.native

}

object util {

  def maintainSize(id: String,
                   parentWidthRatio: Double,
                   parentHeightRatio: Double,
                   minHeight: Int = 300): Unit = {

    val chartElement = js.Dynamic.global.document.getElementById(id)

    if (chartElement != null) {

      val parent = chartElement.parentNode.asInstanceOf[Element]

      def sizeIt(): Unit = {
        val w = parent.clientWidth.toDouble * parentWidthRatio
        val h = (parent.clientHeight.toDouble * parentHeightRatio).max(minHeight.toDouble)
        Plotly.relayout(id, RelayoutOptions(w, h))
      }

      sizeIt()
      window.onresize = (e: UIEvent) => sizeIt()
    }

  }

}
