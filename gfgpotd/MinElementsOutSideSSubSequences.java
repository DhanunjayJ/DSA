class Solution {
    private static int[][][] dp;

    public static int minCount(int[] arr) {
        int n = arr.length;
        // dp[idx][incLast + 1][decLast + 1]
        dp = new int[n + 1][n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return solve(0, -1, -1, arr);
    }

    private static int solve(int idx, int incLast, int decLast, int[] arr) {
        // Base case: processed all elements
        if (idx == arr.length) {
            return 0;
        }

        // Adjust indices for 0-based storage in memo table (-1 maps to 0)
        int incIdx = incLast + 1;
        int decIdx = decLast + 1;

        if (dp[idx][incIdx][decIdx] != -1) {
            return dp[idx][incIdx][decIdx];
        }

        // Choice 1: Skip the current element
        int ans = 1 + solve(idx + 1, incLast, decLast, arr);

        // Choice 2: Include in the increasing subsequence
        if (incLast == -1 || arr[idx] > arr[incLast]) {
            ans = Math.min(ans, solve(idx + 1, idx, decLast, arr));
        }

        // Choice 3: Include in the decreasing subsequence
        if (decLast == -1 || arr[idx] < arr[decLast]) {
            ans = Math.min(ans, solve(idx + 1, incLast, idx, arr));
        }

        return dp[idx][incIdx][decIdx] = ans;
    }
}