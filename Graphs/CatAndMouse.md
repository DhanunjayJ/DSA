Forget the code and the algorithms completely for a moment. Let's start with just the **rules of the game**.

Imagine a board with rooms numbered $0, 1, 2, \dots$ connected by hallways.

* **Room 0** is the **Hole** (safety for Mouse).
* **Mouse** starts at **Room 1**.
* **Cat** starts at **Room 2**.
* **Mouse always moves first**, then Cat, then Mouse, alternating turns.

---

### How they move:

* On your turn, you **must** move to an adjacent connected room.
* **One restriction:** Cat is terrified of the Hole, so **Cat can never step into Room 0**. Mouse can.

---

### How someone wins:

1. **Mouse wins (`1`)**: Mouse reaches **Room 0**.
2. **Cat wins (`2`)**: Cat lands on the **exact same room** where Mouse is.
3. **Draw (`0`)**: They run in circles forever and neither can force a win.

---

Now let's see why a normal computer program gets trapped when trying to solve this.

Usually, to solve a two-player game, we use recursion:

> "Mouse tries a move $\rightarrow$ Cat responds $\rightarrow$ Mouse responds $\rightarrow$ ..."

### The Trap: Cycles (Infinite Loops)

Look at this simple 3-room loop:
`Room 1` is connected to `Room 2`, which is connected to `Room 3`, which connects back to `1`.

```text
(1) --- (2)
  \     /
    (3)

```

If Mouse is at `1` and Cat is at `2`:

1. Mouse moves to `3`.
2. Cat moves to `1`.
3. Mouse moves back to `2`.
4. Cat moves to `3`...

They can chase each other around this circle **forever**.

If you write a standard recursive function (`dfs`), your code will call itself endlessly:


$$\text{solve}() \to \text{solve}() \to \text{solve}() \to \dots \to \textbf{Stack Overflow Error}$$

---

### The Big Realization

We cannot easily move **forward** from the starting position because we get lost in circles.

So instead of asking:

> *"What should we do from the start?"*

We turn the whole problem around and ask:

> *"Where does the game **definitely end**?"*

----

Now let's define a **snapshot** (a "State") of the game.

Imagine taking a photo of the board at any point during play. What are the **only 3 things** you need to know to resume the game?

1. **Where is Mouse?** $\to m$
2. **Where is Cat?** $\to c$
3. **Whose turn is it?** $\to t$ ($1$ for Mouse, $2$ for Cat)

We write this snapshot as a triplet: **$(m, c, t)$**.

---

### Why does "Whose turn is it" matter?

Look at these two snapshots:

* Snapshot A: $(m=1, c=1, t=\text{Mouse})$
* Snapshot B: $(m=1, c=2, t=\text{Mouse})$ vs. $(m=1, c=2, t=\text{Cat})$

Even if the animals are in the exact same rooms ($m=1, c=2$), the game is totally different depending on whose turn it is:

* If it's **Mouse's turn**, Mouse might run away and survive.
* If it's **Cat's turn**, Cat might pounce on room 1 and win instantly!

So the turn $t$ is an essential part of the state.

---

### How many possible snapshots exist?

If there are $N$ rooms:

* $m$ can be anything from $0$ to $N-1$ ($N$ possibilities).
* $c$ can be anything from $1$ to $N-1$ ($N-1$ possibilities, because Cat never enters 0).
* $t$ has $2$ possibilities (Mouse or Cat).

Total states $\approx N \times N \times 2$.

For LeetCode's constraint where $N \le 50$:


$$50 \times 50 \times 2 = 5,000 \text{ states}$$

That is a very small number! We can easily store all $5,000$ states in an array.

---

Now let's find the **Terminal States**—the snapshots where the game is already over and the winner is 100% certain.

You don't need any algorithm to decide these:

---

### Group 1: Mouse Wins Instantly

Whenever **Mouse is in Room 0** ($m = 0$), Mouse has reached the hole.

* It doesn't matter where Cat is.
* It doesn't matter whose turn it is.
* **Winner = Mouse (`1`)**.

So every state that looks like:


$$(0, c, t) \to \text{MOUSE WINS}$$

---

### Group 2: Cat Wins Instantly

Whenever **Cat lands in the same room as Mouse** ($m = c$, where $m \ne 0$), Cat catches Mouse.

* It doesn't matter which room it is.
* It doesn't matter whose turn it is.
* **Winner = Cat (`2`)**.

So every state that looks like:


$$(m, m, t) \to \text{CAT WINS}$$

---

### What about all the other states?

At the very beginning, we label every other state as **`0` (DRAW / Undecided)**.

Our goal is now clear:
We take these known winning states and put them into a **Queue** (a to-do list), then trace the game **backwards** to see what leads into them.

---

Now comes the core logic: **How does an outcome travel backward?**

Suppose we are working backwards from a state where the outcome is already decided. Let's call that decided state $S_{\text{next}}$.

Now look at a previous state $S_{\text{prev}}$ that can legally step into $S_{\text{next}}$.

Let's assume it was **Mouse's turn** at $S_{\text{prev}}$:

---

### Rule 1: Immediate Win (The "One Good Move" Rule)

Suppose $S_{\text{next}}$ is a **Mouse Win**.

At $S_{\text{prev}}$, Mouse is choosing where to go. Mouse sees this move and thinks:

> *"If I make this move, I win the game!"*

Because players play optimally, Mouse will always pick the winning move. Mouse does not care about any other options.

**Result:** State $S_{\text{prev}}$ is immediately declared a **Mouse Win**. We push $S_{\text{prev}}$ into our queue so it can help resolve earlier states.

---

### Rule 2: Forced Loss (The "Trapped" Rule)

Now suppose $S_{\text{next}}$ is a **Cat Win**.

At $S_{\text{prev}}$, Mouse sees this move leads to a trap. Mouse thinks:

> *"I definitely don't want to make this move."*

Does Mouse instantly lose the game at $S_{\text{prev}}$? **Not necessarily!**
Mouse might still have other hallway doors to escape through.

So, instead of declaring a loss immediately, Mouse simply **crosses this bad move off the list of choices**:


$$\text{remaining\_moves}(S_{\text{prev}}) = \text{remaining\_moves}(S_{\text{prev}}) - 1$$

* If Mouse still has remaining moves left $\to$ Mouse tries those instead. We do nothing else for now.
* **Only if remaining moves hits $0$** $\to$ That means **every single door** leads to the Cat winning. Mouse is completely cornered with nowhere safe to go. Only then is $S_{\text{prev}}$ declared a **Cat Win**!

---

The exact same two rules apply if it were **Cat's turn** at $S_{\text{prev}}$:

* If Cat finds *one* move that leads to Cat Win $\to$ $S_{\text{prev}}$ becomes **Cat Win**.
* If Cat sees a move that leads to Mouse Win $\to$ decrement Cat's remaining moves. If remaining moves hits $0 \to$ $S_{\text{prev}}$ becomes **Mouse Win**.

---

Take a moment with this. Does the distinction between **"one winning move = instant win"** versus **"bad move = cross it off until 0 left"** make sense?

----

Now, let's walk through a **concrete step-by-step trace** on a simple 3-room board so you can watch these two rules work in real time.

---

### The Board

```text
Hole (0) ------- Room (1) ------- Room (2)

```

* **Hole (0)** connects to: `[1]`
* **Room (1)** connects to: `[0, 2]`
* **Room (2)** connects to: `[1]`

**Rules check for this board:**

* Mouse starts at **Room 1**.
* Cat starts at **Room 2**.
* Mouse moves first: initial state is **$(m=1, c=2, t=\text{Mouse})$**.
* Cat cannot step into Hole (0).

---

### Step 1: Count Available Moves (`degree`)

For each state, we count how many doors that player can walk through:

* At state $(1, 2, \text{Mouse})$: Mouse is at room 1. It can move to room `0` or `2`.

$$\text{degree} = 2$$


* At state $(1, 2, \text{Cat})$: Cat is at room 2. It can only move to room `1` (Cat cannot enter 0).

$$\text{degree} = 1$$



---

### Step 2: The Queue Starts with What We Know

We know the terminal states immediately:

1. $(0, 2, \text{Cat})$: Mouse is in the Hole (0) $\to$ **Mouse Win (`1`)**.
2. $(1, 1, \text{Mouse})$: Cat caught Mouse at room 1 $\to$ **Cat Win (`2`)**.
3. $(2, 2, \text{Mouse})$: Cat caught Mouse at room 2 $\to$ **Cat Win (`2`)**.

All these go into our BFS **Queue**.

---

### Step 3: Pop From Queue and Trace Backwards

Let's pop the first state from our Queue:


$$\text{Current state: } (0, 2, \text{Cat}) \quad [\text{Outcome: MOUSE WIN}]$$

Now, ask: **Who could have stepped into this state?**

* The turn is Cat, which means the **previous player who moved was Mouse**.
* Where was Mouse before stepping into room 0? Look at the edges: Mouse must have come from **Room 1**.
* Where was Cat? Still at room 2.
* So the predecessor state is:

$$(m=1, c=2, t=\text{Mouse})$$



Now look at what happened:

* At $(1, 2, \text{Mouse})$, it was Mouse's turn.
* Mouse stepped into $(0, 2, \text{Cat})$, which is a **Mouse Win**.
* This matches **Rule 1 (Immediate Win)**: Mouse found a move that wins the game!

Mouse doesn't care about any other options. Mouse takes this move immediately:


$$\text{color}[1][2][\text{Mouse}] = \text{MOUSE WIN (1)}$$

---

### The Result

We wanted to find the outcome of the game starting at $(1, 2, \text{Mouse})$.
It is already resolved as **`1` (Mouse wins)**!

Notice why:
Mouse is at room 1. Mouse's options are room 0 (the hole) or room 2 (where Cat is). An optimal Mouse simply walks into room 0 on turn 1 and wins instantly.

---

Does seeing how that state popped from the queue, found its previous state, and triggered Rule 1 make sense?


---

Now let's translate all of this directly into the final piece of the puzzle: **the Java code**.

Every line of the code corresponds directly to the rules we just stepped through.

---

### Step-by-Step Code Walkthrough

#### 1. Setup Data Structures

We need two 3D arrays:

* `color[m][c][turn]`: stores the winner ($0$ = Draw, $1$ = Mouse, $2$ = Cat).
* `degree[m][c][turn]`: stores how many legal moves are available from this state.

```java
int[][][] color = new int[n][n][3];
int[][][] degree = new int[n][n][3];

// Initialize degrees (number of choices)
for (int m = 0; m < n; m++) {
    for (int c = 1; c < n; c++) {
        degree[m][c][1] = graph[m].length; // Mouse can move to any neighbor

        int catMoves = 0;
        for (int next : graph[c]) {
            if (next != 0) catMoves++;     // Cat can move anywhere EXCEPT 0
        }
        degree[m][c][2] = catMoves;
    }
}

```

---

#### 2. Seed the Queue with Known Endings

We push all the base winning positions into the queue.

```java
Queue<int[]> queue = new LinkedList<>();

// Mouse reached hole (0) -> Mouse wins
for (int c = 1; c < n; c++) {
    for (int t = 1; t <= 2; t++) {
        color[0][c][t] = 1; // 1 = MOUSE
        queue.offer(new int[]{0, c, t, 1});
    }
}

// Cat caught Mouse (m == c, m != 0) -> Cat wins
for (int m = 1; m < n; m++) {
    for (int t = 1; t <= 2; t++) {
        color[m][m][t] = 2; // 2 = CAT
        queue.offer(new int[]{m, m, t, 2});
    }
}

```

---

#### 3. The BFS Loop (Retrograde Propagation)

We pop a known state $(m, c, t, \text{winner})$ and look at all states that could have stepped into it:

```java
while (!queue.isEmpty()) {
    int[] curr = queue.poll();
    int m = curr[0], c = curr[1], t = curr[2], winner = curr[3];

    // Who moved to reach this state?
    // If current turn is Mouse (1), Cat (2) made the previous move.
    // If current turn is Cat (2), Mouse (1) made the previous move.
    int prevTurn = (t == 1) ? 2 : 1;

    // Find all previous states
    List<int[]> prevStates = new ArrayList<>();
    if (prevTurn == 1) { // Mouse moved from prevM to m
        for (int prevM : graph[m]) {
            prevStates.add(new int[]{prevM, c, 1});
        }
    } else { // Cat moved from prevC to c (cannot be 0)
        for (int prevC : graph[c]) {
            if (prevC != 0) {
                prevStates.add(new int[]{m, prevC, 2});
            }
        }
    }

    // Apply Rule 1 and Rule 2 to every previous state
    for (int[] prev : prevStates) {
        int pm = prev[0], pc = prev[1], pt = prev[2];

        // Skip if already resolved
        if (color[pm][pc][pt] != 0) continue;

        // RULE 1: Player pt found a move that wins for them!
        if (winner == pt) {
            color[pm][pc][pt] = winner;
            queue.offer(new int[]{pm, pc, pt, winner});
        } 
        // RULE 2: Player pt moved into a trap; eliminate this move
        else {
            degree[pm][pc][pt]--;

            // If ALL doors are closed, the player is forced to lose!
            if (degree[pm][pc][pt] == 0) {
                int opponentWinner = (pt == 1) ? 2 : 1; // 2=Cat, 1=Mouse
                color[pm][pc][pt] = opponentWinner;
                queue.offer(new int[]{pm, pc, pt, opponentWinner});
            }
        }
    }
}

```

---

#### 4. The Final Answer

Once the queue is empty:

* If a state was never forced to a win or loss, it stays `0` (**DRAW**).
* We simply return the outcome of the starting board:

```java
return color[1][2][1]; // Mouse at 1, Cat at 2, Mouse's turn (1)

```

Now you have the full picture: from the game rules, to why recursion fails, to state representation, to backward propagation, and finally to this Java code.

---

Let's break this loop down step by step so you can see exactly what each section does and why.

---

### The Big Picture of the Loop

Think of the `queue` as a list of **news flashes**:

> *"We just discovered that state `curr` has a guaranteed winner!"*

The `while` loop's only job is:

1. Take that news.
2. Ask: **"Who could have made a move that landed us in this state?"** (Finding the parents / previous states).
3. Update those parent states based on the news.

---

### Part 1: Who made the move to reach `curr`?

```java
int prevTurn = (t == 1) ? 2 : 1;

```

Remember: `t` is whose turn it is **right now** in the popped state `curr`.

* If it is now **Mouse's turn (`t = 1`)**, who just moved to hand the turn over? **Cat (`prevTurn = 2`)**.
* If it is now **Cat's turn (`t = 2`)**, who just moved? **Mouse (`prevTurn = 1`)**.

---

### Part 2: Finding all previous states (`prevStates`)

```java
List<int[]> prevStates = new ArrayList<>();
if (prevTurn == 1) { // Mouse moved from prevM to m
    for (int prevM : graph[m]) {
        prevStates.add(new int[]{prevM, c, 1});
    }
} else { // Cat moved from prevC to c (cannot be 0)
    for (int prevC : graph[c]) {
        if (prevC != 0) {
            prevStates.add(new int[]{m, prevC, 2});
        }
    }
}

```

Since the graph is **undirected**, if there is a path from `prevM` to `m`, there is also a path from `m` to `prevM`.

So to find where the player came from:

* If **Mouse just moved to `m**`: Mouse must have come from one of `m`'s neighbors (`graph[m]`). Cat did not move, so Cat was still at `c`. The state before the move was `(prevM, c, 1)`.
* If **Cat just moved to `c**`: Cat came from one of `c`'s neighbors (`graph[c]`). Mouse did not move, so Mouse was still at `m`. The state before the move was `(m, prevC, 2)` (skipping `0` because Cat cannot enter room 0).

Now `prevStates` contains every previous board state that could step directly into `curr`.

---

### Part 3: Updating each previous state

Now we look at each `prev = (pm, pc, pt)`. Notice that `pt` is the player who was making the choice in that previous state.

#### 1. Skip if already solved

```java
if (color[pm][pc][pt] != 0) continue;

```

If we already know the outcome of this state from an earlier step, do nothing.

---

#### 2. RULE 1: Did `pt` find a winning move?

```java
if (winner == pt) {
    color[pm][pc][pt] = winner;
    queue.offer(new int[]{pm, pc, pt, winner});
}

```

* Suppose `pt` was Mouse (`pt = 1`).
* Mouse was sitting at `(pm, pc, 1)` deciding where to go.
* Mouse sees that moving to `curr` results in **`winner == 1` (Mouse wins)**!
* Mouse says: *"I don't need to check any other doors. I pick this move and win!"*
* So we mark `(pm, pc, 1)` as a **Mouse Win**, and add it to the queue so earlier states can learn about it.

---

#### 3. RULE 2: Did `pt` move into a trap?

```java
else {
    degree[pm][pc][pt]--;

    if (degree[pm][pc][pt] == 0) {
        int opponentWinner = (pt == 1) ? 2 : 1;
        color[pm][pc][pt] = opponentWinner;
        queue.offer(new int[]{pm, pc, pt, opponentWinner});
    }
}

```

* Suppose `pt` was Mouse (`pt = 1`), but `winner == 2` (Cat wins).
* Mouse sees that moving to `curr` lets Cat win.
* Mouse will **avoid** this move if possible.
* So we cross this move off Mouse's available options: `degree[pm][pc][pt]--`.
* **The punchline:** If `degree == 0`, it means **every single move** Mouse could make from `(pm, pc, pt)` leads to Cat winning. Mouse has no escape left. Mouse is trapped.
* Therefore, state `(pm, pc, pt)` is declared an unavoidable **loss** (which means `opponentWinner` wins), and we push it to the queue.

---

### Summary in One Sentence

When state $B$ is resolved, the loop checks every state $A$ that can move into $B$: if moving to $B$ wins the game for the player at $A$, mark $A$ as an immediate win; if moving to $B$ loses, remove that option from $A$, and only mark $A$ as a loss once all its options hit zero.

---

Let's break down this entire problem from scratch, starting with what a "state" actually is, why standard recursion completely breaks down, and how we arrive at the exact logic of the solution.

---

### 1. What Exactly is a "Game State"?

If you pause the game at any second to inspect the board, what do you need to know to predict what happens next?

1. Where the **Mouse** is standing ($m$).
2. Where the **Cat** is standing ($c$).
3. **Whose turn** it is to move ($t$, where $1 = \text{Mouse}$, $2 = \text{Cat}$).

That triplet $(m, c, t)$ is your **State**.

If the graph has $N$ rooms, there are at most:


$$N \times N \times 2 \text{ possible states}$$


For $N \le 50$, that is at most $50 \times 50 \times 2 = 5,000$ states. That's a tiny number!

Think of these $5,000$ states as individual dots on a piece of paper. An arrow goes from State $A$ to State $B$ if the player whose turn it is can legally move to create State $B$.

---

### 2. Why Minimax / Normal DFS Fails

In simple games, you write a recursive function:

```text
canMouseWin(m, c):
    for each neighbor of m:
        if Cat cannot win from (neighbor, c):
            return True
    return False

```

**Why does this crash?**
Because of **Cycles**.

Imagine a triangle graph of nodes `0, 1, 2`. The Mouse can run from `1` to `2`, then back to `1`, then to `2`. If you use DFS, you call `dfs(1) -> dfs(2) -> dfs(1) -> dfs(2) ...` forever until your computer hits a `StackOverflowError`.

You cannot simply use standard memoization either, because whether a cycle is a "Draw" or an avoidable loop depends on whether someone had a forced win elsewhere.

---

### 3. The Core Intuition: Reverse Thinking (Retrograde Analysis)

Instead of asking:

> *"I am at the start: $(1, 2, \text{Mouse Turn})$. What should I do?"*

Flip the timeline upside down and ask:

> *"Where does the game **definitely end**, and how do those outcomes ripple backwards to the start?"*

#### Step A: What do we know for 100% certain on Day 1?

We know the final outcomes of these positions immediately:

1. **Mouse reaches 0**: Any state where $m = 0$ is an **instant Mouse Win** (`1`).
2. **Cat catches Mouse**: Any state where $m = c$ (with $m \ne 0$) is an **instant Cat Win** (`2`).

We call these the **Terminal States**. Every other state starts out marked as **DRAW (`0`)** until proven otherwise.

---

### 4. How Does an Outcome Ripple Backwards?

Now, imagine we already know the final verdict for some state $S_{\text{next}}$.
Suppose state $S_{\text{prev}}$ has a legal move into $S_{\text{next}}$.

Let's say it's **Mouse's turn** at $S_{\text{prev}}$:

* **Scenario 1: Mouse discovers a winning move.**
If $S_{\text{next}}$ is marked as **Mouse Wins**, Mouse is thrilled! Mouse will gladly choose this move.
Therefore, $S_{\text{prev}}$ is immediately resolved: **Mouse Wins**.
*(Notice: Mouse doesn't care about any other choices. One winning move is all it takes!)*
* **Scenario 2: Mouse steps into a trap.**
If $S_{\text{next}}$ is marked as **Cat Wins**, Mouse will actively avoid this move.
Does this mean Mouse loses at $S_{\text{prev}}$? **Not necessarily!** Mouse might still have other doors to escape through.
So, Mouse simply crosses this option off its list:

$$\text{remaining\_valid\_moves}(S_{\text{prev}}) = \text{remaining\_valid\_moves}(S_{\text{prev}}) - 1$$



Only when **all doors are closed** ($\text{remaining\_valid\_moves} == 0$), Mouse has no choice left: every move leads to defeat. Only then is $S_{\text{prev}}$ marked as **Cat Wins**.

The exact symmetric logic applies if it were Cat's turn!

---

### 5. Walking Through a Concrete Example

Let's see this in action on a simple line of 3 nodes:

```text
Hole (0) --- Room (1) --- Room (2)

```

* Edges:
* Node 0 connects to: `[1]`
* Node 1 connects to: `[0, 2]`
* Node 2 connects to: `[1]`


* Starting positions:
* Mouse is at `1`
* Cat is at `2`
* Mouse goes first. Start state is: $(m=1, c=2, t=\text{Mouse})$.



#### Step 1: Initial Degrees (How many moves does each state have?)

* At $(1, 2, \text{Mouse})$: Mouse can move to `0` or `2`. That's **2 options**. $\text{degree} = 2$.
* At $(1, 2, \text{Cat})$: Cat can only move to `1` (Cat cannot enter 0). That's **1 option**. $\text{degree} = 1$.

#### Step 2: Put Terminal States into our Queue

* $(0, 2, \text{Mouse})$ is a **Mouse Win** (Mouse reached hole).
* $(0, 2, \text{Cat})$ is a **Mouse Win** (Mouse reached hole).
* $(1, 1, \text{Mouse})$ is a **Cat Win** (Cat caught Mouse).
* $(2, 2, \text{Mouse})$ is a **Cat Win** (Cat caught Mouse).

#### Step 3: Pop from Queue and Propagate

We pop state $(0, 2, \text{Cat})$, which is known to be **Mouse Win**:

* Who could have moved into this state?
* The previous turn was **Mouse** moving from room `1` to room `0`.
* So the predecessor state is: $(1, 2, \text{Mouse})$.
* At $(1, 2, \text{Mouse})$, it is Mouse's turn, and Mouse sees that moving to `0` leads to a state where **Mouse Wins**!
* Because this move wins the game for Mouse, **Scenario 1 triggers immediately**:

$$\text{color}[1][2][\text{Mouse}] = \text{MOUSE WIN (1)}$$



We look at the answer for our start state $(1, 2, \text{Mouse})$: it is **`1`**! We don't even need to process cycles or check other moves. Mouse simply steps into room 0 and wins on turn 1.

---

### 6. The Complete State Transition Logic in Code

Here is the entire thought process translated directly into Java:

```java
import java.util.*;

class Solution {
    private static final int DRAW = 0;
    private static final int MOUSE = 1;
    private static final int CAT = 2;

    public int catMouseGame(int[][] graph) {
        int n = graph.length;

        // color[m][c][turn] stores the outcome:
        // 0 = DRAW, 1 = MOUSE wins, 2 = CAT wins
        int[][][] color = new int[n][n][3];

        // degree[m][c][turn] stores the number of available moves from this state
        int[][][] degree = new int[n][n][3];

        // Step 1: Count initial available moves for every state
        for (int m = 0; m < n; m++) {
            for (int c = 1; c < n; c++) {
                // Mouse can jump to any adjacent room
                degree[m][c][1] = graph[m].length;

                // Cat can jump to any adjacent room EXCEPT the hole (0)
                int catMoves = 0;
                for (int next : graph[c]) {
                    if (next != 0) catMoves++;
                }
                degree[m][c][2] = catMoves;
            }
        }

        // Step 2: Queue all known terminal states
        // State array format: {m, c, turn, winner}
        Queue<int[]> queue = new LinkedList<>();

        // (A) Mouse is at 0 -> Mouse wins regardless of turn
        for (int c = 1; c < n; c++) {
            for (int t = 1; t <= 2; t++) {
                color[0][c][t] = MOUSE;
                queue.offer(new int[]{0, c, t, MOUSE});
            }
        }

        // (B) Cat and Mouse at the same room (m != 0) -> Cat wins
        for (int m = 1; m < n; m++) {
            for (int t = 1; t <= 2; t++) {
                color[m][m][t] = CAT;
                queue.offer(new int[]{m, m, t, CAT});
            }
        }

        // Step 3: Propagate backward using BFS
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int m = current[0];
            int c = current[1];
            int t = current[2];
            int winner = current[3];

            // If current turn is t=1 (Mouse), the previous player to move was Cat (t=2)
            // If current turn is t=2 (Cat), the previous player to move was Mouse (t=1)
            int prevTurn = (t == 1) ? 2 : 1;

            // Find all predecessor states (states that could transition to this current state)
            List<int[]> predecessors = new ArrayList<>();
            if (prevTurn == 1) {
                // Mouse was the one who moved from prevM to m
                for (int prevM : graph[m]) {
                    predecessors.add(new int[]{prevM, c, 1});
                }
            } else {
                // Cat was the one who moved from prevC to c
                for (int prevC : graph[c]) {
                    if (prevC != 0) { // Cat could never have been at 0
                        predecessors.add(new int[]{m, prevC, 2});
                    }
                }
            }

            // Inspect each predecessor
            for (int[] prev : predecessors) {
                int pm = prev[0];
                int pc = prev[1];
                int pt = prev[2];

                // If this predecessor already has a decided winner, skip it
                if (color[pm][pc][pt] != DRAW) {
                    continue;
                }

                // CASE 1: The move leads to a win for the player whose turn it was
                if (winner == pt) {
                    color[pm][pc][pt] = winner;
                    queue.offer(new int[]{pm, pc, pt, winner});
                } 
                // CASE 2: The move leads to an opponent win; eliminate this move option
                else {
                    degree[pm][pc][pt]--;
                    // If all legal moves lead to defeat, the player is forced to lose!
                    if (degree[pm][pc][pt] == 0) {
                        int opponent = (pt == 1) ? CAT : MOUSE;
                        color[pm][pc][pt] = opponent;
                        queue.offer(new int[]{pm, pc, pt, opponent});
                    }
                }
            }
        }

        // Mouse starts at 1, Cat starts at 2, Mouse moves first (turn 1)
        return color[1][2][1];
    }
}

```

---

### Why Any Remaining State is a Draw (0)

Notice what happens to cyclical states:

* If neither the Cat can force a catch, nor the Mouse can force an entry into room 0, their degree counters will **never hit zero**.
* They never find a guaranteed winning branch, and they never run out of alternative safe moves.
* Therefore, they are **never colored** and **never pushed to the queue**.
* They simply remain as their initial default value: **`0` (DRAW)**!

