package com.github.j5ik2o.parser

import com.github.j5ik2o.util.{ParseException, TokenKind, CharacterSource, TokenScanner}
import java.io.{StringReader, Reader}
import com.github.j5ik2o.parser.model.{SubExpr, AddExpr, ValueExpr, Expression}

object Q0Parser extends App {

  def process(input: String) {
    val parser = new Q0Parser(new StringReader(input))
    val expression = parser.parse
    println(expression)
  }

  process("+ 10 20")
  process("+ 20 30")
}

class Q0Parser(reader: Reader) {

  val scanner = new TokenScanner(new CharacterSource(reader))

  def parse(): Expression = ???

  private def Eof = {
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
