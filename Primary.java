public record Primary(int num) 
        implements Expression {
    public int eval() {
        return this.num;
    }

    public String toString() {
        return "" + this.num;
    }
}
