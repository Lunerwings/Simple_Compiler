public record Term(Token.Type op, Expression lhs, Expression rhs) 
        implements Expression {
    public int eval() {
        return switch(op) {
            case Token.Type.PLUS -> {
                yield lhs.eval() + rhs.eval();
            }
            case Token.Type.MINUS -> {
                yield lhs.eval() - rhs.eval();
            }
            default -> {
                System.out.println("Term: internal error: parse error?");
                // System.exit(69);
                yield 0;
            }
        };
    }

    // TODO: placeholder
    public String toString() {
        return "(" + switch(this.op) {
            case Token.Type.PLUS -> {
                yield "+";
            }
            case Token.Type.MINUS -> {
                yield "-";
            }
            default -> {
                yield "INVALID";
            }
        } + " " + lhs.toString() + " " + rhs.toString() + ")";
    }
}
