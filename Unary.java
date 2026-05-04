// only valid operation is minus
public record Unary(Expression expr) implements Expression {
    public int eval() {
        return -1 * expr.eval();
    }

    public String toString() {
        return "-" + expr.toString();
    }
}
