package com.github.j5ik2o.sps.parser

import org.specs2.mutable.Specification
import com.github.j5ik2o.sps.model.{ValueExpr, SubExpr, AddExpr}
import java.io.StringReader

class Q0ParserSpec extends Specification with SpecSupport {

  protected def createParser(input: String): Parser =
    new Q0Parser(new StringReader(input))

  "Parse result" should {
    "be the same as 30" in {
      process("+ 10 20") must_==(AddExpr(ValueExpr(10), ValueExpr(20)), 30)
    }
    "be the same as 30" in {
      process("- 50 20") must_==(SubExpr(ValueExpr(50), ValueExpr(20)), 30)
    }
  }

}
