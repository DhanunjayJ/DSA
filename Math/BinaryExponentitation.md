This specific algorithm is known as **Binary Exponentiation** (or Exponentiation by Squaring). It’s a clever trick to calculate $a^n$ in $O(\log n)$ time instead of $O(n)$.

The confusion usually stems from how it "jumps" through the numbers. Here is a breakdown of the logic to help it click.

---

### 1. The Core Intuition
Instead of multiplying the base by itself $n$ times, we use the binary representation of the exponent. 

Any number can be written as a sum of powers of 2. For example, if $exp = 13$, its binary is $1101_2$, which means:
$$13 = 8 + 4 + 1$$
Therefore:
$$base^{13} = base^8 \cdot base^4 \cdot base^1$$

The algorithm calculates these powers ($base^1, base^2, base^4, base^8...$) by squaring the base in every iteration, but it **only** multiplies them into the final `ans` if the corresponding bit in the exponent is $1$.

---

### 2. Step-by-Step Logic
Let's trace what happens inside that `while` loop:

* **`if(exp % 2 == 1)`**: This checks if the current lowest bit of the exponent is $1$. If it is, we "collect" the current value of the base into our result.
* **`base = (base * base) % MOD`**: This is the engine. It prepares the base for the next bit. It goes from $base^1 \to base^2 \to base^4 \to base^8 \dots$ regardless of whether we use it in the `ans` or not.
* **`exp /= 2`**: This shifts the exponent to the right, moving us to the next binary bit.



---

### 3. A Dry Run
Let's calculate **$3^{13} \pmod M$**:

| Step | `exp` | `exp%2` | `ans` (Action) | `base` (Squaring) |
| :--- | :--- | :--- | :--- | :--- |
| **Start** | 13 | - | 1 | 3 |
| **1** | 13 | 1 | $1 \cdot 3 = 3$ | $3^2 = 9$ |
| **2** | 6 | 0 | 3 (No change) | $9^2 = 81$ |
| **3** | 3 | 1 | $3 \cdot 81 = 243$ | $81^2 = 6561$ |
| **4** | 1 | 1 | $243 \cdot 6561 = \dots$ | $6561^2 = \dots$ |
| **End** | 0 | - | **Final Result** | - |

---

### 4. Why use `% MOD` everywhere?
You might notice the `base % MOD` at the start and inside the loop. This prevents **Integer Overflow**. 
* Even with `long`, $base^2$ can quickly exceed the maximum value a 64-bit integer can hold (roughly $9 \cdot 10^{18}$). 
* By applying the modulo at every multiplication, we keep the numbers within a manageable range while maintaining the mathematical correctness of the result.

> **Tip:** If your `MOD` is very large (close to $10^9$ or higher), be careful with `base * base`. If `base` is $10^9$, `base * base` is $10^{18}$, which is right at the limit of a `long`. In some languages or with larger mods, you might need `__int128` or BigInteger




The way you shared is the standard "Right-to-Left" binary exponentiation. It is the most common way because it is clean and efficient.

However, there is another way to think about it called the **Left-to-Right** approach. While the Right-to-Left version (your code) processes the "smallest" bits first, the Left-to-Right version processes the "largest" bits first.

---

### 1. The "Left-to-Right" Approach
In this version, you look at the binary bits of the exponent from the most significant bit (the far left) to the least significant bit.

* **How it works:** You start with `ans = 1`. For every bit in the exponent:
    1.  **Square** your current answer: `ans = (ans * ans) % MOD`.
    2.  **If the bit is 1**, multiply by the base: `ans = (ans * base) % MOD`.



This is actually how many people do mental math for powers. For example, to calculate $base^{13}$ (Binary `1101`):
* Start with $1$.
* **Bit 1:** Square ($1^2=1$), multiply by base $\to$ **$base$**
* **Bit 1:** Square ($base^2$), multiply by base $\to$ **$base^3$**
* **Bit 0:** Square ($base^6$), don't multiply $\to$ **$base^6$**
* **Bit 1:** Square ($base^{12}$), multiply by base $\to$ **$base^{13}$**

---

### 2. Comparison of Iterative Methods

| Feature | Right-to-Left (Your Code) | Left-to-Right |
| :--- | :--- | :--- |
| **Direction** | Processes $2^0, 2^1, 2^2 \dots$ | Processes $2^n \dots 2^1, 2^0$ |
| **Variable used** | Updates `base` by squaring it. | Updates `ans` by squaring it. |
| **Ease of Code** | Very easy to write with `while(exp > 0)`. | Needs to find the highest bit first. |
| **Main Use Case** | General purpose. | Great for **Modular Inverse** or Matrix Exponentiation. |

---

### 3. Why stay with your current code?
The code you have is generally considered the **best practice** for iteration because:
1.  **No Bit Hunting:** You don't have to find where the highest bit is; you just stop when `exp` becomes 0.
2.  **Consistency:** It works the same way regardless of the size of the exponent.

The only other "way" people do this iteratively is by using **Bitwise Operators** to make it look "pro," though the logic is identical to yours:

```java
public long power(long base, long exp) {
    long ans = 1;
    base %= MOD;
    while (exp > 0) {
        if ((exp & 1) == 1) ans = (ans * base) % MOD; // Same as exp % 2
        base = (base * base) % MOD;
        exp >>= 1; // Same as exp / 2
    }
    return ans;
}
```
