This is the perfect problem to illustrate why Greedy is often called "Short-sighted." In the **Candy** problem, the "Global Best" is to use the absolute minimum total candies while satisfying two local neighbors.

Here is the breakdown of the Local vs. Global best for your code:

### 1. The Local Best (The Greedy Choice)
In this problem, the "Local Best" is to look at **only one neighbor at a time** and satisfy that specific relationship with the smallest possible increment.

*   **Left-to-Right Pass:** Your "Local Choice" is: *"I only care about my neighbor on the left. If I'm better than them, I'll take just one more candy than they have."*
*   **Right-to-Left Pass:** Your "Local Choice" is: *"I only care about my neighbor on the right. If I'm better than them, I'll take just enough to beat them."*

**The Local Logic:** "If I satisfy my left neighbor now, and then satisfy my right neighbor later, surely the whole row will be satisfied."

### 2. The Global Best (The Goal)
The "Global Best" is the total sum of the `candies` array. To achieve this, you need to satisfy the rule: **"If I am higher rated than BOTH neighbors, I must have more than the maximum of the two."**

### Why do we need TWO passes? (The "Regret" Factor)
This is where Greedy gets interesting. If you only did the first pass (Left-to-Right), you might finish and then **regret** your choices because you didn't know what was happening on the right.

**Example:** Ratings `[1, 2, 5, 4, 3, 2, 1]`

1.  **First Pass (Left-to-Right):** 
    You get `[1, 2, 3, 1, 1, 1, 1]`. 
    *   *Local Best:* You satisfied the `1 < 2 < 5` part. 
    *   *The Regret:* The `5` doesn't know that there is a long downward slope to its right (`4, 3, 2, 1`).

2.  **Second Pass (Right-to-Left):** 
    You look at the `5` again. From the right side, the slope `5 > 4 > 3 > 2 > 1` suggests the `5` needs **5 candies**. 
    *   *The Decision:* You see the `5` already has `3` candies (from the first pass) but needs `5` (from the second pass). You take the `Math.max(3, 5)`.

### The "Greedy" Secret here:
The "Global Best" is achieved by taking two "Local Best" snapshots and merging them. 
*   **Pass 1** gives you the minimum candies to satisfy **left** constraints.
*   **Pass 2** gives you the minimum candies to satisfy **right** constraints.
*   **Math.max** ensures that **both** are satisfied simultaneously.

**In Greedy terms:** You are being "Selfish" twice. First, selfishly satisfying the left-side rules, then selfishly satisfying the right-side rules. Because these two requirements are independent (left-neighbor vs. right-neighbor), combining the local maximums leads to the global minimum.

Does it make sense why `Math.max` is the "bridge" between the two local greedy choices?