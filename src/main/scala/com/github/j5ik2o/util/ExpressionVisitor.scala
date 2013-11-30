package com.github.j5ik2o.util

import com.github.j5ik2o.parser.model.Expression

trait ExpressionVisitor {

  def visit(expression: Expression): Any

}
