public class LC115DistinctSubsequeces {
    class Solution {
    public int numDistinct(String s, String t) {
        int n = s.length();
        int m = t.length();
        int [][] dp = new int[n+1][m+1];
        // on the string s we ahve the chioce of pick and not pick
        //the base case, when the length of the string t is zero
        // and the length of the s is 0 to i.
        //there is alwaus one way to pick which is pick nothing.
        //so we put all the dp[i][0] = 1;
        for(int i=0;i<=n;i++){
            dp[i][0] = 1;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                char c1 = s.charAt(i-1);
                char c2 = t.charAt(j-1);
                if(c1==c2){
                    //pick ith index or else skip.
                    //when the values are equal. 
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                }else{
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[n][m];
    }
}
}
