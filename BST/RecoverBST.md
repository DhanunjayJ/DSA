This is the classic **"Recover Binary Search Tree"** problem. It's a great question because that specific detail—setting `second = curr` twice under different conditions—is the secret sauce of the entire algorithm.

In short: It handles whether the two swapped nodes are **adjacent** (right next to each other) or **non-adjacent** (separated by other numbers) in the inorder traversal.

Let's look at why this happens by breaking it down with examples.

---

### The Core Logic of an Inorder BST

In a correctly sorted BST, an inorder traversal should give us strictly increasing numbers (e.g., `1, 2, 3, 4, 5`).
When exactly **two** nodes are swapped, it creates **drops** in the sequence where a number is smaller than the previous one (`curr.val < prev.val`).

* **The 1st Drop:** The larger swapped element pushed *forward* creates a drop. So, `first = prev`.
* **The 2nd Drop:** The smaller swapped element pushed *backward* creates another drop. So, `second = curr`.

Here is how the code adapts beautifully to whether those drops happen together or apart.

---

### Case 1: The Swapped Nodes are Non-Adjacent (Two Drops)

Let’s take a correct BST sequence: `1, 2, 3, 4, 5`.
Now, let's swap two non-adjacent nodes: **2** and **5**.
The broken sequence becomes: `1, 5, 3, 4, 2`.

Let's trace the code as it finds the drops:

| `prev` | `curr` | Condition (`curr.val < prev.val`)? | What happens in code? |
| --- | --- | --- | --- |
| `1` | `5` | No ($5 > 1$) | Just move on. |
| `5` | `3` | **Yes!** ($3 < 5$) | This is the **first time** we see a drop (`first == null`). <br>

<br> `first = prev` (5)<br>

<br>`second = curr` (3) |
| `3` | `4` | No ($4 > 3$) | Just move on. |
| `4` | `2` | **Yes!** ($2 < 4$) | This is the **second time** we see a drop (`first != null`). <br>

<br> It goes to the `else if` block.<br>

<br>`second = curr` (2) |

**Result:** `first` is 5, `second` is 2. They swap, and the tree is fixed!
*Notice how `second` was temporarily 3, but got overwritten by 2 on the second drop. This is exactly what we want.*

---

### Case 2: The Swapped Nodes are Adjacent (Only One Drop)

Let’s take the same correct sequence: `1, 2, 3, 4, 5`.
This time, let's swap two adjacent nodes: **3** and **4**.
The broken sequence becomes: `1, 2, 4, 3, 5`.

Let's trace the code:

| `prev` | `curr` | Condition (`curr.val < prev.val`)? | What happens in code? |
| --- | --- | --- | --- |
| `2` | `4` | No ($4 > 2$) | Just move on. |
| `4` | `3` | **Yes!** ($3 < 4$) | This is the **first time** we see a drop (`first == null`). <br>

<br> `first = prev` (4)<br>

<br>`second = curr` (3) |
| `3` | `5` | No ($5 > 3$) | Traversal finishes. |

Because the nodes were right next to each other, **there is no second drop**.

If the code *only* set `first = prev` and didn't touch `second` during the first drop, `second` would remain `null`, and the code would crash or fail. By setting `second = curr` on the very first drop, the code safely covers the possibility that the two swapped elements are adjacent.

---

### Summary

Think of `second = curr` in the first `if` block as a **safety net**.

The code assumes the swapped nodes *might* be adjacent, so it grabs `curr` immediately. If it turns out they weren't adjacent, the `else if` block triggers later on, updates `second` with the actual final drop, and corrects the mistake.