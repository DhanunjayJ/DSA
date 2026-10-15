Welcome to the wonderful world of Segment Trees! Tackling LeetCode 307 as a bridge to LeetCode 2916 is a fantastic path. Segment trees can feel abstract at first, but once you visualize them as a **binary tree of nested ranges**, the mechanics click into place.

Here is your complete guide to understanding, visualizing, and coding a Segment Tree from scratch.

---

## 1. Anatomy of a Segment Tree: Size $4n$ and Tree Indexing

A Segment Tree is a binary tree where each node represents a specific **range** `[l, r]` of your original array.

* The **root** node covers the entire array: `[0, n - 1]`.
* Every internal node splits its range in half: a left child covering `[l, mid]` and a right child covering `[mid + 1, r]`.
* **Leaf nodes** represent individual elements of the array: `[i, i]`.

### Why size $4n$?

A segment tree is not always a perfectly balanced binary tree of depth $\log_2 n$. If your array size $n$ is not a power of 2, the bottom layer of the tree will spill over, meaning the total number of nodes in a full binary tree can exceed $2n$.

To safely store all nodes in a flat array (heap-style representation) without running out of bounds, standard practice allocates **$4 \times n$** space. Mathematically, the smallest power of 2 greater than or equal to $n$ is at most $2n$, and a full binary tree with that many leaves requires at most $2 \times (2n) = 4n$ nodes.

### How `node * 2` and `node * 2 + 1` work

Just like a binary heap stored in an array:

* If a node is at index `node`, its **left child** is always at index `2 * node`.
* Its **right child** is always at index `2 * node + 1`.
* This 1-indexed math allows us to navigate up and down the tree instantly using simple arithmetic instead of pointer objects.

---

## 2. Visual Walkthrough on `[1, 3, 5, 7]`

Let's trace our array `nums = [1, 3, 5, 7]`, where $n = 4$. Our tree array will store the **sum** of each range.

### The Tree Structure

Here is how the ranges and sums look in our tree:

```text
           [Index 1] Range: [0, 3], Sum: 16
                      /            \
        [Index 2] Range: [0, 1]    [Index 3] Range: [2, 3]
               Sum: 4                     Sum: 12
              /       \                  /       \
      [Index 4]       [Index 5]  [Index 6]       [Index 7]
     Range: [0,0]   Range: [1,1] Range: [2,2]   Range: [3,3]
       Sum: 1         Sum: 3     Sum: 5          Sum: 7

```

### A. Walkthrough of `build(node, l, r)`

Building the tree is a **post-order traversal** (bottom-up):

1. Start at root `node = 1`, covering range `[0, 3]`. Mid = `(0 + 3) / 2 = 1`.
2. Recurse left (`node = 2`, range `[0, 1]`) and right (`node = 3`, range `[2, 3]`).
3. Down at the leaves, node 4 gets value `1`, node 5 gets `3`, node 6 gets `5`, node 7 gets `7`.
4. Node 2 combines its children (`4 + 5` -> indices `4` and `5`) to get `1 + 3 = 4`.
5. Node 3 combines its children (`6 + 7`) to get `5 + 7 = 12`.
6. Root node 1 combines its children (`2 + 3`) to get `4 + 12 = 16`.

### B. Walkthrough of `point update(node, l, r, idx, val)`

Suppose we want to update `nums[1]` from `3` to `6`.

1. Start at root (`node = 1`, range `[0, 3]`): Does `[0, 3]` contain index `1`? Yes. Recurse towards index 1. Mid = 1.
2. Left child (`node = 2`, range `[0, 1]`): Does `[0, 1]` contain index `1`? Yes. Since `l != r`, mid = 0. Index 1 is greater than mid, so go right.
3. Right child of node 2 is `node = 5`, range `[1, 1]`: Here `l == r == 1`, so we hit our target leaf! Update `tree[5] = 6`.
4. As the recursion unwinds back up, recalculate sums:
* Node 2 updates its sum: left child (`tree[4]` = 1) + new right child (`tree[5]` = 6) = `7`.
* Root node 1 updates its sum: new left child (`tree[2]` = 7) + right child (`tree[3]` = 12) = `19`.



### C. Walkthrough of `range query(node, l, r, ql, qr)`

Suppose we want the sum query for range `[1, 2]` (elements from index 1 to 2, which are `3` and `5`, expected sum = 8).

1. Root (`node = 1`, range `[0, 3]`): Query range `[1, 2]` **partially overlaps** `[0, 3]`. We must check both halves.
2. Left child (`node = 2`, range `[0, 1]`): Query `[1, 2]` partially overlaps `[0, 1]` at index 1. Recurse.
* Leaf node 4 (`[0, 0]`): **No overlap** with `[1, 2]`. Return `0`.
* Leaf node 5 (`[1, 1]`): **Total overlap** inside `[1, 2]`. Return its value `3`.
* Node 2 returns `0 + 3 = 3`.


3. Right child (`node = 3`, range `[2, 3]`): Query `[1, 2]` partially overlaps `[2, 3]` at index 2. Recurse.
* Leaf node 6 (`[2, 2]`): **Total overlap** inside `[1, 2]`. Return its value `5`.
* Leaf node 7 (`[3, 3]`): **No overlap** with `[1, 2]`. Return `0`.
* Node 3 returns `5 + 0 = 5`.


4. Combine results from root's children: `3 + 5 = 8`. Perfect!

---

## 3. Standard Recursive Java Template for LC 307

Here is a clean, modular Java solution designed with the exact method signature style (`node`, `l`, `r`, `ql`, `qr`) that will scale cleanly when you introduce lazy propagation for LeetCode 2916.

```java
class NumArray {
    private int[] tree;
    private int[] nums;
    private int n;

    public NumArray(int[] nums) {
        this.nums = nums;
        this.n = nums.length;
        this.tree = new int[4 * n];
        build(1, 0, n - 1);
    }

    // 1. Build the segment tree
    private void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = nums[l];
            return;
        }
        int mid = l + (r - l) / 2;
        build(2 * node, l, mid);
        build(2 * node + 1, mid + 1, r);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    // 2. Point update an index with a new value
    private void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            nums[idx] = val;
            tree[node] = val;
            return;
        }
        int mid = l + (r - l) / 2;
        if (idx <= mid) {
            update(2 * node, l, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, r, idx, val);
        }
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    // 3. Range query for sum between ql and qr
    private int query(int node, int l, int r, int ql, int qr) {
        // Case 1: Total outside overlap
        if (ql > r || qr < l) {
            return 0;
        }
        // Case 2: Total inside overlap
        if (ql <= l && r <= qr) {
            return tree[node];
        }
        // Case 3: Partial overlap -> split and query both children
        int mid = l + (r - l) / 2;
        int leftSum = query(2 * node, l, mid, ql, qr);
        int rightSum = query(2 * node + 1, mid + 1, r, ql, qr);
        return leftSum + rightSum;
    }

    public void update(int index, int val) {
        update(1, 0, n - 1, index, val);
    }

    public int sumRange(int left, int right) {
        return query(1, 0, n - 1, left, right);
    }
}

```


---
Here is the breakdown of the **time** and **space complexities** for all the operations in our Segment Tree implementation:

---

### 1. Space Complexity

* **Tree Storage (`tree` array):** $\mathcal{O}(n)$
* Specifically, we allocate an array of size **$4n$**. While the tree requires at most $2 \times (\text{next power of } 2 \text{ for } n) \approx 2 \times (2n) = 4n$ nodes in the worst-case scenario, the asymptotic space complexity is still **$\mathcal{O}(n)$** (linear with respect to the input size).


* **Auxiliary Space (Call Stack):** $\mathcal{O}(\log n)$
* Because the segment tree is a balanced binary tree of ranges, the maximum depth of recursion during any operation is proportional to the height of the tree, which is $\log_2 n$.



---

### 2. Time Complexities

| Operation | Time Complexity | Explanation |
| --- | --- | --- |
| **`build()`** | $\mathcal{O}(n)$ | The tree visits every single node exactly once. A tree with $n$ leaves has roughly $2n$ total nodes, meaning construction is linear with respect to the array size. |
| **`update()`** (Point Update) | $\mathcal{O}(\log n)$ | At each level of the tree, we decide to go either left or right (never both). Since the tree height is $\log_2 n$, we traverse a single path from the root down to a leaf and update nodes on the way back up. |
| **`query()`** (Range Query) | $\mathcal{O}(\log n)$ | In the worst-case scenario for a range query, the query range overlaps with segments of the tree at various levels, visiting at most $\mathcal{O}(\log n)$ nodes per level. Thus, the lookup time remains logarithmically bound. |

### Summary

For LeetCode 307 (where you have $Q$ queries and updates, and $n$ elements), each update or query takes **$\mathcal{O}(\log n)$** time, resulting in an overall time complexity of $\mathcal{O}(Q \log n)$, which easily passes within time limits!

---

Imagine you have a phone book with **8,000,000 names**, but instead of reading page by page, you use a game of **"Higher or Lower"** (Binary Search).

Every single time you make a guess, you cut the remaining book in half:

1. Try 1: 8 million names $\rightarrow$ down to 4 million.
2. Try 2: 4 million $\rightarrow$ down to 2 million.
3. ...and so on, until you find your answer.

To cut 8,000,000 down to 1 single name, **how many guesses does it take?** Only **23 guesses!** That magical number 23 is what programmers call $\log_2 n$.

---

### Why `query()` and `update()` take $\log n$ time

When you do a **Range Query** or a **Point Update** in our Segment Tree:

* You start at the root (the whole array).
* Every step you take downwards, you split your range right down the middle (`mid = l + (r - l) / 2`).
* You **never** search both halves blindly; you only go down the path that overlaps your target.

Because you are cutting the array in half at every single step, you hit a leaf node (or finish your query) in just **$\log_2 n$ steps**.

* If your array has $16$ elements, $\log_2(16) = \mathbf{4}$ steps max.
* If your array has $1,000,000$ elements, $\log_2(1,000,000)$ is only about **$20$ steps**!

### Why `build()` takes $n$ time

When building the tree for the very first time:

* You have to look at every single element in your array once to give the tree its initial values.
* If your array has 4 items, you do a few setup steps. If it has 1,000 items, it takes 1,000 steps. It scales directly with the size of your array, which is why it's proportional to $n$.

### Why space takes $4n$

Imagine building a pyramid of blocks. The bottom row has $n$ blocks. As you stack them up, the rows get smaller, but total blocks used is roughly double your bottom row ($2n$). Because computer trees sometimes have awkward shapes when $n$ isn't a clean number, we double that safety margin to $4n$ so we never run out of array space.

---

Let's look at a concrete, hands-on example with $n = 16$ elements.

Imagine our array has 16 numbers (indices `0` through `15`):
`nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]`

### The Magic of $\log_2 n$

Before we trace, remember our math rule: **$\log_2(16) = 4$**.
This means our tree has a maximum depth of **4 levels**. No matter where you look in this entire 16-element array, **you will never take more than 4 steps down** to find or touch any range!

---

### Let's Trace a Range Query: `sumRange(2, 5)`

Suppose we want to find the sum of elements from index `2` to index `5` (which are the numbers `3, 4, 5, 6`).

Let's trace the exact steps the computer takes as it walks down our tree:

#### **Level 1: The Root (Top of the Tree)**

* **Node Range:** `[0, 15]` (covers the whole array)
* **Midpoint:** `(0 + 15) / 2 = 7`
* **What happens?** Our query range is `[2, 5]`. Does `[2, 5]` fit entirely inside either half?
* Left child covers `[0, 7]`. Our query `[2, 5]` is *partially* inside `[0, 7]`. **Must go left.**
* Right child covers `[8, 15]`. Our query `[2, 5]` has **zero overlap** with `[8, 15]`. **Stop right here on the right side** (returns 0 instantly).



#### **Level 2**

* We only went down the left path: **Node Range:** `[0, 7]`
* **Midpoint:** `(0 + 7) / 2 = 3`
* **What happens?** Our query range is `[2, 5]`.
* Left child covers `[0, 3]`. Partial overlap with `[2, 5]`. **Must go left.**
* Right child covers `[4, 7]`. Partial overlap with `[2, 5]`. **Must go right.**
* *(Notice how we split into two paths here!)*



#### **Level 3**

Now we have two separate branches running:

1. **Left Branch (`[0, 3]`):** Midpoint is `1`.
* Left child (`[0, 1]`): No overlap with `[2, 5] $\rightarrow$ **Stop (returns 0)**.
* Right child (`[2, 3]`): **Completely inside** `[2, 5]`! $\rightarrow$ **STOP HERE AND TAKE THE SUM** (returns `nums[2] + nums[3] = 3 + 4 = 7`). We don't need to go any deeper on this branch!


2. **Right Branch (`[4, 7]`):** Midpoint is `5`.
* Left child (`[4, 5]`): **Completely inside** `[2, 5]`! $\rightarrow$ **STOP HERE AND TAKE THE SUM** (returns `nums[4] + nums[5] = 5 + 6 = 11`). Stop going deeper here too!
* Right child (`[6, 7]`): No overlap with `[2, 5]` $\rightarrow$ **Stop (returns 0)**.



---

### The Result

We add up the pre-calculated chunks we collected along the way:

* From the left branch: `7`
* From the right branch: `11`
* **Total Sum:** `7 + 11 = 18` (which is `3 + 4 + 5 + 6`).

### Look at how fast that was!

Even though the array has 16 numbers, we only went down a maximum of **3 steps** (`[0,15] $\rightarrow$ [0,7] $\rightarrow$ [2,3]`).

If the array had **1,000,000 elements**, instead of taking 1,000,000 steps like a regular `for` loop, a Segment Tree would still only take a maximum of **20 steps** ($\log_2 1,000,000 \approx 20$), because whole chunks of the tree get instantly skipped or grabbed in one go!


---

Let’s throw away the math books and look at **why** a tree needs extra room by testing a messy number, like **$n = 5$**.

### The Problem with "Ugly" Array Sizes

If your array has **4** elements, it fits into a neat, perfect binary tree:

* 4 leaves $\rightarrow$ 2 parents $\rightarrow$ 1 root. Total nodes = 7. (Notice $7$ is less than $4 \times 4 = 16$, so $4n$ is plenty safe here).

But what if your array has **5** elements (`[1, 2, 3, 4, 5]`)?

* 5 is **not** a power of 2.
* Because of this, the tree becomes **lopsided**. Some branches stretch down an extra level deeper than others to catch the leftover elements.

### Why the Index Numbers Explode (`2 * node` and `2 * node + 1`)

Remember how our tree array finds children:

* Left child = `2 * node`
* Right child = `2 * node + 1`

Because we use simple multiplication to jump around an ordinary flat array (instead of using actual connected node objects), **we leave empty "ghost" spots** in our array to keep the math working smoothly.

Furthermore, as you go down a lopsided tree, the index numbers double rapidly. If you are at node index 7, its children are 14 and 15. If your tree is uneven, the index numbers can stretch far beyond $2n$.

### The Ultimate Safety Net: $4n$

Mathematicians calculated the absolute worst-case scenario for any possible array size $n$:

1. They found the next highest power of 2 (for $n = 5$, that's $8$).
2. They built the maximum possible tree layout for it.
3. They factored in the 1-indexing jump gaps (`2 * node`).

They proved that **no matter what array size $n$ you throw at it, you will never exceed $4 \times n$ spots in your array.**

That is why we allocate `new int[4 * n]`—it is a 100% guaranteed safety bubble so your code never crashes with an `ArrayIndexOutOfBoundsException`.

---

Ah, that single line—`if (ql <= l && r <= qr)`—is **the most important line** in the entire Segment Tree! It is the secret trick that makes the tree fast instead of slow.

Let's break it down by giving the two ranges clear names so they never get mixed up:

1. **Query Range (`ql` to `qr`)**: What the user **wants** (e.g., "Give me the sum from index 3 to 4").
2. **Node Range (`l` to `r`)**: What the **current box/node in the tree** covers (e.g., a node covering index 3 to 4).

---

### Let's Walk Through Your Example: `ql = 3, qr = 4`

Suppose the user wants the sum from index **3 to index 4** (`ql = 3, qr = 4`).

As our search walks down the tree, imagine it lands on a node whose exact range is **`l = 3` and `r = 4**`.

Let’s test the code's condition:

```java
if (ql <= l && r <= qr) {
    return tree[node];
}

```

Plug in our numbers:

* Is `ql <= l`? $\rightarrow$ Is `3 <= 3`? **True!**
* Is `r <= qr`? $\rightarrow$ Is `4 <= 4`? **True!**

Because **both** are true, the condition triggers: `return tree[node];`

---

### Why is this such a huge helper? (The Shortcut)

Think about what this means:

* The user wants indices **`[3, 4]`**.
* The node we are currently looking at covers indices **`[3, 4]`**.
* That means the node's range is **completely swallowed up** inside the range we want. It is a 100% exact match!

Instead of wasting time splitting this node further down into index 3 and index 4 to add them up manually, our code says:

> *"Hey, `tree[node]` already holds the pre-calculated sum of everything from 3 to 4! I don't need to look any further down this branch. Just grab the total and hand it back immediately!"*

### What if it's NOT a total match? (Partial Overlap)

If the node covers `[2, 5]` but the user only wants `[3, 4]`, that condition fails (`ql <= l` is false because $3$ is not $\le 2$).
That’s when the code moves to the next step: it splits the box in half and checks the left and right children separately.

That "total inside overlap" shortcut is how a Segment Tree skips massive chunks of the array in a single step, giving us that blazing-fast $\log n$ speed!

---

That line—`if (ql > r || qr < l) { return 0; }`—is the **"No Overlap" Shield**. It is the opposite of the shortcut we just talked about.

While the inside check asks, *"Is this node completely inside what I want?"*, the outside check asks, **"Is this node completely out of bounds and irrelevant to what I want?"**

---

### Breaking Down the Math: `ql > r || qr < l`

Again, let's keep our two main players clear:

* **Query Range (`ql` to `qr`)**: What the user **wants**.
* **Node Range (`l` to `r`)**: What the **current node** covers.

There are **two ways** a node can be completely out of bounds:

#### 1. The node is too far to the left (`qr < l`)

* **User wants:** `ql = 4, qr = 6`
* **Node covers:** `l = 0, r = 3`
* Check: Is `qr < l`? $\rightarrow$ Is `6 < 0`? **False.** Is `4 < 3`? **False.**
* Let's fix the example: What if the node covers `[0, 2]`?
* Check: Is `qr < l`? $\rightarrow$ Is `6 < 0`? No. But what if the node covers `[0, 2]` and the query is `[3, 5]`?
* Check: Is `qr < l`? $\rightarrow$ Is `5 < 0`? No. Wait, let's look at the node being tested: if a node covers `[0, 1]` and query is `[2, 3]`, then `qr` (3) is not less than `l`, but wait! `qr < l` means the query's *end* happens *before* the node's *start*.
* Example: Query wants **`[0, 1]`**, but the node we landed on covers **`[2, 3]`**.
* Check `qr < l`: Is `1 < 2`? **Yes!** The query ends at 1, but this node starts at 2. It is completely to the left.



#### 2. The node is too far to the right (`ql > r`)

* Query wants: **`[4, 5]`**
* Node covers: **`[0, 2]`**
* Check `ql > r`: Is `4 > 2`? **Yes!** The query starts at 4, but this node ends at 2. It is completely to the right.

---

### Concrete Walkthrough on `[1, 3, 5, 7]`

Suppose the user wants `sumRange(3, 3)` (just the single number at index 3, which is `7`).

1. **Root Node (`[0, 3]`):**
* Does query `[3, 3]` fit entirely inside `[0, 3]`? Yes, partially overlaps. We must split and look at both children.


2. **Left Child (`[0, 1]`):**
* We are checking range `[0, 1]` for query `[3, 3]`.
* Check outside bounds: Is `ql > r` (`3 > 1`)? **Yes!**
* The code immediately triggers: `return 0;`
* *Why 0?* Because index `[0, 1]` has nothing to do with index 3. It contributes **zero** to our final sum, so we safely return 0 and kill that branch instantly.


3. **Right Child (`[2, 3]`):**
* Range `[2, 3]` partially overlaps query `[3, 3]`. We search further down... and eventually isolate index 3 to get `7`.



### Summary

This line acts as a strict security guard. If a branch of the tree doesn't even touch the range you are looking for, the guard stops the code from wasting time going any deeper down that dead end!