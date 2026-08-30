### What is a Prefix Sum?

A **prefix sum** is an array technique used to efficiently calculate the sum of elements in a given range (subarray) of an array in **$O(1)$ time complexity**, after an initial $O(N)$ preprocessing step.

Instead of looping through elements from a starting point (`sp`) to an ending point (`ep`) every time you need a range sum (which takes $O(N)$ per query), you precompute a cumulative sum array.

---

### How to Build the Prefix Sum Array

Given an original array $A$ of size $N$, we create a prefix sum array $P$ of the same size (or size $N+1$ for easier indexing). Each element at index $i$ in $P$ stores the sum of all elements in $A$ from index $0$ up to $i$.

**Formula:**


$$P[i] = P[i-1] + A[i]$$


*(with $P[0] = A[0]$)*

#### Example:

* **Original Array ($A$):** `[2, 4, 1, 3, 5]`
* **Prefix Sum Array ($P$):** `[2, 6, 7, 10, 15]`
* $P[0] = 2$
* $P[1] = 2 + 4 = 6$
* $P[2] = 6 + 1 = 7$
* $P[3] = 7 + 3 = 10$
* $P[4] = 10 + 5 = 15$



---

### How to Get the Sum for Range (`sp` to `ep`)

Once you have the prefix sum array $P$, you can find the sum of elements from start index `sp` to end index `ep` using this formula:

$$\text{RangeSum}(sp, ep) = P[ep] - P[sp - 1]$$

*(Note: If `sp` is $0$, the formula simplifies to just $P[ep]$, because there are no elements before index $0$.)*

#### Walkthrough Example:

Using our arrays above, let's find the sum of elements from index **`sp = 1`** to **`ep = 3`** (the subarray `[4, 1, 3]`, which should sum to $8$).

1. Look up $P[ep]$ ($P[3]$): **$10$**
2. Look up $P[sp - 1]$ ($P[0]$): **$2$**
3. Subtract: $10 - 2 =$ **$8$**

---

### Quick Implementation (Java)

```java
class PrefixSumExample {
    public static void main(String[] args) {
        int[] A = {2, 4, 1, 3, 5};
        int n = A.length;
        
        // 1. Build prefix sum array
        int[] P = new int[n];
        P[0] = A[0];
        for (int i = 1; i < n; i++) {
            P[i] = P[i - 1] + A[i];
        }
        
        // 2. Query range from sp = 1 to ep = 3
        int sp = 1, ep = 3;
        int rangeSum;
        if (sp == 0) {
            rangeSum = P[ep];
        } else {
            rangeSum = P[ep] - P[sp - 1];
        }
        
        System.out.println("Sum from index " + sp + " to " + ep + " is: " + rangeSum); // Output: 8
    }
}

```

### Complexity

* **Preprocessing Time:** $O(N)$ to build the prefix array once.
* **Query Time:** $O(1)$ for every subsequent range sum request.
* **Space Complexity:** $O(N)$ to store the prefix sum array (or $O(1)$ if you overwrite the original array).

Would you like to see how this concept extends to 2D matrices (subgrid sum queries)?