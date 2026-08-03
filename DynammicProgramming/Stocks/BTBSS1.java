class Solution {

    //greedy way
    public int maxProfit(int[] prices) {
        /*
        We keep updating the max profit for every day.
        when the min goes down than the previous ones
        then we alos update the max, since we can't sell
        in the past. if the max is at the past.
        */
        int min = 10001;
        int max = 0;
        int maxProfit = 0;
        for(int price : prices){
            max = Math.max(price,max);
            if(price<min){
                min = price;
                max = min;
            }
            maxProfit = Math.max(maxProfit,max-min);
        }
        return maxProfit;
    }

    //

    class Solution1 {
    public int maxProfit(int[] prices) {
        /*
        at any day, there will two chioces
        we are holding a stock or not holding a stock.

        so dp[i][0] not holding a stock
        dp[i][1] holding a stock

        transitions 
        dp[i][0] = to end up with zero stocks today. what do we have to do.
        dp[i][0] = smae as yesterday rest today, dp[i-1][0] or 
        if you have bouht the stock yearday sell it today. dp[i-1][1]+prices[i];

        dp[i][1] = to end up with one stock today what do we have to do.
        dp[i][1] = if we bought the stock yesterday get it. dp[i-1][1] 
        or if we have't boung it previosuly then buy it today. 
        - prices[i];

        dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
        dp[i][1] = Math.max(dp[i-1][1],-prices[i]);
        */

        if(prices==null || prices.length==0) return 0;

        int n = prices.length;
        int [][] dp = new int[n][2];

        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for(int i=1;i<n;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            dp[i][1] = Math.max(dp[i-1][1],-prices[i]);
        }

        return Math.max(dp[n-1][0],dp[n-1][1]);
    }
}

//iterative way. 
class Solution2 {
    public int maxProfit(int[] prices) {

        /*
        since the dp table only depend on two vairbles
        dp[i-1][0] , dp[i-1][1]
        we could use variables instead of an array.
        */

        int n = prices.length;
        int nothold = 0;
        int hold = -prices[0];

        for(int i=1;i<n;i++){
            nothold = Math.max(nothold,hold+prices[i]);
            hold = Math.max(hold,-prices[i]);
        }

        return Math.max(nothold,nothold);
    }
}
}

