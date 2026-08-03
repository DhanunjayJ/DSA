public class DistinctSubsequences {
    class Solution {
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int [][] dp = new int[n+1][m+1];
        /*
        
        If j < 0, it means we successfully matched every single character of t, which counts as 1 valid distinct subsequence. So we return 1.

        In your iterative 2D array, indices are shifted by 1. So, when the string t is empty, it corresponds to the 0-th column of your DP table (dp[i][0]).

        */
        for(int i=0;i<=n;i++){
            dp[i][0] = 1;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(s.charAt(i-1)==t.charAt(j-1)){
                    dp[i][j] = dp[i-1][j] + dp[i-1][j-1];
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[n][m];
    }
}
}
