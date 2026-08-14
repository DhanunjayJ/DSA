## Problem Overview

The problem asks for the sum of Manhattan distances between all possible pairs of given points $(x_1, y_1), (x_2, y_2), \dots, (x_n, y_n)$.

The Manhattan distance between two points $(x_i, y_i)$ and $(x_j, y_j)$ is defined as:

$$D(p_i, p_j) = \vert{}x_i - x_j\vert{} + \vert{}y_i - y_j\vert{}$$

---

## 1. The Core Mathematical Insight: Independence of Dimensions

The total sum over all pairs is:

$$\text{Total Sum} = \sum_{1 \le i < j \le n} \left( \vert{}x_i - x_j\vert{} + \vert{}y_i - y_j\vert{} \right)$$

Because summation distributes over addition, we can split this formula into two completely independent 1D problems:

$$\text{Total Sum} = \underbrace{\sum_{1 \le i < j \le n} \vert{}x_i - x_j\vert{}}_{\text{Sum of 1D X-distances}} + \underbrace{\sum_{1 \le i < j \le n} \vert{}y_i - y_j\vert{}}_{\text{Sum of 1D Y-distances}}$$

> **Why this matters:** You do not need to keep $(x_i, y_i)$ paired together. You can sort the $x$-coordinates separately and the $y$-coordinates separately.

---

## 2. Derivation of the $O(n \log n)$ 1D Formula

Consider a sorted 1D array of coordinates:

$$a_0 \le a_1 \le a_2 \le \dots \le a_{n-1}$$

Because the array is sorted, whenever $j > k$, we know $a_j \ge a_k$, so $\vert{}a_j - a_k\vert{} = a_j - a_k$ without needing the absolute value operator.

When we are at index $i$, the distance between $a_i$ and all previous elements $a_0, a_1, \dots, a_{i-1}$ is:

$$(a_i - a_0) + (a_i - a_1) + \dots + (a_i - a_{i-1})$$

Grouping the terms:

* The term $a_i$ appears $i$ times: $a_i \cdot i$
* The subtracted elements are the sum of all previous elements: $\sum_{k=0}^{i-1} a_k$

$$\text{Distance contribution of } a_i = a_i \cdot i - \sum_{k=0}^{i-1} a_k$$

By maintaining a running prefix sum of the elements seen so far, we can compute each point's contribution in $O(1)$ time.

---

## 3. Step-by-Step Walkthrough (Example)

Let $x = [-1, 1, 3, 2]$.

### Sort the Array

$$x = [-1, 1, 2, 3]$$

### Iteration Table

| Index $i$ | Element $x[i]$ | Contribution: $x[i] \cdot i - \text{sum}$ | Running Total $\text{res}$ | Running Prefix Sum $\text{sum}$ |
| --- | --- | --- | --- | --- |
| **0** | `-1` | $(-1) \cdot 0 - 0 = 0$ | $0$ | $-1$ |
| **1** | `1` | $1 \cdot 1 - (-1) = 2$ | $0 + 2 = 2$ | $(-1) + 1 = 0$ |
| **2** | `2` | $2 \cdot 2 - 0 = 4$ | $2 + 4 = 6$ | $0 + 2 = 2$ |
| **3** | `3` | $3 \cdot 3 - 2 = 7$ | $6 + 7 = 13$ | $2 + 3 = 5$ |

Sum of $x$-distances = **13**.

Repeating the same steps for $y = [5, 6, 5, 3] \xrightarrow{\text{sort}} [3, 5, 5, 6]$ yields **9**.

$$\text{Total Manhattan Distance} = 13 + 9 = 22$$

---

## 4. Complete Java Implementation

```java
import java.util.Arrays;

public class ManhattanDistanceSum {

    /**
     * Calculates the sum of absolute differences between all pairs in a 1D array.
     * Uses long for sum accumulators to prevent integer overflow on large inputs.
     */
    private static long compute1DDistanceSum(int[] arr) {
        Arrays.sort(arr);

        long totalDistance = 0;
        long prefixSum = 0;

        for (int i = 0; i < arr.length; i++) {
            totalDistance += (long) arr[i] * i - prefixSum;
            prefixSum += arr[i];
        }

        return totalDistance;
    }

    /**
     * Computes the total Manhattan distance across all pairs of 2D points.
     */
    public static long totalManhattanDistance(int[] x, int[] y) {
        return compute1DDistanceSum(x) + compute1DDistanceSum(y);
    }

    public static void main(String[] args) {
        int[] x = { -1, 1, 3, 2 };
        int[] y = { 5, 6, 5, 3 };

        long result = totalManhattanDistance(x, y);
        System.out.println("Sum of Manhattan distances: " + result); // Output: 22
    }
}

```

---

## 5. Complexity Analysis

* **Time Complexity:**
* Sorting $x$ and $y$ arrays takes $O(n \log n)$.
* Computing prefix sums takes $O(n)$.
* **Overall Time Complexity:** $O(n \log n)$, an improvement over the naive $O(n^2)$ pairwise comparison.


* **Auxiliary Space:** $O(1)$ if sorted in place (or $O(n)$ depending on the sort implementation).

---

Let's throw away the fancy formulas and break this down using plain intuition.

---

### Step 1: The Manhattan Distance Formula has a "Secret"

The formula to find the distance between $(x_1, y_1)$ and $(x_2, y_2)$ is:

$$\vert{}x_1 - x_2\vert{} + \vert{}y_1 - y_2\vert{}$$

Notice something? **The $x$ parts and the $y$ parts never touch each other.** They are just added together at the end.

This means you can solve two completely separate, 1-dimensional problems:

1. Find the distance between all numbers in the $x$ list.
2. Find the distance between all numbers in the $y$ list.
3. Add the two answers together.

---

### Step 2: The 1D Problem (Points on a Number Line)

Let's look at just the $x$-coordinates: `[-1, 1, 3, 2]`.

If we **sort** them in increasing order, they line up nicely on a number line:

$$[-1, \quad 1, \quad 2, \quad 3]$$

Now, why does sorting make our life so easy?

Because every number is **greater than or equal to all the numbers to its left**. You never have to worry about negative distances or absolute value signs (`|a - b|`).

---

### Step 3: Walking Left-to-Right

Imagine we add numbers to our group one by one from left to right:

#### 1. Look at `-1` (Index 0)

* There are no numbers to the left.
* Distance added = **$0$**.
* Sum of numbers seen so far = **$-1$**.

---

#### 2. Look at `1` (Index 1)

* How far is `1` from all numbers to its left?
* Distance to `-1` is: $1 - (-1) = 2$.


* Total new distance = **$2$**.
* Sum of numbers seen so far = $(-1) + 1 = \mathbf{0}$.

---

#### 3. Look at `2` (Index 2)

* How far is `2` from all numbers to its left?
* Distance to `-1` is: $2 - (-1)$
* Distance to `1` is: $2 - 1$


* Add them up:

$$(2 - (-1)) + (2 - 1) = (2 + 2) - (-1 + 1)$$


* Notice the pattern:
* You took the number `2` **two times** ($2 \times 2 = 4$).
* And subtracted the **sum of all previous numbers** ($0$).
* Distance = $4 - 0 = \mathbf{4}$.


* Sum of numbers seen so far = $0 + 2 = \mathbf{2}$.

---

#### 4. Look at `3` (Index 3)

* How far is `3` from all numbers to its left (`-1`, `1`, `2`)?

$$(3 - (-1)) + (3 - 1) + (3 - 2)$$


* Group the `3`s together:
* `3` appears **3 times** ($3 \times 3 = 9$).
* Subtract the sum of all previous numbers ($-1 + 1 + 2 = 2$).
* Distance = $9 - 2 = \mathbf{7}$.



---

### Step 4: The Core Formula Explained

Whenever you are at index `i` looking at element `arr[i]`:

```
Distance to all previous items = (arr[i] * how_many_items_behind_it) - (sum_of_all_items_behind_it)

```

In code terms:

```java
res += (arr[i] * i) - previous_sum;
previous_sum += arr[i];

```

For the $x$-coordinates, the total sum of distances is:

$$0 + 2 + 4 + 7 = 13$$

You do the exact same process for $y$, which gives **$9$**, and add them: $13 + 9 = 22$.

---

### Interactive Visualizer

Step through the sorted points below to see how each new point calculates its distance to all previous points instantly.