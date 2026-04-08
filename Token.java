public record Token (String value, TokenType type) {
  public String toString() {
    return this.value.toString();
  }
}
