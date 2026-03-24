Your code is a clever implementation of the **Encoding/Difference Method**. Instead of storing the actual numbers, you are storing the **relative difference** between the current value and the current minimum. This allows you to track the history of the minimum without a second stack.

Here is the detailed breakdown and a dry run to make it crystal clear.

---

### How the Logic Works

1.  **The "Difference" Formula**: You calculate `diff = val - min`. 
    * If `val >= min`, the difference is **positive** (or zero). We push this and keep the current `min`.
    * If `val < min`, the difference is **negative**. This is our "Flag." It tells us that the minimum has just changed. We push this negative `diff` and update `min = val`.
2.  **The `top()` Recovery**:
    * If the top of the stack (`rem`) is positive, the actual value is just `min + rem`.
    * If `rem` is negative, we know this position *is* the current minimum. So we just return `min`.
3.  **The `pop()` Restoration**:
    * If we pop a negative number, it means the minimum is about to change back to what it was before. We restore it using `min = min - rem`. (Since `rem` is negative, subtracting it actually increases the `min` value).

---

### Dry Run Example (7 Values)

Let's use a mix of decreasing and increasing values: **[10, 12, 5, 8, 3, 15, 3]**.



| Step | Operation | `val` | Action | Stack (Top at right) | `min` | Logic |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | Push | 10 | First element | `[0]` | 10 | `st.push(0L)`, `min = 10` |
| 2 | Push | 12 | $12 - 10 = 2$ | `[0, 2]` | 10 | `diff` is positive, `min` stays 10 |
| 3 | Push | 5 | $5 - 10 = -5$ | `[0, 2, -5]` | **5** | `diff` is negative, **Update min to 5** |
| 4 | Push | 8 | $8 - 5 = 3$ | `[0, 2, -5, 3]` | 5 | `diff` is positive, `min` stays 5 |
| 5 | Push | 3 | $3 - 5 = -2$ | `[0, 2, -5, 3, -2]` | **3** | `diff` is negative, **Update min to 3** |
| 6 | Push | 15 | $15 - 3 = 12$ | `[0, 2, -5, 3, -2, 12]` | 3 | `diff` is positive, `min` stays 3 |
| 7 | **getMin**| - | Return `min` | - | **3** | Returns the current `min` variable |

---

### Reverse Dry Run (Popping them back)

Now let's see how the stack "heals" the `min` variable as we pop.

| Step | Operation | `rem` (popped) | Action | Stack Left | New `min` | Logic |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 8 | **pop** | 12 | `rem > 0` | `[0, 2, -5, 3, -2]` | 3 | Normal pop, `min` stays 3 |
| 9 | **pop** | -2 | **`rem < 0`** | `[0, 2, -5, 3]` | **5** | **Restoration:** $3 - (-2) = 5$ |
| 10 | **top** | 3 | `rem > 0` | - | 5 | **Value:** $min + rem \Rightarrow 5 + 3 = 8$ |
| 11 | **pop** | 3 | `rem > 0` | `[0, 2, -5]` | 5 | Normal pop, `min` stays 5 |
| 12 | **pop** | -5 | **`rem < 0`** | `[0, 2]` | **10** | **Restoration:** $5 - (-5) = 10$ |

---

### Why this is Amazon-Interview Ready
* **Space**: $O(1)$ extra space (the stack itself is required for the data, but no extra stack is used).
* **Time**: All operations are $O(1)$.
* **Edge Case**: Using `Long` handles the case where `val - min` might exceed the range of a standard 32-bit integer.

### One Small Correction
In your `push` method:
```java
if (st.isEmpty()) {
    min = val;
    st.push(0L); 
}
```
If the very first value pushed is `Integer.MIN_VALUE`, your `min` starts there. This is perfectly fine, but make sure your `min` is initialized to something like `0` or `Long.MAX_VALUE` before the first push to avoid issues with your `Long.MAX_VALUE` default if `isEmpty()` isn't checked. Your current code handles it well!

Since you're prepping for Amazon, would you like to see how this logic changes if we had to implement a **Max Stack** instead?