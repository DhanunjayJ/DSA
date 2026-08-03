class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        //edge case if the tranasciotn are more than n or equal to n/2 
        // it is equal to the max stock profit ones type 2.
        // with infinate transactions

         if(k>=n/2){
            int profit = 0;
            for(int i=1;i<n;i++){
                if(prices[i-1]<prices[i]){
                    profit += prices[i]-prices[i-1];
                }
            }
            return profit;
        }


        int [][][] dp = new int[n][2][k+1];

        for(int i=0;i<n;i++){
            for(int j=0;j<2;j++){
                Arrays.fill(dp[i][j],(int)-1e9);
            }
        }

        dp[0][0][0] = 0;
        dp[0][1][1] = -prices[0];

        for(int i=1;i<n;i++){
            dp[i][0][0] = 0;
            for(int j=1;j<=k;j++){
                dp[i][0][j] = Math.max(dp[i-1][0][j],dp[i-1][1][j]+prices[i]);
                dp[i][1][j] = Math.max(dp[i-1][1][j],dp[i-1][0][j-1]-prices[i]);
            }
        }

        int maxProfit = Integer.MIN_VALUE;
        for(int i=0;i<=k;i++){
            maxProfit = Math.max(maxProfit,dp[n-1][0][i]);
        }

        return maxProfit;

    }
}