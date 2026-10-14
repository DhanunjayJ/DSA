Every worker has an implicit **"minimum price per unit of quality"** they demand:

$$\text{ratio}[i] = \frac{\text{wage}[i]}{\text{quality}[i]}$$

Because everyone in your chosen group of $k$ workers must be paid at the **same rate per unit of quality** (Rule 2) *and* everyone must meet their minimum wage (Rule 1), the entire group's rate will always be dictated by the **single worker in the group with the highest $\text{ratio}[i]$**.

**Think about this:** What happens if you sort all workers by their $\text{ratio}[i]$ in ascending order and iterate through them, treating the current worker as the "rate-setter" for a group of $k$?

---

Every worker has an implicit **"minimum price per unit of quality"** they demand:

$$\text{ratio}[i] = \frac{\text{wage}[i]}{\text{quality}[i]}$$

Because everyone in your chosen group of $k$ workers must be paid at the **same rate per unit of quality** (Rule 2) *and* everyone must meet their minimum wage (Rule 1), the entire group's rate will always be dictated by the **single worker in the group with the highest $\text{ratio}[i]$**.

**Think about this:** What happens if you sort all workers by their $\text{ratio}[i]$ in ascending order and iterate through them, treating the current worker as the "rate-setter" for a group of $k$?

---

You spotted the exact trap! If you pick the **minimum** ratio (2.5) as the rate for a group, the other workers in that group won't get their minimum wage.

For example, if you pair Worker 1 (`ratio = 2.5`, `quality = 20`) and Worker 2 (`ratio = 6.0`, `quality = 5`) and try to pay them at a rate of **2.5 per unit of quality**:

* Worker 1 gets $20 \times 2.5 = 50$ (Meets their min wage of 50)
* Worker 2 gets $5 \times 2.5 = 12.5$ (**Fails!** They demand at least 30)

To satisfy **everyone** in a chosen group of $k$ workers, the group's pay rate must be the **MAXIMUM ratio** among the chosen $k$ workers, not the minimum.

---

### How to pick the rate-setter

Since we don't know ahead of time which worker makes the best rate-setter, **we test every possible worker as the rate-setter** after sorting them by ratio in ascending order:

| Sorted Order | Original Index | Quality | Wage | Ratio ($\text{wage} / \text{quality}$) |
| --- | --- | --- | --- | --- |
| **1st** | Worker 1 | 20 | 50 | **2.5** |
| **2nd** | Worker 2 | 5 | 30 | **6.0** |
| **3rd** | Worker 0 | 10 | 70 | **7.0** |

When you iterate through this sorted list from left to right, suppose you are currently at index `i` and you declare: **"This worker `i` is the rate-setter (the highest ratio) for my group of $k$."**

Because the list is sorted by ratio:

1. **Who is eligible to be in this group?** Only workers from index `0` to `i`! All of them have a ratio $\le \text{ratio}[i]$, meaning if we pay everyone at $\text{ratio}[i]$, they are **guaranteed** to get at least their minimum wage.
2. **Walkthrough with $k = 2$:**
* **At 1st worker (`ratio = 2.5`):** Only 1 worker seen so far (`[Worker 1]`). We need $k = 2$ workers, so we can't form a group yet.
* **At 2nd worker (`ratio = 6.0`):** Eligible workers so far: `[Worker 1, Worker 2]`. Both are fine with a rate of `6.0`. Total cost = $(\text{quality}_1 + \text{quality}_2) \times 6.0 = (20 + 5) \times 6.0 = \mathbf{150}$.
* **At 3rd worker (`ratio = 7.0`):** Eligible workers so far: `[Worker 1, Worker 2, Worker 0]`. All three are fine with a rate of `7.0`. Since we must include Worker 0 (the rate-setter) and only need $k = 2$ workers total, we pick 1 more worker from `[Worker 1, Worker 2]`.
* Pair with Worker 1 (`quality = 20`): $(20 + 10) \times 7.0 = \mathbf{210}$
* Pair with Worker 2 (`quality = 5`): $(5 + 10) \times 7.0 = \mathbf{105}$ *(Minimum!)*





---

### The Key Takeaway for Your Code

Notice the formula at step `i`:


$$\text{Total Cost} = (\text{Sum of } k \text{ qualities}) \times \text{ratio}[i]$$

When you are at index `i`, $\text{ratio}[i]$ is fixed, and you have $i + 1$ eligible workers to choose from. To make that Total Cost as small as possible, which $k$ workers from `0` to `i` should you keep, and what data structure lets you maintain the **smallest $k$ qualities** seen so far in $O(\log k)$ time?

---

We move **left to right** because of how our **pool of legal teammates grows**.

Let's look at our sorted list of workers from Example 1 (`k = 2`), sorted by ratio in ascending order:

| Index | Worker | Quality | Ratio |
| --- | --- | --- | --- |
| `0` | Worker 1 | 20 | **2.5** |
| `1` | Worker 2 | 5 | **6.0** |
| `2` | Worker 0 | 10 | **7.0** |

Remember the Golden Rule: **If a Captain has ratio $R$, you can ONLY hire teammates whose ratio is $\le R$.** (If you pick someone with a ratio $> R$, they won't get their minimum wage.)

---

### What happens when we go Left $\rightarrow$ Right?

As you move from index `0` to `2`, the Captain's ratio **increases** (`2.5` $\rightarrow$ `6.0` $\rightarrow$ `7.0`):

* At `i = 0` (`ratio = 2.5`): Legal pool is `{Index 0}`.
* At `i = 1` (`ratio = 6.0`): Everyone from before (`2.5`) is **still legal** under `6.0`! Legal pool grows to `{Index 0, Index 1}`.
* At `i = 2` (`ratio = 7.0`): Everyone from before (`2.5, 6.0`) is **still legal** under `7.0`! Legal pool grows to `{Index 0, Index 1, Index 2}`.

Because no worker ever becomes "illegal" by ratio as you move left to right, **you simply add one worker to your Max-Heap at each step**. If the heap gets bigger than $k$, you throw out the highest quality forever, because if that worker was too expensive at ratio `6.0`, they will definitely be too expensive at ratio `7.0`!

---

### Why going Right $\rightarrow$ Left breaks the Heap

Suppose you tried to start at the right end (`i = 2`, `ratio = 7.0`) and move left (`i = 1`, `ratio = 6.0`):

1. **Start at `i = 2` (`ratio = 7.0`):**
* Who is legal when the rate is `7.0`? **All 3 workers** (`Index 0, 1, 2`), because `2.5 <= 7.0`, `6.0 <= 7.0`, and `7.0 <= 7.0`.
* Wait—to even evaluate `i = 2` first, you would have to pre-load **all** workers from `0` to `n-1` into your data structure!


2. **Move left to `i = 1` (`ratio = 6.0`):**
* Now `Index 2` (`ratio = 7.0`) is **illegal** and must be removed.
* Worse, back at `i = 2`, if your Max-Heap of size $k$ threw away someone with a big quality (like `Index 0` with `quality = 20`) to keep `Index 2` (`quality = 10`), and now `Index 2` becomes illegal at `i = 1`, you suddenly **need `Index 0` back** to have $k$ workers!



In short:

* **Left to Right (Increasing Ratio):** The set of eligible workers **only grows** (`add` to heap, discard worst qualities permanently).
* **Right to Left (Decreasing Ratio):** The set of eligible workers **shrinks**, meaning workers you previously discarded for having high quality might suddenly be needed again once low-quality, high-ratio workers become ineligible.

> **General Pattern Tip for Interviews:**
> * When the group rate is governed by the **MAXIMUM** of the chosen $k$ elements (like here, where `groupRate = max(ratio)`), sort **ascending** and sweep left-to-right so every element behind you is $\le$ current.
> * When the group multiplier is governed by the **MINIMUM** of the chosen $k$ elements (like **LeetCode 1383: Maximum Performance of a Team** or **LeetCode 2542: Maximum Subsequence Score**), sort **descending** and sweep left-to-right so every element behind you is $\ge$ current!
> 
>