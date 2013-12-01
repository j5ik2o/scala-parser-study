package com.github.j5ik2o.sps.parser

import org.specs2.mutable.Specification
import com.github.j5ik2o.sps.model.{ValueExpr, SubExpr, AddExpr}
import java.io.StringReader

class Q1ParserSpec extends Specification with SpecSupport {

  protected def createParser(input: String): Parser =
    new Q1Parser(new StringReader(input))

  "Parse result" should {
    "be the same as 10" in {
      process("10") must_==(ValueExpr(10), 10)
    }
    "be the same as 0" in {
      process("+ 10 - 20 30") must_==(AddExpr(ValueExpr(10), SubExpr(ValueExpr(20), ValueExpr(30))), 0)
    }
    "be the same as 90" in {
      process("+ + + 10 - 20 30 40 50") must_==(AddExpr(AddExpr(AddExpr(ValueExpr(10), SubExpr(ValueExpr(20), ValueExpr(30))), ValueExpr(40)), ValueExpr(50)), 90)
    }
  }

}