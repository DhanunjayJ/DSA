### Part 11: Introduction to KMP (Knuth-Morris-Pratt)

KMP is the most famous string-searching algorithm in computer science.

Like the Z-Algorithm, KMP gives a **strictly guaranteed $O(N + M)$ time complexity**. But it achieves this with one massive architectural advantage:

> **KMP only preprocesses the pattern ($O(M)$ extra space).**
> It never concatenates strings and **never moves backwards in the text**. The text pointer only moves forward, making KMP ideal for streaming data.

---

### The Fundamental Flaw KMP Targets

Look at what happens during a mismatch in naive search:

```
Text:    A  B  A  B  A  B  C
Pattern: A  B  A  B  C
Index:   0  1  2  3  4

```

1. At index 0, 1, 2, 3: Characters match (`"ABAB"`).
2. At index 4: Mismatch! (`'A'` in text vs `'C'` in pattern).

**What does naive brute force do?**
It throws away everything it just learned:

* It resets the text pointer back to index 1 (`'B'`).
* It resets the pattern pointer back to index 0 (`'A'`).
* It starts checking all over again from scratch.

**What does KMP notice?**

> "Wait! Before the mismatch at `'C'`, we successfully matched `"ABAB"`.
> Look at `"ABAB"`: its suffix `"AB"` is identical to its prefix `"AB"`.
> That means we don't have to restart at the beginning of the pattern! We can just slide the pattern so that the prefix `"AB"` aligns with the suffix `"AB"` we already matched!"

Instead of backtracking in the text, KMP **keeps the text pointer where it is** and just falls back to index 2 in the pattern:

```
Text:    A  B [A  B] A  B  C
               │  │  ▲
               ▼  ▼  │ (Check here directly!)
Pattern:      [A  B] C

```

No backtracking in the text. Zero wasted steps.

---

### The Core Engine: The LPS Array ($\pi$ Table)

To know exactly where to fall back when a mismatch occurs, KMP precomputes an array for the pattern called the **LPS Array** (also called the $\pi$ table).

**LPS** stands for:

> **L**ongest **P**roper **P**refix which is also a **S**uffix.

For every index $i$ in the pattern:

* $LPS[i]$ = the length of the longest proper prefix of `pattern[0...i]` that is also a suffix of `pattern[0...i]`.

---

### Definitions: What is a "Proper Prefix"?

Take the string `"ABC"`:

* **Prefixes:** `""`, `"A"`, `"AB"`, `"ABC"`
* **Proper Prefixes:** Prefixes that are **not the entire string**: `""`, `"A"`, `"AB"`.
* **Suffixes:** `""`, `"C"`, `"BC"`, `"ABC"`
* **Proper Suffixes:** Suffixes that are **not the entire string**: `""`, `"C"`, `"BC"`.

---

### Let's Build an LPS Array by Hand (Intuition)

Let's compute $LPS$ for $P = \text{"A A B A A A"}$:

```
Index 0: P[0...0] = "A"
  Proper prefixes: []
  Proper suffixes: []
  Common: None -> Length = 0
  => LPS[0] = 0 (LPS[0] is ALWAYS 0)

Index 1: P[0...1] = "A A"
  Proper prefixes: ["A"]
  Proper suffixes: ["A"]
  Longest common: "A" (Length = 1)
  => LPS[1] = 1

Index 2: P[0...2] = "A A B"
  Proper prefixes: ["A", "AA"]
  Proper suffixes: ["B", "AB"]
  Longest common: None -> Length = 0
  => LPS[2] = 0

Index 3: P[0...3] = "A A B A"
  Proper prefixes: ["A", "AA", "AAB"]
  Proper suffixes: ["A", "BA", "ABA"]
  Longest common: "A" (Length = 1)
  => LPS[3] = 1

Index 4: P[0...4] = "A A B A A"
  Proper prefixes: ["A", "AA", "AAB", "AABA"]
  Proper suffixes: ["A", "AA", "BAA", "ABAA"]
  Longest common: "AA" (Length = 2)
  => LPS[4] = 2

Index 5: P[0...5] = "A A B A A A"
  Proper prefixes: ["A", "AA", "AAB", "AABA", "AABAA"]
  Proper suffixes: ["A", "AA", "AAA", "BAAA", "ABAAA"]
  Longest common: "AA" (Length = 2)
  => LPS[5] = 2

```

Final $LPS$ array:


$$LPS = [0, \; 1, \; 0, \; 1, \; 2, \; 2]$$

---

### Why the Name LPS is Crucial

Notice what $LPS[4] = 2$ means for `"A A B A A"`:

* Prefix: `[A A]`
* Suffix: `[A A]`
If you mismatch right after index 4, you already know the last 2 characters you saw were `"AA"`. Because `"AA"` is also the prefix of the pattern, you can resume matching immediately from index 2 of the pattern without re-checking those first 2 characters!

---

### Part 12: Building the LPS Array in $O(M)$ Time

Calculating the LPS array by listing out all prefixes and suffixes takes $O(M^2)$ or $O(M^3)$.

KMP builds the entire LPS table in **strictly $O(M)$ time** using a two-pointer approach:

* Pointer `i`: scans forward through the pattern (starts at index `1`).
* Variable `len`: tracks the length of the previous longest prefix-suffix match (starts at `0`).

---

### The Intuition

Think of `len` as doing two jobs at once:

1. It is the **length** of the current matched prefix.
2. It is the **index** of the next character in the prefix to compare!

```
Pattern:   a   b   a   b   c
Index:     0   1   2   3   4
                       ^
                       i

```

If we already know the prefix of length `len` matched, the next character that could extend this match is `pattern[len]`.

---

### The Three Rules of LPS Construction

At any point, we compare `pattern[i]` with `pattern[len]`:

#### Rule 1: Characters Match (`pattern[i] == pattern[len]`)

The current matching prefix-suffix just grew by 1 letter!

* Increment length: `len++`
* Assign: `lps[i] = len`
* Advance `i`: `i++`

---

#### Rule 2: Characters Mismatch AND `len > 0`

If characters don't match, we cannot extend the current prefix of length `len`.

Do we reset `len` all the way back to `0`? **No!**

There might be a **smaller** prefix inside our current match that is also a suffix. Where is that smaller match stored?
**In the LPS array itself!**

We fall back to:


$$\text{len} = lps[\text{len} - 1]$$

> **Crucial Detail:** Notice that **`i` does NOT increment** here! We stay on the exact same character `pattern[i]` and check if it can match this shorter fallback prefix.

---

#### Rule 3: Characters Mismatch AND `len == 0`

If we have fallen back all the way to `len = 0` and `pattern[i] != pattern[0]`:

* There is no matching prefix-suffix at all for this position.
* `lps[i] = 0`
* Advance `i`: `i++`

---

### Step-by-Step Hand Dry Run of the Fallback

Let's trace: $P = \text{"a a b a a a"}$ ($M = 6$).

* `lps = [0, 0, 0, 0, 0, 0]`
* `lps[0] = 0` (always 0)
* Start with `len = 0, i = 1`

```
--- Step 1: i = 1 ---
P[1] ('a') vs P[len] which is P[0] ('a')
Match! (Rule 1)
len = len + 1 = 1
lps[1] = 1
i++ -> i = 2
State: lps = [0, 1, 0, 0, 0, 0], len = 1

--- Step 2: i = 2 ---
P[2] ('b') vs P[len] which is P[1] ('a')
Mismatch! And len > 0 (Rule 2)
Fallback: len = lps[len - 1] = lps[0] = 0
(Note: i remains 2!)
State: len = 0, i = 2

--- Step 3: i = 2 (retry) ---
P[2] ('b') vs P[len] which is P[0] ('a')
Mismatch! And len == 0 (Rule 3)
lps[2] = 0
i++ -> i = 3
State: lps = [0, 1, 0, 0, 0, 0], len = 0

--- Step 4: i = 3 ---
P[3] ('a') vs P[len] which is P[0] ('a')
Match! (Rule 1)
len = 0 + 1 = 1
lps[3] = 1
i++ -> i = 4
State: lps = [0, 1, 0, 1, 0, 0], len = 1

--- Step 5: i = 4 ---
P[4] ('a') vs P[len] which is P[1] ('a')
Match! (Rule 1)
len = 1 + 1 = 2
lps[4] = 2
i++ -> i = 5
State: lps = [0, 1, 0, 1, 2, 0], len = 2

--- Step 6: i = 5 ---
P[5] ('a') vs P[len] which is P[2] ('b')
Mismatch! ('a' != 'b') and len = 2 > 0 (Rule 2)
Fallback: len = lps[len - 1] = lps[1] = 1
(Note: i remains 5!)
State: len = 1, i = 5

--- Step 7: i = 5 (retry) ---
P[5] ('a') vs P[len] which is P[1] ('a')
Match! (Rule 1)
len = 1 + 1 = 2
lps[5] = 2
i++ -> i = 6 (Loop finishes)

```

Final computed LPS: `[0, 1, 0, 1, 2, 2]`. Exactly what we found manually!

---

### The Code for LPS Construction

```java
public static int[] computeLPS(String pattern) {
    int m = pattern.length();
    int[] lps = new int[m];

    int len = 0; // length of previous longest prefix-suffix
    int i = 1;

    while (i < m) {
        if (pattern.charAt(i) == pattern.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len != 0) {
                // Fall back to the previous known prefix match
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    return lps;
}

```

---

### Why is this $O(M)$?

In the `while` loop:

* When characters match (Rule 1), `i` increments.
* When `len == 0` (Rule 3), `i` increments.
* When mismatching with `len > 0` (Rule 2), `len` strictly decreases.

Since `len` only ever increases when `i` increases (at most $M$ times), `len` can only decrease at most $M$ times total.

Thus, the total iterations of the while loop cannot exceed $2M \implies \mathbf{O(M)}$.

---

The cleanest, most intuitive way to build the LPS array is using the **"While-Mismatch Pattern"** with a single `for` loop.

It eliminates messy nested `if/else` flags and uses just **6 lines of core logic**.

---

### The Cleanest Implementation

```java
public static int[] buildLPS(String pat) {
    int m = pat.length();
    int[] lps = new int[m];
    
    int len = 0; // length of current matched prefix

    for (int i = 1; i < m; i++) {
        // Step 1: If characters mismatch, fall back until they match or len reaches 0
        while (len > 0 && pat.charAt(i) != pat.charAt(len)) {
            len = lps[len - 1];
        }

        // Step 2: If characters match, extend the prefix length
        if (pat.charAt(i) == pat.charAt(len)) {
            len++;
        }

        // Step 3: Record the result for index i
        lps[i] = len;
    }

    return lps;
}

```

---

### Why This is the "Best" Way to Remember It

1. **`i` always moves forward:** The `for (int i = 1; i < m; i++)` handles advancing the pointer. You never have to worry about when to do `i++` vs when not to.
2. **`while` resolves all mismatches first:** Instead of complex branching, the `while` loop answers one question:
*"If `pat[i]` doesn't match `pat[len]`, what smaller prefix can we try?"*
It keeps falling back via `len = lps[len - 1]` until it finds a match or hits `0`.
3. **`len` plays two roles naturally:**
* It is the **length** of the current matching prefix.
* It is also the **index** of the next character in that prefix to compare against `pat[i]`.


4. **Symmetry with the search phase:** The exact same loop structure is used when searching the text in KMP:
```java
for (int i = 0; i < n; i++) {
    while (j > 0 && text.charAt(i) != pat.charAt(j)) {
        j = lps[j - 1];
    }
    if (text.charAt(i) == pat.charAt(j)) j++;
    if (j == m) { /* match found */ j = lps[j - 1]; }
}

```


Learning this one pattern lets you write both the table builder and the search loop effortlessly.

---

### 3-Step Mental Checklist During an Interview

Whenever you have to write LPS from memory, recite these 3 steps:

1. **Backtrack while mismatching:**
`while (len > 0 && pat.charAt(i) != pat.charAt(len)) len = lps[len - 1];`
2. **Grow if matching:**
`if (pat.charAt(i) == pat.charAt(len)) len++;`
3. **Record length:**
`lps[i] = len;`


-----

The cleanest, most intuitive way to build the LPS array is using the **"While-Mismatch Pattern"** with a single `for` loop.

It eliminates messy nested `if/else` flags and uses just **6 lines of core logic**.

---

### The Cleanest Implementation

```java
public static int[] buildLPS(String pat) {
    int m = pat.length();
    int[] lps = new int[m];
    
    int len = 0; // length of current matched prefix

    for (int i = 1; i < m; i++) {
        // Step 1: If characters mismatch, fall back until they match or len reaches 0
        while (len > 0 && pat.charAt(i) != pat.charAt(len)) {
            len = lps[len - 1];
        }

        // Step 2: If characters match, extend the prefix length
        if (pat.charAt(i) == pat.charAt(len)) {
            len++;
        }

        // Step 3: Record the result for index i
        lps[i] = len;
    }

    return lps;
}

```

---

### Why This is the "Best" Way to Remember It

1. **`i` always moves forward:** The `for (int i = 1; i < m; i++)` handles advancing the pointer. You never have to worry about when to do `i++` vs when not to.
2. **`while` resolves all mismatches first:** Instead of complex branching, the `while` loop answers one question:
*"If `pat[i]` doesn't match `pat[len]`, what smaller prefix can we try?"*
It keeps falling back via `len = lps[len - 1]` until it finds a match or hits `0`.
3. **`len` plays two roles naturally:**
* It is the **length** of the current matching prefix.
* It is also the **index** of the next character in that prefix to compare against `pat[i]`.


4. **Symmetry with the search phase:** The exact same loop structure is used when searching the text in KMP:
```java
for (int i = 0; i < n; i++) {
    while (j > 0 && text.charAt(i) != pat.charAt(j)) {
        j = lps[j - 1];
    }
    if (text.charAt(i) == pat.charAt(j)) j++;
    if (j == m) { /* match found */ j = lps[j - 1]; }
}

```


Learning this one pattern lets you write both the table builder and the search loop effortlessly.

---

### 3-Step Mental Checklist During an Interview

Whenever you have to write LPS from memory, recite these 3 steps:

1. **Backtrack while mismatching:**
`while (len > 0 && pat.charAt(i) != pat.charAt(len)) len = lps[len - 1];`
2. **Grow if matching:**
`if (pat.charAt(i) == pat.charAt(len)) len++;`
3. **Record length:**
`lps[i] = len;`