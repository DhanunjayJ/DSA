To solve this problem using **Topological Sort (Kahn's Algorithm)**, we have to look at the graph through a completely different lens.

By definition, Topological Sort only works on graphs with no cycles. So how do we use it to find safe nodes in a graph that *contains* cycles? We use **indegree elimination**—but with a clever twist.

---

## The Twist: Reversing the Graph

In standard Kahn's Algorithm, we start with nodes that have an **indegree of 0** (no prerequisites) and move forward.

In this problem, we want to find nodes that safely lead to a **terminal node** (nodes with an **outdegree of 0**, meaning no outgoing edges).

If we **reverse all the edges** of the graph:

1. Terminal nodes (outdegree 0) suddenly become source nodes (**indegree 0**).
2. Nodes that point to terminal nodes will now be pointed *to* by those terminal nodes.

By flipping the arrows, we can run standard Kahn's Algorithm starting from the terminal nodes and working our way backward!

---

## Step-by-Step Walkthrough

1. **Create the Reversed Graph:** Build a new adjacency list where every edge $U \rightarrow V$ is flipped to $V \rightarrow U$.
2. **Calculate Indegrees:** Calculate the indegree of every node in this *reversed* graph. (Note: The indegree of a node in the reversed graph is exactly equal to its original outdegree).
3. **Initialize the Queue:** Push all nodes with an indegree of `0` into a queue. These are your original terminal nodes.
4. **Process the Queue (BFS):**
* Pop a node from the queue. This node is **safe**! (Store it).
* Look at its neighbors in the reversed graph.
* Decrement their indegrees.
* If any neighbor's indegree drops to `0`, it means *all* of its original outgoing paths lead to safe nodes. Push it into the queue.


5. **Sort the Result:** Because BFS processes nodes based on graph structure rather than numerical order, sort your list of safe nodes before returning it.

---

## Why does this isolate cycles?

If a node is part of a cycle, or if it can reach a cycle, its indegree in the reversed graph will **never drop to 0**.

The cycle acts as a closed loop that keeps holding onto incoming edges, preventing the BFS from ever reaching those nodes. They get completely stranded and excluded from our safe list.

---

## The Java Implementation

Here is how cleanly this translates into Java code using Kahn's Algorithm:

```java
import java.util.*;

class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int V = graph.length;
        
        // Step 1 & 2: Create reversed graph and track indegrees
        List<List<Integer>> revGraph = new ArrayList<>();
        int[] inDegree = new int[V];
        
        for (int i = 0; i < V; i++) {
            revGraph.add(new ArrayList<>());
        }
        
        for (int i = 0; i < V; i++) {
            for (int neighbor : graph[i]) {
                // Original edge: i -> neighbor
                // Reversed edge: neighbor -> i
                revGraph.get(neighbor).add(i);
                inDegree[i]++; 
            }
        }
        
        // Step 3: Queue all nodes with indegree 0 (original terminal nodes)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        
        // Step 4: BFS processing
        List<Integer> safeNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            safeNodes.add(curr);
            
            for (int neighbor : revGraph.get(curr)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
        
        // Step 5: The problem requires the output to be sorted in ascending order
        Collections.sort(safeNodes);
        
        return safeNodes;
    }
}

```

### Complexity Comparison

Both the **3-state DFS** approach and this **Kahn's Algorithm BFS** approach share the exact same time and space complexities:

* **Time Complexity:** $O(V + E \log V)$ — The graph traversal takes $O(V + E)$, and sorting the final answer list takes up to $O(V \log V)$.
* **Space Complexity:** $O(V + E)$ — To store the reversed graph and the indegree array.