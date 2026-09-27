### Part 7: Introduction to the Z-Algorithm

Now we step away from hashes, math modulo, and collisions.

The **Z-Algorithm** is pure character matching. It guarantees **strict $O(N + M)$ time in the absolute worst case**, with zero collisions and zero math tricks.

---

### What is the Z-Array?

Given a string $S$ of length $n$, the **Z-array** (denoted as $Z$) is an array of size $n$.

For every index $i$ (from $0$ to $n-1$):

> **$Z[i]$ is the length of the longest substring starting at index $i$ that matches a prefix of $S$.**

In plain English:

*"If I start reading from index $i$, how many characters match the beginning of the string $S$?"*

*(By convention, $Z[0]$ is set to $0$ or ignored, because the entire string trivially matches itself from index 0).*

---

### Let's Build a Z-Array by Hand (Intuition First)

Consider the string:


$$S = \text{"a b a c a b a"}$$

Length $n = 7$. Prefixes of $S$ are:

* `"a"`
* `"a b"`
* `"a b a"`
* `"a b a c"` ... and so on.

Let's check each index $i$ one by one:

```
Index 0: S[0...] = "abacaba"  -> Z[0] = 0 (by rule, ignore self)

Index 1: S[1...] = "bacaba"
Compare with prefix "abacaba":
  S[1] is 'b', prefix starts with 'a' -> No match.
  Length = 0
  => Z[1] = 0

Index 2: S[2...] = "acaba"
Compare with prefix "abacaba":
  S[2] is 'a' == S[0] ('a')  [Match 1]
  S[3] is 'c' != S[1] ('b')  [Mismatch]
  Length = 1 ("a")
  => Z[2] = 1

Index 3: S[3...] = "caba"
Compare with prefix "abacaba":
  S[3] is 'c' != S[0] ('a')  -> No match.
  Length = 0
  => Z[3] = 0

Index 4: S[4...] = "aba"
Compare with prefix "abacaba":
  S[4] is 'a' == S[0] ('a')  [Match 1]
  S[5] is 'b' == S[1] ('b')  [Match 2]
  S[6] is 'a' == S[2] ('a')  [Match 3]
  End of string!
  Length = 3 ("aba")
  => Z[4] = 3

Index 5: S[5...] = "ba"
Compare with prefix "abacaba":
  S[5] is 'b' != S[0] ('a')  -> No match.
  Length = 0
  => Z[5] = 0

Index 6: S[6...] = "a"
Compare with prefix "abacaba":
  S[6] is 'a' == S[0] ('a')  [Match 1]
  End of string!
  Length = 1 ("a")
  => Z[6] = 1

```

Final $Z$-array for `"abacaba"`:


$$Z = [0, \; 0, \; 1, \; 0, \; 3, \; 0, \; 1]$$

Look at index 4: $Z[4] = 3$. That immediately tells you: starting at index 4, the string matches a prefix of length 3 (`"aba"`).

---

### How Does This Solve Pattern Matching?

Suppose you want to find pattern $P = \text{"abc"}$ inside text $T = \text{"ababcabcab"}$.

Here is the trick:

1. Glue them together with a unique separator that appears in neither string (like `$` or `#`):

$$\text{Combined String } S = P + \text{"\$"} + T$$


$$S = \text{"abc\$ababcabcab"}$$


2. Compute the Z-array for this combined string $S$.
3. Notice:
* The pattern has length $M = 3$.
* Because of `'$'`, no match can ever extend beyond the pattern into the text.
* If any index $i$ in the text portion has **$Z[i] == M$**, it means starting at index $i$, the next $M$ characters **identically match the prefix** (which is the pattern $P$)!



Finding pattern occurrences is as simple as scanning the $Z$-array and checking:

```java
if (Z[i] == pattern.length()) {
    // Found a match!
}

```

---

### The Big Question

Doing this naively by comparing characters character-by-character takes $O(N^2)$ time.

How does the Z-Algorithm construct the entire $Z$-array in **strictly $O(N)$ linear time**?

The secret is maintaining an active matching window called the **Z-box: $[L, R]$**.

---

### Part 8: The Z-Box $[L, R]$ and Linear Time Magic

To compute the $Z$-array in $O(N)$ time instead of $O(N^2)$, the algorithm avoids re-comparing characters that were already matched earlier.

It does this by maintaining an interval $[L, R]$ called the **Z-box**.

---

### What is the Z-box $[L, R]$?

As we scan through the string from left to right:

* $[L, R]$ represents the **furthest right substring** we have encountered so far that **matches a prefix of $S$**.
* That means:

$$S[L\dots R] == S[0\dots (R - L)]$$



```
String S:
Index:   0  1  2  3  4  5  6  7  8  9
Char:    a  b  c  x  y  a  b  c  z  ...
         ^-----^        ^-----^
         Prefix         Z-box [L, R]
                        L     R

```

Here, characters in $S[5\dots 7]$ (`"abc"`) match the prefix $S[0\dots 2]$ (`"abc"`).
So $L = 5$ and $R = 7$.

---

### How $[L, R]$ Saves Time When Computing $Z[i]$

Suppose we are at index $i$, and we want to calculate $Z[i]$. There are only two possible scenarios:

---

#### Case 1: $i > R$ (Outside the known territory)

Index $i$ is completely outside the current Z-box. We have no prior information about what lies at index $i$.

* **Action:** Compare characters character-by-character starting at $i$ with the prefix (starting at index $0$).
* Count how many characters match; that gives $Z[i]$.
* If we found a match ($Z[i] > 0$), this becomes our new furthest right matching segment, so we set:

$$L = i, \quad R = i + Z[i] - 1$$



---

#### Case 2: $i \le R$ (Inside the known territory — The Magic Case!)

Index $i$ falls **inside** the current Z-box $[L, R]$.

Since we already know that the slice $S[L\dots R]$ is an **exact clone** of the prefix $S[0\dots R - L]$, any character at position $i$ corresponds to an identical character at the prefix!

What is that corresponding index at the prefix?


$$k = i - L$$

```
Prefix:     S[0 . . . . . . . . . . . . . . R - L]
                  ^
                  k = i - L

Z-box:      S[L . . . . . . . . . . . . . . R]
                  ^
                  i

```

Since the segment is an exact clone, **the answer for index $i$ has already been computed at index $k$ ($Z[k]$)!**

We can reuse $Z[k]$, but there's one boundary check:

##### Sub-case 2A: The match from $Z[k]$ fits strictly inside the current box

If $Z[k]$ does not reach the right boundary $R$ (i.e., $Z[k] < R - i + 1$):

* $Z[i] = Z[k]$ directly!
* **Zero character comparisons needed ($O(1)$).**

##### Sub-case 2B: The match from $Z[k]$ touches or extends beyond $R$

$Z[k]$ claims to match at least up to boundary $R$. We know the characters match up to $R$, but **we do not know what happens past $R$** (it hasn't been scanned yet).

* We initialize $Z[i] = R - i + 1$ (the part we already know matches for sure).
* Then, we compare characters one-by-one **only starting from $R + 1$ onward**.
* Once a mismatch occurs, we update our Z-box with the new boundary:

$$L = i, \quad R = \text{new furthest matched index}$$



---

### Why is this $O(N)$ Guaranteed?

Look at the pointer behavior:

* In Sub-case 2A: $Z[i]$ is copied in $O(1)$ time. No character checks happen.
* In Case 1 and Sub-case 2B: Character checks only happen for positions **beyond $R$**.
* Every successful character match **advances $R$ to the right**.
* Since $R$ starts at $0$ and can only move forward up to $N - 1$, the while loop that compares characters can only succeed at most $N$ times total throughout the entire program!

Any unsuccessful comparison immediately breaks the loop and moves $i$ to the next step (at most $N$ failed comparisons).

$$\text{Total character comparisons} \le 2N \implies \mathbf{O(N)} \text{ strictly guaranteed!}$$

---

### Part 9: Complete Dry Run of the Z-Algorithm

Let's trace a string that triggers every branch of the algorithm (Case 1, Sub-case 2A, and Sub-case 2B).

Consider the string:


$$S = \text{"a a b x a a b x a a b y"}$$


Length $N = 12$.

Let's index the characters:

```
Index:   0   1   2   3   4   5   6   7   8   9  10  11
Char:    a   a   b   x   a   a   b   x   a   a   b   y

```

Initial state:

* $Z$-array initialized to all zeros: `[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]`
* Current Z-box: $L = 0, R = 0$
* By rule, $Z[0] = 0$. We loop $i$ from $1$ to $11$.

---

### Step-by-Step Execution

#### $i = 1$ (`S[1] = 'a'`)

* **Is $i \le R$?** $1 \le 0$ is **False** (Case 1: Outside).
* Compare from $i = 1$ with prefix from $0$:
* $S[1]$ (`'a'`) $== S[0]$ (`'a'`) $\rightarrow$ match
* $S[2]$ (`'b'`) $\ne S[1]$ (`'a'`) $\rightarrow$ mismatch!


* Matched $1$ character $\implies Z[1] = 1$.
* Update Z-box: $L = 1, R = 1 + 1 - 1 = 1$.
* State: $Z[1] = 1, [L, R] = [1, 1]$.

---

#### $i = 2$ (`S[2] = 'b'`)

* **Is $i \le R$?** $2 \le 1$ is **False** (Case 1: Outside).
* Compare from $i = 2$ with prefix from $0$:
* $S[2]$ (`'b'`) $\ne S[0]$ (`'a'`) $\rightarrow$ mismatch immediately!


* $Z[2] = 0$.
* No match found, so $[L, R]$ remains $[1, 1]$.

---

#### $i = 3$ (`S[3] = 'x'`)

* **Is $i \le R$?** $3 \le 1$ is **False** (Case 1: Outside).
* Compare with prefix:
* $S[3]$ (`'x'`) $\ne S[0]$ (`'a'`) $\rightarrow$ mismatch immediately!


* $Z[3] = 0$. $[L, R]$ remains $[1, 1]$.

---

#### $i = 4$ (`S[4] = 'a'`)

* **Is $i \le R$?** $4 \le 1$ is **False** (Case 1: Outside).
* Compare with prefix:
* $S[4]$ (`'a'`) $== S[0]$ (`'a'`) $\rightarrow$ match 1
* $S[5]$ (`'a'`) $== S[1]$ (`'a'`) $\rightarrow$ match 2
* $S[6]$ (`'b'`) $== S[2]$ (`'b'`) $\rightarrow$ match 3
* $S[7]$ (`'x'`) $== S[3]$ (`'x'`) $\rightarrow$ match 4
* $S[8]$ (`'a'`) $\ne S[4]$ (`'a'`) ... wait: $S[8]$ is `'a'`, $S[4]$ is `'a'`. Let's check $S[8]$ with prefix index 4:
* Prefix is `"a a b x a a..."` $\implies S[4] = \text{'a'}$. They match!
* Let's check carefully:
* $S[4\dots 7]$ is `"aabx"`, which matches $S[0\dots 3]$ (`"aabx"`).
* Next character: $S[8]$ is `'a'`, prefix $S[4]$ is `'a'`. Does it match? Yes!
* $S[9]$ is `'a'`, prefix $S[5]$ is `'a'`. Match!
* $S[10]$ is `'b'`, prefix $S[6]$ is `'b'`. Match!
* $S[11]$ is `'y'`, prefix $S[7]$ is `'x'`. Mismatch! (`'y' \ne 'x'`)






* Matched 7 characters! (`"aabxaab"`)
* $\implies Z[4] = 7$.
* Update Z-box: $L = 4, R = 4 + 7 - 1 = 10$.
* State: $Z[4] = 7, [L, R] = [4, 10]$.
*(Current Z-box covers $S[4\dots 10]$ = `"aabxaab"`).*

---

#### $i = 5$ (`S[5] = 'a'`) — [Here Comes the Magic!]

* **Is $i \le R$?** $5 \le 10$ is **True** (Case 2: Inside the box).
* Corresponding prefix index:

$$k = i - L = 5 - 4 = 1$$


* Look up previously computed value: $Z[1] = 1$.
* Check remaining space in the box:

$$\text{remaining} = R - i + 1 = 10 - 5 + 1 = 6$$


* Since $Z[k] < \text{remaining}$ ($1 < 6$):
* **Sub-case 2A applies!** The match is strictly inside the Z-box.
* We immediately set:

$$Z[5] = Z[1] = 1$$




* **Zero character comparisons performed!**
* $[L, R]$ remains $[4, 10]$.

---

#### $i = 6$ (`S[6] = 'b'`)

* **Is $i \le R$?** $6 \le 10$ is **True**.
* $k = i - L = 6 - 4 = 2$.
* Look up $Z[2] = 0$.
* Since $Z[k] < \text{remaining}$ ($0 < 5$):
* $Z[6] = Z[2] = 0$ directly in $O(1)$!


* $[L, R]$ remains $[4, 10]$.

---

#### $i = 7$ (`S[7] = 'x'`)

* **Is $i \le R$?** $7 \le 10$ is **True**.
* $k = i - L = 7 - 4 = 3$.
* Look up $Z[3] = 0$.
* $Z[7] = Z[3] = 0$ directly in $O(1)$!
* $[L, R]$ remains $[4, 10]$.

---

#### $i = 8$ (`S[8] = 'a'`) — [Sub-case 2B in Action!]

* **Is $i \le R$?** $8 \le 10$ is **True**.
* $k = i - L = 8 - 4 = 4$.
* Look up $Z[4] = 7$.
* Check remaining space in the box:

$$\text{remaining} = R - i + 1 = 10 - 8 + 1 = 3$$


* Here, $Z[k] \ge \text{remaining}$ ($7 \ge 3$):
* The match touches the boundary $R = 10$!
* We already know the first 3 characters match up to $R$: $Z[8] = 3$.
* Now we **only compare characters starting after $R$** (from index $11$ onward):
* Next index to check: $i + Z[i] = 8 + 3 = 11$.
* Character at index 11 is $S[11] = \text{'y'}$.
* Character at prefix position $Z[8] = 3$ is $S[3] = \text{'x'}$.
* `'y' \ne 'x'` $\rightarrow$ mismatch!




* Loop stops. $Z[8] = 3$.
* Boundary did not extend past $R = 10$, so $[L, R]$ remains $[4, 10]$.

---

#### $i = 9, 10, 11$

* $i = 9$: Inside box, $k = 9 - 4 = 5$, $Z[5] = 1$. Matches within box $\implies Z[9] = 1$.
* $i = 10$: Inside box, $k = 10 - 4 = 6$, $Z[6] = 0 \implies Z[10] = 0$.
* $i = 11$: Outside box ($11 > 10$). $S[11] = \text{'y'} \ne S[0] \implies Z[11] = 0$.

---

### Final Result

| Index | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| **Char** | `a` | `a` | `b` | `x` | `a` | `a` | `b` | `x` | `a` | `a` | `b` | `y` |
| **$Z[i]$** | 0 | 1 | 0 | 0 | 7 | 1 | 0 | 0 | 3 | 1 | 0 | 0 |

Notice: indices 5, 6, 7, 9, and 10 were evaluated in **$O(1)$ operations with zero comparisons**.

---

### Part 10: Complete Java Implementation of the Z-Algorithm

Here is the clean Java code that implements both the $O(N)$ Z-array construction and its application to pattern matching.

---

### Java Code

```java
import java.util.ArrayList;
import java.util.List;

public class ZAlgorithm {

    /**
     * Constructs the Z-array for string s in O(n) time.
     * Z[i] = length of the longest substring starting at s[i] that matches a prefix of s.
     */
    public static int[] calculateZ(String s) {
        int n = s.length();
        int[] z = new int[n];

        // [l, r] defines the current rightmost Z-box
        int l = 0;
        int r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) {
                // Inside Z-box: reuse mirrored prefix value z[i - l],
                // but don't exceed the boundary (r - i + 1)
                z[i] = Math.min(r - i + 1, z[i - l]);
            }

            // Try to expand beyond the current match (Case 1 or Sub-case 2B)
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }

            // If the match extended past r, update the Z-box boundaries
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }

        return z;
    }

    /**
     * Finds all starting indices of pattern inside text using the Z-Algorithm.
     */
    public static List<Integer> search(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();

        if (pattern.isEmpty() || pattern.length() > text.length()) {
            return occurrences;
        }

        // 1. Concatenate: pattern + "$" + text
        // '$' must be a character not present in either text or pattern
        String combined = pattern + "$" + text;
        int m = pattern.length();

        // 2. Compute the Z-array for the combined string
        int[] z = calculateZ(combined);

        // 3. Scan the text section of the Z-array
        // Pattern occupies indices [0 ... m - 1]
        // '$' is at index m
        // Text starts at index m + 1
        for (int i = m + 1; i < combined.length(); i++) {
            if (z[i] == m) {
                // Map the combined index back to the original text index
                occurrences.add(i - (m + 1));
            }
        }

        return occurrences;
    }

    public static void main(String[] args) {
        String text = "ababcabcab";
        String pattern = "abc";

        List<Integer> matches = search(text, pattern);
        System.out.println("Pattern found at indices: " + matches);
        // Output: Pattern found at indices: [2, 5]
    }
}

```

---

### Why the Code is So Compact: The 3 Core Lines

Look at how the two cases from earlier compress into just three operations:

```java
if (i <= r) {
    z[i] = Math.min(r - i + 1, z[i - l]);
}

```

* If $i \le r$, it takes the minimum of:
* $z[i - l]$ (mirrored prefix match)
* $r - i + 1$ (remaining space in the current box)


* If $z[i - l]$ was strictly inside, `z[i]` is initialized to $z[i - l]$.

```java
while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
    z[i]++;
}

```

* If `z[i]` was strictly inside the box, the first check inside this `while` loop will immediately fail because we already know that character $i + z[i]$ causes a mismatch. The loop terminates in 0 iterations!
* If `z[i]` reached the boundary $r$, this loop kicks off character checks **only from $r + 1$ onward**.

```java
if (i + z[i] - 1 > r) {
    l = i;
    r = i + z[i] - 1;
}

```

* Only updates $[l, r]$ when a new match extends further to the right than the current $r$.

---

### Complexity Breakdown

* **Time Complexity:**
* $O(N + M)$ strictly guaranteed.
* Every successful match increments $r$, and $r$ never decreases. Since $r < N + M + 1$, the inner `while` condition can evaluate to `true` at most $N + M$ times across the entire runtime.


* **Space Complexity:**
* $O(N + M)$ to store the `combined` string and the `z` array.



---
Here is why we already know those 3 characters match, without comparing them again.

Look at the two facts established before reaching $i = 8$:

---

### Fact 1: What the Z-box $[L, R] = [4, 10]$ tells us

When $i = 4$, the algorithm matched $7$ characters:


$$S[4\dots 10] = \text{"a a b x a a b"}$$

Because it matched a prefix of length 7, this entire slice is an **exact clone** of the prefix $S[0\dots 6]$:

```
Prefix:    S[0...6]   =  [ a   a   b   x   a   a   b ]
                          0   1   2   3   4   5   6
                          |   |   |   |   |   |   |   (Identical characters!)
Z-box:     S[4...10]  =  [ a   a   b   x   a   a   b ]
                          4   5   6   7   8   9  10
                                          ^       ^
                                          i=8     R=10

```

Notice where index $i = 8$ sits inside this box:

* Index $8$ corresponds to prefix index $4$ ($k = i - L = 8 - 4 = 4$).
* From index $8$ to the end of the box ($R = 10$), there are **3 characters**:
* $S[8] == S[4] == \text{'a'}$
* $S[9] == S[5] == \text{'a'}$
* $S[10] == S[6] == \text{'b'}$



So the characters from index $8$ to $10$ are **`"a a b"`**.

---

### Fact 2: What $Z[k] = Z[4] = 7$ tells us

Now look at the mirror position at the beginning: $k = 4$.
We already computed $Z[4] = 7$ earlier.

What does $Z[4] = 7$ mean?
It means starting at index $4$, the string matches the **first 7 characters of the entire string ($S[0\dots 6]$)**!

So the characters starting at index $4$ begin with:

* $S[4] == S[0] == \text{'a'}$
* $S[5] == S[1] == \text{'a'}$
* $S[6] == S[2] == \text{'b'}$

---

### Putting the Two Facts Together

Connect the chain of equalities:

1. From **Fact 1**:

$$S[8\dots 10] \text{ is identical to } S[4\dots 6]$$


2. From **Fact 2**:

$$S[4\dots 6] \text{ is identical to } S[0\dots 2] \text{ (the string's prefix!)}$$



Therefore, by basic substitution:


$$S[8\dots 10] \text{ is identical to } S[0\dots 2]$$

```
Prefix of string:     S[0...2]   =   a   a   b
                                     |   |   |  (Equal by Z[4] = 7)
Middle slice:         S[4...6]   =   a   a   b
                                     |   |   |  (Equal by Z-box [4, 10])
Our current window:   S[8...10]  =   a   a   b

```

Those are $3$ characters:


$$\text{length} = R - i + 1 = 10 - 8 + 1 = 3$$

Because of transitivity:


$$\text{If } A == B \text{ and } B == C, \text{ then } A == C$$

We are guaranteed that $S[8\dots 10]$ matches the first 3 characters of the string ($S[0\dots 2]$) **without reading a single character**.

---

### Why Can't We Just Set $Z[8] = 7$?

$Z[4]$ was $7$, so why can't we say $Z[8] = 7$?

Because our Z-box ends at $R = 10$.

* We only know for sure what characters exist up to index $10$.
* What lies at index $11$ ($S[11]$)? The current Z-box $[4, 10]$ has never checked index $11$.
* That is why we can only guarantee the match **up to $R$** (which is 3 characters).
* To see if the match continues for a 4th or 5th character, we must inspect characters starting from index $11$ onward.

---

Here is why we already know those 3 characters match, without comparing them again.

Look at the two facts established before reaching $i = 8$:

---

### Fact 1: What the Z-box $[L, R] = [4, 10]$ tells us

When $i = 4$, the algorithm matched $7$ characters:


$$S[4\dots 10] = \text{"a a b x a a b"}$$

Because it matched a prefix of length 7, this entire slice is an **exact clone** of the prefix $S[0\dots 6]$:

```
Prefix:    S[0...6]   =  [ a   a   b   x   a   a   b ]
                          0   1   2   3   4   5   6
                          |   |   |   |   |   |   |   (Identical characters!)
Z-box:     S[4...10]  =  [ a   a   b   x   a   a   b ]
                          4   5   6   7   8   9  10
                                          ^       ^
                                          i=8     R=10

```

Notice where index $i = 8$ sits inside this box:

* Index $8$ corresponds to prefix index $4$ ($k = i - L = 8 - 4 = 4$).
* From index $8$ to the end of the box ($R = 10$), there are **3 characters**:
* $S[8] == S[4] == \text{'a'}$
* $S[9] == S[5] == \text{'a'}$
* $S[10] == S[6] == \text{'b'}$



So the characters from index $8$ to $10$ are **`"a a b"`**.

---

### Fact 2: What $Z[k] = Z[4] = 7$ tells us

Now look at the mirror position at the beginning: $k = 4$.
We already computed $Z[4] = 7$ earlier.

What does $Z[4] = 7$ mean?
It means starting at index $4$, the string matches the **first 7 characters of the entire string ($S[0\dots 6]$)**!

So the characters starting at index $4$ begin with:

* $S[4] == S[0] == \text{'a'}$
* $S[5] == S[1] == \text{'a'}$
* $S[6] == S[2] == \text{'b'}$

---

### Putting the Two Facts Together

Connect the chain of equalities:

1. From **Fact 1**:

$$S[8\dots 10] \text{ is identical to } S[4\dots 6]$$


2. From **Fact 2**:

$$S[4\dots 6] \text{ is identical to } S[0\dots 2] \text{ (the string's prefix!)}$$



Therefore, by basic substitution:


$$S[8\dots 10] \text{ is identical to } S[0\dots 2]$$

```
Prefix of string:     S[0...2]   =   a   a   b
                                     |   |   |  (Equal by Z[4] = 7)
Middle slice:         S[4...6]   =   a   a   b
                                     |   |   |  (Equal by Z-box [4, 10])
Our current window:   S[8...10]  =   a   a   b

```

Those are $3$ characters:


$$\text{length} = R - i + 1 = 10 - 8 + 1 = 3$$

Because of transitivity:


$$\text{If } A == B \text{ and } B == C, \text{ then } A == C$$

We are guaranteed that $S[8\dots 10]$ matches the first 3 characters of the string ($S[0\dots 2]$) **without reading a single character**.

---

### Why Can't We Just Set $Z[8] = 7$?

$Z[4]$ was $7$, so why can't we say $Z[8] = 7$?

Because our Z-box ends at $R = 10$.

* We only know for sure what characters exist up to index $10$.
* What lies at index $11$ ($S[11]$)? The current Z-box $[4, 10]$ has never checked index $11$.
* That is why we can only guarantee the match **up to $R$** (which is 3 characters).
* To see if the match continues for a 4th or 5th character, we must inspect characters starting from index $11$ onward.

---

You hit the nail on the head, but just have **one small sign flip**!

Let's clarify which way the comparison goes:

* When **$\text{remaining} > Z[k]$**: $Z[k]$ fits completely **inside** the box. It does *not* hit the edge $R$. So we set **$Z[i] = Z[k]$**.
* When **$Z[k] \ge \text{remaining}$**: The match hits or tries to go past the wall $R$. We don't know what is past $R$, but up to the wall is safe. So we set **$Z[i] = \text{remaining}$**, and then check whatever lies past $R$!

---

### Think of the Z-box like a "Safe Zone" with a Fog of War

Imagine index $R$ is the edge of a map.

```
[0 . . . . . . . . . . . . . . .]
[L . . . . . . . . . . . . . R] | ??? (FOG OF WAR past R)
               ^             ^
               i             R
               [--remaining--]

```

Everything up to $R$ is **known territory** that is guaranteed to match the prefix of the string. Everything past $R$ is covered in **fog**—we haven't looked there yet.

At position $i$, the distance to the edge of the safe zone is:


$$\text{remaining} = R - i + 1$$

Now, we look up our cheat sheet: the mirror copy near the beginning of the string has score $Z[k]$.

---

### Only Two Scenarios Happen:

#### Scenario A: $Z[k] < \text{remaining}$ (The match is strictly INSIDE the safe zone)

* Example: There are 5 safe characters left to $R$ ($\text{remaining} = 5$), but $Z[k] = 2$.
* $Z[k] = 2$ means: *"The mirror copy matched 2 letters, and on the 3rd letter, it **failed**."*
* Since that 3rd letter is still inside our safe zone (before reaching the fog), it **must fail here too**!
* So the answer is definitively **$Z[i] = Z[k] = 2$**. No checks needed, done!

---

#### Scenario B: $Z[k] \ge \text{remaining}$ (The match HITS the wall at $R$)

* Example: There are only 3 safe characters left to $R$ ($\text{remaining} = 3$), but $Z[k] = 7$.
* Can we say $Z[i] = 7$? **No!** Because after 3 characters, we hit $R$ and step into the **fog**.
* But can we say the first 3 characters definitely match? **Yes!** Because they are inside the safe zone, and the safe zone matches the string's prefix.
* So we say: **"I am 100% sure about 3 characters ($Z[i] = \text{remaining} = 3$)."**
* Then we walk into the fog (starting from $R + 1$) and check character-by-character to see if the streak keeps going!

---

### That is the Entire Logic in 1 Line of Code:

```java
z[i] = Math.min(r - i + 1, z[i - l]);

```

* If $Z[k]$ is smaller $\implies$ take $Z[k]$.
* If $\text{remaining}$ is smaller $\implies$ take $\text{remaining}$ (up to the wall $R$).

After this single line, run the `while` loop to see if the match continues past the wall into the fog!

