**Rabin-Karp** is a string-searching algorithm that uses **hashing** to find pattern matches in a text.

Instead of checking every character at every starting position, it computes a hash value for the pattern and compares it against the hash values of text windows of the same length.

---

### The Intuition: A Sliding Number Window

Imagine searching for the number `456` in a sequence of digits: `1 2 4 5 6 8`.

1. Window 1: `124` $\neq$ `456`
2. Window 2: `245` $\neq$ `456`
3. Window 3: `456` == `456` (Match found!)

Comparing two integers takes **$O(1)$ time**. The Rabin-Karp algorithm converts a string into an integer (a hash) so it can compare whole words in constant time.

---

### The Challenge: How to Shift the Window Efficiently

If recalculating the hash of each $M$-length window took $O(M)$ time, the algorithm would be just as slow as naive brute-force search ($O(N \times M)$).

Rabin-Karp solves this with a **Rolling Hash**. Shifting the window drops the outgoing character and adds the incoming character in **$O(1)$ time**:

$$\text{Next Hash} = \left(\text{Old Hash} - \text{outgoing} \times \text{BASE}^{M-1}\right) \times \text{BASE} + \text{incoming}$$

#### Concrete Example: Base-10 Digits

Suppose our window holds `245`, and the next digit is `6`:

1. **Remove leading digit:** `245 - (2 × 10²) = 45`
2. **Shift left:** `45 × 10 = 450`
3. **Add new digit:** `450 + 6 = 456`

The exact same math applies to text using an alphabet base (like 256 for ASCII) modulo a large prime.

---

### Algorithm Steps

1. Calculate the hash of the pattern `P` of length $M$.
2. Calculate the hash of the first window `T[0...M-1]` of the text.
3. Slide the window one character at a time across the text:
* **Hash Match:** If the window's hash equals the pattern's hash, perform a character-by-character check to verify (to protect against hash collisions, known as a *spurious hit*).
* **Hash Mismatch:** If hashes differ, the substrings are guaranteed not to match; advance to the next character using the rolling hash formula.



---

### Complexity Analysis

| Scenario | Time Complexity | Space Complexity |
| --- | --- | --- |
| **Average Case** | $O(N + M)$ | $O(1)$ |
| **Best Case** | $O(N + M)$ | $O(1)$ |
| **Worst Case** | $O(N \times M)$ | $O(1)$ |

* **Average Case:** With a good hash function and large prime modulo, collisions are rare. Each window shift takes $O(1)$, leading to linear $O(N + M)$ time.
* **Worst Case:** If a poor hash function is chosen (or under an adversarial input) where every window causes a collision, it falls back to full character checks at every index, taking $O(N \times M)$.

---

### Strengths & Trade-offs

* **Pros:**
* **Multiple Pattern Search:** Especially effective when searching for multiple patterns of the same length $M$ simultaneously. Store all pattern hashes in a hash set; checking a window takes $O(1)$ average time regardless of how many patterns there are.
* **Simpler Implementation:** Often easier to implement than deterministic finite automaton (DFA) based algorithms like KMP or Boyer-Moore.


* **Cons:**
* Slower worst-case than **KMP** ($O(N)$ guaranteed) or **Boyer-Moore** (often sublinear in practice) due to the possibility of hash collisions.

----

The **Z-Algorithm** (or Z-Array algorithm) is a linear-time string processing algorithm that finds all occurrences of a pattern in a text in **strictly $O(N + M)$ time and $O(N + M)$ space**—with no hash collisions or worst-case degradation.

---

### What is the Z-Array?

For a string $S$ of length $L$, the **Z-array** is an array of size $L$ where each element $Z[i]$ represents:

> The length of the **longest common prefix** between $S$ and the suffix of $S$ starting at index $i$.

In other words: *How many characters starting at index $i$ match the characters starting from index $0$?*

*(By convention, $Z[0] = 0$ or left undefined because the entire string trivially matches itself).*

#### Example: `S = "aabxaabxcaabxaabxay"`

Let’s look at a simpler example first: `S = "abacaba"`

| Index $i$ | Suffix starting at $i$ | Match with prefix `abacaba` | $Z[i]$ |
| --- | --- | --- | --- |
| **0** | `abacaba` | (Self, ignore) | `0` |
| **1** | `bacaba` | None | `0` |
| **2** | `acaba` | `a` matches | `1` |
| **3** | `caba` | None | `0` |
| **4** | `abacaba` | `aba` matches | `3` |
| **5** | `bacaba` | None | `0` |
| **6** | `a` | `a` matches | `1` |

Resulting $Z$-array: `[0, 0, 1, 0, 3, 0, 1]`

---

### How to Use the Z-Array for Pattern Matching

To search for a `pattern` inside a `text`:

1. Concatenate them with a unique delimiter that appears in neither string:

$$\text{Combined} = \text{pattern} + \text{"\$"} + \text{text}$$


2. Compute the $Z$-array for this combined string.
3. Any index $i$ where $Z[i] == \text{length(pattern)}$ marks an exact match starting in the text at position:

$$\text{text\_index} = i - \text{length(pattern)} - 1$$



Because of the delimiter `$` (which matches nothing), a match can never extend beyond the pattern itself.

---

### The Intuition: Why is it $O(L)$?

A naive calculation would compare characters one-by-one from index $i$, taking $O(L^2)$ time.

The Z-Algorithm achieves linear time by maintaining a **Z-box $[L, R]$**, which represents the rightmost window in the string that has already been verified to match a prefix of the string ($S[L\dots R] == S[0\dots R-L]$).

When calculating $Z[i]$:

1. **If $i > R$:** $i$ is outside the known matching territory. Compare characters naively starting from $i$, and update the boundary $[L, R]$.
2. **If $i \le R$:** $i$ is inside the known window. Look up the corresponding prefix position $k = i - L$:
* If the match fits strictly inside the window ($Z[k] < R - i + 1$), copy it directly: $Z[i] = Z[k]$.
* If the match touches or exceeds the right boundary, start character-by-character checks **only from $R + 1$ onward**, pushing $R$ further right.



Because the right pointer $R$ only advances forward, the total number of character comparisons across the entire string is bounded by $2L$, ensuring **guaranteed $O(L)$ time**.

---

### Z-Algorithm vs. KMP vs. Rabin-Karp

| Feature | Z-Algorithm | KMP ($\pi$ / LPS array) | Rabin-Karp |
| --- | --- | --- | --- |
| **Core Idea** | Longest common prefix with suffix | Longest proper prefix which is also suffix | Rolling polynomial hash |
| **Time Complexity** | $O(N + M)$ guaranteed | $O(N + M)$ guaranteed | $O(N + M)$ average, $O(NM)$ worst |
| **Space Complexity** | $O(N + M)$ | $O(M)$ (preprocess pattern only) | $O(1)$ |
| **Hash Collisions?** | No | No | Yes (needs modular arithmetic / verification) |
| **Mental Model** | Window matching ($[L, R]$ interval) | State machine / Fallback pointer | Sliding window math |

---

### Clean Java Implementation

```java
public class ZAlgorithm {

    public static int[] calculateZ(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) {
                // Inside the current Z-box: reuse previously computed answer
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            // Expand past the Z-box if characters continue to match
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            // Update the rightmost Z-box boundary
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }
}

```

---

For coding interviews and practical problem solving, **you do not need all three for basic string search, but you do need at least two because they solve fundamentally different classes of problems.**

---

### The Reality Check

| Algorithm | Primary Superpower | Can the others replace it? |
| --- | --- | --- |
| **KMP** | Streaming data, deterministic $O(M)$ extra space, cyclic patterns | Replaces Z-Algorithm in 90% of interview problems. |
| **Z-Algorithm** | Prefix-suffix matching with an intuitive $[L, R]$ sliding interval | Can solve almost every single-pattern search problem KMP solves, but takes $O(N + M)$ space. |
| **Rolling Hash (Rabin-Karp)** | $O(1)$ substring queries, multiple patterns, 2D grids, binary search on answer | **Cannot be replaced** by KMP or Z-Algorithm for multi-substring comparisons or 2D problems. |

---

### Why They Are Fundamentally Different

#### 1. Rolling Hash is a General Tool, Not Just a Search Algorithm

Rabin-Karp is just one use case of rolling hashes. Rolling hashes give you an $O(1)$ equality check between **any two substrings anywhere in a text**.

* **Problems only Rolling Hash solves cleanly:**
* *Longest Duplicate Substring* (LeetCode 1044): Requires binary searching the length $L$ and checking hashes in a set. KMP cannot do this in $O(N \log N)$ easily.
* *Distinct Substrings / Palindromic Tree checks*
* *2D Pattern Matching* (searching an image/grid inside another grid).


* **The drawback:** Hash collisions require careful modulo arithmetic or double hashing.

#### 2. KMP and Z-Algorithm are Exact, Collision-Free Matching Engines

KMP and Z-Algorithm never fail and never suffer from worst-case hash collisions. However, between KMP and Z-Algorithm, **there is 90% overlap in what they achieve**:

* Both find a pattern in a text in guaranteed linear time.
* Both detect periodic strings (e.g., *Repeated Substring Pattern*).
* Both identify prefixes that are also suffixes.

The difference lies in how they process data:

* **KMP** processes the pattern once ($O(M)$ memory). The text can be an infinite stream of characters (e.g., network packets, huge log files) without storing the whole text in memory.
* **Z-Algorithm** requires concatenating `pattern + "$" + text`, requiring $O(N + M)$ memory to store the entire string.

---

### The Practical Verdict

If your time is limited:

1. **Learn Rolling Hash (Rabin-Karp):** Essential for tricky substring problems, hash sets of substrings, and binary search on string lengths.
2. **Pick ONE between KMP and Z-Algorithm:**
* **Choose KMP** if you want the industry standard that interviewers know best and that works on streaming data with $O(M)$ auxiliary space.
* **Choose Z-Algorithm** if you struggle with KMP's pointer backtracking and prefer a more visual, interval-based ($[L, R]$) mental model.



Learning **Rolling Hash + either KMP or Z-Algorithm** covers 99% of advanced string problems you will ever encounter.
