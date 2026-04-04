class Solution {
    /* We have to have every substring that is palidrome we have to check before adding 
    that the the string we are adding is a palindrome.
    if it is palidrome then go to the next character. and check for palidnrome and go next
    untill you go till the end of the string. i == s.length();
    */
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();
        generate(ans,s,0,new ArrayList<>());
        return ans;
    }

    public void generate(List<List<String>> ans,String s,int start,List<String> temp){
        if(start==s.length()){
            ans.add(new ArrayList<>(temp));
            return;
        }
        for(int i=start;i<s.length();i++){
            if(isPali(start,i,s)){
                temp.add(s.substring(start,i+1));
                generate(ans,s,i+1,temp);
                temp.remove(temp.size()-1);
            }
        }
    }

    public boolean isPali(int start,int end,String s){
        while(start<end){
            if(s.charAt(start)!=s.charAt(end)) return false;
            start++;
            end--;
        }
        return true;
    }
}

//Here we are using the extra dp space to for all the palindromes in the upfront now the isPalic check is o(1).

class Solution {
    boolean [][] dp;


    public List<List<String>> partition(String s) {
        List<List<String>> ans = new ArrayList<>();


        dp = new boolean[s.length()][s.length()];


        for(int i=0;i<s.length();i++){
            for(int j=i;j<s.length();j++){
                dp[i][j] = isPali(s,i,j);
            }
        }


        helper(ans,new ArrayList<>(),0,s);
        return ans;
    }
    public void helper(List<List<String>> ans, List<String> temp, int start,String s){
        if(start==s.length()){
            ans.add(new ArrayList<>(temp));
            return;
        }
        for(int i=start;i<s.length();i++){
            if(dp[start][i]){
                temp.add(s.substring(start,i+1));
                helper(ans,temp,i+1,s);
                temp.remove(temp.size()-1);
            }
        }
    }
    
    public boolean isPali(String s,int left,int right){
        while(left<=right){
            if(s.charAt(left)!=s.charAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}