class Solution {
    public int longestValidParentheses(String s) {
        Deque<Integer> st = new ArrayDeque<>();
        //we always try to maintain a boundary marker in the stack.
        //when it is popped then we know that that that correspoiding
        //( is not there for ) so again add new ) as new boundary marker
        st.push(-1);
        int max = 0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='('){
                st.push(i);
            }else{
                st.pop();
                //if we poped the boundary marker.
                // then this will be out new boundary marker.
                if(st.isEmpty()){
                    st.push(i);
                }else{
                    max = Math.max(max,i-st.peek());
                }
            }
        }
        return max;
    }
}

//two Pointers approach
class Solution {
    public int longestValidParentheses(String s) {
        int right = 0;
        int left = 0;
        int maxLen = 0;
        //going left to right. 
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(')
                left++;
            else
                right++;
            if (left == right)
                maxLen = Math.max(maxLen, 2 * left);
                //if count of (())) right is greater then we reset. 
            else if (right > left) {
                left = 0;
                right = 0;
            }
        }
        left = 0;
        right = 0;
        //going right to left. 
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '(')
                left++;
            else
                right++;
            if (left == right)
                maxLen = Math.max(maxLen, 2 * right);
                //((()) if the count of the left is greater we reset. 
            else if (left > right) {
                left = 0;
                right = 0;
            }
        }
        return maxLen;
    }
}

