Here is the step-by-step evolution for **Geek's Training**, taking it from the recursive top-down approach all the way to the optimal $O(1)$ space solution.

---

## 1. Top-Down Approach (Recursion + Memoization)

In the top-down approach, we start from the last day ($n-1$) and work our way backward to Day 0.

To avoid picking the same activity consecutively, our recursive function needs to know:

1. What **day** we are currently evaluating.
2. What **activity** we performed on the *next* day (so we can avoid picking it today). We pass `3` initially to mean "no activity has been picked yet."

* **Time Complexity:** $O(N \times 4) = O(N)$ — There are $N \times 4$ unique states, and each state takes $O(1)$ time to compute.
* **Space Complexity:** $O(N \times 4) + O(N) = O(N)$ — For the 2D `memo` table and the recursion call stack.

```java
import java.util.Arrays;

class Solution {
    public int maximumPoints(int[][] mat) {
        int n = mat.length;
        // memo[day][last_activity] -> last_activity can be 0, 1, 2, or 3 (none)
        int[][] memo = new int[n][4];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        
        return helper(n - 1, 3, mat, memo);
    }

    private int helper(int day, int lastActivity, int[][] mat, int[][] memo) {
        // Base Case: If we reach day 0, pick the max available activity that isn't lastActivity
        if (day == 0) {
            int maxPoints = 0;
            for (int i = 0; i < 3; i++) {
                if (i != lastActivity) {
                    maxPoints = Math.max(maxPoints, mat[0][i]);
                }
            }
            return maxPoints;
        }

        // Return cached result if already calculated
        if (memo[day][lastActivity] != -1) {
            return memo[day][lastActivity];
        }

        int maxPoints = 0;
        // Try all 3 activities for the current day
        for (int i = 0; i < 3; i++) {
            if (i != lastActivity) {
                // Points gained today + best points from previous days
                int points = mat[day][i] + helper(day - 1, i, mat, memo);
                maxPoints = Math.max(maxPoints, points);
            }
        }

        return memo[day][lastActivity] = maxPoints;
    }
}

```

---

## 2. Bottom-Up Approach (Tabulation / Iteration)

Instead of starting from the last day, we start from Day 0 and build a table forward.

We define `dp[i][j]` as the maximum merit points earned up to Day `i` given that we performed Activity `j` on Day `i`.

* **Time Complexity:** $O(N)$ — A single linear loop running through the days.
* **Space Complexity:** $O(N \times 3) = O(N)$ — For storing the `dp` table.

```java
class Solution {
    public int maximumPoints(int[][] mat) {
        int n = mat.length;
        int[][] dp = new int[n][3];

        // Base case: On Day 0, the maximum points are just the activities themselves
        dp[0][0] = mat[0][0];
        dp[0][1] = mat[0][1];
        dp[0][2] = mat[0][2];

        // Fill the table for subsequent days
        for (int i = 1; i < n; i++) {
            // Today running (0) -> Max of yesterday fighting (1) or learning (2)
            dp[i][0] = mat[i][0] + Math.max(dp[i - 1][1], dp[i - 1][2]);
            
            // Today fighting (1) -> Max of yesterday running (0) or learning (2)
            dp[i][1] = mat[i][1] + Math.max(dp[i - 1][0], dp[i - 1][2]);
            
            // Today learning (2) -> Max of yesterday running (0) or fighting (1)
            dp[i][2] = mat[i][2] + Math.max(dp[i - 1][0], dp[i - 1][1]);
        }

        // On the final day, find the maximum value among the three choices
        return Math.max(dp[n - 1][0], Math.max(dp[n - 1][1], dp[n - 1][2]));
    }
}

```

---

## 3. Space-Optimized Approach (Optimal)

If you look closely at the tabulation approach, calculating `dp[i]` only requires reading data from `dp[i-1]`. Rows like `dp[i-2]`, `dp[i-3]`, etc., are never touched again.

We can completely eliminate the 2D array and track only an array of size 3 representing **yesterday's points**.

* **Time Complexity:** $O(N)$
* **Space Complexity:** **$O(1)$** — Pure constant extra space.

```java
class Solution {
    public int maximumPoints(int[][] mat) {
        int n = mat.length;
        
        // Array to store the results of the previous day
        int[] prev = new int[3];
        prev[0] = mat[0][0];
        prev[1] = mat[0][1];
        prev[2] = mat[0][2];

        for (int i = 1; i < n; i++) {
            int[] temp = new int[3];
            
            // Compute today's values using yesterday's (prev) data
            temp[0] = mat[i][0] + Math.max(prev[1], prev[2]);
            temp[1] = mat[i][1] + Math.max(prev[0], prev[2]);
            temp[2] = mat[i][2] + Math.max(prev[0], prev[1]);
            
            // Slide our window forward: today becomes the previous day for the next iteration
            prev = temp;
        }

        return Math.max(prev[0], Math.max(prev[1], prev[2]));
    }
}

```

---

## Why We Optimize

| Approach | Advantage | Bottleneck |
| --- | --- | --- |
| **Memoization** | Intuitive to write if you can think of the decision tree. | High recursive stack overhead. Can cause stack overflow errors if $N$ is near $10^5$. |
| **Tabulation** | Safe from stack overflows because it uses iterative loops. | Uses an $O(N)$ matrix size, wasting space on rows that are already calculated and discarded. |
| **Space-Optimized** | Highly performant. Drops space to **$O(1)$**. | **The Best Approach.** Highly preferred by interviewers because it shows deep knowledge of memory lifecycles. |