Binary search is ultimately about **maintaining a search space invariant** while shrinking it at each step. Beyond basic array lookups, mastering the nuances comes down to template selection, boundary handling, and avoiding infinite loops.

---

## 1. The Core Invariant

Every binary search relies on an explicit condition or search range $[low, high]$. At every iteration:

1. Calculate `mid`.
2. Check a predicate function $P(mid)$.
3. Eliminate half the search space **without accidentally discarding the answer**.

---

## 2. The 3 Primary Templates

### Template 1: Basic Search (Exact Match)

Use when searching for a distinct value where you can return immediately upon finding it.

```java
int low = 0, high = nums.length - 1;
while (low <= high) {
    int mid = low + (high - low) / 2;
    if (nums[mid] == target) return mid;
    else if (nums[mid] < target) low = mid + 1;
    else high = mid - 1;
}
return -1; // Not found

```

* **Search Space:** Closed interval $[low, high]$.
* **Termination:** $low > high$ (empty search space).

---

### Template 2: Lower Bound / First `True` (`FFFFTTTT`)

Find the **first index** where $P(x)$ is `True` (e.g., `arr[i] >= target` or `bisect_left`).

```java
int low = 0, high = nums.length; // Range [0, N] if insert position can be N
while (low < high) {
    int mid = low + (high - low) / 2; // Floor division
    if (condition(mid)) {
        high = mid;     // mid could be the first match, keep it in [low, high]
    } else {
        low = mid + 1;  // mid fails condition, discard it
    }
}
return low; // low == high

```

* **Search Space:** Half-open interval $[low, high)$.
* **Termination:** $low == high$.
* **Floor Division Safety:** `mid = low + (high - low) / 2` biases towards `low`. When $high = low + 1$, `mid = low`. Setting `high = mid` shrinks the interval to $low$, terminating the loop safely.

---

### Template 3: Upper Bound / Last `True` (`TTTTFFFF`)

Find the **last index** where $P(x)$ is `True` (e.g., `arr[i] <= target`).

```java
int low = 0, high = nums.length - 1;
while (low < high) {
    int mid = low + (high - low + 1) / 2; // Ceiling division!
    if (condition(mid)) {
        low = mid;      // mid satisfies condition, keep it in [low, high]
    } else {
        high = mid - 1; // mid fails, discard it
    }
}
return low;

```

> ⚠️ **The Ceiling Division Trap:** When $high = low + 1$, floor division sets `mid = low`. If `condition(mid)` is true and you assign `low = mid`, $low$ never changes, causing an **infinite loop**. Adding `+ 1` forces ceiling division (`mid = high`), ensuring the range shrinks.

---

## 3. Critical Pitfalls & Tricks

### 1. Mid Arithmetic Overflow

* **Standard:** `int mid = low + (high - low) / 2;`
* **Bitwise (Java/C++):** `int mid = (low + high) >>> 1;` (unsigned right shift prevents sign-bit distortion if $low + high > Integer.MAX\_VALUE$).

### 2. Binary Search on Answer Space

When searching over a monotonic answer range rather than an array index:

* **Determining Bounds:** Ensure $low$ and $high$ cover all feasible extreme cases (e.g., `low = 1`, `high = sum(array)` or `max(array)`).
* **Monotonicity Check:** Verify that if $x$ works, any $y > x$ also works (or vice versa).

### 3. Floating-Point Binary Search

When searching over continuous real numbers ($double$):

* Do **not** use `low < high`. Use a fixed iteration count (e.g., 60–100 iterations) to guarantee precision without precision loop lockups.

```cpp
for (int i = 0; i < 80; ++i) {
    double mid = low + (high - low) / 2.0;
    if (check(mid)) low = mid;
    else high = mid;
}

```

---