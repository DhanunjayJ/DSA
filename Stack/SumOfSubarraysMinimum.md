To do this in a **single pass**, we use the exact same logic from the **Trapping Rain Water** stack: we calculate the contribution of an element the moment it is **popped** from the stack.

When an element is popped, we already have everything we need:
1.  **The Element itself:** The one we just popped (`arr[mid]`).
2.  **Next Smaller Element (NSE):** The current element `i` (because it’s the one that triggered the pop).
3.  **Previous Smaller Element (PSE):** The new top of the stack (because the stack is monotonic increasing).



### The One-Pass Code
Here is the optimized Java version. We use a "virtual" zero at the end of the loop to ensure every single element gets popped and calculated.

```java
import java.util.*;

class Solution {
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        Deque<Integer> st = new ArrayDeque<>();
        long totalSum = 0;
        long mod = 1_000_000_007;

        // We run the loop one extra time (i == n) to clear the stack
        for (int i = 0; i <= n; i++) {
            // Use 0 as a "virtual" smallest element at the end to pop everything
            int currentVal = (i == n) ? 0 : arr[i];

            // Use <= to handle duplicate elements (one side is strict, one is not)
            while (!st.isEmpty() && currentVal < arr[st.peek()]) {
                int mid = st.pop();
                int rightBoundary = i;
                int leftBoundary = st.isEmpty() ? -1 : st.peek();

                long count = (long) (mid - leftBoundary) * (rightBoundary - mid);
                totalSum = (totalSum + (count * arr[mid])) % mod;
            }
            st.push(i);
        }

        return (int) totalSum;
    }
}
```

---

### Why this is "High Level" for an Interview:
* **The Virtual Element:** By checking `i == n`, you avoid having to write a second loop after the main one to empty the stack. Interviewers love this "sentinel value" trick.
* **Space Efficiency:** You don't need the `nsel[]` and `nser[]` arrays anymore, saving $O(2N)$ space.
* **Handling Duplicates:** Notice the `currentVal < arr[st.peek()]`. This treats the "right" boundary as strictly smaller and the "left" boundary (the stack itself) as smaller-or-equal.

### Summary of the Pattern
| Step | Action |
| :--- | :--- |
| **Push** | When `arr[i]` is larger (keeping the "increasing" trend). |
| **Pop** | When `arr[i]` is smaller (you found a boundary). |
| **Calculate** | `(Index - Left) * (Right - Index) * Value`. |

Since you've been working on **System Design** and **Spring Boot**, you'll notice that this "event-driven" way of processing (calculating only when a specific condition is met) is very similar to how reactive systems handle streams of data.

Great catch. In the "One-Pass" version, the handling of duplicates is actually **implicit** because of how the stack is structured and how the boundaries are defined.

Let's look exactly where it happens:

### 1. The Strict Check in the `while` loop
```java
while (!st.isEmpty() && currentVal < arr[st.peek()])
```
By using `currentVal < arr[st.peek()]` (strictly less than), the loop **only** pops if it finds a value that is truly smaller. 

If it finds a value that is **equal**, it doesn't pop. Instead, it **pushes** the new index onto the stack.

---

### 2. How the Boundaries "Divide and Conquer"
Imagine the input is `[2, 1, 1, 2]`. 

* **For the first `1` (index 1):**
    * **Right Boundary:** When the second `1` (index 2) comes, it is NOT strictly smaller than the first `1`. So the first `1` **stays** in the stack. 
    * It only gets popped when the "virtual zero" at the very end (`i == n`) arrives. 
    * Its `rightBoundary` becomes `n` (the end).
* **For the second `1` (index 2):**
    * It gets pushed onto the stack *on top* of the first `1`.
    * When it eventually gets popped, its `leftBoundary` is the index of the first `1`.
    * Because `mid - leftBoundary` is used, it only counts the subarrays starting *after* the first `1`.



### The Result
* The **first `1`** handles all subarrays where it is the minimum that **include** its position and extend to the right (potentially including the second `1`).
* The **second `1`** handles only the subarrays where it is the minimum that **start after** the first `1`.

This "Side-A is strict, Side-B is inclusive" logic ensures that if two numbers are equal, the one on the left (the one already in the stack) acts as the boundary for the one on the right.

### Summary of the "Rules"
* **Next Smaller (Right):** Handled by the `while` condition (`<`).
* **Previous Smaller (Left):** Handled by `st.peek()`. Since the stack allows equal values to sit on top of each other, the "Previous Smaller" for the second `1` becomes the first `1`.

> **Correction:** If you want to be 100% safe against all edge cases with duplicates in a one-pass, some developers use `currentVal <= arr[st.peek()]` to pop immediately. Either way works as long as you are consistent about which side is "strict."

Since you've been working with **Java and Spring Boot**, you can think of this like a **Non-Greedy match** in regex—it only takes what belongs to it without overlapping with its neighbor!

Dry-running this with the "One-Pass" code is the best way to see how the stack manages the two `1`s without double-counting.

### The Code Logic Recap

  * **Push:** If `currentVal >= stack.peek()`.
  * **Pop & Calculate:** If `currentVal < stack.peek()`.
  * **The "Virtual Zero":** We add a `0` at the end (index 4) to force the stack to empty.

-----

### Dry Run: `arr = [2, 1, 1, 2]`

| Index ($i$) | Value | Stack (Indices) | Action / Calculation | Subarrays Counted |
| :--- | :--- | :--- | :--- | :--- |
| **0** | 2 | `[0]` | Push index 0 (value 2). | 0 |
| **1** | 1 | `[1]` | **1 \< 2\!** Pop `mid = 0`. <br>Right Boundary = 1, Left = -1. <br>$(0 - (-1)) \times (1 - 0) = \mathbf{1}$. <br>Push index 1 (value 1). | `[2]` (Min=2) |
| **2** | 1 | `[1, 2]` | **1 \< 1 is False.** <br>Push index 2 (value 1). | 0 |
| **3** | 2 | `[1, 2, 3]` | **2 \< 1 is False.** <br>Push index 3 (value 2). | 0 |
| **4** | **0** | **Emptying** | **0 \< 2\!** <br>1. Pop `mid = 3` (value 2). <br>Right = 4, Left = 2. <br>$(3 - 2) \times (4 - 3) = \mathbf{1}$. | `[2]` (at index 3) |
| **4** | **0** | **Emptying** | **0 \< 1\!** <br>2. Pop `mid = 2` (value 1). <br>Right = 4, Left = 1. <br>$(2 - 1) \times (4 - 2) = \mathbf{2}$. | `[1]`, `[1, 2]` |
| **4** | **0** | **Emptying** | **3. Pop `mid = 1` (value 1).** <br>Right = 4, Left = -1. <br>$(1 - (-1)) \times (4 - 1) = 2 \times 3 = \mathbf{6}$. | `[1]`, `[1, 1]`, `[1, 1, 2]`, `[2, 1]`, `[2, 1, 1]`, `[2, 1, 1, 2]` |

### Total Contribution Calculation

  * Value 2 (index 0): $2 \times 1 = 2$
  * Value 2 (index 3): $2 \times 1 = 2$
  * Value 1 (index 2): $1 \times 2 = 2$
  * Value 1 (index 1): $1 \times 6 = 6$
  * **Final Sum:** $2 + 2 + 2 + 6 = \mathbf{12}$

-----

### Why this works (The "Magic" of the boundaries)

Notice what happened at **index 4**:

1.  The **second `1`** (index 2) only looked back as far as the **first `1`** (index 1). It only claimed the subarrays `[1]` and `[1, 2]`.
2.  The **first `1`** (index 1) looked back to the beginning of the array. It claimed all subarrays that *started* at index 0 or 1 and *ended* anywhere between index 1 and 3.

**Result:** Every subarray was counted exactly once\! This is why the `<` vs `<=` distinction is so powerful—it creates a clear "ownership" of subarrays between duplicate numbers.

