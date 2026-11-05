The reason you see both $O(V + E)$ and $O(V^2)$ boils down to one crucial detail: **how the graph is stored in memory**.

Both complexities are technically correct, but they describe different graph representations.

---

### 1. Why is it $O(V + E)$? (Adjacency List)

When you use an **Adjacency List** (like the Java code above), every vertex has a dedicated list of its direct neighbors.

During a BFS or DFS traversal:

* **The $V$ part:** The algorithm initializes and visits every single vertex exactly once.
* **The $E$ part:** For every vertex, the algorithm loops through its neighbor list. Across the entire traversal, you look at every edge exactly once (or twice if the graph is undirected).

Because you only touch what actually exists, the time spent is directly proportional to the total number of vertices plus the total number of edges: $O(V + E)$. This is highly efficient for **sparse graphs** (graphs where most nodes are not connected to each other).

---

### 2. Why is it sometimes called $O(V^2)$? (Adjacency Matrix)

If you represent your graph using an **Adjacency Matrix**—a 2D grid of size $V \times V$ where a `1` represents a connection and a `0` represents no connection—the math changes completely.

To find the neighbors of any given vertex, the algorithm can no longer just look at a compact list of connections. Instead, it must scan an entire row of length $V$ in the grid to check for `1`s.

* You have $V$ total vertices to visit.
* For *each* vertex, you must check all $V$ potential neighbors in that row.
* This forces a nested loop behavior: $V \text{ vertices} \times V \text{ columns} = O(V^2)$.

Even if a node has zero connections, you still waste time checking all $V$ slots in its matrix row.

---

### Summary Table

| Representation | Time Complexity | When to Use |
| --- | --- | --- |
| **Adjacency List** | $O(V + E)$ | Best for **Sparse Graphs** (few edges). Saves time and memory. |
| **Adjacency Matrix** | $O(V^2)$ | Best for **Dense Graphs** (where $E$ approaches $V^2$, meaning almost every node connects to every other node). |

> **The "Aha!" Moment:** In a worst-case dense graph, every node connects to every other node, meaning $E \approx V^2$. If you substitute $V^2$ for $E$ in the formula $O(V + E)$, it becomes $O(V + V^2)$, which simplifies right back to $O(V^2)$.