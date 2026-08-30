### The Standard Way to Write Selection Sort

Selection sort works by repeatedly dividing the array into a **sorted** and an **unsorted** region. In every step, it finds the minimum element from the unsorted region and swaps it with the first element of the unsorted region, expanding the sorted portion by one.

Here is the standard, clean implementation in **Java**:

```java
public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        
        // Move the boundary of the unsorted subarray one by one
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in the unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap the found minimum element with the first element
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}

```

* **Time Complexity:** $O(n^2)$ for all cases (Best, Average, Worst) because it always scans the remaining array to find the minimum.
* **Space Complexity:** $O(1)$ auxiliary space as it sorts in-place.

---

### A Problem Where Selection Sort Concept is Useful

While standard sorting problems usually favor more efficient algorithms like QuickSort or MergeSort ($O(n \log n)$), selection sort is uniquely suited for problems where **minimizing the total number of swaps** is critical, or when you only need to find the **$k$-th smallest elements** by running the algorithm $k$ times.

#### Problem: Minimum Swaps to Sort or Partial Sorting (e.g., Finding the Top $K$ elements with minimum write operations)

**Problem Statement:**
You are given an array of $n$ integers. Imagine you have a physical sorting process where moving/swapping elements is an expensive operation (in terms of cost or wear-and-tear), but comparisons are cheap. If you only need to find and place the **first $K$ smallest elements** into their correct sorted positions from left to right, how would you do it efficiently using the selection sort logic?

**Why Selection Sort fits here:**
Unlike Bubble Sort (which performs many swaps) or Insertion Sort (which shifts elements repeatedly), Selection Sort guarantees **at most $n-1$ swaps** in total for a full sort, and exactly **$K$ swaps** if you stop after finding the first $K$ minimum elements.

---
