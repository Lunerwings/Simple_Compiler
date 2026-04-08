public class Tokenizer {
    private String[] finalTokens;
    private String[] finalTokensClean;
    private int counter = 0;
    public Tokenizer(){}
    public String[] intoArray(String a){
        a = a.replace(" ","");
        finalTokens = new String[a.length()];
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)=='{'||a.charAt(i)=='['||a.charAt(i)=='(') {
                finalTokens[i]=a.charAt(i)+"";
            }else if (a.charAt(i)=='}'||a.charAt(i)==']'||a.charAt(i)==')') {
                finalTokens[i]=a.charAt(i)+"";
            }else if (a.charAt(i)=='+'||a.charAt(i)=='-'||a.charAt(i)=='*'||a.charAt(i)=='/') {
                finalTokens[i] = a.charAt(i)+"";
            }else if(a.charAt(i)!=' '){
                if (a.charAt(i) == '1' || a.charAt(i) == '2' || a.charAt(i) == '3' || a.charAt(i) == '4' || a.charAt(i) == '5' || a.charAt(i) == '6' || a.charAt(i) == '7' || a.charAt(i) == '8' || a.charAt(i) == '9') {
                    finalTokens[i] = a.charAt(i) + "";
                    counter = i;
                    for(int j = counter+1; j < a.length(); j++) {
                        if (a.charAt(j) == '1' || a.charAt(j) == '2' || a.charAt(j) == '3' || a.charAt(j) == '4' || a.charAt(j) == '5' || a.charAt(j) == '6' || a.charAt(j) == '7' || a.charAt(j) == '8' || a.charAt(j) == '9') {
                            finalTokens[counter] = finalTokens[counter]+ a.charAt(j);
                            i++;
                        }else break;
                    }
                } else{
                    finalTokens[i] = a.charAt(i) + "";
                }
            }
        }
        counter = 0;
        for(int i = 0; i < finalTokens.length; i++){
            if(finalTokens[i] != null){
                counter++;
            }
        }
        finalTokensClean = new String[counter];
        counter = 0;
        for(int i = 0; i < finalTokens.length; i++){
            if(finalTokens[i] != null){
                finalTokensClean[counter] = finalTokens[i];
                counter++;
            }
        }
        return finalTokensClean;
    }
}
