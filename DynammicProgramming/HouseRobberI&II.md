Here is the exact progression for **House Robber I**. Since the houses are in a straight line instead of a circle, we don't need to split the array into two scenarios. We can evaluate the array from start to finish.

---

## Approach 1: Top-Down Dynamic Programming (Memoization)

This is the cleaned-up version of your recursive code. It stops the exponential branching ($O(2^N)$) by caching calculated results in a `memo` array.

* **Time Complexity:** $O(N)$ — Each index is calculated exactly once.
* **Space Complexity:** $O(N)$ — The `memo` array takes up $N$ space, plus the recursion call stack takes $O(N)$ space.

```java
import java.util.Arrays;

class Solution {
    public int rob(int[] nums) {
        int[] memo = new int[nums.length];
        Arrays.fill(memo, -1);
        return robHelper(nums, 0, memo);
    }

    private int robHelper(int[] nums, int i, int[] memo) {
        // Base case: if we go past the last house, we can't get any money
        if (i >= nums.length) return 0;
        
        // If we already computed this house's subproblem, return it
        if (memo[i] != -1) return memo[i];

        // Decision: Rob current house (+ move to i+2) OR Skip current house (+ move to i+1)
        int rob = nums[i] + robHelper(nums, i + 2, memo);
        int skip = robHelper(nums, i + 1, memo);

        return memo[i] = Math.max(rob, skip);
    }
}

```

---

## Approach 2: Bottom-Up Dynamic Programming (Iteration)

Instead of moving top-down via recursion, we build a `dp` table from left to right.

* `dp[0]` is the value of the first house.
* `dp[1]` is the maximum between the first and second house.
* For any house `i`, `dp[i] = Math.max(dp[i-1], nums[i] + dp[i-2])`.
* **Time Complexity:** $O(N)$ — One linear pass through the `nums` array.
* **Space Complexity:** $O(N)$ — Allocates an explicit `dp` array of size $N$.

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];

        int[] dp = new int[n];
        
        // Base Cases
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        // Build the DP table sequentially
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
        }

        return dp[n - 1];
    }
}

```

---

## Approach 3: Space-Optimized Dynamic Programming (Optimal)

Notice that in Approach 2, calculating `dp[i]` only ever requires `dp[i-1]` and `dp[i-2]`. The rest of the array history is dead weight. We can completely drop the `dp` array and swap state data using just two variables.

* **Time Complexity:** $O(N)$ — Single loop.
* **Space Complexity:** **$O(1)$** — Purely constant memory footprints.

```java
class Solution {
    public int rob(int[] nums) {
        int prevMax = 0; // Represents dp[i-2]
        int currMax = 0; // Represents dp[i-1]

        for (int num : nums) {
            int temp = currMax;
            // Decide whether to skip this house (keep currMax) or rob it (num + prevMax)
            currMax = Math.max(currMax, prevMax + num);
            prevMax = temp; // Slide our two-variable frame forward
        }

        return currMax;
    }
}

```

---

## Why We Optimize

| Phase | Core Mechanism | Primary Issue |
| --- | --- | --- |
| **Memoization** | Recursion tracking with a cache array. | High call stack overhead; risk of `StackOverflowError` if $N$ is massive. |
| **Iteration** | Eliminates recursion by filling a standard array iteratively. | Unnecessarily takes up $O(N)$ memory allocations for historic data we won't read again. |
| **Space-Optimized** | Maintains only two pointers (`prevMax`, `currMax`) mimicking sliding windows. | **None.** This is the gold standard for production environments and interviews. |

---

Here is the step-by-step evolution of the **House Robber II** problem, moving from your memoization approach down to the optimal $O(1)$ space solution.

---

## 1. The Core Trick: Handling the Circle

Because the houses are in a circle, the first house (`0`) and the last house (`n-1`) are neighbors. You cannot rob both. To break the cycle, we split the problem into two separate linear subproblems:

1. **Scenario A:** Rob from house `0` to `n-2` (Ignore the last house).
2. **Scenario B:** Rob from house `1` to `n-1` (Ignore the first house).

The final answer is simply $\max(\text{Scenario A}, \text{Scenario B})$.

---

## Approach 1: Top-Down Dynamic Programming (Memoization)

This is a cleaner, non-duplicate version of your initial code. We use a single helper function and an array (`memo`) to store the results of subproblems we've already solved, preventing exponential time complexity.

* **Time Complexity:** $O(N)$ — Each house configuration is calculated once.
* **Space Complexity:** $O(N)$ — For the recursion call stack and the `memo` array.

```java
import java.util.Arrays;

class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        
        // Scenario A: House 0 to n-2
        int[] memo1 = new int[n];
        Arrays.fill(memo1, -1);
        int max1 = robHelper(nums, 0, n - 2, memo1);
        
        // Scenario B: House 1 to n-1
        int[] memo2 = new int[n];
        Arrays.fill(memo2, -1);
        int max2 = robHelper(nums, 1, n - 1, memo2);
        
        return Math.max(max1, max2);
    }
    
    private int robHelper(int[] nums, int i, int end, int[] memo) {
        if (i > end) return 0;
        if (memo[i] != -1) return memo[i];
        
        // Decision: Rob current house OR skip current house
        int rob = nums[i] + robHelper(nums, i + 2, end, memo);
        int skip = robHelper(nums, i + 1, end, memo);
        
        return memo[i] = Math.max(rob, skip);
    }
}

```

---

## Approach 2: Bottom-Up Dynamic Programming (Iteration)

Instead of using recursion and traveling backward from the goal, we can start from the first available house and build our way up using a literal `dp` table.

For any house `i`, your max profit is the maximum of:

1. Skipping house `i`: Look at the max profit up to `i-1`.
2. Robbing house `i`: Take `nums[i]` plus the max profit up to `i-2`.

* **Time Complexity:** $O(N)$ — A simple linear loop over the array twice.
* **Space Complexity:** $O(N)$ — Storing the explicit `dp` array.

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        
        return Math.max(robLinear(nums, 0, n - 2), robLinear(nums, 1, n - 1));
    }
    
    private int robLinear(int[] nums, int start, int end) {
        if (start == end) return nums[start];
        
        int len = end - start + 1;
        int[] dp = new int[len];
        
        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[start + 1]);
        
        for (int i = 2; i < len; i++) {
            // dp[i] = max(skip current house, rob current house)
            dp[i] = Math.max(dp[i - 1], nums[start + i] + dp[i - 2]);
        }
        
        return dp[len - 1];
    }
}

```

---

## Approach 3: Space-Optimized Dynamic Programming (Optimal)

Look closely at the iteration loop in Approach 2. To calculate `dp[i]`, we **only** need `dp[i-1]` and `dp[i-2]`. Any values calculated before that (`dp[i-3]`, `dp[i-4]`, etc.) are completely discarded and never looked at again.

Instead of allocating a whole array, we can just maintain **two variables** to hold the state of the last two houses.

* **Time Complexity:** $O(N)$
* **Space Complexity:** $O(1)$ — Purely constant extra space.

```java
class Solution {
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        
        return Math.max(robLinear(nums, 0, n - 2), robLinear(nums, 1, n - 1));
    }
    
    private int robLinear(int[] nums, int start, int end) {
        int prevMax = 0; // Represents dp[i-2]
        int currMax = 0; // Represents dp[i-1]
        
        for (int i = start; i <= end; i++) {
            int temp = currMax;
            // Balance robbing current house + prevMax, against just keeping currMax
            currMax = Math.max(currMax, prevMax + nums[i]);
            prevMax = temp; // Slide the window forward
        }
        
        return currMax;
    }
}

```

---

## Summary: Why We Optimize

| Phase | Why We Did It | Catch |
| --- | --- | --- |
| **Brute Force (No Memo)** | Explores every single decision branch ($O(2^N)$ time). | TLE (Time Limit Exceeded) on large inputs. |
| **Memoization ($O(N)$ space)** | Stores repeating recursive branches to slash runtime down to $O(N)$. | Vulnerable to **StackOverflowError** on deep call stacks. |
| **Iteration ($O(N)$ space)** | Flattens the logic into loops to eliminate call stack risk. | Spends unnecessary memory allocations on historical data. |
| **Optimal Variable Swap ($O(1)$ space)** | Trashes historical data we no longer require. Keeps runtime blazing fast. | **The Ultimate Solution.** Minimal memory footprints. |