The key to mastering **permutations** and **combinations** isn't memorizing formulas ($n!$, $\frac{n!}{(n-r)!}$, etc.); it is building a solid **mental model of counting**.

Let's build that mathematical intuition step-by-step using concrete, real-world examples.

---

### Step 1: The Fundamental Rule of Counting (The Foundation)

Before getting into permutations or combinations, ask yourself: **"How do choices multiply?"**

#### Example: Building an Outfit

Suppose you have:

* **3 Shirts:** Red, Blue, Green
* **2 Pants:** Jeans, Chinos

How many distinct outfits can you make?
For *every single shirt* you pick, you have *2 choices of pants*.

* Red Shirt $\rightarrow$ Jeans, Chinos (2 outfits)
* Blue Shirt $\rightarrow$ Jeans, Chinos (2 outfits)
* Green Shirt $\rightarrow$ Jeans, Chinos (2 outfits)

**Total Outfits** = $3 \times 2 = 6$.

> **Rule #1 (The Multiplication Principle):** If Event A can happen in $a$ ways, and for each of those ways Event B can happen in $b$ ways, then the total number of ways both happen together is **$a \times b$**.

---

### Step 2: Permutations (When ORDER Matters)

Now, what if you are filling specific, distinct slots?

#### Concrete Example: A Race with 4 Runners (A, B, C, D)

You want to assign **1st, 2nd, and 3rd place medals** (Gold, Silver, Bronze).

Imagine 3 empty slots: `[ Gold ] [ Silver ] [ Bronze ]`

1. **Gold Slot:** How many people can win Gold? **4 options** (A, B, C, or D).
2. **Silver Slot:** Gold has been chosen. How many people are left for Silver? **3 options**.
3. **Bronze Slot:** Gold and Silver are chosen. How many people are left for Bronze? **2 options**.

Using our Multiplication Principle:


$$\text{Total ways to assign medals} = 4 \times 3 \times 2 = 24 \text{ ways}$$

Notice that `[A = Gold, B = Silver]` is **completely different** from `[B = Gold, A = Silver]`.

* Because changing the order changes the outcome, **Order Matters**.
* **Permutation = Ordering / Arranging / Slot-filling.**

---

### Step 3: Combinations (When ORDER DOES NOT Matter)

Now let's look at what happens when slots don't have titles like "Gold" or "Silver."

#### Concrete Example: Selecting a Committee

Using the same 4 runners (A, B, C, D), you want to choose **3 runners to form a team**.

If you pick:

* Choice 1: **A, B, and C**
* Choice 2: **C, B, and A**

Is there any difference between Team (A, B, C) and Team (C, B, A)? **No!** They are the exact same group of people standing on the field.

So how do we calculate this mathematically?

#### The "Overcounting" Trick

1. **First, pretend order matters** (like in Step 2):

$$4 \times 3 \times 2 = 24$$


2. **Second, fix the overcounting:**
Take a single group of 3 people, say **{A, B, C}**. How many different ways can those 3 people be arranged among themselves?
* A, B, C
* A, C, B
* B, A, C
* B, C, A
* C, A, B
* C, B, A


That's $3 \times 2 \times 1 = 6$ arrangements (or $3!$) for **one single team**.
3. **Divide by the duplicates:**
Since every unique team of 3 was counted 6 times in our permutation calculation, we divide by 6:

$$\text{Actual Combinations} = \frac{24}{6} = 4 \text{ teams}$$



The 4 possible teams are simply: `{A, B, C}`, `{A, B, D}`, `{A, C, D}`, `{B, C, D}`.

> **Combination = Selection / Grouping.**
> $$\text{Combination} = \frac{\text{Permutations (Arrangements)}}{\text{Duplicate Orders}}$$
> 
> 

---

### Summary: Permutation vs. Combination Matrix

| Feature | Permutation | Combination |
| --- | --- | --- |
| **Core Idea** | **Arrangement** into distinct slots/positions | **Selection** into a single bucket |
| **Keyword** | Order matters | Order does *not* matter |
| **Real World Examples** | Lock passwords, race podiums, assigning roles (President, VP, Secretary) | Picking a hand of cards, selecting committee members, picking pizza toppings |
| **Formula Mindset** | Fill slots one by one ($n \times (n-1) \times \dots$) | Calculate permutations, then **divide** by $r!$ to eliminate duplicate arrangements |

---

### A Quick Mental Checklist for Any Problem

When given a problem, run this 2-step mental test:

1. **Can I use the Multiplication Principle?**
* Draw empty slots `[  ] [  ] [  ]` and see how many choices remain for each slot.


2. **Does swapping two items create a NEW outcome?**
* **YES:** It's a **Permutation**. (e.g., swapping passcode digits `1-2` to `2-1` changes the passcode).
* **NO:** It's a **Combination**. (e.g., swapping `Pepperoni & Mushrooms` on a pizza to `Mushrooms & Pepperoni` gives you the exact same pizza $\rightarrow$ divide by duplicate orders!).


----

That core question — **"Why does choosing just the down moves (or just the right moves) give us the ENTIRE answer without missing anything?"** — is the exact moment standard combination formulas feel unintuitive.

Let's break this down step-by-step with a concrete visual grid so it clicks mathematically.

---

### Step 1: Why $m + n - 2$ Total Moves?

Imagine a $3 \times 7$ grid ($m = 3$ rows, $n = 7$ columns). You start at top-left $(0,0)$ and want to reach bottom-right $(2,6)$.

To get there, no matter which path you take, you MUST make:

* **Down moves ($D$):** $m - 1 = 3 - 1 = 2$ moves down.
* **Right moves ($R$):** $n - 1 = 7 - 1 = 6$ moves right.

$$\text{Total Moves} = (m - 1) + (n - 1) = m + n - 2 = 2 + 6 = 8 \text{ total steps}$$

Every single valid path is simply a **sequence of 8 steps** consisting of exactly **2 $D$'s** and **6 $R$'s**.

Examples of valid paths:

* Path 1: `R, R, R, R, R, R, D, D`
* Path 2: `D, R, R, D, R, R, R, R`
* Path 3: `R, D, R, D, R, R, R, R`

---

### Step 2: The "Slot-Filling" Intuition

Think of a path as **8 empty slots in a line**:

`[ Slot 1 ] [ Slot 2 ] [ Slot 3 ] [ Slot 4 ] [ Slot 5 ] [ Slot 6 ] [ Slot 7 ] [ Slot 8 ]`

Every path is formed by placing **2 $D$'s** and **6 $R$'s** into these 8 slots.

Now, here is the key realization: **Once you choose WHICH 2 slots get the $D$'s, the remaining 6 slots MUST automatically be $R$'s.**

Let's test this:

* Pick slots **{1, 8}** for $D$:
`[ D ] [ R ] [ R ] [ R ] [ R ] [ R ] [ R ] [ D ]` $\rightarrow$ Valid unique path!
* Pick slots **{2, 4}** for $D$:
`[ R ] [ D ] [ R ] [ D ] [ R ] [ R ] [ R ] [ R ]` $\rightarrow$ Valid unique path!

> **The Big Aha! Moment:**
> Finding a unique path is NOT about deciding every step independently.
> It is purely about **selecting which 2 slots out of 8 will hold the $D$ moves**.
> That is literally the definition of a combination: choosing $k$ items from $N$ total items!
> $$\binom{N}{k} = \binom{\text{totalMoves}}{\text{downMoves}} = \binom{8}{2}$$
> 
> 

---

### Step 3: Why don't we need to choose both $D$ AND $R$?

You asked: *"Why do we only choose $m$ or $n$?"*

Because **$D$ and $R$ are complementary**.

If you have 8 total slots and you choose 2 slots for $D$:

* How many choices do you have left for $R$? **Zero!** They *have* to fill the remaining 6 slots.

Mathematically, choosing 2 Down moves out of 8 gives the exact same result as choosing 6 Right moves out of 8:

$$\binom{8}{2} = \frac{8 \times 7}{2 \times 1} = 28 \quad \text{and} \quad \binom{8}{6} = \frac{8 \times 7 \times 6 \times 5 \times 4 \times 3}{6 \times 5 \times 4 \times 3 \times 2 \times 1} = 28$$

In general:


$$\binom{N}{k} = \binom{N}{N - k}$$

That's why the code does `int k = Math.min(m - 1, n - 1);`. Choosing the smaller number keeps the multiplication loop as short as possible!

---

### Step 4: Connecting it to the Code Loop

In your code:

```java
int totalMoves = m + n - 2; // N
int k = Math.min(m - 1, n - 1); // k
long res = 1;

for (int i = 1; i <= k; i++) {
    res = res * (totalMoves - k + i) / i;
}

```

This loop is evaluating the combination formula $\binom{N}{k} = \frac{N \times (N-1) \times \dots \times (N - k + 1)}{1 \times 2 \times \dots \times k}$ iteratively to avoid integer overflow!

For $N = 8, k = 2$:

* **Iteration 1 ($i=1$):** `res = 1 * (8 - 2 + 1) / 1` $= 7 / 1$
* **Iteration 2 ($i=2$):** `res = 7 * (8 - 2 + 2) / 2` $= (7 \times 8) / 2 = 28$

---

### Summary Checklist

1. **Total slots to fill:** $N = (m - 1) + (n - 1) = m + n - 2$.
2. **Each slot is either $D$ or $R$.**
3. **Choosing positions for $D$ automatically locks in positions for $R$.**
4. **Order among identical moves doesn't matter:** Down step #1 and Down step #2 are indistinguishable, so it's a **Combination** ($\binom{N}{k}$), not a Permutation.

---

The short answer is: **Yes, the underlying counting principle is identical!**

In both problems, you are arranging a sequence of decisions into slots. But there is **one massive difference** in how the rules are set up, and understanding that difference will make dynamic programming and combinatorics click together.

---

### Comparison at a Glance

| Feature | Unique Paths ($m \times n$ Grid) | Climbing Stairs ($N$ Steps) |
| --- | --- | --- |
| **Total Number of Moves** | **Fixed** ($m + n - 2$ moves every time) | **Variable** (depends on how many 2-steps you take) |
| **Count Type** | **Single Combination:** $\binom{\text{Total}}{\text{Down}}$ | **Sum of Combinations:** $\sum \binom{\text{Total}}{\text{Two-Steps}}$ |

---

### Step-by-Step Logic for Climbing Stairs

Suppose you want to climb **$N = 5$ stairs**, taking either **1 step ($S_1$)** or **2 steps ($S_2$)**.

Unlike the grid problem, you don't know your total number of moves upfront because taking a 2-step reduces the total number of moves!

So, you break it down by **how many 2-steps ($S_2$) you decide to take**:

#### Case 1: Take zero 2-steps ($0 \times S_2$, five $S_1$)

* **Moves sequence:** `[1, 1, 1, 1, 1]` (5 total moves)
* **Selecting positions for zero $S_2$ out of 5 slots:**

$$\binom{5}{0} = 1 \text{ way}$$



#### Case 2: Take one 2-step ($1 \times S_2$, three $S_1$)

* **Total stairs covered:** $2 + 1 + 1 + 1 = 5$
* **Total moves/slots:** $1 + 3 = 4$ slots to fill: `[  ] [  ] [  ] [  ]`
* **Selecting 1 position for the $S_2$ move out of 4 total slots:**

$$\binom{4}{1} = 4 \text{ ways}$$



*(The $S_2$ can be at step 1, step 2, step 3, or step 4: `[2,1,1,1]`, `[1,2,1,1]`, `[1,1,2,1]`, `[1,1,1,2]`)*

#### Case 3: Take two 2-steps ($2 \times S_2$, one $S_1$)

* **Total stairs covered:** $2 + 2 + 1 = 5$
* **Total moves/slots:** $2 + 1 = 3$ slots to fill: `[  ] [  ] [  ]`
* **Selecting 2 positions for the $S_2$ moves out of 3 total slots:**

$$\binom{3}{2} = 3 \text{ ways}$$



*(`[2,2,1]`, `[2,1,2]`, `[1,2,2]`)*

---

### Summing Up All Cases

Total Ways to Climb 5 Stairs = Case 1 + Case 2 + Case 3


$$\text{Total} = \binom{5}{0} + \binom{4}{1} + \binom{3}{2} = 1 + 4 + 3 = 8 \text{ ways}$$

Notice that **8** is the $6^\text{th}$ Fibonacci number!

---

### Why DP / Fibonacci is Preferred over Combinations Here

To compute *Unique Paths*, you only evaluate **one single combination** ($\binom{m+n-2}{m-1}$).

To compute *Climbing Stairs* using combinations, you would have to loop through all possible counts of 2-steps ($k$) and sum them up:

$$\text{Total Ways} = \sum_{k=0}^{\lfloor N/2 \rfloor} \binom{N - k}{k}$$

While mathematically beautiful, it requires multiple combination calculations. That's why in coding interviews, we usually compute Climbing Stairs using **Dynamic Programming / Fibonacci** ($dp[i] = dp[i-1] + dp[i-2]$) in $O(N)$ time instead!