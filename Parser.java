import java.util.*;

// We do both parsing and syntax tree building in one step, called "parsing".
// This equivalent grammar is how the parser runs:
// E -> T ([+ | -] T)*
// T -> F ([* | /] F)*
// F -> (E) | number

public class Parser {
    public Parser() {
        this.currIdx = 0;
    }

    public void reset() {
        this.currIdx = 0;
    }

    public Expression parseExpr(List<Token> tokens) throws ParserException {
        var lhs = parseTerm(tokens);

        var nextOp = tokens.get(this.currIdx).type();
        while(nextOp == Token.Type.PLUS || nextOp == Token.Type.MINUS) {
            this.currIdx += 1;
            var rhs = parseTerm(tokens);
            if(rhs == null) {
                throw new ParserException("Expected expression, got EOF");
            }
            lhs = new Term(nextOp, lhs, rhs);
            nextOp = tokens.get(this.currIdx).type();
        }

        return lhs;
    }

    public Expression parseTerm(List<Token> tokens) throws ParserException {
        var lhs = parseFactor(tokens);

        var nextOp = tokens.get(this.currIdx).type();
        while(nextOp == Token.Type.MUL || nextOp == Token.Type.DIV) {
            this.currIdx += 1;
            var rhs = parseFactor(tokens);
            if(rhs == null) {
                throw new ParserException("Expected expression, got EOF");
            }
            lhs = new Factor(nextOp, lhs, rhs);
            nextOp = tokens.get(this.currIdx).type();
        }

        return lhs;
    }
    private Expression parseFactor(List<Token> tokens) throws ParserException {
        assert(!tokens.isEmpty());
        var firstTok = tokens.get(this.currIdx);

        return switch(firstTok.type()) {
            case Token.Type.NUMBER -> {
                try {
                    this.currIdx += 1;
                    yield new Primary(Integer.parseInt(firstTok.value()));
                } catch(NumberFormatException e) {
                    throw new ParserException(e.toString());
                }
            }
            case Token.Type.LPAREN -> {
                this.currIdx += 1;
                var ret = this.parseExpr(tokens);
                if (tokens.size() <= this.currIdx ||
                    tokens.get(this.currIdx).type() != Token.Type.RPAREN) {
                    throw new ParserException("Expect ')', got "
                            + tokens.get(this.currIdx).value());
                }
                this.currIdx += 1;
                yield ret;
            }
            case Token.Type.EOF -> {
                yield null;
            }
            default -> {
                throw new ParserException("Expect number or '(', got "
                        + firstTok.value());
            }
        };
    }

    private int currIdx;
}
