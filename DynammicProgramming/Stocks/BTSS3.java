class Solution {
    public int maxProfit(int[] prices) {

        int n = prices.length;

        int [][][] dp = new int [n][2][3];

        //filiing the dp wiht -inf to get correct value duing the 0ve vlaues. 
        for(int i=0;i<n;i++){
            for(int j=0;j<2;j++){
                Arrays.fill(dp[i][j],(int)-1e9);
            }
        }

        // base cases for the zeroth days 
        // zero tractions holding a stock 
        dp[0][0][0] = 0; // notholdig a stock
        dp[0][1][1] = -prices[0]; //holding a stock.

        for(int i=1;i<n;i++){
            dp[i][0][0] = 0;
            for(int k=1;k<=2;k++){
                //to end up with zero stock today. 
                //counting buy as the count of transaction/
                dp[i][0][k] = Math.max(dp[i-1][0][k],dp[i-1][1][k]+prices[i]);
                //buy counted as tranaactoin start.
                dp[i][1][k] = Math.max(dp[i-1][1][k],dp[i-1][0][k-1]-prices[i]);
            }
        }
        return Math.max(0,Math.max(dp[n-1][0][1],dp[n-1][0][2]));
    }
}