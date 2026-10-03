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