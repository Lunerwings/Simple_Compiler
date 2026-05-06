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
            // var strExpr = expr.toString();
            // printParseTree(strExpr);
            System.out.println("Parsed expression: " + expr);
            System.out.println("Placeholder printed value: " + ((expr == null)
                    ? "empty"
                    : expr.toString()));

            System.out.println("Eval: " + ((expr == null) ? "empty" : expr.eval()));
        } catch(Exception e) {
            System.out.println("Exception!!!: " + e.getMessage());
            // e.printStackTrace();
            // System.exit(69);
        }
    }

    private static void printTokens(List<Token> tokens) {
        System.out.print("Tokens: [");
        if(tokens.size() >= 2) {
            System.out.print(tokens.getFirst());
        }
        for(int i = 1; i < tokens.size() - 1; ++i) {
            System.out.print(", " + tokens.get(i));
        }
        System.out.println("]");
    }

    private static void printParseTree(String expr){
        expr = expr.replaceAll("\\s","");
        int counter = 0;
        int highestCounter = -1;
        ArrayList<String> tokens = new ArrayList<>();
        for(int i = 0; i < expr.length(); ++i) {
            tokens.add(expr.substring(i, i + 1));
        }
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        System.out.println("this is the tokens: " + tokens);
        for(int i = 0; i < tokens.size(); ++i) {
            if(i >= 2 &&i <= tokens.size() - 3) {
                if ((tokens.get(i-1).equals("+")||tokens.get(i-1).equals("-")
                        ||tokens.get(i-1).equals("*")||tokens.get(i-1).equals("/"))
                        &&!(tokens.get(i).equals("*")||tokens.get(i).equals("/")||tokens.get(i).equals("+")||tokens.get(i).equals("-")||
                        tokens.get(i).equals("(")||tokens.get(i).equals(")"))) {
                    counter++;

                    if (counter > highestCounter) {

                        highestCounter = counter;
                        list.add(new ArrayList<>());
                        for(int j = 0; j < Math.pow(2,highestCounter-1); ++j) {
                            list.get(highestCounter-1).add(null);
                        }

                    }
                }
            }
            if(i>1 && (tokens.get(i-2).equals("*")||tokens.get(i-2).equals("/")||tokens.get(i-2).equals("+") ||tokens.get(i-2).equals("-"))&&
                    (tokens.get(i).equals("*")||tokens.get(i).equals("/")||tokens.get(i).equals("+") ||tokens.get(i).equals("-"))
                    &&tokens.get(i-1).equals("(")) {
                counter++;

                if (counter > highestCounter) {

                    highestCounter = counter;
                    list.add(new ArrayList<>());
                    for(int j = 0; j < Math.pow(2,highestCounter-1); ++j) {
                        list.get(highestCounter-1).add(null);
                    }

                }
            }
            if (tokens.get(i).equals(")")) {

                counter--;

            }
            if ((tokens.get(i).equals("*") || tokens.get(i).equals("/")
                    || tokens.get(i).equals("+") || tokens.get(i).equals("-"))) {


                counter++;





                if(counter > highestCounter) {

                    highestCounter = counter;
                    list.add(new ArrayList<>());
                    for(int j = 0; j < Math.pow(2,highestCounter-1); ++j) {
                        list.get(highestCounter-1).add(null);
                    }
                }

            }
            if(i>1&&((tokens.get(i).equals("*")||tokens.get(i).equals("/")||
                    tokens.get(i).equals("+")||tokens.get(i).equals("-"))
                    &&tokens.get(i-1).equals("("))) {
                counter--;
            }


            if(!(tokens.get(i).equals(")")||tokens.get(i).equals("("))) {

                int space = 0;


                if (counter == 1) {


                    list.get(0).set(0, tokens.get(i));


                }else{

                    for(int j = 0; j < list.get(counter-2).size();j++){

                        if(list.get(counter-2).get(j)!=null) {
                            space++;
                        }
                    }


                    if(list.get(counter-1).get(2*(space-1)) == null) {

                        list.get(counter - 1).set(2*(space-1), tokens.get(i));


                    }else{


                        list.get(counter - 1).set(2*(space-1)+1, tokens.get(i));


                    }
                }
            }
        }
        for(int i = 0; i < list.size(); i++){
            //for(int j = 0; j < (2*list.size())-i*2; j++){
            for(int j = 0; j < (int)Math.pow(2,list.size()-i)-1; j++){
                System.out.print(" ");
            }
            for(int j = 0; j < list.get(i).size(); j++){
                if (list.get(i).get(j) != null) {
                    System.out.print(list.get(i).get(j));
                    for(int k = 0; k < (int)Math.pow(2,list.size()-i+1)-1; k++){
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                    for(int k = 0; k < (int)Math.pow(2,list.size()-i+1)-1; k++){
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }


    }
}
//           -
//          / \
//         3
//(3 + 2)- 5*6
//1+(3*6)
//(3*2)*6-4*5+6*7+(4*7)
//(3+2)*(6+7)
//(3*6)+1+(3*6)*7)

//our if statment (!tokens.get(i-1).equals("(")&&!tokens.get(i-1).equals(")"))
//                && (!tokens.get(i+1).equals("(")&&!tokens.get(i+1).equals(")"))
//                && (!tokens.get(i).equals("(")&&!tokens.get(i).equals(")"))