This approach treats every cell `(i, j)` as the **bottom-right corner** of a potential rectangle, precalculates horizontal streaks of `1`s, and looks upward to form rectangles.

---

### 1. Intuition

Any rectangle in a grid has:

* A **bottom-right corner** at some cell `(i, j)`.
* A **height** $h$ extending upwards to row $k$ ($k \le i$).
* A **width** $w$ extending to the left.

If you fix `(i, j)` as the bottom-right corner and expand upward row by row ($k = i, i-1, i-2, \dots$):

1. The **height** of the rectangle is simply $(i - k + 1)$.
2. The **width** cannot exceed the number of consecutive `1`s ending at `(k, j)` in any of the rows between $k$ and $i$.
3. Therefore, the width is limited by the **bottleneck (minimum width)** across all rows from $k$ to $i$:

$$\text{minWidth} = \min(\text{width}[i][j], \text{width}[i-1][j], \dots, \text{width}[k][j])$$


$$\text{Area} = \text{minWidth} \times (i - k + 1)$$



---

### 2. Breakdown of the Code

**Step 1: Compute Horizontal Widths (1D DP per row)**

```java
if (matrix[i][j] == '1') {
    width[i][j] = (j == 0) ? 1 : width[i][j - 1] + 1;
} else {
    width[i][j] = 0;
}

```

* If cell `(i, j)` is `'1'`, it extends the run of consecutive `1`s from the left neighbor by $+1$.
* If cell `(i, j)` is `'0'`, the run breaks, resetting the width to `0`.

---

**Step 2: Upward Scan from `(i, j)**`

```java
int minWidth = width[i][j];
for (int k = i; k >= 0; k--) {
    if (width[k][j] == 0) {
        break; // Cannot extend rectangle past a 0
    }
    minWidth = Math.min(minWidth, width[k][j]);
    int height = i - k + 1;
    maxArea = Math.max(maxArea, minWidth * height);
}

```

* `minWidth` tracks the narrowest row encountered so far as we climb up.
* If `width[k][j] == 0`, a `'0'` is encountered directly above, meaning no all-`1` rectangle can stretch any higher; we terminate the upward loop immediately (`break`).
* At each step, area is `minWidth * height`, updating `maxArea`.

---

### 3. Visual Trace Example

Take this input matrix:

```text
Matrix:
[1, 0, 1, 1]
[1, 1, 1, 1]
[1, 1, 1, 0]

```

**After Step 1 (`width` array computed):**

```text
width:
Row 0: [1, 0, 1, 2]
Row 1: [1, 2, 3, 4]
Row 2: [1, 2, 3, 0]

```

**Tracing Step 2 for cell `(i=2, j=2)` (Bottom-right corner at `width[2][2] = 3`):**

* **$k = 2$ (Row 2 only, Height = 1):**
* `minWidth = width[2][2] = 3`
* $\text{Area} = 3 \times 1 = \mathbf{3}$


* **$k = 1$ (Rows 2 to 1, Height = 2):**
* `minWidth = Math.min(3, width[1][2]) = min(3, 3) = 3`
* $\text{Area} = 3 \times 2 = \mathbf{6}$


* **$k = 0$ (Rows 2 to 0, Height = 3):**
* `minWidth = Math.min(3, width[0][2]) = min(3, 1) = 1`
* $\text{Area} = 1 \times 3 = \mathbf{3}$



Max area found ending at `(2, 2)` is **6** (a $2 \times 3$ rectangle spanning rows 1–2, columns 0–2).

---

### 4. Complexity Analysis

* **Time Complexity:** $O(M^2 \times N)$
* Step 1 takes $O(M \times N)$ to compute widths.
* Step 2 takes $O(M)$ for the upward loop at each of the $M \times N$ cells $\implies O(M^2 \times N)$.
* For $M, N \le 200$, total operations $\approx 200^3 = 8 \times 10^6$, which easily runs well within the typical 1-second limit.


* **Space Complexity:** $O(M \times N)$ to store the `width` matrix.

---

Here is the complete Java code for the **Width DP + Upward Scan** approach:

```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] width = new int[m][n];
        int maxArea = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // Step 1: Precompute continuous streak of 1s in the current row
                if (matrix[i][j] == '1') {
                    width[i][j] = (j == 0) ? 1 : width[i][j - 1] + 1;
                } else {
                    width[i][j] = 0;
                }

                // Step 2: Expand upward treating (i, j) as the bottom-right corner
                int minWidth = width[i][j];
                for (int k = i; k >= 0; k--) {
                    if (width[k][j] == 0) {
                        break; // Cannot extend rectangle past a row with width 0
                    }
                    minWidth = Math.min(minWidth, width[k][j]);
                    int height = i - k + 1;
                    maxArea = Math.max(maxArea, minWidth * height);
                }
            }
        }

        return maxArea;
    }
}

```