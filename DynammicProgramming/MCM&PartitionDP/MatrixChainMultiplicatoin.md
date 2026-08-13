To see why matrix multiplication uses outer dimensions and requires a specific number of multiplications, let's step away from code or high-level formulas and trace a small concrete example by hand.

---

## 1. How Matrix Multiplication Actually Works

To multiply two matrices $A$ and $B$ (written as $A \times B$):

* Every cell $(i, j)$ in the output matrix is created by taking the **$i$-th row of $A$** and the **$j$-th column of $B$**.
* You pair up corresponding elements, multiply them, and sum them up (this is called a **dot product**).

### Visual Example

Let's multiply a **$1 \times 3$** matrix $A$ by a **$3 \times 2$** matrix $B$:

$$A = \begin{pmatrix} 2 & 1 & 4 \end{pmatrix}_{1 \times 3}$$

$$B = \begin{pmatrix} 1 & 2 \\ 3 & 0 \\ 5 & 1 \end{pmatrix}_{3 \times 2}$$

Notice:

* **Row count of $A$** = 1
* **Column count of $B$** = 2
* **Inner matching dimension** = 3 (Length of $A$'s row == Height of $B$'s column)

---

## 2. Calculating Each Output Cell

Since $A$ has **1 row** and $B$ has **2 columns**, our output matrix $C$ will have **1 row and 2 columns** (dimensions $1 \times 2$):

$$C = \begin{pmatrix} C_{1,1} & C_{1,2} \end{pmatrix}_{1 \times 2}$$

Let's compute each entry step-by-step:

### Computing $C_{1,1}$ (Row 1 of $A$ $\cdot$ Column 1 of $B$):

Row 1 of $A$: $(2, 1, 4)$

Column 1 of $B$: $(1, 3, 5)^T$

$$C_{1,1} = (2 \times 1) + (1 \times 3) + (4 \times 5) = 2 + 3 + 20 = 25$$

> **Notice:** To produce this **single cell**, we performed **3 scalar multiplications** $(2\times1)$, $(1\times3)$, and $(4\times5)$.

---

### Computing $C_{1,2}$ (Row 1 of $A$ $\cdot$ Column 2 of $B$):

Row 1 of $A$: $(2, 1, 4)$

Column 2 of $B$: $(2, 0, 1)^T$

$$C_{1,2} = (2 \times 2) + (1 \times 0) + (4 \times 1) = 4 + 0 + 4 = 8$$

> **Notice:** Again, to produce this **single cell**, we performed **3 scalar multiplications**.

---

## 3. Putting It Together

The resulting matrix $C$ is:

$$C = \begin{pmatrix} 25 & 8 \end{pmatrix}_{1 \times 2}$$

### Key Insights:

1. **Output Dimensions ($1 \times 2$):**
* The number of rows comes from $A$ ($1$).
* The number of columns comes from $B$ ($2$).
* The inner dimension ($3$) was "consumed" during the addition process within each dot product.


2. **Total Multiplications Count:**
* Total cells in output = $1 \times 2 = 2 \text{ cells}$.
* Multiplications per cell = $3$ (the shared inner dimension).
* **Total scalar multiplications** = $\text{Rows of } A \times \text{Inner Dimension} \times \text{Cols of } B = 1 \times 3 \times 2 = \mathbf{6}$.



---

## 4. Back to the Matrix Chain Multiplication Problem

For $M_2$ ($1 \times 3$) and $M_3$ ($3 \times 4$):

* Output matrix size = **$1 \times 4$** (1 row, 4 columns = 4 total cells).
* Each cell requires **3 scalar multiplications** (because each row in $M_2$ has 3 elements and each column in $M_3$ has 3 elements).
* Total multiplications to compute $M_2 \times M_3$ = $1 \times 3 \times 4 = \mathbf{12}$.

Now that $M_2 \times M_3$ is a **$1 \times 4$** matrix, multiplying $M_1$ ($2 \times 1$) with it requires:

* $2 \times 1 \times 4 = \mathbf{8}$ scalar multiplications.

Total operations = $12 + 8 = \mathbf{20}$.

---

Let's break down this example step-by-step using what we learned about matrix dimensions and multiplication costs!

---

## 1. Extracting Matrix Dimensions

Given `arr[] = [1, 2, 3, 4, 3]`, our 4 matrices are:

* **$M_1$**: $1 \times 2$
* **$M_2$**: $2 \times 3$
* **$M_3$**: $3 \times 4$
* **$M_4$**: $4 \times 3$

---

## 2. Parenthesization Order: `(((M1 x M2) x M3) x M4)`

We evaluate from the innermost parentheses outward in **3 distinct steps**:

```
Step 1:  (M1 x M2)               --> Result: Matrix A
Step 2:  (A x M3)                --> Result: Matrix B
Step 3:  (B x M4)                --> Final Answer

```

---

## 3. Tracing Each Step

### **Step 1: Compute $A = M_1 \times M_2$**

* **Dimensions:** $M_1$ ($1 \times 2$) $\times$ $M_2$ ($2 \times 3$)
* **Cost formula:** $\text{rows}_1 \times \text{shared inner} \times \text{cols}_2 = 1 \times 2 \times 3 = \mathbf{6 \text{ multiplications}}$
* **Resulting Matrix $A$ dimensions:** **$1 \times 3$** (takes $M_1$'s row count and $M_2$'s column count)

---

### **Step 2: Compute $B = A \times M_3$**

* **Dimensions:** $A$ ($1 \times 3$) $\times$ $M_3$ ($3 \times 4$)
* **Cost formula:** $\text{rows}_A \times \text{shared inner} \times \text{cols}_3 = 1 \times 3 \times 4 = \mathbf{12 \text{ multiplications}}$
* **Resulting Matrix $B$ dimensions:** **$1 \times 4$** (takes $A$'s row count and $M_3$'s column count)

---

### **Step 3: Compute $Final = B \times M_4$**

* **Dimensions:** $B$ ($1 \times 4$) $\times$ $M_4$ ($4 \times 3$)
* **Cost formula:** $\text{rows}_B \times \text{shared inner} \times \text{cols}_4 = 1 \times 4 \times 3 = \mathbf{12 \text{ multiplications}}$
* **Resulting Matrix dimensions:** **$1 \times 3$**

---

## 4. Total Cost Calculation

Summing the scalar multiplications from all three steps:

$$\text{Total} = \underbrace{(1 \times 2 \times 3)}_{\text{Step 1: } 6} + \underbrace{(1 \times 3 \times 4)}_{\text{Step 2: } 12} + \underbrace{(1 \times 4 \times 3)}_{\text{Step 3: } 12} = 6 + 12 + 12 = \mathbf{30}$$

Any other way of parenthesizing these 4 matrices (for example, `(M1 x M2) x (M3 x M4)`) would yield a higher total number of multiplications.

---

To solve Matrix Chain Multiplication efficiently, we use **Dynamic Programming (DP)**. Checking all $C_{n-1}$ parenthesizations takes exponential time $O(2^n)$, but DP reduces this to $O(n^3)$ by storing and reusing solutions to smaller subproblems.

---

## 1. Core Intuition: The "Split Point" ($k$)

Suppose we want to find the minimum cost to multiply a chain of matrices from index $i$ to $j$ (written as $M_i \times M_{i+1} \times \dots \times M_j$).

To multiply this chain, we **must** pick a place to split it into two sub-chains at some index $k$ (where $i \le k < j$):

$$\text{Chain } (i \dots j) = \text{Left Group } (i \dots k) \times \text{Right Group } (k+1 \dots j)$$

### Total Cost for a Split at $k$:

$$\text{Cost} = \text{Cost of Left Group} + \text{Cost of Right Group} + \text{Cost to multiply the two resulting matrices}$$

* **Left Group Cost:** $dp[i][k]$
* **Right Group Cost:** $dp[k+1][j]$
* **Final Multiplication Cost:** $\text{arr}[i-1] \times \text{arr}[k] \times \text{arr}[j]$

Since we don't know which split point $k$ is best, we try **every valid position for $k$** between $i$ and $j-1$, and take the **minimum**:

$$dp[i][j] = \min_{i \le k < j} \Big( dp[i][k] + dp[k+1][j] + (\text{arr}[i-1] \times \text{arr}[k] \times \text{arr}[j]) \Big)$$

---

## 2. Base Case & Order of Computation

1. **Base Case ($i == j$):** A single matrix $M_i$ requires **0 multiplications**. So, $dp[i][i] = 0$ for all $i$.
2. **Subproblem Size:** To solve a chain of length $L$, we need the solutions for shorter lengths. Therefore, we build the table by **chain length $L$** (from length $2$ up to length $N$).

---

## 3. Visualizing the DP Table for `arr = [1, 2, 3, 4, 3]`

Here, $N = 4$ matrices ($M_1, M_2, M_3, M_4$). We build a 2D table `dp[5][5]`:

```
       M1(1)   M2(2)   M3(3)   M4(4)
M1(1)    0       6      18      30   <-- Output: dp[1][4] = 30
M2(2)    -       0      24      30
M3(3)    -       -       0      36
M4(4)    -       -       -       0

```

### Step-by-Step Table Filling:

* **Length 1:** Diagonal is all `0` (e.g., $dp[1][1] = 0, dp[2][2] = 0, \dots$).
* **Length 2:**
* $dp[1][2] = 1 \times 2 \times 3 = 6$
* $dp[2][3] = 2 \times 3 \times 4 = 24$
* $dp[3][4] = 3 \times 4 \times 3 = 36$


* **Length 3:**
* $dp[1][3] = \min(\text{split at } 1, \text{split at } 2) = \min(0 + 24 + 1\times2\times4, 6 + 0 + 1\times3\times4) = \min(32, 18) = 18$
* $dp[2][4] = \min(\text{split at } 2, \text{split at } 3) = \min(0 + 36 + 2\times3\times3, 24 + 0 + 2\times4\times3) = \min(54, 48) = 30$


* **Length 4:**
* $dp[1][4] = \min(\text{split at } 1, 2, \text{ or } 3) \rightarrow$ **30**



---

## 4. Complexity Analysis

* **Time Complexity:** $O(N^3)$ — 3 nested loops (chain length $L$, starting point $i$, split point $k$).
* **Space Complexity:** $O(N^2)$ — to store the 2D `dp` matrix.

---

