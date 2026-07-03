**DP** stands for **Dynamic Programming**.

At its core, DP is an algorithmic technique used to solve complex problems by breaking them down into simpler subproblems. It’s essentially a clever way of avoiding redundant work by remembering the answers to subproblems you've already solved.

To use DP, a problem must have two main characteristics:

1. **Overlapping Subproblems:** You find yourself solving the exact same subproblems over and over again.
2. **Optimal Substructure:** The optimal solution to the big problem can be built from the optimal solutions of its smaller subproblems.

The classic example used to explain this is the **Fibonacci sequence**, where each number is the sum of the two preceding ones: $F(n) = F(n-1) + F(n-2)$.

---

## The Two Approaches: Top-Down vs. Bottom-Up

There are two ways to implement a Dynamic Programming solution. Think of them as two different strategies for climbing a mountain of data.

### 1. Top-Down (Memoization)

The top-down approach is the more "natural" extension of standard recursion. You start with the big, final problem you want to solve and break it down into smaller pieces.

However, to avoid doing the same work repeatedly, you use a technique called **Memoization** (essentially creating a "memo" or a cache).

* **How it works:** You write a recursive function. Before calculating the solution to a subproblem, you check your cache (like an array or a hash map). If the answer is already there, you return it immediately. If not, you calculate it, save it to the cache, and then return it.
* **Analogy:** Imagine solving a massive math problem. You start from the main question, break it into smaller equations, and every time you get an answer to a smaller equation, you write it down on a notepad so you never have to calculate it again.

### 2. Bottom-Up (Tabulation)

The bottom-up approach flips the script. Instead of starting with the big problem, you start with the absolute smallest, most basic subproblems (the base cases) and literally build your way up to the final solution.

This technique is called **Tabulation** because you typically fill out a table (a 1D or 2D array) sequentially.

* **How it works:** You solve the base cases first (e.g., $F(0) = 0$ and $F(1) = 1$). Then, you use a loop to iterate forward, using the results already stored in your table to calculate the next values until you reach your target.
* **Analogy:** Building a house brick by brick from the foundation upward. You don't look at the roof until the walls are already built on top of the foundation.

---

## Quick Comparison

Here is a quick breakdown of how they stack up against each other:

| Feature | Top-Down (Memoization) | Bottom-Up (Tabulation) |
| --- | --- | --- |
| **Core Mechanism** | Recursion + Caching | Iteration (Loops) + Table Filling |
| **Direction** | From the main problem down to base cases | From base cases up to the main problem |
| **Ease of Designing** | Usually easier because it mimics natural recursion | Can be harder to conceptualize the exact loop order |
| **Overhead** | Has call stack overhead due to recursion (risk of StackOverflow) | No recursion overhead; faster in practice |
| **Subproblem Solving** | Only solves the subproblems *needed* for the answer | Solves *all* subproblems sequentially |

To put it simply: **Top-Down** is like a manager delegating tasks downward and remembering who did what, while **Bottom-Up** is like an assembly line building the product from scratch, piece by piece.

___

It looks like you are implementing the **Fibonacci sequence** using both Top-Down and Bottom-Up Dynamic Programming.

Your `topDown` implementation is absolutely perfect. However, your `bottomUp` implementation has **two small bugs** related to the loop boundary and the array indexing.

Here is a direct breakdown of what needs fixing and why:

### The Bugs in `bottomUp`

1. **The Loop Condition (`i < n`):** Your loop stops *before* reaching `n`. If `n = 5`, your loop stops at `i = 4`. Because of this, `memo[5]` is never calculated and remains its default value (`0`). The loop condition needs to be `i <= n`.
2. **The Array Size & Return Value:** Because the loop stops early, returning `memo[n]` will return an uncalculated `0`. If you fix the loop to `i <= n`, it will throw an `ArrayIndexOutOfBoundsException` if `n = 0` or `n = 1` because you hardcode `memo[0]` and `memo[1]` without checking if the array is large enough.

---

### The Corrected Code

Here is the clean, fixed version of your code:

```java
// User function Template for Java

class Solution {
    static long MOD = 1_000_000_000+7;

    // Perfect implementation!
    static long topDown(int n) {
        if (n == 0 || n == 1) return n; // Edge case safety
        long [] memo = new long[n+1];
        java.util.Arrays.fill(memo, -1);
        return dp(n, memo);
    }
    
    static long dp(int n, long [] memo){
        if(n == 0 || n == 1) return n;
        if(memo[n] != -1) return memo[n];
        memo[n] = (dp(n-1, memo) + dp(n-2, memo)) % MOD;
        return memo[n];
    }

    // Fixed implementation
    static long bottomUp(int n) {
        // Handle edge cases immediately so array creation doesn't break
        if (n == 0 || n == 1) return n; 
        
        long [] memo = new long[n+1];
        memo[0] = 0;
        memo[1] = 1;
        
        // Changed condition from i < n to i <= n
        for(int i = 2; i <= n; i++){ 
            memo[i] = (memo[i-1] + memo[i-2]) % MOD;
        }
        
        return memo[n];
    }
}

```

### Pro-Tip: Space Optimization for Bottom-Up

If you look closely at your bottom-up approach, you only ever need the **last two values** (`memo[i-1]` and `memo[i-2]`) to compute the next one. You don't actually need the whole array!

You can optimize the space complexity from **$O(n)$ down to $O(1)$** like this:

```java
static long bottomUpOptimized(int n) {
    if (n == 0 || n == 1) return n;
    
    long prev2 = 0; // represents memo[i-2]
    long prev1 = 1; // represents memo[i-1]
    long current = 0;
    
    for (int i = 2; i <= n; i++) {
        current = (prev1 + prev2) % MOD;
        prev2 = prev1; // shift forward
        prev1 = current; // shift forward
    }
    
    return current;
}

```