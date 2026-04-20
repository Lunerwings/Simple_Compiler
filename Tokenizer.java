import java.util.ArrayList;

public class Tokenizer {
    /**
     * @brief Here's our finite automaton's states.
     */
    enum TokenState {
        INIT,
        NUMBER,
    }
    /**
     * @brief Here's our finite automaton.
     */
    private TokenState state;
    private StringBuilder currToken;
    private ArrayList<Token> tokens;
    private int currTokenIdx;

    public Tokenizer() {
        this.state = TokenState.INIT;
        this.currToken = new StringBuilder();
        this.tokens = new ArrayList<Token>();
        this.currTokenIdx = 0;
    }

    public ArrayList<Token> tokens() {
        return this.tokens;
    }

    public void tokenize(String s) throws Exception {
        // for(var c : s.toCharArray()) {
        for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch(this.state) {
                case INIT -> {
                    this.tokenizeInit(c, i);
                }
                case NUMBER -> {
                    this.tokenizeNumber(c, i);
                }
            }
        }

        if(!this.currToken.isEmpty()) {
            assert(this.state == TokenState.NUMBER);
            this.tokens.add(new Token(this.currToken.toString(),
                  Token.Type.NUMBER, this.currTokenIdx));
            this.currToken = new StringBuilder();
        }
        this.tokens.add(new Token("", Token.Type.EOF, s.length()));
    }

    /**
     * @brief Reset the tokenizer state and empty its token list.
     */
    public void clear() {
        // basically copy-paste from the constructor.
        this.state = TokenState.INIT;
        this.currToken = new StringBuilder();
        this.tokens = new ArrayList<Token>();
        this.currTokenIdx = 0;
    }

    private void tokenizeInit(char c, int i) throws Exception {
        assert(this.state == TokenState.INIT);
        assert(this.currToken.isEmpty());

        if (Character.isWhitespace(c)) {
            // skip whitespace
            return;
        }
        if (c >= '0' && c <= '9') {
            this.currToken.append(c);
            this.currTokenIdx = i;
            this.state = TokenState.NUMBER;
            return;
        }
        // from here on, c is a sign or parenthesis
        var tokType = switch(c) {
            case '(' -> Token.Type.LPAREN;
            case ')' -> Token.Type.RPAREN;
            case '+' -> Token.Type.PLUS;
            case '-' -> Token.Type.MINUS;
            case '*' -> Token.Type.MUL;
            case '/' -> Token.Type.DIV;
            default -> throw new Exception("Unexpected token " + c
                    + " at index " + i);
            // TODO: otherwise, throw an exception
        };

        this.tokens.add(new Token(Character.toString(c), tokType, i));
    }

    private void tokenizeNumber(char c, int i) throws Exception {
        assert(this.state == TokenState.NUMBER);
        // it's expected that at least the first digit is present
        assert(!this.currToken.isEmpty());
        if (c < '0' || c > '9') {
            this.state = TokenState.INIT;
            this.tokens.add(new Token(this.currToken.toString(),
                        Token.Type.NUMBER, this.currTokenIdx));
            this.currToken = new StringBuilder();
            assert(this.currToken.isEmpty());
            this.currTokenIdx = i;
            this.tokenizeInit(c, i);
            return;
        }

        // we're sure it's only from '1' to '9' here
        assert(c >= '0' && c <= '9');
        this.currToken.append(c);
    }
}
