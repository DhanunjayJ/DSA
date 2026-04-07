class Solution {
    /*
    First we need to check if the string is present and valid from the last valid index to the current index
    if it then go the next string and check validity. if not false;
    we use dp because the values are large if repeating chracters come it will go TLE/
    */
    Boolean [] dp;
    HashSet<String> words;
    public boolean wordBreak(String s, List<String> wordDict) {
        words = new HashSet<>();
        for(String st:wordDict){
            words.add(st);
        }
        dp = new Boolean[s.length()+1];
        return isValid(s,0);
    }
    public boolean isValid(String s,int i){
        if(i==s.length())return true;
        if(dp[i]!=null) return dp[i];
        for(int j=i;j<s.length();j++){
            if(words.contains(s.substring(i,j+1))){
                if(isValid(s,j+1))
                {
                    dp[i] = true;
                    return dp[i];
                }
            }
        }
        dp[i] = false;
        return dp[i];
    }
}