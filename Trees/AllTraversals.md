Doing all three traversals in a single pass is a clever way to see how the stack operates. This is often called the **"All-in-One"** iterative traversal. 

The trick is to store a **State** (or Pair) in the stack along with the node. This state tracks how many times we have "encountered" that specific node:
1.  **State 1 (Preorder):** First time seeing the node. Visit it, increment state, and push its **Left** child.
2.  **State 2 (Inorder):** Second time seeing the node (returning from Left). Visit it, increment state, and push its **Right** child.
3.  **State 3 (Postorder):** Third time seeing the node (returning from Right). Visit it and **Pop** it from the stack.

---

### Java Implementation

```java
import java.util.*;

class Pair {
    TreeNode node;
    int state;

    Pair(TreeNode node, int state) {
        this.node = node;
        this.state = state;
    }
}

public class UniversalTraversal {
    public void allInOne(TreeNode root) {
        if (root == null) return;

        List<Integer> pre = new ArrayList<>();
        List<Integer> in = new ArrayList<>();
        List<Integer> post = new ArrayList<>();

        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(root, 1));

        while (!stack.isEmpty()) {
            Pair top = stack.peek();

            if (top.state == 1) {
                // Preorder: Node -> Left -> Right
                pre.add(top.node.val);
                top.state++;
                if (top.node.left != null) {
                    stack.push(new Pair(top.node.left, 1));
                }
            } else if (top.state == 2) {
                // Inorder: Left -> Node -> Right
                in.add(top.node.val);
                top.state++;
                if (top.node.right != null) {
                    stack.push(new Pair(top.node.right, 1));
                }
            } else {
                // Postorder: Left -> Right -> Node
                post.add(top.node.val);
                stack.pop();
            }
        }

        System.out.println("Preorder: " + pre);
        System.out.println("Inorder: " + in);
        System.out.println("Postorder: " + post);
    }
}
```



---

### Why this works
This simulates the **Euler Tour** of a tree. In a recursive call, the function essentially enters the node, calls left, returns, calls right, and returns again. By using a `state` variable, you are manually tracking which part of that recursive lifecycle you are currently in:
* **Entering** the node (Pre).
* **In-between** children (In).
* **Exiting** the node (Post).

### Complexity
* **Time Complexity:** $O(n)$, but technically we "touch" each node 3 times. Still linear.
* **Space Complexity:** $O(h)$ for the stack, plus the storage for the three result lists.

This is arguably the most elegant way to handle all three because the logic for moving between nodes is handled identically for every traversal type. Does the "state" logic feel easier to manage than the individual algorithms we discussed?