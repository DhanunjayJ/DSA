Your approach is very creative—trying to carry the Kadane's sum over into a second loop is a common intuitive leap for this problem. However, **your current logic is not completely correct and will fail on several test cases.**

Here is exactly where the logic breaks down, followed by the standard $O(N)$ way to solve this.

### Where Your Code Fails

**1. It misses the optimal wrap-around sum.**
Let's trace your code with the second example from the problem description: `[10, -3, -4, 7, 6, 5, -4, -1]`.

* During your first loop, `lastNonZeroIdx` updates to `0` at the very beginning (since `currSum` starts at 0). It never updates again because `currSum` never drops below `0` again.
* Your second loop runs from `i = 0` to `lastNonZeroIdx - 1`. Since `lastNonZeroIdx` is 0, **the second loop doesn't execute at all.**
* Your code returns `21` (from the subarray `[7, 6, 5, -4, -1]`), but the actual answer is `23` (`[7, 6, 5, -4, -1, 10]`).

Tracking the last time the running sum dipped to zero doesn't mathematically guarantee you find the best suffix to combine with the best prefix.

**2. The "All Negatives" Edge Case**
You initialized `max = 0;`. If the array is `[-5, -2, -9]`, your code will return `0`. However, the problem specifies a *non-empty* subarray, so the correct answer should be `-2`.

---

### The Correct Approach (The "Math Trick")

To solve this optimally in one pass, we have to look at the two possible ways the maximum sum can exist:

* **Case 1: The maximum subarray is normal (no wrap-around).**
This is just standard Kadane's algorithm.
* **Case 2: The maximum subarray wraps around.**
If the maximum sum wraps around the ends, it means the elements *not* included in our sum form a continuous block in the middle of the array. And for our wrapping sum to be as large as possible, that middle block must be the **minimum subarray sum**.
Therefore: `Maximum Wrapping Sum = Total Sum of Array - Minimum Subarray Sum`.

### The Solution

We can find the Total Sum, Max Subarray Sum (Kadane's), and Min Subarray Sum (Reverse Kadane's) all in a single loop.

Here is the corrected, optimal $O(N)$ code:

```java
class Solution {
    public int maxCircularSum(int arr[]) {
        int totalSum = 0;
        
        int currMax = 0;
        int maxSum = arr[0]; // Initialize to first element to handle all-negatives
        
        int currMin = 0;
        int minSum = arr[0];
        
        for (int i = 0; i < arr.length; i++) {
            totalSum += arr[i];
            
            // Standard Kadane's for Maximum Subarray
            currMax = Math.max(currMax + arr[i], arr[i]);
            maxSum = Math.max(maxSum, currMax);
            
            // Modified Kadane's for Minimum Subarray
            currMin = Math.min(currMin + arr[i], arr[i]);
            minSum = Math.min(minSum, currMin);
        }
        
        // Edge Case: If all numbers are negative, maxSum will be negative.
        // In this case, totalSum == minSum, so totalSum - minSum = 0.
        // We cannot return an empty array sum (0), so we just return maxSum.
        if (maxSum < 0) {
            return maxSum;
        }
        
        // Return the maximum of the non-wrapping and wrapping cases
        return Math.max(maxSum, totalSum - minSum);
    }
}

```