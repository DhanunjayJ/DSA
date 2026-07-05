import java.util.Arrays;

class Solution {
    int[] dp;
    
    public int frogJump(int[] heights, int k) {
        int n = heights.length;
        dp = new int[n];
        Arrays.fill(dp, -1);
        
        // We want the answer to be computed and returned by the helper
        return helper(heights, k, n - 1);
    }
    
    public int helper(int[] heights, int k, int i) {
        // Base case: 0 energy needed to stay on the 0th step
        if (i == 0) return 0;
        
        // Memoization check
        if (dp[i] != -1) return dp[i];
        
        int min = Integer.MAX_VALUE;
        
        // Check all steps from 1 to k behind the current step i
        for (int j = 1; j <= k; j++) {
            if (i - j >= 0) {
                int dist = helper(heights, k, i - j) + Math.abs(heights[i - j] - heights[i]);
                min = Math.min(dist, min);
            }
        }
        
        dp[i] = min;
        return dp[i];
    }
}