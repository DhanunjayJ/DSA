Iterative **Inorder** traversal is a bit more nuanced than Preorder because you cannot visit the node immediately when you see it. You must dive as deep as possible into the **Left** subtree first, visit the node, and then move to the **Right**.

The order is **Left $\rightarrow$ Root $\rightarrow$ Right**.

---

### The Logic
Think of it like this: "Go left until you can't, visit, then take one step right and repeat."

1.  **Initialize:** Create an empty stack and set a pointer `curr` to the `root`.
2.  **Traverse Left:** While `curr` is not null, push `curr` to the stack and move to `curr.left`.
3.  **Process & Pivot:** Once you hit a null (the end of the left line):
    * Pop from the stack.
    * Visit/print the popped node.
    * Set `curr` to the **right** child of the popped node.
4.  **Repeat:** Continue until the stack is empty **and** `curr` is null.



---

### Java Implementation

```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;

    // Notice the 'OR' condition: we might have an empty stack 
    // but still need to explore a right subtree.
    while (curr != null || !stack.isEmpty()) {
        
        // 1. Reach the leftmost node of the current subtree
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }

        // 2. Current must be null at this point, so we pop from stack
        curr = stack.pop();
        result.add(curr.val); // Inorder: Visit after left is done

        // 3. We have visited the node and its left subtree. 
        // Now, it's the right subtree's turn.
        curr = curr.right;
    }

    return result;
}
```

---

### Key Differences from Preorder
* **When to Visit:** In Preorder, we visit the node the moment we pop it (or even before pushing it). In Inorder, we only visit it **after** we've finished the `while(curr != null)` loop that exhausts the left side.
* **The Outer Loop:** Preorder only needs `while(!stack.isEmpty())`. Inorder needs `while(curr != null || !stack.isEmpty())` because at several points the stack will be empty while we are still transitioning to a right-side branch.

### Complexity
* **Time:** $O(n)$ — Every node is pushed and popped exactly once.
* **Space:** $O(h)$ — The stack stores the path from the root to the current leaf, which is the height of the tree.

