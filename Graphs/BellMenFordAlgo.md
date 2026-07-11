The **Bellman-Ford algorithm** is a Single-Source Shortest Path (SSSP) algorithm. Like Dijkstra's algorithm, it finds the shortest path from a starting node to all other nodes in a weighted graph.

However, Bellman-Ford has a major superpower: **it can handle negative edge weights**, and it can even detect **negative weight cycles**.

---

### Why Dijkstra Fails and Bellman-Ford Wins

Dijkstra is a *greedy* algorithm. Once it marks a node as "visited," it assumes it has found the absolute shortest path to it and never re-evaluates it. If a graph has a negative weight later down the line, Dijkstra misses it.

Bellman-Ford is based on **Dynamic Programming**. It doesn't make assumptions. It systematically over-estimates the distance, and then corrects itself by **relaxing** every single edge in the graph multiple times.

#### What is "Relaxing" an Edge?

Relaxing an edge from node $u$ to node $v$ with weight $w$ means checking if you can improve the current known distance to $v$ by traveling through $u$.


$$\text{if } dist[u] + w < dist[v] \implies dist[v] = dist[u] + w$$

---

### How the Algorithm Works

If a graph has $V$ vertices, the longest possible shortest path without a cycle can only contain $V - 1$ edges. Therefore, Bellman-Ford repeats the relaxation process exactly $V - 1$ times.

1. **Initialize:** Set the distance to the source node to `0`, and all other nodes to infinity (`Integer.MAX_VALUE`).
2. **Relax All Edges $V-1$ Times:** Loop through every edge in the graph. If you can find a shorter path to a destination node, update its distance.
3. **Detect Negative Weight Cycles ($V$-th iteration):** Run the relaxation loop one more time. If any distance *decreases* again, it means a **negative weight cycle** exists (an infinite loop where the path cost keeps dropping forever). In this case, a definitive shortest path cannot be found.

---

### Java Implementation

Here is the clean, standard implementation of the Bellman-Ford algorithm. It returns the array of shortest distances, or an empty array if a negative weight cycle is detected.

```java
import java.util.*;

class Solution {
    public int[] bellmanFord(int V, int[][] edges, int src) {
        int[] dist = new int[V];
        
        // Step 1: Initialize distances to a large value (infinity)
        // We use 1e8 (100,000,000) instead of Integer.MAX_VALUE to prevent integer overflow
        Arrays.fill(dist, 100000000); 
        dist[src] = 0;

        // Step 2: Relax all edges (V - 1) times
        for (int i = 0; i < V - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int wt = edge[2];
                
                // If the source of the edge is reachable, try to relax it
                if (dist[u] != 100000000 && dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                }
            }
        }

        // Step 3: Check for negative weight cycles (V-th iteration)
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int wt = edge[2];
            
            if (dist[u] != 100000000 && dist[u] + wt < dist[v]) {
                System.out.println("Graph contains a negative weight cycle!");
                return new int[]{-1}; // Return an indicator for negative cycle
            }
        }

        return dist;
    }
}

```

---

### Complexity Analysis

* **Time Complexity:** $O(V \times E)$
We run an inner loop over all edges ($E$) exactly $V-1$ times. If the graph is dense ($E \approx V^2$), the worst-case time complexity becomes $O(V^3)$, which is significantly slower than Dijkstra's $O(E \log V)$.
* **Space Complexity:** $O(V)$
We only need an array of size $V$ to store the shortest distances.