You are on the right track with the intuition, but let's sharpen the "why" behind that **1**.

In standard math, the number **1** is the **Multiplicative Identity**. Anything multiplied by 1 stays the same ($5 \times 1 = 5$). When you divide $10 / 2$, you are essentially asking: *"What number can I multiply 2 by to get back to 10?"* or more simply, *"What is the inverse of 2 that brings a number back to the identity (1)?"*

In Modular Arithmetic, we don't have decimals, so we need an integer that acts like a fraction.

### 1. Why the result must be 1?
You asked: *"Is it because $x$ and $y$ are the same if their mod is 1?"* **Not exactly.** It is because $y$ is the "flip" of $x$. 

In normal math: $2 \times \frac{1}{2} = \mathbf{1}$.
In Modular math: $2 \times (\text{Modular Inverse of 2}) \equiv \mathbf{1} \pmod{MOD}$.

We use **1** because it is the "neutral element." If you can find a number $y$ that "neutralizes" $x$ into a **1**, then multiplying by $y$ is mathematically the same as dividing by $x$.



---

### 2. Clarifying your Method A and Method B
You mentioned neither approach is correct—actually, **Method A is mathematically correct**, but it is **impossible for a computer** to do when the numbers are huge.

* **Method A (Big Math):** $(10 / 2) \pmod 3 = 5 \pmod 3 = \mathbf{2}$. 
    * *The Problem:* If the top number was $100,000!$ instead of $10$, the computer would crash before it could even try to divide by 2.
* **Method B (Direct Modulo):** $(10 \pmod 3) / (2 \pmod 3) = 1 / 2 = \mathbf{0.5}$.
    * *The Problem:* Modular arithmetic **only works with integers**. $0.5$ doesn't exist in this system.

---

### 3. The "Correct" Method: The Modular Inverse
To solve $10 / 2 \pmod 3$ without ever using a decimal or a massive number, we do this:

1.  **Find the Inverse:** Find $y$ such that $(2 \times y) \equiv 1 \pmod 3$. As we found, $y = 2$ (because $2 \times 2 = 4$, and $4 \pmod 3 = 1$).
2.  **Multiply:** Instead of dividing by 2, multiply by its inverse (2).
3.  **Calculate:** $(10 \pmod 3 \times 2) \pmod 3$.
4.  **Result:** $(1 \times 2) \pmod 3 = \mathbf{2}$.

**It works!** You got the same answer as Method A, but you never had to deal with decimals or huge divisions.

### Summary of your Logic:
* **Is $x \times y \equiv 1$ correct?** Yes. It defines $y$ as the "replacement" for $1/x$.
* **Why 1?** Because $1$ is the multiplicative identity. We are looking for the integer "mirror" of a fraction that hits that identity.



This is exactly why your code uses `power(denominator, MOD - 2)`. It is a formula that guarantees finding that "mirror" integer $y$ so that your division becomes a safe multiplication.
