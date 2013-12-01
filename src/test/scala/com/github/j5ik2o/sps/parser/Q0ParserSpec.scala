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

class Q0ParserSpec extends Specification with SpecSupport {

  sequential

  protected def createParser(input: String): Parser =
    new Q0Parser(new StringReader(input))

  "Parse result" should {
    "be correct" in {
      process("+ 10 20") must_==(AddExpr(ValueExpr(10), ValueExpr(20)), 30)
      process("- 50 20") must_==(SubExpr(ValueExpr(50), ValueExpr(20)), 30)
    }
  }

}
