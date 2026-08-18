The bucket-sort/frequency-array idea is solid, but this current implementation has a few notable flaws:

* **Memory Inefficiency:** Allocating `new ArrayList[1000_01]` wastes significant memory (an array of ~100k references) regardless of how small `arr` is.
* **Redundant Sorting:** `Collections.sort(sameCountNums[i])` is redundant because elements are already iterated and inserted into `sameCountNums` in ascending order (`for (int i = 1; i < 1000_01; i++)`).
* **Value Constraint Risk:** If `arr[i]` contains negative numbers or values $> 100,000$, `countMap[num]` throws an `ArrayIndexOutOfBoundsException`.

---

### Refactored Standard Approach (HashMap + Custom Sort)

Using a `HashMap` and custom sorting on unique values handles any range of integers (including negative numbers) without wasting memory:

```java
import java.util.*;

class Solution {
    public ArrayList<Integer> sortByFreq(int arr[]) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : arr) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // Extract unique elements
        List<Integer> unique = new ArrayList<>(freqMap.keySet());

        // Sort by frequency (descending); if equal, sort by value (ascending)
        unique.sort((a, b) -> {
            int freqA = freqMap.get(a);
            int freqB = freqMap.get(b);
            if (freqA != freqB) {
                return Integer.compare(freqB, freqA); // Descending frequency
            }
            return Integer.compare(a, b);             // Ascending value
        });

        // Build result
        ArrayList<Integer> ans = new ArrayList<>(arr.length);
        for (int num : unique) {
            int count = freqMap.get(num);
            for (int i = 0; i < count; i++) {
                ans.add(num);
            }
        }
        return ans;
    }
}

```

---

### Alternative: Fixed Bucket Sort (Optimized $O(N)$)

If constraints are strictly non-negative and $\le 10^5$, you can keep the $O(N)$ bucket sort logic by sizing the bucket array to `arr.length + 1` (since max frequency cannot exceed $N$) and removing redundant sorting:

```java
import java.util.*;

class Solution {
    public ArrayList<Integer> sortByFreq(int arr[]) {
        int n = arr.length;
        int maxVal = 0;
        for (int num : arr) {
            if (num > maxVal) maxVal = num;
        }

        int[] count = new int[maxVal + 1];
        for (int num : arr) {
            count[num]++;
        }

        // Max possible frequency is n
        List<Integer>[] buckets = new ArrayList[n + 1];
        for (int val = 0; val <= maxVal; val++) {
            if (count[val] > 0) {
                int freq = count[val];
                if (buckets[freq] == null) {
                    buckets[freq] = new ArrayList<>();
                }
                buckets[freq].add(val); // Naturally in ascending order
            }
        }

        ArrayList<Integer> ans = new ArrayList<>(n);
        for (int f = n; f >= 1; f--) {
            if (buckets[f] != null) {
                for (int val : buckets[f]) {
                    for (int k = 0; k < f; k++) {
                        ans.add(val);
                    }
                }
            }
        }
        return ans;
    }
}

```