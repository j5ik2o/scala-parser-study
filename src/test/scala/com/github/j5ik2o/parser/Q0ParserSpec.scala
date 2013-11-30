package com.github.j5ik2o.parser

import org.specs2.mutable.Specification
import java.io.StringReader
import com.github.j5ik2o.util.Evaluator

class Q0ParserSpec extends Specification {

  def process(input: String) = {
    val parser = new Q0Parser(new StringReader(input))
    val expression = parser.parse()
    val result = expression.accept(new Evaluator)
    println(s"input = $input, ast = $expression, result = $result")
    result
  }

  "Parse result" should {
    "be the same as 30" in {
      process("+ 10 20") must_== 30
    }
    "be the same as 30" in {
      process("- 50 20") must_== 30
    }
  }


}
