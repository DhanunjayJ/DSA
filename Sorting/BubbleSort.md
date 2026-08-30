That code snippet implements **Bubble Sort**, not Selection Sort! You've correctly captured the classic logic where adjacent elements are compared and the largest elements "bubble up" to the end of the array with each pass.

Here is a breakdown of how your specific code works:

### How Your Bubble Sort Implementation Works

1. **The Outer Loop (`for(int i=0; i<n-1; i++)`)**
* Controls the number of passes through the array.
* As `i` increases, the boundary shrinks because the largest elements have already bubbled up and settled into their correct positions at the far right.


2. **The Inner Loop (`for(int j=0; j<n-i-1; j++)`)**
* Iterates through the unsorted portion of the array from left to right.
* It stops at `n-i-1` because the last `i` elements are already sorted (the bubbled elements).


3. **The Comparison & Swap (`if(arr[j] > arr[j+1])`)**
* Compares adjacent elements side-by-side.
* If the left element is greater than the right element, they swap places using a temporary variable (`temp`), pushing the larger value toward the right.



---

### Key Characteristics of This Code

* **Time Complexity:** $O(n^2)$ for the worst and average cases (when the array is in reverse order or random), because the nested loops always run roughly $\frac{n^2}{2}$ times.
* **Space Complexity:** $O(1)$ auxiliary space since it sorts the array entirely in-place.
* **Optimization Note:** While your code works great, a standard optimization for Bubble Sort is adding a boolean `swapped` flag inside the outer loop. If an inner loop finishes without making a single swap, it means the array is already fully sorted, and you can break early (dropping the best-case time complexity to $O(n)$).

---

Here is how you can add the early-exit optimization to your Bubble Sort code using a boolean flag.

### Optimized Bubble Sort Implementation

```java
class Solution {
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        
        for(int i = 0; i < n - 1; i++) {
            swapped = false; // Reset the flag for each new pass
            
            for(int j = 0; j < n - i - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    
                    swapped = true; // A swap occurred
                }
            }
            
            // If no elements were swapped during this pass, the array is already sorted
            if(!swapped) {
                break;
            }
        }
    }
}

```

### Why this helps:

* **Best-Case Time Complexity ($O(n)$):** If you pass an array that is **already sorted**, the inner loop will run once, find that `swapped` remains `false`, and immediately break out of the loop. Your original code would still stubbornly run both loops all the way through ($O(n^2)$ time) even if the array didn't need any changes.

