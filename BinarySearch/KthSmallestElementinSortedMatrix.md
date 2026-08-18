Your current solution uses **Binary Search on Answer** combined with binary search on each row, giving a time complexity of $O(n \log n \log(\text{max} - \text{min}))$.

Because **both rows and columns are sorted**, you can optimize the row-by-row binary search into a **Staircase Walk (Two-Pointer)** algorithm. This reduces counting elements $\le \text{mid}$ from $O(n \log n)$ down to **$O(n)$**.

---

## 1. The Optimization Trick

Instead of binary searching every row independently, start at the **bottom-left corner** (or top-right corner) of the matrix:

* If `matrix[row][col] <= mid`, all elements above it in that column are also $\le \text{mid}$. Add `row + 1` to your count and move **right** (`col++`).
* If `matrix[row][col] > mid`, this element is too large, so move **up** (`row--`).

This allows you to count elements $\le \text{mid}$ in linear $O(n)$ time per binary search step.

---

## 2. Optimized Java Code

```java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0];         // Matrix minimum
        int high = matrix[n - 1][n - 1]; // Matrix maximum
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Count elements <= mid using Staircase Walk in O(n) time
            if (countLessEqual(matrix, mid, n) < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return low;
    }

    private int countLessEqual(int[][] matrix, int target, int n) {
        int count = 0;
        int row = n - 1; // Start from bottom-left
        int col = 0;

        while (row >= 0 && col < n) {
            if (matrix[row][col] <= target) {
                count += (row + 1); // Entire column up to 'row' is <= target
                col++;              // Move right
            } else {
                row--;              // Move up
            }
        }
        return count;
    }
}

```

---

## Complexity Comparison

| Complexity | Your Current Approach | Optimized Binary Search | Min-Heap Approach |
| --- | --- | --- | --- |
| **Time Complexity** | $O(n \log n \cdot \log(\text{max} - \text{min}))$ | **$O(n \cdot \log(\text{max} - \text{min}))$** | $O(k \log n)$ |
| **Space Complexity** | $O(1)$ | **$O(1)$** | $O(n)$ |

---

The Min-Heap approach treats the $n \times n$ matrix like $n$ sorted arrays merged together, using the same pattern as **Merge K Sorted Lists**.

---

## 1. How the Min-Heap Approach Works

Since every row in the matrix is sorted left-to-right:

1. **Initialize the Heap:** Push the first element of each row into a Min-Heap. The heap size is at most $n$. Store `(value, row, col)`.
2. **Extract and Replace:** Pop the smallest element from the heap. Repeat this process $k$ times:
* When you pop `(val, r, c)`, if `c + 1 < n`, push its neighbor `matrix[r][c + 1]` into the heap.


3. **Result:** The $k$-th popped element is the answer.

---

## 2. Java Implementation

```java
import java.util.PriorityQueue;

class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        // Min-Heap storing {value, row, col}
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

        // Step 1: Add the first element of each row (up to n)
        for (int i = 0; i < Math.min(n, k); i++) {
            minHeap.offer(new int[]{matrix[i][0], i, 0});
        }

        // Step 2: Pop k - 1 elements
        for (int i = 0; i < k - 1; i++) {
            int[] current = minHeap.poll();
            int row = current[1];
            int col = current[2];

            // Push the next element in the same row
            if (col + 1 < n) {
                minHeap.offer(new int[]{matrix[row][col + 1], row, col + 1});
            }
        }

        // The k-th element
        return minHeap.peek()[0];
    }
}

```

---

## 3. Min-Heap vs. Binary Search: When to Use Which?

| Metric | Min-Heap Approach | Binary Search + Staircase Walk |
| --- | --- | --- |
| **Time Complexity** | **$O(k \log(\min(n, k)))$** | **$O(n \log(\text{max} - \text{min}))$** |
| **Space Complexity** | $O(\min(n, k))$ | **$O(1)$** |
| **Best Used When** | $k$ is small ($k \ll n^2$) or matrix contains floating-point numbers | $k$ is large ($k \approx \frac{n^2}{2}$) or matrix is strictly integers |

### Key Takeaways:

1. **When $k$ is small ($k < n$ or $k \ll n^2$):**
* **Min-Heap wins.** Since $k$ is small, $O(k \log n)$ requires very few operations regardless of how far apart the minimum and maximum values are in the matrix.


2. **When $k$ is large ($k \approx n^2$):**
* **Binary Search wins.** $O(k \log n)$ degenerates to $O(n^2 \log n)$, whereas Binary Search remains bounded at $O(n \log(\text{range}))$.


3. **Data Type Constraints:**
* If the matrix contains **floats/doubles** or extremely large values where `high - low` causes numerical precision issues or overflow, **Min-Heap is safer** because it relies solely on comparisons, not arithmetic midpoints.