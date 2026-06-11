Here is the complete, optimized Java implementation for **Union-Find (Disjoint Set Union / DSU)** using both **Path Compression** and **Union by Rank**.

I have noted Java as your preferred language for code requests going forward.

### How These Optimizations Work

* **Union by Rank:** Always attaches the smaller tree under the root of the larger tree. This keeps the overall tree height as flat as possible.
* **Path Compression:** During the `find` operation, every visited node is moved to point directly to the root. This dramatically speeds up future lookups.

Combined, these two optimizations bring the amortized time complexity per operation down to $O(\alpha(N))$, where $\alpha$ is the Inverse Ackermann function (which grows so slowly that it is effectively a constant $\le 4$ for all practical values of $N$).

---

## Java Implementation

```java
import java.util.Arrays;

public class DisjointSet {
    private int[] parent;
    private int[] rank;

    // Constructor to initialize DSU with N elements
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        
        // Initially, every element is its own parent (independent set)
        // and the initial rank (height) of each tree is 0
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        Arrays.fill(rank, 0);
    }

    // Find operation with Path Compression
    public int find(int i) {
        // If i is the root of its set, return it
        if (parent[i] == i) {
            return i;
        }
        
        // Path Compression: Reassign i's parent directly to the actual root
        // by recursively calling find on its current parent
        parent[i] = find(parent[i]);
        
        return parent[i];
    }

    // Union operation by Rank
    public void union(int i, int j) {
        // Find the absolute root representatives of both elements
        int rootI = find(i);
        int rootJ = find(j);

        // If they are already in the same set, do nothing
        if (rootI == rootJ) {
            return;
        }

        // Union by Rank: Attach the smaller depth tree under the root of the deeper tree
        if (rank[rootI] < rank[rootJ]) {
            parent[rootI] = rootJ;
        } else if (rank[rootI] > rank[rootJ]) {
            parent[rootJ] = rootI;
        } else {
            // If ranks are equal, make one the root and increment its rank by 1
            parent[rootJ] = rootI;
            rank[rootI]++;
        }
    }

    // Optional helper method to check if two elements belong to the same set
    public boolean isConnected(int i, int j) {
        return find(i) == find(j);
    }

    // Driver code to test the implementation
    public static void main(String[] args) {
        int n = 5;
        DisjointSet dsu = new DisjointSet(n);

        // Perform some union operations
        dsu.union(0, 2);
        dsu.union(4, 2);
        dsu.union(3, 1);

        // Check connections
        System.out.println("Are 4 and 0 connected? " + dsu.isConnected(4, 0)); // Expected: true (0-2-4 form a set)
        System.out.println("Are 1 and 0 connected? " + dsu.isConnected(1, 0)); // Expected: false

        // Union the two distinct groups together
        dsu.union(1, 4);
        System.out.println("Are 1 and 0 connected now? " + dsu.isConnected(1, 0)); // Expected: true
    }
}

```

---

## Complexity Breakdown

* **Time Complexity:** * **Constructor:** $O(N)$ to initialize the parent and rank arrays.
* **Find / Union / isConnected:** $O(\alpha(N))$ amortized time. For all real-world applications, this equates to virtually **$O(1)$ constant time**.


* **Space Complexity:** $O(N)$ because it tracks two separate integer arrays (`parent` and `rank`) of size $N$.