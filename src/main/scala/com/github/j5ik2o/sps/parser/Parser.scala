/*
 * Copyright 2010 @ashigeru.
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

import com.github.j5ik2o.sps.util.{CharacterSource, TokenScanner, ParseException, TokenKind}
import java.io.Reader
import com.github.j5ik2o.sps.model.Expression

trait Parser {

  protected def createScanner(reader: Reader) = TokenScanner(CharacterSource(reader))

  protected val scanner: TokenScanner

  protected def Eof(): Unit = {
    if (scanner.get().kind != TokenKind.EOF) {
      throw ParseException("EOF = " + scanner.get())
    }
  }

  protected def consume(kind: TokenKind.Value): Boolean = {
    if (scanner.get().kind == kind) {
      scanner.consume
      true
    } else
      false
  }

  def parse() : Expression

}
