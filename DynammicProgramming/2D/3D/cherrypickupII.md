This problem (often called **Cherry Pickup II** on LeetCode) is a classic 3D Dynamic Programming problem.

The most critical realization here is that **both robots must move down synchronously, row by row.** If you try to calculate the path for Robot 1 first and then try to calculate the path for Robot 2, you will fail because the choices Robot 1 makes directly affect which cells Robot 2 can pick up without duplication. Moving them together ensures we can handle the "same cell" condition easily.

---

## 1. Defining the DP State

Since both robots move down one row at a time simultaneously, they will always be on the **same row `i**` at any given step.

Therefore, to track the state of both robots, we need 3 variables:

* `i`: The current row number for both robots.
* `j1`: The column position of Robot 1.
* `j2`: The column position of Robot 2.

Our DP function state will be: **`dfs(i, j1, j2)`**, which returns the maximum chocolates collected from row `i` down to the bottom row.

---

## 2. The Move Transitions (Overlapping Subproblems)

From row `i`, each robot can move to 3 possible columns in row `i + 1`: `j - 1`, `j`, or `j + 1`.
Because there are 2 robots, there are $3 \times 3 = 9$ total combinations of movements they can make to transition to the next row!

For each of the 9 paths, the transition is:


$$\text{dfs}(i + 1, j1 + dj1, j2 + dj2)$$


where $dj1, dj2 \in \{-1, 0, 1\}$.

---

## 3. Handling the Rules

* **Out of Bounds:** If `j1` or `j2` drop below `0` or go past `m - 1`, return a massive negative number (like `-1e9`) to signal an invalid path.
* **Same Cell Condition:** * If `j1 == j2` (they land on the same cell), they only collect the chocolates once: `grid[i][j1]`.
* If `j1 != j2`, they collect both: `grid[i][j1] + grid[i][j2]`.


* **Base Case:** When `i == n - 1` (the bottom row), they stop moving and just collect whatever is on that final row.

---

## Top-Down Memoization Solution

Because $n, m \le 70$, a 3D array of size `[70][70][70]` is perfectly optimal and tiny enough to fit comfortably in memory.

```java
import java.util.Arrays;

class Solution {
    public int solve(int n, int m, int[][] grid) {
        // 3D DP array initialized to -1
        int[][][] dp = new int[n][m][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        
        // Robot 1 starts at (0, 0), Robot 2 starts at (0, m-1)
        return memoize(0, 0, m - 1, grid, dp, n, m);
    }
    
    private int memoize(int i, int j1, int j2, int[][] grid, int[][][] dp, int n, int m) {
        // 1. Boundary Check
        if (j1 < 0 || j1 >= m || j2 < 0 || j2 >= m) {
            return (int) -1e9; // Return a large negative value for invalid paths
        }
        
        // 2. Base Case: Reached the bottom row
        if (i == n - 1) {
            if (j1 == j2) return grid[i][j1];
            else return grid[i][j1] + grid[i][j2];
        }
        
        // 3. Memoization Check
        if (dp[i][j1][j2] != -1) return dp[i][j1][j2];
        
        int maxChocolates = (int) -1e9;
        
        // 4. Explore all 9 combinations of moves for both robots
        for (int dj1 = -1; dj1 <= 1; dj1++) {
            for (int dj2 = -1; dj2 <= 1; dj2++) {
                int value = 0;
                
                // Add chocolates of current cell(s)
                if (j1 == j2) {
                    value = grid[i][j1];
                } else {
                    value = grid[i][j1] + grid[i][j2];
                }
                
                // Recurse to the next row
                value += memoize(i + 1, j1 + dj1, j2 + dj2, grid, dp, n, m);
                
                maxChocolates = Math.max(maxChocolates, value);
            }
        }
        
        // Save to DP table and return
        dp[i][j1][j2] = maxChocolates;
        return dp[i][j1][j2];
    }
}

```

---

## Complexity Analysis

* **Time Complexity:** $O(N \times M^2 \times 9) \rightarrow \mathbf{O(N \times M^2)}$. There are $N \times M \times M$ unique states, and each state performs a constant $9$ operations. With constraints up to $70$, $70^3 \approx 343,000$ operations, which executes in a few milliseconds.
* **Space Complexity:** $\mathbf{O(N \times M^2)}$ for the 3D DP table storage matrix + $O(N)$ auxiliary stack space for recursion depth.