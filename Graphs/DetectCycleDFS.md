To detect a cycle in a **directed graph** using DFS, simply checking if a node is "already visited" isn't quite enough.

In an undirected graph, visiting an already visited node means a cycle. But in a **directed graph**, hitting a visited node could just mean you stumbled upon a node that was fully processed in a completely different path.

To solve this, we can't just start from one node and hope to find everything (because directed graphs can be disconnected or have one-way streets). We **must check every node** using a **tri-state color tracking pattern** (similar to the bipartite problem, but with different meanings).

---

## The Tri-State (3-Color) Strategy

Instead of a simple true/false `visited` array, we track three distinct states for each node:

* **`0` (Unvisited - White):** The node has not been touched yet.
* **`1` (Visiting - Gray):** The node is currently in the **active recursion stack**. We are still exploring its neighbors.
* **`2` (Fully Visited - Black):** The node and all of its descendants have been completely explored. It is safe.

> 💡 **The Golden Rule:** A cycle exists if and only if your DFS encounters a node that is currently in the **Visiting (`1`)** state. This means you have looped back onto your current path (a back-edge).

---

## Java Implementation

We use a main loop to check every node (ensuring we don't miss disconnected components), and an array `state[]` to track our colors.

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Step 1: Build the adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
        }

        // Step 2: State array (0 = unvisited, 1 = visiting, 2 = visited)
        int[] state = new int[numCourses];

        // Step 3: We MUST start/check from every node to handle isolated components
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0) {
                if (hasCycle(adj, state, i)) {
                    return true; // Cycle detected!
                }
            }
        }
        return false; // No cycles found anywhere
    }

    private boolean hasCycle(List<List<Integer>> adj, int[] state, int node) {
        // If it's 1, we found a node currently in our active path -> CYCLE!
        if (state[node] == 1) return true;
        // If it's 2, it's already been fully processed and confirmed safe
        if (state[node] == 2) return false;

        // Mark as Visiting (Gray)
        state[node] = 1;

        // Explore all neighbors
        for (int neighbor : adj.get(node)) {
            if (hasCycle(adj, state, neighbor)) {
                return true;
            }
        }

        // Backtracking: Mark as Fully Visited (Black) before leaving the method
        state[node] = 2;
        return false;
    }
}

```

---

## Answering Your Specific Questions

### 1. "Do we start from every node and visit all nodes?"

**Yes.** You must iterate through every node in a global `for` loop. If a node's state is `0`, you kick off a DFS from it. This guarantees that if the graph is broken into isolated clusters or has nodes with no incoming edges, you will still check them.

### 2. "Or just start from one node and check if we already visited this node then we just return true?"

**No, because of "Cross Edges".** Consider this simple directed graph:

* Node `0` points to Node `1` and Node `2`.
* Node `1` points to Node `2`.

```
    0
   / \
  ▼   ▼
  1 ➔ 2

```

If you start a DFS at `0`:

1. It goes to `1`.
2. From `1`, it goes to `2`. `2` has no neighbors, so `2` finishes and becomes state `2` (Black).
3. `1` finishes and becomes state `2` (Black).
4. The recursion goes back to `0`, which now looks at its other neighbor, `2`.

If you only checked `if (visited[2]) return true`, your code would claim there is a cycle here. But looking at the diagram, **there is no cycle!** You can't get trapped in a loop.

By checking if `state[neighbor] == 1`, you only trigger a cycle flag if you hit a node you are *currently* exploring higher up in the active recursion tree.


Here is the interesting truth about this problem: **Finding a valid linear ordering of courses *is* the exact definition of a Topological Sort.** So, you cannot avoid topological sort conceptually. However, if you are asking, *"Can I solve this using standard DFS or BFS without learning a specialized algorithm like Kahn's Algorithm (indegree array)?"*, the answer is **yes!**

You can use the exact same **tri-state DFS cycle detection** logic we just discussed. The only addition is tracking when a node finishes processing.

---

## The Pure DFS Secret: Post-Order Traversal

When a node finishes its DFS and changes its state from **Visiting (`1`)** to **Fully Visited (`2`)**, it means all of its dependent courses have already been explored.

If we record nodes at the exact moment they finish, we get a valid topological order—just completely in **reverse**.

### The Strategy:

1. Run a standard tri-state DFS cycle detection across all nodes.
2. If a cycle is detected at any point, immediately return an empty array `[]` (it's impossible to finish).
3. Every time a node transitions to state `2` (Black), add it to a list or array.
4. Because this list collects nodes from the end of the chain backward, simply reverse the list at the very end to get your final course schedule!

---

## Java Implementation (Pure DFS)

```java
import java.util.*;

class Solution {
    private int index; // Global tracker to fill the result array backward

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // Step 1: Build adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] pre : prerequisites) {
            // pre[1] is the prerequisite -> must be taken BEFORE pre[0]
            adj.get(pre[1]).add(pre[0]);
        }

        int[] state = new int[numCourses]; // 0: unvisited, 1: visiting, 2: visited
        int[] result = new int[numCourses];
        index = numCourses - 1; // Start filling the array from the back to avoid explicit reversal

        // Step 2: Run DFS from every node
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == 0) {
                if (hasCycle(adj, state, i, result)) {
                    return new int[0]; // Cycle found, impossible to finish!
                }
            }
        }

        return result;
    }

    private boolean hasCycle(List<List<Integer>> adj, int[] state, int node, int[] result) {
        if (state[node] == 1) return true;  // Hit a node currently in the active path
        if (state[node] == 2) return false; // Already processed, safe

        state[node] = 1; // Mark as Visiting

        for (int neighbor : adj.get(node)) {
            if (hasCycle(adj, state, neighbor, result)) {
                return true;
            }
        }

        state[node] = 2; // Mark as Fully Visited
        
        // This is the only added line!
        // A node is placed in the result array only after ALL its dependencies are resolved
        result[index--] = node; 
        
        return false;
    }
}

```

---

## Why does filling it backward work?

Imagine course `A` is a prerequisite for course `B` (`A -> B`).

* The DFS starts at `A` and steps into `B`.
* `B` has no outgoing edges. Its DFS finishes first.
* `B` transitions to state `2`. It gets placed at the end of the array (`result[last_index] = B`).
* The recursion steps back to `A`. `A` finishes next and gets placed right before `B` (`result[last_index - 1] = A`).

Your final array naturally records `[A, B]`, which is the exact order you need to take them!