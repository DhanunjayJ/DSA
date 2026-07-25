https://www.geeksforgeeks.org/problems/minimum-sum-partition3317/1

Why we need to stop at target sum?

The short answer is: **Mathematical Symmetry.** Even though we no longer care about the *number* of elements in each subset (the `count` variable), the **sums** of the two subsets are perfectly mirrored around `TotalSum / 2`.

Here is exactly why we only need to check up to half the sum.

---

### The Mathematical Proof

When you split an array into any two subsets (Subset 1 and Subset 2), they must add up to the total sum of the array.

* `Sum1 + Sum2 = TotalSum`
* Therefore, `Sum2 = TotalSum - Sum1`

Because of this rule, every time you form a `Sum1`, you are **automatically** forming its exact opposite, `Sum2`, with the leftover elements.

### A Concrete Example

Let's imagine an array: `arr = [1, 2, 7]`

* **Total Sum** = 10
* **Target (Total / 2)** = 5

Let's look at every possible sum we can make for Subset 1, and see what happens to Subset 2:

| If we pick Subset 1 to have... | Subset 1 Sum | Subset 2 (the leftovers) gets... | Subset 2 Sum | Absolute Difference |
| --- | --- | --- | --- | --- |
| Nothing `[]` | **0** | `[1, 2, 7]` | **10** | `|0 - 10| = 10` |
| `[1]` | **1** | `[2, 7]` | **9** | `|1 - 9| = 8` |
| `[2]` | **2** | `[1, 7]` | **8** | `|2 - 8| = 6` |
| `[1, 2]` | **3** | `[7]` | **7** | `|3 - 7| = 4` |
| --- *MIDPOINT (Total/2 = 5)* --- |  |  |  |  |
| `[7]` | **7** | `[1, 2]` | **3** | `|7 - 3| = 4` |
| `[1, 7]` | **8** | `[2]` | **2** | `|8 - 2| = 6` |
| `[2, 7]` | **9** | `[1]` | **1** | `|9 - 1| = 8` |
| `[1, 2, 7]` | **10** | Nothing `[]` | **0** | `|10 - 0| = 10` |

### The "Aha!" Moment

Look at the table above. Once we cross the midpoint (5), **the differences start repeating exactly.** When Subset 1 has a sum of `3`, the difference is `4`.
When Subset 1 has a sum of `7`, the difference is *still* `4`.

Why? Because the subsets just swapped places!

* In the first case, we actively picked `[1, 2]` to be our Subset 1, leaving `[7]` behind.
* In the second case, we actively picked `[7]` to be our Subset 1, leaving `[1, 2]` behind.

The absolute difference between the two groups is identical.

### Why We Stop at `TotalSum / 2`

If you allow your DP array to go all the way up to `TotalSum` (10), your code will still give you the correct answer. However, you will be doing **double the work** and using **double the memory** just to calculate the exact same absolute differences in reverse.

By forcing the recursion to stop at `TotalSum / 2`, we are essentially saying: *"Hey, we already calculated the difference for the smaller half. We don't need to calculate it again for the bigger half."* Does that make sense why we prune the state tree there?

---

Ah! I see exactly what is happening based on that test case. You have fallen victim to a very classic platform difference!

We were previously discussing the LeetCode problem **"Partition Array Into Two Arrays to Minimize Sum Difference"**, which has a strict rule: **Both subsets MUST have exactly the same number of elements ($n$).** That's why we were tracking `count == n`.

However, the problem you are currently submitting to (which looks like GeeksForGeeks' **"Minimum sum partition"**) **does NOT require the subsets to be of equal size!** Let's look at your test case:
`arr = [9, 2, 2, 3]`

* Total Sum = 16
* **Your code's output (6):** Because of our `count == n` rule, your code forced a split of 2 elements vs 2 elements. It picked `[9, 2]` (sum 11) and `[3, 2]` (sum 5). Difference = $\vert{}11 - 5\vert{} = 6$.
* **The correct output (2):** If we don't care about the size of the subsets, we can put `[9]` in one subset (size 1) and `[2, 2, 3]` in the other subset (size 3). Sums are 9 and 7. Difference = $\vert{}9 - 7\vert{} = 2$!

---

### The Fix: Drop the `count` variable!

Since we don't care how many elements are in the subsets, we can **completely remove the `count` variable** from our DP state.

Our new DP state is just a 2D array: `dp[index][sum]`.

Here is the corrected Top-Down code for this specific problem:

```java
import java.util.Arrays;

class Solution {
    int[][] dp;
    int totalSum;
    
    public int minDifference(int arr[]) {
        totalSum = 0;
        for (int num : arr) totalSum += num;
        
        // We only need to track sums up to totalSum / 2
        int target = totalSum / 2;
        
        // DP dimensions: [index][current sum]
        dp = new int[arr.length][target + 1];
        
        for (int i = 0; i < arr.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        
        return helper(0, 0, arr, target);
    }
    
    public int helper(int i, int sum, int[] arr, int target) {
        // 1. Base Case: Reached the end of the array
        if (i == arr.length) {
            int otherSum = totalSum - sum;
            return Math.abs(sum - otherSum);
        }
        
        // 2. Memoization Check
        if (dp[i][sum] != -1) return dp[i][sum];
        
        // 3. Decision 1: Pick the element
        // (We only pick it if adding it doesn't exceed our target half-sum)
        int pick = (int) 1e9;
        if (sum + arr[i] <= target) {
            pick = helper(i + 1, sum + arr[i], arr, target);
        }
        
        // 4. Decision 2: Skip the element
        int skip = helper(i + 1, sum, arr, target);
        
        // 5. Save and return the minimum difference
        dp[i][sum] = Math.min(pick, skip);
        return dp[i][sum];
    }
}

```

### Notice the slight change in pruning!

Instead of checking `sum > target` at the very beginning of the function and returning `1e9`, I moved that check to the `pick` decision (`if (sum + arr[i] <= target)`).

If we try to pick `9`, it sees that `0 + 9 > 8`, so it just skips the `pick` recursive call entirely and only explores the `skip` branch. This is much cleaner and prevents out-of-bounds exceptions in our DP array!

Try running this version. Once it passes, let me know, and we'll translate this beautiful 2D state into a **Bottom-Up** tabulated version!

---

Min sum Parition LeetCode Problem with Constarints as GFG
using DP

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
