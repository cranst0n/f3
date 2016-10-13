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

import scala.{ Product, Serializable, StringContext }
import scala.Predef._

sealed abstract case class Symbol(base: String, modifier: String = "")
    extends Product
    with Serializable {
  def value = s"$base$modifier"

  def open: Symbol    = new Symbol(base, "-open")     {}
  def dot: Symbol     = new Symbol(base, "-dot")      {}
  def openDot: Symbol = new Symbol(base, "-open-dot") {}
}

object Symbol {
  object Circle           extends Symbol("circle")
  object Square           extends Symbol("square")
  object Diamond          extends Symbol("diamond")
  object Cross            extends Symbol("cross")
  object X                extends Symbol("x")
  object TriangleUp       extends Symbol("triangle-up")
  object TriangleDown     extends Symbol("triangle-down")
  object TriangleLeft     extends Symbol("triangle-left")
  object TriangleRight    extends Symbol("triangle-right")
  object TriangleNE       extends Symbol("triangle-ne")
  object TriangleSE       extends Symbol("triangle-se")
  object TriangleSW       extends Symbol("triangle-sw")
  object TriangleNW       extends Symbol("triangle-nw")
  object Pentagon         extends Symbol("pentagon")
  object Hexagon          extends Symbol("hexagon")
  object Hexagon2         extends Symbol("hexagon2")
  object Octagon          extends Symbol("octagon")
  object Star             extends Symbol("star")
  object Hexagram         extends Symbol("hexagram")
  object StarTriangleUp   extends Symbol("star-triangle-up")
  object StarTriangleDown extends Symbol("star-triangle-down")
  object StarSquare       extends Symbol("star-square")
  object StarDiamond      extends Symbol("star-diamond")
  object DiamondTall      extends Symbol("diamond-tall")
  object DiamondWide      extends Symbol("diamond-wide")
  object HourGlass        extends Symbol("hourglass")
  object BowTie           extends Symbol("bowtie")
  object CircleCross      extends Symbol("circle-cross")
  object CircleX          extends Symbol("circle-x")
  object SquareCross      extends Symbol("square-cross")
  object SquareX          extends Symbol("square-x")
  object DiamondCross     extends Symbol("diamond-cross")
  object DiamondX         extends Symbol("diamond-x")
  object CrossThin        extends Symbol("cross-thin")
  object XThin            extends Symbol("x-thin")
  object Asterisk         extends Symbol("asterisk")
  object Hash             extends Symbol("hash")
  object YUp              extends Symbol("y-up")
  object YDown            extends Symbol("y-down")
  object YLeft            extends Symbol("y-left")
  object YRight           extends Symbol("y-right")
  object LineEW           extends Symbol("line-ew")
  object LineNS           extends Symbol("line-ns")
  object LineNE           extends Symbol("line-ne")
  object LineNW           extends Symbol("line-nw")
}
