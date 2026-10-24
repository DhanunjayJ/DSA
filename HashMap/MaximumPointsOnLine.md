Your intuition about the constraints ($N \le 300$, so $N^2 \approx 90,000$ operations) is spot on: **an $O(N^2)$ algorithm is required and will easily pass.**

However, your proposed idea of globally hashing all slopes and dividing by 2 **will not work** due to two major mathematical traps.

---

### Why Global Slope Hashing Fails

1. **Parallel Lines Share the Same Slope**:
If you group all pairs by slope globally, any two separate parallel lines will collide into the same bucket because they have the exact same slope. For example, a line connecting $(0,0)$ to $(1,1)$ and a completely separate line connecting $(0,5)$ to $(1,6)$ both have a slope of $1$. Hashing them globally treats them as a single line, completely breaking the count.
2. **Combinations vs. Point Counts**:
Counting pairs gives you combinations (e.g., $\binom{4}{2} = 6$ pairs for 4 points), not the actual number of points. Converting that back accurately while handling overlapping lines becomes unnecessarily complicated.

---

### The Correct $O(N^2)$ Approach: The "Anchor Point" Method

To solve this correctly in $O(N^2)$ time, we use the **Anchor Point + GCD Slope** technique:

1. **Fix one point as an anchor** (`points[i]`).
2. **Calculate the slope** from this anchor to every other point `points[j]` (where $j > i$).
3. **Use GCD (Greatest Common Divisor)** to represent the slope as an irreducible fraction `(dy, dx)` instead of a floating-point number, avoiding precision errors.
4. **Count frequencies** using a HashMap for *that specific anchor*. The maximum frequency + 1 (for the anchor itself) gives the maximum points on a line passing through that anchor.
5. **Reset the map** and move to the next anchor.

---

### Java Implementation

```java
import java.util.*;

class Solution {
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;
        
        int maxPointsOnLine = 1;
        
        for (int i = 0; i < n; i++) {
            Map<String, Integer> slopeMap = new HashMap<>();
            int duplicates = 0;
            int localMax = 0;
            
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                
                // Handle identical points (though constraints say unique, good practice)
                if (dx == 0 && dy == 0) {
                    duplicates++;
                    continue;
                }
                
                // Reduce the fraction using GCD to avoid floating-point precision loss
                int gcd = gcd(dx, dy);
                dx /= gcd;
                dy /= gcd;
                
                // Normalize signs so negative slopes match consistently (e.g., -1/2 vs 1/-2)
                if (dx < 0) {
                    dx = -dx;
                    dy = -dy;
                } else if (dx == 0) {
                    dy = Math.abs(dy); // Vertical line
                } else if (dy == 0) {
                    dx = Math.abs(dx); // Horizontal line
                }
                
                String slopeKey = dy + "," + dx;
                slopeMap.put(slopeKey, slopeMap.getOrDefault(slopeKey, 0) + 1);
                localMax = Math.max(localMax, slopeMap.get(slopeKey));
            }
            
            // Total points on this line = max points with same slope + anchor point + any duplicates
            maxPointsOnLine = Math.max(maxPointsOnLine, localMax + duplicates + 1);
        }
        
        return maxPointsOnLine;
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }
}

```

### Complexity

* **Time Complexity:** $O(N^2 \log (\text{max coordinate}))$, because we iterate through all pairs and compute the GCD for each pair. With $N = 300$, this executes in a few milliseconds.
* **Space Complexity:** $O(N)$ to store slopes in the HashMap for each anchor point.

---

Let's break down why your global idea wouldn't work, and then walk through the correct **Anchor Point + GCD** method step-by-step so it makes complete sense.

---

### Part 1: Why Global Slope Hashing Fails

Imagine you have two completely different, parallel roads on a map. They never touch each other, but because they point in the exact same direction, they have the **same slope**.

If you just calculate every possible pair of points in the whole grid, find their slopes, and throw them into one giant global HashMap, those two separate parallel lines will mix together. The code will think they are all part of *one single line* because it only looks at the slope value, ignoring where the line is actually located on the X-Y plane.

---

### Part 2: The Solution — The "Anchor Point" Method

To fix this, we change our perspective: **Instead of looking at all lines globally, we pick one point at a time and look at the world from its point of view.**

#### Step-by-Step Logic:

1. **Pick an Anchor**: Pick a starting point, say point $A$ (`points[i]`).
2. **Measure Slopes to Everyone Else**: Draw a line from point $A$ to every other point $B$ (`points[j]`) that comes after it.
3. **Group by Slope**: Count how many points share that *exact same slope* relative to our anchor $A$.
* Since all these lines share point $A$ as a starting anchor, any points that share the same slope are guaranteed to be on the **exact same straight line**.


4. **Reset for the Next Anchor**: Once we check all other points relative to $A$, we record our maximum, clear our map, pick point $B$ as our new anchor, and repeat.

---

### Part 3: The Floating-Point Problem & GCD

When you calculate a slope, you usually do $\frac{y_2 - y_1}{x_2 - x_1}$.

* If you divide these to get a decimal (like `1.0 / 3.0 = 0.3333333333`), computers run into **floating-point precision errors**. Two slopes that are mathematically identical might look slightly different to a computer due to rounding.

#### How GCD Fixes This:

Instead of dividing into decimals, we keep the slope as a **fraction: `(dy, dx)**` (change in $Y$, change in $X$) and reduce it to its simplest form using **GCD (Greatest Common Divisor)**.

**Example:**

* Suppose point $A$ is at `(1, 1)` and point $B$ is at `(3, 5)`.
* $dx = 3 - 1 = 2$
* $dy = 5 - 1 = 4$
* Slope fraction is `4 / 2`.


* Find the GCD of `4` and `2`, which is `2`.
* Divide both numbers by the GCD:
* $dx = 2 / 2 = 1$
* $dy = 4 / 2 = 2$


* The normalized slope key becomes `"2,1"`.

No matter which two points on that same line you pick, their reduced fraction will always simplify to `"2,1"`, avoiding any decimal errors completely!

---

### Summary Walkthrough of the Code Logic

1. Outer loop picks `points[i]` as the anchor.
2. Inner loop looks at every subsequent point `points[j]`, calculates $dx$ and $dy$.
3. Simplifies $dx$ and $dy$ using the `gcd` function.
4. Stores the string `"dy,dx"` into a local `HashMap` and increments its count.
5. Takes the highest count found for that anchor, adds `1` (to include the anchor point itself), and updates our global maximum.

---
That block of code handles **normalization**—ensuring that two lines pointing in the exact same direction always produce the exact same text key in our HashMap, even if the math calculates their fractions with different negative signs.

Without normalization, a slope going "up and left" might be written differently than a slope going "down and right," even though they lie on the exact same line.

---

### The Problem: Equivalent Slopes Look Different

Suppose your anchor point is at `(0, 0)`:

* **Point B** is at `(-1, 2)`:
* $dx = -1 - 0 = -1$
* $dy = 2 - 0 = 2$
* Unnormalized fraction: `dy = 2, dx = -1` (Key: `"2,-1"`)


* **Point C** is at `(1, -2)`:
* $dx = 1 - 0 = 1$
* $dy = -2 - 0 = -2$
* Unnormalized fraction: `dy = -2, dx = 1` (Key: `"-2,1"`)



Mathematically, $\frac{2}{-1}$ and $\frac{-2}{1}$ represent the **exact same slope** (a slope of $-2$). But to a computer's `HashMap`, `"2,-1"` and `"-2,1"` are two completely different strings! If we don't fix this, the algorithm will think Point B and Point C are on two different lines.

---

### How the Normalization Rules Fix This

The normalization block forces every slope to follow a strict standard format:

1. **`if (dx < 0)`**:
* If the denominator ($dx$) is negative, we flip the signs of **both** $dx$ and $dy$.
* *Example*: `dy = 2, dx = -1` becomes `dy = -2, dx = 1`. Multiplying top and bottom by $-1$ keeps the math identical while flipping the negative sign to the numerator.


2. **`else if (dx == 0)` (Vertical Lines)**:
* A vertical line has a change in $X$ of zero ($dx = 0$). It doesn't matter if you go straight up ($dy = 5$) or straight down ($dy = -5$), it's the exact same vertical line.
* We force $dy$ to always be positive using `Math.abs(dy)` so it always maps to the same key (e.g., `"1,0"`).


3. **`else if (dy == 0)` (Horizontal Lines)**:
* A horizontal line has a change in $Y$ of zero ($dy = 0$). It doesn't matter if you go right ($dx = 4$) or left ($dx = -4$), it's the exact same horizontal line.
* We force $dx$ to always be positive using `Math.abs(dx)` (e.g., `"0,1"`).



By enforcing these rules, any slope pointing along that line is forced into one unique, canonical signature before we store it in the HashMap!
---

You have hit on the exact core of why normalization is needed. Let's clear up how signs interact and look at **all possible directions** (quadrants) around an anchor point to see how normalization forces opposite directions on the same line to match.

---

### Why Normalizing $dx < 0$ Works

When you have a fraction $\frac{dy}{dx}$, a negative sign anywhere in the fraction makes the *entire slope* negative.

* Mathematically: $\frac{-1}{-2} = \frac{1}{-2} = \frac{-1}{2} = 0.5$ (all represent the exact same slope magnitude and direction).
* But to a computer's `HashMap`, `"1,-2"`, `"-1,2"`, and `"-1,-2"` look like three completely different strings.

By enforcing a rule that **$dx$ must never be negative**, if $dx$ *is* negative, we flip the signs of **both** numbers (`dx = -dx; dy = -dy;`). This shifts the negative sign (or cancels it out) while keeping the fraction mathematically identical.

---

### Walkthrough of All 4 Directions (Quadrants) on a Line

Imagine your anchor point is at `(0, 0)` on a graph, and you have points sitting on a diagonal line passing through the origin (a slope of $1$, where $y = x$).

Let's test points in all different directions along this line to see how normalization handles them:

#### Case 1: Both positive (Top-Right Quadrant)

* **Point:** `(2, 2)`
* $dx = 2 - 0 = 2$
* $dy = 2 - 0 = 2$
* **Reduced fraction:** $\frac{2}{2} \rightarrow \frac{1}{1}$
* **Check $dx < 0$:** $dx$ is $1$ (positive), so **no change**.
* **HashMap Key:** `"1,1"`

#### Case 2: Both negative (Bottom-Left Quadrant - Opposite direction)

* **Point:** `(-2, -2)`
* $dx = -2 - 0 = -2$
* $dy = -2 - 0 = -2$
* **Reduced fraction:** $\frac{-2}{-2} \rightarrow \frac{-1}{-1}$
* **Check $dx < 0$:** $dx$ is $-1$ (negative!).
* *Action:* Flip both signs: `dx = -(-1) = 1`, `dy = -(-1) = 1`.


* **HashMap Key:** `"1,1"`
* *Result:* Even though this point is in the exact opposite direction from the anchor, normalization turns it into `"1,1"`, grouping it onto the **same straight line**.

#### Case 3: $dx$ is negative, $dy$ is positive (Top-Left Quadrant)

* **Point:** `(-2, 2)` (Slope is $-1$)
* $dx = -2 - 0 = -2$
* $dy = 2 - 0 = 2$
* **Reduced fraction:** $\frac{2}{-2} \rightarrow \frac{1}{-1}$
* **Check $dx < 0$:** $dx$ is $-1$ (negative!).
* *Action:* Flip both signs: `dx = -(-1) = 1`, `dy = -(1) = -1`.


* **HashMap Key:** `"-1,1"`

#### Case 4: $dx$ is positive, $dy$ is negative (Bottom-Right Quadrant - Opposite direction)

* **Point:** `(2, -2)` (Slope is $-1$)
* $dx = 2 - 0 = 2$
* $dy = -2 - 0 = -2$
* **Reduced fraction:** $\frac{-2}{2} \rightarrow \frac{-1}{1}$
* **Check $dx < 0$:** $dx$ is $1$ (positive), so **no change**.
* **HashMap Key:** `"-1,1"`
* *Result:* This matches Case 3 because they lie on the exact same downward-sloping line!

---

### Summary of Horizontal and Vertical Cases

* **Vertical Lines ($dx = 0$):**
* Whether you go straight up `(0, 5)` or straight down `(0, -5)`, $dx$ is already $0$.
* We force $dy = \text{Math.abs(dy)}$ so any vertical line always resolves to `"1,0"`.


* **Horizontal Lines ($dy = 0$):**
* Whether you go right `(5, 0)` or left `(-5, 0)`, $dy$ is $0$.
* Because $dx$ might be negative (`-5`), the rule `if (dx < 0)` kicks in, flips $dx$ to positive (`5`), ensuring horizontal lines always resolve to `"0,1"`.