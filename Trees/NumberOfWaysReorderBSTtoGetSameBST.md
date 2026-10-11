### Intuition & Core Concept

When inserting numbers sequentially into a Binary Search Tree (BST), two rules dictate its shape:

1. **The Root is Fixed First:**
The very first element in any array or subarray becomes the root of that subtree. If the first element changes, the root changes, and the resulting BST is immediately different.
2. **Subtrees are Independent:**
Every number smaller than the root must end up in the left subtree, and every number larger than the root must end up in the right subtree.

Consider `nums = [3, 4, 5, 1, 2]`.

* The root is `3`.
* Elements smaller than `3`: `[1, 2]` (left subtree).
* Elements greater than `3`: `[4, 5]` (right subtree).

Notice that inside the left subtree, `1` appears before `2`. As long as `1` appears before `2`, the left subtree retains the exact same structure. Similarly, as long as `4` appears before `5`, the right subtree retains its structure.

Does the order of a left subtree node relative to a right subtree node matter?
No. If we place `1` before `4` or `4` before `1`, `1` will still go to the left of `3`, and `4` will still go to the right of `3`. They never compete for the same slot.

---

### Step-by-Step Breakdown

#### 1. Interleaving Elements (Combinatorics)

Suppose the left subtree has $L$ elements and the right subtree has $R$ elements, giving a total of $L + R$ slots behind the root.

We need to choose which $L$ slots out of the $L + R$ available positions will be occupied by the elements of the left subtree. Once those positions are picked, the remaining $R$ slots must be filled by the right subtree elements.

The number of ways to pick these positions is:


$$\binom{L + R}{L} = \frac{(L + R)!}{L! \cdot R!}$$

Within those chosen $L$ slots, the left elements must appear in an order that forms the valid left subtree. Similarly, the right elements must appear in an order that forms the valid right subtree.

#### 2. The Recurrence Relation

Because left and right subtree formations are independent subproblems, the total valid arrangements for a list `arr` is:


$$\text{ways}(arr) = \binom{L + R}{L} \times \text{ways}(\text{left}) \times \text{ways}(\text{right}) \pmod{10^9 + 7}$$

**Base Cases:**

* If an array has $0$, $1$, or $2$ elements, there is only $1$ way to arrange them to maintain the BST (for $2$ elements, swapping them changes which one is root).

#### 3. Subtracting the Original Array

The problem asks for the number of **different reorderings**. The recurrence calculates all valid arrays, including the original sequence given in the input. Therefore, the final answer is:


$$(\text{ways}(nums) - 1) \pmod{10^9 + 7}$$

---

### Precomputing Combinations (Pascal's Triangle)

Because $N \le 1000$, computing factorials modulo $10^9 + 7$ with modular inverses works, but building **Pascal's Triangle** takes only $O(N^2)$ additions without division:


$$C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) \pmod{10^9 + 7}$$

For $N = 1000$, an $N \times N$ 2D array contains $10^6$ entries, which executes in a few milliseconds in Java.

---

### Complete Java Implementation

```java
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final int MOD = 1_000_000_007;
    private long[][] comb;

    public int numOfWays(int[] nums) {
        int n = nums.length;
        
        // Step 1: Precompute Pascal's triangle for combinations C(n, k) % MOD
        comb = new long[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            comb[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                comb[i][j] = (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
            }
        }

        // Step 2: Convert array to List for recursive splitting
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        // Step 3: Compute ways and subtract 1 to exclude the original array
        long totalWays = dfs(list);
        return (int) ((totalWays - 1 + MOD) % MOD);
    }

    private long dfs(List<Integer> list) {
        // Base case: lists of size <= 2 have only 1 valid ordering
        if (list.size() <= 2) {
            return 1;
        }

        int root = list.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        // Partition remaining elements into left and right subtrees
        for (int i = 1; i < list.size(); i++) {
            int val = list.get(i);
            if (val < root) {
                left.add(val);
            } else {
                right.add(val);
            }
        }

        // Combinations: C(leftSize + rightSize, leftSize)
        long waysToInterleave = comb[left.size() + right.size()][left.size()];

        // Recurse on left and right subtrees
        long leftWays = dfs(left);
        long rightWays = dfs(right);

        // Combine results
        long result = (waysToInterleave * leftWays) % MOD;
        result = (result * rightWays) % MOD;

        return result;
    }
}

```

---

### Complexity Analysis

| Metric | Complexity | Notes |
| --- | --- | --- |
| **Pascal's Triangle Time** | $O(N^2)$ | Precomputing combinations up to $1000 \times 1000$. |
| **Recursion Time** | $O(N^2)$ worst case, $O(N \log N)$ average | Each recursive step filters elements. At worst (skewed tree), tree height is $N$, doing $O(N)$ work at each level. |
| **Total Time Complexity** | $O(N^2)$ | Easily executes well within the 2-second time limit for $N \le 1000$. |
| **Space Complexity** | $O(N^2)$ | Dominated by the $1000 \times 1000$ 2D `comb` array and recursion allocations. |

---

Yes. Instead of building Pascal's triangle with $O(N^2)$ space, you can precompute factorials and their modular inverses to evaluate the binomial coefficient directly:

$$\binom{n}{k} = \frac{n!}{k! \cdot (n - k)!} \equiv n! \times (k!)^{-1} \times ((n - k)!)^{-1} \pmod{10^9 + 7}$$

By **Fermat's Little Theorem**, since $MOD = 10^9 + 7$ is prime:


$$a^{-1} \equiv a^{MOD - 2} \pmod{MOD}$$

---

### Step-by-Step Approach

1. **Precompute Factorials in $O(N)$**:

$$\text{fact}[i] = (\text{fact}[i - 1] \times i) \pmod{MOD}$$


2. **Precompute Inverse Factorials in $O(N)$**:
* Compute $(\text{fact}[N])^{-1} = (\text{fact}[N])^{MOD - 2} \pmod{MOD}$ using binary exponentiation once.
* Work backwards:

$$\text{invFact}[i - 1] = (\text{invFact}[i] \times i) \pmod{MOD}$$



*(because $\frac{1}{(i - 1)!} = \frac{i}{i!}$)*.


3. **Query Combinations in $O(1)$**:

$$\binom{n}{k} = (\text{fact}[n] \times \text{invFact}[k] \pmod{MOD}) \times \text{invFact}[n - k] \pmod{MOD}$$



This reduces the auxiliary space from $O(N^2)$ down to **$O(N)$**.

---

### Complete Java Implementation

```java
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final int MOD = 1_000_000_007;
    private long[] fact;
    private long[] invFact;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        // Step 1: Precompute fact and invFact in O(N)
        fact = new long[n + 1];
        invFact = new long[n + 1];
        
        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        // Fermat's Little Theorem for the largest factorial
        invFact[n] = power(fact[n], MOD - 2);
        for (int i = n - 1; i >= 0; i--) {
            invFact[i] = (invFact[i + 1] * (i + 1)) % MOD;
        }

        // Step 2: Convert array to List for recursion
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        // Step 3: Compute ways and subtract the original array
        long totalWays = dfs(list);
        return (int) ((totalWays - 1 + MOD) % MOD);
    }

    private long dfs(List<Integer> list) {
        if (list.size() <= 2) {
            return 1;
        }

        int root = list.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            int val = list.get(i);
            if (val < root) {
                left.add(val);
            } else {
                right.add(val);
            }
        }

        int L = left.size();
        int R = right.size();

        // O(1) Binomial Coefficient: C(L + R, L)
        long waysToInterleave = nCr(L + R, L);

        long leftWays = dfs(left);
        long rightWays = dfs(right);

        long result = (waysToInterleave * leftWays) % MOD;
        return (result * rightWays) % MOD;
    }

    private long nCr(int n, int r) {
        if (r < 0 || r > n) return 0;
        long num = fact[n];
        long den = (invFact[r] * invFact[n - r]) % MOD;
        return (num * den) % MOD;
    }

    private long power(long base, long exp) {
        long res = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                res = (res * base) % MOD;
            }
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return res;
    }
}

```

---

### Comparison with Pascal's Approach

| Aspect | Pascal's Triangle ($O(N^2)$ Table) | Factorial + Inverse Factorials |
| --- | --- | --- |
| **Combination Precomputation** | $O(N^2)$ additions | $O(N)$ multiplications + one $O(\log MOD)$ power |
| **Combination Query Time** | $O(1)$ table lookup | $O(1)$ multiplication |
| **Space Complexity** | $O(N^2)$ memory (`1000 x 1000` array) | $O(N)$ memory (`2 x 1000` array) |

---

The overall time complexity is **$O(N^2)$ in the worst case** and **$O(N \log N)$ on average**.

Here is the breakdown by phase:

---

### 1. Precomputation: $O(N)$

* **Factorials array:** One loop from $1$ to $N \implies O(N)$.
* **Modular inverse of $(N!) \pmod{MOD}$:** `power(fact[n], MOD - 2)` executes binary exponentiation in $O(\log(MOD))$. Since $MOD \approx 10^9$, this is $\approx 30$ operations $\implies O(1)$.
* **Inverse factorials array:** One loop backwards from $N-1$ down to $0 \implies O(N)$.
* **Total precomputation:** $O(N)$.

---

### 2. Recursive `dfs(list)`

At each recursive call for a list of size $K$:

* Filtering the list into `left` and `right` takes **$O(K)$** time.
* `nCr` calculation takes **$O(1)$** arithmetic operations.

The total time spent in recursion depends on the tree's balance:

#### **Worst Case: $O(N^2)$**

Occurs when the input forms a skewed tree (e.g., sorted array `[1, 2, 3, 4, 5]` or reverse sorted `[5, 4, 3, 2, 1]`).

* The root separates the list into one empty subtree and one subtree of size $K - 1$.
* The work done at each step is:

$$(N) + (N - 1) + (N - 2) + \dots + 1 = \frac{N(N + 1)}{2} \implies O(N^2)$$



#### **Average Case: $O(N \log N)$**

Occurs when the tree is reasonably balanced (the root splits elements roughly in half):

* Recurrence: $T(N) = 2T(N/2) + O(N)$
* By Master Theorem: $T(N) = O(N \log N)$.

---

### Summary Table

| Component | Time Complexity |
| --- | --- |
| Precomputing `fact` & `invFact` | $O(N)$ |
| DFS (Average Case) | $O(N \log N)$ |
| DFS (Worst Case - Skewed Tree) | $O(N^2)$ |
| **Total Time Complexity** | **$O(N^2)$** |
| **Total Space Complexity** | **$O(N)$** (arrays + recursion stack + subarray allocations) |

Since $N \le 1000$, $N^2 \approx 10^6$ operations, which easily runs in **under 15–25 ms** on LeetCode.