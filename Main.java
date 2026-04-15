import java.util.*;

public class Main {
    public static void main(String[] args) {
        var tokenizer = new Tokenizer();
        var scanner = new Scanner(System.in);
        System.out.print("Enter the expression: ");
        var input = scanner.nextLine();

        try {
            tokenizer.tokenize(input);
        } catch(Tokenizer.TokenizerException e) {
            System.out.println("TODO: log the exception.");
            System.exit(69);
        }
        var tokens = tokenizer.tokens();
        printTokens(tokens);

        var parser = new Parser();
        try {
            var expr = parser.parseExpr(tokens);
            System.out.println("Placeholder printed value: " + ((expr == null)
                ? "empty"
                : expr.toString()));

            System.out.println("Eval: " + ((expr == null) ? "empty" : expr.eval()));
        } catch(Exception e) {
            System.out.println("Exception!!!: " + e);
            e.printStackTrace();
        }
    }

    private static void printTokens(List<Token> tokens) {
        System.out.print("Tokens: [");
        if(tokens.size() >= 2) {
            System.out.print(tokens.get(0));
        }
        for(int i = 1; i < tokens.size() - 1; ++i) {
            System.out.print(", " + tokens.get(i));
        }
        System.out.println("]");
    }

    private static void printParseTree(String[][] sortedTokens){
        for(int i = 0; i < 2*sortedTokens[0].length-1; ++i) {
            System.out.print(" ");
        }
        System.out.println(sortedTokens[0][0]);
        for(int i = 1; i < (2*sortedTokens[0].length -1)/2; ++i) {
            for(int j = 0; j < 2*sortedTokens[0].length-2*i; ++j) {
                System.out.print(" ");
            }
            System.out.println("/ \\");
            for(int j = 0; j < 2*sortedTokens[0].length-1-i*2; ++j) {
                System.out.print(" ");
            }
            System.out.print(sortedTokens[0][i]);
            for(int j = 0; j < 3; ++j) {
                System.out.print(" ");
            }
            System.out.println(sortedTokens[1][i-1]);
        }
    }
}

