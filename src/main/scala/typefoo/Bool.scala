/**
 * Copyright 2016 Harshad Deo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package typefoo

import language.higherKinds

/**
 * Church encodings of booleans.
 *
 * Can be understood as an alias for a type constructor that chooses between one of two alternative types. Is primarily
 * used to enforce the invariants of more complex type-level operations
 *
 * @author Harshad Deo
 * @since 0.1
 */
sealed trait Bool {
  type If[T <: Up, F <: Up, Up] <: Up
}

trait True extends Bool {
  override type If[T <: Up, F <: Up, Up] = T
}

trait False extends Bool {
  override type If[T <: Up, F <: Up, Up] = F
}

object Bool {

  /**
   * Conjunction
   */
  type &&[A <: Bool, B <: Bool] = A#If[B, False, Bool]

  /**
   * Disjunction
   */
  type ||[A <: Bool, B <: Bool] = A#If[True, B, Bool]

  /**
   * Negation
   */
  type Not[A <: Bool] = A#If[False, True, Bool]

  /**
   * Exclusive OR
   */
  type Xor[A <: Bool, B <: Bool] = A#If[Not[B], B, Bool]

  /**
   * Material Implication
   */
  type ->>[A <: Bool, B <: Bool] = A#If[B, True, Bool]

  /**
   * Equivalence
   */
  type Eqv[A <: Bool, B <: Bool] = A#If[B, Not[B], Bool]

  /**
   * Method to convert a typelevel boolean to its value representation
   */
  def toBoolean[B <: Bool](implicit ev: BoolRep[B]) = ev.v
}

/**
 * Provides a value for a type level boolean
 *
 * @author Harshad Deo
 * @since 0.1
 */
sealed class BoolRep[+B <: Bool](val v: Boolean)

object BoolRep {
  implicit object TrueRep extends BoolRep[True](true)
  implicit object FalseRep extends BoolRep[False](false)
}

