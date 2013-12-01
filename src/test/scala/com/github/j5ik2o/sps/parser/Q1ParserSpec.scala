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
import com.github.j5ik2o.sps.model.{ValueExpr, SubExpr, AddExpr}
import java.io.StringReader

class Q1ParserSpec extends Specification with SpecSupport {

  sequential

  protected def createParser(input: String): Parser =
    new Q1Parser(new StringReader(input))

  "Parse result" should {
    "be correct" in {
      process("10") must_==(ValueExpr(10), 10)
      process("+ 10 - 20 30") must_==(AddExpr(ValueExpr(10), SubExpr(ValueExpr(20), ValueExpr(30))), 0)
      process("+ + + 10 - 20 30 40 50") must_==(AddExpr(AddExpr(AddExpr(ValueExpr(10), SubExpr(ValueExpr(20), ValueExpr(30))), ValueExpr(40)), ValueExpr(50)), 90)
    }
  }

}