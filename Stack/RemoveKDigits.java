class Solution {
    public String removeKdigits(String num, int k) {
        //the edge cae here that if the length ==k 
        if (k == num.length()) return "0";

        Deque<Character> st = new ArrayDeque<>();

        StringBuilder str = new StringBuilder();

        //maintain the montonic increasing stack and pop the the element
        //when we have element that are greater thatn what is there ont he
        //top of the stack.
        for(int i=0;i<num.length();i++){
            char c = num.charAt(i);
            while(!st.isEmpty() && k>0 && c<st.peek()){
                    st.pop();
                    k--; 
            }
            st.push(c);
        }

        //if k is still there, then pop the k rem elements.from the top
        // becase the stack is in increasing order.
        while(k>0 && !st.isEmpty()){
            st.pop();
            k--;
        }

        //add them to the stringBuilder
        while (!st.isEmpty()) {
            str.append(st.pop());
        }

        str.reverse();

        //delete leading zeros.!!!
        while(str.length()>0 && str.charAt(0)=='0'){
            str.deleteCharAt(0);
        }

        return str.length()==0 ? "0" : str.toString();
    }
}