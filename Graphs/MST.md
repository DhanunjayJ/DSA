A **Minimum Spanning Tree (MST)** is a subset of edges from an edge-weighted, connected, undirected graph that connects all vertices together without cycles, while minimizing the total edge weight.

---

### Core Concepts

To understand an MST, break it down into its components:

1. **Spanning**: The subgraph must include **all vertices** ($V$) of the original graph.
2. **Tree**: The subgraph must be connected and **acyclic**. For any tree with $V$ vertices, it contains exactly **$V - 1$ edges**.
3. **Minimum**: Out of all possible spanning trees for the graph, the sum of weights of the selected edges is minimized:

$$\text{Weight}(T) = \sum_{e \in T} w(e)$$


<p align="center">
  <img src="https://encrypted-tbn2.gstatic.com/licensed-image?q=tbn:ANd9GcRbQdJFThx_kx5RQo728Xuclfltnd3fRruQPeD-YZCeBTIHHT93Q8EAmopwG86pcil9OQ8ZEe6SDSeva2o">
</p>

---

### Fundamental Properties

* **Edge Count:** Any MST on a graph with $V$ vertices contains exactly $V - 1$ edges.
* **Uniqueness:** If all edge weights in a graph are distinct, the graph has exactly **one unique MST**. If multiple edges share the same weight, there can be multiple valid MSTs with the identical minimum total cost.
* **Cut Property:** Divide the vertices into two disjoint sets $S$ and $V \setminus S$. Across any cut separating $S$ and $V \setminus S$, the edge with the **strictly minimum weight crossing the cut must belong to the MST**.
* **Cycle Property:** For any cycle in a graph, the edge with the **strictly maximum weight cannot belong to any MST** (unless there are ties).

---

### Standard MST Algorithms

Both primary algorithms for finding an MST rely on greedy strategies, but they approach the problem from different angles:

#### 1. Kruskal’s Algorithm (Edge-Centric)

Kruskal’s algorithm treats every vertex as an independent component and iteratively adds the cheapest available edge that connects two disconnected components.

* **Approach:**
1. Sort all edges in non-decreasing order of weight.
2. Iterate through sorted edges. For each edge $(u, v)$:
* Check if $u$ and $v$ belong to different connected components using a **Disjoint Set Union (DSU / Union-Find)** structure.
* If they are in different sets, union them and include the edge in the MST.
* If they are already in the same set, skip the edge to avoid creating a cycle.


3. Stop when $V - 1$ edges have been added.


* **Time Complexity:** $O(E \log E)$ or $O(E \log V)$ dominated by edge sorting. DSU operations with path compression and union-by-rank run in near-linear time $O(E \cdot \alpha(V))$.
* **Space Complexity:** $O(V + E)$ for storing edges and DSU parent/rank arrays.

#### 2. Prim’s Algorithm (Vertex-Centric)

Prim’s algorithm starts at an arbitrary source vertex and grows a single connected component one vertex at a time.

* **Approach:**
1. Maintain a set of visited vertices and a **Min-Heap (Priority Queue)** storing candidate edges/nodes with their minimum connection weights.
2. Pick an arbitrary starting vertex, mark it visited, and push its adjacent edges to the heap.
3. While the heap is not empty and fewer than $V$ vertices are visited:
* Extract the edge with the smallest weight connecting to an unvisited vertex $v$.
* Mark $v$ as visited and add the edge to the MST.
* Push all edges from $v$ leading to unvisited vertices into the heap.




* **Time Complexity:**
* Using a Binary Min-Heap: $O((V + E) \log V) = O(E \log V)$.
* Using a Fibonacci Heap: $O(E + V \log V)$.


* **Space Complexity:** $O(V + E)$ for adjacency list and priority queue.

---

### Kruskal vs. Prim Comparison

| Feature | Kruskal's Algorithm | Prim's Algorithm |
| --- | --- | --- |
| **Strategy** | Grows a forest of trees into a single tree | Grows a single tree outward from one vertex |
| **Primary Data Structure** | Disjoint Set Union (DSU) | Min-Heap / Priority Queue |
| **Best Suited For** | Sparse graphs ($E \approx V$) | Dense graphs ($E \approx V^2$) |
| **Input Format** | Edge list $(u, v, w)$ | Adjacency list / matrix |
| **Cycle Prevention** | DSU `find()` check | Visited array check |

---

### Practical Applications

* **Network Infrastructure:** Designing telecommunication lines, power grids, pipeline layouts, and local area network (LAN) cabling to minimize cable installation costs.
* **Approximation Algorithms:** Used in the 2-approximation metric Traveling Salesperson Problem (TSP) using MST traversal.
* **Clustering & Data Mining:** Single-linkage hierarchical clustering can be derived directly by cutting the heaviest edges of an MST.
* **Image Segmentation:** Graph-based computer vision models use MST variations to identify coherent boundary regions.