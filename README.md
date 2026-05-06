# Simple_Compiler
> Calculate the value of an integer expression.

## Features
- Evaluate an expression, and will evaluate correctly provided no floating
number is needed to represent the results, or immediate results.
- Tree-dumping in Lisp-styled expressions. It's somewhat easy to read, given
familiarity in reading Lisp expressions.
  - It's *very* easy for another program to parse this format to get the exact
  same tree. No operator precedence needed.

## How to build and run
```fish
# build
javac Main.java
# run
java Main
```

## Some inputs that work
- The tree printer might behave oddly with certain input, so when an input
errors,  it's best to comment out `printParseTree` in line 23 then retry.
  - A list of errornerous input is listed
  [below](#unless-printparsetree-commented-out)

```c
1 + 1
1 + 2 + 3 + 4 + 5 + 6
1 + 2 * 3 + 4 * 5 / 2
1 - ----------2
1 + -(+(+(2)))
(((((69))))) + 1
```

### Outputs of the examples above
```txt
Tokens: [1, +, 1]
Placeholder printed value: (+ 1 1)
Eval: 2
```

```txt
Tokens: [1, +, 2, +, 3, +, 4, +, 5, +, 6]
Placeholder printed value: (+ (+ (+ (+ (+ 1 2) 3) 4) 5) 6)
Eval: 21
```

```txt
Tokens: [1, +, 2, *, 3, +, 4, *, 5, /, 2]
Placeholder printed value: (+ (+ 1 (* 2 3)) (/ (* 4 5) 2))
Eval: 17
```

```txt
Tokens: [1, -, -, -, -, -, -, -, -, -, -, -, 2]
Placeholder printed value: (- 1 (negate (negate (negate (negate (negate (negate (negate (negate (negate (negate 2)))))))))))
Eval: -1
```

```txt
Enter the expression: 1 + -(+(+(2)))   
Tokens: [1, +, -, (, +, (, +, (, 2, ), ), )]
Placeholder printed value: (+ 1 (negate 2))
Eval: -1
```

```txt
Tokens: [(, (, (, (, (, 69, ), ), ), ), ), +, 1]
Placeholder printed value: (+ 69 1)
Eval: 70
```

## Some inputs that won't work
```c
-1 -
1 +
1 + (2 + 3
99999999999999999999 + 1
```

### Unless printParseTree commented out
```c
-1
-1 + 2
1
23 + 34 // or any input with more than 1 digit per number
// empty input
```

## Quirks
- Since this is an integer evaluator, floating numbers can't be represented.
For example, `1 / 2 * 2` is zero, while `1 * 2 / 2` is one.
- We don't use `BigInteger` or `BigDecimal`, so the result and any immediate
values are subject to `int` bounds. Inputting a number too large probably will
hit a `NumberFormatException`.
- We print unary negation (e.g. `-3`) as `(negate <expr>)` (for our example,
`(negate 3)`). It's more eye clutter, but it's distinguishable from `-`, which
is already used in subtraction.
