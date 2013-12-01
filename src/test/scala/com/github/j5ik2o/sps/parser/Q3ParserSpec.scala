package com.github.j5ik2o.sps.parser

import org.specs2.mutable.Specification
import com.github.j5ik2o.sps.model.{SubExpr, MinusExpr, ValueExpr}
import java.io.StringReader

class Q3ParserSpec extends Specification with SpecSupport {

  protected def createParser(input: String): Parser =
    new Q3Parser(new StringReader(input))

  "Parse result" should {
    "be the same as 10" in {
      process("10 + 20") must_==(ValueExpr(10), 10)
    }
    "be the same as -10" in {
      process("10 - 20") must_==(MinusExpr(ValueExpr(10)), -10)
    }
    "be the same as -10" in {
      process("10 / 20") must_==(SubExpr(ValueExpr(10), ValueExpr(20)), -10)
    }
    "be the same as -10" in {
      process("10") must_==(SubExpr(ValueExpr(10), ValueExpr(20)), -10)
    }
  }

}
