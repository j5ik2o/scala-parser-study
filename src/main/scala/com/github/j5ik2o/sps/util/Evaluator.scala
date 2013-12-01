/*
 * Copyright 2010 @ashigeru.
 * Copyright 2013 @j5ik2o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.github.j5ik2o.sps.util

import com.github.j5ik2o.sps.model._
import com.github.j5ik2o.sps.model.SubExpr
import com.github.j5ik2o.sps.model.AddExpr
import com.github.j5ik2o.sps.model.ValueExpr

case class Evaluator() extends ExpressionVisitor {

  def visit(expression: Expression): Any = expression match {
    case AddExpr(l, r) =>
      (visit(l), visit(r)) match {
        case (l: BigDecimal, r: BigDecimal) =>
          l + r
      }
    case SubExpr(l, r) =>
      (visit(l), visit(r)) match {
        case (l: BigDecimal, r: BigDecimal) =>
          l - r
      }
    case PlusExpr(e) =>
      visit(e) match {
        case e: BigDecimal =>
          e * +1
      }
    case MinusExpr(e) =>
      visit(e) match {
        case e: BigDecimal =>
          e * -1
      }
    case MultiExpr(l, r) =>
      (visit(l), visit(r)) match {
        case (l: BigDecimal, r: BigDecimal) =>
          l * r
      }
    case DivExpr(l, r) =>
      (visit(l), visit(r)) match {
        case (l: BigDecimal, r: BigDecimal) =>
         l / r
      }
    case ParenthesizedExpr(e) =>
      visit(e)
    case ValueExpr(v) => v
  }
}
