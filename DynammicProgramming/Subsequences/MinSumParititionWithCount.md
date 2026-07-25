With these constraints, we can throw the Meet in the Middle approach out the window and go straight back to **Dynamic Programming (DP)**.

The constraint $1 \le n \times \text{sum} \le 10^6$ is the ultimate clue. Let's break down why this constraint changes everything and what the time complexity looks like.

---

## 1. Why Standard DP Works Now

In the previous constraint version, the sum could be massive (up to $1.5 \times 10^8$), which meant a DP table using `sum` as a dimension would trigger a Memory Limit Exceeded (MLE) error.

Here, the *entire product* of $n$ and the total sum is capped at $10^6$.

* If $n$ is large (e.g., $10^4$), the total sum must be small (e.g., $10^2$).
* If the total sum is large (e.g., $10^5$), $n$ must be small (e.g., $10$).

Because their product is so small, a 2D DP table of size `[n][sum]` will only take around $10^6$ integers of space, which easily fits in memory (only a few megabytes).

---

## 2. Redefining the DP State

We want to know what subset sums are possible using exactly $c$ elements.

* **State Variables:** 1. `i`: The current index in the array (from $0$ to $2n$).
2. `c`: The number of elements picked so far (from $0$ to $n$).
3. `s`: The current accumulated sum.

Since we can optimize the index `i` away using a space-optimized bottom-up approach, our core DP table only needs two dimensions: `dp[c][s]`.

> **`dp[c][s]`** = A boolean value (`true`/`false`) representing whether it is possible to pick exactly `c` elements that add up to a sum of `s`.

---

## 3. The DP Transitions (Pick / No-Pick)

For every element `num` in your array, you loop backwards through the counts and sums to update your table:

$$\text{dp}[c][s] = \text{dp}[c][s] \lor \text{dp}[c - 1][s - \text{num}]$$

This means you can achieve a sum of `s` using `c` elements if:

1. You could *already* make a sum of `s` using `c` elements (without using the current `num`).
2. You could make a sum of `s - num` using `c - 1` elements, and now you are picking the current `num`.

---

## 4. Time and Space Complexity Analysis

Let's look at the loops required for this bottom-up DP:

```java
// Total elements = 2 * n
for (int num : nums) { 
    for (int c = n; c >= 1; c--) {
        for (int s = totalSum / 2; s >= num; s--) {
            // DP transition here
        }
    }
}

```

### Time Complexity

* The outer loop runs $2n$ times.
* The middle loop runs $n$ times.
* The inner loop runs $\frac{\text{sum}}{2}$ times.

Multiplying these together:


$$\text{Total Operations} \approx 2n \times n \times \frac{\text{sum}}{2} = n \times (n \times \text{sum})$$

We know from the problem constraints that $(n \times \text{sum}) \le 10^6$. Since $nums.length = 2n$, and the maximum possible value of $arr[i]$ is $10^4$, $n$ itself cannot exceed a few thousands. Therefore, the total operations will comfortably be around $10^7$ to $10^8$, which executes well within the 1-second time limit.

### Space Complexity

Our space-optimized DP matrix size is $n \times \frac{\text{sum}}{2}$.


$$\text{Space Complexity} = O(n \times \text{sum})$$


Given the constraint, this is bounded by $10^6$ booleans, which takes **less than 1 MB** of memory.

---

This is a fantastic approach to mastering Dynamic Programming! Breaking a problem down from Top-Down (Memoization) $\rightarrow$ Bottom-Up (Tabulation) $\rightarrow$ Space Optimization is the exact path to fully understanding how DP works under the hood.

As requested, we will focus **only on the Top-Down (Memoization) approach** in this message. We will tackle the others in the follow-ups.

---

### 1. Defining the DP State (Top-Down)

To write our recursive function, we need to know what variables are changing at each step.

1. **Index (`i`)**: Which element in the array are we currently looking at? (Ranges from $0$ to $2n-1$).
2. **Count (`c`)**: How many elements have we picked for our first subset so far? (We must pick exactly $n$ elements).
3. **Current Sum (`s`)**: What is the sum of the elements we have picked so far?

Our recursive function `helper(i, c, s)` will return the **minimum absolute difference** achievable from this current state.

### 2. A Crucial Optimization: The `TotalSum / 2` Trick

Because all elements are strictly positive ($arr[i] > 0$), the sum of our picked elements will only ever increase.

If the total sum of the array is `100`, the perfect target for a subset is `50`.

* If we pick elements and our current sum becomes `55`, we know the other subset will have a sum of `45`.
* Because we explore *all* valid combinations, we will eventually explore the path that picks the `45` subset directly!

Therefore, to save memory and time, **we only need to explore paths where our current sum is $\le \frac{\text{totalSum}}{2}$**. If a branch exceeds this half-sum, we can immediately stop exploring it (prune it) because its "mirror" subset will be explored in another branch.

---

### 3. Top-Down Java Code

```java
import java.util.Arrays;

class Solution {
    int[][][] memo;
    int totalSum = 0;
    int n;

    public int minimumDifference(int[] nums) {
        this.n = nums.length / 2;
        
        // Calculate the total sum of the array
        for (int num : nums) {
            totalSum += num;
        }

        // The maximum sum we ever need to track is totalSum / 2
        int target = totalSum / 2;
        
        // Initialize our 3D memoization table
        // Dimensions: [index][count picked][current sum]
        memo = new int[nums.length][n + 1][target + 1];
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        // Start from index 0, 0 elements picked, current sum of 0
        return helper(nums, 0, 0, 0, target);
    }

    private int helper(int[] nums, int i, int c, int s, int target) {
        // Base Case 1: We successfully picked exactly 'n' elements
        if (c == n) {
            int otherSum = totalSum - s;
            return Math.abs(otherSum - s);
        }

        // Base Case 2: We ran out of elements OR our sum exceeded half
        if (i == nums.length || s > target) {
            return (int) 1e9; // Return a huge number to invalidate this path
        }

        // Memoization Check: If we've calculated this state before, return it
        if (memo[i][c][s] != -1) {
            return memo[i][c][s];
        }

        // Decision 1: Pick the current element
        // (Increments index, increments count, adds to sum)
        int pick = helper(nums, i + 1, c + 1, s + nums[i], target);

        // Decision 2: Skip the current element
        // (Increments index, count stays the same, sum stays the same)
        int skip = helper(nums, i + 1, c, s, target);

        // Save the best (minimum) difference in our memo table and return it
        memo[i][c][s] = Math.min(pick, skip);
        return memo[i][c][s];
    }
}

```

---

### 4. Complexity Analysis (Top-Down)

* **Time Complexity:** $O(n^2 \times \text{totalSum})$
There are $2n$ possible values for the index, $n$ possible values for the count, and $\frac{\text{totalSum}}{2}$ possible values for the sum. $2n \times n \times \frac{\text{totalSum}}{2}$ gives us our state space. Because the work done inside each state is $O(1)$ (just making two recursive calls), the time complexity scales directly with the number of states.
* **Space Complexity:** $O(n^2 \times \text{totalSum})$
This is the exact size of the 3D `memo` array we created. There is also an implicit recursion stack space of $O(n)$ which is negligible compared to the 3D array.

---

Take a moment to digest how the `helper` function builds the answer by combining the results of the `pick` and `skip` branches.

Whenever you are ready to convert this recursive logic into a Bottom-Up iterative table, let me know! Are you comfortable with how we define the base cases before we move on to tabulation?

---

This is a great exercise. Converting a 3D state into a 2D state is one of the most satisfying "level-ups" in Dynamic Programming.

Let's start by visualizing the full 3D table for the **LeetCode version** (where we must pick exactly $n$ elements), and then watch how we can compress it into 2D.

---

## Part 1: The 3D Bottom-Up Approach (Unoptimized)

In a pure translation from Top-Down to Bottom-Up, we keep all three dimensions: **Index**, **Count**, and **Sum**.

* `dp[i][c][s]` = `true` if we can achieve a sum of exactly `s` using exactly `c` elements, considering only the first `i` elements of the array.

### The 3D Code

```java
class Solution {
    public int minimumDifference(int[] nums) {
        int totalLen = nums.length;
        int n = totalLen / 2;
        int totalSum = 0;
        for (int num : nums) totalSum += num;
        
        int target = totalSum / 2;
        
        // dp[index][count][sum]
        boolean[][][] dp = new boolean[totalLen + 1][n + 1][target + 1];
        
        // Base case: Using 0 elements from the first i items, we can make a sum of 0
        for (int i = 0; i <= totalLen; i++) {
            dp[i][0][0] = true;
        }
        
        // Iterate through the array elements (i represents the first i elements, so 1-indexed)
        for (int i = 1; i <= totalLen; i++) {
            int currentNum = nums[i - 1]; // 0-indexed array
            
            // We can pick at most 'i' elements, up to our limit of 'n'
            for (int c = 1; c <= Math.min(i, n); c++) {
                
                for (int s = 0; s <= target; s++) {
                    // Option 1: SKIP the current element
                    // Just carry over the result from the previous row
                    dp[i][c][s] = dp[i - 1][c][s];
                    
                    // Option 2: PICK the current element
                    // Check if our sum is large enough to include currentNum
                    if (s >= currentNum) {
                        dp[i][c][s] = dp[i][c][s] || dp[i - 1][c - 1][s - currentNum];
                    }
                }
            }
        }
        
        // Find the largest possible sum closest to target using exactly 'n' elements
        // We check the final row of our table (i = totalLen)
        for (int s = target; s >= 0; s--) {
            if (dp[totalLen][n][s]) {
                int otherSum = totalSum - s;
                return Math.abs(otherSum - s);
            }
        }
        
        return 0;
    }
}

```

### 3D Complexity

* **Time Complexity:** $O(n^2 \times \text{target})$
* **Space Complexity:** $O(n^2 \times \text{target})$ because our 3D array has sizes `2n * n * target`. This can take up quite a bit of memory.

---

## Part 2: The 2D Space-Optimized Approach

Look very closely at these two lines from the 3D code:

```java
dp[i][c][s] = dp[i - 1][c][s];
dp[i][c][s] = dp[i][c][s] || dp[i - 1][c - 1][s - currentNum];

```

Notice the pattern? **Row `i` only ever needs data from row `i - 1`.** Once we finish calculating row `i=5`, we never need to look at row `i=0`, `1`, `2`, or `3` ever again.

Because we only need the *previous* state to calculate the *current* state, we can completely erase the `i` dimension. We just use a 2D array: `dp[c][s]`.

### The "Loop Backwards" Rule

When we drop the `i` dimension, we are overwriting our array in place.
If we loop forward through the counts and sums, we might accidentally use the *current* number we just added to calculate the next state in the exact same step (essentially using the same number twice).

To prevent this, we **must iterate `c` and `s` backwards**. This ensures that when we look up `dp[c - 1][s - currentNum]`, we are looking at the *old* data from the previous step, simulating `i - 1` perfectly!

### The 2D Code

```java
class Solution {
    public int minimumDifference(int[] nums) {
        int totalLen = nums.length;
        int n = totalLen / 2;
        int totalSum = 0;
        for (int num : nums) totalSum += num;
        
        int target = totalSum / 2;
        
        // Dropped the 'i' dimension. dp[count][sum]
        boolean[][] dp = new boolean[n + 1][target + 1];
        
        // Base case
        dp[0][0] = true;
        
        for (int num : nums) {
            
            // Loop backwards to simulate looking at the 'previous' row
            for (int c = n; c >= 1; c--) {
                for (int s = target; s >= num; s--) {
                    
                    // We only need the PICK logic now.
                    // The SKIP logic happens automatically because the array 
                    // already holds the values from the previous step!
                    dp[c][s] = dp[c][s] || dp[c - 1][s - num];
                    
                }
            }
        }
        
        // Look at the final state of our 2D array for the answer
        for (int s = target; s >= 0; s--) {
            if (dp[n][s]) {
                int otherSum = totalSum - s;
                return Math.abs(otherSum - s);
            }
        }
        
        return 0;
    }
}

```

### 2D Complexity

* **Time Complexity:** $O(n^2 \times \text{target})$. The number of operations remains exactly the same.
* **Space Complexity:** $O(n \times \text{target})$. We have successfully sliced an entire dimension off our memory footprint.

