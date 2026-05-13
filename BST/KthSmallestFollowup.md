To implement the **Order Statistic Tree** for the follow-up, we need to modify the node structure to store the size of the subtree. This allows us to find the $k$-th smallest element in $O(H)$ time by treating the subtree sizes as an index.

Here is how you would implement the structure and the search logic.

### 1. Augmented Node Structure

We add a `size` field which represents: `1 (itself) + size(left child) + size(right child)`.

```java
class TreeNode {
    int val;
    int size; // Total nodes in this subtree
    TreeNode left, right;

    TreeNode(int x) {
        val = x;
        size = 1;
    }
}

```

### 2. The Solution Class

This approach assumes that during `insert` and `delete` operations, you are correctly updating the `size` property of every node along the path.

```java
class Solution {
    /**
     * Finds the kth smallest element in O(H) time.
     * H is the height of the tree (log N if balanced).
     */
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) return -1;

        // Calculate the size of the left subtree
        int leftSize = (root.left != null) ? root.left.size : 0;

        if (k == leftSize + 1) {
            // Case 1: The current root is the kth element
            return root.val;
        } else if (k <= leftSize) {
            // Case 2: The kth element is in the left subtree
            return kthSmallest(root.left, k);
        } else {
            // Case 3: The kth element is in the right subtree
            // We subtract the left nodes and the current root from k
            return kthSmallest(root.right, k - leftSize - 1);
        }
    }
}

```

---

### How to Maintain the Tree (Insert/Delete)

Since the follow-up mentions the tree is modified often, you must ensure the `size` stays accurate. When you insert a node, you increment the `size` of every ancestor.

**Example Insertion Logic:**

```java
public TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    
    if (val < root.val) {
        root.left = insert(root.left, val);
    } else {
        root.right = insert(root.right, val);
    }
    
    // Update size after recursion returns
    root.size = 1 + getSize(root.left) + getSize(root.right);
    return root;
}

private int getSize(TreeNode node) {
    return (node == null) ? 0 : node.size;
}

```

### Why this is the "Pro" Move:

1. **Speed:** You no longer need to "count" nodes during the search. You simply look at the `size` metadata and decide whether to go left or right.
2. **Frequency:** If you have 10,000 queries for the $k$-th smallest element, this saves you $O(N)$ work every single time.
3. **Scalability:** If this were a balanced BST (like an AVL tree), your search would be a guaranteed **$O(\log N)$**.

Does the logic for why we subtract `leftSize + 1` when moving to the right subtree make sense?