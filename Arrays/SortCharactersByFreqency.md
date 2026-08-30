Yes, there are a couple of popular alternative ways to solve **Sort Characters By Frequency** (LeetCode 451), depending on whether you want to use a **Max-Heap (Priority Queue)** or a simpler **Custom Object/Array sorting** approach.

Your current approach uses **Bucket Sort**, which is actually the most optimal approach in terms of time complexity ($O(N)$). However, using a Heap or Array sorting provides clean alternatives.

---

### Alternative 1: Max-Heap (Priority Queue)

Instead of buckets, you can push the character frequency pairs into a Max-Heap (or custom class/array) so that the character with the highest frequency naturally pops out first.

```java
import java.util.*;

class Solution {
    public String frequencySort(String s) {
        // Step 1: Count frequencies
        Map<Character, Integer> countMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        
        // Step 2: Put characters into a Max-Heap ordered by frequency descending
        PriorityQueue<Character> maxHeap = new PriorityQueue<>(
            (a, b) -> countMap.get(b) - countMap.get(a)
        );
        maxHeap.addAll(countMap.keySet());
        
        // Step 3: Build the result string
        StringBuilder sb = new StringBuilder();
        while (!maxHeap.isEmpty()) {
            char c = maxHeap.poll();
            int freq = countMap.get(c);
            for (int i = 0; i < freq; i++) {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}

```

* **Time Complexity:** $O(N + K \log K)$, where $K$ is the number of **unique** characters. Since $K \le 256$ (or total ASCII/Unicode characters), this practically runs in $O(N)$ time.
* **Space Complexity:** $O(N)$ to store the map and heap.

---

### Alternative 2: Array Sorting (Good for fixed character sets like ASCII)

Since strings usually contain standard ASCII characters, you can map frequencies using a fixed-size integer array instead of a `HashMap`, sort them, and construct the string.

```java
class Solution {
    public String frequencySort(String s) {
        // Step 1: Count frequencies using an array (assuming ASCII)
        int[][] freq = new int[256][2];
        for (int i = 0; i < 256; i++) {
            freq[i][0] = i; // store the character ASCII value
        }
        
        for (char c : s.toCharArray()) {
            freq[c][1]++;
        }
        
        // Step 2: Sort based on frequency descending
        Arrays.sort(freq, (a, b) -> b[1] - a[1]);
        
        // Step 3: Build the result string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            if (freq[i][1] == 0) break; // no more characters
            char c = (char) freq[i][0];
            int count = freq[i][1];
            for (int j = 0; j < count; j++) {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}

```

* **Time Complexity:** $O(N + K \log K)$ where $K \le 256$. Sorting a fixed size of 256 takes $O(1)$ time in practice.
* **Space Complexity:** $O(K)$ extra space for the frequency tracking array.