These three code blocks represent the **three standard ways** to solve the *Cheapest Flights Within K Stops* problem. Now that we've talked about the logical traps of this problem (like information bleeding and over-pruning), looking at these codes will make perfect sense!

---

## Code 1: Level-by-Level BFS (Breadth-First Search)

This code uses a standard queue to traverse the graph layer by layer, where each "layer" represents the total number of flights taken.

### How it works:

* **The Layer Trick (`while (sz-- > 0)`):** Before expanding any nodes, it records the size of the queue (`sz = q.size()`). It processes exactly that many nodes inside the inner loop. This guarantees that **all nodes processed within this inner loop have taken exactly the same number of stops**.
* **Tracking Progress:** The `stops` counter is only incremented *after* an entire layer of the queue is emptied. This elegantly keeps track of the $k$ stop constraint without needing to bundle the stop count inside the queue array elements.
* **The Filter (`price + distance >= dist[neighbour]`):** It only pushes a neighbor into the queue if this new route offers a strictly cheaper price than previously recorded for that neighbor.
```java

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] i : flights)
            adj.computeIfAbsent(i[0], value -> new ArrayList<>()).add(new int[] { i[1], i[2] });

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] { src, 0 });
        int stops = 0;

        while (stops <= k && !q.isEmpty()) {
            int sz = q.size();
            // Iterate on current level.
            while (sz-- > 0) {
                int[] temp = q.poll();
                int node = temp[0];
                int distance = temp[1];

                if (!adj.containsKey(node))
                    continue;
                // Loop over neighbors of popped node.
                for (int[] e : adj.get(node)) {
                    int neighbour = e[0];
                    int price = e[1];
                    if (price + distance >= dist[neighbour])
                        continue;
                    dist[neighbour] = price + distance;
                    q.offer(new int[] { neighbour, dist[neighbour] });
                }
            }
            stops++;
        }
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
}

```
### Why it's clean:

Because it processes level-by-level, you don't need a complex multi-dimensional array tracker. You can guarantee that once `stops` crosses $k$, you've looked exactly as far as the rules allow.

---

## Code 2: Bellman-Ford Algorithm (Edge Relaxation)

This code approaches the problem by focusing purely on the flights (edges) rather than traversing nodes step-by-step.

### How it works:

* **The K Constraint:** It runs the outer loop exactly `k + 1` times. This forces the algorithm to find paths that use a maximum of $k + 1$ flights (which equals $k$ stops).
* **The Snapshot (`int[] temp = Arrays.copyOf(dist, n)`):** This is the exact `temp` array concept we discussed! By modifying `temp` based on the old values in `dist`, it ensures that a flight sequence like $0 \rightarrow 1$ and $1 \rightarrow 2$ cannot both be calculated inside the same iteration loop.
* **Updating:** After all flights are evaluated for the current iteration, it updates `dist = temp` to unlock those newly discovered cities for the next iteration.

```java
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // Distance from source to all other nodes.
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Run only K+1 times since we want shortest distance in K hops
        for (int i = 0; i <= k; i++) {
            // Create a copy of dist vector.
            int[] temp = Arrays.copyOf(dist, n);
            for (int[] flight : flights) {
                if (dist[flight[0]] != Integer.MAX_VALUE) {
                    temp[flight[1]] = Math.min(temp[flight[1]], dist[flight[0]] + flight[2]);
                }
            }
            // Copy the temp vector into dist.
            dist = temp;
        }
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }
};

```
### Why it's clean:

It bypasses building a graph adjacency list entirely. It reads directly from the raw `flights` array, making it incredibly space-efficient and short to write.

---

## Code 3: Modified Dijkstra's Algorithm

This code uses a `PriorityQueue` sorted by **price** (`(a, b) -> a[0] - b[0]`), ensuring it always picks the absolute cheapest known path to explore next.

### How it works:

* **Queue Structure:** The Priority Queue stores items as `{dist, node, steps}`.
* **The Clever Pruning Guard:** 

```java
if (steps > stops[node] || steps > k + 1) continue;
```

This is how it handles the constraint safely. It maintains an array called `stops[]` that records the *minimum number of stops* used to reach each node so far. 
If a path pulled from the queue took **more steps** to reach `node` than a path we processed earlier, it skips it (`continue`). 

```java
import java.util.*;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // 1. Initialize the graph using a List of Lists
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // 2. Populate the adjacency list
        for (int[] flight : flights) {
            int u = flight[0];
            int v = flight[1];
            int price = flight[2];
            adj.get(u).add(new int[] { v, price });
        }

        // Track the minimum number of stops used to reach each node
        int[] stops = new int[n];
        Arrays.fill(stops, Integer.MAX_VALUE);
        
        // PriorityQueue sorted by distance/cost ascending: {dist, node, steps}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[] { 0, src, 0 });

        while (!pq.isEmpty()) {
            int[] temp = pq.poll();
            int dist = temp[0];
            int node = temp[1];
            int steps = temp[2];

            // If this path takes more stops than a previous path to this node,
            // or if it exceeds the maximum allowed flights (k stops means k + 1 flights)
            if (steps > stops[node] || steps > k + 1)
                continue;
            
            // Finalize the minimum steps to reach this node
            stops[node] = steps;
            
            // Since it's a Min-Heap, the first time we pop 'dst', it's guaranteed cheapest
            if (node == dst)
                return dist;

            // Loop over neighbors smoothly. 
            // If adj.get(node) is empty, the loop naturally skips without crashing!
            for (int[] nbr : adj.get(node)) {
                int neighborNode = nbr[0];
                int flightPrice = nbr[1];
                
                pq.offer(new int[] { dist + flightPrice, neighborNode, steps + 1 });
            }
        }
        
        return -1;
    }
}

```

* **Early Return:** Because a Priority Queue always processes the cheapest total cost first, the very first time `node == dst` is pulled out of the queue, it is **guaranteed** to be the cheapest valid answer under the limit. It returns the price immediately.

### Why it's clean:

It's often the fastest approach for massive graphs because it stops searching the moment it hits the destination node, ignoring any lingering more-expensive routes.