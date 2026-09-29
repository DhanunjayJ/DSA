// User function Template for Java

class Solution {
    static String postToInfix(String exp) {
        // code here
        Deque<String> ope = new ArrayDeque<>();
        int n = exp.length();
        for(int i=0;i<n;i++){
            char c = exp.charAt(i);
            if(Character.isLetterOrDigit(c)){
                ope.push(c+"");
            }else{
                String val2 = ope.pop();
                String val1 = ope.pop();
                ope.push("("+val1+c+val2+")");
            }
        }
        return ope.pop();
    }
}
