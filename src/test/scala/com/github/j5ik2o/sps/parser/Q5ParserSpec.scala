package com.github.j5ik2o.sps.parser

import org.specs2.mutable.Specification
import com.github.j5ik2o.sps.model.{ParenthesizedExpr, SubExpr, MinusExpr, ValueExpr}
import java.io.StringReader

class Q5ParserSpec extends Specification with SpecSupport {

  protected def createParser(input: String): Parser =
    new Q5Parser(new StringReader(input))

  "Parse result" should {
    "be the same as 10" in {
      process("(((10)))") must_==(ParenthesizedExpr(ParenthesizedExpr(ParenthesizedExpr(ValueExpr(10)))), 10)
    }
    "be the same as -10" in {
      process("10 + 20 - 30 * 40 / 60") must_==(MinusExpr(ValueExpr(10)), -10)
    }
    "be the same as -10" in {
      process("10 + -(20 - +30) + +-0") must_==(SubExpr(ValueExpr(10), ValueExpr(20)), -10)
    }
  }

}
