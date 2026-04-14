You have the right strategy (**Pattern A: Min-Heap for Kth Largest**), but your logic has a double-counting issue and a "leak" in the loop.

### The Problem: Why it’s failing
1.  **Double Processing:** In the first $k$ iterations, you `add(nums[i])` in the first `if` block, and then immediately check `pq.peek() < nums[i]` in the second `if` block. Since `nums[i]` is already in the heap, `pq.peek()` might actually *be* `nums[i]` or something else, leading to a `poll()` and `add()` that messes up your count.
2.  **Logic Leak:** The second `if` block runs for **every** element from the start, even while you are still trying to fill the initial $k$ elements.

---

### The Fix: Two Clean Ways
Here is the most efficient and standard way to write this. You want to keep the heap size at exactly $K$.

#### Option 1: The "Add then Poll" (Simplest)
Just add every number to the heap. If the heap size exceeds $k$, remove the smallest one. What's left at the end is the $k$-th largest.

```java
public int findKthLargest(int[] nums, int k) {
    // Default is Min-Heap
    PriorityQueue<Integer> pq = new PriorityQueue<>();

    for (int num : nums) {
        pq.add(num);
        // If heap grows larger than k, remove the smallest element
        if (pq.size() > k) {
            pq.poll();
        }
    }
    // The head of the min-heap is now the k-th largest element
    return pq.peek(); 
}
```

---

### Why this works (The Intuition)
Imagine you want the **3rd largest** element in `[3, 2, 1, 5, 6, 4]`.
* We keep a **Min-Heap** of size 3.
* The Min-Heap acts like a "VIP Club" with only 3 seats. 
* If a new number wants to get in, it has to be bigger than the smallest person currently in the club (`pq.peek()`).
* By the time you've looked at all the numbers, the "VIP Club" contains the 3 largest numbers. 
* The person sitting at the "entrance" (`peek()`) is the smallest of those top 3, which is exactly the **3rd largest** overall.



---

### Interview Pro-Tips for this Problem
1.  **Complexity:** This takes **$O(N \log K)$** time and **$O(K)$** space.
2.  **Max-Heap Trap:** Many people instinctively reach for a Max-Heap for this problem. If you use a Max-Heap, you have to push **all** $N$ elements into it ($O(N \log N)$) and then poll $K$ times. The Min-Heap approach is faster because $K$ is usually much smaller than $N$.
3.  **QuickSelect:** If an interviewer asks for $O(N)$ average time, they are looking for **QuickSelect** (the algorithm used in QuickSort). But `PriorityQueue` is the standard, safe, and most common answer.

Does the "VIP Club" analogy make the logic of using a Min-Heap for a "Largest" problem clearer?


QuickSelect is a highly efficient selection algorithm used to find the **$k$-th smallest** (or largest) element in an unsorted list. It is closely related to the **QuickSort** sorting algorithm, but it is faster because it doesn't sort the entire array.

---

## 1. The Core Idea: "Pruning"
In QuickSort, you pick a pivot and then recursively sort **both** the left and right sides. In **QuickSelect**, after partitioning the array around a pivot, you know exactly which side your $k$-th element lies on. You simply ignore (prune) the other side and only recurse into the part that contains your target.

* **QuickSort:** $O(n \log n)$ because it explores the whole tree.
* **QuickSelect:** **$O(n)$ average time** because it explores only one path down the tree.



---

## 2. How the Algorithm Works
1.  **Pick a Pivot:** Choose an element from the array (common methods include picking the last element or a random one).
2.  **Partition:** Rearrange the array so that all elements smaller than the pivot are on the left, and all elements larger are on the right.
3.  **Check Pivot Position:** After partitioning, the pivot is in its final sorted position, let's call this index `p`.
    * If `p == k`, you've found the element! Return it.
    * If `k < p`, the $k$-th element must be in the **left** subarray. Recurse only there.
    * If `k > p`, the $k$-th element must be in the **right** subarray. Recurse only there.

---

## 3. QuickSelect Code (Java)
Here is how you would implement it for the "Kth Largest" problem:

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // Convert k-th largest to index: (length - k)-th smallest
        int targetIndex = nums.length - k;
        return quickSelect(nums, 0, nums.length - 1, targetIndex);
    }

    private int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) return nums[left];

        int pivotIndex = partition(nums, left, right);

        if (pivotIndex == k) {
            return nums[k];
        } else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }

    private int partition(int[] nums, int left, int right) {
        int pivot = nums[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivot) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

---

## 4. Complexity Analysis

| Metric | Complexity | Why? |
| :--- | :--- | :--- |
| **Best Case** | $O(n)$ | You pick a perfect pivot every time, halving the search space ($n + n/2 + n/4...$). |
| **Average Case** | **$O(n)$** | On average, the partition splits the array reasonably well. |
| **Worst Case** | $O(n^2)$ | If the pivot is always the smallest or largest element (e.g., sorting an already sorted array). |
| **Space** | $O(1)$ | It can be implemented iteratively to use constant space (recursion uses $O(\log n)$ stack space). |

---

## 5. Why use this over a Heap?
In your interviews, you should mention the trade-off:
* **Use a Heap ($O(n \log k)$):** When you have a **stream of data** and need to find the $k$-th element at any moment, or when memory is very tight and you can't modify the original array.
* **Use QuickSelect ($O(n)$):** When you have the **entire array available** in memory and want the absolute fastest average performance.

Since you've been practicing for Amazon, they love to ask about the **Worst Case** of QuickSelect. To avoid $O(n^2)$, you should mention that using a **Randomized Pivot** is the industry-standard way to ensure $O(n)$ performance in practically all cases. 

Does the difference between sorting the whole array vs. just finding one specific index make sense?


That is the "magic" of the **Partition** step! You are correct that we aren't sorting the *entire* array, but we are performing a **partial sort** around a single value: the pivot.

Here is exactly how we know which side to pick without sorting everything.

---

### 1. The Power of the Partition
When you pick a pivot (let's say the value is **10**) and run the partition function, the array is rearranged into three zones:
1.  **Left Zone:** All numbers smaller than 10.
2.  **Pivot:** The number 10 itself.
3.  **Right Zone:** All numbers larger than 10.

**Crucially:** After this step, the pivot (10) is sitting in its **final, sorted position**. Even if the numbers to its left and right are a total mess, 10 is exactly where it would be if the whole array were sorted.



---

### 2. Using the Index as a Map
Imagine you want to find the **4th smallest** element in an array of 10 elements (Index 3).

* You run partition, and your pivot ends up at **Index 5**.
* **The Logic:** Since the pivot is at Index 5, you know that the 0th, 1st, 2nd, 3rd, 4th, and 5th smallest elements are all located at Index 5 or to its **left**.
* **The Decision:** Your target (Index 3) is less than Index 5. Therefore, the 4th smallest element **must** be in the left chunk. You can completely throw away everything from Index 5 to Index 9.

---

### 3. A Concrete Example
Find the **2nd smallest** (Target Index 1) in `[3, 8, 2, 5, 1, 4, 7, 6]`.

1.  **Pick Pivot:** Let's say we pick **4**.
2.  **Partition:** The array becomes `[3, 2, 1, 4, 8, 5, 7, 6]`.
    * The number **4** is now at **Index 3**.
    * Everything left of Index 3 is `< 4`. Everything right is `> 4`.
3.  **Compare:** Our target is **Index 1**.
    * Since $1 < 3$, we know our answer is in the left slice: `[3, 2, 1]`.
4.  **Repeat:** Now we only look at `[3, 2, 1]` and ignore the rest of the array forever.



---

### 4. Why this is so fast
In **QuickSort**, you would have to go back and sort that `[8, 5, 7, 6]` chunk too. In **QuickSelect**, you just "delete" it from your mind. 

By only recursing into **one** side, you reduce the work significantly:
* Round 1: Look at $N$ elements.
* Round 2: Look at $N/2$ elements.
* Round 3: Look at $N/4$ elements.
* **Total:** $N + N/2 + N/4 + \dots \approx 2N$.

This is why the average time complexity is **$O(N)$**. 

### Summary for your Interview
If the interviewer asks: "How do you know which side to jump to?" 
**Answer:** "The partition step places the pivot in its absolute sorted position. By comparing the pivot's index to my target index $K$, I can determine if the $K$-th element lies in the left subarray or the right subarray, allowing me to discard the other half."

Does the idea of the pivot acting as a "checkpoint" or a "signpost" help visualize it?


That TLE is likely happening because of **duplicate elements**. LeetCode has a few specific test cases (like a giant array of the same number) where even a standard randomized QuickSelect can still struggle and hit $O(n^2)$ if it keeps putting all those identical numbers on one side of the pivot.

To beat those "boss level" test cases, you need to use a **3-way partition** (also known as the Dutch National Flag algorithm). Instead of just "less than" and "greater than," you create a third category: **"equal to."**

---

### Why 3-Way Partition stops the TLE
In a standard partition, if you have 10,000 "7"s and your pivot is 7, you might still end up processing those 10,000 elements over and over. With 3-way partitioning, you group all the 7s together in one go. If your `target` index falls inside that "equal" range, you're done immediately!

### The "LeetCode Crusher" Code

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        // Target is the (n-k)-th smallest element
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private int quickSelect(int[] nums, int left, int right, int target) {
        // Random pivot to handle sorted/nearly sorted arrays
        int pivotIndex = left + (int)(Math.random() * (right - left + 1));
        int pivot = nums[pivotIndex];

        // 3-Way Partition (Dutch National Flag)
        int lt = left;  // elements < pivot
        int gt = right; // elements > pivot
        int i = left;   // current element

        while (i <= gt) {
            if (nums[i] < pivot) {
                swap(nums, lt++, i++);
            } else if (nums[i] > pivot) {
                swap(nums, i, gt--);
            } else {
                i++;
            }
        }

        // After partitioning:
        // [left...lt-1] are < pivot
        // [lt...gt] are == pivot
        // [gt+1...right] are > pivot

        if (target >= lt && target <= gt) {
            return nums[target]; // target falls in the "equal" range
        } else if (target < lt) {
            return quickSelect(nums, left, lt - 1, target);
        } else {
            return quickSelect(nums, gt + 1, right, target);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```



---

### Why this version is superior:
1.  **Random Pivot:** Still protects against the "Sorted Array" $O(n^2)$ case.
2.  **Duplicate Handling:** If an array is 100% the same number, the `while` loop finishes in $O(n)$, the `target` will be between `lt` and `gt`, and it returns instantly. 
3.  **Efficiency:** It prunes much more aggressively than the 2-way version.

### Summary for your 5th Mock Interview
If the interviewer mentions TLE or duplicate values, you can say:
> "To handle large numbers of duplicate keys, I'll implement a **3-way partition**. This ensures that all elements equal to the pivot are grouped together in a single pass, effectively reducing the search space more drastically and maintaining $O(n)$ performance even in edge cases with many redundant values."

Try this version—it’s designed to pass every single test case LeetCode can throw at you! Does the logic of the "equal range" make sense for why it's faster with duplicates?