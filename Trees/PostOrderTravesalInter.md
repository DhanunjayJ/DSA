Postorder traversal (**Left $\rightarrow$ Right $\rightarrow$ Root**) is the most challenging of the three to implement iteratively because the "Root" must be processed last, even though you encounter it first.

---

## 1. Using Two Stacks (The "Reverse" Trick)
This is the most intuitive way to handle postorder. The trick is to realize that Postorder is just the **reverse** of a modified Preorder (Root $\rightarrow$ Right $\rightarrow$ Left).

### The Logic
1.  Push the root to **Stack 1**.
2.  While Stack 1 is not empty:
    * Pop from Stack 1 and push it to **Stack 2**.
    * Push the **left** child of the popped node to Stack 1.
    * Push the **right** child of the popped node to Stack 1.
3.  Once Stack 1 is empty, Stack 2 contains all nodes in Postorder. Pop everything from Stack 2 to get the result.



### Java Implementation
```java
public List<Integer> postorderTwoStacks(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    Deque<TreeNode> s1 = new ArrayDeque<>();
    Deque<TreeNode> s2 = new ArrayDeque<>();

    s1.push(root);
    while (!s1.isEmpty()) {
        TreeNode node = s1.pop();
        s2.push(node);

        if (node.left != null) s1.push(node.left);
        if (node.right != null) s1.push(node.right);
    }

    while (!s2.isEmpty()) {
        result.add(s2.pop().val);
    }
    return result;
}
```

---

## 2. Using One Stack
This is more complex because you need to distinguish between moving down to a child and moving back up from a child. You only "visit" the node if you are returning from its **right** child (or if there is no right child).

### The Logic
We use a pointer `lastVisited` to keep track of the node we just processed.

1.  Keep pushing **left** nodes onto the stack until you hit `null`.
2.  Peek at the top node of the stack.
3.  If the top node has a **right** child and it hasn't been visited yet (`node.right != lastVisited`), move to that right child and repeat step 1.
4.  Otherwise, the left and right subtrees are done: **Pop** the node, visit it, and mark it as `lastVisited`.



### Java Implementation
```java
public List<Integer> postorderOneStack(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    TreeNode lastVisited = null;

    while (curr != null || !stack.isEmpty()) {
        if (curr != null) {
            stack.push(curr);
            curr = curr.left; // Always try to go left first
        } else {
            TreeNode peekNode = stack.peek();
            // If right child exists and traversing node from left child, 
            // then move to right child
            if (peekNode.right != null && lastVisited != peekNode.right) {
                curr = peekNode.right;
            } else {
                // Visit the node
                result.add(peekNode.val);
                lastVisited = stack.pop();
            }
        }
    }
    return result;
}
```

---

### Summary Comparison

| Method | Space Complexity | Difficulty | Intuition |
| :--- | :--- | :--- | :--- |
| **2 Stacks** | $O(n)$ | Easy | Postorder is just reversed "Root-Right-Left". |
| **1 Stack** | $O(h)$ | Hard | Requires tracking the `lastVisited` node. |

The **Two Stacks** approach is usually preferred in interviews unless the interviewer specifically asks you to optimize the space to $O(h)$ by using only one stack.

Which of these three iterative traversals (Pre, In, or Post) feels the most natural to you now?