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

To master Dynamic Programming, you have to look at it like building a functional machine. Before you can write the code, you need to understand the individual structural components.

Here are the formal definitions of the core concepts in DP, explained through the lens of how they actually function during a problem.

---

## 1. DP State

The **State** is a formal representation of a specific subproblem. It is a snapshot or a set of parameters (variables) that perfectly and uniquely describes your current situation in the problem.

* **Why it matters:** It tells the algorithm exactly where it is and what constraints it is currently operating under.
* **Real-World Analogy:** If you are playing a video game, the "State" is your current save file: **(Level: 4, Health: 80%, Coins: 15)**. That information perfectly captures your exact situation.
* **Example in CP:** In the 0/1 Knapsack problem, the state is typically defined by two parameters: `dp[i][w]`, which means: *"The maximum value possible considering the first `i` items with a remaining backpack capacity of `w`."*

---

## 2. Transition / Recurrence Relation

The **Recurrence Relation** (often called the **Transition Equation**) is the mathematical formula or logical rule that connects a larger DP state to one or more smaller, already-solved DP states.

* **Why it matters:** This is the core logic of your DP. It dictates how making a choice *transforms* your current state into the next state.
* **Real-World Analogy:** A map direction: *"To get to the finish line, you must take the minimum distance of either (Path A + 5 minutes) or (Path B + 10 minutes)."*
* **Example in CP:** For the Fibonacci sequence, the recurrence relation is:

$$dp[n] = dp[n-1] + dp[n-2]$$



For Knapsack, it represents the choice of either skipping or taking an item:

$$dp[i][w] = \max(dp[i-1][w], \; dp[i-1][w-\text{weight}[i]] + \text{value}[i])$$



---

## 3. Base Case

The **Base Case** is the smallest, most fundamental subproblem whose answer is already known upfront without needing any calculation.

* **Why it matters:** Because DP states depend on smaller states, the chain has to stop somewhere. Without a base case, your recurrence relation would loop infinitely into negative numbers or out-of-bounds memory.
* **Real-World Analogy:** Knowing that $0! = 1$ or that you start a game at Level 1 with $0.
* **Example in CP:** In climbing stairs (where you can take 1 or 2 steps), the base cases are: `dp[0] = 1` (1 way to stay at the ground) and `dp[1] = 1` (1 way to reach the first step).

---

## 4. Memoization (Top-Down Approach)

**Memoization** is the technique of solving a problem recursively by starting at the final, ultimate goal and breaking it down backward. Crucially, whenever you calculate the answer to a state, you write it down in a lookup table (cache) so that if you encounter that state again, you return the cached answer instantly in $O(1)$ time.

* **The Flow:** Start at `Solve(N)` $\rightarrow$ Ask for `Solve(N-1)` $\rightarrow$ Keep drilling down to the Base Case $\rightarrow$ Return the values back up while saving them.
* **Keywords:** Recursion, Cache, Look-up, "Top-Down".

---

## 5. Tabulation (Bottom-Up Approach)

**Tabulation** is the technique of solving a problem iteratively by starting directly at the **Base Cases** and filling out a table (an array or matrix) sequentially until you reach the final target state.

* **The Flow:** Initialize `dp[0]`, then use a loop (`for i from 1 to N`) to build up `dp[i]` step-by-step using the values you *just* calculated.
* **Keywords:** Iterative, For-loops, Table-filling, "Bottom-Up".
* **Why CPers love it:** It completely avoids the overhead of function calls on the recursion stack, preventing "Stack Overflow" errors on large inputs.

---

## 6. Optimal Substructure & Overlapping Subproblems

These are the two **prerequisites** a problem must possess for DP to even be a valid solution:

* **Optimal Substructure:** A global optimal solution can be constructed optimally from the optimal solutions of its local subproblems. (e.g., The shortest path from Pune to Delhi via Mumbai contains the absolute shortest path from Pune to Mumbai).
* **Overlapping Subproblems:** The naive recursive execution path naturally visits the exact same states over and over again. If the states were completely unique every time, caching them would be useless.

---

### Putting It All Together: The DP Workflow

When you approach a CP problem, your mental pipeline follows these exact definitions in order:

1. Define the **DP State** (What variables do I need to track?).
2. Pin down the **Base Cases** (What do I know for free?).
3. Craft the **Recurrence Relation** (How do I transition between states based on my choices?).
4. Choose the strategy: Implement via **Tabulation** or **Memoization**.