To solve the Coin Change problem using Breadth-First Search (BFS), you need to change how you look at the problem. Instead of viewing it as an array of overlapping subproblems, treat it as a **shortest-path problem on an unweighted graph**.

* **The starting node** is your target `amount`.
* **The edges** are the `coins`. Subtracting a coin moves you to a new node.
* **The destination** is `0`.

Because BFS explores layer by layer, the first time you reach `0`, you are guaranteed to have taken the shortest path (the minimum number of coins).

### The BFS Implementation

The most critical part of this approach is the `visited` array. Without it, you would re-calculate the same amounts over and over, leading to a Time Limit Exceeded (TLE) error.

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[amount + 1];
        
        // Start from the target amount
        queue.offer(amount);
        visited[amount] = true;
        
        int level = 0; // This tracks the number of coins used
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++; // We are taking one more step (using one more coin)
            
            // Process all nodes at the current depth level
            for (int i = 0; i < size; i++) {
                int currentAmount = queue.poll();
                
                // Try subtracting every available coin
                for (int coin : coins) {
                    int nextAmount = currentAmount - coin;
                    
                    // Early exit: We hit 0 exactly!
                    if (nextAmount == 0) {
                        return level;
                    }
                    
                    // If the remaining amount is valid and hasn't been seen yet
                    if (nextAmount > 0 && !visited[nextAmount]) {
                        visited[nextAmount] = true;
                        queue.offer(nextAmount);
                    }
                }
            }
        }
        
        // Queue emptied without reaching 0
        return -1; 
    }
}

```

---

### When is BFS faster than DP?

In terms of worst-case Big-O notation, both have a time complexity of $O(S \times n)$ where $S$ is the amount and $n$ is the number of coins. However, their real-world performance depends heavily on the test cases.

| Feature | Dynamic Programming (Bottom-Up) | Breadth-First Search (BFS) |
| --- | --- | --- |
| **Execution Style** | Exhaustive | Early Exit |
| **Best Case Scenario** | Needs to compute all subproblems anyway. | Target is reached quickly with large coins. |
| **Overhead** | Very low (simple array writes). | Higher (object creation, queue operations). |

**BFS wins when the solution requires very few coins.**
Imagine `coins = [1, 2, 100]` and `amount = 100`.

* **DP** will painstakingly calculate the minimum coins needed for amounts $1, 2, 3, \dots 99$ before finally computing $100$.
* **BFS** will subtract `100` on the very first loop iteration, hit `0`, and immediately return `1`. It skips calculating the 99 other states entirely.

**DP wins in general practice.**
If the amount is large and takes many coins to reach (or is impossible to reach), BFS has to explore a massive tree of possibilities. While the `visited` array caps the worst-case time limit, the overhead of enqueuing and dequeuing objects in Java makes BFS noticeably slower than DP's lightning-fast array lookups.

---

A greedy algorithm for this problem means always picking the largest possible coin that fits into the remaining amount.

While this seems completely logical, it fails because making the locally optimal choice (grabbing the biggest coin right now) does not guarantee a globally optimal solution (the fewest total coins).

## The Classic Counterexample

Imagine you have a slightly unusual currency system and you need to make change for a specific amount.

* **Coins:** `[1, 3, 4]`
* **Target Amount:** `6`

Here is how the two different approaches handle it:

| Strategy | Steps Taken | Coins Used | Outcome |
| --- | --- | --- | --- |
| **Greedy** | Takes `4` (leaves 2) → Takes `1` (leaves 1) → Takes `1` (leaves 0). | **3** (`[4, 1, 1]`) | Suboptimal |
| **Optimal (DP/BFS)** | Takes `3` (leaves 3) → Takes `3` (leaves 0). | **2** (`[3, 3]`) | Minimum |

Because the greedy algorithm eagerly grabs the `4`, it backs itself into a corner, forcing you to use two `1` coins to finish the job. It has no mechanism to "look ahead" or backtrack to realize that ignoring the largest coin actually yields a better overall result.

It can also fail to find a valid solution entirely. If your coins are `[2, 3]` and the amount is `4`, a greedy approach grabs the `3`, leaving `1`. It then concludes it's impossible to make change. DP and BFS would correctly find `[2, 2]`.

## When *Does* Greedy Work?

You probably thought of a greedy approach because it is exactly what cashiers do in real life.

Greedy algorithms work perfectly for real-world currencies (like US Dollars or Euros) because those denominations (1, 5, 10, 25) form a **canonical coin system**. In a canonical system, the math guarantees that the greedy choice will always yield the optimal result.

However, LeetCode and technical interviews will always feed the algorithm arbitrary, non-canonical arrays to explicitly break greedy implementations and force you to use Dynamic Programming or BFS.


---

Great question! The word **"combinations"** here is the single most important distinction between **Coin Change II** and standard **Coin Change (I)** or permutation problems.

---

### What "Combinations" Means Here

"Combinations" means **order does NOT matter**.

If you pick `[1, 2, 2]` to make `5`, that is considered **the exact same combination** as `[2, 1, 2]` or `[2, 2, 1]`. They count as **$1$ unique way**, not $3$.

In contrast, if a problem asks for **permutations** (or "distinct sequences"), then `[1, 2, 2]`, `[2, 1, 2]`, and `[2, 2, 1]` would count as **$3$ different ways**.

---

### Key Differences: Coin Change I vs. Coin Change II

| Feature | Coin Change I (Min Coins) | **Coin Change II (This Problem)** |
| --- | --- | --- |
| **Goal** | Find the **minimum number** of coins to reach `amount`. | Find the **total number of unique combinations** to reach `amount`. |
| **Question Asked** | *"What's the fewest coins I can use?"* | *"In how many distinct ways can I select coins?"* |
| **Output Type** | Single minimum count (e.g., `2` coins). | Total ways count (e.g., `4` combinations). |
| **Duplicate Sets?** | Doesn't matter — it just seeks the shortest path. | **Strictly forbidden** — order doesn't create new options. |

---

### Why Loop Order Prevents Duplicate Combinations

In Dynamic Programming, the order of your nested loops decides whether you are counting **combinations** (order doesn't matter) or **permutations** (order matters):

#### 1. Combinations (Coin Change II)

```java
// OUTER LOOP: Coins | INNER LOOP: Amount
for (int coin : coins) {
    for (int j = coin; j <= amount; j++) {
        dp[j] += dp[j - coin];
    }
}

```

* **Why it works:** You process coin `1` completely for all amounts, *then* move to coin `2`, *then* coin `5`. Because you only move forward through the `coins` array, you naturally force an ordered pick (e.g., all `1`s first, then `2`s, then `5`s). This completely eliminates duplicate arrangements like `[2, 1]` vs `[1, 2]`.

#### 2. Permutations (Different Problem)

```java
// OUTER LOOP: Amount | INNER LOOP: Coins
for (int j = 1; j <= amount; j++) {
    for (int coin : coins) {
        if (j >= coin) {
            dp[j] += dp[j - coin];
        }
    }
}

```

* **Why this is different:** At *every amount*, you try *every single coin*. This allows picking a `2` first and then a `1` later, effectively counting `[1, 2]` and `[2, 1]` as two separate solutions.

---

> **Takeaway:** For **Coin Change II**, keeping `coins` on the outer loop guarantees that every valid set of coins is only counted **once**, regardless of the order you might pick them in real life.