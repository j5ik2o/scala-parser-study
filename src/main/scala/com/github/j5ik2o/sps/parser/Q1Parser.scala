package com.github.j5ik2o.sps.parser

import java.io.Reader
import com.github.j5ik2o.sps.model.Expression

class Q1Parser (reader: Reader) extends Parser {

  protected val scanner = createScanner(reader)

  /**
   * この構文解析器に渡された入力を解析し、解析結果のモデルオブジェクトを返す。
   *
   * 次の[[com.github.j5ik2o.sps.model.Expression]]をゴール記号とする構文を解析する。
   *
   * {{{
   * Expression ::= '+' Expression$a Expression$b ; new AddExpr(a, b)
   *             |  '-' Expression$a Expression$b ; new SubExpr(a, b)
   *             |  Value$v                       ; v
   *      Value ::= NUMBER$t                      ; new ValueExpr(t.image)
   * }}}
   *
   * ただし、それぞれの構文アクションに出現する型名は
   * modelパッケージ下に宣言されたクラスを利用するものとする。
   *
   * @return 解析結果の式
   * @throws ParseException 構文解析に失敗した場合
   */
  def parse(): Expression = ???

}
