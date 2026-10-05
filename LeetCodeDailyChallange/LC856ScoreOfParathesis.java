class Solution {
    public int scoreOfParentheses(String s) {
       Stack<Integer> st = new Stack<>();
       //when ever we get ( we push 0.
       //at last place we always place the value at the last of the stack.
       st.push(0);
       for(int i=0;i<s.length();i++){
        char c = s.charAt(i);
        if(c=='('){
            st.push(0);
        }else{
            int innerScore = st.pop();
            //if innerScore is zero. we add one. 
            //if not zero we add 2*current Innervalue.
            int val = (innerScore==0) ? 1 : 2*innerScore;
            st.push(val+st.pop());
        }
       }
       return st.pop();
    }
}

//Bit Manipulation Approach

class Solution {
    public int scoreOfParentheses(String s) {
        int score = 0;
        int depth = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                depth++;
            } else {
                depth--;
                // Check if this ')' immediately follows a '(' (a core "()")
                if (s.charAt(i - 1) == '(') {
                    score += 1 << depth; // Equivalent to 2^depth
                }
            }
        }
        
        return score;
    }
}