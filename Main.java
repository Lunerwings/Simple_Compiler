import java.util.*;
import java.util.stream.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var tokenizer = new Tokenizer();
        var scanner = new Scanner(System.in);
        System.out.print("Enter the expression: ");
        var input = scanner.nextLine();

        try {
            tokenizer.tokenize(input);
        } catch(Exception e) {
            System.err.println("Exception!!!: " + e.getMessage());
            System.exit(69);
        }
        var tokens = tokenizer.tokens();
        printTokens(tokens);

        var parser = new Parser();
        try {
            var expr = parser.parseExpr(tokens);
            //System.out.println("Placeholder printed value: " + ((expr == null)
            //System.out.println(expr);
            List<String> list = Arrays.stream(expr.toString().split("")).map(String::trim) .toList();
            printParseTree(list);
               // ? "empty"
              //  : expr.toString()));

            System.out.println("Eval: " + ((expr == null) ? "empty" : expr.eval()));
        } catch(Exception e) {
            System.out.println("Exception!!!: " + e.getMessage());
            System.exit(69);
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

    private static void printParseTree(List<String> sortedTokens2){
        List<String> sortedTokens = new ArrayList<>();
        for(int i = 0; i < sortedTokens2.size(); ++i) {
            sortedTokens.add(String.valueOf(sortedTokens2.get(i)));
        }
        sortedTokens.removeIf(token -> (token.equals("(")));
        sortedTokens.removeIf(token -> (token.equals(")")));
        System.out.println(sortedTokens);
        sortedTokens = sortedTokens.stream().filter(s -> s != null &&
                !s.trim().isEmpty()).collect(Collectors.toList());
        System.out.println(sortedTokens);
        for(int i = 0; i < 2*sortedTokens.size()/2-2; ++i) {
            System.out.print(" ");
        }
        System.out.println(sortedTokens.get(0));
        for(int i = 1; i < (2*sortedTokens.size()/2+1)/2; ++i) {
            for(int j = 0; j < (2*sortedTokens.size()/2-1)-2*i; ++j) {
                System.out.print(" ");
            }
            System.out.println("/ \\");
            for(int j = 0; j < (2*sortedTokens.size()/2-2)-i*2; ++j) {
                System.out.print(" ");
            }
            System.out.print(sortedTokens.get(i));
            for(int j = 0; j < 3; ++j) {
                System.out.print(" ");
            }
            System.out.println(sortedTokens.get(sortedTokens.size()-i));
        }

    //(3 + 2) * 5 - 1
    }
}

