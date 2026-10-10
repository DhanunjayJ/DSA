To understand the Binary Search approach, let's step away from the code and picture the problem visually.

---

### 1. The Core Idea: Cutting Towers with a Ceiling

Suppose our absolute differences are:


$$\text{diffs} = [10, 8, 8, 3, 1]$$


And we have a total operation budget $k = 7$.

Imagine each difference as a vertical pillar of blocks:

```text
10 | █
 9 | █
 8 | █  █  █
 7 | █  █  █
...| █  █  █  █
 1 | █  █  █  █  █
---+---------------
     A  B  C  D  E

```

Every time you spend $1$ unit of $k$, you shave $1$ block off the tallest pillar.

Now, imagine holding a **horizontal blade** at height $T$ (a ceiling cap) and slicing off all blocks that stick out above $T$:

* If you set $T = 7$:
* Column $A$ ($10$) loses $10 - 7 = 3$ blocks.
* Columns $B$ and $C$ ($8$) each lose $8 - 7 = 1$ block.
* Columns $D$ ($3$) and $E$ ($1$) are already below $7$, so they lose $0$ blocks.
* **Total cost** $= 3 + 1 + 1 + 0 + 0 = 5$ operations.



Since your budget is $k = 7$ and $5 \le 7$, setting a ceiling of $T = 7$ is **achievable**.

Can we push the blade lower to $T = 6$?

* Column $A$: loses $10 - 6 = 4$
* Columns $B, C$: each lose $8 - 6 = 2$
* **Total cost** $= 4 + 2 + 2 = 8$ operations.
* But $8 > 7$ (exceeds budget $k$), so $T = 6$ is **impossible** to achieve completely.

Therefore, the lowest flat ceiling you can afford to cut everything down to is **$T = 7$**.

---

### 2. Why Does Binary Search Work?

Notice the monotonic relationship:

| Ceiling Height ($T$) | Total Blocks Sliced Off | Afford with $k = 7$? |
| --- | --- | --- |
| $10$ (highest diff) | $0$ | Yes |
| $9$ | $1$ | Yes |
| $8$ | $2$ | Yes |
| **$7$** | **$5$** | **Yes (Smallest valid $T$)** |
| $6$ | $8$ | No (over budget) |
| $5$ | $12$ | No |
| $0$ | $30$ | No |

* Higher $T$ $\rightarrow$ fewer blocks cut $\rightarrow$ cheaper cost.
* Lower $T$ $\rightarrow$ more blocks cut $\rightarrow$ higher cost.

Because the cost changes strictly in one direction, you don't have to test every height $10, 9, 8, 7\dots$ one by one. You can **binary search** for the optimal ceiling $T$ between $0$ and $\max(d)$.

---

### 3. The Helper Function: `canAchieve(T, k)`

For any candidate ceiling $T$, how many operations does it take?

```java
long opsNeeded = 0;
for (int d : diff) {
    if (d > T) {
        opsNeeded += (d - T);
    }
}
return opsNeeded <= k;

```

If `opsNeeded <= k`, the ceiling $T$ is achievable! We try to see if an even lower ceiling is possible by searching in the lower half.

---

### 4. Handling the Leftover $k$

Once binary search finds the optimal ceiling $T$, we have spent some operations, but we might have a few leftover operations:

$$\text{leftover\_k} = k - \text{cost}(T)$$

In our example ($k = 7$, optimal $T = 7$):

* Cost to bring everything above $7$ down to $7$ was $5$.
* Leftover operations $= 7 - 5 = 2$.

After cutting down to $T = 7$, our array is:


$$[7, 7, 7, 3, 1]$$

We still have $2$ operations remaining! Where should we spend them?
Naturally, on the current largest values (which are the ones equal to $T = 7$).

* Decrement two of the $7$'s to $6$.
* The array becomes: $[6, 6, 7, 3, 1]$.
* Leftover operations are now $0$.

Finally, square every number and sum them up:


$$6^2 + 6^2 + 7^2 + 3^2 + 1^2 = 36 + 36 + 49 + 9 + 1 = 131$$

---

### Complete Code

```java
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        int maxDiff = 0;
        long totalDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            totalDiff += diff[i];
        }

        long k = (long) k1 + k2;

        // If we can reduce all differences to 0
        if (totalDiff <= k) {
            return 0;
        }

        // Binary search for the smallest achievable ceiling T
        int low = 0, high = maxDiff;
        int bestT = maxDiff;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (getCost(diff, mid) <= k) {
                bestT = mid;       // Achievable, try to go lower
                high = mid - 1;
            } else {
                low = mid + 1;     // Too expensive, must raise the ceiling
            }
        }

        // Apply the ceiling bestT and deduce cost from k
        long spent = 0;
        for (int i = 0; i < n; i++) {
            if (diff[i] > bestT) {
                spent += (diff[i] - bestT);
                diff[i] = bestT;
            }
        }

        long leftoverK = k - spent;

        // Distribute leftover k to decrement elements that are at bestT down to bestT - 1
        for (int i = 0; i < n && leftoverK > 0; i++) {
            if (diff[i] == bestT && diff[i] > 0) {
                diff[i]--;
                leftoverK--;
            }
        }

        // Compute total squared sum
        long ans = 0;
        for (int d : diff) {
            ans += (long) d * d;
        }

        return ans;
    }

    private long getCost(int[] diff, int T) {
        long cost = 0;
        for (int d : diff) {
            if (d > T) {
                cost += (d - T);
            }
        }
        return cost;
    }
}

```



### The Intuition: Staircase Leveling

Instead of guessing a ceiling with binary search, the **Sort + Water-Level Lowering** approach sorts the differences in descending order and lowers the tallest elements step-by-step like a plateau.

Consider the sorted differences:


$$\text{diff} = [10, 8, 8, 3, 1], \quad k = 11$$

Visually, think of the sorted values as a descending staircase:

```text
Height
  10 | [0]
   9 | [0]
   8 | [0]  [1]  [2]
   7 | [0]  [1]  [2]
 ... | ...
   3 | [0]  [1]  [2]  [3]
   2 | ...
   1 | [0]  [1]  [2]  [3]  [4]
-----+------------------------
 Idx:  0     1    2    3    4

```

Notice the pattern:

* At index `0`, there is $1$ column at height $10$.
* The next distinct height is $8$ (at index `1` and `2`).
* To bring column `0` down to height $8$, you must shave $10 - 8 = 2$ units.
* Once column `0` drops to $8$, you have a **flat plateau** of $3$ columns (`idx 0, 1, 2`) all sitting at height $8$.
* To lower this entire plateau further, every unit drop costs $3$ operations because all $3$ columns must be decremented together to keep them balanced.

---

### Step-by-Step Walkthrough ($k = 11$)

#### Step 1: Drop Index 0 to Match Index 1

* Current height of peak: $10$
* Target height (value of next element): $8$
* Height difference $\Delta h = 10 - 8 = 2$
* Number of columns at this peak width: $w = 1$ (just column 0)
* Cost: $w \times \Delta h = 1 \times 2 = 2$

Since $k = 11 \ge 2$, we pay $2$ operations.

* $k_{\text{remaining}} = 11 - 2 = 9$
* Array state: $[8, 8, 8, 3, 1]$

```text
Height
   8 | [0]  [1]  [2]  <-- Flat plateau of width 3
 ... | ...
   3 | [0]  [1]  [2]  [3]
-----+-------------------
 Idx:  0     1    2    3    4

```

---

#### Step 2: Drop Plateau [0..2] to Match Index 3

* Current peak height: $8$
* Target height (value at index 3): $3$
* Height difference $\Delta h = 8 - 3 = 5$
* Width of plateau: $w = 3$ (columns 0, 1, and 2)
* Total cost to drop all 3 to height $3$: $w \times \Delta h = 3 \times 5 = 15$

Can we afford this?

* Budget is $k = 9$, but cost is $15$. **We cannot reach height 3.**

---

#### Step 3: Divide the Remaining Budget Across the Plateau

Since we cannot reach height $3$, we exhaust our remaining $k = 9$ evenly across the plateau of width $w = 3$:

1. **Even drop:**

$$\text{drop} = \lfloor k / w \rfloor = \lfloor 9 / 3 \rfloor = 3$$



Every element in the plateau drops from $8$ by $3 \rightarrow 8 - 3 = 5$.
2. **Remainder:**

$$\text{rem} = k \pmod w = 9 \pmod 3 = 0$$



No extra $-1$ decrements needed.

* Array becomes: $[5, 5, 5, 3, 1]$
* Budget $k$ is now $0$. Stop immediately.

---

### Calculating the Final Answer

$$\text{Sum of Squares} = 5^2 + 5^2 + 5^2 + 3^2 + 1^2 = 25 + 25 + 25 + 9 + 1 = 85$$

---

### Java Implementation

```java
import java.util.Arrays;

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        long totalDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            totalDiff += diff[i];
        }

        long k = (long) k1 + k2;

        // If we can reduce all differences completely to 0
        if (totalDiff <= k) {
            return 0;
        }

        // Sort ascending: smallest -> largest
        Arrays.sort(diff);

        // Append a virtual floor of height 0 at the bottom (diff[-1] equivalent)
        // We iterate backwards from largest to smallest.
        for (int i = n - 1; i >= 0; i--) {
            int currentHeight = diff[i];
            int nextHeight = (i > 0) ? diff[i - 1] : 0;
            
            // If adjacent heights are the same, expand the width without spending k
            if (currentHeight == nextHeight) {
                continue;
            }

            int width = n - i; // Number of elements at or above currentHeight
            long heightDiff = currentHeight - nextHeight;
            long cost = width * heightDiff;

            if (k >= cost) {
                k -= cost;
                // All elements from index i to n-1 now drop to nextHeight
                // (Loop continues to consider lowering them further with next elements)
            } else {
                // Cannot bring everyone down to nextHeight
                long drop = k / width;
                long rem = k % width;

                long finalHigh = currentHeight - drop;
                long finalLow = finalHigh - 1;

                // 'rem' elements get lowered to finalLow, 
                // the remaining '(width - rem)' stay at finalHigh
                long ans = 0;
                // Elements below the plateau untouched:
                for (int j = 0; j < i; j++) {
                    ans += (long) diff[j] * diff[j];
                }
                // Plateau elements:
                ans += rem * (finalLow * finalLow);
                ans += (width - rem) * (finalHigh * finalHigh);

                return ans;
            }
        }

        return 0;
    }
}

```

### Complexity

* **Time Complexity:** $O(n \log n)$ due to sorting the differences. The leveling loop runs in $O(n)$ time.
* **Space Complexity:** $O(n)$ to store differences (or $O(1)$ auxiliary if modifying `nums1` in place).