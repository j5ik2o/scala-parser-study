package com.github.j5ik2o.parser

import com.github.j5ik2o.util._
import java.io.{StringReader, Reader}
import com.github.j5ik2o.parser.model.{SubExpr, AddExpr, ValueExpr, Expression}
import com.github.j5ik2o.util.ParseException

class Q0Parser(reader: Reader) {

  private val scanner = new TokenScanner(new CharacterSource(reader))

  def parse(): Expression = ???

  private def Eof(): Unit = {
    if (scanner.get().kind != TokenKind.EOF) {
      throw ParseException("EOF = " + scanner.get())
    }
  }

  private def consume(kind: TokenKind.Value): Boolean = {
    if (scanner.get().kind == kind) {
      scanner.consume
      true
    } else
      false
  }

}
