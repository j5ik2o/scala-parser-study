package com.github.j5ik2o.util

import scala.collection.mutable.ListBuffer


class TokenScanner(source: CharacterSource) {
  private val EOF: Token = new Token(TokenKind.EOF, "")
  private var content: Array[Token] = Array.empty
  private var cursor: Int = -1

  def get(k: Int = 0) = {
    require(k >= 0)
    prepare()
    if (isEof(k)) {
      EOF
    } else {
      content(cursor + k)
    }
  }

  def consume: Token = {
    prepare()
    if (isEof(0)) {
      EOF
    } else {
      val r = content(cursor)
      cursor += 1
      r
    }
  }

  private def prepare(): Unit = {
    if (cursor >= 0) {
      return
    }
    val list = ListBuffer[Token]()
    def loop: Unit = {
      val token = fetch
      if (token.isEmpty) {
        ()
      } else {
        list.append(token.get)
        loop
      }
    }
    loop
    cursor = 0
    content = list.toArray
  }

  private def isIdentifierStart(c: Int) = {
    ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || (c == '_') || (c == '+') || (c == '=')
  }

  private def isDigit(c: Int) = {
    '0' <= c && c <= '9'
  }

  private def isIdentifierPart(c: Int) = {
    isIdentifierStart(c) || isDigit(c)
  }

  val regex = """[0-9]+""".r

  private def getIdentifierKind(image: String) = {
    image match {
      case "if" => TokenKind.IF
      case "else" => TokenKind.ELSE
      case "+" => TokenKind.PLUS
      case "-" => TokenKind.MINUS
      case regex() => TokenKind.NUMBER
    }
 }

  private def consumeWhiteSpace(): Unit = {
    val c = source.get()
    if (!Character.isWhitespace(c)) {
      ()
    } else {
      source.consume()
      consumeWhiteSpace()
    }
  }


  private def fetchIdentifier() = {
    val buf = new StringBuilder()
    val start = source.consume()
    buf.append(start.asInstanceOf[Char])

    def loop(): Unit = {
      val part = source.get()
      if (!isIdentifierPart(part)) {
        ()
      } else {
        buf.append(part.asInstanceOf[Char])
        source.consume()
        loop()
      }

    }

    loop()
    val image = buf.toString()
    val kind = getIdentifierKind(image)
    new Token(kind, image)
  }

  private def fetch: Option[Token] = {
    consumeWhiteSpace()
    val head = source.get()
    if (isIdentifierPart(head)) {
      Some(fetchIdentifier())
    } else if (head == CharacterSource.EOF) {
      None
    } else {
      Some(fetchSymbol(TokenKind.UNKNOWN))
    }
  }

  private def fetchSymbol(kind: TokenKind.Value) = {
    val symbol = source.get()
    source.consume()
    new Token(kind, String.valueOf(symbol.asInstanceOf[Char]))
  }

  private def isEof(k: Int) = {
    cursor + k >= content.length
  }
}
