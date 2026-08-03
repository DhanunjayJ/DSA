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