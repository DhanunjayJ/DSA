The short answer is **no, this approach is not correct** for the LeetCode problem "Swim in Rising Water".

While your intuition about trying to find a path and tracking the time makes sense, your current implementation is a **greedy Depth First Search (DFS)** that makes irreversible choices.

Here is a breakdown of why this approach fails, followed by how to fix it.

---

## Why Your Current Approach Fails

### 1. The Greedy Trap (Local vs. Global Minimum)

Your code looks at the immediate 4 neighbors, picks the one with the lowest elevation (due to `Collections.sort`), and immediately dives into it via DFS.

* **The issue:** The path with the lowest *immediate* step might lead straight into a massive wall later on, while a slightly higher immediate step could open up a completely flat, optimal path to the destination. Because you don't backtrack or explore other paths globally, your code gets trapped in local choices.

### 2. Time Accumulation Logic is Flawed

In this problem, you don't "add the difference" to the time. The problem states that at time `t`, you can swim anywhere as long as the water level is at least the elevation of the platform. Therefore, the time taken to traverse any path is simply the **maximum elevation encountered anywhere along that path**.


$$\text{Time for a path} = \max(\text{grid}[i][j] \text{ for all cells } (i, j) \text{ in the path})$$

### 3. Missing Error Handling

If a cell has no unvisited neighbors (for example, it hits a dead end), your code will crash with an `IndexOutOfBoundsException` because you call `cnbrs.get(0)` without checking if `cnbrs` is empty.

---

## The Correct Approach

This problem is essentially a variation of **Dijkstra's Algorithm** (or a Breadth-First Search using a Min-Heap / Priority Queue).

Instead of choosing the best *immediate* neighbor, you should always expand the cell with the **lowest overall water level reached so far** across the entire grid boundary you have explored.

### How it works:

1. Use a **Priority Queue (Min-Heap)** that stores cells sorted by the maximum water level required to reach them.
2. Maintain a `visited` array to ensure you don't process the same cell twice.
3. At each step, pop the cell with the lowest water level. If it's the bottom-right corner, **you've found your answer**.
4. Otherwise, push all unvisited neighbors into the priority queue, updating their required water level to $\max(\text{current\_time}, \text{neighbor's\_elevation})$.

---

## Corrected Java Code

Here is how you can implement this efficiently:

```java
import java.util.*;

class Solution {
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] vis = new boolean[n][n];
        
        // Min-Heap: stores array of {row, col, max_elevation_so_far}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));
        
        // Start at the top-left cell
        pq.add(new int[]{0, 0, grid[0][0]});
        vis[0][0] = true;
        
        int[][] nbrs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int r = curr[0];
            int c = curr[1];
            int time = curr[2];
            
            // If we reached the destination, this is the minimum time possible
            if (r == n - 1 && c == n - 1) {
                return time;
            }
            
            // Explore all 4 neighbors
            for (int[] dir : nbrs) {
                int nr = r + dir[0];
                int nc = c + dir[1];
                
                if (nr >= 0 && nc >= 0 && nr < n && nc < n && !vis[nr][nc]) {
                    vis[nr][nc] = true;
                    // The time required to step into the neighbor is the max of 
                    // the time spent getting here and the neighbor's own height.
                    pq.add(new int[]{nr, nc, Math.max(time, grid[nr][nc])});
                }
            }
        }
        
        return 0;
    }
}

```

### Complexity

* **Time Complexity:** $O(N^2 \log N)$ because there are $N^2$ cells, and each heap operation takes $O(\log N)$ time.
* **Space Complexity:** $O(N^2)$ for the `vis` grid and the elements stored in the Priority Queue.

---

While Dijkstra's algorithm (using a Min-Heap) is the most efficient and standard way to solve "Swim in Rising Water," you **can** absolutely solve this problem using **DFS**.

However, because a standard DFS will get stuck or timed out trying to find the shortest path in a weighted grid, you have to pair it with a technique called **Binary Search on the Answer**.

---

## The Strategy: Binary Search + DFS

Instead of trying to find the optimal path *during* the DFS, we guess a specific time $t$, and use DFS just to check a simple yes/no question:

> *"Can we reach the destination if the maximum water level we are allowed to swim through is $t$?"*

### Why this works:

* The minimum possible answer is the value of the starting cell: `grid[0][0]`.
* The maximum possible answer is the highest elevation in the entire grid (or just $N \times N - 1$ since the grid contains a permutation of numbers from $0$ to $N^2-1$).
* If we can reach the end at time $t$, we can definitely reach it at time $t+1$. If we can't reach it at time $t$, we definitely can't reach it at time $t-1$. This monotonic property means we can binary search for $t$.

---

## Step-by-Step Breakdown

1. **Define the Range:** Set `low = grid[0][0]` and `high = n * n - 1`.
2. **Binary Search:** Find the midpoint `mid = low + (high - low) / 2`.
3. **Run DFS (The Validator):** * Start a DFS from `(0, 0)`.
* You can only step on a neighboring cell if its elevation is **less than or equal to `mid**` and it hasn't been visited yet.


4. **Adjust the Search Range:**
* If DFS successfully reaches the bottom-right corner, `mid` is a valid time! We save it as a potential answer and try to look for a smaller time by setting `high = mid - 1`.
* If DFS fails to reach the end, `mid` is too low. We must look for a higher time by setting `low = mid + 1`.



---

## Java Implementation

Here is how you write the clean, working DFS solution using this approach:

```java
class Solution {
    int[][] nbrs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int low = grid[0][0];
        int high = n * n - 1;
        int ans = high;

        // Binary search for the minimum possible maximum time
        while (low <= high) {
            int mid = low + (high - low) / 2;
            boolean[][] vis = new boolean[n][n];

            // Check if we can reach the end using 'mid' as our maximum allowed height
            if (dfs(grid, vis, 0, 0, mid)) {
                ans = mid;        // Record valid time
                high = mid - 1;   // Try to find a smaller valid time
            } else {
                low = mid + 1;    // 'mid' is too small, try a larger time
            }
        }

        return ans;
    }

    private boolean dfs(int[][] grid, boolean[][] vis, int r, int c, int maxTime) {
        int n = grid.length;

        // Base case: reached the bottom-right corner
        if (r == n - 1 && c == n - 1) {
            return true;
        }

        vis[r][c] = true;

        // Explore all 4 directions
        for (int[] dir : nbrs) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            // Condition 1: Stay within boundaries
            // Condition 2: Neighbor must not be visited yet
            // Condition 3: Neighbor's elevation must be <= our current limit (maxTime)
            if (nr >= 0 && nc >= 0 && nr < n && nc < n && !vis[nr][nc] && grid[nr][nc] <= maxTime) {
                if (dfs(grid, vis, nr, nc, maxTime)) {
                    return true; // If any path leads to the end, return true immediately
                }
            }
        }

        return false;
    }
}

```

---

## Complexity Analysis

* **Time Complexity:** $O(N^2 \log(N))$.
* The Binary Search takes $O(\log(N^2))$ steps, which simplifies to $O(2 \log N) \approx O(\log N)$.
* In each step of the binary search, we run a full DFS that visits at most all $N^2$ cells, taking $O(N^2)$ time.


* **Space Complexity:** $O(N^2)$ due to the `vis` matrix allocated in each binary search loop iteration and the recursive call stack for the DFS grid traversal.

---

Solving this problem using **Disjoint Set Union (DSU)** / **Union-Find** is a fantastic approach. It feels incredibly satisfying because it reverses the way you think about the grid.

Instead of moving from the start to the end, you **flood the grid from the ground up**.

---

## The Strategy: "Flooding" the Grid

1. **Flatten and Sort:** Since the grid contains values from $0$ to $N^2-1$, we can map every cell $(r, c)$ to a single 1D coordinate using the formula: $\text{ID} = r \times N + c$. We then sort all the cells based on their elevation (`grid[r][c]`) in ascending order.
2. **Time/Elevation Steps:** We start at time $t = 0$ and increment the time step by step. At each time $t$, we "activate" the cell that has that exact elevation.
3. **Union Neighbors:** When a cell becomes active, we look at its 4 neighbors. If any neighbor is *already* active (meaning its elevation is $\le t$), we connect (Union) our current cell with that neighbor.
4. **The Stop Condition:** After connecting the newly activated cell to its valid neighbors, we check if the **top-left corner $(0,0)$** and the **bottom-right corner $(N-1, N-1)$** belong to the same component. The exact time $t$ when they finally connect is our answer!

---

## Java Implementation

Here is how you implement the DSU approach:

```java
import java.util.*;

class Solution {
    // Standard DSU class with Path Compression
    class DisjointSet {
        int[] parent;

        public DisjointSet(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]); // Path compression
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                parent[rootI] = rootJ;
            }
        }
    }

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int totalCells = n * n;
        
        // Map to store: elevation -> 1D coordinate ID
        // Since positions are 0 to N^2-1, we can use an array instead of a HashMap
        int[] cellPositions = new int[totalCells];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int id = r * n + c;
                cellPositions[grid[r][c]] = id;
            }
        }

        DisjointSet dsu = new DisjointSet(totalCells);
        boolean[] active = new boolean[totalCells];
        
        int[][] nbrs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int startId = 0;          // 0 * n + 0
        int endId = totalCells - 1; // (n-1) * n + (n-1)

        // Simulate time t rising from 0 upwards
        for (int t = 0; t < totalCells; t++) {
            int currId = cellPositions[t];
            active[currId] = true;

            int r = currId / n;
            int c = currId % n;

            // Check 4 neighbors
            for (int[] dir : nbrs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr >= 0 && nc >= 0 && nr < n && nc < n) {
                    int neighborId = nr * n + nc;
                    
                    // If the neighbor is already active, union them
                    if (active[neighborId]) {
                        dsu.union(currId, neighborId);
                    }
                }
            }

            // Check if start and end are connected
            if (active[startId] && active[endId] && dsu.find(startId) == dsu.find(endId)) {
                return t;
            }
        }

        return 0;
    }
}

```

---

## Complexity Analysis

* **Time Complexity:** $O(N^2 \cdot \alpha(N^2))$, where $\alpha$ is the Inverse Ackermann function (which grows so slowly it is practically a constant, $\le 4$).
* Setting up the positions takes $O(N^2)$.
* The main loop runs $N^2$ times, executing up to 4 `union` and `find` operations per step. This makes the DSU solution faster and more optimized than the Dijkstra or Binary Search + DFS solutions!


* **Space Complexity:** $O(N^2)$ to store the `parent` array and the tracking `active` array.

---

Yes, there are a couple of other ways to solve this problem!

While **Dijkstra's**, **Binary Search + DFS/BFS**, and **DSU** are the most common and optimal interview solutions, you can also solve this using **Bellman-Ford / SPFA** or treating it as a **Minimum Spanning Tree (MST)** problem.

Here is how these two alternative approaches work.

---

## 1. Kruskal's Algorithm (Minimum Spanning Tree)

We can look at the grid as a graph where each cell is a vertex, and the edges connect adjacent cells.
The "weight" of an edge between cell $A$ and cell $B$ is the maximum water level required to cross them: $\max(\text{grid}[A], \text{grid}[B])$.

### The Strategy:

Instead of treating it as a standard shortest-path problem, we want to find a path where the maximum edge weight is minimized. This is a classic **Minimax Path** problem, which can be solved using Kruskal's algorithm to build a Minimum Spanning Tree.

1. Create a list of all possible edges between adjacent cells in the grid.
2. Assign each edge a weight: $\text{weight} = \max(\text{grid}[r1][c1], \text{grid}[r2][c2])$.
3. Sort all edges by their weights in ascending order.
4. Iterate through the sorted edges and add them to your MST using a Disjoint Set Union (DSU) structure.
5. Stop the moment the top-left cell and bottom-right cell become connected. The weight of the edge that finally connects them is your answer.

> **Note:** While this uses DSU internally, it differs from the previous DSU approach because you are explicitly sorting and iterating over *edges* rather than activating individual *nodes* sequentially by time.

---

## 2. SPFA (Shortest Path Faster Algorithm) / Modified Bellman-Ford

You can use a dynamic programming/relaxation approach similar to the Bellman-Ford algorithm, optimized with a queue (SPFA).

### The Strategy:

Instead of tracking the absolute shortest distance, you maintain a 2D array `minTime[r][c]`, which stores the minimum possible time required to reach cell $(r, c)$ from the start.

1. Initialize `minTime[0][0] = grid[0][0]` and all other cells to $\infty$.
2. Push the starting cell `(0, 0)` into a standard FIFO queue.
3. While the queue is not empty, pop a cell $(r, c)$ and look at its neighbors $(nr, nc)$.
4. Calculate the potential time to reach the neighbor:

$$\text{potentialTime} = \max(\text{minTime}[r][c], \text{grid}[nr][nc])$$


5. **Relaxation step:** If $\text{potentialTime} < \text{minTime}[nr][nc]$, update the neighbor's value with this lower time. If the neighbor's value was updated and it's not currently in the queue, push it back into the queue so it can update *its* neighbors.
6. Repeat until the queue is empty. Your answer will be stored in `minTime[n-1][n-1]`.

### Why it's rarely used here:

While intuitive, SPFA has a worst-case time complexity of $O(V \cdot E)$, which translates to $O(N^4)$ for an $N \times N$ grid if the graph structure causes frequent re-queuing. Dijkstra's $O(N^2 \log N)$ is much more predictable and faster.

---

## Summary of All Approaches

| Approach | Core Mechanism | Worst-Case Time Complexity | Why Choose It? |
| --- | --- | --- | --- |
| **Dijkstra's (Min-Heap)** | Expands paths with lowest time first | $O(N^2 \log N)$ | Standard, most intuitive shortest-path choice. |
| **Binary Search + DFS/BFS** | Guesses the time, validates if path exists | $O(N^2 \log N)$ | Highly structured; easiest to write without custom classes. |
| **DSU (Flooding)** | Activates nodes by time, checks connectivity | $O(N^2 \cdot \alpha(N^2))$ | Mathematically the fastest due to near-constant DSU operations. |
| **Kruskal's (MST)** | Sorts edges, connects components | $O(N^2 \log N)$ | Great if you naturally view the grid as a weighted edge graph. |
| **SPFA** | Relaxes maximum path values continuously | $O(N^4)$ | Good fallback if you forget how to implement a Priority Queue comparator. |

---

Here is the full Java implementation using **Kruskal's Algorithm**.

To implement this, we treat the grid cells as coordinates and create explicit **edges** between every pair of adjacent cells. The weight of each edge is the minimum water level required to cross between those two cells, which is $\max(\text{grid}[r1][c1], \text{grid}[r2][c2])$.

---

## Java Implementation

```java
import java.util.*;

class Solution {
    // A class to represent an edge between two cells
    class Edge {
        int u; // 1D coordinate of cell 1
        int v; // 1D coordinate of cell 2
        int weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    // Standard Disjoint Set Union (DSU) for Kruskal's
    class DisjointSet {
        int[] parent;

        public DisjointSet(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]); // Path compression
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                parent[rootI] = rootJ;
            }
        }
    }

    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int totalCells = n * n;

        // If it's a 1x1 grid, no swimming is required
        if (n == 1) return grid[0][0];

        List<Edge> edges = new ArrayList<>();

        // Generate all internal edges (Right and Down to avoid duplicates)
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int u = r * n + c;

                // Check right neighbor
                if (c + 1 < n) {
                    int v = r * n + (c + 1);
                    int weight = Math.max(grid[r][c], grid[r][c + 1]);
                    edges.add(new Edge(u, v, weight));
                }
                // Check down neighbor
                if (r + 1 < n) {
                    int v = (r + 1) * n + c;
                    int weight = Math.max(grid[r][c], grid[r + 1][c]);
                    edges.add(new Edge(u, v, weight));
                }
            }
        }

        // Kruskal's Step 1: Sort all edges by weight in ascending order
        Collections.sort(edges, (a, b) -> Integer.compare(a.weight, b.weight));

        // Kruskal's Step 2: Initialize DSU and process edges
        DisjointSet dsu = new DisjointSet(totalCells);
        int startId = 0;
        int endId = totalCells - 1;

        for (Edge edge : edges) {
            dsu.union(edge.u, edge.v);

            // If the start and end are connected, this edge's weight is our answer
            if (dsu.find(startId) == dsu.find(endId)) {
                return edge.weight;
            }
        }

        return 0;
    }
}

```

---

## Why This Works Step-by-Step

1. **Mapping to 1D:** We flatten the 2D grid into 1D IDs ($r \times n + c$). The top-left cell $(0,0)$ becomes ID `0`, and the bottom-right cell becomes ID `n*n - 1`.
2. **Building the Graph Structure:** We look at every horizontal and vertical connection. The bottleneck to cross an edge between two cells is the higher of the two elevations. So, an edge between a cell with height `4` and a cell with height `2` gets a weight of `4`.
3. **Greedy Assembly (Kruskal's):** Sorting the edges guarantees that we build our paths using the lowest possible grid-barriers first.
4. **Early Termination:** The moment `dsu.find(startId) == dsu.find(endId)` is true, it means a complete, contiguous path has formed from the start to the finish. Because the edges were sorted, the final edge that bridges the two halves of the grid is guaranteed to be the absolute minimum possible "maximum height" for that path.