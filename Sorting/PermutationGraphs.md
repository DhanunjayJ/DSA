## Understanding Minimum Swaps to Sort an Array via Permutation Graphs

When tasked with finding the minimum number of swaps required to sort an array—especially when elements are sorted using a custom key (such as digit sums)—brute force or simple mismatch counting fails.

The problem is best solved using **Cycle Decomposition** and **Permutation Graphs**, which can be implemented either using a **Visited Array** or **Disjoint Set Union (DSU)**.

---

### Core Concept: Permutation Graphs and Cycles

Every element in an array has a **current index** and a **target index** where it belongs after sorting. If you draw a directed arrow from every current index to its target sorted index, you form a **Permutation Graph**.

Because every node has exactly one outgoing arrow and one incoming arrow, the graph forms a set of closed loops called **cycles**.

> **The "Free Last Element" Rule:** For any independent cycle (or connected component) of length $L$, you only need **$L - 1$ swaps** to place all elements in their correct sorted positions. The final $L$-th element falls into place automatically without requiring an extra swap.

---

### The Mathematical Shortcut: $N - K$

If an array of size $N$ is decomposed into $K$ independent cycles (connected components), the total number of swaps is calculated as:

$$\text{Total Swaps} = \sum (L_i - 1)$$

Expanding this algebraic expression:

$$\text{Total Swaps} = (L_1 + L_2 + \dots + L_K) - (\underbrace{1 + 1 + \dots + 1}_{\text{$K$ times}})$$

Since the sum of all cycle lengths equals $N$, this simplifies to the powerful shortcut formula:

$$\text{Total Swaps} = N - K$$

*(where $N$ is the total number of elements and $K$ is the number of connected components / cycles).*

---

### Approach 1: Cycle Decomposition with a Visited Array

This is the most direct and practically fastest approach for coding interviews.

#### How it works:

1. **Pair Mapping:** Create an array of `Pair` objects storing each element's value, its custom sorting key (e.g., digit sum), and its **original index**.
2. **Sorting:** Sort the pairs based on your custom sorting criteria.
3. **Cycle Tracing:** Maintain a boolean `visited` array. Iterate through each index; if an index hasn't been visited and isn't already in its correct position, follow the chain of pointers (`originalIndex`) until the cycle closes.
4. **Counting Swaps:** For each cycle of size $L$, add $L - 1$ to your total swap count.

#### Java Implementation:

```java
import java.util.Arrays;

class Solution {
    class Pair {
        int dSum;
        int num;
        int originalIndex;

        Pair(int dSum, int num, int originalIndex) {
            this.dSum = dSum;
            this.num = num;
            this.originalIndex = originalIndex;
        }
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        Pair[] pairs = new Pair[n];
        
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(calcDSum(nums[i]), nums[i], i);
        }

        // Sort by criteria (e.g., digit sum first, then value)
        Arrays.sort(pairs, (a, b) -> {
            if (a.dSum == b.dSum) return Integer.compare(a.num, b.num);
            return Integer.compare(a.dSum, b.dSum);
        });

        boolean[] visited = new boolean[n];
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] || pairs[i].originalIndex == i) {
                continue;
            }

            int cycleSize = 0;
            int j = i;
            while (!visited[j]) {
                visited[j] = true;
                j = pairs[j].originalIndex;
                cycleSize++;
            }

            if (cycleSize > 1) {
                swaps += (cycleSize - 1);
            }
        }

        return swaps;
    }

    public int calcDSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}

```

---

### Approach 2: Disjoint Set Union (DSU / Union-Find)

An alternative graph-theoretic approach leverages the component-counting formula $N - K$.

#### How it works:

1. Initialize a DSU data structure where each index starts as its own component of size 1 (so initial components $K = N$).
2. For every element, perform a `union` operation between its **current index** $i$ and its **target sorted index** (`pairs[i].originalIndex`).
3. Every time a successful merge happens, decrement the component count $K$.
4. Return the final answer using the formula: **$N - \text{components}$**.

#### Java Implementation:

```java
import java.util.Arrays;

class Solution {
    class Pair {
        int dSum;
        int num;
        int originalIndex;

        Pair(int dSum, int num, int originalIndex) {
            this.dSum = dSum;
            this.num = num;
            this.originalIndex = originalIndex;
        }
    }

    class DSU {
        int[] parent;
        int components;

        DSU(int n) {
            parent = new int[n];
            components = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]); // Path compression
        }

        void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                parent[rootI] = rootJ;
                components--; // Each merge reduces component count
            }
        }
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        Pair[] pairs = new Pair[n];

        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(calcDSum(nums[i]), nums[i], i);
        }

        Arrays.sort(pairs, (a, b) -> {
            if (a.dSum == b.dSum) return Integer.compare(a.num, b.num);
            return Integer.compare(a.dSum, b.dSum);
        });

        DSU dsu = new DSU(n);

        for (int i = 0; i < n; i++) {
            dsu.union(i, pairs[i].originalIndex);
        }

        return n - dsu.components;
    }

    public int calcDSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}

```

---

### Complexity Comparison

| Metric | Visited Array Approach | DSU Approach |
| --- | --- | --- |
| **Time Complexity** | $O(N \log N)$ (dominated by sorting) | $O(N \log N)$ (dominated by sorting) |
| **Space Complexity** | $O(N)$ for pairs and visited array | $O(N)$ for pairs and DSU parent array |
| **Overhead** | Lower overhead, highly optimized in loops | Slight method call and structure overhead |