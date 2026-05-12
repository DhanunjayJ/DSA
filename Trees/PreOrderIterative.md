Implementing the iterative preorder traversal in Java follows the same stack-based logic. Since Java's `Stack` class is generally considered legacy, it is best practice to use `Deque` (Double Ended Queue) via the `ArrayDeque` implementation for better performance.

### Java Implementation

```java
import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class BinaryTreeTraversal {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // Using Deque as a stack for better performance
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            // 1. Pop the top node
            TreeNode node = stack.pop();
            result.add(node.val);

            // 2. Push right child first (so left is on top)
            if (node.right != null) {
                stack.push(node.right);
            }

            // 3. Push left child
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return result;
    }
}
```

---

### Key Java Details to Remember

* **`ArrayDeque` vs `Stack`**: The `java.util.Stack` class extends `Vector`, which is synchronized. This adds unnecessary overhead in a single-threaded environment. `ArrayDeque` is faster and more modern.
* **Null Checks**: Always check if the `root` is null at the start to avoid a `NullPointerException`.
* **The LIFO Property**: In the code above, `stack.push(node.right)` happens before `stack.push(node.left)`. This ensures that when we call `stack.pop()` in the next iteration, the **left** node comes out first, satisfying the Preorder requirement.



### Performance Summary
| Metric | Complexity | Notes |
| :--- | :--- | :--- |
| **Time** | $O(n)$ | Each node is pushed and popped exactly once. |
| **Space** | $O(h)$ | $h$ is the height; proportional to the number of nodes stored in the stack. |

