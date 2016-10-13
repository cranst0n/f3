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

import scala.inline

package object f3 {

  @inline def identity[A](a: A): A    = a
  def implicitly[A](implicit a: A): A = a

  type Any                   = scala.Any
  type AnyRef                = scala.AnyRef
  type AnyVal                = scala.AnyVal
  type BigInt                = scala.BigInt
  type BigDecimal            = scala.BigDecimal
  type Boolean               = scala.Boolean
  type Byte                  = scala.Byte
  type Char                  = scala.Char
  type Double                = scala.Double
  type Float                 = scala.Float
  type Int                   = scala.Int
  type Long                  = scala.Long
  type Nothing               = scala.Nothing
  type PartialFunction[A, B] = scala.PartialFunction[A, B]
  type Product               = scala.Product
  type Serializable          = scala.Serializable
  type Short                 = scala.Short
  type String                = java.lang.String
  type Unit                  = scala.Unit
  type StringContext         = scala.StringContext

  final val BigInt        = scala.BigInt
  final val BigDecimal    = scala.BigDecimal
  final val Boolean       = scala.Boolean
  final val Byte          = scala.Byte
  final val Char          = scala.Char
  final val Double        = scala.Double
  final val Float         = scala.Float
  final val Int           = scala.Int
  final val Long          = scala.Long
  final val Short         = scala.Short
  final val Unit          = scala.Unit
  final val StringContext = scala.StringContext

  type List[A]   = scala.collection.immutable.List[A]
  type Map[A, B] = scala.collection.immutable.Map[A, B]
  type Option[A] = scala.Option[A]

  final val List   = scala.collection.immutable.List
  final val Map    = scala.collection.immutable.Map
  final val Option = scala.Option

  @inline implicit def byteWrapper(x: Byte)       = new scala.runtime.RichByte(x)
  @inline implicit def shortWrapper(x: Short)     = new scala.runtime.RichShort(x)
  @inline implicit def intWrapper(x: Int)         = new scala.runtime.RichInt(x)
  @inline implicit def charWrapper(c: Char)       = new scala.runtime.RichChar(c)
  @inline implicit def longWrapper(x: Long)       = new scala.runtime.RichLong(x)
  @inline implicit def floatWrapper(x: Float)     = new scala.runtime.RichFloat(x)
  @inline implicit def doubleWrapper(x: Double)   = new scala.runtime.RichDouble(x)
  @inline implicit def booleanWrapper(x: Boolean) = new scala.runtime.RichBoolean(x)

  /** `???` can be used for marking methods that remain to be implemented.
    *  @throws NotImplementedError
    *  @group utilities
    */
  def ??? : Nothing = throw new scala.NotImplementedError

  // The dollar prefix is to dodge accidental shadowing of this method
  // by a user-defined method of the same name (SI-7788).
  // The collections rely on this method.
  implicit def $conforms[A]: A <:< A = <:<.singleton.asInstanceOf[A <:< A]

  implicit final class ArrowAssoc[A](val self: A) extends AnyVal {
    @inline def ->[B](y: B): scala.Tuple2[A, B] = scala.Tuple2(self, y)
    def →[B](y: B): scala.Tuple2[A, B]          = ->(y)
  }

  def print(x: Any)   = scala.Console.print(x)
  def println()       = scala.Console.println()
  def println(x: Any) = scala.Console.println(x)

}
