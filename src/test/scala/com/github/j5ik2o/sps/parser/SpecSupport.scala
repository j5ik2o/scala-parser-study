package com.github.j5ik2o.sps.parser

import com.github.j5ik2o.sps.util.Evaluator

trait SpecSupport {

  protected def createParser(input: String): Parser

  protected def process(input: String) = {
    val expression = createParser(input).parse()
    val result = expression.accept(Evaluator())
    println(s"input = $input, ast = $expression, result = $result")
    (expression, result)
  }

}
