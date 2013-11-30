package com.github.j5ik2o.util

import com.github.j5ik2o.parser.model.{SubExpr, ValueExpr, AddExpr, Expression}

class Evaluator extends ExpressionVisitor {

  def visit(expression: Expression): Any = expression match {
    case AddExpr(l, r) =>
      (visit(l), visit(r)) match {
        case (l: String, r: String) =>
          l.toInt + r.toInt
      }
    case SubExpr(l, r) =>
      (visit(l), visit(r)) match {
        case (l: String, r: String) =>
          l.toInt - r.toInt
      }
    case ValueExpr(v) => v
  }
}
