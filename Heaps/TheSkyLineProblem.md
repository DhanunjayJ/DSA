This code uses the **Sweep Line Algorithm** paired with a **Max-Heap** (`PriorityQueue`). Instead of looking at whole rectangles at once, it breaks every building into two vertical edges—a **start edge** and an **end edge**—and scans across the x-axis from left to right like a vertical laser beam.

At every edge it hits, the Max-Heap keeps track of all buildings currently overlapping that x-coordinate and instantly tells us the tallest one via `pq.peek()`.

---

## 1. The Negative Height Trick (`-h` vs `+h`)

```java
points.add(new Pair(sp, -h)); // Left edge (Start of building)
points.add(new Pair(ep, h));  // Right edge (End of building)

```

Why store the starting height as **negative** and the ending height as **positive**? It serves two critical purposes:

1. **Distinguishing Starts from Ends:** Later in the loop, `if (h < 0)` immediately tells the code *"a building is starting here, add its height to our active pool"*, whereas `h > 0` means *"a building just ended, remove its height from the active pool."*
2. **Clever Tie-Breaking During Sorting:** Look at the `compareTo` method in `Pair`:
```java
if (this.x != o.x) {
    return this.x - o.x; // Sort left-to-right by x
} else {
    return this.h - o.h; // Tie-breaker if x is the same!
}

```


When two edges share the exact same `x` coordinate, `this.h - o.h` sorts them from **smallest (most negative) to largest (most positive)**. That single line automatically handles three tricky edge cases so we never record false dips or redundant points:

| Edge Case at Same `x` | Stored Heights (`h`) | Which Processes First? (`this.h - o.h`) | Why It Matters |
| --- | --- | --- | --- |
| **Two buildings start** (`h = 10` and `h = 15`) | `-10` and `-15` | `-15` comes before `-10` | The taller building (`15`) enters the heap first, immediately setting the max height to `15` so `10` doesn't trigger a false key point. |
| **Two buildings end** (`h = 10` and `h = 15`) | `+10` and `+15` | `+10` comes before `+15` | The shorter building (`10`) is removed first while `15` is still in the heap, preventing a fake intermediate drop before `15` leaves. |
| **One starts, one ends** (`h = 10` each) | `-10` (start) and `+10` (end) | `-10` comes before `+10` | The new building (`10`) is added *before* the old building (`10`) is removed, keeping the skyline flat at `10` instead of dropping to `0` for an instant. |

---

## 2. How the Max-Heap Tracks the Highest Building

```java
PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
pq.add(0);
int prevht = 0;

```

* **`Collections.reverseOrder()`:** In Java, a `PriorityQueue` is a Min-Heap by default. Passing `reverseOrder()` turns it into a **Max-Heap**, meaning `pq.peek()` always gives us the **maximum height** currently inside the queue.
* **`pq.add(0)`:** This represents the **ground**. Even when no buildings are active, the ground (`0`) stays in the heap so the skyline naturally drops back to `0` when the last building ends.
* **`prevht = 0`:** Tracks the skyline's height just before the current x-coordinate. We only care when the maximum height **changes**.

---

## 3. The Sweep Line Loop

```java
for (int i = 0; i < points.size(); i++) {
    int x = points.get(i).x;
    int h = points.get(i).h;
    
    if (h < 0) {
        pq.add(-h);      // Building starts: put its positive height into Max-Heap
    } else {
        pq.remove(h);    // Building ends: remove its height from Max-Heap
    }
    
    // Did adding/removing this building change the tallest height?
    if (pq.peek() != prevht) {
        ArrayList<Integer> t = new ArrayList<>();
        t.add(x);
        t.add(pq.peek()); // New tallest height at this x
        ans.add(t);
        prevht = pq.peek(); // Update previous max height
    }
}

```

Here is what happens at every point:

1. **Update Active Buildings:** If `h < 0`, a building has begun, so we flip the sign back (`-h`) and toss it into `pq`. If `h > 0`, a building has ended, so we remove one instance of `h` from `pq`.
2. **Check if the Contour Changed (`pq.peek() != prevht`):**
* Suppose we are inside a tall building of height `15` (`prevht = 15`), and a smaller building of height `12` starts (`x = 5`). We add `12` to `pq`. Now `pq` contains `[15, 12, 10, 0]`. What is `pq.peek()`? Still `15`! Since `15 == prevht`, the shorter building is completely hidden behind the taller one, so the `if` block is skipped.
* Next, at `x = 7`, the building of height `15` ends (`h = +15`). We call `pq.remove(15)`. Now `pq` contains `[12, 10, 0]`. Suddenly, `pq.peek()` is `12`! Since `12 != 15` (`prevht`), the skyline drops to the next tallest available building (`12`), and `[7, 12]` is recorded.



---

## 4. Dry Run on Example 1 (`[[2,9,10], [3,7,15], [5,12,12]]`)

Tracing the first three overlapping buildings shows the heap in action:

| Point `(x, h)` | Action | Max-Heap `pq` Contents | `pq.peek()` (Max) | `prevht` | Skyline Changed? (`ans` Output) |
| --- | --- | --- | --- | --- | --- |
| **Init** | Add ground `0` | `[0]` | `0` | `0` | — |
| `(2, -10)` | Start `10` → `pq.add(10)` | `[10, 0]` | `10` | `0` | **Yes!** Add `[2, 10]`, `prevht = 10` |
| `(3, -15)` | Start `15` → `pq.add(15)` | `[15, 10, 0]` | `15` | `10` | **Yes!** Add `[3, 15]`, `prevht = 15` |
| `(5, -12)` | Start `12` → `pq.add(12)` | `[15, 12, 10, 0]` | `15` | `15` | No (`15 == 15`, hidden inside `15`) |
| `(7, +15)` | End `15` → `pq.remove(15)` | `[12, 10, 0]` | `12` | `15` | **Yes!** Add `[7, 12]`, `prevht = 12` |
| `(9, +10)` | End `10` → `pq.remove(10)` | `[12, 0]` | `12` | `12` | No (`12 == 12`, `10` ended under `12`) |
| `(12, +12)` | End `12` → `pq.remove(12)` | `[0]` | `0` | `12` | **Yes!** Add `[12, 0]`, `prevht = 0` |

---

## One Performance Catch to Keep in Mind

While this code is clean and passes many test cases, **`pq.remove(h)` in Java's `PriorityQueue` takes $O(N)$ time** because a binary heap has to scan linearly to find the element `h` before removing it. Across $N$ buildings, that makes the total time complexity **$O(N^2)$**.

To optimize this exact logic to **$O(N \log N)$** in an interview, swap `PriorityQueue<Integer>` for a **`TreeMap<Integer, Integer>`** (storing `height -> frequency count`). A `TreeMap` lets you add, remove, and grab the maximum key (`map.lastKey()`) all in guaranteed $O(\log N)$ time.


---

To upgrade the `PriorityQueue` to a `TreeMap` without changing your core logic, we store **`height -> count`** (how many active buildings currently have that exact height).

Because a `TreeMap` keeps its keys sorted in ascending order, **`map.lastKey()`** always gives us the maximum active height in $O(\log N)$ time, and adding or removing a height also takes $O(\log N)$ time.

### The $O(N \log N)$ Code

The `Pair` class and sorting logic stay **100% identical**—only the data structure inside `getSkyline` changes:

```java
import java.util.*;

class Pair implements Comparable<Pair> {
    int x;
    int h;
    Pair(int x, int h) {
        this.x = x;
        this.h = h;
    }
    public int compareTo(Pair o) {
        if (this.x != o.x) {
            return this.x - o.x;
        } else {
            return this.h - o.h;
        }
    }
}

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Pair> points = new ArrayList<>();
        
        for (int i = 0; i < buildings.length; i++) {
            int sp = buildings[i][0];
            int ep = buildings[i][1];
            int h = buildings[i][2];
            points.add(new Pair(sp, -h));
            points.add(new Pair(ep, h));
        }
        
        Collections.sort(points);
        
        // TreeMap stores: Key = Height, Value = Frequency (how many active buildings have this height)
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, 1); // Ground level (height 0) is always active once
        int prevht = 0;
        
        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).x;
            int h = points.get(i).h;
            
            if (h < 0) {
                // Building starts: increment frequency of this height
                int actualHeight = -h;
                map.put(actualHeight, map.getOrDefault(actualHeight, 0) + 1);
            } else {
                // Building ends: decrement frequency of this height
                int count = map.get(h);
                if (count == 1) {
                    map.remove(h); // No more active buildings of this height, remove the key completely!
                } else {
                    map.put(h, count - 1); // Still other overlapping buildings of this height
                }
            }
            
            // map.lastKey() grabs the highest key currently in the TreeMap
            int currMaxHeight = map.lastKey();
            
            if (currMaxHeight != prevht) {
                ans.add(Arrays.asList(x, currMaxHeight));
                prevht = currMaxHeight;
            }
        }
        
        return ans;
    }
}

```

---

### What Changed? (Side-by-Side Comparison)

| Operation | Old `PriorityQueue` ($O(N^2)$ total) | New `TreeMap` ($O(N \log N)$ total) |
| --- | --- | --- |
| **Init Ground `0**` | `pq.add(0);` | `map.put(0, 1);` |
| **Building Starts (`h < 0`)** | `pq.add(-h);` — $O(\log N)$ | `map.put(-h, map.getOrDefault(-h, 0) + 1);` — $O(\log N)$ |
| **Building Ends (`h > 0`)** | `pq.remove(h);` — **$O(N)$ bottleneck** | Decrement count; if `0`, `map.remove(h);` — **$O(\log N)$** |
| **Get Max Height** | `pq.peek()` — $O(1)$ | `map.lastKey()` — $O(\log N)$ |

### Why Do We Need a Frequency Count (`Value`)?

Unlike a `PriorityQueue`, a `TreeMap` cannot hold duplicate keys. If two overlapping buildings both have height `10`, calling `map.put(10, ...)` without counting would overwrite the first one. Then, as soon as the *first* building of height `10` ended, removing key `10` would delete the *second* building too!

Tracking the count ensures we only call `map.remove(h)` when the **last** overlapping building of that height ends.