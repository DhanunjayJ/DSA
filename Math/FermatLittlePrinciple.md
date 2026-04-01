# Fermat's Little Theorem

## Statement

If **p is a prime number** and **a is any integer not divisible by p**, then:

$$a^{p-1} \equiv 1 \pmod{p}$$

Equivalently: **p divides (aᵖ⁻¹ − 1)**

Another common form: for **any integer a**:

$$a^p \equiv a \pmod{p}$$

---

## Intuition First

Think of it this way — if you keep multiplying a number by itself and take remainders mod a prime, the remainders eventually **cycle back to 1** after exactly **(p−1)** steps. Primes have this magical "reset" property that composites don't.

---

## Concrete Example

Let **a = 3**, **p = 7**

Compute 3⁶ mod 7:

| Power | Value | mod 7 |
|-------|-------|-------|
| 3¹ | 3 | 3 |
| 3² | 9 | 2 |
| 3³ | 27 | 6 |
| 3⁴ | 81 | 4 |
| 3⁵ | 243 | 5 |
| 3⁶ | 729 | **1** ✅ |

3⁶ mod 7 = 1. Theorem holds!

---

## Proof (via Necklace / Multiplying Sets)

Consider the set **S = {1, 2, 3, ..., p−1}** (all non-zero residues mod p).

Now multiply every element by **a mod p** → new set **S' = {a, 2a, 3a, ..., (p−1)a} mod p**

**Key claim:** S' is just a rearrangement of S.

Why? Because:
- None of the elements in S' are 0 (since p is prime and p ∤ a)
- All elements are distinct (if ia ≡ ja mod p, then i ≡ j mod p)

So S and S' contain the **same elements**, just reordered. Therefore their products are equal:

$$1 \cdot 2 \cdot 3 \cdots (p{-}1) \equiv a \cdot 2a \cdot 3a \cdots (p{-}1)a \pmod{p}$$

$$(p-1)! \equiv a^{p-1} \cdot (p-1)! \pmod{p}$$

Since gcd((p−1)!, p) = 1, we can cancel (p−1)! from both sides:

$$\boxed{a^{p-1} \equiv 1 \pmod{p}}$$

---

## Why "Little" Theorem?

Fermat also has a **"Last" Theorem** (aⁿ + bⁿ = cⁿ has no integer solutions for n > 2). This theorem is called "little" just to distinguish it — it's simpler but incredibly powerful in practice.

---

## Important Limitations

| Condition | Why it matters |
|-----------|---------------|
| p must be **prime** | Fails for composites (leads to Carmichael numbers — pseudoprimes) |
| a must **not be divisible by p** | If p \| a, then aᵖ⁻¹ ≡ 0, not 1 |

---

## DSA / Competitive Programming Applications

This is where it gets **directly useful for you**:

**1. Modular Inverse**
To find a⁻¹ mod p (inverse of a under prime modulus):
```
a⁻¹ ≡ a^(p−2) mod p
```
Used in: nCr mod p, fraction mod prime, etc.

**2. Fast Modular Exponentiation**
Combined with binary exponentiation, compute aᵖ⁻² in **O(log p)** time.

```java
long modInverse(long a, long p) {
    return modPow(a, p - 2, p);
}

long modPow(long base, long exp, long mod) {
    long result = 1;
    base %= mod;
    while (exp > 0) {
        if ((exp & 1) == 1) result = result * base % mod;
        base = base * base % mod;
        exp >>= 1;
    }
    return result;
}
```

**3. Primality Testing (Fermat Test)**
If aᵖ⁻¹ mod p ≠ 1 for some a, then p is definitely **not prime**. (Note: not foolproof due to Carmichael numbers — Miller-Rabin is stronger.)

**4. nCr mod p** (combinatorics problems)
```
C(n, r) mod p = n! * inverse(r!) * inverse((n-r)!) mod p
```

# Modular Inverse using Fermat's Little Theorem

## What is Modular Inverse?

The modular inverse of **a** under modulus **m** is a number **x** such that:

$$a \cdot x \equiv 1 \pmod{m}$$

Just like in normal math: 5 × (1/5) = 1
In modular math: a × a⁻¹ ≡ 1 (mod m)

**Real analogy:** In normal division, 10 / 2 = 5.
In modular math, "dividing by 2 mod 7" means multiplying by 2's inverse mod 7.

---

## When Does Inverse Exist?

| Condition | Inverse Exists? |
|-----------|----------------|
| gcd(a, m) = 1 (a and m are coprime) | ✅ Yes |
| m is **prime** and a % m ≠ 0 | ✅ Yes (use Fermat) |
| gcd(a, m) ≠ 1 | ❌ No |

---

## The Formula (from Fermat's Little Theorem)

From FLT, when **p is prime**:

$$a^{p-1} \equiv 1 \pmod{p}$$

Rewrite it:

$$a \cdot a^{p-2} \equiv 1 \pmod{p}$$

So by definition of modular inverse:

$$\boxed{a^{-1} \equiv a^{p-2} \pmod{p}}$$

**That's it.** To find inverse of a mod p → compute **a^(p−2) mod p**

---

## Step-by-Step Example

**Find inverse of 3 mod 7**

- a = 3, p = 7
- Formula: 3⁷⁻² mod 7 = **3⁵ mod 7**

Compute 3⁵:
```
3¹ = 3
3² = 9   → 9 mod 7 = 2
3³ = 3² × 3 = 2 × 3 = 6 mod 7
3⁴ = 3³ × 3 = 6 × 3 = 18 → 18 mod 7 = 4
3⁵ = 3⁴ × 3 = 4 × 3 = 12 → 12 mod 7 = 5
```

So **3⁻¹ mod 7 = 5**

**Verify:** 3 × 5 = 15 → 15 mod 7 = **1** ✅

---

## Another Example — Division mod prime

**Compute (10 / 4) mod 7**

Direct division doesn't work in modular math. Instead:

```
Step 1: Find inverse of 4 mod 7
        4^(7−2) mod 7 = 4⁵ mod 7

4¹ = 4
4² = 16 mod 7 = 2
4³ = 2 × 4 = 8 mod 7 = 1
4⁴ = 1 × 4 = 4
4⁵ = 4 × 4 = 16 mod 7 = 2

→ inverse of 4 mod 7 = 2

Step 2: (10 / 4) mod 7 = (10 × 2) mod 7
                       = 20 mod 7
                       = 6
```

**Verify:** 4 × 6 = 24 mod 7 = **3**, and 10 mod 7 = 3 ✅

---

## Efficient Computation — Binary Exponentiation

Computing a^(p−2) naively is slow for large p. Use **fast power** in O(log p):

```java
// Modular Inverse using Fermat's Little Theorem
// Works only when mod is PRIME
static long modInverse(long a, long mod) {
    return modPow(a, mod - 2, mod);
}

// Fast exponentiation: computes (base^exp) % mod in O(log exp)
static long modPow(long base, long exp, long mod) {
    long result = 1;
    base = base % mod;

    while (exp > 0) {
        // If exp is odd, multiply base into result
        if ((exp & 1) == 1) {
            result = (result * base) % mod;
        }
        exp = exp >> 1;        // exp = exp / 2
        base = (base * base) % mod;  // square the base
    }
    return result;
}

// Example usage
public static void main(String[] args) {
    long a = 3, p = 7;
    System.out.println(modInverse(a, p)); // Output: 5
}
```

---

## Real CP Use Case — nCr mod p

This is the **most common** place you'll use modular inverse in contests:

$$C(n, r) = \frac{n!}{r! \cdot (n-r)!}$$

In modular arithmetic (can't divide directly):

$$C(n, r) \mod p = n! \cdot (r!)^{-1} \cdot ((n-r)!)^{-1} \mod p$$

```java
static final long MOD = 1_000_000_007L;

static long nCr(int n, int r) {
    if (r > n) return 0;
    long num = factorial(n);
    long den = (modInverse(factorial(r), MOD) * modInverse(factorial(n - r), MOD)) % MOD;
    return (num * den) % MOD;
}

static long factorial(int n) {
    long result = 1;
    for (int i = 2; i <= n; i++)
        result = (result * i) % MOD;
    return result;
}
```

---

## Summary

| Step | Action |
|------|--------|
| 1 | Check p is prime |
| 2 | Formula: inverse = a^(p−2) mod p |
| 3 | Compute using binary exponentiation |
| 4 | Verify: a × inverse ≡ 1 mod p |

> **One-liner to remember:** *"Inverse of a mod prime p = a to the power (p−2) mod p"*

This comes up in **almost every combinatorics problem** in competitive programming — nail this and you'll never be stuck on modular division again.


# How We Get a⁻¹ = a^(p−2) mod p

## Start from Fermat's Little Theorem

We know FLT says:

$$a^{p-1} \equiv 1 \pmod{p}$$

That's our **starting point**. Now just do one algebraic split.

---

## The One Key Step

Split the exponent **(p−1)** as **(1 + (p−2))**:

$$a^{p-1} = a^{1 + (p-2)} = a^1 \cdot a^{p-2}$$

So FLT becomes:

$$a^1 \cdot a^{p-2} \equiv 1 \pmod{p}$$

$$a \cdot a^{p-2} \equiv 1 \pmod{p}$$

---

## Now Compare with the Definition

The definition of modular inverse says:

$$a \cdot x \equiv 1 \pmod{p}$$

You just found:

$$a \cdot a^{p-2} \equiv 1 \pmod{p}$$

**Both are the same form.** So directly:

$$\boxed{x = a^{p-2}}$$

$$\boxed{a^{-1} \equiv a^{p-2} \pmod{p}}$$

---

## One-Line Intuition

> FLT gives you **a × (something) = 1**.
> That "something" is **a^(p−2)**.
> And anything that satisfies **a × x = 1** is by definition the **inverse of a**.

That's the entire derivation. No magic — just splitting the exponent and matching with the definition.