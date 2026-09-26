class Solution {
    /**
     * Implements the Rabin-Karp string-matching algorithm to find the first 
     * occurrence of 'needle' inside 'haystack'.
     *
     * Core Idea:
     * Instead of comparing characters in O(m) time at every index, we treat substrings
     * as base-B polynomial numbers and compare their hashes in O(1) time.
     *
     * Complexity:
     * - Time: O(n + m) on average.
     * - Space: O(1) auxiliary memory.
     */
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        // Edge case: A needle longer than the haystack cannot be contained within it.
        if (m > n) return -1;
        if (m == 0) return 0;

        // Base multiplier (B): Represents the radix of the alphabet.
        // Chosen as 31 because lowercase English characters range from 0 to 25.
        long B = 31;

        // Modulo (MOD): A large prime to prevent 64-bit integer overflow 
        // while minimizing hash collisions (spurious hits).
        long MOD = 1_000_000_007L;

        long patternHash = 0;
        long windowHash = 0;

        /*
         * Formula 1: Horner's Polynomial Evaluation
         * Computes the initial hash for a string S[0...m-1]:
         *   Hash = (S[0]*B^(m-1) + S[1]*B^(m-2) + ... + S[m-1]*B^0) mod MOD
         *
         * Computed iteratively from left to right in O(m):
         *   currentHash = ((currentHash * B) + nextCharVal) mod MOD
         */
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * B + (needle.charAt(i) - 'a')) % MOD;
            windowHash = (windowHash * B + (haystack.charAt(i) - 'a')) % MOD;
        }

        // Early exit: First window [0 ... m-1] matches the needle.
        // Optional verification: if (patternHash == windowHash && haystack.startsWith(needle, 0))
        if (patternHash == windowHash) return 0;

        /*
         * Formula 2: High-Order Place Value (H)
         * H = B^(m - 1) mod MOD
         *
         * Represents the positional weight of the leftmost (outgoing) character 
         * in a window of length m. Precalculated once in O(m) so it can be reused in O(1).
         */
        long H = 1;
        for (int i = 0; i < m - 1; i++) {
            H = (H * B) % MOD;
        }

        int sp = 0; // Left pointer: index of the outgoing character

        /*
         * Sliding Window Loop:
         * Moves the window one character right on each iteration:
         *   - sp: index of the character leaving the left side
         *   - ep: index of the character entering the right side
         */
        for (int ep = m; ep < n; ep++) {
            int spVal = haystack.charAt(sp) - 'a';

            /*
             * Formula 3: Prefix Subtraction & Modulo Wrap-Around Fix
             *   removed = (windowHash - spVal * H) mod MOD
             *
             * Why ((... % MOD) + MOD) % MOD?
             * In Java, the '%' operator yields negative remainders for negative operands 
             * (e.g., -5 % 7 = -5). Adding MOD before taking the final modulo ensures 
             * the remainder is strictly non-negative in the range [0, MOD - 1].
             */
            long removed = ((windowHash - (spVal * H) % MOD) % MOD + MOD) % MOD;
            sp++; // Advance start pointer to match the start index of the new window

            /*
             * Formula 4: Left-Shift & Suffix Addition (Rolling Step)
             *   newHash = (removed * B + incomingVal) mod MOD
             *
             * 1. Multiplies by B to shift remaining characters left by one place value.
             * 2. Adds the incoming character at the units place (B^0).
             */
            long incoming = haystack.charAt(ep) - 'a';
            windowHash = (removed * B + incoming) % MOD;

            // When the rolled window hash equals the pattern hash, return the new window's start index.
            if (patternHash == windowHash) {
                // For 100% collision immunity, check: if (haystack.startsWith(needle, sp))
                return sp;
            }
        }

        return -1; // Needle not found anywhere in haystack
    }
}