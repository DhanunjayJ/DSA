class Solution {
    public int countK(int[] arr) {
        
        // ==========================================
        // STEP 1: Find the minimum element in the array
        // ==========================================
        // Intuition: If all elements leave the same remainder when divided by k,
        // the difference between *any* two elements must be divisible by k.
        // Instead of checking all O(N^2) pairs, we can pick the minimum element 
        // as a baseline. If k divides the difference between everyone and the minimum,
        // a chain reaction guarantees it divides the difference between any two people!
        int min = arr[0];
        for (int x : arr) {
            if (x < min) {
                min = x;
            }
        }
        
        // ==========================================
        // STEP 2: Compute the GCD of all differences
        // ==========================================
        // For k to work, k must divide evenly into every single difference (arr[i] - min).
        // This means k must be a "common divisor" of all these differences.
        // The Greatest Common Divisor (GCD) of a set of numbers represents the largest 
        // number that divides all of them. Any valid k MUST be a divisor of this GCD.
        int gcdVal = 0;
        for (int x : arr) {
            gcdVal = gcd(gcdVal, x - min);
        }
        
        // ==========================================
        // STEP 3: Handle the edge case (All elements are equal)
        // ==========================================
        // If all elements are equal, all differences are 0, making the GCD 0.
        // When GCD is 0, every positive integer k will leave a remainder of 0 
        // for all elements. This means there are infinitely many valid values of k.
        if (gcdVal == 0) {
            return -1;
        }
        
        // ==========================================
        // STEP 4: Count all positive divisors of the GCD
        // ==========================================
        // Since any valid k must be a divisor of our computed GCD, we simply need 
        // to count how many positive divisors `gcdVal` has.
        int count = 0;
        for (int i = 1; i * i <= gcdVal; i++) {
            if (gcdVal % i == 0) {
                count++; // 'i' is a divisor
                
                // If i is not the square root (e.g., for gcdVal = 4, i = 2), 
                // the paired divisor (gcdVal / i) is also a valid divisor.
                if (i * i != gcdVal) {
                    count++; 
                }
            }
        }
        
        return count;
    }
    
    // Helper method to find the Greatest Common Divisor using the Euclidean algorithm
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}