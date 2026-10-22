Instead, the $O(N^2)$ solution relies on a clever mathematical property of XOR:

1. **The Core Condition:** We want $a = b$, which means:

$$\text{arr}[i] \oplus \dots \oplus \text{arr}[j-1] = \text{arr}[j] \oplus \dots \oplus \text{arr}[k]$$


2. **XOR Cancellation:** If we XOR both sides by $a$, the entire combined segment from $i$ to $k$ becomes equal to $0$! That is:

$$\text{arr}[i] \oplus \text{arr}[i+1] \oplus \dots \oplus \text{arr}[k] = 0$$


3. **What this means for $O(N^2)$:**
* Whenever we find **any** subsegment from index $i$ to index $k$ whose total XOR sum is `0`, **any split point $j$** (where $i < j \le k$) will automatically satisfy $a == b$.
* Therefore, for a fixed starting index $i$, as we expand our ending index $k$ and compute the running XOR, every time the running XOR equals `0`, we can add $(k - i)$ to our total count because there are $(k - i)$ valid choices for $j$!



---

### Java Implementation ($O(N^2)$ Approach)

```java
class Solution {
    public int countTriplets(int[] arr) {
        int n = arr.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            int currentXor = 0;
            for (int k = i; k < n; k++) {
                currentXor ^= arr[k];
                
                // If the XOR sum from i to k is 0,
                // any j from i + 1 to k is a valid split point.
                if (currentXor == 0 && k > i) {
                    count += (k - i);
                }
            }
        }
        
        cio.println(count); // or return count;
        return count;
    }
}

```

---

To optimize the solution to **$O(N)$**, we can use prefix XORs combined with a frequency map. This approach eliminates the need for nested loops by leveraging a mathematical property of XOR.

---

### The $O(N)$ Mathematical Insight

1. **Prefix XOR Definition:** Let $P[m]$ be the XOR sum of elements from index $0$ to $m-1$ (with $P[0] = 0$).
2. **The Core Equality:** The original condition states $a = b$, which translates using prefix XORs to:

$$P[j] \oplus P[i] = P[k+1] \oplus P[j]$$


3. **Simplification:** XORing both sides by $P[j]$ cancels it out, leaving:

$$P[i] = P[k+1]$$



This means whenever we find **two identical prefix XOR values** at different indices $i$ and $m$ (where $m = k + 1$), any split point $j$ strictly between $i$ and $k$ will form a valid triplet. Specifically, for a fixed $m$ and a matching previous index $i$, the number of valid choices for $j$ is **$(k - i)$**.

Instead of iterating through every $i$ and $k$, we can keep a running total of the **frequency** of each prefix XOR and the **sum of their indices** using HashMaps.

---

### Java Implementation ($O(N)$)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int countTriplets(int[] arr) {
        int n = arr.length;
        
        Map<Integer, Integer> countMap = new HashMap<>();
        Map<Integer, Integer> indexSumMap = new HashMap<>();
        
        // Base case: prefix XOR 0 has occurred 1 time before the array starts
        countMap.put(0, 1);
        
        int prefixXor = 0;
        int totalTriplets = 0;
        
        for (int i = 0; i < n; i++) {
            prefixXor ^= arr[i];
            
            // If we've seen this prefix XOR before, calculate the valid triplets
            if (countMap.containsKey(prefixXor)) {
                int count = countMap.get(prefixXor);
                int indexSum = indexSumMap.getOrDefault(prefixXor, 0);
                
                // Formula: (current_index * count) - sum_of_previous_indices
                totalTriplets += (i * count) - indexSum;
            }
            
            // Update maps using (i + 1) as the 1-based prefix index position
            countMap.put(prefixXor, countMap.getOrDefault(prefixXor, 0) + 1);
            indexSumMap.put(prefixXor, indexSumMap.getOrDefault(prefixXor, 0) + i + 1);
        }
        
        return totalTriplets;
    }
}

```

### Complexity

* **Time Complexity:** $O(N)$ because we traverse the array of length $N$ once, performing $O(1)$ HashMap insertions and lookups.
* **Space Complexity:** $O(N)$ to store the prefix XOR frequencies and index sums in the HashMaps.

---

The **`sumMap`** is a clever math optimization to avoid writing a nested loop!

When you encounter the same prefix XOR value at multiple past indices—let's call them $i_1, i_2, \dots, i_m—$**every single one** of those past indices can form valid triplets with your current index $k$.

---

### The Mathematical Breakdown

For a fixed current index $k$ and a matching past index $i$, the number of valid split points $j$ is **$k - i$**.

If that same prefix XOR value appeared **3 times in the past** at indices $i_1, i_2,$ and $i_3$, the total number of triplets you can form *right now* at index $k$ is:


$$\text{Total} = (k - i_1) + (k - i_2) + (k - i_3)$$

If we use algebra to expand and regroup that equation:


$$\text{Total} = \underbrace{(k + k + k)}_{\text{count times } k} - \underbrace{(i_1 + i_2 + i_3)}_{\text{sum of all past indices}}$$

Which simplifies to:


$$\text{Total} = (\text{count} \times k) - (\text{sum of all past indices } i)$$

---

### What `sumMap` Stores

* **`countMap`** keeps track of **how many times** a specific prefix XOR value has appeared (the `count`).
* **`sumMap`** keeps track of the **sum of the indices** where that prefix XOR value appeared ($i_1 + i_2 + \dots + i_m$).

Instead of looping backward through every old index $i$ every time we see a matching prefix XOR, `sumMap` lets us compute the answer in **$O(1)$ time**:

```java
totalTriplets += (count * k) - indexSum;

```

---