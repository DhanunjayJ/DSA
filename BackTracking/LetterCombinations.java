class Solution {
    /* first we need to maintain these values in a hasmap or where we can that string 
    when we give the digit it will give the string.
    for each string of length n, we need to literally map it to the every character of the next
    occuring character in the next stirng. 
    so for each string character we call the next digit and match it through all the 
    character of that to the next one. untill we find a string that is equal to the length of the
    digit. 
    once we find it out then we add that string to the answer and return.
    we use for loop to tracverse and throught the current string and goto the next string too.
     */
    public List<String> letterCombinations(String digits) {
        List<String> ans  = new ArrayList<>();
        //for each backtracking state we need to pass the string,ans,currentdigitindex,stringbuilder
        generate(digits,ans,0,new StringBuilder());
        return ans;
    }
    public void generate(String digits,List<String> ans,int idx,StringBuilder st){

        if(st.length()==digits.length()){
            ans.add(st.toString());
            return;
        }

        String str = getString(digits.charAt(idx));

        for(int i=0;i<str.length();i++){
            st.append(str.charAt(i));
            generate(digits,ans,idx+1,st);
            st.deleteCharAt(st.length()-1);
        }

    }

    public String getString(char c){
        return switch(c) {
            case '2' -> "abc";
            case '3' -> "def";
            case '4' -> "ghi";
            case '5' -> "jkl";
            case '6' -> "mno";
            case '7' -> "pqrs";
            case '8' -> "tuv";
            case '9' -> "wxyz";
            default -> "";
        };
    }
}