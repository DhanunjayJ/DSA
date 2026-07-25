import java.util.Arrays;

public class CoinChange {
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


//

class Solution {
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int [] dp = new int[amount+1];
        //the base case to get amount zero we need zero coins.
        dp[0] = 0;
        //for all amounts find the the minimum
        //denominations to reach hat amount.
        for(int i=1;i<=amount;i++){
            //intalize a vaiable with max vlaues
            int min = Integer.MAX_VALUE;
            //detect each coin from the amount.
            for(int j=0;j<n;j++){
                //check if the coin is less than the amount
                if(coins[j]<=i){
                    //if it possible get the mincount of it from the
                    //dp table. 
                    int res = dp[i-coins[j]];
                    //if that min count to reach that check if
                    // is there way to reach it , if not negative. 
                    if(res>=0){
                        min = Math.min(min,res+1);
                    }
                }
            }
            //if it is impossible to reach this denomination
            //then keep it -1;
            dp[i] = min == Integer.MAX_VALUE ? -1 : min;
        }
        return dp[amount];
    }
}



// class Solution {
    public int coinChange(int[] coins, int amount) {
        int [] dp = new int [amount+1];
        Arrays.fill(dp,amount+1);
        dp[0] = 0;
        for(int i=0;i<=amount;i++){
            for(int coin : coins){
                if(coin<=i){
                    dp[i] = Math.min(dp[i],dp[i-coin]+1);
                }
            }
        } 
        return dp[amount] > amount ? -1 : dp[amount];
    }
}


// BFS Approach


}
