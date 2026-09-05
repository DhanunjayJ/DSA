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

It is completely normal to be skeptical of this! It sounds like magic—how can we just *ignore* what happens inside the buckets without missing the biggest gap?

Let’s put it to the test with a concrete, hands-on dry run using an irregular array, and look at the **physical physics** of why an intra-bucket gap can never be the winner.

---

### ## The Dry Run: `nums = [2, 5, 6, 20, 25]`

* Total elements ($N$) = `5`
* Minimum value = `2`
* Maximum value = `25`
* Total distance from min to max = $25 - 2 = 23$

#### Step 1: Calculate the Bucket Size

We have 5 numbers, which means there are $5 - 1 = 4$ gaps between them.


$$\text{bucket\_size} = \frac{23}{4} = 5.75 \rightarrow \text{rounded down to } 5$$

This means **every single bucket is a box that spans a width of 5 units.**

* Box 0 covers values from `2` to `7`
* Box 1 covers values from `8` to `13`
* Box 2 covers values from `14` to `19`
* Box 3 covers values from `20` to `25`

---

#### Step 2: Drop the Numbers Into the Boxes

Let's see where our numbers land:

* `2`, `5`, and `6` all fall into **Box 0** (because they are all between 2 and 7).
* `20` falls into **Box 3**.
* `25` falls into **Box 4** (or the edge of Box 3).

---

#### Step 3: Let's Check Inside Box 0 (The Intra-Bucket Gaps)

Box 0 contains the numbers `2`, `5`, and `6`. If we sort them inside this box, what are the gaps between them?

* Gap between 2 and 5 = **3**
* Gap between 5 and 6 = **1**

Now, look at the **true maximum gap** of the entire array. If you look at the sorted array `[2, 5, 6, 20, 25]`, the biggest gap is between `6` and `20`, which is:


$$20 - 6 = \mathbf{14}$$

---

### ## Why couldn't the gap of 14 be *inside* a bucket?

Look at Box 0 again. Box 0 is a box with a strict size limit of **5**.

* **The Container Rule:** You cannot fit a distance of 14 inside a box that only spans 5 units. The numbers inside Box 0 (`2`, `5`, and `6`) are crammed into a 5-unit space, so their internal gaps (`3` and `1`) **must** be smaller than 5.
* **The Pigeonhole Guarantee:** Because the total distance of 23 is split into 4 gaps, *at least one* gap in the whole array is guaranteed to be $\ge 5$ (in this case, our max gap is `14`).

Since **every single intra-bucket gap is strictly limited by the bucket size (5)**, and the **true maximum gap (14) is way larger than the bucket size**, the true maximum gap **physically cannot fit inside any bucket**.

### The Takeaway

An intra-bucket gap is trapped inside a small box (size 5). The maximum gap is too big to fit in that box, so it *has* to be the jump *between* Box 0 and Box 3 (from 6 to 20).

---
The **Pigeonhole Principle** is a famous mathematical rule: *If you have more pigeons than pigeonholes, at least one pigeonhole must contain more than one pigeon.*

In this specific problem, we flip it slightly to use a powerful guarantee:

### The Pigeonhole Guarantee Here:

> **If you have $n$ numbers and you divide their total range into $n - 1$ buckets, at least one bucket is GUARANTEED to be completely empty.**

Let's see how this plays out with our big example from earlier:

* We had **10 numbers** ($n = 10$).
* We created **9 gaps** (which means we set up roughly $n - 1$ buckets).
* Look back at the table: **Buckets 2, 3, 5, 6, and 7 were completely empty!**

---

### Why does an empty bucket solve the whole problem?

Because of the Pigeonhole Principle forcing at least one bucket to be empty, it creates a physical "cliff" or gap in your number line:

1. **You can't have numbers in an empty bucket.** That means there is a whole range of values where *no numbers exist*.
2. When the sorted list of numbers hits that empty space, it has to **jump completely over it** to get to the next available number.
3. Because it has to jump across empty space, that specific jump (**the inter-bucket gap**) is guaranteed to be larger than the bucket size.
4. And because every intra-bucket gap is trapped inside a small box, **the true maximum gap will always be one of these cross-bucket jumps over an empty space.**

### Summary

The Pigeonhole Principle guarantees that **empty buckets will exist**. The existence of empty buckets guarantees that **large gaps must happen between buckets**. And that is why we can safely ignore everything inside the buckets and only look at the spaces *between* them!

---

To see how the **Pigeonhole Principle** guarantees empty buckets, let’s translate the classic math puzzle directly into our problem:

* **The Pigeons:** Your $n$ numbers (e.g., 10 numbers = 10 pigeons).
* **The Pigeonholes:** The buckets we create to span from `min` to `max`.

### Why an Empty Bucket is Guaranteed

Think about what happens when you spread your numbers across the buckets:

1. **The Uniform Case (No empty buckets):** If your numbers were spaced **completely evenly** (like `[2, 4, 6, 8]`), every single bucket would catch exactly one number. In that rare case, every gap is identical, and there are **zero** empty buckets.
2. **The Real/Messy Case (Empty buckets appear):** But what happens if your numbers are *not* evenly spaced—meaning some numbers are clumped tightly together, and others are far apart?
* When numbers clump together, they squeeze into the same bucket (sharing a pigeonhole).
* Because you only have $n$ numbers total, if multiple numbers pile into the early buckets, it leaves **no numbers left over** for the later ranges.
* That wide-open space stretches across a whole section of the number line. Because no numbers exist in that range, **no numbers land in those buckets, leaving them completely empty.**



### The Big Takeaway

The Pigeonhole Principle tells us that if your array has a gap that is *larger* than the average bucket size, the numbers are forced to jump over a region. That skipped region results in **one or more empty buckets**.

An empty bucket is essentially a **giant neon sign** saying: *"A massive gap happened right here, forcing us to leap over this space!"* That is why we only need to look at the boundaries right across those empty spaces to find our maximum gap.

---