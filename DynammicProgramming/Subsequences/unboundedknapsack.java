import java.util.Arrays;

public class unboundedknapsack {
    class Solution {
    int max;
    public int knapSack(int val[], int wt[], int capacity) {
        // code here
        max = Integer.MIN_VALUE;
        int n = val.length;
        int [][] dp = new int[n][capacity+1];
        for(int i=0;i<n;i++) Arrays.fill(dp[i],-1);
        return helper(dp,val,wt,0,capacity);
    }
    public int helper(int [][] dp,int []val,int [] wt,int i,int capacity){
        if(capacity==0){
            return 0;
        }
        if(i==val.length) return 0;
        
        if(dp[i][capacity]!=-1) return dp[i][capacity];
        
        int pickAndStay = 0;
        
        if(wt[i]<=capacity)
        pickAndStay = helper(dp,val,wt,i,capacity-wt[i])+val[i];
        int noPick = helper(dp,val,wt,i+1,capacity);
        
        dp[i][capacity] = Math.max(pickAndStay,noPick);
        
        
        return dp[i][capacity];
    }
}
}
