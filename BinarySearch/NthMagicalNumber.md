**Yes, exactly!** It uses the exact same core concept as **Ugly Number III (LC 1201)**: **Binary Search on Answer + Principle of Inclusion-Exclusion (PIE)**.

Because there are only 2 numbers ($a$ and $b$) instead of 3, the math is even simpler.

---

### 1. The Inclusion-Exclusion Formula

For any candidate number $x$, the total count of numbers $\le x$ divisible by $a$ or $b$ is:

$$\text{count}(x) = \lfloor \frac{x}{a} \rfloor + \lfloor \frac{x}{b} \rfloor - \lfloor \frac{x}{\text{lcm}(a, b)} \rfloor$$

Where:

* $\lfloor \frac{x}{a} \rfloor$: numbers divisible by $a$
* $\lfloor \frac{x}{b} \rfloor$: numbers divisible by $b$
* $\lfloor \frac{x}{\text{lcm}(a, b)} \rfloor$: common multiples (divisible by both $a$ and $b$) subtracted once so they are not double-counted
* $\text{lcm}(a, b) = \frac{a \times b}{\gcd(a, b)}$

---

### 2. Binary Search Condition: `>= n` vs `<= n`

To find the **$n$-th** magical number:

* As $x$ increases, $\text{count}(x)$ **increases monotonically**.
* We want the smallest $x$ such that $\text{count}(x) \ge n$.

```java
if (count >= n) {
    high = mid;      // Mid could be the answer, try to find a smaller valid one
} else {
    low = mid + 1;   // Not enough magical numbers, need a larger mid
}

```

---

### 3. Java Implementation

```java
class Solution {
    public int nthMagicalNumber(int n, int a, int b) {
        long MOD = 1_000_000_007;

        long lcm = ((long) a * b) / gcd(a, b);

        long low = Math.min(a, b);
        long high = (long) n * Math.min(a, b);

        while (low < high) {
            long mid = low + (high - low) / 2;

            // Principle of Inclusion-Exclusion
            long count = (mid / a) + (mid / b) - (mid / lcm);

            if (count >= n) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return (int) (low % MOD);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

```

---

### Key Takeaways & Edge Cases

* **Search Boundaries:**
* $\text{low} = \min(a, b)$
* $\text{high} = n \times \min(a, b)$ (the $n$-th multiple of $\min(a, b)$ is guaranteed to have $\ge n$ magical numbers).


* **64-bit Integers (`long`):**
* Maximum possible value of $\text{high} = 10^9 \times 4 \cdot 10^4 = 4 \times 10^{13}$, which exceeds 32-bit `int` limits. Always use `long` inside the binary search and apply `% (10^9 + 7)` only at the end.