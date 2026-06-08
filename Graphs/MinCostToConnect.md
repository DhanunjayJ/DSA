To solve LeetCode 1584 (**Min Cost to Connect All Points**) using Prim's Algorithm, you can apply the exact streamlined approach we just looked at.

The only difference here is that **the graph is implicit**. You are not given an adjacency list; instead, every point connects to every other point, and the weight of the edge between them is the **Manhattan distance**:

`Math.abs(x1 - x2) + Math.abs(y1 - y2)`

Here is the clean, optimized Java solution using Prim's algorithm tailored specifically for this problem.

### Java Solution

```java
import java.util.PriorityQueue;

class Solution {
    // Helper class for the Min-Heap
    static class Pair implements Comparable<Pair> {
        int node, weight;
        
        public Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
        
        @Override
        public int compareTo(Pair other) {
            return this.weight - other.weight;
        }
    }

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[n];
        int mstWeight = 0;
        int edgesConnected = 0;

        // Start from index 0 with a weight of 0
        pq.add(new Pair(0, 0));

        while (!pq.isEmpty() && edgesConnected < n) {
            Pair curr = pq.poll();
            int u = curr.node;

            // If already included in MST, skip
            if (visited[u]) continue;

            // Include in MST
            visited[u] = true;
            mstWeight += curr.weight;
            edgesConnected++;

            // Check connections to all other nodes (implicit dense graph)
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    // Calculate Manhattan distance dynamically
                    int distance = Math.abs(points[u][0] - points[v][0]) 
                                 + Math.abs(points[u][1] - points[v][1]);
                    pq.add(new Pair(v, distance));
                }
            }
        }

        return mstWeight;
    }
}

```

### Why this works perfectly here:

1. **No Graph Building Required:** Building an explicit adjacency list first would take $O(N^2)$ space. By calculating the Manhattan distance inside the loop dynamically, you keep the space complexity to a minimal $O(N)$ for tracking states.
2. **Early Exit Condition:** The condition `edgesConnected < n` guarantees that as soon as you have connected all $N$ vertices (meaning you've picked $N-1$ edges), the algorithm stops immediately without processing the remaining elements in the priority queue.

### Complexity Analysis

* **Time Complexity:** $O(N^2 \log N)$ where $N$ is the number of points. In the worst-case scenario, every node pushes all other unvisited nodes into the heap.
* **Space Complexity:** $O(N)$ to store the `visited` array and the elements inside the `PriorityQueue`.

---

Yes, **duplicate edges for the same target nodes absolutely go into the priority queue.** Here is exactly how the `visited` array saves us from crashing, and why the queue getting bloated changes our time complexity.

---

## 1. How the `visited` array saves us from duplicates

When we pull an element out of the `PriorityQueue`, the very first thing we do is check:

```java
if (visited[u]) continue;

```

Because of this single line, duplicate entries for a node are completely harmless to our final answer.

### Step-by-Step Scenario:

Imagine Node 3 is sitting in the queue twice because Node 0 saw it at a distance of **15**, and later Node 1 saw it at a distance of **5**.

1. Because it's a Min-Heap, the entry with the smaller weight `(Node 3, weight 5)` will naturally bubble up to the top first.
2. We poll `(Node 3, weight 5)`.
3. `visited[3]` is currently `false`. So we process it, mark `visited[3] = true`, and add **5** to our total cost.
4. Much later, the worse edge `(Node 3, weight 15)` finally reaches the top of the heap and gets polled.
5. The code hits `if (visited[3]) continue;`. It sees `visited[3]` is already `true`, **instantly discards it**, and moves to the next loop iteration without doing any heavy calculation or looking at its neighbors again.

---

## 2. Does this make the algorithm slow?

**Yes, it does.** You are completely correct.

Because we dynamically push up to $N$ neighbors for *every* single node we visit, the total number of elements inside our Priority Queue can grow to $O(N^2)$ in the worst case.

When the queue size is $O(N^2)$, inserting or removing an element takes $\log(N^2)$ time, which mathematically simplifies to $2 \log N$, which is still $O(\log N)$.
Since we do this push operation for almost every potential edge pair, the total time complexity becomes:


$$\text{Time Complexity} = O(N^2 \log N)$$

---

## 3. The $O(N^2)$ Optimized Approach (No Heap)

For a dense graph where $E \approx N^2$ (like this LeetCode problem where every point connects to every other point), you can actually drop the Priority Queue entirely to make the code faster!

Instead of a heap, you can use a simple array called `minDist[]` to keep track of the absolute cheapest cost to connect any unvisited node to the growing tree. At each step, you manually scan the array ($O(N)$) to find the minimum value.

### Optimized Prim's (No Heap) — $O(N^2)$ Time, $O(N)$ Space

```java
class Solution {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        int mstWeight = 0;
        boolean[] visited = new boolean[n];
        
        // minDist[i] stores the shortest distance from the current MST to point i
        int[] minDist = new int[n];
        java.util.Arrays.fill(minDist, Integer.MAX_VALUE);
        minDist[0] = 0; // Start with the first point

        for (int step = 0; step < n; step++) {
            int u = -1;
            
            // Step 1: Find the unvisited node with the absolute minimum distance
            // This replaces the pq.poll() operation
            for (int i = 0; i < n; i++) {
                if (!visited[i] && (u == -1 || minDist[i] < minDist[u])) {
                    u = i;
                }
            }

            // Add the minimum edge weight to our answer
            visited[u] = true;
            mstWeight += minDist[u];

            // Step 2: Update the minDist array for all unvisited neighbors
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    int dist = Math.abs(points[u][0] - points[v][0]) 
                             + Math.abs(points[u][1] - points[v][1]);
                    
                    // If this new path is shorter than what v previously knew, update it
                    if (dist < minDist[v]) {
                        minDist[v] = dist;
                    }
                }
            }
        }

        return mstWeight;
    }
}

```

### Why this version is faster for LeetCode 1584:

* **No Queue Bloat:** There are no duplicate entries. The `minDist` array stays exactly size $N$ the entire time.
* **Better Complexity:** The inner loops run exactly $N \times N$ times, giving an absolute fixed runtime of **$O(N^2)$**, avoiding the $\log N$ overhead entirely. In a dense graph, $O(N^2)$ beats $O(N^2 \log N)$.