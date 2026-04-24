import java.util.Arrays;

public class CoinChange {
    /*
    Intiatin is nothing but just check every coin for every amount and pick the minimum one for each coin and 
    return count. and use dp to check if the count is already done.
    */
    class Solution {
    int [] dp;
    public int coinChange(int[] coins, int amount) {
        dp = new int[amount+1];
        Arrays.fill(dp,-2);
        return countMin(coins,amount);
    }
    public int countMin(int []coins,int amount){
        if (amount==0) return 0;
        if (amount<0) return -1;
        if(dp[amount]!=-2) return dp[amount];
        int min = Integer.MAX_VALUE;
        for(int i=0;i<coins.length;i++){
            int res = countMin(coins,amount-coins[i]);
            if(res>=0){
                min = Math.min(res+1,min);
            }
        }
        dp[amount] = min==Integer.MAX_VALUE ? -1 : min;
        return dp[amount];
    }
}
}



// bottom up 
class Solution {
    int [] dp;
    public int coinChange(int[] coins, int amount) {
    dp = new int[amount+1];
    Arrays.fill(dp,-2);
    dp[0] = 0;
    for(int i=1;i<=amount;i++){
        int min = Integer.MAX_VALUE;
        for(int j = 0;j<coins.length;j++){
            // to get the amount that is equal to i how many coins i need
            if(i-coins[j]>=0){
            int res = dp[i-coins[j]];
            if(res>=0){
                min = Math.min(res+1,min);
            }
            }
        }
        dp[i] = min==Integer.MAX_VALUE ? -2 : min;
    }
    return dp[amount]==-2 ? -1 : dp[amount];
    }
}