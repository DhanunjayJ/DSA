### Part 1: Why Do We Need Rabin-Karp?

To understand why Rabin-Karp exists, we have to look at what happens when you do string searching the "normal" (brute-force) way, and see exactly where it breaks down.

---

### The Problem

You are given two strings:

* **Text ($T$)** of length $N$: e.g., `"A B A B A B C"` ($N = 7$)
* **Pattern ($P$)** of length $M$: e.g., `"A B C"` ($M = 3$)

Goal: Find the starting index where $P$ appears inside $T$.

---

### How Brute Force Does It

The naive approach places $P$ at every possible starting window in $T$ and checks letter-by-letter:

```
Window 0:
T:  [A  B  A] B  A  B  C
P:  [A  B  C]
Check: 'A'=='A', 'B'=='B', 'A'!='C'  -> Failed at index 2 (did 3 checks)

Window 1:
T:   A [B  A  B] A  B  C
P:     [A  B  C]
Check: 'B'!='A'                       -> Failed at index 1 (did 1 check)

Window 2:
T:   A  B [A  B  A] B  C
P:        [A  B  C]
Check: 'A'=='A', 'B'=='B', 'A'!='C'  -> Failed at index 4 (did 3 checks)
...

```

For each window, we might compare up to $M$ characters.
There are about $(N - M + 1)$ windows.

**Worst-Case Scenario:**
Imagine this worst-case input:

* $T$ = `"A A A A A A A A A B"` ($N$ characters)
* $P$ = `"A A A B"` ($M$ characters)

At every single window, you compare almost all characters before realizing the last one doesn't match:

* Window 0: checks $M$ characters $\rightarrow$ mismatch at end
* Window 1: checks $M$ characters $\rightarrow$ mismatch at end
* Window 2: checks $M$ characters $\rightarrow$ mismatch at end

Total character comparisons = roughly $N \times M$.
If $N = 100{,}000$ and $M = 10{,}000$, $N \times M = 1{,}000{,}000{,}000$ (1 billion operations), which **Time Limit Exceeded (TLE)** on modern judges.

---

### The Core Question Rabin-Karp Asks

> *"Why are we spending up to $M$ operations comparing characters in a window, when in computers, comparing two integers takes just **$O(1)$ constant time**?"*

If two numbers are $456$ and $456$:

* CPU compares them in **1 CPU clock cycle** ($O(1)$).
* It does not check digit-by-digit.

So, the grand idea of Rabin-Karp is:

1. Turn the pattern $P$ into a single number (a **hash**).
2. As we slide across the text $T$, represent each window as a single number (its **hash**).
3. Compare the two numbers in **$O(1)$** time!

---

### The Big Catch (The "Rolling" Problem)

Converting a word of length $M$ into a number normally takes $O(M)$ time because you have to loop through all $M$ letters.

If every time we slide our window by 1 step we spend $O(M)$ time recalculating the number, we've saved nothing! It's still $O(N \times M)$.

**The magic of Rabin-Karp is:**
When the window shifts by 1 position:

* Exactly **one** old character leaves on the left.
* Exactly **one** new character enters on the right.
* Can we update the window's number from the old number in **$O(1)$** time, without re-reading the characters in between?

**Yes.** That mechanism is called a **Rolling Hash**.

---

### Part 1: Why Do We Need Rabin-Karp?

To understand why Rabin-Karp exists, we have to look at what happens when you do string searching the "normal" (brute-force) way, and see exactly where it breaks down.

---

### The Problem

You are given two strings:

* **Text ($T$)** of length $N$: e.g., `"A B A B A B C"` ($N = 7$)
* **Pattern ($P$)** of length $M$: e.g., `"A B C"` ($M = 3$)

Goal: Find the starting index where $P$ appears inside $T$.

---

### How Brute Force Does It

The naive approach places $P$ at every possible starting window in $T$ and checks letter-by-letter:

```
Window 0:
T:  [A  B  A] B  A  B  C
P:  [A  B  C]
Check: 'A'=='A', 'B'=='B', 'A'!='C'  -> Failed at index 2 (did 3 checks)

Window 1:
T:   A [B  A  B] A  B  C
P:     [A  B  C]
Check: 'B'!='A'                       -> Failed at index 1 (did 1 check)

Window 2:
T:   A  B [A  B  A] B  C
P:        [A  B  C]
Check: 'A'=='A', 'B'=='B', 'A'!='C'  -> Failed at index 4 (did 3 checks)
...

```

For each window, we might compare up to $M$ characters.
There are about $(N - M + 1)$ windows.

**Worst-Case Scenario:**
Imagine this worst-case input:

* $T$ = `"A A A A A A A A A B"` ($N$ characters)
* $P$ = `"A A A B"` ($M$ characters)

At every single window, you compare almost all characters before realizing the last one doesn't match:

* Window 0: checks $M$ characters $\rightarrow$ mismatch at end
* Window 1: checks $M$ characters $\rightarrow$ mismatch at end
* Window 2: checks $M$ characters $\rightarrow$ mismatch at end

Total character comparisons = roughly $N \times M$.
If $N = 100{,}000$ and $M = 10{,}000$, $N \times M = 1{,}000{,}000{,}000$ (1 billion operations), which **Time Limit Exceeded (TLE)** on modern judges.

---

### The Core Question Rabin-Karp Asks

> *"Why are we spending up to $M$ operations comparing characters in a window, when in computers, comparing two integers takes just **$O(1)$ constant time**?"*

If two numbers are $456$ and $456$:

* CPU compares them in **1 CPU clock cycle** ($O(1)$).
* It does not check digit-by-digit.

So, the grand idea of Rabin-Karp is:

1. Turn the pattern $P$ into a single number (a **hash**).
2. As we slide across the text $T$, represent each window as a single number (its **hash**).
3. Compare the two numbers in **$O(1)$** time!

---

### The Big Catch (The "Rolling" Problem)

Converting a word of length $M$ into a number normally takes $O(M)$ time because you have to loop through all $M$ letters.

If every time we slide our window by 1 step we spend $O(M)$ time recalculating the number, we've saved nothing! It's still $O(N \times M)$.

**The magic of Rabin-Karp is:**
When the window shifts by 1 position:

* Exactly **one** old character leaves on the left.
* Exactly **one** new character enters on the right.
* Can we update the window's number from the old number in **$O(1)$** time, without re-reading the characters in between?

**Yes.** That mechanism is called a **Rolling Hash**.

---
### Part 3: From Digits to Strings & The Overflow Dilemma

Now that the math makes sense with decimal digits, let's translate this to strings.

---

### 1. Converting Characters into Numbers

In the base-10 example, our digits came from the alphabet of digits: $\{0, 1, 2, \dots, 9\}$ (10 possible symbols, so Base = $10$).

In text, our characters come from an alphabet.

* If your string consists only of lowercase English letters (`'a'` to `'z'`), there are 26 letters.
* You can map `'a' \to 1, 'b' \to 2, \dots, 'z' \to 26$ (or use $0 \dots 25$).
* A natural base choice is any number $\ge 26$ (e.g., $B = 31$).


* If your string has standard ASCII characters, there are 128 (or 256) characters.
* You can directly use their ASCII codes: `'A' = 65, 'B' = 66$, etc.
* A common base choice is $B = 256$ (or a prime number like $31, 53$, or $911382$).



#### Let's Trace a Small Example:

Suppose we use:

* Base $B = 10$ (just to keep calculations simple for now).
* Letters mapped to numbers: `'a' = 1, 'b' = 2, 'c' = 3, 'd' = 4`.

Suppose we want to hash `"abc"` ($M = 3$):


$$\text{hash}(\text{"abc"}) = 1 \times 10^2 + 2 \times 10^1 + 3 \times 10^0 = 123$$

If the text is `"abcd"` and the window shifts from `"abc"` to `"bcd"`:

* Outgoing character: `'a'` ($1$)
* Incoming character: `'d'` ($4$)

Using our formula:


$$\text{New Hash} = (123 - 1 \times 10^2) \times 10 + 4 = (23) \times 10 + 4 = 234$$


`"bcd"` has digits $2, 3, 4 \rightarrow 234$. The exact same logic holds!

---

### 2. The Big Obstacle: Integer Overflow

In real programming, patterns are rarely 3 characters long. What if the pattern length $M = 100$, or $M = 1{,}000$?

Look at what happens to the number:


$$\text{Hash} = c_0 \times B^{M-1} + c_1 \times B^{M-2} + \dots + c_{M-1} \times B^0$$

Even if base $B = 31$:

* $31^{10} \approx 8.19 \times 10^{14}$
* $31^{15}$ already exceeds the capacity of a standard 64-bit integer (`long` in Java/C++ maxes out at $\approx 9 \times 10^{18}$).
* $31^{100}$ has over 140 digits!

Hardware registers cannot store numbers that large in native 64-bit CPU registers. If you let it overflow naturally, numbers wrap around unpredictably; if you use arbitrary-precision integers (like `BigInteger`), arithmetic operations cease to be $O(1)$!

---

### 3. The Solution: Modulo Arithmetic ($\% \text{ MOD}$)

To keep the numbers bounded within regular integer/long ranges so all operations remain hardware-level $O(1)$, we compute everything **modulo a large prime number ($MOD$)**.

Typically, programmers use a large prime like:


$$MOD = 10^9 + 7 \quad \text{or} \quad MOD = 10^9 + 9$$

Now, every single arithmetic step is wrapped in $\% \text{ MOD}$:

* All hash values are guaranteed to stay strictly between $0$ and $MOD - 1$.
* They fit cleanly in a standard 64-bit integer, and every addition/multiplication takes $O(1)$ time.

---

### The New Challenge: "Spurious Hits" (Collisions)

Once we introduce modulo arithmetic:

* Before modulo: Every unique string had a unique number.
* After modulo: Since there are infinitely many strings, but only $MOD$ possible hash values ($0$ to $MOD-1$), by the **Pigeonhole Principle**, two completely different strings **can** yield the exact same hash!

This is called a **Hash Collision** (or a **Spurious Hit**):

* If $\text{Hash}(A) \ne \text{Hash}(B) \implies$ $A$ and $B$ are **100% definitely different**. (Zero doubt)
* If $\text{Hash}(A) == \text{Hash}(B) \implies$ $A$ and $B$ are **probably the same**, but there is a tiny chance they just collided modulo $MOD$.

Because of this, whenever Rabin-Karp sees $\text{Hash}(\text{window}) == \text{Hash}(\text{pattern})$, it performs a quick character-by-character check to verify if it's a real match or a collision.

---

### Part 4: The Modulo Formula & The Negative Number Trap

Now we combine everything: characters, base powers, and modulo arithmetic into the exact formula used in production code.

---

### The Precomputation: High Power ($H$)

Remember the rolling formula:


$$\text{New Hash} = \left(\text{Old Hash} - \text{outgoing} \times B^{M-1}\right) \times B + \text{incoming}$$

The factor $B^{M-1}$ is needed on every single roll. Let's call it $H$:


$$H = B^{M-1} \pmod{MOD}$$

Instead of computing this with `Math.pow` (which overflows), we compute it iteratively with modulo:

```java
long H = 1;
for (int i = 0; i < M - 1; i++) {
    H = (H * BASE) % MOD;
}

```

---

### The Modulo Rolling Hash Step

When rolling from one window to the next:

1. **Subtract outgoing character contribution:**

$$\text{removed} = \text{Old Hash} - (\text{outgoing} \times H) \pmod{MOD}$$


2. **The Negative Number Trap (Crucial!):**
In Java, C++, and many languages, the `%` operator on a negative number yields a negative result:

$$-5 \pmod{7} \rightarrow -5 \quad (\text{not } +2)$$


If $\text{Old Hash} < (\text{outgoing} \times H) \pmod{MOD}$, the subtraction becomes negative.
To fix this, **always add $MOD$ before taking modulo again**:

$$\text{removed} = (\text{Old Hash} - (\text{outgoing} \times H) \pmod{MOD} + MOD) \pmod{MOD}$$


3. **Shift left by $B$ and add incoming character:**

$$\text{New Hash} = (\text{removed} \times B + \text{incoming}) \pmod{MOD}$$



---

### Step-by-Step Hand Dry Run

Let's pick small, friendly numbers to trace every calculation by hand:

* **Base $B = 10$**
* **$MOD = 13$** (small prime to see modulo in action)
* Mapping: `'a'=1, 'b'=2, 'c'=3, 'd'=4`
* **Pattern $P = \text{"bc"}$** $\implies M = 2$
* **Text $T = \text{"abcd"}$** $\implies N = 4$

#### Step 1: Precompute $H = B^{M-1} \pmod{MOD}$

$$H = 10^{2-1} \pmod{13} = 10^1 \pmod{13} = 10$$

---

#### Step 2: Compute Pattern Hash ($P = \text{"bc"}$)

Characters are `2` (`'b'`) and `3` (`'c'`).

* Start: $0$
* Add `'b'`: $(0 \times 10 + 2) \pmod{13} = 2$
* Add `'c'`: $(2 \times 10 + 3) \pmod{13} = 23 \pmod{13} = 10$

$$\text{Target Pattern Hash} = 10$$

---

#### Step 3: Compute First Window Hash in Text ($T[0\dots 1] = \text{"ab"}$)

Characters are `1` (`'a'`) and `2` (`'b'`).

* Start: $0$
* Add `'a'`: $(0 \times 10 + 1) \pmod{13} = 1$
* Add `'b'`: $(1 \times 10 + 2) \pmod{13} = 12 \pmod{13} = 12$

$$\text{Window 0 Hash} = 12$$

**Comparison:**
$\text{Window 0 Hash } (12) \ne \text{Pattern Hash } (10) \implies$ **No match at index 0.**

---

#### Step 4: Roll to Window 1 ($T[1\dots 2] = \text{"bc"}$)

* Outgoing character: `'a'` ($1$)
* Incoming character: `'c'` ($3$)
* Current window hash: $12$

Apply the formula:

1. $\text{outgoing} \times H = 1 \times 10 = 10$
2. Subtract: $12 - 10 = 2$ (positive, so no wrap-around needed)
3. Shift and add incoming:

$$(2 \times 10 + 3) \pmod{13} = 23 \pmod{13} = 10$$



$$\text{Window 1 Hash} = 10$$

**Comparison:**
$\text{Window 1 Hash } (10) == \text{Pattern Hash } (10)$.

* The hashes match!
* Verify actual characters: $T[1\dots 2] = \text{"bc"}$, Pattern = $\text{"bc"}$.
* **Exact match found at index 1!**

---

#### Step 5: Roll to Window 2 ($T[2\dots 3] = \text{"cd"}$)

* Outgoing character: `'b'` ($2$)
* Incoming character: `'d'` ($4$)
* Current window hash: $10$

Apply the formula:

1. $\text{outgoing} \times H = 2 \times 10 = 20 \pmod{13} = 7$
2. Subtract: $10 - 7 = 3$
3. Shift and add incoming:

$$(3 \times 10 + 4) \pmod{13} = 34 \pmod{13} = 8$$



$$\text{Window 2 Hash} = 8$$

**Comparison:**
$\text{Window 2 Hash } (8) \ne \text{Pattern Hash } (10) \implies$ **No match at index 2.**

Search complete. Matches found: `[1]`.

---
### Part 5: Complete Java Implementation & Code Breakdown

Here is the clean, production-ready Java implementation of the Rabin-Karp algorithm.

---

### Java Code

```java
import java.util.ArrayList;
import java.util.List;

public class RabinKarp {

    // Base: 256 covers the full extended ASCII character set
    private static final long BASE = 256;
    // Modulo: A large prime to minimize hash collisions
    private static final long MOD = 1_000_000_007L;

    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();

        // Edge case: pattern is longer than the text or empty
        if (m == 0 || m > n) {
            return matches;
        }

        // Step 1: Precompute H = (BASE^(m - 1)) % MOD
        long H = 1;
        for (int i = 0; i < m - 1; i++) {
            H = (H * BASE) % MOD;
        }

        // Step 2: Compute initial hashes for pattern and the first text window
        long patternHash = 0;
        long windowHash = 0;

        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + text.charAt(i)) % MOD; // using pattern characters for patternHash
            // Correction: use pattern.charAt(i) for pattern, text.charAt(i) for text
        }
        
        // Re-writing cleanly to avoid any confusion:
        patternHash = 0;
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + pattern.charAt(i)) % MOD;
            windowHash = (windowHash * BASE + text.charAt(i)) % MOD;
        }

        // Step 3: Slide the window across the text
        for (int i = 0; i <= n - m; i++) {

            // Check if hash matches
            if (windowHash == patternHash) {
                // Verification step to guard against hash collisions (spurious hits)
                if (text.startsWith(pattern, i)) {
                    matches.add(i);
                }
            }

            // Roll to the next window (if we are not at the last window)
            if (i < n - m) {
                char outgoing = text.charAt(i);
                char incoming = text.charAt(i + m);

                // 1. Remove outgoing character's high-order value
                long removed = (windowHash - (outgoing * H) % MOD) % MOD;

                // 2. Handle negative results from modulo arithmetic
                if (removed < 0) {
                    removed += MOD;
                }

                // 3. Shift left and add incoming character
                windowHash = (removed * BASE + incoming) % MOD;
            }
        }

        return matches;
    }

    public static void main(String[] args) {
        String text = "ababcabcab";
        String pattern = "abc";

        List<Integer> occurrences = search(text, pattern);
        System.out.println("Pattern found at indices: " + occurrences);
        // Output: Pattern found at indices: [2, 5]
    }
}

```

---

### Step-by-Step Code Walkthrough

#### 1. Constants & Why `long`?

```java
private static final long BASE = 256;
private static final long MOD = 1_000_000_007L;

```

* In Java, character values go up to `255` for standard ASCII. When multiplying `windowHash * BASE`, the value can reach up to $\approx 10^9 \times 256 \approx 2.56 \times 10^{11}$.
* A 32-bit `int` maxes out at $\approx 2.14 \times 10^9$, so doing this in `int` would overflow before modulo can catch it. Using `long` (max $\approx 9 \times 10^{18}$) prevents integer overflow.

#### 2. Precomputing $H = \text{BASE}^{M-1} \pmod{MOD}$

```java
long H = 1;
for (int i = 0; i < m - 1; i++) {
    H = (H * BASE) % MOD;
}

```

* This gives the place-value multiplier for the oldest character in the window. We only need to calculate it once.

#### 3. Sliding and Rolling the Window

```java
if (i < n - m) {
    char outgoing = text.charAt(i);
    char incoming = text.charAt(i + m);

    long removed = (windowHash - (outgoing * H) % MOD) % MOD;
    if (removed < 0) {
        removed += MOD;
    }
    windowHash = (removed * BASE + incoming) % MOD;
}

```

* `outgoing` is at index `i` (the left edge leaving).
* `incoming` is at index `i + m` (the new right edge entering).
* Notice the `if (removed < 0) removed += MOD;`. This single check fixes Java's negative remainder quirk.

#### 4. The Collision Defense

```java
if (windowHash == patternHash) {
    if (text.startsWith(pattern, i)) {
        matches.add(i);
    }
}

```

* Even with a good prime like $10^9 + 7$, collisions are theoretically possible. Verifying with `text.startsWith(pattern, i)` guarantees 100% correctness without changing the average $O(N + M)$ runtime.

---

### Summary of What You Just Mastered

1. **The Core Intuition:** Compare hashes in $O(1)$ instead of characters in $O(M)$.
2. **The Rolling Math:** Shift a base-number window by dropping the high-order digit and adding the low-order digit.
3. **Modulo Arithmetic:** Use a prime $MOD$ to prevent overflow, and handle negative wrap-arounds.
4. **Collision Handling:** Check the actual string when hashes match.

---

### Part 6: The Advanced Variation — The Prefix Hash Array

In standard Rabin-Karp, we maintain a **single sliding window** of a **fixed size $M$**.

That works great if you only want to search for one pattern of known length. But what if a problem asks:

* *"Are substrings `s[2...8]` and `s[10...16]` equal?"*
* *"Is `s[1...4]` equal to `s[15...18]`?"*
* *"Find the longest duplicate substring across all possible lengths?"*

If you have to re-run the sliding window for every length or pair of indices, you're back to being slow.

The **Prefix Hash Array** solves this completely: **Precompute once in $O(N)$, and query the hash of ANY substring $[L\dots R]$ in $O(1)$ time.**

---

### The Analogy: Prefix Sums vs. Prefix Hashes

You already know standard prefix sums:
If `nums = [3, 1, 4, 1, 5]`:

* `prefixSum[i]` stores the sum from index $0$ to $i-1$.
* To find the sum of range $[L\dots R]$, you do:

$$\text{sum}(L\dots R) = \text{prefixSum}[R + 1] - \text{prefixSum}[L]$$



Can we do the exact same thing with string hashes? **Yes, with one twist: place values (powers of base).**

---

### The Math: Why We Need Power Alignment

Let's represent a string as polynomial terms:
For string `s = "c a t s"` with base $B$:

| Prefix | Formula |
| --- | --- |
| `s[0]` (`"c"`) | $c$ |
| `s[0...1]` (`"ca"`) | $c \cdot B + a$ |
| `s[0...2]` (`"cat"`) | $c \cdot B^2 + a \cdot B + t$ |
| `s[0...3]` (`"cats"`) | $c \cdot B^3 + a \cdot B^2 + t \cdot B + s$ |

Now, suppose you want the hash of the substring **`"at"`** (`s[1...2]`):
What should `"at"` look like?


$$\text{hash}("at") = a \cdot B + t$$

Look at prefix `s[0...2]` (`"cat"`) and prefix `s[0...0]` (`"c"`):

* $\text{prefixHash}(2) = c \cdot B^2 + a \cdot B + t$
* $\text{prefixHash}(0) = c$

If you just subtract them like a normal prefix sum:


$$\text{prefixHash}(2) - \text{prefixHash}(0) = (c \cdot B^2 + a \cdot B + t) - c$$


The $c$ does **not** cancel out because $c$ has a factor of $B^2$ in the longer prefix!

To cancel out the prefix before index $1$, we must **shift the prefix by multiplying it by $B^{\text{length}}$**:


$$\text{length of "at"} = 2$$

$$\text{shifted prefix} = \text{prefixHash}(0) \times B^2 = c \cdot B^2$$

Now subtract:


$$(c \cdot B^2 + a \cdot B + t) - (c \cdot B^2) = a \cdot B + t = \text{hash}("at") \quad \text{!}$$

---

### The Universal Substring Formula

For any substring from index $L$ to index $R$ (length = $R - L + 1$):

$$\text{hash}(L\dots R) = \Big( \text{prefixHash}[R + 1] - \left(\text{prefixHash}[L] \times B^{\text{length}}\right) \Big) \pmod{MOD}$$

To make this query $O(1)$, we precompute two arrays up to size $N + 1$:

1. `hash[i]`: Hash of prefix of length $i$.
2. `power[i]`: $B^i \pmod{MOD}$.

---

### Hand Dry Run of the Array Construction

String $S = \text{"banana"}$, $B = 10$, $MOD = 1009$ (small values for tracing).
Mapping: `'a'=1, 'b'=2, 'n'=3`.

* `power[0] = 1`
* `power[1] = 10`
* `power[2] = 100`
* `power[3] = 1000`

Prefix Hashes:

* `hash[0] = 0`
* `hash[1]` (`"b"`): $(0 \times 10 + 2) = 2$
* `hash[2]` (`"ba"`): $(2 \times 10 + 1) = 21$
* `hash[3]` (`"ban"`): $(21 \times 10 + 3) = 213$
* `hash[4]` (`"bana"`): $(213 \times 10 + 1) = 2131 \pmod{1009} = 113$

Suppose we want the hash of substring from index $1$ to $3$ (`"ana"`):

* Substring length = $3 - 1 + 1 = 3$.
* Look up `hash[4]` (prefix `"bana"`) = $2131$.
* Look up `hash[1]` (prefix `"b"`) = $2$.
* Look up `power[3]` = $10^3 = 1000$.

Calculate:


$$\text{hash}(1\dots 3) = 2131 - (2 \times 1000) = 2131 - 2000 = 131$$


Does `"ana"` have hash $131$?

* `'a'=1, 'n'=3, 'a'=1 \rightarrow 1 \cdot 10^2 + 3 \cdot 10^1 + 1 = 131$.
It matches without looking at the characters.

---

### Clean Java Implementation: `StringHash` Class

This is the exact reusable data structure used in competitive programming and interview problems:

```java
public class StringHash {
    private static final long BASE = 911382L;
    private static final long MOD = 1_000_000_007L;

    private final long[] hash;
    private final long[] power;

    public StringHash(String s) {
        int n = s.length();
        hash = new long[n + 1];
        power = new long[n + 1];

        power[0] = 1;
        hash[0] = 0;

        for (int i = 0; i < n; i++) {
            power[i + 1] = (power[i] * BASE) % MOD;
            hash[i + 1] = (hash[i] * BASE + s.charAt(i)) % MOD;
        }
    }

    // Returns hash of substring s[left ... right] (inclusive) in O(1)
    public long getHash(int left, int right) {
        int len = right - left + 1;
        long h = (hash[right + 1] - (hash[left] * power[len]) % MOD) % MOD;
        
        // Handle negative result
        if (h < 0) {
            h += MOD;
        }
        return h;
    }
}

```

---

### Why the Article Used "Double Hashing"

In the snippet from your first question, you saw:

* `MOD1 = 1000000007L`
* `MOD2 = 1000000009L`

Why did they compute two hashes for every substring?

* With **one modulo** ($10^9+7$), if you compare $10^5$ substrings, the chance of a collision via the Birthday Paradox is roughly $0.5\%$. Small, but it can fail test cases on LeetCode/Codeforces.
* With **double hashing**, a hash is a pair of numbers: `(hash1, hash2)`.
Two strings only match if **both** hashes match.
The chance of collision drops to $\frac{1}{10^9 \times 10^9} = \frac{1}{10^{18}}$, which is virtually impossible.

---

You now understand:

1. Standard Sliding Window Rabin-Karp ($O(1)$ space, fixed window).
2. The Prefix Hash Array ($O(N)$ space, arbitrary $[L\dots R]$ queries in $O(1)$).
3. Why Double Hashing eliminates collisions.

