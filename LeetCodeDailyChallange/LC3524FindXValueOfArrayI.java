class Solution {
    public long[] resultArray(int[] nums, int k) {
        // ans[x] will store the cumulative count of ALL subarrays found so far
        // across the entire array whose product modulo k equals x.
        // We use 'long' because an array of size n can have up to n*(n+1)/2 subarrays,
        // which easily overflows a 32-bit standard integer.
        long[] ans = new long[k];

        // dp[r] acts as our "state from the previous step".
        // It holds the count of contiguous subarrays that END EXACTLY at index (i - 1)
        // and have a running product % k == r.
        // There can only ever be k possible remainders (0 through k - 1).
        long[] dp = new long[k];

        for (int i = 0; i < nums.length; i++) {
            // nextDp will store the count of subarrays that END EXACTLY at the current index i.
            // We create a fresh array on every iteration because subarrays MUST be contiguous.
            // If an old subarray does not connect to nums[i], it cannot continue to index i.
            long[] nextDp = new long[k];

            // -------------------------------------------------------------------------
            // CASE 1: The current element starts a BRAND NEW subarray of length 1 -> [nums[i]]
            // -------------------------------------------------------------------------
            // Every element by itself is a valid non-empty subarray ending at index i.
            // We find its remainder when divided by k and record 1 new subarray in that bucket.
            int currRem = nums[i] % k;
            nextDp[currRem]++;

            // -------------------------------------------------------------------------
            // CASE 2: Extending PREVIOUS subarrays to also end at index i
            // -------------------------------------------------------------------------
            // Any subarray that ended at index (i - 1) can be extended by appending nums[i].
            // Thanks to modular arithmetic, we do NOT need the actual giant products:
            // (PreviousProduct * nums[i]) % k == ((PreviousProduct % k) * nums[i]) % k
            //                                 == (r * nums[i]) % k
            //
            // So every previous subarray that had remainder 'r' will now transform
            // into a new subarray with remainder 'nextRem'.
            for (int r = 0; r < k; r++) {
                if (dp[r] > 0) {
                    // Cast to 'long' before multiplying to prevent integer overflow
                    // if 'r * nums[i]' exceeds Integer.MAX_VALUE.
                    int nextRem = (int) (((long) r * nums[i]) % k);

                    // If there were dp[r] subarrays ending at (i - 1) with remainder r,
                    // extending all of them gives exactly dp[r] new subarrays ending at i
                    // with remainder nextRem.
                    nextDp[nextRem] += dp[r];
                }
            }

            // -------------------------------------------------------------------------
            // TALLY INTO GRAND TOTAL:
            // -------------------------------------------------------------------------
            // 'nextDp' now contains all valid contiguous subarrays ending at index i.
            // We add these counts to our overall answer array 'ans'.
            for (int r = 0; r < k; r++) {
                ans[r] += nextDp[r];
            }

            // -------------------------------------------------------------------------
            // ADVANCE THE DP STATE:
            // -------------------------------------------------------------------------
            // For the next loop iteration (index i + 1), what is currently 'nextDp'
            // becomes the 'dp' of the previous element.
            dp = nextDp;
        }

        return ans;
    }
}