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
```c
1 + 1
1 + 2 + 3 + 4 + 5 + 6
1 + 2 * 3 + 4 * 5 / 2
1 - ----------2
1 + -(+(+(2)))
(((((69))))) + 1
```

## Quirks
- Since this is an integer evaluator, floating numbers can't be represented.
For example, `1 / 2 * 2` is zero, while `1 * 2 / 2` is one.
