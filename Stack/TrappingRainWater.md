To solve **Trapping Rain Water** using a **Monotonic Decreasing Stack**, you aren't looking for the "global maximum" to the left or right. Instead, you are looking for the **immediate boundaries** that form a "pit" or a "container."

Think of it as filling the water **horizontally** (row by row) rather than vertically (column by column).

### How the Stack Works
You maintain a stack of indices where the heights are in **decreasing order**. 

1.  **Iterate** through the heights.
2.  **While** the current height is **greater** than the height at the stack's top index:
    * This means the top of the stack is a "pit" because it has a taller bar to its left (the element below it in the stack) and a taller bar to its right (the current element).
    * **Pop** the top element; let's call its index `mid`. This is the bottom of our container.
    * **If the stack is empty** after popping, there is no left boundary, so no water can be trapped. Break the loop.
    * **Otherwise**, the new top of the stack is the `left_boundary` index. The current index is the `right_boundary`.
3.  **Calculate the Water:**
    * **Height of water:** $h = \min(\text{height}[left\_boundary], \text{height}[right\_boundary]) - \text{height}[mid]$
    * **Width of water:** $w = right\_boundary - left\_boundary - 1$
    * **Add to total:** $\text{Total} += h \times w$
4.  **Push** the current index onto the stack and move to the next bar.



### Why this is different from NGE
In a standard **Next Greater Element** problem, you just find the right boundary and you're done. In this problem, the stack helps you find **three** things at once:
* The **bottom** of the pit (`mid`).
* The **left wall** (the element below `mid` in the stack).
* The **right wall** (the current element).

By calculating the area this way, you are essentially "filling" the gaps layer by layer from the bottom up.

---

### Comparison Table

| Feature | Prefix/Suffix Max (Two Arrays) | Monotonic Stack |
| :--- | :--- | :--- |
| **Calculation** | Vertical (column by column) | Horizontal (layer by layer) |
| **Passes** | Two or Three passes | One single pass |
| **Space** | $O(N)$ for arrays | $O(N)$ for stack |
| **Logic** | Finds absolute highest peaks | Finds immediate boundaries |

