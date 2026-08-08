import java.util.*;

class Solution {
    // Function to return the LIS as a list
    public List<Integer> longestIncreasingSubsequence(int[] nums) {
        int n = nums.length;

        // DP array to store length of LIS ending at each index
        int[] dp = new int[n];

        // Array to store previous index of LIS element for reconstruction
        int[] prev = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);

        // Compute LIS length for each index
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    // Update dp[i] and store previous index
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
        }

        // Find the index of maximum LIS length
        int maxLen = 0, maxIndex = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxIndex = i;
            }
        }

        // Reconstruct LIS sequence
        List<Integer> lisSeq = new ArrayList<>();
        int curr = maxIndex;
        while (curr != -1) {
            lisSeq.add(nums[curr]);
            curr = prev[curr];
        }

        // Reverse sequence as it was built backwards
        Collections.reverse(lisSeq);

        return lisSeq;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};

        Solution sol = new Solution();
        List<Integer> lis = sol.longestIncreasingSubsequence(nums);

        System.out.print("LIS: ");
        for (int x : lis) System.out.print(x + " ");
        System.out.println();
    }
}
