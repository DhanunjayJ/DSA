// User function Template for Java

class Solution {
    static String postToPre(String post_exp) {
        // code here
        Deque<String> stack = new ArrayDeque<>();
        int n = post_exp.length();
        for(int i=0;i<n;i++){
            char c = post_exp.charAt(i);
            if(Character.isLetterOrDigit(c)){
                stack.push(c+"");
            }else{
                String val2 = stack.pop();
                String val1 = stack.pop();
                stack.push(c+val1+val2);
            }
        }
        return stack.pop();
    }
}
