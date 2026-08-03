class Solution {
    //greedy way
    public int maxProfit(int[] prices) {
        int profit = 0;
        for(int i=1;i<prices.length;i++){
            if(prices[i]>prices[i-1]){
                profit += prices[i]-prices[i-1];
            }
        }
        return profit;
    }

    //dp apprach optimized
    class Solution1 {
    public int maxProfit(int[] prices) {
        /*
        same as BTBSS1 but here when buying today we conider
        the profit of the nohold state of the yesterday since 
        we are able to make multiple transactions
        */
        int n = prices.length;
        int noHold = 0;
        int hold = -prices[0];
        for(int i=1;i<n;i++){
            noHold = Math.max(noHold,hold+prices[i]);
            hold = Math.max(hold,noHold-prices[i]);
        } 
        return Math.max(noHold,hold);
    }
}
}