Here is the exact reason why checking **only $k$ and $k+1$** (or $k+2$ in a specific boundary case) is mathematically guaranteed to be enough.

---

### The Anatomy of Substring $b$

If $b$ is a substring of repeated copies of $a$, visualize where $b$ can start and end:

```
Repeated 'a':   [  a  ] [  a  ] [  a  ] [  a  ] [  a  ]
String 'b':        [------- b -------]
                   ^                 ^
                Prefix             Suffix

```

Any valid occurrence of $b$ inside repeated copies of $a$ is composed of three parts:

1. **A suffix of $a$** (at the very beginning of $b$)
2. **Zero or more full copies of $a$** (in the middle of $b$)
3. **A prefix of $a$** (at the very end of $b$)

---

### Why the Upper Bound is Capped

Let $L_a = \text{length}(a)$ and $L_b = \text{length}(b)$.

The minimum number of copies of $a$ needed just to reach the length of $b$ is:


$$k = \lceil L_b / L_a \rceil$$

Now, where does $b$ start inside that first copy of $a$?
There are only two possibilities:

#### Case 1: $b$ starts exactly at index 0 of the first copy of $a$

If $b$ starts at index 0:

* The first character of $b$ matches the first character of $a$.
* Since $b$ has length $L_b$, it spans across at most $\lceil L_b / L_a \rceil = k$ copies.
* **Result:** **$k$ copies** are sufficient to hold it.

#### Case 2: $b$ starts somewhere in the middle of $a$ (index $\ge 1$)

If $b$ starts at index 1 or later:

* It consumes a small piece (suffix) of the 1st copy of $a$.
* Then it consumes full copies of $a$.
* The remaining tail of $b$ spills over into the next copy of $a$.
* Because it started shifted to the right, the entire window of $b$ shifts right by at most one boundary.
* How many extra copies of $a$ can a right-shift cause? **At most 1 additional copy.**
* **Result:** **$k + 1$ copies** are sufficient.

---

### Why Can't $b$ Need $k + 2$? (The Ceil vs. Integer Division Detail)

If you define:


$$k = \lceil L_b / L_a \rceil$$

$k$ copies can cover any alignment that doesn't spill over both ends. If it spills over both the left boundary and the right boundary, it requires **$k + 1$** copies.

> **Example:**
> $a = \text{"abcd"}$ ($L_a = 4$)
> $b = \text{"cdabcdab"}$ ($L_b = 8$)
> 1. Minimum copies by length: $k = \lceil 8 / 4 \rceil = 2$ copies: `"abcdabcd"` (length 8).
> * Can $b$ fit in 2 copies? No, because $b$ starts at `"cd"` (offset 2).
> 
> 
> 2. Add 1 more copy $\implies k + 1 = 3$ copies: `"abcdabcdabcd"`.
> * `"cdabcdab"` is inside: `ab [cd abcd ab] cd`.
> * Match found at 3 copies!
> 
> 
> 
> 

#### What if using integer division `k = L_b / L_a`?

If you compute $k$ using integer division without ceiling (e.g., $L_b = 9, L_a = 4 \implies 9 / 4 = 2$):

* $k = 2$ has length $8 < 9$ (cannot fit).
* $k + 1 = 3$ has length $12 \ge 9$.
* $k + 2 = 4$ covers the shift offset.
* *That is why some articles check up to $k + 2$ when they define $k = \lfloor L_b / L_a \rfloor$.*

If you ensure the string length is **$\ge L_b$** first (which is $k = \lceil L_b / L_a \rceil$):

* You only need to check **that base string (count $k$)** and **base string + one more $a$ (count $k + 1$)**.

---

### The Impossibility Rule: Why Stop at $k + 1$?

If $b$ is **not** found in $k + 1$ copies, could repeating $a$ to $k + 2$ or $k + 10$ suddenly make it work?

**No.** Because $a$ repeats periodically!
Every copy of $a$ is identical. Shifting the starting position past the first copy of $a$ (index $\ge L_a$) is completely redundant—it just repeats the exact same alignments you already checked starting in copy 1.

Every possible starting alignment within $a$ (offsets $0, 1, 2, \dots, L_a - 1$) is fully tested within $k + 1$ copies. If none match, **it is impossible**, and you safely return `-1`.