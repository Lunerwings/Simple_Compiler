import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] tokens;
        String[][] nums;
        Tokenizer fromString = new Tokenizer();
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the expression : ");
        String starterTokens = scan.nextLine();
        tokens = fromString.intoArray(starterTokens);
        System.out.println(Arrays.toString(tokens));
    }
}

