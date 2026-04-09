# Checklist
- [x] `main` prompting for input.
- [x] Working lexical analyzer.
  - Note: the only purpose is to break the input string into tokens. The
  lexical analyzer doesn't care if the input is a valid expression.
  - [ ] We might need to account for negative numbers, say, in `-1 + 2`.
- [x] Working parser, for the valid expressions.
- [ ] Improve error reporting for invalid expressions.
  - [ ] For `Token`s, also save the index of the token in the input string.
  Say, input "334 + 35", the first token has value "334" at index 0, the plus
  "+" at index 4, "35" at index 6.
  - [ ] Report the types of error:
    - [ ] Unexpected token.
    - [ ] Expect token, but hit `EOF`.
- [ ] Evaluator
  - [ ] Current version kind of works, but because of division, we really want
  an `eval` returning `float` or `double` instead.
- [ ] Improve the AST printer. Currently, it's printed in Lisp form. We want
the tree form as shown in the assignment sheet(?).
- [ ] And we're done.
- [ ] Maybe attach an AGPL-3.0-or-later to annoy the professor.
