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


---

It all comes down to **which version of the DP table you are reading from**—the *current* item's updated state or the *previous* item's state.

When we compress the 2D DP array into a 1D array to save space, running the loop **backward** prevents an item from "re-using" itself in the same turn.

---

## The Core Concept

In 0/1 Knapsack, when considering an item with weight $W$ and value $V$, the transition formula is:

$$dp[\text{cap}] = \max(dp[\text{cap}], dp[\text{cap} - W] + V)$$

* **$dp[\text{cap}]$** is the max value for capacity $\text{cap}$.
* **$dp[\text{cap} - W]$** represents the max value for the *remaining* weight capacity after taking this item.

---

## Forward Loop vs. Backward Loop (Visual Breakdown)

Imagine we have an item with **Weight = 2** and **Value = 10**.

### Scenario A: Forward Loop (`cap = 2` to `W_max`)

If you iterate **forward**, smaller capacities get updated **first**:

```
Initial 1D Array: [0, 0, 0, 0, 0, 0]  (Capacities 0 to 5)

1. At cap = 2: 
   dp[2] = max(dp[2], dp[2 - 2] + 10) = max(0, 0 + 10) = 10
   Array: [0, 0, 10, 0, 0, 0]

2. At cap = 4:
   dp[4] = max(dp[4], dp[4 - 2] + 10) = max(0, dp[2] + 10)
                                      = max(0, 10 + 10) = 20  <-- BUG!
   Array: [0, 0, 10, 0, 20, 0]

```

> **What happened?** When calculating `dp[4]`, it checked `dp[2]`. But `dp[2]` was **already updated** in the current loop to include the item once! Adding $V$ again meant taking the item a **second time**. This is **Unbounded Knapsack**.

---

### Scenario B: Backward Loop (`cap = W_max` down to `2`)

If you iterate **backward**, larger capacities read from values that **haven't been touched yet** in this item's pass:

```
Initial 1D Array: [0, 0, 0, 0, 0, 0]  (Capacities 0 to 5)

1. At cap = 4:
   dp[4] = max(dp[4], dp[4 - 2] + 10) = max(0, dp[2] + 10)
                                      = max(0, 0 + 10) = 10   <-- Fresh value!
   Array: [0, 0, 0, 0, 10, 0]

2. At cap = 2:
   dp[2] = max(dp[2], dp[2 - 2] + 10) = max(0, 0 + 10) = 10
   Array: [0, 0, 10, 0, 10, 0]

```

> **Why did this work?** When `dp[4]` looked at `dp[2]`, `dp[2]` still held the value from the **previous item's row**. It hadn't been updated by the current item yet. Thus, the item was used **at most once**.

---

## Quick Summary Rule

| Loop Direction | What `dp[cap - W]` holds | Problem Type |
| --- | --- | --- |
| **Forward** (`2` $\to$ `W_max`) | **Updated value** (Current item included) | **Unbounded Knapsack** (Pick infinite times) |
| **Backward** (`W_max` $\to$ `2`) | **Old value** (Previous items only) | **0/1 Knapsack** (Pick at most once) |