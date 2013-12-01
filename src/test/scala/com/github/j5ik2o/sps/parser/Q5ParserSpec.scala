/*
 * Copyright 2013 @j5ik2o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.github.j5ik2o.sps.parser

import org.specs2.mutable.Specification
import com.github.j5ik2o.sps.model.{ParenthesizedExpr, SubExpr, MinusExpr, ValueExpr}
import java.io.StringReader

class Q5ParserSpec extends Specification with SpecSupport {
  sequential
  protected def createParser(input: String): Parser =
    new Q5Parser(new StringReader(input))

  "Parse result" should {
    "be the same as 10" in {
      process("(((10)))") must_==(ParenthesizedExpr(ParenthesizedExpr(ParenthesizedExpr(ValueExpr(10)))), 10)
    }
//    "be the same as -10" in {
//      process("10 + 20 - 30 * 40 / 60") must_==(MinusExpr(ValueExpr(10)), -10)
//    }
//    "be the same as -10" in {
//      process("10 + -(20 - +30) + +-0") must_==(SubExpr(ValueExpr(10), ValueExpr(20)), -10)
//    }
  }

}
