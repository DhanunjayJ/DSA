### Why the Initial Intuition Struggles

Your intuition of finding the nearest neighbor for each element works well if you only need the **absolute smallest** ($k = 1$) distance (which is just the minimum difference between adjacent elements after sorting).

However, for finding the $k$-th smallest distance, this approach runs into two main challenges:

1. **Multiple Pairs per Element:** A single number can form pairs with multiple other numbers, not just its single nearest neighbor.
2. **Time and Space Limits:** With $n \le 10^4$, the total number of pairs is $\frac{n(n - 1)}{2} \approx 5 \times 10^7$. Generating, collecting, or sorting all pair differences will result in a **Time Limit Exceeded (TLE)** or **Memory Limit Exceeded (MLE)**.

---

### The Optimal Approach: Binary Search + Two Pointers

Instead of generating the pairs, we **binary search on the distance itself**:

1. **Sort the array:** Sorting `nums` takes $O(n \log n)$ and lets us quickly count pairs using two pointers.
2. **Search Range:**
* Minimum possible distance: `low = 0`
* Maximum possible distance: `high = nums[n - 1] - nums[0]`


3. **Monotonic Condition:** If a distance $D$ has at least $k$ pairs with distance $\le D$, then the answer might be $D$ or smaller. Otherwise, the answer must be strictly greater than $D$.
4. **Counting Function (Sliding Window / Two Pointers):** For a fixed midpoint distance `mid`, count how many pairs have a distance $\le \text{mid}$ in $O(n)$ time by moving a left pointer `left` and a right pointer `right`.

---

### Java Implementation

```java
import java.util.Arrays;

class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        // Step 1: Sort the array
        Arrays.sort(nums);
        int n = nums.length;

        // Step 2: Set the binary search boundaries for the distance
        int low = 0;
        int high = nums[n - 1] - nums[0];

        // Step 3: Binary search on the answer (distance)
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (countPairsWithDistanceLessThanOrEqualTo(nums, mid) >= k) {
                // If we have at least k pairs <= mid, answer is at most mid
                high = mid;
            } else {
                // If we have fewer than k pairs <= mid, answer must be greater than mid
                low = mid + 1;
            }
        }

        return low;
    }

    // Helper method to count pairs (nums[j] - nums[i] <= mid) in O(n) time
    private int countPairsWithDistanceLessThanOrEqualTo(int[] nums, int targetDistance) {
        int count = 0;
        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            // Shrink window from the left until distance is within targetDistance
            while (nums[right] - nums[left] > targetDistance) {
                left++;
            }
            // All elements from index 'left' to 'right - 1' form valid pairs with nums[right]
            count += right - left;
        }

        return count;
    }
}

```

---

### Complexity Analysis

* **Time Complexity:**
* Sorting: $O(n \log n)$
* Binary search range is from $0$ to $W = \max(nums) - \min(nums)$, running $\log(W)$ iterations.
* In each iteration, counting pairs takes $O(n)$ via two pointers.
* **Total Time Complexity:** $O(n \log n + n \log W)$, which easily completes within the 1-second limit for $n = 10^4$ and $W \le 10^6$.


* **Space Complexity:** $O(1)$ auxiliary space (ignoring the $O(\log n)$ stack space used internally by `Arrays.sort`).