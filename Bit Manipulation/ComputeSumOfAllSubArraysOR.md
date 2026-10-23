https://www.hackerrank.com/contests/codeagon/challenges/bob-and-subarray-or/problem?isFullScreen=false
---

## 1. Understanding the Problem Core

We are given an array of integers. A **subarray** is any contiguous chunk of elements (e.g., in `[1, 2, 3]`, subarrays are `[1]`, `[2]`, `[3]`, `[1, 2]`, `[2, 3]`, and `[1, 2, 3]`).

Our goal is to:

1. Compute the bitwise OR value for **every single possible subarray**.
2. Sum all those OR values together and return the grand total.

---

## 2. Why the Naive Approach Fails ($O(N^2)$)

* **The Idea:** Use two loops to find every possible start and end point, compute the OR of that slice, and add it to a running sum.
* **The Catch:** With an array size of $N = 10^5$, there are roughly $\frac{N(N+1)}{2} \approx 5 \times 10^9$ subarrays. Generating and checking every single one takes way too long and results in a **Time Limit Exceeded (TLE)** error. We need a faster strategy.

---

## 3. The Brilliant Intuition: Properties of Bitwise OR

To optimize, we rely on two key observations:

### Observation A: Bitwise OR Never Decreases

In binary, the bitwise OR (`|`) operation can only turn bits **from `0` to `1**`. Once a bit becomes `1`, subsequent OR operations can never turn it back to `0`.
Therefore, as you expand a subarray to the left by including more numbers, the bitwise OR value **can only grow larger or stay the same**. It never shrinks.

### Observation B: There Are at Most 30 Unique OR Values

Standard integers in these problems use at most **30 bits** (up to $2^{30}-1$). Because a running OR value can only change its bit pattern when a new bit flips from `0` to `1`, **it can change its value at most 30 times**.

This means that no matter how massive the array is ($10^5$ elements), at any given index $i$, **there can be at most 30 distinct bitwise OR values** for all subarrays ending at that exact spot!

---

## 4. The Frequency-Tracking Approach ($O(30 \times N)$)

Instead of tracking thousands of subarrays, we only ever maintain a collection of the **distinct OR values ending at the current index**, along with **how many subarrays (`frequency`)** produced each value.

As we iterate through the array element `num`:

1. **Start fresh:** Every element forms a brand-new subarray of length 1 containing just `num` (frequency = 1).
2. **Expand history:** Take all the distinct OR values ending at the *previous* index, and perform `previous_or | num`.
3. **Merge duplicates:** If multiple previous subarrays result in the same new OR value, we add their frequencies together using a `HashMap`.
4. **Accumulate:** Add `(OR_value * frequency)` to our total sum for all subarrays ending at this index.

---

## 5. Final Complete Java Code

Here is the clean, easy-to-understand code using your preferred `Scanner` approach and the critical `long` cast to prevent integer overflow:

```java
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 1. Read array size and elements
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        
        long totalSum = 0;
        
        // 2. HashMap stores: { bitwise_or_value : frequency of subarrays }
        // This tracks all unique OR values ending at the previous index.
        HashMap<Integer, Integer> currentOrs = new HashMap<>();
        
        // 3. Process each number in the array
        for (int num : nums) {
            HashMap<Integer, Integer> nextOrs = new HashMap<>();
            
            // A single element itself forms a valid subarray of length 1
            nextOrs.put(num, 1);
            
            // Combine the current number with all previous OR values
            for (Map.Entry<Integer, Integer> entry : currentOrs.entrySet()) {
                int prevOr = entry.getKey();
                int freq = entry.getValue();
                
                int newOr = prevOr | num;
                
                // If this new OR value already exists, add its frequencies together
                nextOrs.put(newOr, nextOrs.getOrDefault(newOr, 0) + freq);
            }
            
            // Move forward: currentOrs becomes nextOrs for the next iteration
            currentOrs = nextOrs;
            
            // Add the contribution of all subarrays ending at this index to the total sum
            for (Map.Entry<Integer, Integer> entry : currentOrs.entrySet()) {
                int val = entry.getKey();
                int freq = entry.getValue();
                
                // CRITICAL: Cast to long during multiplication to avoid integer overflow
                totalSum += (long) val * freq;
            }
        }
        
        // 4. Print the final grand total sum
        System.out.println(totalSum);
    }
}

```

---

## 6. Summary Checklist for Revision

* **Why HashMap?** Because multiple different starting points can produce the exact same bitwise OR. We need frequencies to count every single subarray accurately.
* **Why max 30 values?** Because integers have 30 usable bits, and OR values can only grow/change up to 30 times. This bounds our inner loop size.
* **Why `(long) val * freq`?** To prevent arithmetic overflow when the product exceeds 32-bit integer limits before adding to `totalSum`.