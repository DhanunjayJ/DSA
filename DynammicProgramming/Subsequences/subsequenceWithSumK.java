package DynammicProgramming.Subsequences;

public class subsequenceWithSumK {
    class Solution {
    public boolean checkSubsequenceSum(int[] arr, int k) {
        int n = arr.length;
        // 2D DP: rows = indices (0 to n-1), columns = target sums (0 to k)
        Boolean[][] dp = new Boolean[n][k + 1];
        return helper(dp, arr, 0, k);
    }
    
    public boolean helper(Boolean[][] dp, int[] arr, int i, int k){
        // Base cases
        if (k == 0) return true;
        if (k < 0) return false;
        if (i >= arr.length) return false; // k == 0 is already handled above
        
        // Check both dimensions: index 'i' AND remaining sum 'k'
        if (dp[i][k] != null) return dp[i][k];
        
        // Pick the current element OR skip it
        dp[i][k] = helper(dp, arr, i + 1, k - arr[i]) || helper(dp, arr, i + 1, k);
        
        return dp[i][k];
    }
}
}
