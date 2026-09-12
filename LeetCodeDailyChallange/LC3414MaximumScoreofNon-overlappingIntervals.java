import java.util.*;

class Solution {
    
    /**
     * Helper class to bundle both the accumulated weight and the list of chosen interval indices.
     * This is necessary because we need to maximize the total weight while simultaneously 
     * satisfying a strict tie-breaking rule: choosing the lexicographically smallest index array.
     */
    static class Result {
        long weight;
        List<Integer> indices;

        Result(long weight, List<Integer> indices) {
            this.weight = weight;
            this.indices = indices;
        }

        /**
         * Compares two Result objects to pick the optimal one according to problem constraints:
         * 1. Maximize total weight.
         * 2. If weights are identical, pick the lexicographically smaller sequence of indices.
         */
        public static Result compare(Result r1, Result r2) {
            if (r1 == null) return r2;
            if (r2 == null) return r1;

            // Rule 1: Always prioritize the higher total weight
            if (r1.weight != r2.weight) {
                return r1.weight > r2.weight ? r1 : r2;
            }

            // Rule 2: Weights are equal, evaluate lexicographical order element-by-element
            for (int i = 0; i < Math.min(r1.indices.size(), r2.indices.size()); i++) {
                int cmp = Integer.compare(r1.indices.get(i), r2.indices.get(i));
                if (cmp != 0) {
                    return cmp < 0 ? r1 : r2; // Return the one with the smaller element first
                }
            }
            
            // If one list is a strict prefix of the other, or they are identical, 
            // the shorter list is considered lexicographically smaller.
            return r1.indices.size() <= r2.indices.size() ? r1 : r2;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        
        // Step 1: Augment each interval with its original index (stored at position 3)
        // This is crucial because sorting the intervals alters their initial positions, 
        // but the problem requires us to return their original indices.
        for (int i = 0; i < n; i++) {
            intervals.get(i).add(i);
        }
        
        // Step 2: Sort intervals primarily by their start times (l).
        // Why sort by start time? It allows us to process elements chronologically and 
        // use binary search to instantly jump past overlapping intervals.
        Collections.sort(intervals, (a, b) -> Integer.compare(a.get(0), b.get(0)));

        // Step 3: Initialize a Memoization table for Dynamic Programming.
        // dp[i][rem] stores the cached Result starting from index 'i' with 'rem' quota remaining.
        // Dimensions: n intervals × up to 5 quota states (0 to 4).
        Result[][] dp = new Result[n][5];
        
        // Start the recursive DFS from index 0 with a maximum quota of 4 intervals.
        Result best = solve(dp, intervals, 0, 4);

        // Step 4: Convert the optimal List of indices into a primitive int array for the final answer.
        int[] ans = new int[best.indices.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = best.indices.get(i);
        }
        return ans;
    }

    /**
     * Recursive DP function with memoization.
     * Represents the choice of whether to pick or skip the interval at index 'i' 
     * given that we are allowed to pick up to 'rem' more intervals.
     */
    public Result solve(Result[][] dp, List<List<Integer>> intervals, int i, int rem) {
        // Base case: If we have used up all 4 intervals (rem == 0) or reached the end of the list.
        if (rem == 0 || i >= intervals.size()) {
            return new Result(0, new ArrayList<>());
        }

        // Return cached result if already computed to avoid redundant exponential subproblems.
        if (dp[i][rem] != null) return dp[i][rem];

        // Choice 1: SKIP the current interval. 
        // Move directly to the next adjacent interval (i + 1) keeping the remaining quota intact.
        Result skipResult = solve(dp, intervals, i + 1, rem);

        // Choice 2: PICK the current interval.
        // Since intervals are sorted by start time, any non-overlapping interval must start 
        // strictly after the current interval's end time (intervals.get(i).get(1)).
        // We use binary search to instantly find the first valid non-overlapping index.
        int nextIdx = binarySearch(intervals, intervals.get(i).get(1), i + 1);
        
        // Recursively solve for the remaining valid interval choices with quota reduced by 1.
        Result nextResult = solve(dp, intervals, nextIdx, rem - 1);

        // Construct the combined index sequence for the "Pick" choice
        List<Integer> newIndices = new ArrayList<>();
        newIndices.add(intervals.get(i).get(3)); // Add the current interval's original index
        newIndices.addAll(nextResult.indices);   // Append subsequent chosen indices
        
        // Ensure the list remains sorted to maintain proper lexicographical evaluation order.
        Collections.sort(newIndices);

        // Calculate total weight for this pick choice
        Result pickResult = new Result(nextResult.weight + intervals.get(i).get(2), newIndices);

        // Compare both choices (Pick vs. Skip) using our custom logic, 
        // caching the optimal Result in our dp table, and returning it.
        return dp[i][rem] = Result.compare(pickResult, skipResult);
    }

    /**
     * Efficient Binary Search (Upper Bound variant) to find the index of the first interval 
     * whose start time is strictly greater than the given target end time (val).
     */
    public int binarySearch(List<List<Integer>> intervals, int val, int low) {
        int high = intervals.size() - 1;
        int ans = intervals.size(); // Default to out-of-bounds if no valid interval exists
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            // If this interval starts after our target ends, it's a candidate.
            // Record it and search further left for an even earlier valid interval.
            if (intervals.get(mid).get(0) > val) {
                high = mid - 1;
                ans = mid;
            } else {
                // Otherwise, it starts too early (overlaps), look to the right.
                low = mid + 1;
            }
        }
        return ans;
    }
}