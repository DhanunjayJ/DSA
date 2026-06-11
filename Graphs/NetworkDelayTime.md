###DFS
```java
class Solution {
    // Adjacency list
    Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();

    private void DFS(int[] signalReceivedAt, int currNode, int currTime) {
        // If the current time is greater than or equal to the fastest signal received
        // Then no need to iterate over adjacent nodes
        if (currTime >= signalReceivedAt[currNode]) {
            return;
        }

        // Fastest signal time for currNode so far
        signalReceivedAt[currNode] = currTime;
        
        if (!adj.containsKey(currNode)) {
            return;
        }
        
        // Broadcast the signal to adjacent nodes
        for (Pair<Integer, Integer> edge : adj.get(currNode)) {
            int travelTime = edge.getKey();
            int neighborNode = edge.getValue();
            
            // currTime + time : time when signal reaches neighborNode
            DFS(signalReceivedAt, neighborNode, currTime + travelTime);
        }
    }
    
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build the adjacency list
        for (int[] time : times) {
            int source = time[0];
            int dest = time[1];
            int travelTime = time[2];
            
            adj.putIfAbsent(source, new ArrayList<>());
            adj.get(source).add(new Pair(travelTime, dest));
        }
        
        // Sort the edges connecting to every node
        for (int node : adj.keySet()) {
            Collections.sort(adj.get(node), (a, b) -> a.getKey() - b.getKey());
        }
        
        int[] signalReceivedAt = new int[n + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);
        
        DFS(signalReceivedAt, k, 0);
        
        int answer = Integer.MIN_VALUE;
        for (int node = 1; node <= n; node++) {
            answer = Math.max(answer, signalReceivedAt[node]);
        }
        
        // Integer.MAX_VALUE signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}
```
These codes approach **LeetCode 743: Network Delay Time** using a **Depth-First Search (DFS) with Memoization / Pruning**.

While problems involving the shortest path on a weighted graph are usually solved with Dijkstra's algorithm, this approach forces a DFS to work by constantly exploring paths and "pruning" them the moment they become worse than a path we've already found.

Here is the breakdown of exactly how this logic operates.

---

## 🛠️ The Strategic Pre-Processing

Before diving into the recursion, the code does a very clever optimization step:

```java
// Sort the edges connecting to every node
for (int node : adj.keySet()) {
    Collections.sort(adj.get(node), (a, b) -> a.getKey() - b.getKey());
}

```

### Why sort the edges?

Because DFS is inherently blind and explores a single path all the way to a dead-end before backtracking, it can waste a lot of time exploring horribly long routes first.

By sorting the adjacency list based on `travelTime` (the edge weight) in ascending order, the DFS is forced to **greedily pick the fastest outgoing paths first**. This ensures that the array tracking arrival times gets populated with small numbers early on, which makes the pruning mechanism drastically more effective.

---

## 🛑 The Pruning Guard (The Core Engine)

The recursive `DFS` function handles the signal transmission. Its most important lines are right at the top:

```java
if (currTime >= signalReceivedAt[currNode]) {
    return;
}

```

### How this tracks "Visited" and saves time:

* **`signalReceivedAt[node]`** acts as our memoization array. It stores the absolute fastest time the signal has managed to reach a specific node *so far*.
* If the DFS traverses a path and arrives at `currNode` at a `currTime` that is **greater than or equal to** a time we already recorded, it means this current path is a waste of time.
* The algorithm immediately triggers a `return;` (pruning the branch), preventing it from needlessly re-broadcasting a slow signal to all of its neighbors.

If `currTime < signalReceivedAt[currNode]`, the path is a new record! The code updates the array: `signalReceivedAt[currNode] = currTime;` and continues broadcasting down to the neighbors.

---

## 🔄 A Visual Trace of the Pruning

Imagine the signal travels from **Node 1** to **Node 3** via two different paths:

1. **Path A (Greedy/Fast):** `1 -> 3` takes **2 seconds**.
* `signalReceivedAt[3]` is updated to `2`.


2. **Path B (Slow Chained Path):** `1 -> 2 -> 3`.
* `1 -> 2` takes 5 seconds.
* `2 -> 3` takes 1 second (Total `currTime = 6`).


3. When the DFS tries to transition from Node 2 into Node 3, it hits the guard:
* `if (6 >= 2)` $\rightarrow$ **True!**
* The DFS halts right there and refuses to look at any of Node 3's neighbors again through this path.



---

## 📊 Resolving the Final Answer

Once the DFS completely finishes traversing and pruning all valid corners of the graph, the `signalReceivedAt` array will contain the definitive minimum time it takes for the signal to reach every single individual node.

```java
int answer = Integer.MIN_VALUE;
for (int node = 1; node <= n; node++) {
    answer = Math.max(answer, signalReceivedAt[node]);
}
return answer == Integer.MAX_VALUE ? -1 : answer;

```

* **Why take the maximum (`Math.max`)?** The question asks when *all* nodes have received the signal. The network is only fully saturated when the very last, most delayed node finally receives the transmission.
* **Why check for `Integer.MAX_VALUE`?** If any index in the array still holds its initialization value of `Integer.MAX_VALUE`, it means the DFS was completely unable to find any physical route to that node from source `k`. Thus, it returns `-1`.

---

## ⚠️ A Quick Warning on this Approach

While this code is clean and passes the test cases, **DFS is generally suboptimal for shortest-path problems on general graphs**.

In worst-case scenarios (like a graph designed to trick the sorting step), a node's minimum time could be updated over and over again slightly each time, leading to exponential time complexity ($\mathcal{O}(V^V)$ or $\mathcal{O}(N!)$). Dijkstra's Algorithm remains the industry standard because its use of a PriorityQueue guarantees an efficient $\mathcal{O}(E \log V)$ processing limit.


###BFS

```java

class Solution {
    // Adjacency list
    Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();

    private void BFS(int[] signalReceivedAt, int sourceNode) {
        Queue<Integer> q = new LinkedList<>();
        q.add(sourceNode);
        
        // Time for starting node is 0
        signalReceivedAt[sourceNode] = 0;
        
        while (!q.isEmpty()) {
            int currNode = q.remove();
            
            if (!adj.containsKey(currNode)) {
                continue;
            }
            
            // Broadcast the signal to adjacent nodes
            for (Pair<Integer, Integer> edge : adj.get(currNode)) {
                int time = edge.getKey();
                int neighborNode = edge.getValue();
                
                // Fastest signal time for neighborNode so far
                // signalReceivedAt[currNode] + time : 
                // time when signal reaches neighborNode
                int arrivalTime = signalReceivedAt[currNode] + time;
                if (signalReceivedAt[neighborNode] > arrivalTime) {
                    signalReceivedAt[neighborNode] = arrivalTime;
                    q.add(neighborNode);
                }
            }
        }
    }
    
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build the adjacency list
        for (int[] time : times) {
            int source = time[0];
            int dest = time[1];
            int travelTime = time[2];
            
            adj.putIfAbsent(source, new ArrayList<>());
            adj.get(source).add(new Pair(travelTime, dest));
        }
        
        int[] signalReceivedAt = new int[n + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);
        
        BFS(signalReceivedAt, k);
        
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            answer = Math.max(answer, signalReceivedAt[i]);
        }
        
        // INT_MAX signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}

```

This code approaches **LeetCode 743: Network Delay Time** using a **Standard BFS (Queue-based) with SPFA-like Edge Relaxation**.

Even though it calls the method `BFS`, this isn't a traditional level-order traversal (like counting layers of hops). Instead, it uses a FIFO queue to propagate signal updates across the network. If it discovers a faster way to reach a node, it updates that node's time and re-queues it to spread the update to its neighbors.

Here is the step-by-step breakdown of how this approach functions, why it works, and its hidden catch.

---

## ⚙️ How the Algorithm Works

### 1. The Dynamic Update Check (The Filter)

Inside the neighbor loop, the code checks if the new path's total time is faster than anything seen before:

```java
int arrivalTime = signalReceivedAt[currNode] + time;
if (signalReceivedAt[neighborNode] > arrivalTime) {
    signalReceivedAt[neighborNode] = arrivalTime;
    q.add(neighborNode);
}

```

* **Why update `signalReceivedAt`?** It records the currently known absolute minimum time for the signal to reach `neighborNode`.
* **Why re-queue `neighborNode`?** If the signal arrived there faster than before, any nodes connected further down the line might *also* get the signal faster now. We push it back into the queue so its neighbors can recalculate their arrival times based on this new record.

---

## 🏎️ Let's Trace a Correction Scenario

Imagine a network layout where a node is evaluated multiple times:

```text
       (10 seconds)
   [1] -------------> [2]
    |                  ^
    | (1s)        (1s) |
    v                  |
   [3] ----------------+

```

1. **Step 1:** Node `1` is processed.
* It sees neighbor `2` and sets `signalReceivedAt[2] = 10`. Queue gets: `[2]`.
* It sees neighbor `3` and sets `signalReceivedAt[3] = 1`. Queue becomes: `[2, 3]`.


2. **Step 2:** Node `2` is removed from the queue. It has no neighbors.
3. **Step 3:** Node `3` is removed from the queue. It looks at neighbor `2`.
* The new calculated time via Node `3` is $1 + 1 = 2$ seconds.
* The algorithm checks: `if (signalReceivedAt[2] > 2)` $\rightarrow$ `10 > 2` is **True!**
* It updates `signalReceivedAt[2] = 2` and pushes Node `2` **back into the queue** so any of Node 2's dependencies can benefit from this 8-second time save.



---

## 🔍 BFS vs. Dijkstra vs. SPFA

While this code uses a regular `Queue`, the mechanism itself mimics the **Shortest Path Faster Algorithm (SPFA)**, which is an optimized variation of Bellman-Ford.

| Feature | Standard BFS | This Code (SPFA-like) | Dijkstra's Algorithm |
| --- | --- | --- | --- |
| **Data Structure** | `Queue` (FIFO) | `Queue` (FIFO) | `PriorityQueue` (Min-Heap) |
| **Node Visitation** | Visited exactly **once** | Can be re-queued **multiple times** | Visited exactly **once** (finalized) |
| **Edge Weights** | Only works if all weights are equal (or 1) | Works perfectly on varying positive weights | Works perfectly on varying positive weights |

---

## ⚠️ The Performance Caveat

This code will pass the LeetCode tests, but a regular `Queue` lacks the greedy intelligence of a `PriorityQueue`.

Because a standard queue processes whatever was added first, it might waste time exploring a long, highly-delayed path through the network, updating 50 downstream nodes, only to discover a much faster direct path a moment later. When it finds that faster path, it has to re-queue all 50 nodes and recalculate everything all over again.

* **Worst-Case Time Complexity:** $\mathcal{O}(V \times E)$ (where it acts like Bellman-Ford).
* **Average-Case Time Complexity:** $\mathcal{O}(E)$ on random graphs.

Using a `PriorityQueue` (Dijkstra) instead ensures you always pull the absolute lowest time out first, meaning each node is finalized the very first time it leaves the queue, sealing the time complexity tightly at $\mathcal{O}(E \log V)$.


###Dijkstra

```java
class Solution {
    // Adjacency list
    Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();
    
    private void dijkstra(int[] signalReceivedAt, int source, int n) {
        Queue<Pair<Integer, Integer>> pq = new PriorityQueue<Pair<Integer,Integer>>
            (Comparator.comparing(Pair::getKey));
        pq.add(new Pair(0, source));
        
        // Time for starting node is 0
        signalReceivedAt[source] = 0;
        
        while (!pq.isEmpty()) {
            Pair<Integer, Integer> topPair = pq.remove();
            
            int currNode = topPair.getValue();
            int currNodeTime = topPair.getKey();
            
            if (currNodeTime > signalReceivedAt[currNode]) {
                continue;
            }
            
            if (!adj.containsKey(currNode)) {
                continue;
            }
            
            // Broadcast the signal to adjacent nodes
            for (Pair<Integer, Integer> edge : adj.get(currNode)) {
                int time = edge.getKey();
                int neighborNode = edge.getValue();
                
                // Fastest signal time for neighborNode so far
                // signalReceivedAt[currNode] + time : 
                // time when signal reaches neighborNode
                if (signalReceivedAt[neighborNode] > currNodeTime + time) {
                    signalReceivedAt[neighborNode] = currNodeTime + time;
                    pq.add(new Pair(signalReceivedAt[neighborNode], neighborNode));
                }
            }
        }
    }
    
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build the adjacency list
        for (int[] time : times) {
            int source = time[0];
            int dest = time[1];
            int travelTime = time[2];
            
            adj.putIfAbsent(source, new ArrayList<>());
            adj.get(source).add(new Pair(travelTime, dest));
        }
        
        int[] signalReceivedAt = new int[n + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);
        
        dijkstra(signalReceivedAt, k, n);
        
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            answer = Math.max(answer, signalReceivedAt[i]);
        }
        
        // INT_MAX signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}

```

This code implements **Dijkstra's Algorithm** using a **PriorityQueue (Min-Heap)** to solve **LeetCode 743: Network Delay Time**.

This is the absolute **industry gold standard** approach for this problem. Unlike the previous BFS code that used a standard queue, this version processes nodes greedily based on who gets the signal first. This optimization guarantees maximum efficiency because once a node is fully processed at its minimum time, it never has to be re-evaluated.

---

## ⚙️ How the Algorithm Works

### 1. The Greedy Min-Heap Strategy

```java
Queue<Pair<Integer, Integer>> pq = new PriorityQueue<Pair<Integer,Integer>>
    (Comparator.comparing(Pair::getKey));
pq.add(new Pair(0, source));

```

The PriorityQueue is configured to sort by `Pair::getKey`, which represents the **accumulated time** taken to reach a node.
Because it is a Min-Heap, `pq.remove()` will **always return the node currently closest to the source in terms of time**.

---

### 2. The Obsolete Path Guard (Dijkstra's Key Optimization)

Inside the `while` loop, you have this critical guard statement:

```java
if (currNodeTime > signalReceivedAt[currNode]) {
    continue;
}

```

#### Why is this necessary?

As a signal propagates, a node can be pushed into the `PriorityQueue` multiple times via different routes.

Suppose Node `A` gets pushed into the queue twice:

1. First, via a slow route: `[Time: 25, Node: A]`
2. Later, via a fast route: `[Time: 5, Node: A]`

Because of the Min-Heap sorting, `[Time: 5, Node: A]` gets popped out first. The algorithm processes it, updates `signalReceivedAt[A] = 5`, and explores its neighbors.

Much later, the queue finally unearths the old, stale pair `[Time: 25, Node: A]`. The guard checks: `if (25 > 5)`. Since this is true, the algorithm says *"We already found a much faster way to handle Node A!"*, executes a `continue`, and drops it immediately. This prevents redundant work.

---

### 3. Edge Relaxation (Updating the Neighbors)

```java
if (signalReceivedAt[neighborNode] > currNodeTime + time) {
    signalReceivedAt[neighborNode] = currNodeTime + time;
    pq.add(new Pair(signalReceivedAt[neighborNode], neighborNode));
}

```

For every neighbor of the current node, the code calculates the prospective arrival time (`currNodeTime + time`). If this value is strictly better than the neighbor's previously recorded best time (`signalReceivedAt[neighborNode]`), the tracker is updated, and the new, faster path is pushed into the heap.

---

## 🏎️ Why this is drastically better than the regular BFS version

Consider the same scenario that slows down standard queue-based BFS:

```text
       (10 seconds)
   [1] -------------> [2]
    |                  ^
    | (1s)        (1s) |
    v                  |
   [3] ----------------+

```

1. **Step 1:** Node `1` adds `[10, Node 2]` and `[1, Node 3]` to the PriorityQueue.
2. **Step 2:** The PriorityQueue sorts them. `[1, Node 3]` is at the front because `1 < 10`. It is popped first!
3. **Step 3:** Node `3` looks at Node `2`. It calculates a total time of $1 + 1 = 2$ seconds. It updates `signalReceivedAt[2] = 2` and adds `[2, Node 2]` to the queue.
4. **Step 4:** The PriorityQueue now contains `[2, Node 2]` and `[10, Node 2]`. It pops `[2, Node 2]` because it is cheaper. Node `2`'s final minimum time is locked in at **2 seconds**.
5. **Step 5:** The queue eventually pops `[10, Node 2]`. The optimization guard sees that `10 > 2`, hits `continue`, and ignores it.

**The Win:** Node `2` never spreads outdated information to downstream neighbors. Everything is solved cleanly in a single pass.

---

## 📊 Time & Space Complexity

* **Time Complexity:** $\mathcal{O}(E \log V)$ where $E$ is the number of edges (times) and $V$ is the number of vertices ($n$). Each edge relaxation takes constant time, and inserting/removing from the PriorityQueue costs logarithmic time relative to the number of elements.
* **Space Complexity:** $\mathcal{O}(V + E)$ to store the graph map structure and the lookup array trackers.