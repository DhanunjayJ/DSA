

## 1. The Core Data Structure: `PriorityQueue<E>`

By default, Java's `PriorityQueue` is a **Min-Heap**.

### Basic Syntax
```java
// Default: Min-Heap (Smallest element at the top)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-Heap: (Largest element at the top)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

### Essential Methods (Cheat Sheet)
| Operation | Method | Time Complexity |
| :--- | :--- | :--- |
| **Insert** | `add(element)` or `offer(e)` | $O(\log N)$ |
| **Remove Top** | `poll()` (returns null if empty) | $O(\log N)$ |
| **Peek Top** | `peek()` (returns null if empty) | $O(1)$ |
| **Size** | `size()` | $O(1)$ |
| **Check Empty** | `isEmpty()` | $O(1)$ |

---

## 2. Handling Complex Objects (The "Amazon Level")
Interviewers rarely ask you to just store integers. They usually want you to store objects (like `Transaction`, `Student`, or `Pair`). For this, you must use a **Comparator**.

```java
class Transaction {
    int id;
    double amount;
    
    Transaction(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }
}

// Max-Heap based on transaction amount
PriorityQueue<Transaction> pq = new PriorityQueue<>((a, b) -> Double.compare(b.amount, a.amount));

pq.offer(new Transaction(1, 100.50));
pq.offer(new Transaction(2, 500.00));

System.out.println(pq.peek().amount); // Output: 500.0
```

---

## 3. Top 3 "Patterns" to Solve Heap Problems

### Pattern A: The "K-th Largest/Smallest" Element
This is the most common use case.
* **To find K-th Smallest:** Use a **Max-Heap**. Keep the size at $K$. If you add a $(K+1)$-th element, `poll()` the largest. The one left at the top is your answer.
* **To find K-th Largest:** Use a **Min-Heap**. Keep the size at $K$.

### Pattern B: The "Top K Frequent" Elements
1. Count frequencies using a `HashMap`.
2. Push entries into a Heap based on the frequency count.
3. Extract the top $K$.

### Pattern C: "Merging K Sorted Lists"
1. Put the first element of each list into a Min-Heap.
2. `poll()` the smallest, and immediately `offer()` the next element from that same list.
3. Repeat until the heap is empty.

---

## 4. Crucial Tips for Performance
* **Initialization:** If you already have a list of $N$ elements, don't `offer()` them one by one ($O(N \log N)$). Instead, pass the list to the constructor: `new PriorityQueue<>(myList)`. This uses the $O(N)$ Build-Heap algorithm we just discussed!
* **Removal:** `pq.remove(object)` is **$O(N)$** because it has to search the array for that specific object. Avoid it if possible; stick to `poll()`.
* **Updating:** If an object’s value changes while it’s in the PQ, the PQ **does not resort itself**. You must `remove()` and `re-add()` it.

---

## 5. Summary Table for Your Mock Interview



| Scenario | Use This Heap |
| :--- | :--- |
| **Find K-th Largest** | Min-Heap |
| **Find K-th Smallest** | Max-Heap |
| **Find Closest to Origin** | Max-Heap (of distances) |
| **Task Scheduling/Priorities** | Min-Heap (on time/priority) |

