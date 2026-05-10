Morris Inorder Traversal is a clever algorithm that allows you to traverse a binary tree without using extra space for a stack or recursion. In a standard inorder traversal ($Left \rightarrow Root \rightarrow Right$), you typically need $O(n)$ space to keep track of the nodes you need to return to. Morris traversal reduces this space complexity to $O(1)$.

---

## How It Works: The "Threaded" Logic

The core idea is to utilize the **null pointers** in the tree's leaf nodes. In a binary tree, many nodes have a `null` right child. Morris traversal temporarily points these `null` pointers to the node’s **inorder successor** (the ancestor it should visit next).

### The Step-by-Step Process:

1. **Start at the Root:** Initialize `current` as the root.
2. **Check the Left Child:**
* If `current` has no left child, visit `current` and move to its right child.
* If `current` **has** a left child, find the **Inorder Predecessor** (the rightmost node in the left subtree).


3. **Create or Break a "Thread":**
* **Case A:** If the predecessor's right child is `null`, point it to `current` (creating a thread). Then move `current` to its left child.
* **Case B:** If the predecessor's right child is already pointing to `current`, it means we’ve finished the left subtree. Set the right child back to `null` (break the thread), visit `current`, and move to its right child.



---

## Why is it "Better"?

"Better" is usually defined by resource efficiency. Here is how Morris Traversal compares to standard recursive or stack-based methods:

### 1. Space Complexity: $O(1)$

This is the main selling point.

* **Standard Traversal:** Even if you don't see a stack in recursion, the "Call Stack" uses $O(h)$ space, where $h$ is the height of the tree. In the worst case (a skewed tree), this is $O(n)$.
* **Morris Traversal:** It uses no extra data structures and no recursion. It only uses a few temporary pointers, making it truly constant space.

### 2. No Stack Overflow

Since there is no recursion, you don't have to worry about crashing the program with a `StackOverflowError` if the tree is exceptionally deep or skewed.

### 3. Time Complexity: $O(n)$

While it might seem slower because it visits some nodes multiple times (to find predecessors), each edge in the tree is traversed at most 3 times. Mathematically, the complexity remains linear, $O(n)$.

---

## The Trade-offs

While memory-efficient, Morris Traversal isn't always the "go-to" for every situation:

* **Modifies the Tree:** It temporarily changes the structure of the tree. If your environment is multi-threaded, or if the tree must remain read-only/immutable, Morris traversal can cause issues unless you are very careful to restore the `null` pointers.
* **Code Complexity:** It is significantly harder to write and debug compared to a simple 3-line recursive function.

> **Summary:** Use Morris Traversal when you are working in a **memory-constrained environment** where $O(n)$ or even $O(\log n)$ stack space is a luxury you cannot afford.

Preorder traversal ($Root \rightarrow Left \rightarrow Right$) can also be performed using Morris’s logic with $O(1)$ space. The implementation is almost identical to the inorder version, with one key difference: **when** you visit the node.

---

## The Logic Shift

In **Inorder**, you visit the node after returning from the left subtree (when you break the thread).
In **Preorder**, you visit the node the moment you first encounter it—specifically, when you first create the "thread" to the inorder successor.

### The Step-by-Step Process:

1. **Start at the Root:** Initialize `current` as the root.
2. **Check the Left Child:**
* If `current` has no left child: **Visit** `current`, then move to its right child.
* If `current` **has** a left child: Find the **Inorder Predecessor** (rightmost node in the left subtree).


3. **Handle the Predecessor:**
* **Case A (New Thread):** If the predecessor's right child is `null`:
* **Visit `current` now.** (This ensures the root is processed before the subtrees).
* Set the predecessor's right child to `current`.
* Move `current` to its left child.


* **Case B (Break Thread):** If the predecessor's right child is already `current`:
* Set the right child back to `null` (restoring the tree).
* Move `current` to its right child.





---

## Comparison: Inorder vs. Preorder Morris

| Feature | Morris Inorder | Morris Preorder |
| --- | --- | --- |
| **Visit Timing** | When breaking the thread (Case B). | When creating the thread (Case A). |
| **No Left Child** | Visit and move right. | Visit and move right. |
| **Space Complexity** | $O(1)$ | $O(1)$ |
| **Tree Restoration** | Restores tree after visiting left. | Restores tree after visiting left. |

---

## Why use Morris Preorder?

Similar to the inorder version, it is objectively "better" in terms of **memory management**.

* **Embedded Systems:** If you are working with limited RAM where even a small stack could cause a crash, this algorithm is a lifesaver.
* **Large-Scale Trees:** For trees with millions of nodes, avoiding $O(h)$ auxiliary space saves significant overhead.

The primary disadvantage remains the same: it is slightly more complex to implement than the standard iterative stack-based preorder traversal, and it involves temporary "mutation" of the tree structure.