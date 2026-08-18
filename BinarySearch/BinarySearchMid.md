We write `int mid = low + (high - low) / 2;` instead of `int mid = (low + high) / 2;` to **prevent 32-bit signed integer overflow**.

Mathematically, both formulas are identical:


$$low + \frac{high - low}{2} = \frac{2 \cdot low + high - low}{2} = \frac{low + high}{2}$$

In computer memory with fixed-width integers, their behavior is very different.

---

### The Integer Overflow Problem

In most languages (Java, C, C++), a standard 32-bit signed integer has a maximum limit:

* **`Integer.MAX_VALUE`** = $2^{31} - 1 = \mathbf{2,147,483,647}$

Suppose you are performing binary search on a large array or search space where:

* `low` = $1,500,000,000$
* `high` = $2,000,000,000$

Using **`(low + high) / 2`**:

1. `low + high` = $3,500,000,000$
2. Because $3,500,000,000 > 2,147,483,647$, the 32-bit integer overflows into negative values (specifically, **$-794,967,296$**).
3. Dividing by 2 yields **$-397,483,648$**.
4. Accessing `arr[mid]` throws an `ArrayIndexOutOfBoundsException` or enters an infinite loop.

---

### Why `low + (high - low) / 2` Is Safe

Using **`low + (high - low) / 2`**:

1. `high - low` = $2,000,000,000 - 1,500,000,000 = 500,000,000$ (safe subtraction, well within limits).
2. $(high - low) / 2$ = $250,000,000$.
3. `low + 250,000,000` = $1,750,000,000$ (fits safely inside positive integer bounds).

---

### Alternative Safe Syntax

You can also use the unsigned right shift operator in languages like Java:

```java
int mid = (low + high) >>> 1;

```

`>>>` treats the 32-bit sum as an unsigned integer, so even if `low + high` produces a negative signed bit, shifting it right by 1 bit moves a `0` into the most significant bit, correctly giving $\lfloor(low + high) / 2\rfloor$.