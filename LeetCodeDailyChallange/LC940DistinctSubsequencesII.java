public class LC940DistinctSubsequencesII {
    class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        int n = s.length();
        
        // dp[i] stores the number of distinct subsequences for the prefix of length i
        long[] dp = new long[n + 1];
        dp[0] = 1; // Base case: 1 for the empty subsequence
        
        // Tracks the last seen 1-based index of each lowercase letter ('a' through 'z')
        int[] last = new int[26];
        
        for (int i = 1; i <= n; i++) {
            int c = s.charAt(i - 1) - 'a';
            
            // Step 1: Double the previous count
            dp[i] = (2 * dp[i - 1]) % MOD;
            
            // Step 2: If this character appeared before, subtract the duplicates
            if (last[c] > 0) {
                dp[i] = (dp[i] - dp[last[c] - 1] + MOD) % MOD;
            }
            
            // Step 3: Update the last seen position of this character
            last[c] = i;
        }
        
        // Subtract 1 because the problem asks for NON-EMPTY subsequences (exclude the empty string)
        return (int) ((dp[n] - 1 + MOD) % MOD);
    }
}
}
