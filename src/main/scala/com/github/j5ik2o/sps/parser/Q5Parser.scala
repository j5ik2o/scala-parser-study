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
import com.github.j5ik2o.sps.model.AddExpr
import com.github.j5ik2o.sps.model.ValueExpr
import com.github.j5ik2o.sps.model.ParenthesizedExpr
import scala.annotation.tailrec

class Q5Parser(reader: Reader) extends Parser {

  protected val scanner = createScanner(reader)

  /**
   * この構文解析器に渡された入力を解析し、解析結果のモデルオブジェクトを返す。
   *
   * 次の[[com.github.j5ik2o.sps.model.Expression]]をゴール記号とする構文を解析する。
   *
   * {{{
   *  Expression ::= AddExpr$e                  ; e
   *     AddExpr ::= AddExpr$a '+' MultExpr$b   ; new AddExpr(a, b)
   *              |  AddExpr$a '-' MultExpr$b   ; new SubExpr(a, b)
   *              |  MultExpr$a                 ; a
   *    MultExpr ::= MultExpr$a '*' UnaryExpr$b ; new MultiExpr(a, b)
   *              |  MultExpr$a '/' UnaryExpr$b ; new DivExpr(a, b)
   *              |  UnaryExpr$a                ; a
   *   UnaryExpr ::= '+' UnaryExpr$e            ; new PlusExpr(e)
   *              |  '-' UnaryExpr$e            ; new MinusExpr(e)
   *              |  PrimaryExpr$e              ; e
   * PrimaryExpr ::= '(' Expression$e ')'       ; new ParenthesizedExpr(e)
   *              |  Value$v                    ; v
   *       Value ::= NUMBER$t                   ; new ValueExpr(BigDecimal(t.image))
   * }}}
   *
   * つまり、通常の四則演算、+-の符号、括弧、整数が利用可能な通常の式を解析できる。
   *
   * ただし、それぞれの構文アクションに出現する型名は
   * modelパッケージ下に宣言されたクラスを利用するものとする。
   *
   * @return 解析結果の式
   * @throws ParseException 構文解析に失敗した場合
   */
  def parse(): Expression = {
    val expr = Expression
    Eof()
    expr
  }

  private def Expression: Expression = {
    val expr = AdditiveExpression
    expr
  }

  private def Value: Expression = {
    if (scanner.get().kind == TokenKind.NUMBER) {
      val token = scanner.consume
      ValueExpr(BigDecimal(token.image))
    } else {
      throw ParseException("NUMBER : " + scanner.get())
    }
  }

  private def AdditiveExpression: Expression = {
    // AddExpr$a '+'/'-' MultExpr$b
    // MultExpr$a
    // =>
    // MultExpr$a ( '+'/'-' MultExpr$b ; a = new Add/Subtract(a, b) )* ; a
    var a = MultiplicativeExpression
    @tailrec
    def loop(): Unit = {
      if (consume(TokenKind.PLUS)) {
        val b = MultiplicativeExpression
        a = AddExpr(a, b)
        loop()
      } else if (consume(TokenKind.MINUS)) {
        val b = MultiplicativeExpression
        a = SubExpr(a, b)
        loop()
      } else {
        ()
      }
    }
    loop()
    a
  }

  private def MultiplicativeExpression: Expression = {
    // MultExpr$a '+'/'-' UnaryExpr$b
    // UnaryExpr$a
    // =>
    // UnaryExpr$a ( '*'/'/' UnaryExpr$b ; a = new Multiply/Divide(a, b) )* ; a
    var a = UnaryExpression
    @tailrec
    def loop(): Unit = {
      if (consume(TokenKind.ASTERISK)) {
        val b = UnaryExpression
        a = MultiExpr(a, b)
        loop()
      }
      else if (consume(TokenKind.SLASH)) {
        val b = UnaryExpression
        a = DivExpr(a, b)
        loop()
      }
      else {
        ()
      }
    }
    loop()
    a
  }

  private def UnaryExpression: Expression = {
    // '+' UnaryExpression$e ; new Plus(e)
    if (consume(TokenKind.PLUS)) {
      val operand = UnaryExpression
      PlusExpr(operand)
    }
    // '-' UnaryExpression$e ; new Minus(e)
    else if (consume(TokenKind.MINUS)) {
      val operand = UnaryExpression
      MinusExpr(operand)
    }
    // PrimaryExpression$e ; e
    else {
      PrimaryExpression
    }
  }

  private def PrimaryExpression: Expression = {
    // '(' Expression$e ')' ; new Parenthesized(e)
    if (consume(TokenKind.OPEN_PAREN)) {
      val v = Expression
      if (!consume(TokenKind.CLOSE_PAREN)) {
        throw ParseException(scanner.get().toString)
      }
      ParenthesizedExpr(v)
    }
    // Value$v ; v
    else {
      val v = Value
      v
    }
  }

}
