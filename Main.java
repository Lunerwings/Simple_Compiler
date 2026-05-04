import java.util.*;

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
            var strExpr = expr.toString();
            // doesn't handle unary expressions
            // printParseTree(strExpr);
            // System.out.println("Parsed expression: " + expr);
            System.out.println("Placeholder printed value: " + ((expr == null)
                    ? "empty"
                    : expr.toString()));

            System.out.println("Eval: " + ((expr == null) ? "empty" : expr.eval()));
        } catch(Exception e) {
            // throw e;
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

    private static void printParseTree(String expr){
        expr = expr.replaceAll("\\s","");
        int counter = -1;
        int highestCounter = -1;
        ArrayList<String> tokens = new ArrayList<>();
        for(int i = 0; i < expr.length(); ++i) {
            tokens.add(expr.substring(i, i + 1));
        }
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        list.add(new ArrayList<String>());
        System.out.println("this is the tokens: " + tokens);
        for(int i = 0; i < tokens.size(); ++i) {

            if (tokens.get(i).equals("(") && (i==0||!tokens.get(i - 1).equals(")"))) {
                counter ++;
                if (counter > highestCounter) {
                    highestCounter = counter;
                    list.add(new ArrayList<String>());
                }
            } else if (tokens.get(i).equals(")")) {
                counter--;
            } else if (tokens.get(i).equals("*") || tokens.get(i).equals("/")
                    || tokens.get(i).equals("+") || tokens.get(i).equals("-")) {
                list.get(counter).add(tokens.get(i));
                if(!tokens.get(i+1).equals("("))
                    counter++;
                if(counter > highestCounter) {
                    highestCounter = counter;
                    list.add(new ArrayList<String>());
                }
            }else{
                for(int j = 0; j < 10;j++){
                    if(String.valueOf(j).equals(tokens.get(i))
                            &&!String.valueOf(j+3).equals(tokens.get(i))){
                        list.get(counter).add(tokens.get(i));
                    };
                }
            }
        }
        System.out.println(list+"\n\n\n\n\n\n\n\n");
        for(int i = 0; i < list.size(); ++i) {
            for(int j = 0; j < list.get(i).size(); ++j) {
                System.out.print(list.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}

//(3 + 2)- 5*6
//(3*2)*6-4*5+6*7+(4*7)
//(3+2)*(6+7)
