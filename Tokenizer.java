import java.util.ArrayList;

public class Tokenizer {
    /**
     * @brief Here's our finite automaton's states.
     *
     * Initial state is aptly named INIT
     * Both INIT and NUMBER are valid end states.
     * There's technically a implicit invalid state, but if we hit that state,
     * we throw an exception anyways, so no need to put that state here.
     *
     * For each character:
     * - if currently at INIT:
     *   - if received +, -, *, /, (, or ), add the character into the token
     *   list (with the corresponding token type), and advance the input cursor.
     *   The state is still INIT.
     *   - if received whitespace, advance the input cursor. State is still INIT.
     *   - if in range [0, 9], push the character into a temporary string,
     *   advance the input cursor, and go to NUMBER.
     * - if currently at NUMBER:
     *   - if received [0, 9], push the character to the temporary string.
     *   State is still NUMBER.
     *   - Otherwise, convert the temporary string to a NUMBER token. Does not
     *   advance the input. Change state to INIT.
     *
     * At the end, if the state is still NUMBER, we know the temporary string is
     * not empty, and is a number, and we convert that string into a NUMBER
     * token.
     * Finally, add an EOF token. We find this to be more convenient than
     * checking if the list of tokens is empty while parsing, as it's just
     * another switch case, compared to some separate logic.
     */
    enum TokenState {
        INIT,
        NUMBER,
    }
    /**
     * @brief Here's our finite automaton.
     */
    private TokenState state;
    /**
     * @brief Basically a number builder.
     */
    private StringBuilder currToken;
    private ArrayList<Token> tokens;
    /**
     * @brief As the Token class does keep track of where a token is, we need
     * it tracked here.
     */
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
     * Useful if we ever want to reuse the parser. Currently we don't need it.
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
        // currToken is NOT empty only in NUMBER state.
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
