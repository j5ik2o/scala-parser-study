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

import java.io.Reader
import com.github.j5ik2o.sps.model._
import com.github.j5ik2o.sps.util.TokenKind
import com.github.j5ik2o.sps.util.ParseException
import com.github.j5ik2o.sps.model.SubExpr
import com.github.j5ik2o.sps.model.AddExpr
import com.github.j5ik2o.sps.model.ValueExpr


class Q3Parser(reader: Reader) extends Parser {

  protected val scanner = createScanner(reader)

  /**
   * この構文解析器に渡された入力を解析し、解析結果のモデルオブジェクトを返す。
   *
   * 次の[[com.github.j5ik2o.sps.model.Expression]]をゴール記号とする構文を解析する。
   * {{{
   * Expression ::= Value$a '+' Value$b ; new AddExpr(a, b)
   *             |  Value$a '-' Value$b ; new SubExpr(a, b)
   *             |  Value$a '*' Value$b ; new MultiExpr(a, b)
   *             |  Value$a '/' Value$b ; new DivExpr(a, b)
   *             |  Value$a             ; a
   *      Value ::= NUMBER$t            ; new ValueExpr(BigDecimal(t.image))
   * }}}
   *
   * ただし、それぞれの構文アクションに出現する型名は
   * modelパッケージ下に宣言されたクラスを利用するものとする。
   *
   * @return 解析結果の式
   * @throws ParseException 構文解析に失敗した場合
   */
  def parse(): Expression = {
    val result = Expression
    Eof()
    result
  }

  /**
   * 左括り出しを行う
   * {{{
   * Expression ::= Value$a Rest(a) ; new AddExpr(a, b)
   * Rest(a)    ::= '+' Value$b     ; new AddExpr(a, b)
   *             |  '-' Value$b     ; new SubExpr(a, b)
   *             |  '*' Value$b     ; new MultiExpr(a, b)
   *             |  '/' Value$b     ; new DivExpr(a, b)
   *             |  empty           ; a
   *      Value ::= NUMBER$t        ; new ValueExpr(BigDecimal(t.image))
   * }}}
   */
  private def Expression: Expression = {
    // Value$a ...
    val a = Value
    Rest(a)
  }

  private def Rest(a: Expression): Expression = {
    // Value$a '+' Value$b ; new Add(a, b)
    if (consume(TokenKind.PLUS)) {
      val b = Value
      AddExpr(a, b)
    }
    // Value$a '-' Value$b ; new Subtract(a, b)
    else if (consume(TokenKind.MINUS)) {
      val b = Value
      SubExpr(a, b)
    }
    // Value$a '*' Value$b ; new Multiply(a, b)
    else if (consume(TokenKind.ASTERISK)) {
      val b = Value
      MultiExpr(a, b)
    }
    // Value$a '/' Value$b ; new Divide(a, b)
    else if (consume(TokenKind.SLASH)) {
      val b = Value
      DivExpr(a, b)
    }
    // Value$a empty ; a
    else if (scanner.get().kind == TokenKind.EOF) {
      a
    }
    // ... otherwise
    else {
      throw ParseException("OP/EOF : " + scanner.get())
    }
  }

  private def Value: Expression = {
    if (scanner.get().kind == TokenKind.NUMBER) {
      val token = scanner.consume
      ValueExpr(BigDecimal(token.image))
    } else {
      throw ParseException("NUMBER : " + scanner.get())
    }
  }

}
