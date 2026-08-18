Here is the complete Java implementation using Bottom-Up (Tabulation) Dynamic Programming.

---

### Java Implementation

```java
public class MatrixChainMultiplication {

    public static int matrixMultiplication(int[] arr) {
        int n = arr.length;
        
        // dp[i][j] stores the minimum multiplication cost for matrices Mi through Mj
        int[][] dp = new int[n][n];

        // Base Case: dp[i][i] is already initialized to 0 (single matrix cost = 0)

        // L is the chain length (from length 2 to n-1)
        for (int L = 2; L < n; L++) {
            for (int i = 1; i <= n - L; i++) {
                int j = i + L - 1;
                
                // Initialize cell to maximum value before finding minimum
                dp[i][j] = Integer.MAX_VALUE;

                // Try all possible split points k between i and j-1
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k+1][j] + (arr[i - 1] * arr[k] * arr[j]);
                    
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                    }
                }
            }
        }

        // Return minimum operations for the full chain from 1 to n-1
        return dp[1][n - 1];
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

### Key Highlights of the Code

1. **Loop Structure:**
* **Outer Loop (`L`):** Controls chain length starting from `2` up to `N-1`.
* **Middle Loop (`i`):** Controls the starting matrix of the current subchain.
* **Inner Loop (`k`):** Iterates over all valid split points between `i` and `j-1`.


2. **Time Complexity:** $\mathcal{O}(N^3)$ due to 3 nested loops.
3. **Space Complexity:** $\mathcal{O}(N^2)$ for the 2D `dp` table.