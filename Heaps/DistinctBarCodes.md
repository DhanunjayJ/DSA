### Intuition Behind the PriorityQueue (Max-Heap) Approach

Instead of using the two-pass even/odd slot trick, you can solve this problem greedily by always placing the **top two most frequent** available elements side by side.

1. **Count frequencies** of all barcodes using a `HashMap` or array.
2. **Push pairs** `(count, barcode)` into a **Max-Heap** prioritized by frequency.
3. In each step:
* Pop the two most frequent elements: `first` and `second`.
* Append `first` then `second` to the result array.
* Decrement their counts.
* If they still have remaining counts, push them back into the heap.


4. If one element is left at the end (when array length is odd), pop and place it in the final slot.

Because you always pick two *different* elements in each step, you are guaranteed that no two adjacent elements are identical.

---

### Java Implementation

```java
import java.util.*;

class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        // 1. Build frequency map
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int code : barcodes) {
            freqMap.put(code, freqMap.getOrDefault(code, 0) + 1);
        }

        // 2. Max-Heap storing [barcode, frequency], ordered by frequency desc
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            maxHeap.offer(new int[]{entry.getKey(), entry.getValue()});
        }

        int n = barcodes.length;
        int[] ans = new int[n];
        int idx = 0;

        // 3. Process the top 2 elements together
        while (maxHeap.size() >= 2) {
            int[] first = maxHeap.poll();
            int[] second = maxHeap.poll();

            // Place first and second sequentially
            ans[idx++] = first[0];
            ans[idx++] = second[0];

            first[1]--;
            second[1]--;

            // Push back if they still have occurrences left
            if (first[1] > 0) {
                maxHeap.offer(first);
            }
            if (second[1] > 0) {
                maxHeap.offer(second);
            }
        }

        // 4. If an odd-numbered element remains, place it
        if (!maxHeap.isEmpty()) {
            ans[idx] = maxHeap.poll()[0];
        }

        return ans;
    }
}

```

---

### Step-by-Step Trace: `barcodes = [1, 1, 2, 2, 3]`

1. **Frequencies:** `1: 2`, `2: 2`, `3: 1`
2. **Heap contents:** `[(1, 2), (2, 2), (3, 1)]`
3. **Round 1:**
* Pop `(1, 2)` and `(2, 2)`.
* Add to result: `ans[0] = 1`, `ans[1] = 2`.
* Remaining counts: `(1, 1)` and `(2, 1)`. Push both back.
* Heap: `[(1, 1), (2, 1), (3, 1)]`


4. **Round 2:**
* Pop `(1, 1)` and `(2, 1)`.
* Add to result: `ans[2] = 1`, `ans[3] = 2`.
* Counts hit 0, neither is re-added.
* Heap: `[(3, 1)]`


5. **Final Step:**
* Heap has 1 element left: `(3, 1)`.
* Add to result: `ans[4] = 3`.



**Final Output:** `[1, 2, 1, 2, 3]` (valid arrangement).

---

### Complexity Analysis

| Metric | Complexity | Explanation |
| --- | --- | --- |
| **Time Complexity** | **$O(N \log K)$** | Where $N$ is array length and $K$ is the number of unique elements ($K \le N$). Heap operations take $O(\log K)$. |
| **Space Complexity** | **$O(K)$** | Space needed for the frequency map and heap entries. |


---

### Why Your Approach Fails

Your current code pairs the smallest non-zero element `i` with the largest non-zero element `j`. This fails for two main reasons:

1. **Colliding Pointers:** When only one unique number is left with a remaining count, or when `i` meets `j`, alternating between `i` and `j` places identical numbers right next to each other (e.g., `ans[k] = i` and then `ans[k+1] = j` where `i == j`).
2. **Ignoring Dominant Frequencies:** In problems like LeetCode 1054 (*Distant Barcodes*), the most critical constraint is the element with the **maximum frequency**. If you do not prioritize placing the most frequent element first, it will run out of available alternating spots and end up clustered at the end.

---

### The Correct Greedy Strategy

1. Find the barcode that appears the most times.
2. Fill **all even indices** (`0, 2, 4, ...`) first, starting with the most frequent element.
3. Once the even indices reach the end of the array, wrap around and start filling **all odd indices** (`1, 3, 5, ...`).
4. Because the problem guarantees a valid rearrangement exists, the most frequent element occurs at most $\lceil n / 2 \rceil$ times. Placing it on even indices ensures no two identical barcodes can ever be adjacent.

---

### Optimized Solution ($O(N)$ Time & $O(1)$ Extra Aux Space)

Since values in `barcodes` are bounded between `1` and `10000`, a frequency array runs in linear time without needing a PriorityQueue:

```java
class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        int[] count = new int[10001];
        int maxFreq = 0;
        int maxVal = 0;

        // 1. Count frequencies and track the most frequent value
        for (int code : barcodes) {
            count[code]++;
            if (count[code] > maxFreq) {
                maxFreq = count[code];
                maxVal = code;
            }
        }

        int n = barcodes.length;
        int[] ans = new int[n];
        int idx = 0;

        // 2. Place the most frequent barcode at even indices first
        while (count[maxVal] > 0) {
            ans[idx] = maxVal;
            idx += 2;
            count[maxVal]--;
        }

        // 3. Fill the remaining barcodes
        for (int i = 0; i <= 10000; i++) {
            while (count[i] > 0) {
                // If even indices are exhausted, switch to odd indices
                if (idx >= n) {
                    idx = 1;
                }
                ans[idx] = i;
                idx += 2;
                count[i]--;
            }
        }

        return ans;
    }
}

```

### Complexity

* **Time Complexity:** $O(N + K)$, where $N$ is the number of barcodes and $K = 10001$ is the value range. This is $O(N)$ overall.
* **Space Complexity:** $O(K)$ for the count array, effectively $O(1)$ auxiliary space.