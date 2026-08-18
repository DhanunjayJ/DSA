To build a deep intuition for **Longest Increasing Subsequence (LIS)**, we'll start from raw problem-solving intuition and naturally evolve toward the optimal solution.

---

## Step 1: Clarify the Building Blocks

First, let's distinguish a **subsequence** from a **subarray**:

* A **subarray** must be contiguous (adjacent elements in order).
* A **subsequence** does **not** have to be contiguous; you can pick elements at any positions as long as you maintain their relative left-to-right order.

**Example:** `nums = [10, 9, 2, 5, 3, 7, 101, 18]`

* `[2, 5, 7]` is a valid increasing subsequence.
* `[2, 3, 7, 101]` is also valid, and its length is **4**.

---

## Step 2: The Brute Force Mental Model (Recursive Decisions)

If you were sitting with a pencil and paper trying to build an increasing subsequence element by element, what choices do you make at each index?

At index $i$, you have two options:

1. **Include `nums[i]**` in your current sequence (only allowed if `nums[i]` is strictly greater than the last included element).
2. **Skip `nums[i]**` and keep looking forward.

If you explore all possibilities, you generate a binary decision tree of height $n$. Since each element has 2 choices (include/exclude), the total combinations are $2^n$.

### Why Brute Force Fails

* For $n = 2500$, $2^{2500}$ is vastly larger than the number of atoms in the observable universe.
* Notice the **overlapping subproblems**: If you arrive at index 5 with the last chosen element being `7`, it doesn't matter *how* you arrived at `7`—the best remaining sequence starting from index 5 onward given `7` will always be identical.

---

## Step 3: The Dynamic Programming Intuition ($O(n^2)$)

To eliminate redundant work, change your perspective from *"Should I take or skip?"* to **"What is the best outcome ending right here?"**

Define $dp[i]$:

> **$dp[i]$ = Length of the longest strictly increasing subsequence that *ends* at index $i$.**

### Why focus on the "ending at index $i$" subproblem?

If you know the best LIS ending at every index *before* $i$ (i.e., indices $j < i$), then to find $dp[i]$:

1. Look back at all previous numbers `nums[j]` where $j < i$.
2. Filter for those where `nums[j] < nums[i]` (meaning `nums[i]` can extend the sequence ending at $j$).
3. Take the maximum among those valid choices and add 1 for `nums[i]` itself.

### Recurrence Relation

$$dp[i] = 1 + \max(\{dp[j] \mid 0 \le j < i \text{ and } \text{nums}[j] < \text{nums}[i]\})$$

*(If no `nums[j] < nums[i]` exists, $dp[i] = 1$ because a single element is a sequence of length 1.)*

#### Tracing a Short Example: `nums = [10, 9, 2, 5, 3, 7]`

* `i = 0` (`10`): $dp[0] = 1$
* `i = 1` (`9`): No element before 9 is smaller than 9. $dp[1] = 1$
* `i = 2` (`2`): No element before 2 is smaller than 2. $dp[2] = 1$
* `i = 3` (`5`): Look back at 10, 9, 2. Only 2 is smaller than 5. $dp[3] = dp[2] + 1 = 2$ (sequence: `[2, 5]`)
* `i = 4` (`3`): Look back. Only 2 is smaller than 3. $dp[4] = dp[2] + 1 = 2$ (sequence: `[2, 3]`)
* `i = 5` (`7`): Look back. 2, 5, 3 are all smaller than 7.
* Extend `[2]` $\rightarrow$ length 2
* Extend `[2, 5]` $\rightarrow$ length 3
* Extend `[2, 3]` $\rightarrow$ length 3
* $dp[5] = \max(2, 3, 3) = 3$ (sequence: `[2, 3, 7]` or `[2, 5, 7]`)



The overall answer is $\max(dp) = 3$.

* **Time Complexity:** $O(n^2)$ (for each element $i$, we scan all previous $j < i$).
* **Space Complexity:** $O(n)$ (for the $dp$ array).

---

## Step 4: The Greedy + Binary Search Intuition ($O(n \log n)$)

Can we do better than $O(n^2)$? To avoid scanning all previous elements, ask yourself:

> **"If two subsequences have the SAME length, which one gives me a better chance of growing longer in the future?"**

Suppose we have two increasing subsequences of length 3:

* Sequence A: `[2, 5, 100]` (ends in **100**)
* Sequence B: `[2, 3, 7]` (ends in **7**)

Sequence B is strictly better to build upon because **7 is smaller than 100**. Any future number $> 100$ can extend both, but a number like $15$ can only extend Sequence B.

### The Greedy Strategy

Maintain an array `tails`, where **`tails[k]` represents the SMALLEST ending value of all increasing subsequences of length `k + 1` seen so far.**

When iterating through a new number `x` in `nums`:

1. **If `x` is greater than all elements in `tails**`:
`x` extends the longest sequence found so far! Append `x` to `tails`.
2. **If `x` is NOT greater than all elements**:
`x` cannot extend our max length yet, but it can **improve an existing length** by making its ending value smaller (greedy choice). Find the first element in `tails` that is $\ge x$ and replace it with `x`.

Because `tails` is always strictly sorted in ascending order, you can use **Binary Search** (`std::lower_bound` or `bisect_left`) to find the position to replace in $O(\log n)$ time!

---

### Step-by-step Trace of `[10, 9, 2, 5, 3, 7, 101, 18]`

Let's watch `tails` evolve:

1. `x = 10`: `tails = [10]`
2. `x = 9`: `9` is smaller than `10`. Replace `10`.
`tails = [9]` *(A sequence of length 1 ending in 9 is better than one ending in 10)*
3. `x = 2`: `2` is smaller than `9`. Replace `9`.
`tails = [2]`
4. `x = 5`: `5` is larger than `2`. Append `5`.
`tails = [2, 5]` *(Found length 2!)*
5. `x = 3`: `3` is smaller than `5`. Replace `5`.
`tails = [2, 3]` *(Length 2 ending in 3 is better than ending in 5)*
6. `x = 7`: `7` is larger than `3`. Append `7`.
`tails = [2, 3, 7]` *(Found length 3!)*
7. `x = 101`: `101` is larger than `7`. Append `101`.
`tails = [2, 3, 7, 101]` *(Found length 4!)*
8. `x = 18`: Binary search finds `101` (first element $\ge 18$). Replace `101` with `18`.
`tails = [2, 3, 7, 18]`

**Final result:** The length of `tails` is **4**.

*(Note: `tails` itself does not store the actual LIS items in exact order, but its size is guaranteed to equal the maximum length of LIS).*

---

### Complexity Comparison

| Method | Time Complexity | Space Complexity | Main Key Idea |
| --- | --- | --- | --- |
| **Brute Force Recursion** | $O(2^n)$ | $O(n)$ | Check every subset |
| **Dynamic Programming** | $O(n^2)$ | $O(n)$ | $dp[i]$ = max length ending at $i$ |
| **Greedy + Binary Search** | $O(n \log n)$ | $O(n)$ | Keep smallest tails for each length |

---

Here is the complete Java implementation of the $O(n \log n)$ Greedy + Binary Search approach (also known as the **Patience Sorting algorithm**), with detailed inline annotations explaining each step.

```java
import java.util.Arrays;

public class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // tails[i] stores the smallest tail of all increasing subsequences 
        // of length (i + 1) found so far.
        int[] tails = new int[nums.length];
        
        // Tracks the current length of the longest increasing subsequence.
        int length = 0;

        for (int num : nums) {
            // Binary search to find the insertion index for `num` in `tails[0 ... length - 1]`.
            // We look for the first element in `tails` that is >= num.
            int i = 0, j = length;
            while (i < j) {
                int mid = i + (j - i) / 2;
                if (tails[mid] < num) {
                    i = mid + 1; // Move right if current tail is smaller
                } else {
                    j = mid;     // Move left to find the first element >= num
                }
            }

            // `i` is now the target index in `tails`:
            // 1. If `i == length`, `num` is strictly larger than all existing tails.
            //    It extends the LIS by 1.
            // 2. If `i < length`, `num` can replace `tails[i]` to make a subsequence
            //    of length (i + 1) end with a smaller value (greedy optimization).
            tails[i] = num;

            // If we placed `num` at the end of `tails`, we grew our LIS length.
            if (i == length) {
                length++;
            }
        }

        return length;
    }
}

```

---

## Key Execution Details

1. **Binary Search Mechanism (`lower_bound`):**
* Since `tails` is kept strictly sorted throughout the loop, binary search runs in $O(\log k)$ time for each element (where $k \le n$).


2. **Greedy Replacement:**
* Replacing an existing tail at `tails[i]` with a smaller value `num` never decreases the overall length found so far, but it creates a lower "threshold" for future numbers to extend that subsequence.


3. **Space Complexity:**
* Uses an auxiliary `tails` array of size $O(n)$, making space complexity $O(n)$.


4. **Time Complexity:**
* $n$ elements processed $\times$ $O(\log n)$ binary search per element = **$O(n \log n)$ total time**.

---
### Why Greedy + Binary Search Breaks Sequence Reconstruction

The binary search technique works for finding the **length** of the LIS because it aggressively overwrites `tailIndices` with smaller elements to keep tails minimal for *future* potential sequences.

However, when an element gets overwritten in `tailIndices`, **the parent-child history gets corrupted**.

Consider `arr = [0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15]`:

1. When `8` is processed, `tailIndices` has `[0, 8]`.
2. When `4` arrives, binary search overwrites `8` with `4` because `4 < 8`.
3. Later elements that build off `4` will set their `parent` pointer to `4`. But elements that *already* built off `8` earlier are left stranded or end up pointing to overwritten indices that no longer represent a valid strictly increasing ordering from left to right!

While there are complex workarounds to patch this (like storing full history trees), **this is why standard Dynamic Programming $O(n^2)$ is the universal standard for reconstructing the LIS sequence.**

---

### The Clean $O(n^2)$ Solution (Guaranteed Correct)

In the standard DP approach:

1. `dp[i]` stores the length of LIS ending at index `i`.
2. `parent[i]` stores the index of the element that comes immediately before `arr[i]`.
3. We track `maxIndex` (the index where the maximum overall length ends) to start our reconstruction cleanly.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

class Solution {
    public ArrayList<Integer> getLIS(int arr[]) {
        if (arr == null || arr.length == 0) {
            return new ArrayList<>();
        }

        int n = arr.length;
        int[] dp = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(parent, -1);

        int maxLength = 1;
        int maxIndex = 0;

        // Standard DP: Find the best preceding element for each arr[i]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        parent[i] = j; // Record valid parent link
                    }
                }
            }

            // Track where the global longest sequence ends
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                maxIndex = i;
            }
        }

        // Backtrack to reconstruct the sequence
        ArrayList<Integer> result = new ArrayList<>();
        int curr = maxIndex;

        while (curr != -1) {
            result.add(arr[curr]);
            curr = parent[curr];
        }

        Collections.reverse(result);
        return result;
    }
}

```

### Why this works 100% of the time:

* **No overwriting:** `parent[i]` is set once by checking all prior elements $j < i$ where `arr[j] < arr[i]`.
* **Lexicographical / Valid paths:** It guarantees that every element in the path actually came before the next element in the original array index order.