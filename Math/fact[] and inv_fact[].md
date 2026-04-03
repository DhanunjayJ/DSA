# Precomputing fact[] and inv_fact[] for Fast nCr Queries

## Why Precompute?

If you compute nCr **naively** every time:
- Each query = O(n) for factorial + O(log p) for inverse
- 10⁵ queries × O(n) = **TLE** in contests

If you **precompute** once:
- Build fact[] and inv_fact[] once = O(n log n)
- Each query = **O(1)** ✅

---

## What are these two arrays?

```
fact[i]     = i! mod p          → precomputed factorials
inv_fact[i] = (i!)⁻¹ mod p     → precomputed inverse factorials
```

Once you have both, nCr becomes:

$$C(n,r) = fact[n] \times inv\_fact[r] \times inv\_fact[n-r] \pmod{p}$$

---

## Step 1 — Build fact[] (Easy)

Just multiply one by one:

```
fact[0] = 1
fact[1] = 1
fact[2] = 2
fact[3] = 6
fact[4] = 24
fact[5] = 120
...
fact[i] = fact[i-1] * i  (mod p)
```

```java
static final int MOD = 1_000_000_007;
static final int MAXN = 100001;
static long[] fact = new long[MAXN];

static void buildFactorial() {
    fact[0] = 1;
    for (int i = 1; i < MAXN; i++) {
        fact[i] = fact[i - 1] * i % MOD;
    }
}
```

---

## Step 2 — Build inv_fact[] (The Smart Part)

**Naive approach (slow):** call modPow(fact[i], MOD-2, MOD) for every i → O(n log p)

**Smart approach:** compute only **one** inverse, then go backwards.

### The Key Trick

We know:

$$inv\_fact[n] = (n!)^{-1} \pmod{p}$$

And we also know:

$$n! = (n-1)! \times n$$

So taking inverse on both sides:

$$(n!)^{-1} = ((n-1)!)^{-1} \times n^{-1}$$

$$inv\_fact[n] = inv\_fact[n-1] \times inv(n)$$

But there's an **even cleaner** relation:

$$inv\_fact[i] = inv\_fact[i+1] \times (i+1)$$

### Why does that work?

```
inv_fact[i+1] = 1 / (i+1)!
inv_fact[i+1] × (i+1) = (i+1) / (i+1)! = 1 / i! = inv_fact[i]  ✅
```

So you just need:
1. Compute `inv_fact[MAXN-1]` using one modPow call
2. Fill backwards using the relation above

```java
static long[] inv_fact = new long[MAXN];

static void buildInvFactorial() {
    // One modPow call only
    inv_fact[MAXN - 1] = modPow(fact[MAXN - 1], MOD - 2, MOD);

    // Fill backwards — O(n), no modPow needed
    for (int i = MAXN - 2; i >= 0; i--) {
        inv_fact[i] = inv_fact[i + 1] * (i + 1) % MOD;
    }
}
```

---

## Step 3 — nCr Query in O(1)

```java
static long nCr(int n, int r) {
    if (r < 0 || r > n) return 0;
    return fact[n] * inv_fact[r] % MOD * inv_fact[n - r] % MOD;
}
```

That's it. Three array lookups and two multiplications. **O(1) per query.**

---

## Full Working Code

```java
import java.util.*;

public class FastNCR {

    static final int MOD  = 1_000_000_007;
    static final int MAXN = 100_001;

    static long[] fact     = new long[MAXN];
    static long[] inv_fact = new long[MAXN];

    // Build both arrays — call this once at the start
    static void precompute() {
        fact[0] = 1;
        for (int i = 1; i < MAXN; i++)
            fact[i] = fact[i - 1] * i % MOD;

        inv_fact[MAXN - 1] = modPow(fact[MAXN - 1], MOD - 2, MOD);
        for (int i = MAXN - 2; i >= 0; i--)
            inv_fact[i] = inv_fact[i + 1] * (i + 1) % MOD;
    }

    // O(1) nCr query
    static long nCr(int n, int r) {
        if (r < 0 || r > n) return 0;
        return fact[n] * inv_fact[r] % MOD * inv_fact[n - r] % MOD;
    }

    // Fast power — O(log exp)
    static long modPow(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return result;
    }

    public static void main(String[] args) {
        precompute();
        System.out.println(nCr(5, 2));   // 10
        System.out.println(nCr(10, 3));  // 120
        System.out.println(nCr(6, 6));   // 1
        System.out.println(nCr(100000, 50000)); // huge number mod 1e9+7
    }
}
```

---

## Dry Run — Small Example (p = 7, MAXN = 6)

**fact[] building:**
```
fact[0] = 1
fact[1] = 1
fact[2] = 2
fact[3] = 6
fact[4] = 24 mod 7 = 3
fact[5] = 3 × 5 = 15 mod 7 = 1
```

**inv_fact[] building:**
```
inv_fact[5] = modPow(1, 5, 7) = 1

inv_fact[4] = inv_fact[5] × 5 mod 7 = 1 × 5 = 5
inv_fact[3] = inv_fact[4] × 4 mod 7 = 5 × 4 = 20 mod 7 = 6
inv_fact[2] = inv_fact[3] × 3 mod 7 = 6 × 3 = 18 mod 7 = 4
inv_fact[1] = inv_fact[2] × 2 mod 7 = 4 × 2 = 8 mod 7 = 1
inv_fact[0] = inv_fact[1] × 1 mod 7 = 1
```

**Query C(5,2):**
```
fact[5] × inv_fact[2] × inv_fact[3]
= 1 × 4 × 6 mod 7
= 24 mod 7
= 3

Verify: C(5,2) = 10, and 10 mod 7 = 3 ✅
```

---

## Complexity Summary

| Step | Time | Space |
|------|------|-------|
| Build fact[] | O(n) | O(n) |
| Build inv_fact[] | O(n + log p) | O(n) |
| Each nCr query | **O(1)** | — |
| n queries total | **O(n)** | — |

---

## When to Use This Pattern

Use precomputed fact[] and inv_fact[] whenever you see:
- Multiple nCr queries in one problem
- Combinatorics with large n (up to 10⁶)
- Problems involving paths, distributions, binomial coefficients
- Keywords: **"number of ways"**, **"combinations"**, **"choose"**

> **Golden rule for CP:** Always precompute fact[] and inv_fact[] at the top of your solution whenever you smell combinatorics. It costs nothing and saves everything.



# How inv_fact[i] = inv_fact[i+1] × (i+1) is derived

## Start from the definition

By definition:

$$inv\_fact[i] = (i!)^{-1} \pmod{p}$$

$$inv\_fact[i+1] = ((i+1)!)^{-1} \pmod{p}$$

---

## Key relationship between factorials

You know that:

$$(i+1)! = i! \times (i+1)$$

Now take **modular inverse on both sides:**

$$((i+1)!)^{-1} = (i!)^{-1} \times (i+1)^{-1}$$

In array notation:

$$inv\_fact[i+1] = inv\_fact[i] \times inv(i+1)$$

---

## Now just rearrange

You have:

$$inv\_fact[i+1] = inv\_fact[i] \times inv(i+1)$$

Divide both sides by inv(i+1), which means **multiply both sides by (i+1)**:

$$inv\_fact[i+1] \times (i+1) = inv\_fact[i] \times inv(i+1) \times (i+1)$$

The right side: inv(i+1) × (i+1) = **1** (inverse cancels the number)

$$inv\_fact[i+1] \times (i+1) = inv\_fact[i] \times 1$$

$$\boxed{inv\_fact[i] = inv\_fact[i+1] \times (i+1)}$$

---

## Verify with a number

Let **p = 7**, check for **i = 3**:

```
inv_fact[3] = (3!)⁻¹ mod 7 = 6⁻¹ mod 7

Find inverse of 6 mod 7:
6 × x ≡ 1 mod 7 → x = 6   (since 6×6 = 36 mod 7 = 1)

So inv_fact[3] = 6
```

Now use the formula — we need inv_fact[4] first:
```
inv_fact[4] = (4!)⁻¹ mod 7 = (24 mod 7)⁻¹ = 3⁻¹ mod 7

3 × x ≡ 1 mod 7 → x = 5   (since 3×5 = 15 mod 7 = 1)

So inv_fact[4] = 5
```

Apply formula:
```
inv_fact[3] = inv_fact[4] × (3+1)
            = 5 × 4 mod 7
            = 20 mod 7
            = 6  ✅
```

Matches perfectly.

---

## Why this is powerful

Without this trick, you'd need to call `modPow` for **every single index** → O(n log p)

With this trick:
- Call `modPow` **once** for inv_fact[MAXN-1]
- Then fill **backwards** using just multiplication → O(n)

```
inv_fact[MAXN-1]  ← one modPow call here
inv_fact[MAXN-2]  = inv_fact[MAXN-1] × (MAXN-1)
inv_fact[MAXN-3]  = inv_fact[MAXN-2] × (MAXN-2)
...
inv_fact[0]       = inv_fact[1] × 1
```

> **One modPow. Then pure multiplication all the way down. That's the elegance.**