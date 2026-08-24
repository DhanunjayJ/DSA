To solve the **Maximum Gap** problem in **linear time ($O(n)$)** and with **linear extra space ($O(n)$)**, a standard comparison-based sort (like Quicksort or Mergesort) won't work because they take $O(n \log n)$ time. Instead, we can use the **Bucket Sort (Pigeonhole Principle)** approach.

---

### ## The Intuition

1. **Find the Bounds:** Find the minimum ($min\_val$) and maximum ($max\_val$) elements in the array.
2. **Handle Edge Cases:** If the array has fewer than 2 elements, or if $min\_val == max\_val$, all elements are identical, so the maximum gap is `0`.
3. **Establish Buckets:**
* Let $n$ be the number of elements.
* The minimum possible gap between successive elements in the sorted array is at least $\lceil (max\_val - min\_val) / (n - 1) \rceil$. Let's call this bucket size `bucket_size`.
* We create buckets to cover the range from $min\_val$ to $max\_val$. Each bucket will store only two things: the **minimum value** and the **maximum value** assigned to that bucket.


4. **The Pigeonhole Principle:**
* Because the maximum gap can't be smaller than our `bucket_size`, the maximum gap will **never** be between two elements inside the *same* bucket.
* It will always be between the maximum of one bucket and the minimum of the next non-empty bucket. This allows us to ignore intra-bucket differences and only check inter-bucket differences!



---

### ## Step-by-Step Python Implementation

Here is how you can implement this efficiently in Python:

```python
class Solution:
    def maximumGap(self, nums: list[int]) -> int:
        n = len(nums)
        if n < 2:
            return 0
        
        min_val = min(nums)
        max_val = max(nums)
        
        if min_val == max_val:
            return 0
        
        # Minimum possible gap for sorted elements
        bucket_size = max(1, (max_val - min_val) // (n - 1))
        # Number of buckets needed
        bucket_count = (max_val - min_val) // bucket_size + 1
        
        # Each bucket will store [min_val, max_val] initialized to None/infinity
        buckets_min = [float('inf')] * bucket_count
        buckets_max = [float('-inf')] * bucket_count
        
        # Distribute numbers into buckets
        for num in nums:
            bucket_idx = (num - min_val) // bucket_size
            buckets_min[bucket_idx] = min(buckets_min[bucket_idx], num)
            buckets_max[bucket_idx] = max(buckets_max[bucket_idx], num)
            
        # Scan buckets to find the maximum gap
        max_gap = 0
        prev_max = min_val
        
        for i in range(bucket_count):
            # Skip empty buckets
            if buckets_min[i] == float('inf'):
                continue
            
            # Current bucket's min minus previous non-empty bucket's max
            max_gap = max(max_gap, buckets_min[i] - prev_max)
            prev_max = buckets_max[i]
            
        return max_gap

```

---

### ## Complexity Analysis

* **Time Complexity:** $\mathcal{O}(n)$. Finding the min/max takes $\mathcal{O}(n)$, placing elements into buckets takes $\mathcal{O}(n)$, and iterating through the buckets takes $\mathcal{O}(n)$ time since the number of buckets is proportional to $n$.
* **Space Complexity:** $\mathcal{O}(n)$. We use a few arrays of size proportional to the number of buckets, which is bounded by $n$.
---

Here is the complete solution implemented in **Java**, following the same linear-time bucket sort approach:

```java
import java.util.Arrays;

class Solution {
    public int maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        
        int minVal = nums[0];
        int maxVal = nums[0];
        for (int i = 1; i < n; i++) {
            minVal = Math.min(minVal, nums[i]);
            maxVal = Math.max(maxVal, nums[i]);
        }
        
        if (minVal == maxVal) {
            return 0;
        }
        
        // Minimum possible gap for sorted elements
        int bucketSize = Math.max(1, (maxVal - minVal) / (n - 1));
        // Number of buckets needed
        int bucketCount = (maxVal - minVal) / bucketSize + 1;
        
        int[] bucketsMin = new int[bucketCount];
        int[] bucketsMax = new int[bucketCount];
        Arrays.fill(bucketsMin, Integer.MAX_VALUE);
        Arrays.fill(bucketsMax, Integer.MIN_VALUE);
        
        // Distribute numbers into buckets
        for (int num : nums) {
            int bucketIdx = (num - minVal) / bucketSize;
            bucketsMin[bucketIdx] = Math.min(bucketsMin[bucketIdx], num);
            bucketsMax[bucketIdx] = Math.max(bucketsMax[bucketIdx], num);
        }
        
        // Scan buckets to find the maximum gap
        int maxGap = 0;
        int prevMax = minVal;
        
        for (int i = 0; i < bucketCount; i++) {
            // Skip empty buckets
            if (bucketsMin[i] == Integer.MAX_VALUE) {
                continue;
            }
            
            // Current bucket's min minus previous non-empty bucket's max
            maxGap = Math.max(maxGap, bucketsMin[i] - prevMax);
            prevMax = bucketsMax[i];
        }
        
        return maxGap;
    }
}

```

---

### ## Key Java Details:

* **`Integer.MAX_VALUE` and `Integer.MIN_VALUE`:** Used similarly to Python's `float('inf')` and `float('-inf')` to initialize our empty buckets.
* **`Arrays.fill(...)`:** Efficiently initializes the min and max tracking arrays.
* **Complexity:** This runs in $\mathcal{O}(n)$ time and uses $\mathcal{O}(n)$ extra space, satisfying all constraints of the problem.


---

Let's break down the **Bucket Sort** approach using a visual, step-by-step example.

Imagine you have these numbers: `nums = [3, 6, 9, 1]`.

---

### Step 1: Find the Min and Max

First, we find the smallest and largest numbers in our array:

* `min_val = 1`
* `max_val = 9`
* Total elements ($n$) = `4`

---

### Step 2: Calculate the "Bucket Size"

Think of buckets like storage boxes or ranges. We want to divide our number range ($1$ to $9$) evenly into boxes.

* The total span is `9 - 1 = 8`.
* We have `4` numbers, which means there are `3` gaps between them.
* The smallest possible average gap would be $\frac{8}{3} \approx 2.67$. We round this up to a size of `2`.

So, our **bucket size is 2**. This means every bucket will cover a range of 2 numbers:

* Bucket 0: covers values from `1` to `2`
* Bucket 1: covers values from `3` to `4`
* Bucket 2: covers values from `5` to `6`
* Bucket 3: covers values from `7` to `8`
* Bucket 4: covers values from `9` to `10`

---

### Step 3: Throw Numbers into the Buckets

Now we take each number from `[3, 6, 9, 1]` and drop it into its matching bucket. Instead of keeping all the numbers, we **only keep track of the Minimum and Maximum number inside each bucket**:

* `1` goes into **Bucket 0** $\rightarrow$ Min: `1`, Max: `1`
* `3` goes into **Bucket 1** $\rightarrow$ Min: `3`, Max: `3`
* `6` goes into **Bucket 2** $\rightarrow$ Min: `6`, Max: `6`
* `9` goes into **Bucket 4** $\rightarrow$ Min: `9`, Max: `9`
*(Note: Bucket 3 is empty because no numbers fell between 7 and 8).*

---

### Step 4: Why this is magic (The Pigeonhole Principle)

Look at our bucket sizes. Each bucket has a size of `2`.
Because our bucket size is `2`, **the distance between any two numbers inside the exact same bucket can never be larger than 2**.

Therefore, **the maximum gap can *never* happen inside a bucket.** It can only happen **between** two different buckets!

---

### Step 5: Find the Max Gap by Looking at Buckets

Now, we just walk through our buckets from left to right, keeping track of the previous bucket's maximum value (`prev_max`):

1. Start with `prev_max = min_val` (`1`).
2. Look at **Bucket 0**: Min is `1`. Gap = `1 - 1 = 0`. Update `prev_max = 1`.
3. Look at **Bucket 1**: Min is `3`. Gap = `3 - 1 = **2**`. Update `prev_max = 3`.
4. Look at **Bucket 2**: Min is `6`. Gap = `6 - 3 = **3**`. Update `prev_max = 6`.
5. Look at **Bucket 3**: Empty, skip!
6. Look at **Bucket 4**: Min is `9`. Gap = `9 - 6 = **3**`. Update `prev_max = 9`.

Our largest gap found is **`3`**.

---

### Summary of the Trick

Instead of sorting all numbers (which takes $\mathcal{O}(n \log n)$ time), we sort them *coarsely* into buckets in a single pass. Because we only care about the gap between adjacent elements in the fully sorted array, we only need to compare the **maximum of one bucket** to the **minimum of the next non-empty bucket**.

Does this visual breakdown make the bucket logic click a bit more?

---

That sentence is the core of why the whole algorithm works! Let's break it down with simple numbers.

### The Rule of the Bucket Size

When we set our **bucket size to 2**, we are saying that every single bucket represents a range of numbers *strictly smaller than 2 units wide*.

For example, look at **Bucket 1**, which covers the range from `3` to `4`:

* What is the biggest possible difference between any two numbers you can put inside this bucket?
* The lowest number you can put in is `3`.
* The highest number you can put in is `4`.
* The difference between them is $4 - 3 = 1$.

Even if a bucket holds multiple numbers, say `3`, `3`, and `4`, the gap between the smallest and largest number inside that same bucket **cannot exceed the bucket size** (which is 2).

---

### Why does this matter for the Maximum Gap?

Think about what we are looking for: the **maximum** difference between successive elements in the sorted array.

Imagine our bucket size is `2`.

* That means the gap *inside* any bucket is at most `2`.
* But what if the overall maximum gap of the whole array turns out to be `3` or `5` or `10`?
* Since any intra-bucket gap is *at most* `2`, a gap of `3`, `5`, or `10` **is physically too big to fit inside a single bucket**.

Therefore, the maximum gap **guaranteed** cannot be hiding inside any single bucket. It *must* happen across the fence—between the maximum number of one bucket and the minimum number of the next non-empty bucket!

---

### A Quick Mental Check

* **Inside a bucket:** Gaps are small (bounded by the bucket size, e.g., $\le 2$).
* **Between buckets:** Gaps can be large because we are jumping over empty space or skipping to a higher range.

Because we know the maximum gap *cannot* be inside a bucket, we can completely ignore what happens inside the buckets and only look at the jumps *between* them. That's what makes the algorithm so fast!

---

It is **mathematically impossible** for the maximum gap to be inside a bucket.

Here is why, based on how the bucket size is chosen:

### 1. The Bucket Size is Based on the "Average" Gap

When we calculate the bucket size:


$$\text{bucket\_size} = \frac{\text{max\_val} - \text{min\_val}}{n - 1}$$

This formula calculates the **average gap size** if all the numbers were spread out perfectly and evenly across the entire range.

### 2. The Pigeonhole Guarantee

Because it's an average, **at least one gap** in the actual sorted array *must* be greater than or equal to that average bucket size. (You can't have every single gap be smaller than the average; otherwise, the total distance wouldn't reach from the minimum to the maximum).

This means the **true maximum gap** of the array is **always greater than or equal to the bucket size**.

### 3. Why an Intra-Bucket Gap Can Never Win

* The maximum possible difference between any two numbers *inside* a single bucket is strictly limited by the **bucket size** itself. (For example, if your bucket covers numbers from 3 to 4, the largest gap inside it is $4 - 3 = 1$).
* But we just established that the true maximum gap of the array is **larger** than the bucket size.

Because the true maximum gap is bigger than the bucket size, **it can never fit inside a single bucket**. The only place a gap that large can exist is *between* two different buckets (jumping across empty space or moving to a higher range).

That is the genius of the Pigeonhole Principle here: by making the buckets small enough, we mathematically guarantee that the maximum gap *must* live outside of them, leaving us only to check the boundaries between buckets!

---



