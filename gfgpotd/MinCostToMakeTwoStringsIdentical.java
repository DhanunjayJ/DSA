class Solution {
    public int findMinCost(String s1, String s2, int costS1, int costS2) {
        // code here
        int n = s1.length();
        int m = s2.length();
        long [][] dp = new long[n+1][m+1];
        //base cases for if the j the string is empty the cost will
        // be length of the stirng + the cost of deleting the each character. 
        for(int i=0;i<=n;i++){
            dp[i][0] = costS1*i;
        }
        //same here like above. 
        for(int j=0;j<=m;j++){
            dp[0][j] = costS2*j;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                char c1 = s1.charAt(i-1);
                char c2 = s2.charAt(j-1);
                if(c1==c2){
                    //if equals same cost as preivous ones.
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    //else get the min cost out of the two options skipping the 
                    //i character or skipping the jth character 
                    dp[i][j] = Math.min(dp[i-1][j]+costS1,dp[i][j-1]+costS2);
                }
            }
        }
        return (int) dp[n][m];
    }
}