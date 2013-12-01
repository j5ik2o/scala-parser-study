# scala-parser-study

この成果物は、@ashigeru さん主催のパーサー勉強会の資料をベースにScala版として再作成したものです。

## Q0Parser

[Q0Parser](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/main/scala/com/github/j5ik2o/sps/parser/Q0Parser.scala)クラス内のparse()メソッドの内容を、同メソッドのscaladocに説明される動作と同等の動作を行うようにせよ。
また、単体テスト[Q0ParserSpec](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/test/scala/com/github/j5ik2o/sps/parser/Q0ParserSpec.scala)が全て成功することを確認すること。
なお、Q0Parserは次のような構文規則と構文アクションを持ち、Expressionから開始する言語である。

```
Expression	::=	Value$a '+' Value$b        	    ; new AddExpr(a, b)
Value	    ::=	NUMBER$t                        ; new ValueExpr(BigDecimal(t.image))
```

## Q1Parser

[Q1Parser](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/main/scala/com/github/j5ik2o/sps/parser/Q1Parser.scala)クラス内のparse()メソッドの内容を、同メソッドのscaladocに説明される動作と同等の動作を行うようにせよ。
また、単体テスト[Q1ParserSpec](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/test/scala/com/github/j5ik2o/sps/parser/Q1ParserSpec.scala)が全て成功することを確認すること。
なお、Q1Parserは次のような構文規則と構文アクションを持ち、Expressionから開始する言語である。

```
Expression	::=	'+' Expression$a Expression$b  ; new AddExpr(a, b)
	          | '-' Expression$a Expression$b  ; new SubExpr(a, b)
              | Value$v                        ; v
Value       ::= NUMBER$t                       ; new ValueExpr(BigDecimal(t.image))
```

## Q2Parser

[Q2Parser](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/main/scala/com/github/j5ik2o/sps/parser/Q2Parser.scala)クラス内のparse()メソッドの内容を、同メソッドのscaladocに説明される動作と同等の動作を行うようにせよ。
また、単体テスト[Q2ParserSpec](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/test/scala/com/github/j5ik2o/sps/parser/Q2ParserSpec.scala)が全て成功することを確認すること。
なお、Q2Parserは次のような構文規則と構文アクションを持ち、Expressionから開始する言語である。

```
Expression	::=	'-' Value$a         ; new MinusExpr(a)
	          |	'-' Value$a Value$b	; new SubExpr(a, b)
              |	Value$v             ; v
Value       ::=	NUMBER$t            ; new ValueExpr(BigDecimal(t.image))
```

## Q3Parser

[Q3Parser](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/main/scala/com/github/j5ik2o/sps/parser/Q3Parser.scala)クラス内のparse()メソッドの内容を、同メソッドのscaladocに説明される動作と同等の動作を行うようにせよ。
また、単体テスト[Q3ParserSpec](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/test/scala/com/github/j5ik2o/sps/parser/Q3ParserSpec.scala)が全て成功することを確認すること。
なお、Q3Parserは次のような構文規則と構文アクションを持ち、Expressionから開始する言語である。

```
Expression	::=	Value$a '+' Value$b     ; new AddExpr(a, b)
              |	Value$a '-' Value$b     ; new SubExpr(a, b)
              |	Value$a '*' Value$b     ; new MultiExpr(a, b)
              |	Value$a '/' Value$b     ; new DivExpr(a, b)
              |	Value$v	; v
Value       ::=	NUMBER$t                ; new ValueExpr(BigDecimal(t.image))
```

### Q4Parser

[Q4Parser](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/main/scala/com/github/j5ik2o/sps/parser/Q4Parser.scala)クラス内のparse()メソッドの内容を、同メソッドのscaladocに説明される動作と同等の動作を行うようにせよ。
また、単体テスト[Q4ParserSpec](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/test/scala/com/github/j5ik2o/sps/parser/Q4ParserSpec.scala)が全て成功することを確認すること。
なお、Q4Parserは次のような構文規則と構文アクションを持ち、Expressionから開始する言語である。

```
Expression  ::=	AddExpr$e               ; e
AddExpr     ::=	AddExpr$a '+' Value$b	; new AddExpr(a, b)
              |	Value$a                 ; a
Value       ::=	NUMBER$t                ; new ValueExpr(BigDecimal(t.image))
```

## Q5Parser

[Q5Parser](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/main/scala/com/github/j5ik2o/sps/parser/Q5Parser.scala)クラス内のparse()メソッドの内容を、同メソッドのscaladocに説明される動作と同等の動作を行うようにせよ。
また、単体テスト[Q5ParserSpec](https://github.com/j5ik2o/scala-parser-study/blob/develop/src/test/scala/com/github/j5ik2o/sps/parser/Q5ParserSpec.scala)が全て成功することを確認すること。
なお、Q5Parserは次のような構文規則と構文アクションを持ち、Expressionから開始する言語である。

```
Expression	::=	AddExpr$e                   ; e
AddExpr     ::=	AddExpr$a '+' MultExpr$b	; new AddExpr(a, b)
              |	AddExpr$a '-' MultExpr$b	; new SubExpr(a, b)
              |	MultExpr$a                  ; a
MultExpr	::=	MultExpr$a '*' UnaryExpr$b	; new MultiExpr(a, b)
              |	MultExpr$a '/' UnaryExpr$b	; new DivExpr(a, b)
              |	UnaryExpr$a                 ; a
UnaryExpr	::=	'+' UnaryExpr$e             ; new PlusExpr(e)
              |	'-' UnaryExpr$e             ; new MinusExpr(e)
              |	PrimaryExpr$e               ; e
PrimaryExpr	::=	'(' Expression$e ')'        ; new ParenthesizedExpr(e)
              |	Value$v                     ; v
Value       ::=	NUMBER$t                    ; new ValueExpr(BigDecimal(t.image))
```
