package Subsequences;

import java.util.Arrays;

public class TargetSum {
    class Solution {
    int totalSum;
    public int findTargetSumWays(int[] nums, int target) {
       /*
       we can pick +/- for each number.
       */ 
       int n = nums.length;
       totalSum = 0;
       for(int num:nums) totalSum+=num;
       
       //if target is > than the sum of all the elemns then
       //it is impoosible reach target
       if(Math.abs(target)>totalSum) return 0;

       int [][] dp = new int[n][2*totalSum+2];
       for(int i=0;i<n;i++) Arrays.fill(dp[i],-1);
       return helper(nums,0,target,dp);
    }
    public int helper(int [] nums,int i,int target,int [][]dp){
        if(i==nums.length){
            if(target==0) return 1;
            else return 0;
        }

        //if the neagtiv vlaues are soo large that the totalsum
        //can't even handle it then it will never be possible 
        //so we retrn 0;
        if (Math.abs(target) > totalSum) {
        return 0;
        }

        //to handle negative values. we add totalSum offset.
        if(dp[i][target+totalSum] != -1) return dp[i][target+totalSum];

        dp[i][target+totalSum] = helper(nums,i+1,target+nums[i],dp) + 
                            helper(nums,i+1,target-nums[i],dp);

        return dp[i][target+totalSum];
    }
}
}
