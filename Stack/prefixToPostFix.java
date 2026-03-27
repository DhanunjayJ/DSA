// User function Template for Java

class Solution {
    static String preToPost(String pre_exp) {
        // code here
        Deque<String> ope = new ArrayDeque<>();
        int n = pre_exp.length();
        for(int i=n-1;i>=0;i--){
            char c = pre_exp.charAt(i);
            if(Character.isLetterOrDigit(c)){
                ope.push(c+"");
            }else{
                String val1 = ope.pop();
                String val2 = ope.pop();
                ope.push(val1+val2+c);
            }
        }
        return ope.pop();
    }
}
