

## 2. The Mathematical Relationship

This code relies on the **Difference of Squares** identity. If $x$ is the repeated value and $y$ is the missing value:

$$x - y = \text{sumDiff}$$

$$x^2 - y^2 = \text{sqrDiff}$$

We know that $x^2 - y^2 = (x - y)(x + y)$. By substituting:


$$\text{sqrDiff} = (\text{sumDiff})(x + y)$$

$$\frac{\text{sqrDiff}}{\text{sumDiff}} = x + y$$

Now you have a system of two equations:

1. $x + y = A$ (where $A = \frac{\text{sqrDiff}}{\text{sumDiff}}$)
2. $x - y = B$ (where $B = \text{sumDiff}$)

Adding them gives $2x = A + B$, so **$x = \frac{A+B}{2}$**.
Subtracting them gives $2y = A - B$, so **$y = \frac{A-B}{2}$**.

---

## 3. What category is this?

This is the **Mathematical/Formulaic** category.

* **When to use it:** When you are dealing with a set of numbers that *should* follow a known sequence (like $1$ to $N$).
* **The "Pro" move:** This approach is faster than the "Negation" approach because you only pass through the grid once and you never modify the original data. It is considered "Read-Only" friendly.

### Comparison Table

| Feature | Negation (Pattern 1) | Math (Pattern 2) |
| --- | --- | --- |
| **Logic** | Swapping/Marking | Algebra/Identities |
| **Space** | $O(1)$ | $O(1)$ |
| **Modification** | **Destructive** (changes grid) | **Non-destructive** |
| **Complexity** | Easy to debug | Easy to overflow |

**Do you have another piece of code you're curious about, or should we try to apply this "Math Pattern" to a different problem?**