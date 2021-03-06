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

import scala.{ Product, Serializable }
import scala.Predef._

// values: ['top left', 'top center', 'top right',
//   'middle left', 'middle center', 'middle right',
//   'bottom left', 'bottom center', 'bottom right']
sealed abstract case class TextPosition(value: String) extends Product with Serializable

object TextPosition {
  object TopLeft      extends TextPosition("top left")
  object TopCenter    extends TextPosition("top center")
  object TopRight     extends TextPosition("top right")
  object MiddleLeft   extends TextPosition("middle left")
  object MiddleCenter extends TextPosition("middle center")
  object MiddleRight  extends TextPosition("middle right")
  object BottomLeft   extends TextPosition("bottom left")
  object BottomCenter extends TextPosition("bottom center")
  object BottomRight  extends TextPosition("bottom right")
}
