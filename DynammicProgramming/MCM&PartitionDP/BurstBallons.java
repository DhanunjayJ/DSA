class Solution {
    public int maxCoins(int[] nums) {
        /*

        since the last index and first indexes that are out of the array are considered
        as 1 we include them in the array to make the ocunt of the coins easy.

        dp[i][j] = represents the maximum number coings we get STRICTLY between i and j
        and j-i>1. if <=1 then there is no ballon.

        recurance relation dp[i][j] = dp[i][k] + dp[k][j] + nums[i]*nums[k]*nums[j];
        */
        int n = nums.length;
        int [] newBallons = new int[n+2];
        newBallons[0] = 1;
        for(int i=0;i<n;i++) newBallons[i+1] = nums[i];
        newBallons[n+1] = 1;
        int totalLen = n+2;
        int [][] dp = new int[totalLen][totalLen];
        for(int i=0;i<totalLen;i++) Arrays.fill(dp[i],-1);
        return helper(newBallons,dp,0,totalLen-1);
    }
    public int helper(int [] ballons,int [][] dp ,int i,int j){
        if(j-i<=1) return 0;
        if(dp[i][j]!=-1) return dp[i][j];
        int max = Integer.MIN_VALUE;
        for(int k=i+1;k<j;k++){
            int cMax = helper(ballons,dp,i,k) + helper(ballons,dp,k,j) + ballons[i]*ballons[k]*ballons[j];
            max = Math.max(cMax,max);
        }
        return dp[i][j] = max;
    }
}