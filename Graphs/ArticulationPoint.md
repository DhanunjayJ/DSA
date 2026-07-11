An **Articulation Point** (or cut vertex) is a node that, if removed along with its edges, splits the remaining graph into two or more disconnected pieces.

The mechanism here is almost identical to finding bridges (`disc`, `low`, and back-edges), but with two critical differences in the logic. Let's look at why your code tracks articulation points slightly differently.

---

## 1. The Core Condition Change: `>=` instead of `>`

For a **bridge**, the condition is `low[nbr] > disc[src]`.
For an **articulation point**, your code checks:

```java
if (low[nbr] >= disc[src])

```

### Why the `=` matters:

Imagine a simple cycle graph: `0 - 1 - 2 - 0`.
If you remove the *edge* `(1, 2)`, the nodes are still connected via `1 - 0 - 2`. So no edge is a bridge.
However, if you remove the *node* `1`, node `0` and node `2` are now completely cut off from each other!

When a neighbor's `low` value equals your `disc` value (`low[nbr] == disc[src]`), it means the neighbor can loop back *exactly* to you, but no higher. If you disappear, that loop is broken, leaving that neighbor stranded.

---

## 2. The Two Separate Cases (Root vs. Non-Root)

An articulation point behaves differently depending on whether it is the **root** of your DFS tree (the first node you called the function on) or a **non-root** node. Your code handles this explicitly using `par == -1`.

### Case A: The Node is NOT the Root (`par != -1`)

This uses the standard condition:

```java
if (low[nbr] >= disc[src]) {
    ans[src] = true;
}

```

If any child branch cannot reach an ancestor above you, removing you isolates that entire branch.

### Case B: The Node IS the Root (`par == -1`)

The `low[nbr] >= disc[src]` logic **fails** for the root node. Why? Because the root has a discovery time of `0`. *Every single node* in the graph will have a `low` value $\ge 0$. If we used the standard rule, the root would almost always be falsely flagged as an articulation point.

Instead, the root cares about one thing only: **Independent Children Count (`count`)**.

```java
if (par == -1) {
    if (count >= 2) {
        ans[src] = true;
    }
}

```

* **What `count` actually tracks:** `count` only increments when a neighbor is **unvisited** (`vis[nbr] == false`). This means it tracks entirely separate, independent branches.
* If the root has `count >= 2`, it means the DFS had to completely finish exploring one branch, return to the root, and kickstart a brand new branch that couldn't be reached any other way. If you delete the root, those two branches are instantly severed from each other.

---

## 3. One Important Structural Bug to Fix

Your code has a lurking bug that will cause it to break if the graph is **disconnected** (i.e., contains multiple separate components).

Right now, your initialization only calls `bridge(0, -1)`. If the graph has nodes that aren't reachable from node `0`, those nodes will never be visited, and you will miss their articulation points.

### The Fix

In your main `articulationPoints` method, you should loop through all vertices to ensure every component gets explored:

```java
// Replace bridge(0, -1); with this loop:
for (int i = 0; i < V; i++) {
    if (!vis[i]) {
        bridge(i, -1);
    }
}

```

This ensures full graph coverage regardless of how the network is structured!

---

Here is the correct, fully optimized code for finding Articulation Points.

This version includes the fix for disconnected graphs (looping through all nodes in the main function) and ensures the logic handles all edge cases perfectly.

```java
import java.util.*;

class Solution {
    int[] disc;
    int[] low;
    boolean[] vis;
    boolean[] isArticulation;
    int time = 0;
    
    public ArrayList<Integer> articulationPoints(int V, ArrayList<ArrayList<Integer>> adj) {
        disc = new int[V];
        low = new int[V];
        vis = new boolean[V];
        isArticulation = new boolean[V]; // Prevents adding the same node multiple times
        
        // Loop through all vertices to handle disconnected graphs
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                findAPs(i, -1, adj);
            }
        }
        
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (isArticulation[i]) {
                result.add(i);
            }
        }
        
        // If no articulation point is found, return [-1] as per standard problem specs
        if (result.isEmpty()) {
            result.add(-1);
        }
        
        return result;
    }
    
    private void findAPs(int src, int par, ArrayList<ArrayList<Integer>> adj) {
        vis[src] = true;
        disc[src] = time;
        low[src] = time;
        time++;
        
        int childCount = 0; // Tracks independent DFS branches from 'src'
        
        ArrayList<Integer> nbrs = adj.get(src);
        for (int nbr : nbrs) {
            if (nbr == par) {
                continue; // Skip the edge pointing back to the immediate parent
            }
            
            if (!vis[nbr]) {
                childCount++;
                findAPs(nbr, src, adj);
                
                // Upon return, update low value of current node
                low[src] = Math.min(low[src], low[nbr]);
                
                // Case 1: Root Node condition
                if (par == -1) {
                    if (childCount >= 2) {
                        isArticulation[src] = true;
                    }
                } 
                // Case 2: Non-root Node condition
                else {
                    if (low[nbr] >= disc[src]) {
                        isArticulation[src] = true;
                    }
                }
            } else {
                // Back-edge found: update low using neighbor's discovery time
                low[src] = Math.min(low[src], disc[nbr]);
            }
        }
    }
}

```

### What makes this implementation bulletproof?

* **`isArticulation` array:** Using a boolean array instead of directly pushing to a list avoids duplicates. If a node has 3 separate child branches that all satisfy the `low[nbr] >= disc[src]` condition, it would be added 3 times to a list. The boolean array keeps it clean.
* **Global Graph Loop:** By putting the `!vis[i]` check in the main method, this code will successfully find articulation points even if the input graph is split into separate islands.

---

You are *so* close here! This code structure is excellent, and you've successfully fixed the disconnected graph bug from your earlier versions.

However, there is **one logical flaw** in your `bridges` method that will cause it to incorrectly flag the **root node** (when `par == -1`) as an articulation point when it shouldn't be.

---

## The Issue: The Root Node Override

Take a look at how your `if` statements are arranged inside the `if (!vis[nbr])` block:

```java
if (par == -1) {
    if (count > 1) {
        isArticulation[src] = true;
    }
}

if (low[nbr] >= disc[src]) {
    isArticulation[src] = true;
}

```

### Why this breaks:

Even if `par == -1` and `count` is only `1` (meaning the root should *not* be marked as an articulation point), your code immediately moves to the next `if` statement right below it:

```java
if (low[nbr] >= disc[src])

```

Because the root node is the very first node visited, its `disc[src]` is `0`. **Every** child node's `low` value will be $\ge 0$.

As a result, the second `if` statement will evaluate to `true` for the root node *every single time*, completely overriding your root node `count` logic!

---

## The Fix

You need to put these two conditions into an `if-else` structure. If a node is the root, it should *only* be evaluated by its child count. If it is not the root, it should *only* be evaluated by the `low[nbr] >= disc[src]` threshold.

### Corrected Code Snippet:

Modify that specific section inside your loop like this:

```java
// Case 1: If it's the root node, ONLY look at the independent child count
if (par == -1) {
    if (count > 1) {
        isArticulation[src] = true;
    }
} 
// Case 2: For any other node, look at the discovery time threshold
else {
    if (low[nbr] >= disc[src]) {
        isArticulation[src] = true;
    }
}

```

### Alternative Clean Code

Alternatively, you can group them into a single condition like this to keep it compact:

```java
if ((par == -1 && count > 1) || (par != -1 && low[nbr] >= disc[src])) {
    isArticulation[src] = true;
}

```

Once you make this change to separate the Root logic from the Non-Root logic, your solution will pass flawlessly!