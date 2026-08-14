Here is how you can solve this problem **in-place** with $O(N \log N)$ time complexity and $O(1)$ extra space.

---

### Key Idea

1. **Custom Comparator / Sorting Logic:**
Instead of writing complex two-pointer swaps followed by separate sorts, you can sort the entire array using a single comparison rule:
* If both numbers are **even**, sort them in **ascending order** ($a < b$).
* If both numbers are **odd**, sort them in **ascending order** ($a < b$).
* If one is **even** and the other is **odd**, the **even number must come first**.


2. **Primitive Sorting in Java:**
Since primitive arrays (`int[]`) cannot use a custom `Comparator` directly in standard `Arrays.sort()`, we can:
* **Step 1:** Partition the array in-place so all even numbers are on the left and odd numbers on the right (similar to the quicksort partition step).
* **Step 2:** Sort the two subarrays (`arr[0...evenCount-1]` and `arr[evenCount...n-1]`) in-place using `Arrays.sort(arr, fromIndex, toIndex)`.



---

### Java Implementation

```java
import java.util.Arrays;

class Solution {
    void segregateEvenOdd(int arr[]) {
        int n = arr.length;
        
        // Step 1: Partition evens to the left and odds to the right in-place
        int left = 0, right = n - 1;
        
        while (left < right) {
            // Move left pointer until an odd number is found
            while (left < right && arr[left] % 2 == 0) {
                left++;
            }
            
            // Move right pointer until an even number is found
            while (left < right && arr[right] % 2 != 0) {
                right--;
            }
            
            // Swap the odd element on the left with the even element on the right
            if (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
        
        // Find the boundary index where odd numbers start
        int evenCount = 0;
        while (evenCount < n && arr[evenCount] % 2 == 0) {
            evenCount++;
        }
        
        // Step 2: Sort the even segment and odd segment separately in-place
        // Dual-Pivot Quicksort (Arrays.sort for primitives) runs in-place
        Arrays.sort(arr, 0, evenCount);
        Arrays.sort(arr, evenCount, n);
    }
}

```

---

### Complexity Analysis

* **Time Complexity:**
* **Partitioning:** $O(N)$ with two pointers.
* **Sorting:** $O(K \log K) + O((N - K) \log (N - K))$, where $K$ is the count of even numbers.
* **Total Time:** **$O(N \log N)$**, which comfortably passes for $N = 10^6$.


* **Space Complexity:** **$O(1)$ auxiliary space** (in-place Dual-Pivot Quicksort uses minimal call-stack overhead).

----

**Yes**, it is possible using **Counting Sort / Frequency Array**, taking advantage of the constraint:

> **$0 \le \text{arr}[i] \le 10^5$**

Because the range of numbers is small and fixed ($\le 100,000$), you can count the frequencies of all elements and then overwrite `arr` directly in **$O(N)$ time** without any sorting calls (`Arrays.sort`).

---

### $O(N)$ Frequency / Counting Approach

```java
class Solution {
    void segregateEvenOdd(int arr[]) {
        int maxVal = 100000;
        int[] count = new int[maxVal + 1];
        
        // Count frequency of each number
        for (int num : arr) {
            count[num]++;
        }
        
        int idx = 0;
        
        // Step 1: Place all even numbers in ascending order
        for (int i = 0; i <= maxVal; i += 2) {
            while (count[i] > 0) {
                arr[idx++] = i;
                count[i]--;
            }
        }
        
        // Step 2: Place all odd numbers in ascending order
        for (int i = 1; i <= maxVal; i += 2) {
            while (count[i] > 0) {
                arr[idx++] = i;
                count[i]--;
            }
        }
    }
}

```

---

### Custom In-Place Quicksort (Comparison-Based)

If you strictly want a comparison-based in-place sort without an extra count array, you can implement a custom **QuickSort** that incorporates the even/odd condition directly into the partition step:

```java
class Solution {
    void segregateEvenOdd(int arr[]) {
        customQuickSort(arr, 0, arr.length - 1);
    }
    
    private void customQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            customQuickSort(arr, low, p - 1);
            customQuickSort(arr, p + 1, high);
        }
    }
    
    // Returns true if a should come before b
    private boolean comesBefore(int a, int b) {
        boolean aEven = (a % 2 == 0);
        boolean bEven = (b % 2 == 0);
        
        if (aEven && !bEven) return true;  // Even comes before Odd
        if (!aEven && bEven) return false; // Odd comes after Even
        return a < b;                      // Same parity: sort ascending
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (comesBefore(arr[j], pivot)) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}

```

---

### Comparison

| Method | Time Complexity | Extra Space | Note |
| --- | --- | --- | --- |
| **Counting Sort** | **$O(N + \max(\text{arr}))$** | $O(\max(\text{arr}))$ | Fastest ($O(N)$), avoids all standard sorting functions. |
| **Custom QuickSort** | **$O(N \log N)$** | $O(1)$ | Pure in-place comparison sort using the parity rule. |

----


