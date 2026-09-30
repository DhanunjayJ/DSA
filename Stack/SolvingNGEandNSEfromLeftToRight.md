You can solve **all four variations** (NGE right, NSE right, PGE left, PSE left) strictly traversing **Left-to-Right ($0 \to n-1$)**.

You do not ever strictly *have* to traverse Right-to-Left.

Here is why:

* When looking **Left (PGE / PSE)**, Left-to-Right is already the natural way because elements on the left have already been visited. The current element gets resolved **immediately** using `st.peek()`.
* When looking **Right (NGE / NSE)**, Left-to-Right treats the stack as a **waiting room**. Elements sit on the stack with their indices until a future element arrives and resolves them upon being popped.

---

### The Universal Left-to-Right Blueprint

Always store **indices** in the stack so you can fill your answer array directly without a HashMap.

```java
int[] ans = new int[n];
Arrays.fill(ans, -1); // or n / -1 depending on boundary needs
Deque<Integer> st = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    // ... pop and resolve logic here ...
    st.push(i);
}

```

---

### The 4 Variations Strictly Left-to-Right

#### 1. NGE Right (Next Greater Element)

* **Strategy:** Waiting room. Current `arr[i]` resolves elements in the stack that are smaller than it.
* **Stack condition:** Decreasing stack.

```java
int[] nge = new int[n];
Arrays.fill(nge, -1);
Deque<Integer> st = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    while (!st.isEmpty() && arr[st.peek()] < arr[i]) {
        int idx = st.pop();
        nge[idx] = arr[i]; // resolved!
    }
    st.push(i);
}
// Any index left in st keeps -1 (no greater element to its right)

```

---

#### 2. NSE Right (Next Smaller Element)

* **Strategy:** Waiting room. Current `arr[i]` resolves elements in the stack that are greater than it.
* **Stack condition:** Increasing stack.

```java
int[] nse = new int[n];
Arrays.fill(nse, -1); // or fill with 'n' for range/histogram problems
Deque<Integer> st = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    while (!st.isEmpty() && arr[st.peek()] > arr[i]) {
        int idx = st.pop();
        nse[idx] = arr[i]; // resolved!
    }
    st.push(i);
}

```

---

#### 3. PGE Left (Previous Greater Element)

* **Strategy:** Immediate resolution. Elements to the left are already in the stack; pop useless ones ($\le arr[i]$) to find the nearest greater on the left.
* **Stack condition:** Decreasing stack.

```java
int[] pge = new int[n];
Deque<Integer> st = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    while (!st.isEmpty() && arr[st.peek()] <= arr[i]) {
        st.pop();
    }
    pge[i] = st.isEmpty() ? -1 : arr[st.peek()];
    st.push(i);
}

```

---

#### 4. PSE Left (Previous Smaller Element)

* **Strategy:** Immediate resolution. Pop useless ones ($\ge arr[i]$) to find the nearest smaller on the left.
* **Stack condition:** Increasing stack.

```java
int[] pse = new int[n];
Deque<Integer> st = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    while (!st.isEmpty() && arr[st.peek()] >= arr[i]) {
        st.pop();
    }
    pse[i] = st.isEmpty() ? -1 : arr[st.peek()];
    st.push(i);
}

```

---

### The Power of Left-to-Right: 2-in-1 Resolution

Because Left-to-Right resolves **Right** on pop and **Left** via the element underneath it, you can compute **both PSE and NSE in a single pass** (the exact trick used in *Largest Rectangle in Histogram* and *Sum of Subarray Minimums*):

When popping an element `mid = st.pop()` because `arr[i] < arr[mid]`:

* **Next Smaller Element:** is `i` (the incoming element triggering the pop).
* **Previous Smaller Element:** is `st.peek()` (the element sitting right below it in the stack).

Sticking strictly to **Left-to-Right with indices** is a completely robust, interview-ready strategy for every monotonic stack problem.


----

**Yes, exactly.** That property **always holds** when you maintain a monotonic stack from Left-to-Right.

When you pop an element, both its left and right boundaries are determined simultaneously:

---

### The Two Guarantees on Pop

Consider maintaining a **strictly decreasing stack** (for Next Greater / Previous Greater):

1. **Next Greater on the Right (NGE):**
It is the incoming element `arr[i]` because `arr[i] > arr[mid]` is what triggered the pop.
2. **Previous Greater on the Left (PGE):**
It is `st.peek()` (the element sitting directly underneath `mid` in the stack).

---

### Why Does It Always Hold?

Suppose the stack contains indices `[... , A, mid]`.

* When `mid` was pushed earlier, everything between `A` and `mid` that was smaller than `mid` had already been popped, or they were processed and removed.
* `mid` could never pop `A` when `mid` arrived because `arr[A] > arr[mid]` (the invariant of a decreasing stack).
* Since `A` is the **closest element to the left of `mid` that was strictly greater than `mid**`, `A` is by definition the **Previous Greater Element (PGE)** of `mid`.

---

### Concrete Walkthrough

Take array: `[2, 1, 5, 6, 2, 3]`

Let's trace when elements pop in a decreasing stack looking for NGE and PGE:

| Step | Stack (Indices) | Incoming Element | Action | NGE (Right) | PGE (Left) |
| --- | --- | --- | --- | --- | --- |
| `i = 0` | `[0]` (val: `2`) | `2` | Push `0` | — | — |
| `i = 1` | `[0, 1]` (vals: `2, 1`) | `1` | Push `1` (`1 < 2`) | — | — |
| `i = 2` | Pop `1` | `5` (`5 > 1`) | **`mid = 1` (val 1) pops** | `i = 2` (val `5`) | `st.peek() = 0` (val `2`) |

Notice what happened for `mid = 1`:

* **To its right:** The first element greater than `1` is `5` (`arr[i]`).
* **To its left:** The first element greater than `1` is `2` (`arr[st.peek()]`).

Both boundaries were captured at the exact moment of the pop.

---

### Summary of the "2-in-1" Rule on Pop

Whenever `mid = st.pop()` is triggered by incoming index `i`:

| Stack Type | Trigger Condition | Incoming `i` gives | Element underneath (`st.peek()`) gives |
| --- | --- | --- | --- |
| **Decreasing Stack** | `arr[i] > arr[mid]` | **NGE** (Next Greater) | **PGE** (Previous Greater) |
| **Increasing Stack** | `arr[i] < arr[mid]` | **NSE** (Next Smaller) | **PSE** (Previous Smaller) |

> **Edge case to remember:** If the stack becomes empty after `mid = st.pop()`, it means there is no element to the left that satisfies the condition (i.e., PGE/PSE does not exist, so left boundary is out-of-bounds at `-1`).


---

**Yes, exactly.** That property **always holds** when you maintain a monotonic stack from Left-to-Right.

When you pop an element, both its left and right boundaries are determined simultaneously:

---

### The Two Guarantees on Pop

Consider maintaining a **strictly decreasing stack** (for Next Greater / Previous Greater):

1. **Next Greater on the Right (NGE):**
It is the incoming element `arr[i]` because `arr[i] > arr[mid]` is what triggered the pop.
2. **Previous Greater on the Left (PGE):**
It is `st.peek()` (the element sitting directly underneath `mid` in the stack).

---

### Why Does It Always Hold?

Suppose the stack contains indices `[... , A, mid]`.

* When `mid` was pushed earlier, everything between `A` and `mid` that was smaller than `mid` had already been popped, or they were processed and removed.
* `mid` could never pop `A` when `mid` arrived because `arr[A] > arr[mid]` (the invariant of a decreasing stack).
* Since `A` is the **closest element to the left of `mid` that was strictly greater than `mid**`, `A` is by definition the **Previous Greater Element (PGE)** of `mid`.

---

### Concrete Walkthrough

Take array: `[2, 1, 5, 6, 2, 3]`

Let's trace when elements pop in a decreasing stack looking for NGE and PGE:

| Step | Stack (Indices) | Incoming Element | Action | NGE (Right) | PGE (Left) |
| --- | --- | --- | --- | --- | --- |
| `i = 0` | `[0]` (val: `2`) | `2` | Push `0` | — | — |
| `i = 1` | `[0, 1]` (vals: `2, 1`) | `1` | Push `1` (`1 < 2`) | — | — |
| `i = 2` | Pop `1` | `5` (`5 > 1`) | **`mid = 1` (val 1) pops** | `i = 2` (val `5`) | `st.peek() = 0` (val `2`) |

Notice what happened for `mid = 1`:

* **To its right:** The first element greater than `1` is `5` (`arr[i]`).
* **To its left:** The first element greater than `1` is `2` (`arr[st.peek()]`).

Both boundaries were captured at the exact moment of the pop.

---

### Summary of the "2-in-1" Rule on Pop

Whenever `mid = st.pop()` is triggered by incoming index `i`:

| Stack Type | Trigger Condition | Incoming `i` gives | Element underneath (`st.peek()`) gives |
| --- | --- | --- | --- |
| **Decreasing Stack** | `arr[i] > arr[mid]` | **NGE** (Next Greater) | **PGE** (Previous Greater) |
| **Increasing Stack** | `arr[i] < arr[mid]` | **NSE** (Next Smaller) | **PSE** (Previous Smaller) |

> **Edge case to remember:** If the stack becomes empty after `mid = st.pop()`, it means there is no element to the left that satisfies the condition (i.e., PGE/PSE does not exist, so left boundary is out-of-bounds at `-1`).