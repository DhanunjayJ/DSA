public class LongestPalindromicSubsequence {
    class Solution {
    public int longestPalindromeSubseq(String s) {
        /*
        we have to find the longest palindromic subsequnce
        if we take the same string in the reverse order.
        then we fid he lcs, then we have that one whi ch is 
        the lps, because the if ther eis a palidrome then 
        if we write it in the rervese order alos it will be palidrome,
        so we do this.
        */
        int n = s.length();
        String r = new StringBuilder(s).reverse().toString();
        int [][] dp = new int[n+1][n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                int c1 = s.charAt(i-1);
                int c2 = r.charAt(j-1);
                if(c1==c2){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[n][n];
    }
}
}
