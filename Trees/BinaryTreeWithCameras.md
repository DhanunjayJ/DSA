The intuition for **968. Binary Tree Cameras** comes down to one powerful realization: **Greedy choices work best from the bottom up.**

If you start from the root and work your way down, you don't know what's happening below. But if you start at the leaves, the decision is incredibly simple.

Here is how to think about it step-by-step.

---

## 1. The Leaf Node Epiphany

Imagine a leaf node (a node with no children). Where should we put a camera to cover it?

* **Option A:** Put a camera on the leaf itself. It covers the leaf and its parent.
* **Option B:** Put a camera on the leaf's *parent*. It covers the leaf, the parent, the parent's *other* children, and the parent's parent.

**Option B is always better.** Placing a camera higher up the tree maximizes its coverage. Therefore, **we should never place a camera on a leaf node.** We should always let its parent handle it.

---

## 2. The 3 States of a Node

Because we are working from the bottom up (Post-order Traversal: Left, Right, then Root), every node needs to tell its parent its current situation. A node can be in one of three states:

1. **`0` = UNCOVERED:** The node needs a camera. It is telling its parent, *"Hey, I am exposed. You need to put a camera on yourself to cover me."*
2. **`1` = HAS_CAMERA:** The node has a camera installed on it. It tells its parent, *"I've got a camera, so you are automatically covered by me."*
3. **`2` = COVERED:** The node does not have a camera, but it is being watched by one of its children. It tells its parent, *"I'm good, you don't need to worry about me. But I don't have a camera to share with you either."*

---

## 3. The Bottom-Up Decision Logic

When you are at a parent node, you look at the states of your left and right children and make a choice based on their needs:

* **Case 1: If any child is UNCOVERED (`0`)**
* If a child is naked, the parent *must* place a camera here.
* *Action:* Increment camera count. Return **`1` (HAS_CAMERA)**.


* **Case 2: If any child HAS_CAMERA (`1`)**
* If at least one child has a camera, this parent node is now safely covered.
* *Action:* Return **`2` (COVERED)**.


* **Case 3: If all children are COVERED (`2`)**
* If the children are covered but don't have cameras themselves (e.g., they are covered from below), this parent node is currently vulnerable and completely uncovered. But remember our leaf epiphany? We shouldn't put a camera here yet; we should wait for *this* node's parent to cover it.
* *Action:* Return **`0` (UNCOVERED)**.



> **What about `null` nodes?** > Virtual `null` children (below the leaves) should be treated as **`2` (COVERED)**. Why? Because we don't want to waste a camera on a leaf node just because of a `null` child. Treat `null` as safely out of the equation.

---

## 4. The Edge Case: The Root

After the recursive function finishes checking the whole tree, it returns the state of the `root` node.
If the root returns **`0` (UNCOVERED)**, there is no parent above it to save it! In this final case, we must force a camera onto the root node itself.

---

## Clean Java Implementation

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    // Global or class-level variable to keep track of the total cameras needed
    private int cameras = 0;

    public int minCameraCover(TreeNode root) {
        // If the root node itself is left uncovered after the traversal, 
        // we must place a camera directly on the root.
        if (dfs(root) == 0) {
            cameras++;
        }
        return cameras;
    }

    /**
     * Helper function that performs a bottom-up post-order traversal.
     * Returns the state of the current node:
     * 0 -> UNCOVERED (Needs a camera)
     * 1 -> HAS_CAMERA (Has a camera installed)
     * 2 -> COVERED (Watched by a child's camera)
     */
    private int dfs(TreeNode node) {
        // Base case: Null nodes are considered "covered" so they don't trick 
        // leaf nodes into placing unnecessary cameras.
        if (node == null) {
            return 2;
        }

        int left = dfs(node.left);
        int right = dfs(node.right);

        // Case 1: If any child is uncovered, this parent MUST install a camera.
        if (left == 0 || right == 0) {
            cameras++;
            return 1; // This node now has a camera
        }

        // Case 2: If at least one child has a camera, this parent is safely covered.
        if (left == 1 || right == 1) {
            return 2; // This node is covered
        }

        // Case 3: If both children are covered but neither has a camera (they return 2),
        // this parent is currently uncovered and must rely on its own parent above.
        return 0;
    }
}
```

### Complexity

* **Time Complexity:** $O(N)$ because we visit every node exactly once.
* **Space Complexity:** $O(H)$ where $H$ is the height of the tree, due to the recursion stack.