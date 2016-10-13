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

package f3

import scala.annotation.implicitNotFound

/**
  * An instance of `A <:< B` witnesses that `A` is a subtype of `B`.
  * Requiring an implicit argument of the type `A <:< B` encodes
  * the generalized constraint `A <: B`.
  *
  * @note we need a new type constructor `<:<` and evidence `conforms`,
  * as reusing `Function1` and `identity` leads to ambiguities in
  * case of type errors (`any2stringadd` is inferred)
  *
  * To constrain any abstract type T that's in scope in a method's
  * argument list (not just the method's own type parameters) simply
  * add an implicit argument of type `T <:< U`, where `U` is the required
  * upper bound; or for lower-bounds, use: `L <:< T`, where `L` is the
  * required lower bound.
  *
  * In part contributed by Jason Zaugg.
  */
@implicitNotFound(msg = "Cannot prove that ${From} <:< ${To}.")
sealed abstract class <:<[-From, +To] extends (From => To) with Serializable

object <:< {
  final val singleton = new <:<[Any, Any] { def apply(x: Any): Any = x }
}

/** An instance of `A =:= B` witnesses that the types `A` and `B` are equal.
  *
  * @see `<:<` for expressing subtyping constraints
  */
@implicitNotFound(msg = "Cannot prove that ${From} =:= ${To}.")
sealed abstract class =:=[From, To] extends (From => To) with Serializable

object =:= {
  final val singleton               = new =:=[Any, Any] { def apply(x: Any): Any = x }
  implicit def tpEquals[A]: A =:= A = singleton.asInstanceOf[A =:= A]
}
