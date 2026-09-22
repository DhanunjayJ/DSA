### 1. The Core Intuition: Why XOR Loves Greedy

XOR ($\oplus$) gives `1` when bits differ, and `0` when bits are identical:

* $0 \oplus 1 = 1$
* $1 \oplus 0 = 1$
* $0 \oplus 0 = 0$
* $1 \oplus 1 = 0$

In binary representation, the **leftmost bit (Most Significant Bit / MSB)** holds vastly more weight than all lower bits combined.

For example, having a `1` at bit position 4 (value $2^4 = 16$) is strictly greater than having `1`s at all positions below it ($2^3 + 2^2 + 2^1 + 2^0 = 8 + 4 + 2 + 1 = 15$).

Therefore, to maximize $A \oplus B$:

* You **must prioritize getting a `1` at the highest bit position possible**.
* A `1` at bit $k$ beats any combination of `1`s from bits $0$ to $k-1$.
* This means our decision-making is strictly **greedy from left to right (MSB down to LSB)**.

---

### 2. How the Binary Trie Represents Numbers

Instead of a dictionary Trie with 26 letters ('a' through 'z'), a **Binary Trie** has only **2 branches: `0` and `1**`.

Every 32-bit non-negative integer is represented as a sequence of exactly 31 or 32 bits. Let's fix the length to 31 bits (from bit index 30 down to 0, since numbers are up to $2^{31}-1$).

```
         Root
        /    \
       0      1
      / \    / \
     0   1  0   1
    ...

```

* **Root** represents an empty prefix (before looking at any bits).
* **Level 1** represents bit 30.
* **Level 2** represents bit 29.
* ...
* **Level 31 (Leaves)** represents bit 0.

#### Why paths never "mix and match" bits from different numbers:

Consider inserting three 4-bit numbers:

* $A = 5$ (`0 1 0 1`)
* $B = 7$ (`0 1 1 1`)
* $C = 12$ (`1 1 0 0`)

```
             Root
            /    \
      (0)  /      \  (1)
        Node1     Node2
          \         \
      (1)  \         \  (1)
          Node3     Node4
          /   \        \
    (0)  /     \ (1)    \ (0)
      Node5   Node6    Node7
        \       \        \
    (1)  \   (1) \    (0) \
        [5]     [7]      [12]

```

Notice:

1. `Node1` represents all numbers starting with prefix `0`.
2. `Node3` represents all numbers starting with prefix `01`.
3. If you move down to `Node5`, you are now **locked** into candidates whose prefix is `010`. You cannot jump over to `Node6` or `Node7`.
4. Any path that goes from **Root down to a Leaf is a single continuous train track** representing one real number from your original array.

---

### 3. The Query Step: Finding the Best Partner for $X$

Suppose we have inserted all numbers into the Trie. Now, pick any number $X$ from the array. We want to find a partner $Y$ in the Trie such that $X \oplus Y$ is as large as possible.

Let's say we are looking at bit $i$ of $X$:

1. Let $b$ be the $i$-th bit of $X$ (`0` or `1`).
2. **What would make $X \oplus Y$ have a `1` at position $i$?**
* We need $Y$'s $i$-th bit to be the opposite: `opposite = 1 - b` (or `b ^ 1`).


3. **Check the Trie from your current node:**
* **Case A: The `opposite` branch exists.**
* Great! Take that branch.
* By stepping into `opposite`, you ensure the $i$-th bit of the XOR result is `1`.
* Add $2^i$ (or `1 << i`) to your running XOR total.


* **Case B: The `opposite` branch does NOT exist.**
* You have no choice. None of the numbers surviving in this branch have the opposite bit.
* You must take the branch matching $b$.
* The $i$-th bit of the XOR result becomes `0`.




4. Move down to the next bit ($i - 1$) and repeat until bit 0.

Because you always took the branch giving a `1` whenever possible, and because higher bits dominate lower bits, the final XOR sum obtained at the leaf is **guaranteed to be the absolute maximum XOR possible for number $X$**.

---

### 4. Step-by-Step Example

Let's use 4-bit numbers: `nums = [3, 10, 5, 25]`
In 5 bits:

* $3$  = `00011`
* $5$  = `00101`
* $10$ = `01010`
* $25$ = `11001`

Insert all 4 numbers into the Trie:

* Root has two branches: `0` (contains 3, 5, 10) and `1` (contains 25).

Now, let's query with **$X = 5$ (`00101`)**:

| Bit Position | $X$'s Bit | Desired Partner Bit (`1 - b`) | Branch Available in Trie? | Action Taken | Resulting XOR Bit |
| --- | --- | --- | --- | --- | --- |
| **Bit 4** (weight 16) | `0` | `1` | **Yes** (number 25 has `1`) | Go right to `1` | **`1`** (value: 16) |
| **Bit 3** (weight 8) | `0` | `1` | **Yes** (25 has `1` at bit 3) | Go right to `1` | **`1`** (value: 8) |
| **Bit 2** (weight 4) | `1` | `0` | **Yes** (25 has `0` at bit 2) | Go left to `0` | **`1`** (value: 4) |
| **Bit 1** (weight 2) | `0` | `1` | **No** (25 has `0` here; no other candidates remain) | Forced left to `0` | **`0`** (value: 0) |
| **Bit 0** (weight 1) | `1` | `0` | **No** (25 has `1` here) | Forced right to `1` | **`0`** (value: 0) |

* Total XOR: $16 + 8 + 4 + 0 + 0 = 28$.
* The partner we traced down to was $25$, and $5 \oplus 25 = 28$.

---

### 5. Algorithmic Complexity

* **Number of bits:** 31 (bits 30 down to 0).
* **Insertion:** Inserting one number takes $31$ operations. For $N$ numbers: $O(31 \times N)$.
* **Query:** Querying one number takes $31$ operations. For $N$ numbers: $O(31 \times N)$.
* **Total Time Complexity:** $O(31 \times N) \approx O(N)$, which easily passes for $N = 2 \times 10^5$.
* **Space Complexity:** At most $31 \times N$ nodes, each node having 2 pointers (`left` for 0, `right` for 1) $\approx O(N)$.

visuaalization https://share.gemini.google/fXOen5X7hVbj