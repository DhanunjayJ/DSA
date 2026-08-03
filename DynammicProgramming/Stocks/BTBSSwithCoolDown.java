class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        
        if(n<=1) return 0;

        int[][] dp = new int[n][2];

        //base cases for the first two days.

        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        dp[1][0] = Math.max(dp[0][0],dp[0][1]+prices[1]);
        dp[1][1] = Math.max(dp[0][1],dp[0][0]-prices[1]);

        for(int i=2;i<n;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            //if we are buying today then we have to check the value of 
            //the last 2nd day. 
            dp[i][1] = Math.max(dp[i-1][1],dp[i-2][0]-prices[i]);
        }

        return Math.max(dp[n-1][0],dp[n-1][1]);
    }
}