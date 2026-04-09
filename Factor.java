public record Factor(Token.Type op, Expression lhs, Expression rhs) 
        implements Expression {
    public int eval() {
        return switch(op) {
            case Token.Type.MUL -> {
                yield lhs.eval() * rhs.eval();
            }
            case Token.Type.DIV -> {
                yield lhs.eval() / rhs.eval();
            }
            default -> {
                System.out.println("Factor: internal error: parse error?");
                // System.exit(69);
                yield 0;
            }
        };
    }

    public String toString() {
        return "(" + switch(this.op) {
            case Token.Type.MUL -> {
                yield "*";
            }
            case Token.Type.DIV -> {
                yield "-";
            }
            default -> {
                yield "INVALID";
            }
        } + " " + lhs.toString() + " " + rhs.toString() + ")";
    }
}
