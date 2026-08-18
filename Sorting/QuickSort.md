When we talk about the "ways" to write QuickSort, we are actually talking about two different choices: **How do we pick the pivot?** and **How do we partition the array?**

Because QuickSort is so famous, computer scientists have spent decades tweaking it. Here are the main ways to write it, how they compare, and which one is the "best."

---

## 1. The Partitioning Schemes (How you move the numbers)

### Way 1: Lomuto’s Partition Scheme (The one we just used)

* **How it works:** You pick a pivot (usually the last element) and use a single pointer to sweep from left to right, tossing smaller elements to the left.
* **Pros:** It is extremely easy to write, read, and memorize. It is highly resistant to infinite loops and off-by-one errors (the bugs you were running into earlier).
* **Cons:** It does more swaps than necessary, making it slightly slower in practice than other methods.

### Way 2: Hoare’s Partition Scheme (What you originally tried)

* **How it works:** You use two pointers (`p1` at the start, `p2` at the end) and move them toward each other. When they find elements on the wrong side of the pivot, they swap them.
* **Pros:** It is highly efficient. On average, it performs **3 times fewer swaps** than Lomuto's scheme.
* **Cons:** It is notoriously difficult to code perfectly from memory. Handling duplicate values and preventing pointers from crossing incorrectly or going out of bounds is very tricky.

### Way 3: 3-Way Partitioning (Dutch National Flag)

* **How it works:** Instead of dividing the array into two parts (less than, greater than), it divides it into three parts: **less than the pivot, equal to the pivot, and greater than the pivot**.
* **Pros:** This is the absolute best way to handle arrays that have a lot of **duplicate numbers**. Standard Hoare and Lomuto schemes can slow down to $O(N^2)$ time if all elements are the same, but 3-way partitioning sorts an array of all duplicates in $O(N)$ time.
* **Cons:** Slightly more complex to write than Lomuto.

---

## 2. The Pivot Strategies (Which number you pick)

No matter which partitioning scheme you use, picking a bad pivot (like the smallest or largest number in the array) ruins QuickSort's performance.

* **First / Last Element:** Easy to code, but if you get an array that is *already sorted* (or reverse sorted), QuickSort slows down to its worst-case $O(N^2)$ time.
* **Random Pivot:** You pick a random index, swap it with the last element, and then do standard partitioning. This makes it mathematically nearly impossible to hit the worst-case scenario.
* **Median-of-Three:** You look at the first, middle, and last elements of the array, and pick the middle value among those three. This is what professional libraries do because it avoids the overhead of a random number generator.

---

## So, which one is "Best"?

It depends entirely on your context:

| Context | The "Best" Approach | Why? |
| --- | --- | --- |
| **Coding Interviews / LeetCode** | **Lomuto + Random Pivot** | It is fast enough to pass the time limits, but simple enough that you won't introduce infinite loops or pointer bugs under pressure. |
| **Real-World Software Engineering** | **Java's `Arrays.sort()**` | You should never write your own QuickSort in production. Java uses a highly optimized **Dual-Pivot QuickSort** combined with Insertion Sort for small arrays. |
| **Arrays with many duplicates** | **3-Way Partitioning** | Completely eliminates the Time Limit Exceeded (TLE) risk that plagues standard QuickSort on repetitive data. |

**The Verdict for You Right Now:** Stick to **Lomuto's scheme**. As you saw when trying to implement the two-pointer approach, the edge cases in Hoare's scheme are painful. Lomuto is standard, clean, and reliable for passing algorithms tests.
---

Here is the complete and highly efficient **Dutch National Flag (3-Way Partitioning)** implementation of QuickSort.

This version is specifically designed to destroy arrays with a massive number of duplicate values, sorting them in record time.

```java
class Solution {
    public void quickSort(int[] arr, int low, int high) {
        if (low >= high) return;

        // 1. Choose the pivot (we use arr[low], but you can randomize it to be extra safe)
        int pivot = arr[low];
        
        // 2. Initialize our three pointers
        int lt = low;       // Boundary for elements LESS than pivot
        int i = low + 1;    // The scanning pointer
        int gt = high;      // Boundary for elements GREATER than pivot

        // 3. Scan the array
        while (i <= gt) {
            if (arr[i] < pivot) {
                // If current element is less than pivot, swap it to the 'less than' section
                swap(arr, i, lt);
                lt++;
                i++;
            } else if (arr[i] > pivot) {
                // If current element is greater, swap it to the 'greater than' section
                swap(arr, i, gt);
                gt--; 
                // Notice we DO NOT do i++ here, because the new arr[i] hasn't been checked yet
            } else {
                // If it's equal to the pivot, just move on
                i++;
            }
        }

        // 4. The magic: all elements from 'lt' to 'gt' are exactly equal to the pivot!
        // We only need to recursively sort the strictly smaller and strictly larger sections.
        quickSort(arr, low, lt - 1);
        quickSort(arr, gt + 1, high);
    }

    // A small helper method to keep the main logic clean
    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}

```

### How the Pointers Work

This algorithm divides the array into three distinct sections on the fly:

* **`lt` (Less Than):** Points to where the next number *smaller* than the pivot should go.
* **`gt` (Greater Than):** Points to where the next number *larger* than the pivot should go.
* **`i` (Index):** The active scanner checking elements one by one.

### Why this is the ultimate weapon against duplicates

In standard QuickSort, if you have an array like `[5, 5, 5, 5, 5, 5]`, the algorithm does a huge amount of unnecessary swapping and recursive calls, dragging the performance down to O(N²).

With 3-Way Partitioning, the `while` loop groups all the `5`s together in the center. When the loop finishes, `lt` and `gt` frame the duplicates. The algorithm sees that the left and right subarrays are empty, skips the recursion, and finishes the whole array in just O(N) time.

---


```java
class Solution {
    public void quickSort(int[] arr, int low, int high) {
        if (low >= high) return;
        
        int[] p = partition(arr, low, high);
        
        // Fix 1: Correctly pass the ranges for left and right subarrays
        quickSort(arr, low, p[0] - 1);   // Sort elements less than pivot
        quickSort(arr, p[1] + 1, high);  // Sort elements greater than pivot
    }

    private int[] partition(int[] arr, int low, int high) {
        int randomIndx = (int) (Math.random() * (high - low + 1)) + low;
        swap(arr, low, randomIndx);
        
        int pivot = arr[low];
        
        int lt = low;
        int i = low + 1;
        int gt = high;
        
        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, i, lt);
                lt++;
                i++;
            } else if (arr[i] > pivot) {
                swap(arr, i, gt);
                gt--;
            } else {
                i++;
            }
        }
        
        return new int[]{lt, gt};
    }
    
    public void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}

```