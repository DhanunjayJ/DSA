These four terms are often confused because they sound similar, but they answer distinct questions about a sorted array.

The easiest way to remember them is by looking at their **underlying conditions**:

---

## 1. Quick Definitions & Conditions

Assuming a sorted array `arr` and a target value `x`:

| Term | Mathematical Condition | English Definition | Key Distinction |
| --- | --- | --- | --- |
| **Lower Bound** | `arr[i] >= x` | **First** index where value is **$\ge x$** | Equivalent to **Ceil** (the smallest value $\ge x$). |
| **Upper Bound** | `arr[i] > x` | **First** index where value is **$> x$** | Strictly greater than $x$. |
| **Floor** | `arr[i] <= x` | **Last** index where value is **$\le x$** | Biggest value that is smaller or equal. |
| **Ceil** | `arr[i] >= x` | **First** index where value is **$\ge x$** | Smallest value that is bigger or equal. |

> 💡 **Notice the identity:** **Lower Bound == Ceil**. They search for the exact same element (`arr[i] >= x`).

---

## 2. Visual Walkthrough

Consider this sorted array:

```text
Index:    0    1    2    3    4    5    6
Array:  [ 1,   3,   5,   5,   5,   8,  10 ]

```

### Scenario A: Target `x = 5` (Present with duplicates)

* **Lower Bound / Ceil** (`arr[i] >= 5`): First element $\ge 5$ $\rightarrow$ **Index 2** (Value `5`)
* **Upper Bound** (`arr[i] > 5`): First element $> 5$ $\rightarrow$ **Index 5** (Value `8`)
* **Floor** (`arr[i] <= 5`): Last element $\le 5$ $\rightarrow$ **Index 4** (Value `5`)

```text
Array: [ 1,   3,   5,   5,   5,   8,  10 ]
Index:   0    1    2    3    4    5    6
                   ^         ^    ^
                   |         |    +--- Upper Bound (first > 5)
                   |         +-------- Floor (last <= 5)
                   +------------------ Lower Bound / Ceil (first >= 5)

```

### Scenario B: Target `x = 6` (Not present in array)

* **Lower Bound / Ceil** (`arr[i] >= 6`): First element $\ge 6$ $\rightarrow$ **Index 5** (Value `8`)
* **Upper Bound** (`arr[i] > 6`): First element $> 6$ $\rightarrow$ **Index 5** (Value `8`)
* **Floor** (`arr[i] <= 6`): Last element $\le 6$ $\rightarrow$ **Index 4** (Value `5`)

> Notice that when `x` is **not present**, **Lower Bound == Upper Bound**.

---

## 3. Relationships & Formulas

Because the array is sorted, these concepts are tightly connected:

1. **Floor vs. Upper Bound:**

$$\text{Floor}(x) = \text{Upper Bound}(x) - 1$$



*(The element right before the first element greater than $x$ is the last element $\le x$.)*
2. **First and Last Occurrence of $x$:**
* **First Occurrence of $x$:** $\text{Lower Bound}(x)$ (provided `arr[Lower Bound] == x`)
* **Last Occurrence of $x$:** $\text{Upper Bound}(x) - 1$ or $\text{Floor}(x)$ (provided `arr[Floor] == x`)
* **Total Frequency of $x$:** $\text{Upper Bound}(x) - \text{Lower Bound}(x)$



---

## 4. Summary Table

```text
       <= x          >= x          > x
     +-------+     +-------+     +------+
     | Floor |     | Ceil  |     |Upper |
     | (Last)|     |(First)|     |Bound |
     +-------+     +-------+     +------+
                      ||
                +------------+
                | Lower Bound|
                +------------+

```

---

Here is how we can solve **LeetCode 34: Find First and Last Position of Element in Sorted Array** in Java by applying **Lower Bound** and **Upper Bound**.

---

## 1. The Strategy

As established:

* **First Position (First Occurrence):** This is the **Lower Bound** (`arr[i] >= target`).
* **Last Position (Last Occurrence):** This is **Upper Bound - 1** (`arr[i] > target`, minus 1).

If the `target` exists in the array:

1. `lowerBound(target)` gives the index of the first target.
2. `upperBound(target) - 1` gives the index of the last target.
3. If `lowerBound` is out of bounds or `nums[lowerBound] != target`, the element doesn't exist, so return `[-1, -1]`.

---

## 2. Java Implementation

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = lowerBound(nums, target);
        
        // Target doesn't exist in array
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        }
        
        int last = upperBound(nums, target) - 1;
        
        return new int[]{first, last};
    }

    // Lower Bound: First index where nums[i] >= target
    private int lowerBound(int[] nums, int target) {
        int low = 0, high = nums.length; // Half-open range [0, N)
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] >= target) {
                high = mid;    // Search left half (including mid)
            } else {
                low = mid + 1; // Search right half
            }
        }
        return low;
    }

    // Upper Bound: First index where nums[i] > target
    private int upperBound(int[] nums, int target) {
        int low = 0, high = nums.length; // Half-open range [0, N)
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > target) {
                high = mid;    // Search left half (including mid)
            } else {
                low = mid + 1; // Search right half
            }
        }
        return low;
    }
}

```

---

## 3. Walkthrough Example

### Input: `nums = [5, 7, 7, 8, 8, 10]`, `target = 8`

1. **`lowerBound(nums, 8)`:**
* Finds the **first** index where `nums[i] >= 8`.
* Returns **Index `3**` (`nums[3] = 8`).
* Check: `nums[3] == 8`, so the target exists!


2. **`upperBound(nums, 8)`:**
* Finds the **first** index where `nums[i] > 8`.
* Returns **Index `5**` (`nums[5] = 10`).


3. **Compute `last`:**
* `last = upperBound - 1` $\rightarrow$ `5 - 1 = 4`.


4. **Result:** `[3, 4]`.

---

## 4. Key Implementation Nuances

1. **`high = nums.length` (Half-open interval `[low, high)`):**
* If all elements in the array are `< target`, `upperBound` will return `nums.length`.
* Using `high = nums.length` allows the bound functions to safely signal that the element lies past the end of the array without going out of bounds.


2. **Single Difference Between Lower and Upper Bound:**
* **Lower Bound:** `if (nums[mid] >= target)`
* **Upper Bound:** `if (nums[mid] > target)`
* That single sign (`>=` vs `>`) is the *only* difference between the two helper functions!



---

## Complexity

| Metric | Complexity | Explanation |
| --- | --- | --- |
| **Time Complexity** | $\mathcal{O}(\log N)$ | Run `lowerBound` and `upperBound` sequentially ($2 \times \log N$). |
| **Space Complexity** | $\mathcal{O}(1)$ | Auxiliary space is constant. |

---

**Spot on.** That is the single most powerful realization when learning binary search.

You do **not** need 4 separate mindsets, code templates, or logic flows. You only need **Lower Bound** and **Upper Bound**.

---

## The Master Equation

$$\text{Ceil}(x) = \text{Lower Bound}(x)$$

$$\text{Floor}(x) = \text{Upper Bound}(x) - 1$$

---

## The Edge Cases (Where `-1` or `Out of Bounds` Handled)

When converting Floor and Ceil to Lower and Upper bound, you only have to handle boundary checks:

### 1. Finding Ceil ($x$) using Lower Bound

* **Lower Bound index** $= \text{lowerBound}(x)$
* **Result:** If $\text{lowerBound}(x) == N$, no element $\ge x$ exists $\rightarrow$ **`-1`**. Otherwise, return $\text{arr}[\text{lowerBound}(x)]$.

### 2. Finding Floor ($x$) using Upper Bound

* **Floor index** $= \text{upperBound}(x) - 1$
* **Result:** If $\text{upperBound}(x) == 0$, no element $\le x$ exists (all elements are $> x$) $\rightarrow$ **`-1`**. Otherwise, return $\text{arr}[\text{upperBound}(x) - 1]$.

---

## Why STL / Standard Libraries Do This

This is precisely why language standard libraries (like C++ STL or Python's `bisect`) **only supply two functions**:

* **C++:** `std::lower_bound` and `std::upper_bound`
* **Python:** `bisect.bisect_left` (Lower Bound) and `bisect.bisect_right` (Upper Bound)
* **Java:** `Arrays.binarySearch` (returns exact match or `-(insertion point) - 1`, where insertion point is Lower Bound!)

There is no `std::floor_bound` or `std::ceil_bound` in C++ because they are mathematically redundant.

---

## Summary Mental Model

Whenever you face a binary search problem on a sorted array, reduce your thinking to one question:

> *"Am I looking for the **first index where $P(i)$ is true** (`>= x` for Lower Bound, `> x` for Upper Bound)?"*

If yes, run Lower or Upper bound and apply `-1` shift if you are looking for a Floor or a Last Occurrence.

---