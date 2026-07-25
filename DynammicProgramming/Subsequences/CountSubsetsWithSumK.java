class Solution {
    // Function to calculate the number of subsets with a given sum
    public int perfectSum(int[] nums, int target) {
        // code here
        int n = nums.length;
        
        int [][] dp = new int[n][target+1];
        
        for(int i=0;i<n;i++) Arrays.fill(dp[i],-1);
        
        return helper(dp,nums,0,target);
    }
    public int helper(int [][]dp,int [] nums,int i,int target){
        
        //If we reached the end then check if the target 
        //became zero if it is then count+1
        if(i==nums.length){
            return target==0 ? 1 : 0;
        }
        
        if(dp[i][target]!=-1) return dp[i][target];
        
        int pick = 0;
        
        //to pick this element arr[i]<=target
        if(nums[i]<=target)
        pick = helper(dp,nums,i+1,target-nums[i]);
        
        int notpick = helper(dp,nums,i+1,target);
        
        dp[i][target] = pick+notpick;
        
        return dp[i][target];
    }
}

// Iterative Approach
class Solution {
    // Function to calculate the number of subsets with a given sum
    public int perfectSum(int[] nums, int target) {
        // code here
        int n = nums.length;
        int [][] dp = new int[n+1][target+1];
        dp[n][0] = 1;
        for(int i=n-1;i>=0;i--){
            for(int t=0;t<=target;t++){
                int exclude = dp[i+1][t];
                int include = 0;
                if(nums[i]<=t){
                    include = dp[i+1][t-nums[i]];
                }
                dp[i][t] = include+exclude;
            }
        }
        return dp[0][target];
    }
}
//optimized
class Solution {
    // Function to calculate the number of subsets with a given sum
    public int perfectSum(int[] nums, int target) {
        // code here
        int n = nums.length;
        int [] dp = new int[target+1];
        dp[0] = 1;
        for(int i=n-1;i>=0;i--){
            for(int t=target;t>=nums[i];t--){
                if(dp[t-nums[i]]!=0){
                    dp[t] += dp[t-nums[i]];
                }
            }
        }
        return dp[target];
    }
}