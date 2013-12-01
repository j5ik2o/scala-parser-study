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
package com.github.j5ik2o.sps.util

import java.io.Reader

object CharacterSource {
  val EOF = -1
}

case class CharacterSource(reader: Reader) {

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
