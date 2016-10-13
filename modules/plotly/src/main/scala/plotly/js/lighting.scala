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

case class Lighting(
  ambient: Option[Double] = None,
  diffuse: Option[Double] = None,
  specular: Option[Double] = None,
  roughness: Option[Double] = None,
  fresnel: Option[Double] = None
) {

  def ambient(ambient: Double): Lighting     = copy(ambient = Some(ambient))
  def diffuse(diffuse: Double): Lighting     = copy(diffuse = Some(diffuse))
  def specular(specular: Double): Lighting   = copy(specular = Some(specular))
  def roughness(roughness: Double): Lighting = copy(roughness = Some(roughness))
  def fresnel(fresnel: Double): Lighting     = copy(fresnel = Some(fresnel))

  def build = {
    js.Dictionary[Any](
        "ambient"   -> ambient.getOrElse(js.undefined),
        "diffuse"   -> diffuse.getOrElse(js.undefined),
        "specular"  -> specular.getOrElse(js.undefined),
        "roughness" -> roughness.getOrElse(js.undefined),
        "fresnel"   -> fresnel.getOrElse(js.undefined)
      )
      .filterNot { case (k, v) => js.isUndefined(v) }
      .dict
  }

}
