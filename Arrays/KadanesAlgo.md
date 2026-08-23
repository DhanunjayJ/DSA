Kadane’s Algorithm is one of the most famous and foundational Dynamic Programming (DP) algorithms in computer science. It solves the **Maximum Subarray Sum** problem: finding the contiguous subarray within a one-dimensional array of numbers that has the largest sum.

Here is a complete breakdown of how it works, why it is the optimal approach, and its most important variations.

---

## The Core Intuition (Why it Works)

At its heart, Kadane's Algorithm is based on a simple, real-time decision made at every single element in the array: **"Do I join the existing subarray, or do I start a brand new subarray from here?"**

Imagine you are walking through the array and keeping a running sum (`currSum`).

* If `currSum` becomes **negative**, it has become a liability. Any future elements added to a negative sum will be dragged down by it.
* Therefore, the optimal move is to discard the running sum entirely and start fresh from the current element.

Mathematically, it is a state-optimized DP problem. The recurrence relation is:
$dp[i] = \max(arr[i], dp[i-1] + arr[i])$

Instead of storing the entire $dp$ array, we only ever need the previous state ($dp[i-1]$), which we store in a single variable (`currSum`). This brings our space complexity from $O(N)$ down to $O(1)$.

## The "Best" Implementation

There are two common ways people write Kadane's. The first is checking `if (currSum < 0) { currSum = 0; }`.

However, **the approach below is the industry standard and the "best" way to write it**, because it elegantly handles arrays that contain *only* negative numbers without needing extra edge-case flags.

```java
public int maxSubArray(int[] nums) {
    int maxSum = nums[0];
    int currSum = nums[0];

    for (int i = 1; i < nums.length; i++) {
        // Decision: Start fresh at nums[i], or add nums[i] to the running total?
        currSum = Math.max(nums[i], currSum + nums[i]);
        
        // Update the global maximum
        maxSum = Math.max(maxSum, currSum);
    }

    return maxSum;
}

```

### Why is Kadane's the "Best"?

We use it because it reaches the absolute theoretical limits of efficiency for this problem:

1. **Time Complexity:** $O(N)$. You must look at every element at least once to know the max sum. Kadane's looks at each element exactly once.
2. **Space Complexity:** $O(1)$. It requires only two integer variables (`currSum` and `maxSum`) regardless of how massive the input array is.

---

## Crucial Variations

Once you understand standard Kadane's, you will see its DNA in many other algorithmic problems. Here are the most common variations you will encounter in advanced technical interviews:

### 1. Returning the Subarray (Tracking Indices)

Interviewers often ask, "Now return the actual elements that make up the maximum sum, not just the integer." You modify Kadane's by keeping track of the starting and ending indices whenever `maxSum` updates.

```java
public int[] maxSubArrayIndices(int[] nums) {
    int maxSum = nums[0], currSum = nums[0];
    int start = 0, end = 0, tempStart = 0;

    for (int i = 1; i < nums.length; i++) {
        if (nums[i] > currSum + nums[i]) {
            currSum = nums[i];
            tempStart = i; // We started a new subarray
        } else {
            currSum += nums[i];
        }

        if (currSum > maxSum) {
            maxSum = currSum;
            start = tempStart; // Lock in the start index
            end = i;           // Lock in the end index
        }
    }
    // Subarray is from nums[start] to nums[end]
    return Arrays.copyOfRange(nums, start, end + 1);
}

```

### 2. Maximum Product Subarray

Instead of addition, you are multiplying. Because multiplying two negative numbers creates a positive number, a deeply negative running product can instantly become the maximum product if you hit another negative number.

* **The tweak:** You must track *both* the `currMax` and the `currMin` at every step.

### 3. Maximum Alternating Subarray Sum

You are looking for a subarray where the elements alternate signs (positive, negative, positive, negative), and you want the max sum of such a subarray.

* **The tweak:** You reset `currSum` not only when it drops below zero, but also if the current element has the same sign as the previous element.

### 4. 2D Kadane’s (Maximum Sum Rectangle in a 2D Matrix)

Used to find the sub-matrix with the highest sum.

* **The tweak:** You lock the left and right columns using two pointers, collapse the rows between them into a single 1D array by summing them horizontally, and then run standard 1D Kadane's on that collapsed array.

---

Ah, I understand exactly what you mean now. You are referring to the most intuitive way Kadane's algorithm is taught: **keeping a running sum and explicitly resetting it to zero (or discarding it) the moment it drops below zero.**

This is actually the exact logic you were trying to use in your original code with `if(currSum <= 0)`.

Here is how you write this specific variation perfectly, why it works, and the common trap to avoid.

### The "Reset to Zero" Implementation

Here is the cleanest way to write the reset-to-zero style of Kadane's algorithm in Java:

```java
public int maxSubArray(int[] nums) {
    // 1. Initialize to MIN_VALUE, NOT zero. 
    int maxSum = Integer.MIN_VALUE; 
    int currSum = 0;

    for (int i = 0; i < nums.length; i++) {
        // Step 1: Add the current element to the running sum
        currSum += nums[i];

        // Step 2: Update the global max BEFORE you reset
        if (currSum > maxSum) {
            maxSum = currSum;
        }

        // Step 3: The Kadane's Reset
        // If our running sum has dropped below zero, it's toxic.
        // We drop it completely and start fresh at 0 for the next iteration.
        if (currSum < 0) {
            currSum = 0;
        }
    }

    return maxSum;
}

```

### The Logic Behind the Reset

Think of `currSum` as your "current momentum."

1. As long as your momentum is positive (even if you just added a negative number like `-2` to a sum of `10`), you are still holding onto a net-positive value of `8`. That `8` could still help a future number grow even larger.
2. But the exact moment your momentum hits **less than zero** (e.g., `-3`), it becomes a liability. If the next number in the array is `10`, combining it with `-3` gives you `7`. You would have been better off just taking the `10` by itself.
3. Therefore, whenever `currSum < 0`, you cut your losses. You set `currSum = 0`, essentially saying, "I will start a brand new subarray starting at the *next* element."

### Why Your Original Code Bugged Out (The "All-Negative" Trap)

When developers write the "reset to zero" variation, they frequently make two fatal mistakes that cause it to fail on arrays containing *only* negative numbers (like `[-5, -2, -9]`).

**Mistake 1: Initializing `maxSum = 0**`
In your first prompt, you wrote `int max = 0;`. If the array is `[-5, -2, -9]`, `currSum` will keep dropping below zero, getting reset to `0`, and `maxSum` will never be updated because `currSum` never goes above `0`. Your code will return `0`. But the correct answer for the maximum non-empty subarray is `-2`.

* **The Fix:** Always initialize `maxSum` to `Integer.MIN_VALUE` or `arr[0]`.

**Mistake 2: Resetting before recording the max**
If you put the `if (currSum < 0) { currSum = 0; }` block *before* you update `maxSum`, the negative numbers get erased before they can ever be recorded as the maximum.

* **The Fix:** Always update `maxSum = Math.max(maxSum, currSum)` immediately after adding `nums[i]`, and save the reset logic for the very last step inside the loop.

### Summary of Styles

Both the `Math.max()` style I showed earlier and this "Reset to Zero" style are perfectly valid $O(N)$ solutions.

* **The Math.max style:** `currSum = Math.max(nums[i], currSum + nums[i]);` is favored by competitive programmers because it handles the all-negative edge case automatically in one line.
* **The Reset style:** `if (currSum < 0) currSum = 0;` is highly visual and closely mimics human logic ("if I go into debt, declare bankruptcy and start over tomorrow"). You just have to be careful about your initialization variables.