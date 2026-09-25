class Solution {
    public int minAddToMakeValid(String s) {
        // Stack<Character> st = new Stack<>();
        int countOpen = 0;
        int countClose = 0;
        for(char c : s.toCharArray()){
            if(c==')' && countOpen>0){
                countOpen--;
            }else{
                if(c==')') countClose++;
                if(c=='(') countOpen++;
            }
        }
        return countOpen+countClose;
    }
}