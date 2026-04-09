// TODO: position in the original expression string
public record Token (String value, Token.Type type) {
    public enum Type {
      NUMBER,
      PLUS,
      MINUS,
      MUL,
      DIV,
      LPAREN,
      RPAREN,
      // End-of-file, will be helpful when parsing
      EOF,
    }
    public String toString() {
        return this.value.toString();
    }
}
