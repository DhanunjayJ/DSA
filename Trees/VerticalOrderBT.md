This is the hardest part of the **Vertical Order Traversal** problem. To solve it, you need to track more than just the "vertical" column ($x$); you also need to track the "horizontal" depth ($y$).

In this problem, sorting is required **only if** two nodes have the exact same $x$ (column) **and** the exact same $y$ (row).

### 1. The Strategy: Use a Triple-Coordinate System
Instead of just storing the value in a list, you should store a small object or a tuple that contains:
1.  The **Value** of the node.
2.  The **Row ($y$)** of the node.



### 2. Why your current code is tricky
Your current `HashMap<Integer, List<Integer>>` stores all nodes in a column. However, once they are in that list, you've lost the information about which row they came from. If two nodes are in the same column but different rows, the one on the higher row comes first. If they are in the same row AND same column, you sort them numerically.

### 3. The Solution: Use `PriorityQueue` or `TreeMap`
The most robust way to do this is to use a `TreeMap` of `TreeMap`s. This keeps everything sorted automatically.

*   **Outer TreeMap:** Sorts by Column ($x$).
*   **Inner TreeMap:** Sorts by Row ($y$).
*   **PriorityQueue (at the end):** Sorts the values numerically if $x$ and $y$ are identical.

```java
class Solution {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // <Column, <Row, PriorityQueue<Values>>>
        TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map = new TreeMap<>();
        
        // Helper to perform DFS and fill the map
        dfs(root, 0, 0, map);
        
        List<List<Integer>> result = new ArrayList<>();
        for (TreeMap<Integer, PriorityQueue<Integer>> cols : map.values()) {
            List<Integer> currentColumn = new ArrayList<>();
            for (PriorityQueue<Integer> nodes : cols.values()) {
                // PriorityQueue ensures numerical order for same x and y
                while (!nodes.isEmpty()) {
                    currentColumn.add(nodes.poll());
                }
            }
            result.add(currentColumn);
        }
        return result;
    }

    private void dfs(TreeNode node, int x, int y, TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>> map) {
        if (node == null) return;
        
        if (!map.containsKey(x)) map.put(x, new TreeMap<>());
        if (!map.get(x).containsKey(y)) map.get(x).put(y, new PriorityQueue<>());
        
        map.get(x).get(y).offer(node.val);
        
        dfs(node.left, x - 1, y + 1, map);
        dfs(node.right, x + 1, y + 1, map);
    }
}
```

### Summary of the Sorting Logic
To answer your specific question: **How to sort nodes at the same level?**
1.  **By Row ($y$):** Higher nodes (smaller $y$) always come before lower nodes.
2.  **By Value:** If they have the same $x$ (column) and the same $y$ (row), the `PriorityQueue` inside the map ensures that `poll()` returns the smaller value first.

### Why use `TreeMap`?
Using `TreeMap` means you don't have to track `minlevel` and `maxlevel` yourself; the map keys will always be in order when you iterate through them.


This specific solution—the "Boss Level" nested structure—has a slightly higher complexity than a standard traversal because of all the automatic sorting happening behind the scenes.

Here is the breakdown of the **Time Complexity**:

### 1. The Traversal Phase (DFS)
*   You visit every node exactly once: **$N$ iterations**.
*   Inside each visit, you interact with:
    *   **Outer TreeMap (Columns):** $O(\log W)$ where $W$ is the width of the tree.
    *   **Inner TreeMap (Rows):** $O(\log H)$ where $H$ is the height of the tree.
    *   **PriorityQueue (Values):** $O(\log K)$ where $K$ is the number of nodes at the exact same $(x, y)$ coordinate.
*   **Phase 1 Total:** $O(N \cdot (\log W + \log H))$. Since $W$ and $H$ are both $\le N$, this simplifies to **$O(N \log N)$**.

### 2. The Extraction Phase (Nested Loops)
*   You iterate through every column, every row, and every node to build the final list.
*   Even though there are nested loops, you only process each node **once**.
*   Polling from the `PriorityQueue` takes $O(\log K)$.
*   **Phase 2 Total:** **$O(N \log K)$**.

---

### Total Time Complexity: $O(N \log N)$
In the worst-case scenario (like a skewed tree that looks like a single line), the logarithmic factors dominate. While a standard BFS is $O(N)$, the requirement to sort nodes at the same coordinate pushes this to $O(N \log N)$.

### Space Complexity: $O(N)$
*   **Map Storage:** You are storing every node's value across the nested `TreeMap` and `PriorityQueue` structures. This takes $O(N)$ space.
*   **Recursion Stack:** In the worst case (a skewed tree), the DFS stack depth can reach **$O(N)$**. In a balanced tree, it would be **$O(\log N)$**.

---

### Summary Table

| Operation | Complexity |
| :--- | :--- |
| **Visiting Nodes** | $O(N)$ |
| **TreeMap Insertions** | $O(\log \text{Width} + \log \text{Height})$ |
| **PriorityQueue (Tie-breaking)** | $O(\log K)$ |
| **Overall Time** | **$O(N \log N)$** |
| **Overall Space** | **$O(N)$** |



This complexity is actually the same as the "Coordinate List + Sort" method you saw earlier. Whether you sort *during* the traversal (using TreeMaps) or *after* the traversal (using `Collections.sort`), you can't really escape that **$N \log N$** cost if you need to handle the sorting requirements for nodes at the same level!