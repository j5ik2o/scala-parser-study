package com.github.j5ik2o.util

import java.io.Reader

object CharacterSource {
  val EOF = -1
}

class CharacterSource(reader: Reader) {

  private var cursor = 0

  private val content = drain(reader)

  def get(size: Int = 0): Int = {
    require(size >= 0)
    if (isEof(size)) {
      CharacterSource.EOF
    } else {
      content(cursor + size)
    }
  }

  def consume(): Int = {
    if (isEof(0)) {
      CharacterSource.EOF
    } else {
      val r = content(cursor)
      cursor += 1
      r
    }
  }

  private def drain(reader: Reader) = {
    val out = new StringBuffer()
    def read = {
      val buf = new Array[Char](256)
      val read = reader.read(buf)
      if (read == -1) {
        ()
      } else {
        out.append(buf, 0, read)
        read
      }
    }
    read
    val r = new Array[Char](out.length())
    out.getChars(0, out.length(), r, 0)
    r
  }

  private def isEof(k: Int) = {
    cursor + k >= content.length
  }

}
