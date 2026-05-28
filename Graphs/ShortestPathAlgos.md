Shortest path algorithms are some of the most fundamental tools in graph theory. Because different graphs have different constraints (like weights, directed/undirected edges, or negative values), we use different algorithms depending on the situation.

Here is a breakdown of the primary shortest path algorithms, categorized by their use cases.

---

## 1. Single-Source Shortest Path (SSSP)

These algorithms find the shortest path from **one specific source vertex** to all other vertices in the graph.

### A. Breadth-First Search (BFS)

* **Best Used For:** Unweighted graphs (or graphs where all edges have a uniform weight of 1).
* **How it works:** It explores the graph layer by layer using a FIFO queue. The first time a node is visited, it is guaranteed to be via the shortest path.
* **Time Complexity:** $O(V + E)$
* **Limitation:** Cannot handle graphs with varying edge weights.

### B. Dijkstra's Algorithm

* **Best Used For:** Graphs with **non-negative weights** (can be directed or undirected).
* **How it works:** It uses a greedy approach, typically backed by a **Priority Queue (Min-Heap)**. It always expands the closest unvisited node, updating the distances of its neighbors.
* **Time Complexity:** $O((V + E) \log V)$
* **Limitation:** Fails completely if the graph contains even a single negative edge weight.

### C. Bellman-Ford Algorithm

* **Best Used For:** Graphs that contain **negative edge weights**.
* **How it works:** It uses dynamic programming by "relaxing" all edges in the graph $V - 1$ times. On the $V$-th iteration, if any distance shrinks further, it proves the graph contains a **negative weight cycle** (an infinite loop where the path cost keeps decreasing).
* **Time Complexity:** $O(V \times E)$
* **Limitation:** Slower than Dijkstra's algorithm.

### D. Shortest Path in Directed Acyclic Graphs (DAG)

* **Best Used For:** Graphs with **no cycles** (even if they have negative weights).
* **How it works:** It first finds a **Topological Sort** of the vertices. Then, it processes the vertices in that exact linear order, relaxing edges sequentially.
* **Time Complexity:** $O(V + E)$
* **Limitation:** Only works if the graph has absolutely no cycles.

---

## 2. All-Pairs Shortest Path (APSP)

These algorithms find the shortest path between **every single pair of vertices** $(u, v)$ in the graph.

### A. Floyd-Warshall Algorithm

* **Best Used For:** Dense graphs where you need a complete lookup matrix of distances between all nodes. It can handle negative weights but not negative cycles.
* **How it works:** A dynamic programming approach using a 2D matrix. It systematically checks if routing through an intermediate vertex $k$ yields a shorter path between vertex $i$ and vertex $j$.
* **Time Complexity:** $O(V^3)$
* **Limitation:** Highly inefficient for large, sparse graphs due to its cubic time complexity.

### B. Johnson’s Algorithm

* **Best Used For:** Sparse graphs with negative weights where you need all-pairs shortest paths.
* **How it works:** It combines Bellman-Ford and Dijkstra. It uses Bellman-Ford once to "re-weight" the graph to eliminate all negative weights without changing the shortest paths, and then runs Dijkstra's algorithm from every vertex.
* **Time Complexity:** $O(V^2 \log V + VE)$
* **Limitation:** More complex to implement than Floyd-Warshall.

---

## 3. Heuristic / Pathfinding (Single-Pair)

When you only care about getting from **one specific start to one specific end node** (common in maps and games).

### A. A* (A-Star) Search Algorithm

* **Best Used For:** Geographical pathfinding, maps, and games.
* **How it works:** An extension of Dijkstra's algorithm that uses a **heuristic function** (like straight-line distance to the goal) to guide its search direction, ignoring paths leading away from the destination.
* **Time Complexity:** $O(E)$ in the best case, but bounded by Dijkstra's worst-case.

---

## Cheat Sheet: Which one should you pick?

| Graph Type | Edge Weights | Best Algorithm | Time Complexity |
| --- | --- | --- | --- |
| **Unweighted** | All equal (or 1) | **BFS** | $O(V + E)$ |
| **DAG** | Any (Positive/Negative) | **Topological Sort + SSSP** | $O(V + E)$ |
| **General** | Non-negative ($\ge 0$) | **Dijkstra** | $O((V + E) \log V)$ |
| **General** | Contains Negative ($< 0$) | **Bellman-Ford** | $O(V \times E)$ |
| **All-Pairs** | Non-negative / Dense | **Floyd-Warshall** | $O(V^3)$ |
| **All-Pairs** | Sparse / Negative weights | **Johnson's** | $O(V^2 \log V + VE)$ |