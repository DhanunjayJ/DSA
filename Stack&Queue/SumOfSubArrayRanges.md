### The Big Difference in Efficiency
While your $O(n^2)$ code works perfectly for the **Subarray Ranges** problem (where $n = 1000$), it is actually the "slow" way to solve it. 

For **Subarray Ranges**, there is a famous $O(n)$ optimization using a **Monotonic Stack**. The logic is:
$$\text{Sum of (Max - Min)} = \sum \text{Maxes} - \sum \text{Mins}$$

1.  Find how many subarrays each number $nums[i]$ acts as the **maximum** element.
2.  Find how many subarrays each number $nums[i]$ acts as the **minimum** element.
3.  Contribution = $(nums[i] \times \text{count\_as\_max}) - (nums[i] \times \text{count\_as\_min})$.



This optimization is **not** possible for the "Sum of Beauty" string problem because beauty depends on the relative counts of *all* characters in the window, not just the single largest or smallest value.

To get from $O(n^2)$ down to $O(n)$, we stop looking at **subarrays** and start looking at **individual elements**. 

Instead of asking "What is the min/max of this subarray?", we ask: **"For this specific number $nums[i]$, in how many subarrays is it the minimum (or maximum)?"**

If $nums[i]$ is the minimum in $K$ subarrays, it contributes $- (nums[i] \times K)$ to the final sum. If it's the maximum in $M$ subarrays, it contributes $+ (nums[i] \times M)$.

---

### The Secret: Monotonic Stack
We use a **Monotonic Stack** to find the boundaries of where an element is the "boss" (the min or max).

1.  **Left Boundary ($L$):** The nearest index to the left with a smaller value.
2.  **Right Boundary ($R$):** The nearest index to the right with a smaller value.
3.  **Count of Subarrays:** The number of subarrays where $nums[i]$ is the minimum is $(i - L) \times (R - i)$.



---

### Why this is faster
In your $O(n^2)$ code, you are doing work for every single pair of $(i, j)$. In the $O(n)$ version:
* We pass through the array a few times with a stack.
* Each element is pushed and popped exactly once.
* Total time complexity: **$O(n)$**.

### The Logic in Action
If you have `nums = [1, 2, 3]`:
* **For the number `2`:**
    * To the left, `1` is smaller. So the boundary starts at index 1.
    * To the right, there is no smaller number. So the boundary ends at index 2.
    * `2` is the minimum only in the subarray `[2]`. 
* **For the number `1`:**
    * It is the minimum for `[1]`, `[1,2]`, and `[1,2,3]`.

### The "Sum of Max - Sum of Min" Formula
The most efficient way to code this is to calculate two separate values:
1.  **`sumMax`**: The sum of every element multiplied by the number of subarrays where it is the maximum.
2.  **`sumMin`**: The sum of every element multiplied by the number of subarrays where it is the minimum.

$$\text{Result} = \text{sumMax} - \text{sumMin}$$

This effectively calculates the difference for all possible subarrays simultaneously! 

To get the final answer, you generally calculate the **Sum of Maximums** and the **Sum of Minimums** separately. You can do this in two distinct passes (one for Max, one for Min), or you can combine them into one loop for efficiency.

Here is how the "Two-Pass" logic works conceptually:

### 1. The "Min" Pass
We find how many subarrays each element $nums[i]$ is the **minimum**. 
* We use a stack to find the **Previous Smaller Element (PSE)** and the **Next Smaller Element (NSE)**.
* The number of subarrays where $nums[i]$ is the minimum is: $(i - \text{PSE\_index}) \times (\text{NSE\_index} - i)$.
* **Contribution to Sum:** $-(nums[i] \times \text{count})$.

### 2. The "Max" Pass
We find how many subarrays each element $nums[i]$ is the **maximum**.
* We use a stack to find the **Previous Greater Element (PGE)** and the **Next Greater Element (NGE)**.
* The number of subarrays where $nums[i]$ is the maximum is: $(i - \text{PGE\_index}) \times (\text{NGE\_index} - i)$.
* **Contribution to Sum:** $+(nums[i] \times \text{count})$.



---

### The Final Calculation
Instead of iterating through every subarray like your $O(n^2)$ code did, we just add up these contributions:
$$\text{Total Sum} = \sum (\text{Value} \times \text{Max\_Count}) - \sum (\text{Value} \times \text{Min\_Count})$$

### Why the "Two-Pass" (or Four-Pass) is better
Even though "two passes" sounds like more work than "one nested loop," the math is:
* **Your Code ($O(n^2)$):** $1000 \times 1000 = 1,000,000$ operations.
* **Stack Code ($O(n)$):** $4 \times 1000 = 4,000$ operations.

If the array size was $10^5$, your $O(n^2)$ code would take **10 billion** operations (and fail), while the stack code would only take **400,000** operations (and pass instantly).

---

### Important Note on "Equal Elements"
When you have duplicate numbers (like `[1, 2, 2, 1]`), you have to be careful not to double-count subarrays. Usually, we use a "strict" inequality on one side ($<$) and a "non-strict" inequality on the other ($\leq$) when finding boundaries.

Here is the high-performance Java implementation using the **Monotonic Stack**. 

The logic uses a single helper function to calculate the "Total Sum of Extrema" (either all minimums or all maximums). This way, the final answer is simply: 
$$\text{Result} = \text{SumOfMaxes} - \text{SumOfMins}$$

### The $O(n)$ Java Solution

```java
import java.util.*;

class Solution {
    public long subArrayRanges(int[] nums) {
        // Result = (Sum of all subarray maximums) - (Sum of all subarray minimums)
        return solve(nums, true) - solve(nums, false);
    }

    private long solve(int[] nums, boolean findMax) {
        int n = nums.length;
        long total = 0;
        int[] left = new int[n];  // Nearest boundary on the left
        int[] right = new int[n]; // Nearest boundary on the right
        Deque<Integer> stack = new ArrayDeque<>();

        // 1. Find boundaries on the left
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && (findMax ? nums[stack.peek()] <= nums[i] : nums[stack.peek()] >= nums[i])) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // 2. Find boundaries on the right
        for (int i = n - 1; i >= 0; i--) {
            // Note: Use strict inequality here (< or >) to handle duplicate values correctly
            while (!stack.isEmpty() && (findMax ? nums[stack.peek()] < nums[i] : nums[stack.peek()] > nums[i])) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // 3. Calculate contribution of each element
        for (int i = 0; i < n; i++) {
            long count = (long) (i - left[i]) * (right[i] - i);
            total += count * nums[i];
        }

        return total;
    }
}
```

---

### How the "Boundary Math" Works
When we say an element `nums[i]` is the "minimum" of a subarray, we are looking for the largest possible window where it remains the smallest.



* **Left Boundary (`left[i]`):** The first index to the left that is *smaller* than `nums[i]`.
* **Right Boundary (`right[i]`):** The first index to the right that is *smaller* than `nums[i]`.
* **Number of Subarrays:** Any subarray starting between `left[i] + 1` and `i`, and ending between `i` and `right[i] - 1`, will have `nums[i]` as its minimum. 
    * Choices for start: $(i - left[i])$
    * Choices for end: $(right[i] - i)$
    * **Total Combinations:** $(i - left[i]) \times (right[i] - i)$

---

### Why this is the "Interview Gold" Answer
1.  **Complexity:** It drops from $O(n^2)$ to $O(n)$ time.
2.  **Space:** It uses $O(n)$ space for the stack and boundary arrays.
3.  **Handling Duplicates:** By using `<=` on one pass and `<` on the other, we ensure that if two elements are equal (e.g., `[2, 2]`), we only count the "range" once, preventing double-counting.

Here is the complete, high-performance $O(n)$ solution. 

To keep it clean and professional, I have combined the logic into a single method `solve(int[] nums, boolean isMax)`. This allows you to calculate the **Sum of Maximums** and **Sum of Minimums** using the same core engine, then simply subtract them.

### The $O(n)$ Monotonic Stack Solution

```java
import java.util.*;

class Solution {
    public long subArrayRanges(int[] nums) {
        // Result = (Total sum of all subarray maxes) - (Total sum of all subarray mins)
        return getSum(nums, true) - getSum(nums, false);
    }

    private long getSum(int[] nums, boolean isMax) {
        int n = nums.length;
        long total = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        // Loop to n to "flush" the stack for remaining elements
        for (int i = 0; i <= n; i++) {
            // Use a virtual boundary value at index n
            // For Max: use +Infinity to pop everything
            // For Min: use -Infinity to pop everything
            long currentVal = (i == n) ? 
                (isMax ? Integer.MAX_VALUE : Integer.MIN_VALUE) : nums[i];

            while (!stack.isEmpty()) {
                int midIndex = stack.peek();
                long midVal = nums[midIndex];

                // Check if currentVal breaks the monotonic property
                boolean shouldPop = isMax ? (midVal < currentVal) : (midVal > currentVal);
                
                if (!shouldPop) break;

                stack.pop();
                int leftBoundary = stack.isEmpty() ? -1 : stack.peek();
                int rightBoundary = i;

                // Combinations: (elements to the left) * (elements to the right)
                long count = (long) (midIndex - leftBoundary) * (rightBoundary - midIndex);
                total += count * midVal;
            }
            stack.push(i);
        }
        return total;
    }
}
```

---

### Key Takeaways for your Interview:

1.  **The "Flush" Technique:** By running the loop to `i == n`, we don't need a separate loop at the end to empty the stack. The virtual `MAX_VALUE` or `MIN_VALUE` forces all previous elements to be "popped" and calculated.
2.  **Long Precision:** Notice the use of `(long)` for the `count` calculation. Even if `n` is only $1000$, the number of subarrays can grow quickly, and the total sum can exceed the range of a standard 32-bit `int`.
3.  **Strict vs. Non-Strict:** In the `while` condition, using `<` for Max and `>` for Min naturally handles duplicate numbers. One "equal" number will pop the other, ensuring each unique subarray is attributed to exactly one of the equal elements.



---

### Comparison with your original code:
* **Your $O(n^2)$ Code:** Checks every possible window. Simple, but slow for large $n$.
* **This $O(n)$ Code:** Calculates the "contribution" of each number. It asks: "How many times does this specific number act as the king of its window?" and multiplies that count by the number itself.
