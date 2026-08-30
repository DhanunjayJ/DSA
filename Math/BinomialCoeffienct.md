A **binomial coefficient** is simply a way of answering this question: *"How many ways can I choose a small group of items out of a larger group, where the order doesn’t matter?"*

In mathematics, you write it like this:


$$\binom{n}{k}$$


*(Read as **"$n$ choose $k$"**)*

---

### The Intuition with an Example

Imagine you have **4 friends** (let's call them A, B, C, and D), and you want to pick **2 of them** to go on a trip with you.

How many different pairs can you choose? Let's list them:

1. {A, B}
2. {A, C}
3. {A, D}
4. {B, C}
5. {B, D}
6. {C, D}

There are **6 different ways** to choose 2 friends out of 4.
Instead of writing them all out, you can calculate it instantly using the binomial coefficient formula: $\binom{4}{2} = 6$.

*(Note: Order doesn't matter here. Choosing {A, B} is the exact same thing as choosing {B, A}).*

---

### The Formula

To calculate $\binom{n}{k}$ mathematically, you use factorials ($!$):

$$\binom{n}{k} = \frac{n!}{k! \times (n - k)!}$$

Let’s test our example ($\binom{4}{2}$) using the formula:

* $n = 4$ (total items)
* $k = 2$ (items to choose)

$$\binom{4}{2} = \frac{4!}{2! \times (4 - 2)!} = \frac{4!}{2! \times 2!}$$

Expanding the factorials:


$$= \frac{4 \times 3 \times 2 \times 1}{(2 \times 1) \times (2 \times 1)} = \frac{24}{2 \times 2} = \frac{24}{4} = 6$$

It gives us 6, exactly as expected!

---

### Why Does This Matter for Catalan Numbers?

Earlier, we saw the direct Catalan formula:


$$C_n = \frac{1}{n+1} \mathbf{\binom{2n}{n}}$$

That $\binom{2n}{n}$ part is just a binomial coefficient! It represents choosing $n$ items out of a total of $2n$ items. In the context of valid parentheses or paths, it counts all the total possible combinations before we filter out the illegal/crossing ones.

---

Both approaches are doing the exact same mathematical trick, but they just handle the loop index differently! Let's break down why yours starts at `0` and mine started at `1`, and which one is better.

---

### The Difference in Loop Indexing

* **The Code You Shared (starts at `i = 0`):**
```java
for (int i = 0; i < k; ++i) {
    res *= (n - i);
    res /= (i + 1);
}

```


Here, `i` goes from `0` up to `k - 1`.
* When `i = 0`, it multiplies by `(n - 0)` and divides by `(0 + 1)` $\rightarrow$ dividing by `1`.
* When `i = 1`, it multiplies by `(n - 1)` and divides by `(1 + 1)` $\rightarrow$ dividing by `2`.


* **The Code I Shared (starts at `i = 1`):**
```java
for (int i = 1; i <= k; i++) {
    res = res * (n - i + 1);
    res = res / i;
}

```


Here, `i` goes from `1` up to `k`.
* When `i = 1`, it multiplies by `(n - 1 + 1)` = `n`, and divides by `1`.
* When `i = 2`, it multiplies by `(n - 2 + 1)` = `n - 1`, and divides by `2`.



---

### Which One Is Better?

* **Mathematically:** They are **100% identical**. They both multiply the exact same sequence of numbers in the numerator and divide by the exact same sequence in the denominator.
* **Readability / Preference:**
* The code you shared (`for (int i = 0; i < k; ++i)`) is actually the **classic GeeksforGeeks implementation**, which maps very cleanly to the terms being pulled off the factorial expansion.
* Starting from `i = 1` sometimes feels more natural to people because the divisor `i` matches standard counting (1, 2, 3...), but you have to adjust the numerator to `(n - i + 1)`.



Both ways are completely correct and run in **$O(k)$ time with $O(1)$ space**. Feel free to use the GFG version if it matches what you are used to seeing in tutorials!

---

To see the exact mathematical expression this loop is calculating, let's trace what happens step-by-step for each iteration of `i` when finding a binomial coefficient $\binom{n}{k}$:

### Step-by-Step Expansion of the Loop

If you expand the loop `for (int i = 0; i < k; ++i)`, it multiplies and divides sequentially:

1. **When `i = 0`:**

$$\text{res} = 1 \times \frac{n - 0}{0 + 1} = \frac{n}{1}$$


2. **When `i = 1`:**

$$\text{res} = \left(\frac{n}{1}\right) \times \frac{n - 1}{1 + 1} = \frac{n \times (n - 1)}{1 \times 2}$$


3. **When `i = 2`:**

$$\text{res} = \left(\frac{n(n-1)}{2}\right) \times \frac{n - 2}{2 + 1} = \frac{n \times (n - 1) \times (n - 2)}{1 \times 2 \times 3}$$


4. **... and so on, all the way until `i = k - 1`.**

---

### The Final Mathematical Equation

By the time the loop finishes after $k$ steps, the entire accumulation represents the expanded form of the combination formula **$\binom{n}{k}$**:

$$\text{res} = \frac{n \times (n - 1) \times (n - 2) \times \dots \times (n - k + 1)}{1 \times 2 \times 3 \times \dots \times k}$$

Which is just the expanded shorthand for:


$$\binom{n}{k} = \frac{n!}{k!(n - k)!}$$

*(Note: In the context of the Catalan function code you shared earlier, that function calls `binomialCoeff(2 * n, n)`, meaning it replaces $n$ with $2n$ and $k$ with $n$ to calculate $\binom{2n}{n}$.)*

---

That is a fantastic observation and a very common point of confusion! Let's clear up why the loop ends at `i = k - 1` even though the formula has $k$ terms.

The secret is **0-based indexing**.

### 1. Count vs. Index

In mathematics, when we say "there are $k$ terms in the product," we mean the **total quantity** of numbers we are multiplying together.

In code, our loop uses an index `i` to keep track of *how many steps* we have taken. Because computers start counting at `0`:

* Step 1 is when **`i = 0`**
* Step 2 is when **`i = 1`**
* Step 3 is when **`i = 2`**
* ...
* Step $k$ is when **`i = k - 1`**

Even though the loop stops *counting* when `i < k`, it executes a **total of $k$ times**.

---

### Let's see it with a real example ($k = 3$)

Suppose we want to calculate $\binom{n}{3}$, so $k = 3$.
The math formula says we need **3 terms** in the numerator:


$$n \times (n - 1) \times (n - 2)$$

Now let's trace the loop `for (int i = 0; i < k; ++i)` where $k = 3$:

* **`i = 0`**: It multiplies by `(n - 0)` $\rightarrow$ This is our **1st** term: $n$
* **`i = 1`**: It multiplies by `(n - 1)` $\rightarrow$ This is our **2nd** term: $(n - 1)$
* **`i = 2`**: It multiplies by `(n - 2)` $\rightarrow$ This is our **3rd** term: $(n - 2)$

The loop condition `i < k` (`i < 3`) stops right there because `i` has hit `2`. But notice that the code successfully executed **3 times** (for `i = 0`, `i = 1`, and `i = 2`), giving us all 3 terms required by the formula!

---

