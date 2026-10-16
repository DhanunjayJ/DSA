**Yes, in this specific problem (LeetCode 2916), the value of `v` passed to `update` will always be `1`.**

Here is why:
Whenever you encounter an element `nums[r] = x`, it appears for the first time in all subarrays starting from index `prev + 1` up to `r`. By definition of distinct counts, adding a new element at the end either increases the distinct count of those subarrays by exactly **`+1`** or leaves them unchanged. Because we restrict our range update to `[prev + 1, r]`, every single subarray starting in that range is guaranteed to gain exactly **`1`** new distinct element.

### Why is `v` a parameter then?

Writing `update(..., long v)` instead of hardcoding `1` makes your Segment Tree template **reusable** for other problems. For instance:

* If a problem asked you to add `+5` to a range, you could call `update(..., 5)`.
* If a problem asked you to subtract or assign values, having `v` as a parameter keeps your `apply()` and `update()` generic.

Since LeetCode 2916 only ever adds `+1` to the range `[prev + 1, r]`, every call in `sumCounts` passes `1` as the last argument (`update(1, 0, n - 1, prev + 1, r, 1)`).



----


**Yes, in this specific problem (LeetCode 2916), the value of `v` passed to `update` will always be `1`.**

Here is why:
Whenever you encounter an element `nums[r] = x`, it appears for the first time in all subarrays starting from index `prev + 1` up to `r`. By definition of distinct counts, adding a new element at the end either increases the distinct count of those subarrays by exactly **`+1`** or leaves them unchanged. Because we restrict our range update to `[prev + 1, r]`, every single subarray starting in that range is guaranteed to gain exactly **`1`** new distinct element.

### Why is `v` a parameter then?

Writing `update(..., long v)` instead of hardcoding `1` makes your Segment Tree template **reusable** for other problems. For instance:

* If a problem asked you to add `+5` to a range, you could call `update(..., 5)`.
* If a problem asked you to subtract or assign values, having `v` as a parameter keeps your `apply()` and `update()` generic.

Since LeetCode 2916 only ever adds `+1` to the range `[prev + 1, r]`, every call in `sumCounts` passes `1` as the last argument (`update(1, 0, n - 1, prev + 1, r, 1)`).


---

That is a fantastic question. At first glance, it feels like checking `if (ql <= mid)` and `if (qr > mid)` should be an `if-else` block.

The reason it is **two separate `if` statements** (and never `if-else`) comes down to how a query or update range overlaps with the tree's left and right children.

---

### The Crucial Difference: Point/Single vs. Range

* **If you were searching for a single index** (like `ql == qr`, e.g., "update index 5"), the target index could **only ever be on one side**—either in the left child (`mid <= 5`) or the right child (`5 > mid`). In that case, an `if-else` would be correct.
* **But here, `[ql, qr]` is a RANGE.**

An update range like `[1, 5]` can easily span across the midpoint. For example, if the current node covers `[0, 10]` and `mid = 5`:

* Left child covers `[0, 5]`
* Right child covers `[6, 10]`

Does `[1, 5]` overlap with the left child `[0, 5]`? **Yes (`1 <= 5`)**.
Does `[1, 5]` overlap with the right child `[6, 10]`? **Wait!** In this specific case, 5 is less than 6, so it doesn't overlap the right child.

But what if our update range was **`[1, 8]`**?

* Does `[1, 8]` overlap the left child `[0, 5]`? **Yes (`1 <= 5`)**, so we must update the left child.
* Does `[1, 8]` overlap the right child `[6, 10]`? **Also Yes (`8 > 5`)**, so we must *also* update the right child!

---

### What would happen if you used `if-else`?

If you wrote `if (...) else if (...)`, you would **only ever visit one child**.

* If a range `[1, 8]` overlapped both sides, your code would completely ignore the right side (`[6, 10]`), meaning elements inside `[6, 8]` would **never get their `+1` update!** Your segment tree would have missing updates, and your final answer would be completely wrong.

### Summary

* **Separate `if` statements** allow the recursion to **branch out** and visit *both* children when the update range `[ql, qr]` overlaps both the left and right halves of the current node.
* An `if-else` assumes the range can only go one way, which only works for single-index point updates, not range updates.

---

To optimize from $O(n^2)$ to $O(n \log n)$, you need to combine a **sweep-line approach** (iterating over the right endpoint $r$) with a **Lazy Propagation Segment Tree** that maintains both the sum of distinct counts and the sum of squares of distinct counts across all valid left endpoints $l \le r$.

---

## 1. The Core Observation: How Distinct Counts Change

Suppose we iterate the right endpoint $r$ from $0$ to $n - 1$. At each step $r$, consider an array $D$ of size $n$, where $D[l]$ represents the number of distinct elements in the subarray `nums[l..r]` for $0 \le l \le r$.

When we advance from $r - 1$ to $r$, we introduce a new element $x = \text{nums}[r]$. How does $x$ affect $D[l]$ for each starting index $l \in [0, r]$?

* Let `prev` be the last index where $x$ appeared before $r$ (or $-1$ if this is the first time we see $x$).
* For any subarray starting at $l \le \text{prev}$, the value $x$ is **already present** in `nums[l..r-1]`. Appending $x$ at index $r$ does not change its distinct count $D[l]$.
* For any subarray starting at $\text{prev} + 1 \le l \le r$, the value $x$ appears **for the first time**. Thus, $D[l]$ increases by $1$ for every $l$ in the range $[\text{prev} + 1, r]$.

At the end of step $r$, the sum of squares of distinct counts for all subarrays ending at $r$ is simply:


$$\sum_{l=0}^{r} (D[l])^2$$


If we can perform this range addition and query the sum of squares in $O(\log n)$ time at each step $r$, we can accumulate the total answer across all $r$ in $O(n \log n)$ time.

---

## 2. Maintaining Sum of Squares Under Range Addition

A standard segment tree easily maintains a range sum under range addition. To maintain a **range sum of squares** when adding a constant $v$ to every element in a segment $[L, R]$ of length $\text{len} = R - L + 1$, expand the algebra for each element $D[i]$ in that segment:

$$(D[i] + v)^2 = D[i]^2 + 2v \cdot D[i] + v^2$$

Summing this over all $i \in [L, R]$ gives the transition rules for a segment tree node:

$$\text{new\_sum2} = \sum_{i=L}^{R} (D[i] + v)^2 = \underbrace{\sum_{i=L}^{R} D[i]^2}_{\text{old\_sum2}} + 2v \underbrace{\sum_{i=L}^{R} D[i]}_{\text{old\_sum1}} + \text{len} \cdot v^2$$

$$\text{new\_sum1} = \sum_{i=L}^{R} (D[i] + v) = \text{old\_sum1} + \text{len} \cdot v$$

Because updating `sum2` requires knowing the current `sum1` (the linear sum of $D[i]$), each node in our Segment Tree must store three values:

1. `sum1`: $\sum D[i]$ in the node's interval
2. `sum2`: $\sum D[i]^2$ in the node's interval
3. `lazy`: pending increment $v$ to be pushed down to the node's children

> **Crucial Order of Operations:** Always update `sum2` **before** `sum1` (since the formula for `new_sum2` depends on `old_sum1`), or store `old_sum1` in a temporary variable.

---

## 3. Step-by-Step Walkthrough (`nums = [1, 2, 1]`)

Initially, all $D[l] = 0$, and `last_seen` is empty.

1. **$r = 0$, `nums[0] = 1`:**
* `prev = -1`. Range to increment by $+1$: $[0, 0]$.
* $D$ becomes $[1, 0, 0]$.
* Total $\sum D[l]^2 = 1^2 = 1$. Add $1$ to `ans` (`ans = 1`).
* Update `last_seen[1] = 0`.


2. **$r = 1$, `nums[1] = 2`:**
* `prev = -1`. Range to increment by $+1$: $[0, 1]$.
* $D$ becomes $[2, 1, 0]$.
* Total $\sum D[l]^2 = 2^2 + 1^2 = 5$. Add $5$ to `ans` (`ans = 6`).
* Update `last_seen[2] = 1`.


3. **$r = 2$, `nums[2] = 1`:**
* `prev = last_seen[1] = 0`. Range to increment by $+1$: $[0 + 1, 2] = [1, 2]$.
* $D$ becomes $[2, 2, 1]$.
* Total $\sum D[l]^2 = 2^2 + 2^2 + 1^2 = 9$. Add $9$ to `ans` (`ans = 15`).
* Update `last_seen[1] = 2`.



---

## 4. Implementation ($O(n \log n)$ Time, $O(n)$ Space)

Since we always query the sum of squares over the entire array $[0, n-1]$ (indices $> r$ are still $0$ and contribute $0^2 = 0$), we don't even need a separate range query function—we can read `sum2[1]` directly from the root of the segment tree after each update.

Both the **Lazy Segment Tree** approach and the **Fenwick Tree (BIT)** approach in Java are provided below. Since `nums[i] <= 10^5`, both solutions use a primitive `int[]` array instead of a `HashMap` to track `lastSeen`, avoiding boxing overhead and cutting runtime significantly.

---

## 1. Lazy Propagation Segment Tree (Standard Approach)

* **Time Complexity:** $O(n \log n)$
* **Space Complexity:** $O(n)$

```java
import java.util.Arrays;

class Solution {
    private static final long MOD = 1_000_000_007L;
    private long[] sum1;
    private long[] sum2;
    private long[] lazy;

    private void apply(int node, int l, int r, long v) {
        long len = r - l + 1;
        // Update sum2 BEFORE sum1 because new_sum2 depends on old_sum1
        sum2[node] = (sum2[node] + 2L * v % MOD * sum1[node] + len * (v * v % MOD)) % MOD;
        sum1[node] = (sum1[node] + len * v) % MOD;
        lazy[node] = (lazy[node] + v) % MOD;
    }

    private void push(int node, int l, int r) {
        if (lazy[node] != 0) {
            int mid = l + (r - l) / 2;
            apply(node * 2, l, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, r, lazy[node]);
            lazy[node] = 0;
        }
    }

    private void update(int node, int l, int r, int ql, int qr, long v) {
        if (ql <= l && r <= qr) {
            apply(node, l, r, v);
            return;
        }
        push(node, l, r);
        int mid = l + (r - l) / 2;
        if (ql <= mid) {
            update(node * 2, l, mid, ql, qr, v);
        }
        if (qr > mid) {
            update(node * 2 + 1, mid + 1, r, ql, qr, v);
        }
        sum1[node] = (sum1[node * 2] + sum1[node * 2 + 1]) % MOD;
        sum2[node] = (sum2[node * 2] + sum2[node * 2 + 1]) % MOD;
    }

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum1 = new long[4 * n];
        sum2 = new long[4 * n];
        lazy = new long[4 * n];

        // Since 1 <= nums[i] <= 10^5, use an array instead of HashMap for speed
        int maxVal = 0;
        for (int x : nums) {
            maxVal = Math.max(maxVal, x);
        }
        int[] lastSeen = new int[maxVal + 1];
        Arrays.fill(lastSeen, -1);

        long ans = 0;
        for (int r = 0; r < n; r++) {
            int x = nums[r];
            int prev = lastSeen[x];

            // Increment distinct count by 1 for all subarrays starting in [prev + 1, r]
            update(1, 0, n - 1, prev + 1, r, 1);
            lastSeen[x] = r;

            // Root (node 1) holds the sum of squares for all l in [0, r]
            ans = (ans + sum2[1]) % MOD;
        }

        return (int) ans;
    }
}

```

---

## 2. Fenwick Tree / BIT Approach (Faster & Shorter)

In Java, a 4$n$ recursive Segment Tree can have noticeable constant-factor overhead. Using the $\Delta \text{sum2} = 2 \sum_{l=\text{prev}+1}^{r} D[l] + (r - \text{prev})$ identity, we only need a **Range-Update Range-Query Fenwick Tree**, which runs ~3x faster on LeetCode.

```java
import java.util.Arrays;

class Solution {
    private static final long MOD = 1_000_000_007L;
    private int n;
    private long[] tree1;
    private long[] tree2;

    // Standard BIT point update
    private void add(long[] tree, int idx, long val) {
        for (; idx <= n; idx += idx & -idx) {
            tree[idx] = (tree[idx] + val) % MOD;
        }
    }

    // Add 'val' to range [l, r] (1-indexed)
    private void rangeAdd(int l, int r, long val) {
        add(tree1, l, val);
        add(tree1, r + 1, MOD - val);
        add(tree2, l, val * (l - 1) % MOD);
        add(tree2, r + 1, (MOD - val * r % MOD) % MOD);
    }

    // Prefix sum [1..idx]
    private long prefixSum(int idx) {
        long s1 = 0, s2 = 0;
        for (int i = idx; i > 0; i -= i & -i) {
            s1 = (s1 + tree1[i]) % MOD;
            s2 = (s2 + tree2[i]) % MOD;
        }
        return (s1 * idx - s2 + MOD) % MOD;
    }

    // Range sum [l..r] (1-indexed)
    private long rangeQuery(int l, int r) {
        return (prefixSum(r) - prefixSum(l - 1) + MOD) % MOD;
    }

    public int sumCounts(int[] nums) {
        this.n = nums.length;
        this.tree1 = new long[n + 2];
        this.tree2 = new long[n + 2];

        int maxVal = 0;
        for (int x : nums) {
            maxVal = Math.max(maxVal, x);
        }
        int[] lastSeen = new int[maxVal + 1];
        Arrays.fill(lastSeen, -1);

        long totalAns = 0;
        long curSum2 = 0; // Tracks sum of D[l]^2 for the current right endpoint r

        for (int r = 0; r < n; r++) {
            int x = nums[r];
            // Convert 0-based indices [prev + 1, r] to 1-based [L, R] for BIT
            int L = lastSeen[x] + 2;
            int R = r + 1;

            // Query current sum of D[l] in [L, R] before incrementing
            long rangeSum = rangeQuery(L, R);
            long len = R - L + 1;

            // Update running sum of squares: sum((D[l] + 1)^2) - sum(D[l]^2) = 2 * sum(D[l]) + len
            curSum2 = (curSum2 + 2 * rangeSum + len) % MOD;
            totalAns = (totalAns + curSum2) % MOD;

            // Increment D[l] by 1 in [L, R]
            rangeAdd(L, R, 1);
            lastSeen[x] = r;
        }

        return (int) totalAns;
    }
}

```
---

## 5. Alternative $O(n \log n)$ Approach: Fenwick Tree (BIT) Trick

If you dislike writing a full 4$n$ Segment Tree during a contest, you can notice that at step $r$, we only ever add $+1$ to the range $[\text{prev} + 1, r]$.
When every $D[l]$ in $[\text{prev} + 1, r]$ increases by $1$:


$$(D[l] + 1)^2 - D[l]^2 = 2 \cdot D[l] + 1$$


This means the **change** in the sum of squares at step $r$ (compared to step $r - 1$) is simply:


$$\Delta \text{sum2} = 2 \sum_{l = \text{prev} + 1}^{r} D[l] + (r - \text{prev})$$


Because this formula only requires range addition ($+1$) and range sum queries on $D[l]$ (no squares inside the tree!), you can maintain $D$ using a standard **Range-Update Range-Query Fenwick Tree (Binary Indexed Tree)**, which is significantly faster and shorter to code.