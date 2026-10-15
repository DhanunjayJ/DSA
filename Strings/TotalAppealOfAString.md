Here is the complete, start-to-finish breakdown of **LeetCode 2262 (Total Appeal of a String)**, taking you from the core intuition all the way to the final optimized code.

---

## 1. The Core Intuition: What is "Appeal"?

The appeal of a string is the **number of unique characters** it contains. For example, `"abca"` has 3 unique characters (`a`, `b`, `c`), so its appeal is 3.

If we want to find the **total appeal** across *every single possible substring* of a word, writing a brute-force nested loop would take $O(n^2)$ or $O(n^3)$ time, which is too slow for $n = 10^5$. We need a smart way to track unique counts as we expand our string one letter at a time from left to right (a **sweep-line** approach).

---

## 2. Grouping Substrings by Where They End

Instead of looking at substrings randomly, let's group them by **their right endpoint (`r`)**.

Imagine you are building a word letter by letter. As you add a new character at index `r`, you look at all substrings ending at `r`.

* Some of those substrings **already contain** this new character from an earlier position.
* Other substrings **do not contain** it yet because they started too late.

### The "Wall" Metaphor (`lastSeen`)

Whenever a character `c` appears at index `r`, its **previous occurrence** (stored in `lastSeen[c]`) acts like a dividing wall:

1. **Substrings starting at or to the left of the wall ($l \le \text{lastSeen}[c]$):** These already crossed paths with the old `c`. Adding a new `c` is just a duplicate for them, so their unique count increases by **`+0`**.
2. **Substrings starting strictly to the right of the wall ($l > \text{lastSeen}[c]$):** These started after the old `c` appeared, meaning they **missed it**. For all of them, this new character is a brand-new unique discovery! Their unique counts all increase by **`+1`**.

---

## 3. The $O(1)$ Math Trick

Instead of updating individual scoreboard slots in an array one by one, we can calculate the total impact in $O(1)$ time:

* How many substrings get a `+1` boost?
The number of valid starting positions is from `lastSeen[c] + 1` up to `r`.
In math, the total count of elements in that inclusive range is:

$$\text{Count} = r - \text{lastSeen}[c]$$


* **The Magic of `-1`:** If a character has **never** appeared before, we initialize its `lastSeen` value to **`-1`**.
When we plug `-1` into our formula: $r - (-1) = r + 1$.
This means *every single* substring from index `0` to `r` gets `+1`. The formula automatically handles first-time appearances and repeat appearances with zero `if/else` checks!

At every step `r`:

1. We add `r - lastSeen[c]` to our running `currSum` (the sum of unique counts for all substrings ending at `r`).
2. We add `currSum` to our `totalSum`.
3. We update `lastSeen[c] = r`.

---

## 4. Complete Commented Java Code

```java
import java.util.Arrays;

class Solution {
    public long appealSum(String s) {
        long totalSum = 0;
        long currSum = 0;
        
        // Track the last seen index for each of the 26 lowercase English letters.
        // Initialized to -1 meaning "has not appeared yet".
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        
        // Sweep-line: expand the right endpoint r from 0 to n-1
        for (int r = 0; r < s.length(); r++) {
            int c = s.charAt(r) - 'a';
            
            // How many substrings ending at r get a +1 boost?
            // If c is new (lastSeen == -1), r - (-1) equals r + 1 (all substrings get +1).
            // If c was seen before, r - lastSeen[c] gives the exact count of substrings 
            // starting strictly after the previous occurrence.
            currSum += (r - lastSeen[c]);
            
            // Accumulate the current sum of all substrings ending at r into the grand total
            totalSum += currSum;
            
            // Update the last seen position of character c to the current index
            lastSeen[c] = r;
        }
        
        return totalSum;
    }
}

```