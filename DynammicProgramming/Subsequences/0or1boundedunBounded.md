Your current solution uses **Top-Down Memoization** (recursion + a 2D array). It works, but it can be heavily optimized.

Before we optimize, there is one important detail in your code: by using `helper(dp, val, wt, i, capacity - wt[i])` instead of `i + 1`, your code is currently solving the **Unbounded Knapsack problem** (where you can pick the same item multiple times).

Below are the optimizations for **both** scenarios, bringing your space complexity down from $O(N \times C)$ to just $O(C)$ using a 1D array.

## Quick Cleanups First

1. **Remove `int max;**`: You initialized it to `Integer.MIN_VALUE` but never used it.
2. **Remove Recursion**: Recursion adds a hidden space cost of $O(N)$ due to the call stack. Moving to a Bottom-Up (Tabulation) approach eliminates this overhead.

---

## Optimization 1: Standard 0/1 Knapsack (Items can only be picked ONCE)

If your goal was the classic 0/1 Knapsack, you only need a single 1D array of size `capacity + 1`. We iterate through the capacities **backwards** to ensure we don't accidentally use the same item more than once in the same iteration.

```java
class Solution {
    public int knapSack(int val[], int wt[], int capacity) {
        // dp[w] will store the maximum value we can get with capacity 'w'
        int[] dp = new int[capacity + 1];
        
        int n = val.length;
        
        for (int i = 0; i < n; i++) {
            // Traverse backwards for 0/1 Knapsack
            for (int w = capacity; w >= wt[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - wt[i]] + val[i]);
            }
        }
        
        return dp[capacity];
    }
}

```

---

## Optimization 2: Unbounded Knapsack (Items can be picked MULTIPLE times)

If you intended to solve Unbounded Knapsack (matching your original recursive logic), the 1D optimization is almost exactly the same, but we iterate **forwards**.

```java
class Solution {
    public int knapSack(int val[], int wt[], int capacity) {
        int[] dp = new int[capacity + 1];
        
        int n = val.length;
        
        for (int i = 0; i < n; i++) {
            // Traverse forwards for Unbounded Knapsack
            for (int w = wt[i]; w <= capacity; w++) {
                dp[w] = Math.max(dp[w], dp[w - wt[i]] + val[i]);
            }
        }
        
        return dp[capacity];
    }
}

```

### Why does the 1D Array work?

In a standard 2D Knapsack DP table, calculating the current row `dp[i]` only ever relies on the **previous row** `dp[i-1]`. Because we don't need the history of all previous rows, we can just overwrite a single 1D array as we process each item.

### Performance Gains:

* **Time Complexity:** $O(N \times \text{capacity})$ (Remains the same mathematically, but runs much faster in practice without recursion overhead).
* **Space Complexity:** $O(\text{capacity})$ (Down from $O(N \times \text{capacity})$ + call stack).