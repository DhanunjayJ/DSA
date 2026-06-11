The **Floyd-Warshall algorithm** is an **All-Pairs Shortest Path (APSP)** algorithm. This means it computes the shortest path between *every single pair* of vertices in a graph.

Unlike Dijkstra or Bellman-Ford, which only find paths from one fixed starting node, Floyd-Warshall gives you a complete "distance matrix" where `matrix[i][j]` represents the shortest distance from node `i` to node `j`.

---

### The Core Concept: The "Intermediate Node"

Floyd-Warshall is a classic **Dynamic Programming** algorithm. Its central idea is simple:

To find the shortest path from vertex $i$ to vertex $j$, the algorithm tests whether routing through an **intermediate vertex $k$** would provide a shorter path than the current known route.

For every pair of nodes $(i, j)$, it asks:

> *"Is it shorter to go straight from $i$ to $j$, or to go from $i$ to $k$, and then from $k$ to $j$?"*

Mathematically, the update formula (called edge relaxation) is:


$$matrix[i][j] = \min(matrix[i][j], matrix[i][k] + matrix[k][j])$$

The algorithm runs this check systematically for every possible node $k$ as the intermediate stepping stone.

---

### How the Algorithm Works

1. **Initialize a Matrix:** Create a 2D grid of size $V \times V$.
* If there is a direct edge from $i$ to $j$, set `matrix[i][j] = weight`.
* Set diagonal elements `matrix[i][i] = 0` (distance to itself).
* For any pairs with no direct edge, set the distance to **Infinity**.


2. **Three Nested Loops:** * The outermost loop picks the intermediate vertex $k$.
* The inner two loops pick the source $i$ and destination $j$.


3. **Update:** Apply the formula for every combination.

---

### Java Implementation

In many coding platforms (like GeeksforGeeks or LeetCode), the problem requires you to update an adjacency matrix in place. If no path exists, the cell typically contains `-1`, which we must temporarily treat as infinity during calculation.

```java
class Solution {
    public void floydWarshall(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Prepare the matrix. 
        // Convert -1 (no edge) to a large value (Infinity) to safely do math.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1) {
                    matrix[i][j] = 100000000; // Using 1e8 to prevent integer overflow
                }
                if (i == j) {
                    matrix[i][j] = 0; // Distance to self is 0
                }
            }
        }

        // Step 2: The Core 3-Loop Structure
        // k is the intermediate node
        for (int k = 0; k < n; k++) {
            // i is the source node
            for (int i = 0; i < n; i++) {
                // j is the destination node
                for (int j = 0; j < n; j++) {
                    // Check if going through 'k' is shorter
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }

        // Step 3: Convert Infinity back to -1 for the final answer
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] >= 100000000) {
                    matrix[i][j] = -1;
                }
            }
        }
    }
}

```

---

### Why is the order of the loops `k -> i -> j`?

This is the most common mistake when writing this algorithm! **The `k` loop must be on the outside.** If you put `k` on the inside, you are calculating the shortest path between $i$ and $j$ using *only the first available intermediate node*, and you will never re-evaluate that pair when better intermediate paths open up later. Putting `k` on the outside guarantees that the algorithm fully calculates all paths utilizing node `k` before moving to the next potential bridge.

### Complexity Analysis

* **Time Complexity:** $O(V^3)$
Because of the three nested loops running from $0$ to $V$, the time complexity is strictly cubic.
* **Space Complexity:** $O(1)$ auxiliary space if done in place, or $O(V^2)$ to store the distance matrix.

### When should you use it?

* When the graph is **dense** (has a lot of edges) and you need the distance between all pairs.
* When the vertex count $V$ is relatively small (typically $V \le 400$). If $V = 1000$, $V^3$ is 1 billion operations, which will cause a Time Limit Exceeded (TLE) error.