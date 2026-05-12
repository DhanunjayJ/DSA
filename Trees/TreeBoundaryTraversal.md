You don’t necessarily need to perform three *full* tree traversals (visiting every single node three times), but you do need **three specific sub-routines**.

If you think of a "full traversal" as visiting every node (like Level-order or In-order), then you only do that **once** to find the leaves. The boundaries are much faster because you are only "skimming" the edges.

Here is the breakdown of how to structure it:

### 1. The Root
Add `root.data` to your result list first. 
*   **Edge Case:** If the root is a leaf node (no left or right), just return the root and stop. You don't want to process boundaries if they don't exist.

### 2. The Left Boundary (Top-Down)
Start at `root.left`. 
*   Travel down. If a node has a left child, go left. If it only has a right child, go right.
*   **Action:** Add the node to your list *before* moving to the next one.
*   **Stop:** Before you hit a leaf.

### 3. The Leaves (Left-to-Right)
This is the only part where you do a **full traversal** (usually a simple Pre-order or In-order).
*   **Action:** Visit every node in the tree.
*   **Condition:** If `node.left == null && node.right == null`, add it to the list.
*   **Note:** By using a standard DFS (Depth First Search), you naturally find them in the correct left-to-right order.



### 4. The Right Boundary (Bottom-Up)
Start at `root.right`.
*   Travel down. If a node has a right child, go right. If it only has a left child, go left.
*   **Action:** You need these in **reverse order** (from the leaf up to the root).
*   **How to code it:**
    *   **Option A:** Use a Stack to store them and pop them off at the end.
    *   **Option B (Cleaner):** Use recursion. Make the recursive call first, and add the node to the list *after* the call returns. This naturally reverses the order.

---

### Why we do it this way:
If you try to use one standard traversal, you'll run into "duplicate" problems. For example, the leftmost leaf is technically part of the Left Boundary *and* the Leaf list. By writing three separate functions, you can easily control the logic to "skip" the leaf nodes during the boundary checks, ensuring each node appears in your `ans` list exactly once.

**Does the "Bottom-Up" logic for the right boundary make sense, or would you like to see how that specific recursion looks?**

Here is the implementation using the three-part strategy. Notice how we use separate helper methods for the left boundary, the leaves, and the right boundary to keep the logic clean and avoid duplicates.



```java
class Solution {
    public ArrayList<Integer> boundaryTraversal(Node root) {
        ArrayList<Integer> res = new ArrayList<>();
        if (root == null) return res;

        // 1. Add root (if it's not a leaf)
        if (!isLeaf(root)) {
            res.add(root.data);
        } else {
            res.add(root.data);
            return res; // Root is the only node
        }

        // 2. Add Left Boundary (excluding leaf)
        addLeftBoundary(root.left, res);

        // 3. Add all Leaves
        addLeaves(root, res);

        // 4. Add Right Boundary in reverse (excluding leaf)
        addRightBoundary(root.right, res);

        return res;
    }

    private boolean isLeaf(Node node) {
        return node.left == null && node.right == null;
    }

    private void addLeftBoundary(Node curr, ArrayList<Integer> res) {
        while (curr != null) {
            if (!isLeaf(curr)) res.add(curr.data);
            if (curr.left != null) curr = curr.left;
            else curr = curr.right;
        }
    }

    private void addLeaves(Node curr, ArrayList<Integer> res) {
        if (curr == null) return;
        if (isLeaf(curr)) {
            res.add(curr.data);
            return;
        }
        addLeaves(curr.left, res);
        addLeaves(curr.right, res);
    }

    private void addRightBoundary(Node curr, ArrayList<Integer> res) {
        Stack<Integer> stack = new Stack<>();
        while (curr != null) {
            if (!isLeaf(curr)) stack.push(curr.data);
            if (curr.right != null) curr = curr.right;
            else curr = curr.left;
        }
        // Pop from stack to get reverse order
        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
    }
}
```

### Key Highlights of this Code:
*   **The `isLeaf` Check:** This is crucial. It prevents the leftmost leaf from being added twice (once by the left boundary and once by the leaf function).
*   **The Right Boundary Stack:** Since we need the right side from bottom-to-top, we push the nodes onto a stack and then pop them into our final list. You could also do this with recursion by adding the value *after* the recursive call.
*   **Preference Logic:** In both boundaries, we prioritize the "outward" direction. For the left boundary, we check `left` first; if it's null, we "stretch" to the `right`.

### Complexity:
*   **Time Complexity:** $O(N)$ because we visit every node at most a couple of times.
*   **Space Complexity:** $O(H)$ for the recursion stack and the boundary stack, where $H$ is the height of the tree.

