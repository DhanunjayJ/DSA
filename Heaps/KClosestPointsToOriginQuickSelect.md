Quickselect solves **973. K Closest Points to Origin** in **$O(N)$ average time** by partially partitioning the array around a pivot until the $k$-th element is placed at index $k - 1$. Once the pivot settles at index $k - 1$ (or $k$), all elements to its left are guaranteed to be smaller or equal, giving the $k$ closest points in any order without fully sorting the array.

Squaring the distance ($x^2 + y^2$) avoids floating-point operations with square roots, as relative order remains unchanged.

---

### Java Implementation

```java
import java.util.Arrays;
import java.util.Random;

class Solution {
    private final Random random = new Random();

    public int[][] kClosest(int[][] points, int k) {
        quickSelect(points, 0, points.length - 1, k);
        return Arrays.copyOfRange(points, 0, k);
    }

    private void quickSelect(int[][] points, int left, int right, int k) {
        if (left >= right) return;

        // Choose a random pivot index to avoid O(N^2) worst case
        int pivotIndex = left + random.nextInt(right - left + 1);
        int finalPivotIndex = partition(points, left, right, pivotIndex);

        // We need exactly k elements in indices [0, k - 1]
        if (finalPivotIndex == k) {
            return;
        } else if (finalPivotIndex < k) {
            quickSelect(points, finalPivotIndex + 1, right, k);
        } else {
            quickSelect(points, left, finalPivotIndex - 1, k);
        }
    }

    private int partition(int[][] points, int left, int right, int pivotIndex) {
        int pivotDist = distSq(points[pivotIndex]);
        // Move pivot to the end
        swap(points, pivotIndex, right);

        int storeIdx = left;
        for (int i = left; i < right; i++) {
            if (distSq(points[i]) <= pivotDist) {
                swap(points, storeIdx, i);
                storeIdx++;
            }
        }

        // Move pivot to its final sorted position
        swap(points, storeIdx, right);
        return storeIdx;
    }

    private int distSq(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    private void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }
}

```

---

### How Partitioning Works

1. **Squared Distance Calculation:**

$$\text{distSq}(p) = x^2 + y^2$$


2. **Lomuto Partition:**
* Pick a random pivot index and swap it to `right`.
* Iterate $i$ from `left` to `right - 1`. Whenever `distSq(points[i]) <= pivotDist`, swap it to `storeIdx` and increment `storeIdx`.
* Swap `right` back into `storeIdx`. The pivot is now at its true sorted index `storeIdx`.


3. **Target Index Check:**
* If `finalPivotIndex == k`, the first $k$ positions (`0` through `k - 1`) are already smaller than or equal to `points[k]`. The process terminates early.
* If `finalPivotIndex < k`, recurse on the right subarray `[finalPivotIndex + 1, right]`.
* If `finalPivotIndex > k`, recurse on the left subarray `[left, finalPivotIndex - 1]`.



---

### Complexity Analysis

* **Time Complexity:**
* **Average:** $O(N)$ — like standard Quickselect, the search space halves on average each step: $N + \frac{N}{2} + \frac{N}{4} + \dots \le 2N$.
* **Worst Case:** $O(N^2)$ — mitigated by randomized pivot selection.


* **Space Complexity:**
* $O(\log N)$ average call stack space for recursion (or $O(1)$ auxiliary space if written iteratively).
* Output array takes $O(k)$ space.

---

