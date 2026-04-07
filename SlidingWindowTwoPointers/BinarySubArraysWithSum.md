This question is a bit of a "trap" for standard sliding window techniques. Usually, sliding window is great for finding the **longest** or **shortest** subarray. However, when you need the **exact count** of subarrays that meet a specific sum (like `goal`), a standard window struggles because adding or removing a `0` doesn't change the sum, but it *does* change the number of valid subarrays.

To solve this efficiently using Sliding Window, we use a clever mathematical trick.

---

## The "Exactly K" Trick
Instead of trying to find exactly `goal`, we write a helper function that finds the number of subarrays where the `sum <= goal`.

**The logic:**
$$\text{Exactly}(goal) = \text{AtMost}(goal) - \text{AtMost}(goal - 1)$$

If you find all subarrays with `sum <= 2` and subtract all subarrays with `sum <= 1`, you are left with exactly the subarrays that sum to `2`.

### Why use "At Most"?
Finding "at most" is much easier with a sliding window because the condition is **monotonic**: as the window grows, the sum only increases. As the window shrinks, the sum only decreases.

---

## 1. The Code Implementation

```java
class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        // Exactly(goal) = AtMost(goal) - AtMost(goal - 1)
        return atMost(nums, goal) - atMost(nums, goal - 1);
    }

    private int atMost(int[] nums, int goal) {
        if (goal < 0) return 0;
        
        int start = 0;
        int currentSum = 0;
        int count = 0;

        for (int end = 0; end < nums.length; end++) {
            currentSum += nums[end];

            // Shrink window if sum exceeds goal
            while (currentSum > goal) {
                currentSum -= nums[start];
                start++;
            }

            // KEY POINT: The number of subarrays ending at 'end' 
            // with sum <= goal is (end - start + 1)
            count += (end - start + 1);
        }

        return count;
    }
}
```

---

## 2. Why does `count += (end - start + 1)` work?
This is the most important part of the "At Most" pattern. If a window `[start...end]` is valid (sum $\le$ goal), then **every** subarray ending at `end` within that window is also valid.

**Example:** `nums = [1, 0, 1]`, `goal = 2`
If your window is `[1, 0, 1]`, the subarrays ending at index 2 are:
1. `[1]` (just the last element)
2. `[0, 1]`
3. `[1, 0, 1]`
The number of these subarrays is exactly `end - start + 1` (which is `2 - 0 + 1 = 3`).



---

## 3. Comparison with Prefix Sum Approach
You might also see people solve this using a **Prefix Sum + HashMap** (similar to "Subarray Sum Equals K").

| Feature | Sliding Window (AtMost) | Prefix Sum + HashMap |
| :--- | :--- | :--- |
| **Time Complexity** | $O(N)$ (Two passes) | $O(N)$ (One pass) |
| **Space Complexity** | $O(1)$ (No extra structures) | $O(N)$ (To store the Map) |

### When to use which?
* **Sliding Window:** Best when the elements are **non-negative** (like this problem, where values are only 0 and 1). Since the sum never decreases as we move `end`, the window logic works.
* **HashMap:** Necessary if the array contains **negative numbers**. If you had `-1` in the array, adding an element could decrease the sum, breaking the sliding window logic.

### Summary for your Amazon Prep:
In an interview, if you see "Binary array" or "non-negative numbers" and they ask for a count, immediately think: **"Can I use the AtMost(K) - AtMost(K-1) trick?"** It’s a very clean $O(1)$ space solution that impresses interviewers.

Do you see how subtracting the two "At Most" results isolates exactly the subarrays you need?