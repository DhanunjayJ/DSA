To solve **Rearrange Array Alternately** in **$O(N)$ time** and **$O(1)$ extra space**, you need a clever trick.

Since the array elements are positive integers and you are not allowed to use an auxiliary array, you can **store two values at a single index** using modulo arithmetic.

### The Trick: Storing Two Values at One Index

We can use a max value $M$ that is strictly greater than any element in the array (e.g., `arr[n-1] + 1`).
Any number in the array can be encoded as:


$$\text{Encoded Value} = (\text{New Value} \times M) + \text{Old Value}$$

* To retrieve the **original value**, you do: `encoded % M`
* To retrieve the **newly placed value**, you do: `encoded / M`

### Java Implementation

```java
class Solution {
    public static void rearrange(long arr[], int n) {
        int minIndex = 0;
        int maxIndex = n - 1;
        
        // Choose a number greater than the maximum element in the array
        long maxElement = arr[n - 1] + 1;
        
        for (int i = 0; i < n; i++) {
            // Even index: put the maximum remaining element
            if (i % 2 == 0) {
                arr[i] += (arr[maxIndex] % maxElement) * maxElement;
                maxIndex--;
            } 
            // Odd index: put the minimum remaining element
            else {
                arr[i] += (arr[minIndex] % maxElement) * maxElement;
                minIndex++;
            }
        }
        
        // Decode the array to get the final updated values
        for (int i = 0; i < n; i++) {
            arr[i] = arr[i] / maxElement;
        }
    }
}

```

### Complexity Analysis

* **Time Complexity:** $O(N)$ because we traverse the array a constant number of times.
* **Space Complexity:** $O(1)$ extra space since everything is done in-place inside the original array.