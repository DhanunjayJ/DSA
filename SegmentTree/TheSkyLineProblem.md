To solve **The Skyline Problem** using a **Segment Tree**, we revisit the "Brute Force" idea from earlier—where we wanted to maintain an array `height[x]` and update `height[x] = max(height[x], H)` for every building from `left` to `right`—and fix its two flaws using **Coordinate Compression** and **Lazy Propagation**.

---

## 1. The Core Idea: Range Max Update

Imagine each building `[left, right, height]` is just a **Range Update** instruction:

> *"For every interval between `left` and `right`, set the height to `max(current_height, height)`."*

However, there are three obstacles we have to handle to make a Segment Tree work here:

### Obstacle A: Coordinates are up to $2^{31} - 1$ ($\rightarrow$ Fix: Coordinate Compression)

We cannot build a Segment Tree of size $2 \times 10^9$. Instead, notice that with at most $10^4$ buildings, there are at most **$2 \times 10^4$ unique x-coordinates** (`left` and `right` values).

* We collect all unique `left` and `right` values, sort them, and map each real x-coordinate to its **compressed index** `0, 1, 2, ..., K-1`.
* Now our Segment Tree only needs to cover at most $20,000$ indices!

### Obstacle B: Points vs. Segments ($\rightarrow$ Fix: Half-Open Intervals `[L, R - 1]`)

A building from `x = 2` to `x = 9` with height `10` means the roof is at height `10` starting at `2`, and **drops off right at `9**`.

* If we updated both index `2` and index `9` to height `10`, then at `x = 9` the tree would think the height is still `10` instead of dropping to `0`!
* Therefore, each leaf index `i` in our Segment Tree represents the **elementary segment** from `coords[i]` to `coords[i + 1]`.
* When a building spans from compressed index `L` to `R`, we update the index range **`[L, R - 1]`** (inclusive). Index `R` is where the building has already ended.

### Obstacle C: Fast Range Updates ($\rightarrow$ Fix: Lazy Propagation)

Updating `[L, R - 1]` one leaf at a time would still take $O(N^2)$. Using a Segment Tree where each node stores a `tree[node]` max value that gets pushed down lazily, each building updates in **$O(\log N)$** time.

---

## 2. Visual Walkthrough of Coordinate Compression (`Example 1`)

For the first three buildings `[[2,9,10], [3,7,15], [5,12,12]]`:

1. **Unique sorted x-coordinates (`coords`):** `[2, 3, 5, 7, 9, 12]`
* Index `0` $\rightarrow x = 2$ (covers segment `[2, 3)`)
* Index `1` $\rightarrow x = 3$ (covers segment `[3, 5)`)
* Index `2` $\rightarrow x = 5$ (covers segment `[5, 7)`)
* Index `3` $\rightarrow x = 7$ (covers segment `[7, 9)`)
* Index `4` $\rightarrow x = 9$ (covers segment `[9, 12)`)
* Index `5` $\rightarrow x = 12$ (covers segment `[12, \infty)` — where everything ends)


2. **How Buildings Update `[L, R - 1]`:**
* Building `[2, 9, 10]`: `2` is index `0`, `9` is index `4`. Update indices **`[0, 3]`** with height `10`.
* Building `[3, 7, 15]`: `3` is index `1`, `7` is index `3`. Update indices **`[1, 2]`** with height `15`.
* Building `[5, 12, 12]`: `5` is index `2`, `12` is index `5`. Update indices **`[2, 4]`** with height `12`.


3. **Final Leaf Heights After All Updates:**

| Leaf Index `i` | `0` | `1` | `2` | `3` | `4` | `5` |
| --- | --- | --- | --- | --- | --- | --- |
| **Real `x` (`coords[i]`)** | `2` | `3` | `5` | `7` | `9` | `12` |
| **Max Height in Leaf** | `10` | `15` | `15` | `12` | `12` | `0` |
| **Differs from Previous?** | **Yes** (`[2,10]`) | **Yes** (`[3,15]`) | No (`15==15`) | **Yes** (`[7,12]`) | No (`12==12`) | **Yes** (`[12,0]`) |

---

## 3. Java Implementation (Segment Tree + Coordinate Compression)

```java
import java.util.*;

class Solution {
    private int[] tree;

    public List<List<Integer>> getSkyline(int[][] buildings) {
        // 1. Collect and sort all unique x-coordinates (Coordinate Compression)
        TreeSet<Integer> xSet = new TreeSet<>();
        for (int[] b : buildings) {
            xSet.add(b[0]);
            xSet.add(b[1]);
        }
        
        List<Integer> coords = new ArrayList<>(xSet);
        int n = coords.size();
        
        // Map each real x-coordinate to its compressed index [0 ... n-1]
        Map<Integer, Integer> xMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            xMap.put(coords.get(i), i);
        }

        // 2. Build Segment Tree of size 4 * n
        // Each node stores the lazy max height covering its entire segment
        tree = new int[4 * n];

        // 3. Perform Range Max Updates on [L, R - 1] for each building
        for (int[] b : buildings) {
            int L = xMap.get(b[0]);
            int R = xMap.get(b[1]) - 1; // R - 1 because the right edge is exclusive!
            int H = b[2];
            update(1, 0, n - 1, L, R, H);
        }

        // 4. Collect final heights at every leaf index and build the skyline
        int[] heights = new int[n];
        collectHeights(1, 0, n - 1, 0, heights);

        List<List<Integer>> ans = new ArrayList<>();
        int prevHeight = 0;
        for (int i = 0; i < n; i++) {
            if (heights[i] != prevHeight) {
                ans.add(Arrays.asList(coords.get(i), heights[i]));
                prevHeight = heights[i];
            }
        }

        return ans;
    }

    // Range Update: set range [qL, qR] to max(current, val)
    private void update(int node, int l, int r, int qL, int qR, int val) {
        if (qL > r || qR < l) {
            return; // Out of range
        }
        if (qL <= l && r <= qR) {
            // Fully covered: lazily record the max height on this node
            tree[node] = Math.max(tree[node], val);
            return;
        }
        int mid = l + (r - l) / 2;
        update(2 * node, l, mid, qL, qR, val);
        update(2 * node + 1, mid + 1, r, qL, qR, val);
    }

    // Push all lazy max values down to the leaves in a single O(N) pass
    private void collectHeights(int node, int l, int r, int currentMax, int[] heights) {
        currentMax = Math.max(currentMax, tree[node]);
        if (l == r) {
            heights[l] = currentMax;
            return;
        }
        int mid = l + (r - l) / 2;
        collectHeights(2 * node, l, mid, currentMax, heights);
        collectHeights(2 * node + 1, mid + 1, r, currentMax, heights);
    }
}

```

---

## 4. Why `collectHeights` is So Short (A Neat Segment Tree Trick)

Normally, Lazy Propagation requires a `pushDown()` function inside `update` because updates can overwrite each other.

However, here **every update is a `Math.max` operation** (`max` is both *commutative* and *associative*—order doesn't matter!). Because we don't query the tree until *after* all buildings are inserted, we never even need `pushDown()` during `update`.

Each node `tree[node]` simply holds the highest building that completely covers its segment. At the very end, `collectHeights` walks from root to leaf in a single $O(N)$ pass, carrying `currentMax = Math.max(currentMax, tree[node])` down the tree!

---

## 5. Sweep Line vs. Segment Tree: When to Use Which?

| Feature | Sweep Line + `TreeMap` | Segment Tree + Coordinate Compression |
| --- | --- | --- |
| **Time Complexity** | $O(N \log N)$ | $O(N \log N)$ |
| **Space Complexity** | $O(N)$ | $O(N)$ |
| **Tie-Breaking Headaches** | **High** — Must carefully handle start-vs-end and same-$x$ sorting rules (`-h` vs `+h`). | **Zero** — Updating `[L, R - 1]` with `Math.max` naturally merges overlapping or touching buildings! |
| **Best Follow-up Problem** | **Meeting Rooms II** (LC 253) | **Falling Squares** (LC 699) — where blocks stack on top of existing max heights dynamically. |