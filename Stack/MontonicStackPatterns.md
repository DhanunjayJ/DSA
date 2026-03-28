

### 1. The Core Pattern: "The Interrupter"
The "Aha!" moment for a monotonic stack is when you realize you are looking for the **Next Greater Element (NGE)** or **Next Smaller Element (NSE)**.

* **Monotonic Increasing Stack:** You want to keep things in increasing order ($1, 2, 5, 10$).
    * **The Trigger:** You encounter a value **smaller** than the top of the stack.
    * **The Action:** This "smaller" value **interrupts** the peace. You must pop the larger elements to make room for it.
* **Monotonic Decreasing Stack:** You want to keep things in decreasing order ($10, 5, 2, 1$).
    * **The Trigger:** You encounter a value **larger** than the top of the stack.
    * **The Action:** This "larger" value **interrupts** the peace. You must pop the smaller elements.

---

### 2. Which one to use? (The Cheat Sheet)

| Problem Type | Goal | Which Stack? | Why? |
| :--- | :--- | :--- | :--- |
| **Remove K Digits** | Make the number as small as possible. | **Increasing** | You want small digits at the start. If you see a smaller digit later, pop the "peaks" (larger ones) before it. |
| **Daily Temperatures** | Find the next warmer day. | **Decreasing** | You keep "cold" days in the stack. When a "hot" day comes, it "solves" the wait for all the cold days in the stack. |
| **Rain Water Trapping** | Find a "valley" that can hold water. | **Decreasing** | You need a left wall (high), a middle (low), and a right wall (high). A decreasing stack stores the left wall and the floor; a **larger** value (right wall) triggers the calculation. |
| **Sum of Subarray Minimums** | Find the range where a number is the smallest. | **Increasing** | You want to find the "Next Smaller Element" on both sides. The stack stays increasing until it hits a smaller element, which defines the boundary of that number's "dominance." |



---

### 3. How to "See" the Pattern in a Problem
Ask yourself these two questions:

#### A. Does the "current" element solve a problem for "past" elements?
In **Rain Water Trapping**, the current tall bar "traps" water for all the shorter bars you saw previously. In **Sum of Subarray Minimums**, the current smaller element "ends" the range for the larger elements currently in the stack.
* If **Yes** $\rightarrow$ Use a Stack.

#### B. Do I want to eliminate "Peaks" or "Valleys"?
* **To eliminate Peaks (to get a minimum):** Use an **Increasing** stack. It forces the big numbers out. (Example: *Remove K Digits*).
* **To eliminate Valleys (to get a maximum):** Use a **Decreasing** stack. It forces the small numbers out. (Example: *Largest Rectangle in Histogram*).
