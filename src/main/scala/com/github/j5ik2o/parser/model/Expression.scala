package com.github.j5ik2o.parser.model

import com.github.j5ik2o.util.ExpressionVisitor

trait Expression {

  def accept(visitor: ExpressionVisitor): Any = {
    visitor.visit(this)
  }

}
