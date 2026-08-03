Your current approach is highly efficient! You essentially built a greedy algorithm that runs in $O(n)$ time and $O(1)$ space. Tracking the minimum price seen so far and checking the profit against it is the most optimal way to solve this specific problem.

However, exploring the **Dynamic Programming (DP)** approach is a fantastic idea because it sets up a foundational template. While DP might feel like overkill for *this* specific problem, this exact DP state machine is the key to solving the much harder variations of this problem (like when you are allowed 2 transactions, $k$ transactions, or unlimited transactions with cooldowns).

Here is how to think about this problem using Dynamic Programming.

### 1. Define the States

On any given day `i`, you can only be in one of two states:

1. **Not holding a stock (`dp[i][0]`):** You either didn't buy one yet, or you already bought and sold it.
2. **Holding a stock (`dp[i][1]`):** You bought a stock on day `i` or on some previous day, and you haven't sold it yet.

### 2. Define the Transitions (The Choices)

For each day, we look at the previous day to decide our maximum possible profit for the current day.

* **To end up with 0 stocks today (`dp[i][0]`):**
* *Choice A:* We had 0 stocks yesterday, and we rest today. Profit remains `dp[i-1][0]`.
* *Choice B:* We had 1 stock yesterday, and we sell it today at today's price. Profit becomes `dp[i-1][1] + prices[i]`.
* *Transition:* `dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i])`


* **To end up with 1 stock today (`dp[i][1]`):**
* *Choice A:* We had 1 stock yesterday, and we hold it. Profit remains `dp[i-1][1]`.
* *Choice B:* We buy a stock today. Because we are only allowed **one transaction total**, buying a stock means our profit goes from exactly 0 down to `-prices[i]`.
* *Transition:* `dp[i][1] = Math.max(dp[i-1][1], -prices[i])`



### 3. Initialize the Base Cases

On day 0 (the first day):

* `dp[0][0] = 0` (If we don't buy, our profit is 0).
* `dp[0][1] = -prices[0]` (If we buy, our profit is negative the price of the stock).

---

### The 2D DP Implementation

Here is the formal DP approach mapped into Java code:

```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        
        int n = prices.length;
        
        // dp[i][0] = max profit on day i with 0 stocks in hand
        // dp[i][1] = max profit on day i with 1 stock in hand
        int[][] dp = new int[n][2];
        
        // Base cases for day 0
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        
        for (int i = 1; i < n; i++) {
            // Either keep 0 stocks, or sell the 1 stock we had
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            
            // Either keep the 1 stock we had, or buy today's stock
            dp[i][1] = Math.max(dp[i-1][1], -prices[i]); 
        }
        
        // Return the max profit on the last day with 0 stocks in hand
        return dp[n-1][0];
    }
}

```

### Space Optimization: Bridging DP and Your Solution

The 2D array takes $O(n)$ space. But notice that `dp[i]` *only* relies on `dp[i-1]`. We don't need to keep the whole table; we only need the values from the previous day.

We can optimize the space to $O(1)$ using two variables:

```java
class Solution {
    public int maxProfit(int[] prices) {
        // Equivalent to dp[0][0]
        int notHold = 0; 
        
        // Equivalent to dp[0][1]
        int hold = -prices[0]; 
        
        for (int i = 1; i < prices.length; i++) {
            notHold = Math.max(notHold, hold + prices[i]);
            hold = Math.max(hold, -prices[i]);
        }
        
        return notHold;
    }
}

```

If you look closely at this $O(1)$ DP version, it's mathematically doing the exact same thing as your original solution! The `hold` variable acts as `-min` (keeping track of the lowest stock price), and `notHold` acts as your `maxProfit` variable.

---

The 3-state approach is a fantastic way to visualize this problem because it perfectly maps to the real-world timeline of a single transaction. Instead of just tracking whether you are holding a stock or not, you are tracking the exact phase of the transaction lifecycle.

## The 3-State Machine Explained

At any given day, you can only be in one of three phases:

| State | Phase | Available Choices | Transition |
| --- | --- | --- | --- |
| **0** | **Ready to Buy** | Buy today <br>

<br> Skip today | Go to State 1 <br>

<br> Stay in State 0 |
| **1** | **Holding Stock** | Sell today <br>

<br> Skip today | Go to State 2 <br>

<br> Stay in State 1 |
| **2** | **Done** | Skip today (transaction is over) | Stay in State 2 |

Because you are only allowed one transaction (Best Time to Buy and Sell Stock I), State 2 acts as a terminal phase. Once you enter it, you can no longer buy or sell.

---

## 1. Top-Down Recursive (Memoization)

This approach starts at day 0 and uses recursion to explore every possible choice, storing the results in a `dp` array to avoid recalculating the same states.

```java
class Solution {
    int n;
    int[] p;
    int[][] dp;

    public int maxProfit(int[] prices) {
        n = prices.length;
        p = prices;
        
        // dp[i][b] where i is the day, and b is the state (0, 1, or 2)
        dp = new int[n][3];
        
        // Initialize memoization table with -1 (uncalculated)
        for(int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        
        // Start on day 0, in state 0 (Ready to Buy)
        return f(0, 0);
    }

    public int f(int i, int b) {
        // Base case: If we run out of days, no more profit can be made
        if (i >= n) return 0;
        
        // If we already calculated this exact state, return it instantly
        if (dp[i][b] != -1) return dp[i][b];
        
        int doAction = 0;
        
        // Choice 1: Do nothing today, carry over the current state to tomorrow
        int skip = f(i + 1, b);
        
        // Choice 2: Take action based on our current state
        if (b == 0) {
            // We can buy. Profit drops by price, state moves to 1 (Holding)
            doAction = -p[i] + f(i + 1, 1);
        } else if (b == 1) {
            // We can sell. Profit increases by price, state moves to 2 (Done)
            doAction = p[i] + f(i + 1, 2);
        }
        // Notice we do nothing if b == 2, because the transaction is already done
        
        // Store the maximum of skipping vs taking action
        int ans = Math.max(skip, doAction);
        dp[i][b] = ans;
        
        return ans;
    }
}

```

---

## 2. Bottom-Up Iterative (Tabulation)

This approach translates the recursion into a standard loop. Because day `i` depends on the answers for day `i + 1`, we must build our table backward, starting from the last day down to day 0.

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        
        // dp[i][b] stores the max profit from day 'i' to the end of the array.
        // We use n + 1 rows so that dp[n][...] automatically holds 0 (our base case).
        int[][] dp = new int[n + 1][3];
        
        // Loop backward from the last day down to the first day
        for (int i = n - 1; i >= 0; i--) {
            
            // State 2: Done
            // We can only skip. Our profit is whatever we'd get tomorrow in State 2 (which is 0)
            dp[i][2] = dp[i + 1][2]; 
            
            // State 1: Holding Stock (Ready to Sell)
            // We can either skip selling today, or sell today and move to State 2
            int skipSell = dp[i + 1][1];
            int doSell = prices[i] + dp[i + 1][2];
            dp[i][1] = Math.max(skipSell, doSell);
            
            // State 0: Ready to Buy
            // We can either skip buying today, or buy today and move to State 1
            int skipBuy = dp[i + 1][0];
            int doBuy = -prices[i] + dp[i + 1][1];
            dp[i][0] = Math.max(skipBuy, doBuy);
        }
        
        // The answer is the maximum profit starting on day 0, in state 0
        return dp[0][0];
    }
}

```

Both approaches run in $O(n)$ time and $O(n)$ space, though the iterative approach will execute slightly faster because it completely avoids the overhead of the recursive call stack.