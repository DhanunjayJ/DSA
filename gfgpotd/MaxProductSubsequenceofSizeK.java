    import java.util.Arrays;

class Solution {
    public int maxProduct(int[] arr, int k) {
        int n = arr.length;
        
        // Step 1: Sort the array. 
        // Note: Sorting does not ruin the subsequence property because multiplication 
        // is commutative (order doesn't change the product math). 
        // We sort to bring the extreme values (smallest negatives and largest positives) 
        // to the edges so we can easily evaluate them.
        Arrays.sort(arr);
        
        long prod = 1;
        int start = 0;
        int end = n - 1;
        
        // Step 2: Handle the all-negative edge case when k is odd.
        // If the largest element is <= 0 (meaning all elements are negative) and k is odd,
        // picking a single element and multiplying pairs from the left will make the 
        // result an unnecessarily large negative magnitude. 
        // The best strategy here is simply to take the last k elements (the least negative ones 
        // closest to zero) directly.
        if (arr[n - 1] <= 0 && k % 2 != 0) {
            for (int i = n - 1; i >= n - k; i--) {
                prod *= arr[i];
            }
            return (int) prod;
        }
        
        // Step 3: Handle general odd k.
        // Since our two-pointer pairing loop works by stepping 2 elements at a time (even steps),
        // an odd k needs to be adjusted. We anchor our product by taking the single 
        // largest available element from the right (`arr[end]`), which is safe and optimal,
        // then decrement k to make it even.
        if (k % 2 != 0) {
            prod = arr[end];
            end--;
            k--;
        }
        
        // Step 4: Greedy Two-Pointer approach from Extremes (Pairing strategy).
        // Now that k is even, we evaluate pairs of elements from both ends:
        // - `leftprod`: Multiplying two negative numbers from the left gives a large positive number 
        //   (Negative * Negative = Positive).
        // - `rightprod`: Multiplying two positive numbers from the right gives a large positive number 
        //   (Positive * Positive = Positive).
        while (k > 0) {
            // Cast to long to prevent potential integer overflow during multiplication
            long leftprod = (long) arr[start] * arr[start + 1];
            long rightprod = (long) arr[end] * arr[end - 1];
            
            // Greedily choose whichever pair yields a larger product, 
            // multiply it into our running product, and shift pointers accordingly.
            if (leftprod > rightprod) {
                prod *= leftprod;
                start += 2; // Consume 2 elements from the left
            } else {
                prod *= rightprod;
                end -= 2; // Consume 2 elements from the right
            }
            
            // Decrease k by 2 since we successfully picked a pair of elements
            k -= 2;
        }
        
        // Cast back to int and return the maximum product obtained
        return (int) prod;
    }
}