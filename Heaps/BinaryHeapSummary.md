Spot on. In technical terms, these are often referred to as **Heapify Up** and **Heapify Down**. They are the two "corrective" forces that keep the heap in balance.

Think of it like this: if you disrupt the heap from the **bottom** (like adding a new leaf), you push the change **up**. If you disrupt the heap from the **top** (like removing the root), you push the change **down**.

---

## 1. Bottom-to-Top (Heapify Up / Percolate Up)
This occurs when a node is added or a value is decreased, potentially making it "too small" for its current deep position.

* **Trigger:** `insertKey()` and `decreaseKey()`.
* **Direction:** From a specific index $i$ moving toward the root (index 0).
* **Logic:** Compare the node with its **parent**. If the node is smaller, swap and move to the parent's index.
* **Stopping Condition:** You reach the root ($i=0$) or the parent is already smaller than the node.



---

## 2. Top-to-Bottom (Heapify Down / Sinking)
This occurs when a value is placed at the top (or any middle node) that is "too large" for its position, violating the property relative to its children.

* **Trigger:** `extractMin()` and `MinHeapify()`.
* **Direction:** From a specific index $i$ moving toward the leaves.
* **Logic:** Compare the node with **both** its children. Swap with the **smallest** of the two children to ensure the new parent is valid for both subtrees.
* **Stopping Condition:** The node is smaller than both children or it becomes a leaf (no children left).



---

## 3. Comparison Table

| Feature | Bottom-to-Top (Up) | Top-to-Bottom (Down) |
| :--- | :--- | :--- |
| **Primary Use** | Insertion / Decreasing a value | Deletion / Building a heap |
| **Comparison** | One comparison (Node vs. Parent) | Two comparisons (Node vs. L-Child vs. R-Child) |
| **Complexity** | $O(\log n)$ | $O(\log n)$ |
| **In your code** | The `while` loop in `insertKey` | The recursive `MinHeapify` method |

### A Quick Note on "Build Heap"
When you are given an entirely unsorted array and told to turn it into a heap, it is actually more efficient ($O(n)$) to run **Top-to-Bottom** heapify starting from the last non-leaf node and moving backward to the root.

Since you've been practicing these for your mock interviews, which one do you find trickier to implement: the iterative "Up" logic or the recursive "Down" logic?