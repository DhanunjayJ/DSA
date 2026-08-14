Let's trace this with a concrete example where a number is divisible by **all three** values $a$, $b$, and $c$.

---

## The Setup

Let:

* $a = 2$
* $b = 3$
* $c = 5$
* $x = 30$

We want to track what happens to the specific number **$30$** at every stage of the formula:

$$\text{Count}(x) = \left\lfloor\frac{x}{a}\right\rfloor + \left\lfloor\frac{x}{b}\right\rfloor + \left\lfloor\frac{x}{c}\right\rfloor - \left\lfloor\frac{x}{\text{lcm}(a,b)}\right\rfloor - \left\lfloor\frac{x}{\text{lcm}(b,c)}\right\rfloor - \left\lfloor\frac{x}{\text{lcm}(a,c)}\right\rfloor + \left\lfloor\frac{x}{\text{lcm}(a,b,c)}\right\rfloor$$

---

## Step-by-Step Breakdown for the Number $30$

### Step 1: Adding Singles ($\lfloor 30/2 \rfloor + \lfloor 30/3 \rfloor + \lfloor 30/5 \rfloor$)

* Multiples of $2$ up to $30$ ($\lfloor 30/2 \rfloor = 15$): includes **$30$** $\rightarrow$ ($+1$)
* Multiples of $3$ up to $30$ ($\lfloor 30/3 \rfloor = 10$): includes **$30$** $\rightarrow$ ($+1$)
* Multiples of $5$ up to $30$ ($\lfloor 30/5 \rfloor = 6$): includes **$30$** $\rightarrow$ ($+1$)

**Running count for the number $30$:**

$$1 + 1 + 1 = +3$$

The number $30$ is currently counted **$3$ times**.

---

### Step 2: Subtracting Pairwise LCMs

Now we subtract multiples of pair combinations:

* $\text{lcm}(2, 3) = 6 \implies \lfloor 30/6 \rfloor = 5$: the list $\{6, 12, 18, 24, \mathbf{30}\}$ includes **$30$** $\rightarrow$ ($-1$)
* $\text{lcm}(3, 5) = 15 \implies \lfloor 30/15 \rfloor = 2$: the list $\{15, \mathbf{30}\}$ includes **$30$** $\rightarrow$ ($-1$)
* $\text{lcm}(2, 5) = 10 \implies \lfloor 30/10 \rfloor = 3$: the list $\{10, 20, \mathbf{30}\}$ includes **$30$** $\rightarrow$ ($-1$)

**Running count for the number $30$:**

$$3 - 1 - 1 - 1 = \mathbf{0}$$

Because $30$ was part of **all three pair LCMs**, it was subtracted three separate times. The number $30$ has been **completely eliminated** ($0$ times).

---

### Step 3: Adding the Triple LCM ($+\lfloor 30/\text{lcm}(2, 3, 5) \rfloor$)

To rescue the number $30$ from being completely removed:

* $\text{lcm}(2, 3, 5) = 30 \implies \lfloor 30/30 \rfloor = 1$: the list $\{\mathbf{30}\}$ includes **$30$** $\rightarrow$ ($+1$)

**Final count for the number $30$:**

$$0 + 1 = \mathbf{1}$$

The number $30$ is now counted **exactly $1$ time**.

---

## Full Verification with Actual Numbers up to $x = 30$

Let's plug all values into the formula for $x = 30$:

$$15 + 10 + 6 - 5 - 2 - 3 + 1 = 22$$

There are exactly **$22$ unique numbers** between $1$ and $30$ that are divisible by $2$, $3$, or $5$:

$$\{2, 3, 4, 5, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30\}$$

Without the final $+\lfloor x/\text{lcm}(a,b,c) \rfloor$ term, the formula would have produced $21$, completely missing the number $30$.