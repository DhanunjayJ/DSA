Here is the **Memoized (Top-Down Dynamic Programming)** approach in Java.

Unlike the tabulation (bottom-up) approach which uses nested loops for chain lengths, memoization uses **recursion** to split the problem starting from the full chain `(1, n-1)` down to individual matrices, storing results in a lookup table to avoid redundant calculations.

---

### Java Implementation (Top-Down / Memoization)

```java
import java.util.Arrays;

public class MatrixChainMultiplicationMemo {

    public static int matrixMultiplication(int[] arr) {
        int n = arr.length;
        
        // dp[i][j] will store the minimum cost for multiplying matrices Mi to Mj
        int[][] dp = new int[n][n];

        // Initialize DP table with -1 to mark uncalculated subproblems
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // Solve for the full chain: from 1st matrix (i = 1) to last matrix (j = n - 1)
        return solve(arr, 1, n - 1, dp);
    }

    private static int solve(int[] arr, int i, int j, int[][] dp) {
        // Base Case: A single matrix requires 0 multiplications
        if (i == j) {
            return 0;
        }

        // Return cached result if already computed
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int minCost = Integer.MAX_VALUE;

        // Try every split point k between i and j-1
        for (int k = i; k < j; k++) {
            int currentCost = solve(arr, i, k, dp) 
                            + solve(arr, k + 1, j, dp) 
                            + (arr[i - 1] * arr[k] * arr[j]);

            minCost = Math.min(minCost, currentCost);
        }

        // Store and return the result
        return dp[i][j] = minCost;
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 1, 3, 4};
        System.out.println("Minimum multiplications: " + matrixMultiplication(arr1)); // Output: 20

        int[] arr2 = {1, 2, 3, 4, 3};
        System.out.println("Minimum multiplications: " + matrixMultiplication(arr2)); // Output: 30
    }
}

```

---

### Key Comparison: Tabulation vs. Memoization

| Feature | Memoization (Top-Down) | Tabulation (Bottom-Up) |
| --- | --- | --- |
| **Approach** | Recursive (start at full chain, recurse down) | Iterative (start at length 2, build up to $N$) |
| **Intuition** | Easier to write directly from the recurrence relation | Requires planning loop orders ($L$, $i$, $k$) |
| **Time Complexity** | $\mathcal{O}(N^3)$ | $\mathcal{O}(N^3)$ |
| **Space Complexity** | $\mathcal{O}(N^2) + \mathcal{O}(N)$ recursion stack | $\mathcal{O}(N^2)$ |
