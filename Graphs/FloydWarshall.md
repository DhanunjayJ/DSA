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


This is one of the most counterintuitive parts of the Floyd-Warshall algorithm when you first encounter it. It feels natural to think, *"I want to update the path between $i$ and $j$, so let me pick $i$ and $j$ first, and then test all possible intermediate vertices $k$ between them."*

If you put the $k$-loop on the inside, the algorithm breaks completely. Here is exactly why.

---

## The Core Reason: Dynamic Programming States

The Floyd-Warshall algorithm is a **Dynamic Programming** algorithm. The fundamental definition of its state depends on $k$:

> **Definition:** $D[i][j]$ represents the shortest path from vertex $i$ to vertex $j$ using **only a subset of vertices $\{1, 2, \dots, k\}$** as allowed intermediate steps.

For the math to work correctly, when we are on loop iteration $k$, the algorithm assumes that **all** pairs $(i, j)$ have already computed their shortest paths using only the vertices $\{1, 2, \dots, k-1\}$.

The recurrence relation is:


$$D^{(k)}[i][j] = \min\left(D^{(k-1)}[i][j], \quad D^{(k-1)}[i][k] + D^{(k-1)}[k][j]\right)$$

To calculate the right side of this equation safely, the values for $D^{(k-1)}[i][k]$ and $D^{(k-1)}[k][j]$ must already be **fully finalized** for the previous set of intermediate vertices.

---

## What Happens if $k$ is on the Inside?

If you put the $k$-loop on the inside, your loops look like this:

```python
# INCORRECT APPROACH
for i in range(V):
    for j in range(V):
        for k in range(V):  # k on the inside
            dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

```

If you do this, you are trying to calculate the final shortest path between $i$ and $j$ by checking all intermediates $k$ **before you have even found the true shortest paths from $i$ to $k$ or from $k$ to $j$**.

### A Concrete Example

Imagine a graph with 4 vertices in a straight line: `0 -> 1 -> 2 -> 3`.

```
[0] ----> [1] ----> [2] ----> [3]

```

Let's trace what happens if $k$ is on the inside when the loops hit $i = 0$ and $j = 3$:

1. The algorithm tries to update the path from `0` to `3`.
2. It loops through all $k$. When $k = 1$, it looks at `dist[0][1] + dist[1][3]`.
3. **The Trap:** What is `dist[1][3]` right now? Since the outer loops haven't processed $i = 1$ yet, the path from `1` to `3` hasn't been computed! `dist[1][3]` is still infinity ($\infty$).
4. The loop finishes for $i=0, j=3$, and concludes that there is no path from `0` to `3`, because it relied on intermediate paths that hadn't been discovered yet.

---

## Why the Outside Loop Fixes It

When the $k$-loop is on the **outside**, the algorithm builds the paths incrementally by expanding the "allowed pool" of detour vertices one by one:

* **When $k=0$:** You find all shortest paths that are allowed to use vertex `0` as a stepping stone.
* **When $k=1$:** You find all shortest paths allowed to use vertices `0` and `1` as stepping stones. Because $k=0$ ran completely, any path that needs to step through `0` to get to `1` is already perfectly calculated.

Going back to our `0 -> 1 -> 2 -> 3` example with $k$ on the outside:

* When **$k=1$**, the path `0 -> 1 -> 2` is found and saved into `dist[0][2]`.
* When **$k=2$**, the algorithm checks `dist[0][3] = min(dist[0][3], dist[0][2] + dist[2][3])`. Because $k=1$ already finished completely, `dist[0][2]` is perfectly computed, and the full path `0 -> 1 -> 2 -> 3` is successfully discovered.

> **Summary:** The outer loop dictates the *growth of the graph's connectivity*. The inner loops just update individual pairs based on that current level of connectivity.