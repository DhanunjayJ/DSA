Solving the **Longest Consecutive Sequence** problem using **Union-Find (Disjoint Set Union, DSU)** is a great way to understand how DSU can handle dynamic connectivity problems beyond graph theory.

Here is how you can implement it in **Java** to meet the $O(n)$ time complexity requirement.

---

### Approach & Intuition

1. **Hash Map for Tracking:** Since the numbers can be large and negative (from $-10^9$ to $10^9$), we cannot use a simple array for Union-Find. Instead, we use a `HashMap` to map each number to its parent in the DSU structure, along with another map to store the **size** of each component.
2. **Avoid Duplicates:** First, put all numbers into a `HashSet` (or handle duplicates via the map) to ensure each unique number is processed once.
3. **Union Neighbors:** For each number $x$, if $x - 1$ exists in our set, we union $x$ and $x - 1$. Similarly, if $x + 1$ exists, we union $x$ and $x + 1$.
4. **Track Maximum Size:** As we perform unions, we keep track of the maximum size of any component.

---

### Java Implementation

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    
    // DSU structure with parent and size maps
    static class UnionFind {
        Map<Integer, Integer> parent = new HashMap<>();
        Map<Integer, Integer> size = new HashMap<>();
        int maxSize = 0;

        public void add(int x) {
            if (parent.containsKey(x)) return;
            parent.put(x, x);
            size.put(x, 1);
            maxSize = Math.max(maxSize, 1);
        }

     public int find(int x){
        if(parent.get(x)==x) return x;
        //recursively find the parent.
        int root = find(parent.get(x));
        parent.put(x,root);
        return root;
     }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                // Attach rootJ under rootI
                parent.put(rootJ, rootI);
                int newSize = size.get(rootI) + size.get(rootJ);
                size.put(rootI, newSize);
                maxSize = Math.max(maxSize, newSize);
            }
        }
    }

    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        UnionFind uf = new UnionFind();

        // Initialize all unique elements in DSU
        for (int num : nums) {
            uf.add(num);
        }

        // Union adjacent elements if they exist
        for (int num : nums) {
            if (uf.parent.containsKey(num + 1)) {
                uf.union(num, num + 1);
            }
            // Optional: checking num - 1 is handled symmetrically when processing num - 1
        }

        return uf.maxSize;
    }
}

```

---

### Complexity Analysis

* **Time Complexity:** $O(n \cdot \alpha(n))$ where $\alpha$ is the Inverse Ackermann function (which is practically constant, $\le 4$). Inserting into the `HashMap` and performing find/union operations take near $O(1)$ time on average per element, resulting in an overall **$O(n)$** time complexity.
* **Space Complexity:** **$O(n)$** to store the elements in the `HashMap` parent and size trackers.

---

