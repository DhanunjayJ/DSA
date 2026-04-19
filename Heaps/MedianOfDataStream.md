### 1. The Core Intuition: "Dividing the World"
To explain this approach to an interviewer (or yourself), think of it as **balancing a scale**.

A median is the "middle" of a sorted list. To find it instantly, you need to split your data into two equal halves:
* **The Left Half (Small numbers):** You only care about the **largest** among them. (Max-Heap)
* **The Right Half (Large numbers):** You only care about the **smallest** among them. (Min-Heap)



### 2. How to Arrive at this Approach (The "Exploration" Steps)
If you were in an interview and forgot the solution, here is the logical path to rediscover it:

* **Step 1: The Brute Force.** "I could store numbers in a list and sort it every time." 
    * *Problem:* $O(N \log N)$ every time we add a number is too slow.
* **Step 2: Binary Search/Insertion.** "I could keep the list sorted and use `Collections.binarySearch` to find the spot."
    * *Problem:* $O(N)$ to shift elements in an array is still slow for millions of numbers.
* **Step 3: The "Middle" Observation.** "I only ever need the middle 1 or 2 numbers. I don't need the rest of the list to be perfectly sorted."
* **Step 4: Data Structure Choice.** "What structure gives me the 'best' (max or min) element in $O(1)$ and lets me add in $O(\log N)$? **A Heap.**"
* **Step 5: The "Two-Heap" Realization.** "If I use one heap, I only get one end. If I use two heaps facing each other, I get the two 'middle' ends of the sorted data."

### 3. Explaining the "Balancing" Logic
The trickiest part to explain is why we "pass" the number through one heap before putting it in the other. 

**The Explanation:**
> "To ensure the heaps stay sorted relative to each other (i.e., every number in the left heap is smaller than every number in the right heap), we don't just add a number to a side. We add it to one side, let that side pick its 'best' candidate, and then move that candidate to the other side. This naturally maintains the boundary between small and large numbers."



### 4. Are there other ways?
While the Two-Heap method is the **Industry Standard**, there are two other ways you might see:

#### **A. Balanced Binary Search Tree (BST)**
In Java, you could use a `TreeMap` (or `multiset` in C++).
* **How:** You keep a pointer to the "median node." When you add a number, you move the pointer one step left or right to keep it in the center.
* **Comparison:** Same complexity ($O(\log N)$ add, $O(1)$ find), but much harder to implement from scratch in an interview.

#### **B. Bucket Sort / Frequency Array (Limited Range)**
* **How:** If you knew all numbers were between 1 and 100, you could just count frequencies in an array of size 100.
* **Comparison:** $O(1)$ add, but only works if the range of numbers is small.

### Summary of your implementation:
* **Time Complexity:** $O(\log N)$ for `addNum`, $O(1)$ for `findMedian`.
* **Space Complexity:** $O(N)$ to store the numbers.

**One tiny "Pro" tip for your code:** In `findMedian`, you use `(right.peek() + left.peek()) / 2.0`. This is perfect. Some people make the mistake of using `/ 2`, which causes integer division bugs. You avoided that!

This is a fantastic "follow-up" question because it moves from **general data structures** to **domain-specific optimizations**. This approach is essentially a variation of **Counting Sort**.

If the range of numbers is small and fixed (like 1 to 100), you don't need to store the numbers themselves; you only need to store **how many times each number has appeared**.

### 1. The Data Structure
Instead of Heaps, you use a simple integer array where the **index** represents the number, and the **value** represents the count.
```java
int[] counts = new int[101]; // Indices 0-100
int totalElements = 0;
```

---

### 2. How `addNum(int num)` works
When a number comes in, you just increment its spot in the array. This is an **$O(1)$** operation.
* `addNum(5)` $\rightarrow$ `counts[5]++`
* `addNum(5)` $\rightarrow$ `counts[5]++` (now we know there are two 5s)
* `totalElements++`

---

### 3. How `findMedian()` works
Since the array indices (0, 1, 2... 100) are already "sorted," finding the median is just a matter of "walking" through the counts until you reach the middle.

1.  Calculate the target index: `mid = totalElements / 2`.
2.  Start a running sum at `0`.
3.  Loop through the `counts` array. Add the value at each index to your sum.
4.  As soon as the sum reaches or exceeds your `mid`, the current **index** is your median (or one of them).



---

### 4. A Concrete Example
Imagine we have these numbers: `1, 2, 2, 2, 3`. The median is **2**.

| Index (The Number) | Count (Frequency) | Running Total |
| :--- | :--- | :--- |
| 1 | 1 | 1 |
| 2 | 3 | 4 (Target reached!) |
| 3 | 1 | 5 |

If we are looking for the 3rd element (the middle of 5), we see that at Index 1, we only have 1 element. At Index 2, we suddenly have 4 elements. That means the 2nd, 3rd, and 4th elements are all the number **2**.

---

### 5. Pros and Cons vs. The Heap Method

| Feature | Two-Heap Method | Frequency Array Method |
| :--- | :--- | :--- |
| **Add Time** | $O(\log N)$ | **$O(1)$** |
| **Find Time** | **$O(1)$** | $O(\text{Range})$ — in this case, $O(100)$ |
| **Space** | $O(N)$ (Grows with data) | **$O(\text{Range})$** (Fixed size) |
| **Constraint** | Works for any numbers | **Only works for small ranges** |

### Why an interviewer might ask this:
They want to see if you can optimize for **specific constraints**. If a company tells you, "We are processing billions of movie ratings (1 to 5 stars)," the Heap method is actually **bad** because it stores billions of integers. The Frequency Array method is **perfect** because it only needs an array of size 5, no matter how many billions of ratings you have.

This approach is incredibly fast because it treats the **indices** of an array as the actual values. Since the array is naturally indexed from $0, 1, 2 \dots$, the "sorting" is done for you automatically.

Here is how you would implement it in Java.

### The Implementation

```java
class MedianFinder {
    // Let's assume the range is 0 to 100
    private int[] counts = new int[101];
    private int totalElements = 0;

    public void addNum(int num) {
        // O(1) time
        counts[num]++;
        totalElements++;
    }

    public double findMedian() {
        // O(Range) time - in this case O(101)
        if (totalElements == 0) return 0.0;

        if (totalElements % 2 != 0) {
            // Odd: find the element at position (total/2 + 1)
            return (double) findKthElement(totalElements / 2 + 1);
        } else {
            // Even: average of (total/2) and (total/2 + 1)
            int m1 = findKthElement(totalElements / 2);
            int m2 = findKthElement(totalElements / 2 + 1);
            return (m1 + m2) / 2.0;
        }
    }

    private int findKthElement(int k) {
        int countSum = 0;
        for (int i = 0; i < counts.length; i++) {
            countSum += counts[i];
            if (countSum >= k) {
                return i; // i is the actual number
            }
        }
        return -1;
    }
}
```

---

### How it Works (Step-by-Step)

Imagine you have the numbers: `1, 4, 1, 3, 1`

**1. The `addNum` Phase:**
Your `counts` array would look like this:
* `counts[1] = 3` (The number 1 appeared 3 times)
* `counts[3] = 1` (The number 3 appeared 1 time)
* `counts[4] = 1` (The number 4 appeared 1 time)
* `totalElements = 5`



**2. The `findMedian` Phase:**
We have 5 elements (odd), so the median is the **3rd** element in sorted order.
* We start a loop through the `counts` array:
    * **Index 0:** count is 0. `sum = 0`.
    * **Index 1:** count is 3. `sum = 3`. 
    * **Is `sum >= 3`?** Yes! 
* The loop stops at **Index 1**. The median is **1**.

---

### Why is this efficient?

* **No Sorting:** You never call `Arrays.sort()` or maintain a heap. The "sorting" is inherent in the array structure.
* **Constant Memory:** If you have 1 million numbers but they are all between 0 and 100, your array stays size 101. A Heap would grow to size 1,000,000.
* **Deterministic Time:** No matter how much data you have, finding the median always takes exactly 101 steps.

### When would this fail?
If the range of numbers is **large or unknown** (e.g., numbers can be anywhere from $-10^9$ to $10^9$), you cannot create an array that large. In that case, you must go back to the **Two-Heap** approach you remembered earlier.

This is a classic example of **Trading Generality for Performance**. If you know your data limits, you can beat the standard "best" algorithms!