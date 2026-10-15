Lazy Propagation transforms a basic Segment Tree from a point-update structure into a range-update powerhouse by deferring work until it is strictly necessary.

---

## 1. The Bottleneck & The "Sticky Note" Idea

In a standard Segment Tree (like LeetCode 307), updating a single element takes $O(\log n)$ time because you only traverse one path from the root to a single leaf.

However, if you want to add `val` to every element in a range `[ql, qr]`, a naive Segment Tree must travel all the way down to every individual leaf inside `[ql, qr]`. In the worst case (updating `[0, n - 1]`), you visit all $4n$ nodes in the tree, degrading your update time to **$O(n)$**.

### How `lazy[]` Fixes This in $O(\log n)$

Remember why Segment Tree *queries* are $O(\log n)$: any range `[ql, qr]` can be decomposed into at most $O(\log n)$ disjoint tree nodes that **completely cover** the range.

Lazy propagation applies this exact same shortcut to **updates**:

1. **Stop Early on Full Overlap:** When descending the tree during a range update, as soon as a node's range `[l, r]` lies **completely inside** `[ql, qr]`, we **stop recursing**.
2. **Update the Node Summary Immediately:** If every element in `[l, r]` increases by `val`, and there are `(r - l + 1)` elements in this segment, the total sum of this segment increases by `val * (r - l + 1)`. We update `tree[node]` right away so any parent looking at `tree[node]` sees the completely accurate, up-to-date sum.
3. **Leave a Sticky Note (`lazy[node]`):** Because we stopped early, `node`'s children do *not* know about this `+val` update yet. So we attach a "sticky note" by doing `lazy[node] += val`.

**Meaning of `lazy[node]`:**

> *"Warning: `tree[node]` is already updated and accurate, but every element in `node`'s left and right subtrees still needs `lazy[node]` added to it."*

We only peel off that sticky note and pass it to the children (`push`) if a future update or query forces us to look *inside* `node`'s subtrees (a partial overlap).

---

## 2. The Two Core Helpers: `apply` and `push`

Separating the logic into `apply` and `push` keeps your code clean, bug-free, and easy to adapt to harder problems (including LeetCode 2916).

### Helper 1: `apply(int node, int l, int r, long val)`

This function tells `node` (which covers index range `[l, r]`) that every element in its range has increased by `val`. It does **two** things:

1. Updates `tree[node]` immediately by `val * (r - l + 1)`.
2. Adds `val` to `lazy[node]` so its children will eventually receive the update.

```java
private void apply(int node, int l, int r, long val) {
    tree[node] += val * (r - l + 1); // Update current node's sum immediately
    lazy[node] += val;               // Leave/accumulate sticky note for children
}

```

### Helper 2: `push(int node, int l, int r)`

Before we ever recurse into `node`'s children (`2 * node` and `2 * node + 1`), we must check if `node` has a pending sticky note (`lazy[node] != 0`). If it does, we **apply** that sticky note to both children and **clear** `node`'s sticky note (`lazy[node] = 0`).

```java
private void push(int node, int l, int r) {
    if (lazy[node] != 0) {
        int mid = l + (r - l) / 2;
        apply(2 * node, l, mid, lazy[node]);         // Pass sticky note to left child
        apply(2 * node + 1, mid + 1, r, lazy[node]); // Pass sticky note to right child
        lazy[node] = 0;                              // Sticky note is now cleared!
    }
}

```

---

### Step-by-Step Dry Run

Suppose `n = 4` and our initial array is `nums = [0, 0, 0, 0]`. Every node stores `sum (lazy)`.

#### Operation 1: `update(ql = 0, qr = 2, val = +1)`

We start at the root `node = 1` covering `[0, 3]`:

1. **At Node 1 `[0, 3]`:** Partial overlap with `[0, 2]`. We call `push(1, 0, 3)` (nothing happens since `lazy[1] == 0`), then branch left and right.
2. **Left Child — Node 2 `[0, 1]`:** Completely inside `[0, 2]`!
* Call `apply(2, 0, 1, +1)`:
* `tree[2] += 1 * (1 - 0 + 1) = 2`
* `lazy[2] += 1`
* **STOP!** Leaves `[0, 0]` (Node 4) and `[1, 1]` (Node 5) are *never visited* and remain `0`.


3. **Right Child — Node 3 `[2, 3]`:** Partial overlap with `[0, 2]`. Branch into its children:
* **Node 6 `[2, 2]`:** Completely inside `[0, 2]`! Call `apply(6, 2, 2, +1)` $\rightarrow$ `tree[6] = 1, lazy[6] = 1`.
* **Node 7 `[3, 3]`:** Outside `[0, 2]` $\rightarrow$ return immediately.
* Back at **Node 3 `[2, 3]`:** Pull up from children: `tree[3] = tree[6] + tree[7] = 1 + 0 = 1`.


4. **Back at Root — Node 1 `[0, 3]`:** Pull up from children: `tree[1] = tree[2] + tree[3] = 2 + 1 = 3`.

Look at the tree state after `update(0, 2, +1)`:

| Node | Range `[l, r]` | `tree[node]` (Sum) | `lazy[node]` (Sticky Note) | Status |
| --- | --- | --- | --- | --- |
| **Node 1** | `[0, 3]` | `3` | `0` | Accurate |
| **Node 2** | `[0, 1]` | `2` | **`+1`** | Accurate, holding note for Nodes 4 & 5 |
| **Node 3** | `[2, 3]` | `1` | `0` | Accurate |
| **Node 4** | `[0, 0]` | `0` *(stale!)* | `0` | Waiting on Node 2's sticky note |
| **Node 5** | `[1, 1]` | `0` *(stale!)* | `0` | Waiting on Node 2's sticky note |

#### Operation 2: `query(ql = 1, qr = 2)`

Now let's see how `push()` rescues those stale leaves when we query `[1, 2]`:

1. **At Node 1 `[0, 3]`:** Partial overlap with `[1, 2]`. `push` does nothing. Go left and right.
2. **At Node 2 `[0, 1]`:** Partial overlap with `[1, 2]` (we only want index `1`, not `0`). Because we must go inside Node 2's children, we first call **`push(2, 0, 1)`**:
* `lazy[2]` is `+1`!
* `apply(4, 0, 0, +1)` $\rightarrow$ Node 4 `[0, 0]` gets `tree[4] = 1, lazy[4] = 1`.
* `apply(5, 1, 1, +1)` $\rightarrow$ Node 5 `[1, 1]` gets `tree[5] = 1, lazy[5] = 1`.
* `lazy[2]` is reset to `0`.
* Now we visit **Node 5 `[1, 1]**`, which is completely inside `[1, 2]`, and it returns the freshly updated `tree[5] = 1`!


3. **At Node 3 `[2, 3]`:** Partial overlap with `[1, 2]`. Visits **Node 6 `[2, 2]**`, which returns `tree[6] = 1`.
4. **Total Query Result:** `1 + 1 = 2`.

---

## 3. Complete Java Template (From LC 307 to Lazy Segment Tree)

Compared to your basic LC 307 Segment Tree, there are only **three changes**:

1. Add a `long[] lazy` array of size `4 * n`.
2. In `update`, change the base case from `l == r` (single index) to `ql <= l && r <= qr` (full range overlap), calling `apply(node, l, r, val)`.
3. Call `push(node, l, r)` right before branching into children in **both** `update` and `query`.

```java
class LazySegmentTree {
    private final int n;
    private final long[] tree;
    private final long[] lazy;

    public LazySegmentTree(int[] nums) {
        this.n = nums.length;
        this.tree = new long[4 * n];
        this.lazy = new long[4 * n];
        build(1, 0, n - 1, nums);
    }

    private void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            tree[node] = nums[l];
            return;
        }
        int mid = l + (r - l) / 2;
        build(2 * node, l, mid, nums);
        build(2 * node + 1, mid + 1, r, nums);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    // 1. Apply "+val" to every element in node's segment [l, r]
    private void apply(int node, int l, int r, long val) {
        tree[node] += val * (r - l + 1);
        lazy[node] += val;
    }

    // 2. Push pending lazy updates down to direct children
    private void push(int node, int l, int r) {
        if (lazy[node] != 0) {
            int mid = l + (r - l) / 2;
            apply(2 * node, l, mid, lazy[node]);
            apply(2 * node + 1, mid + 1, r, lazy[node]);
            lazy[node] = 0; // Clear sticky note after pushing
        }
    }

    // Public API: Add 'val' to all elements in [ql, qr]
    public void updateRange(int ql, int qr, long val) {
        update(1, 0, n - 1, ql, qr, val);
    }

    private void update(int node, int l, int r, int ql, int qr, long val) {
        // Case 1: No overlap
        if (qr < l || r < ql) {
            return;
        }
        // Case 2: Full overlap ([l, r] is completely inside [ql, qr])
        if (ql <= l && r <= qr) {
            apply(node, l, r, val);
            return; // Stop here! O(log n) magic
        }
        // Case 3: Partial overlap -> push sticky notes BEFORE going down
        push(node, l, r);

        int mid = l + (r - l) / 2;
        update(2 * node, l, mid, ql, qr, val);
        update(2 * node + 1, mid + 1, r, ql, qr, val);

        // Pull up updated sums from children
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    // Public API: Get sum of elements in [ql, qr]
    public long queryRange(int ql, int qr) {
        return query(1, 0, n - 1, ql, qr);
    }

    private long query(int node, int l, int r, int ql, int qr) {
        // Case 1: No overlap
        if (qr < l || r < ql) {
            return 0L;
        }
        // Case 2: Full overlap
        if (ql <= l && r <= qr) {
            return tree[node];
        }
        // Case 3: Partial overlap -> push sticky notes BEFORE going down
        push(node, l, r);

        int mid = l + (r - l) / 2;
        return query(2 * node, l, mid, ql, qr) 
             + query(2 * node + 1, mid + 1, r, ql, qr);
    }
}

```

### Three Golden Rules to Prevent Bugs

1. **Always use `long` for `tree[]` and `lazy[]`:** In problems like LeetCode 2916, range sums and accumulated lazy values easily overflow 32-bit `int`.
2. **Accumulate (`+=`), never overwrite (`=`) in `apply`:** A child node might already have an unpushed sticky note from an earlier update. When its parent pushes another sticky note down, the two updates must stack (`lazy[node] += val`), not overwrite each other.
3. **Recalculate `tree[node]` after `update`:** On the way back up from a partial-overlap `update`, always run `tree[node] = tree[2 * node] + tree[2 * node + 1]` so ancestor sums reflect the changes made deeper in the tree.

----

Let's slow way down and throw out the code for a minute. We will build the intuition using **one tiny example with drawings of the tree**.

---

## Step 1: Forget Lazy Propagation—Look at a Basic Segment Tree

Suppose our array has 4 zeros: `nums = [0, 0, 0, 0]`.
Here is what our Segment Tree looks like initially. Each box shows the **Sum** of its range:

```text
               [Node 1: range 0..3]
                     Sum = 0
                    /       \
                   /         \
     [Node 2: range 0..1]   [Node 3: range 2..3]
           Sum = 0                Sum = 0
           /     \                /     \
          /       \              /       \
      [0..0]     [1..1]      [2..2]     [3..3]
      Sum = 0    Sum = 0     Sum = 0    Sum = 0
     (Node 4)   (Node 5)    (Node 6)   (Node 7)

```

### The Problem

Suppose someone says: **"Add +10 to index 0 and index 1 (range `0..1`)."**

* **The Naive Way (LC 307):** You go all the way down to leaf `Node 4` (`0..0`) and add `10`. You go all the way down to leaf `Node 5` (`1..1`) and add `10`. Then you come back up to `Node 2` (`0..1`) and set its sum to `10 + 10 = 20`.
* **Why is that bad?** With 4 elements, visiting 2 leaves is fine. With **100,000 elements**, visiting every single leaf in range `0..99999` takes 100,000 steps per update. That causes **Time Limit Exceeded (TLE)**.

---

## Step 2: The "Lazy" Shortcut (Why go to the leaves?)

Look at **`Node 2`** in the diagram above. Its range is **`0..1`**.
And what range did we want to add `+10` to? **`0..1`**!

Here is the million-dollar question:

> **If we want to add `+10` to every element in `0..1`, do we actually need to walk down to `Node 4` and `Node 5` right now to know what `Node 2`'s new sum is?**

**No!** `Node 2` covers **2 elements** (index `0` and index `1`). If both of them go up by `+10`, then `Node 2`'s sum **must** go up by `2 * 10 = 20`.

So instead of walking down to the leaves (`Node 4` and `Node 5`), we stop right at `Node 2` and do two things:

1. Change `Node 2`'s sum to `20`.
2. Slap a **sticky note** on `Node 2` that says **`lazy = +10`** (meaning: *"Hey! My two children below me still haven't received their `+10` yet because I was too lazy to walk down there!"*).

Here is the tree now:

```text
               [Node 1: range 0..3]
                     Sum = 20
                    /        \
                   /          \
     [Node 2: range 0..1]    [Node 3: range 2..3]
           Sum = 20                Sum = 0
      📌 lazy = +10 (NOTE!)
           /     \                 /     \
          /       \               /       \
      [0..0]     [1..1]       [2..2]     [3..3]
      Sum = 0    Sum = 0      Sum = 0    Sum = 0
     (WRONG!)   (WRONG!)     (Node 6)   (Node 7)

```

Notice something super important:

* **`Node 1` (`0..3`) and `Node 2` (`0..1`) have the 100% CORRECT Sum (`20`).**
* **`Node 4` (`0..0`) and `Node 5` (`1..1`) still say `Sum = 0` (WRONG / OUTDATED).**

Is it okay that `Node 4` and `Node 5` are wrong right now?
**YES!** Because as long as nobody asks about `0..0` or `1..1` individually, nobody will ever look at `Node 4` or `Node 5`. And if someone *does* walk down past `Node 2` later, they will see the `📌 lazy = +10` sticky note waiting on `Node 2`!

---

## Step 3: When Do We Pass the Sticky Note Down? (`push`)

Now suppose someone asks: **"What is the sum of index `1..1` (`Node 5`)?"**

To reach `Node 5`, we start at the root (`Node 1`), go left to `Node 2` (`0..1`), and now we need to step **inside** `Node 2`'s children to reach `Node 5` (`1..1`).

Wait! Before we are allowed to step below `Node 2`, we see its sticky note: **`📌 lazy = +10`**.
If we ignore that sticky note and just look at `Node 5`, `Node 5` will tell us `Sum = 0` (which is wrong!).

So **right before stepping into a node's children**, we must **push** the sticky note down to its two children (`Node 4` and `Node 5`):

1. Tell left child (`Node 4`, size 1): *"Add `+10` to yourself!"* $\rightarrow$ `Node 4` becomes `Sum = 10`, `lazy = +10`.
2. Tell right child (`Node 5`, size 1): *"Add `+10` to yourself!"* $\rightarrow$ `Node 5` becomes `Sum = 10`, `lazy = +10`.
3. Tear off `Node 2`'s sticky note (`lazy = 0`), because its children now know the truth.

```text
     [Node 2: range 0..1]
           Sum = 20
      lazy = 0 (Cleared!)
           /          \
          /            \
      [0..0]          [1..1]
      Sum = 10        Sum = 10   <-- Now Node 5 is fixed!
     📌lazy=+10      📌lazy=+10

```

Now we look at `Node 5` (`1..1`), and it gives us the right answer: **`10`**.

---

## Step 4: That's Literally All `apply` and `push` Are!

Now those two helper functions in Java should make complete sense:

1. **`apply(node, l, r, val)` = "Put a sticky note on `node` and fix its sum"**
```java
void apply(int node, int l, int r, long val) {
    int count = r - l + 1;     // How many elements live inside [l..r]?
    tree[node] += val * count; // Fix this node's sum right now
    lazy[node] += val;         // Leave sticky note for its children
}

```


2. **`push(node, l, r)` = "Pass `node`'s sticky note down to its 2 children"**
```java
void push(int node, int l, int r) {
    if (lazy[node] != 0) {     // If there is a sticky note on this node...
        int mid = (l + r) / 2;
        apply(2 * node, l, mid, lazy[node]);         // Give note to left child
        apply(2 * node + 1, mid + 1, r, lazy[node]); // Give note to right child
        lazy[node] = 0;        // Rip off this node's sticky note
    }
}

```



---

