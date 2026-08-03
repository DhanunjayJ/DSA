class Solution {
    public int minDistance(String word1, String word2) {
      int n = word1.length();
      int m = word2.length();
      int [][] dp = new int[n+1][m+1];
      //base cases
      //for zero length of the word 1 
      // then to create word 2 we need to insert length of word2
      for(int i=0;i<=n;i++){
        dp[i][0] = i;
      }
      //same here for word2 that is length zero
      // need i then.
      for(int j=0;j<=m;j++){
        dp[0][j] = j;
      }

      for(int i=1;i<=n;i++){
        for(int j=1;j<=m;j++){
            char c1 = word1.charAt(i-1);
            char c2 = word2.charAt(j-1);
            if(c1==c2){
                dp[i][j] = dp[i-1][j-1];
            }else{
                dp[i][j] = Math.min(dp[i][j-1],Math.min(dp[i-1][j-1],dp[i-1][j]))+1;
            }
        }
      } 
      return dp[n][m];
    }
}