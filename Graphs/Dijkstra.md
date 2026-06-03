## 1. Dijkstra's Algorithm Code (Java)

Here is a standard, optimized implementation of Dijkstra's algorithm using an **Adjacency List** and a **Min-Priority Queue** (Min-Heap).

```java
import java.util.*;

class Solution {
    // Class to represent a node and its distance/weight
    static class Pair {
        int node;
        int distance;
        
        Pair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public int[] dijkstra(int V, List<List<Pair>> adj, int src) {
        // Step 1: Initialize distances array with Infinity
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        // Base case: Distance to source is always 0
        dist[src] = 0;

        // Step 2: Create a Min-Priority Queue sorted by distance
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);
        
        // Push the source node into the Priority Queue
        pq.add(new Pair(src, 0));

        // Step 3: Process the Priority Queue
        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int u = curr.node;
            int currDist = curr.distance;

            // Optimization: If we found a shorter path to 'u' already, skip processing
            if (currDist > dist[u]) continue;

            // Explore all neighbors of node 'u'
            for (Pair neighbor : adj.get(u)) {
                int v = neighbor.node;
                int weight = neighbor.distance; // edge weight

                // Core Concept: Edge Relaxation
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Pair(v, dist[v]));
                }
            }
        }

        // Optional: Replace remaining Integer.MAX_VALUE with -1 for unreachable nodes
        for (int i = 0; i < V; i++) {
            if (dist[i] == Integer.MAX_VALUE) dist[i] = -1;
        }

        return dist;
    }
}

```

---

## 2. The Core Thing of the Algorithm: "Greedy Choice"

If the core thing about the DAG algorithm was **Topological Order**, the core thing about Dijkstra's algorithm is the **Greedy Choice Property**.

Instead of sorting the whole graph ahead of time (which you can't do if there are cycles), Dijkstra makes a locally optimal choice at every step. It asks:

> *"Of all the unvisited nodes we can currently reach, which one has the absolute shortest path from the source right now?"*

It picks that node using a **Min-Priority Queue**.

### Why does this greedy choice work?

Because all edge weights are **positive**, taking any *other* path to reach this exact same node later will only accumulate *more* distance. Therefore, the moment a node is popped out of the Priority Queue, its shortest distance from the source is finalized.

Once a node is popped, you use it to **relax** its neighbors, updating the DP table (`dist` array) with better values.

---

## 3. Why Do We Use It? (The Problem It Solves)

We use Dijkstra's algorithm because it strikes an incredible balance between **flexibility** and **speed**.

* **It handles cycles:** Unlike the DAG algorithm, Dijkstra doesn't care if your graph has loops, paths that double back, or complex networks.
* **It is highly efficient:** By using a Min-Heap, it avoids checking every path blindly. It runs in $O((V + E) \log V)$ time. For a massive graph like a real-world map, this is drastically faster than a brute-force approach like Bellman-Ford ($O(V \times E)$).

> ⚠️ **The Big Limitation:** Dijkstra **fails** if the graph contains negative edge weights. If a negative weight exists, a path that looked longer initially could suddenly become shorter later, breaking the "greedy" guarantee that a popped node is finalized.

---

## 4. Where Is It Used? (Real-World Applications)

Because Dijkstra calculates the physical "shortest cost" through a network, it runs behind the scenes of many systems you use daily:

* **Google Maps / GPS Navigation:** Road networks are treated as a giant graph where intersections are vertices and roads are edges. The weights aren't just physical distances; they include real-time traffic delays. Dijkstra (and its optimized variant, the $A^*$ algorithm) is used to find your fastest driving route.
* **Network Routing Protocols (OSPF):** Open Shortest Path First (OSPF) is a routing protocol used by internet routers to send data packets across the web. Routers use Dijkstra to find the path with the least layout delay or highest bandwidth to forward your internet traffic.
* **Flight Agenda Systems:** Finding the cheapest or shortest connecting flights across multiple airports and layovers.
* **Social Network Analysis:** Suggesting connections or finding your "degree of separation" from another user on platforms like LinkedIn.

---

To understand why a `PriorityQueue` is essential, let’s look at what happens if we *don’t* use one.

If we used a standard, normal `Queue` (like in a regular Breadth-First Search), we would process nodes simply in the order we discovered them. In a weighted graph, this causes a major problem: **we might process a node using a long, suboptimal path first.** Later on, we will find a much shorter path to that same node. When that happens, we are forced to re-enter that node into the queue and re-process *all* of its neighbors all over again. This ruins our efficiency and drags the time complexity way up.

The `PriorityQueue` is the engine that prevents this. Here is exactly why we use it:

---

## 1. It Guarantees the "Greedy" Property

A Min-Priority Queue always keeps the node with the **absolute smallest tentative distance** at the very top.

By always pulling out the closest available node next, Dijkstra guarantees a massive rule: **The moment a node is popped out of the Priority Queue, its absolute shortest path from the source has been found.** Because all edge weights are positive, any other route we discover later down the line *must* add more distance. It can never beat the minimum value we just popped.

---

## 2. It Prevents Redundant Work (No Backtracking)

Because the `PriorityQueue` ensures that a node's distance is finalized the first time it is popped, **we only have to look at a node's outgoing neighbors exactly once.** Instead of blindly wandering through the graph, the Priority Queue focuses the algorithm's attention only on the most promising, shortest paths first.

---

## A Visual Example: Queue vs. PriorityQueue

Imagine this scenario:

* Path A: `Source -> Node 1 -> Node 2` (Total Distance = 100)
* Path B: `Source -> Node 3 -> Node 4 -> Node 2` (Total Distance = 5)

### With a Regular Queue (BFS style):

1. The queue processes layer by layer. Path A has fewer stops (edges), so it reaches `Node 2` first.
2. `Node 2` updates its distance to **100** and adds all of its own neighbors into the queue.
3. Much later, Path B finally reaches `Node 2` with a distance of **5**.
4. Because 5 is less than 100, `Node 2` updates its distance to 5, and **must throw itself and all of its neighbors back into the queue** to fix the old calculations. This is highly inefficient.

### With a PriorityQueue (Dijkstra):

1. Even though Path A has fewer steps, the Priority Queue looks at the cumulative distances.
2. It sees that Path B's step distances (e.g., 1, 2, 5) are much smaller than Path A's massive jump of 100.
3. The Priority Queue bubble-sorts Path B to the top. It reaches and finalizes `Node 2` with a distance of **5** first.
4. When Path A finally arrives later with a distance of 100, the algorithm checks `if (100 < 5)`, sees it's worse, and completely ignores it. No re-processing required!

---

## Summary of Efficiency

By using a `PriorityQueue`, the cost of finding the next closest node is reduced to **$O(\log V)$** (the time it takes for the heap to rearrange itself). This brings the overall time complexity of the algorithm to a highly efficient **$O((V + E) \log V)$**.

---

In an interview setting, **your old code (the "Lazy Evaluation" approach) is much better and easier to write.** Here is exactly why you should use your approach in an interview, along with one tiny tweak to make it flawless.

---

## Why Your Code Wins in an Interview

### 1. It acts as its own "Visited" array

In standard Dijkstra implementations, you have to manage a `dist` array **and** a separate `boolean[] visited` array.
In your code, the `ans` array does both jobs!

* If `ans[i] == Integer.MAX_VALUE`, it means the node is **unvisited**.
* If `ans[i] != Integer.MAX_VALUE`, it means it is **visited and finalized**.

This eliminates an entire data structure, meaning fewer variables for you to track while under pressure.

### 2. No risk of Integer Overflow

As we saw with the Kahn's algorithm version, adding a weight to `Integer.MAX_VALUE` causes an overflow disaster unless you remember to add a specific safety check.
Because your code *never* pulls `Integer.MAX_VALUE` out to do addition, **it is physically impossible for your code to overflow.** This eliminates a massive edge-case trap that interviewers love to watch candidates fall into.

### 3. It is incredibly clean to read

Your `while` loop essentially boils down to just 3 logical blocks:

1. Pop the minimum element.
2. If it's already visited, skip it.
3. If not, finalize it and add its neighbors.

---

## The One Tiny Problem in Your Old Code

There is a slight bug in how you built the graph in your old template. The question specifies that the graph is **Directed**, but your graph building loop builds an **Undirected** graph because it adds the edge both ways:

```java
// What your old code did (Undirected):
graph.get(v1).add(new Pair(v2, w));
graph.get(v2).add(new Pair(v1, w)); // Remove this line for directed graphs!

```

---

## The Perfect Interview-Ready Version of Your Code

Here is your exact approach, cleaned up for a **Directed Graph**, and adding the final `-1` check for unreachable nodes that online judges usually ask for:

```java
import java.util.*;

class Pair implements Comparable<Pair> {
    int v;
    int w;
    Pair(int v, int w) {
        this.v = v;
        this.w = w;
    }
    
    @Override
    public int compareTo(Pair o) {
        return this.w - o.w; // Keeps the Min-Priority Queue sorted correctly
    }
}

class Solution {
    public int[] dijkstra(int V, int[][] edges, int src) {
        // 1. Build Directed Graph
        ArrayList<ArrayList<Pair>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) graph.add(new ArrayList<>());
        
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];
            graph.get(u).add(new Pair(v, w)); // Only u -> v (Directed)
        }
        
        // 2. Setup DP/Visited array and PQ
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        int[] ans = new int[V];
        Arrays.fill(ans, Integer.MAX_VALUE);
        
        // Start with the source
        pq.add(new Pair(src, 0));
        
        // 3. Dijkstra's Core Loop
        while (!pq.isEmpty()) {
            Pair rem = pq.remove();
            
            // If already finalized, skip (acts as visited check)
            if (ans[rem.v] != Integer.MAX_VALUE) continue;
            
            // Lock in the absolute shortest distance
            ans[rem.v] = rem.w;
            
            // Explore neighbors
            for (Pair nbr : graph.get(rem.v)) {
                if (ans[nbr.v] != Integer.MAX_VALUE) continue;
                
                // Pure addition, zero danger of MAX_VALUE overflow
                pq.add(new Pair(nbr.v, nbr.w + rem.w));
            }
        }
        
        // 4. Convert unreachable nodes to -1
        for (int i = 0; i < V; i++) {
            if (ans[i] == Integer.MAX_VALUE) ans[i] = -1;
        }
        
        return ans;
    }
}

```

Stick with this template! It is short, robust, intuitive to explain out loud to an interviewer, and very difficult to accidentally break.

---

Let’s break down the time complexity of your interview-ready Dijkstra code step by step. It runs in **$O((V + E) \log V)$** time.

To understand why, we look at the two main components of the algorithm: **Graph Traversal** and **Priority Queue Operations**.

---

### 1. Graph Traversal: $O(V + E)$

Let's look at how the `while` loop visits nodes and edges:

* **Vertices ($V$):** Because of your "magic line" (`if (ans[rem.v] != Integer.MAX_VALUE) continue;`), the code inside the loop only fully executes **exactly once for each vertex**. Once a vertex is finalized, any duplicate copies of it popped from the queue are instantly skipped.
* **Edges ($E$):** For every vertex we process, we loop through its neighbors. Across the entire run of the algorithm, we look at every single directed edge in the adjacency list **exactly once**.

Combined, exploring the structure of the graph takes **$O(V + E)$** time, just like a standard Breadth-First Search (BFS).

---

### 2. Priority Queue Operations: $O(\log V)$ per operation

The data structure driving the algorithm is the `PriorityQueue`. Every time you add or remove an element from a heap of size $N$, it takes $O(\log N)$ time to rearrange itself.

In the worst-case scenario, how many elements can be inside the Priority Queue at once?

* A node is added to the queue when we traverse an edge leading to it.
* Therefore, the maximum number of elements ever pushed into the queue is equal to the number of edges, $E$.
* This means pushing and popping from the queue takes **$O(\log E)$** time.

> **Why do we write $\log V$ instead of $\log E$?** > In a simple graph without duplicate parallel edges, the maximum possible number of edges between $V$ vertices is $V^2$.
> Mathematically, $\log(V^2) = 2 \log V$. Since we drop constants in Big-O notation, $O(\log E)$ simplifies directly to **$O(\log V)$**.

---

### Putting It All Together

Now we multiply the graph actions by the cost of the Priority Queue operations:

1. **Popping from the Queue:** We pop an element every time the loop runs. In the worst case, we might pop up to $E$ elements.

$$\text{Cost} = E \times O(\log V)$$


2. **Pushing into the Queue:** We push a neighbor into the queue only when traversing an edge. We do this at most $E$ times.

$$\text{Cost} = E \times O(\log V)$$



When you combine the graph traversal limits with the heap restructuring costs, the final bound for the algorithm is:


$$O((V + E) \log V)$$

### Why this is highly efficient:

If you have a massive road network (like an entire country on Google Maps) where vertices are intersections and edges are roads, $V$ and $E$ are huge. An $O((V + E) \log V)$ runtime means the algorithm scales almost linearly with the size of the map, allowing it to calculate your driving route in milliseconds!

---

