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

        System.out.print("[");
        if(tokens.size() >= 2) {
            System.out.print(tokens.get(0));
        }
        for(int i = 1; i < tokens.size() - 1; ++i) {
            System.out.print(", " + tokens.get(i));
        }
        System.out.println("]");
    }
}

