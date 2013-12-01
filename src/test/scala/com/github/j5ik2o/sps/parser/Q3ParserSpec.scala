package com.github.j5ik2o.sps.parser

import org.specs2.mutable.Specification
import com.github.j5ik2o.sps.model._
import java.io.StringReader
import com.github.j5ik2o.sps.model.SubExpr
import com.github.j5ik2o.sps.model.AddExpr
import com.github.j5ik2o.sps.model.ValueExpr

class Q3ParserSpec extends Specification with SpecSupport {

  protected def createParser(input: String): Parser =
    new Q3Parser(new StringReader(input))

  "Parse result" should {
    "be the same as 10" in {
      process("10 + 20") must_==(AddExpr(ValueExpr(10),ValueExpr(20)),30)
    }
    "be the same as -10" in {
      process("10 - 20") must_==(SubExpr(ValueExpr(10),ValueExpr(20)),-10)
    }
    "be the same as -10" in {
      process("10 / 20") must_==(DivExpr(ValueExpr(10),ValueExpr(20)),0.5)
    }
    "be the same as -10" in {
      process("10") must_==(ValueExpr(10),10)
    }
  }

}

