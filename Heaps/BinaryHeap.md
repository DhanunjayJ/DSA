A **Binary Heap** is a specialized, tree-based data structure that satisfies two primary properties: the **Shape Property** and the **Heap Property**. It is commonly used to implement priority queues because it allows for very fast access to the minimum or maximum element.

---

## 1. Core Properties

### Shape Property
A binary heap is a **complete binary tree**. This means all levels of the tree are fully filled, except possibly the last level, which is filled from left to right. This structural requirement ensures the tree remains balanced, keeping its height at $O(\log n)$.

### Heap Property
There are two variations of a binary heap based on this property:
* **Max-Heap:** The value of each node is **greater than or equal to** the values of its children. The largest element is always at the root.
* **Min-Heap:** The value of each node is **less than or equal to** the values of its children. The smallest element is always at the root.



---

## 2. Array Representation
Because a binary heap is a complete binary tree, it can be efficiently stored in an **array** without using pointers. For a node stored at index $i$ (using 0-based indexing):

* **Left Child:** $2i + 1$
* **Right Child:** $2i + 2$
* **Parent:** $\lfloor (i - 1) / 2 \rfloor$

This mapping allows for $O(1)$ navigation between parents and children.

---

## 3. Common Operations

| Operation | Description | Time Complexity |
| :--- | :--- | :--- |
| **Insert** | Add a new element at the end and "bubble up" (heapify up) to restore order. | $O(\log n)$ |
| **Extract Min/Max** | Remove the root, replace it with the last element, and "bubble down" (heapify down). | $O(\log n)$ |
| **Peek** | Return the root element without removing it. | $O(1)$ |
| **Heapify** | Create a heap from an unordered array. | $O(n)$ |

---

## 4. Why Use a Binary Heap?
While a sorted array also allows $O(1)$ access to the min/max, inserting into a sorted array takes $O(n)$. A binary heap provides a middle ground:
1.  **Efficiency:** It balances the cost of insertion and deletion ($O(\log n)$), making it ideal for dynamic datasets where you constantly need the "most important" item.
2.  **Memory:** Since it uses an array, there is no overhead for storing left/right child pointers as seen in Linked Lists or Binary Search Trees.
3.  **Applications:** It is the backbone of the **Heapsort** algorithm and is used in **Dijkstra’s Shortest Path** algorithm to efficiently find the next vertex with the shortest distance.


Understanding the array representation of a Binary Heap is a "lightbulb moment" for many developers. It’s elegant because it allows us to treat a linear **Array** as a hierarchical **Tree** without the memory overhead of pointers (like `node.left` or `node.right`).

Here is a breakdown of how the math works and why it’s so efficient.

---

## 1. The Logic of the Mapping
Since a Binary Heap is a **complete binary tree** (filled level by level, left to right), there are no gaps. This predictable structure allows us to use simple arithmetic to find any relative.

### Finding Children (Moving Down)
If you are at a node at index $i$, its children are always located further down the array:
* **Left Child:** $2i + 1$
* **Right Child:** $2i + 2$

### Finding the Parent (Moving Up)
If you are at a child node at index $i$, you can jump back to its parent using:
* **Parent:** $\lfloor (i - 1) / 2 \rfloor$ *(Note: In programming languages like Java or C++, integer division automatically handles the floor/rounding down).*

---

## 2. Visual Example
Imagine we have the following elements in a **Min-Heap**: `[10, 15, 30, 40, 50, 100, 40]`.



### Let's trace the relationships:

| Node Value | Index ($i$) | Left Child ($2i+1$) | Right Child ($2i+2$) |
| :--- | :--- | :--- | :--- |
| **10** (Root) | **0** | Index 1 (Value: 15) | Index 2 (Value: 30) |
| **15** | **1** | Index 3 (Value: 40) | Index 4 (Value: 50) |
| **30** | **2** | Index 5 (Value: 100) | Index 6 (Value: 40) |

**Verification of Parent Logic:**
Look at the node **100** at **Index 5**. Who is its parent?
$$(5 - 1) / 2 = 4 / 2 = 2$$
Index 2 contains the value **30**, which is indeed the parent of 100.

---

## 3. Why this is powerful
1.  **Space Efficiency:** You don't store pointers. In a large tree, saving two pointers (8–16 bytes) per node adds up to significant memory savings.
2.  **Cache Locality:** Arrays are stored in contiguous memory blocks. CPUs are much faster at accessing elements that are next to each other in memory compared to following pointers to random memory addresses.
3.  **Simplicity:** Operations like "Heapify Up" or "Heapify Down" simply involve swapping elements at calculated indices.

### A Note on 1-Based Indexing
In some textbooks, you might see the array starting at index **1** instead of **0**. If the root is at `arr[1]`, the math becomes slightly cleaner:
* **Left:** $2i$
* **Right:** $2i + 1$
* **Parent:** $i / 2$

However, in modern languages like Java, we stick to **0-based indexing**, so the formulas you provided ($2i+1$ and $2i+2$) are the ones you will use in your code.


The `insert()` operation is all about maintaining the balance of the tree while ensuring the heap property (Min or Max) stays intact. Since you are likely working with **Java** for your banking project, you can think of this as adding an element to the end of an `ArrayList` and then "bubbling it up."

Here is a clear breakdown of the process for a **Min-Heap**:

---

## 1. The Two-Step Process

### Step A: The "Shape" Fix (Insertion)
We always maintain a **Complete Binary Tree**. To do this, we insert the new element at the **next available spot** in the array (the end of the list). 
* In array terms: `arr[size] = newKey`.
* This preserves the shape but often breaks the **Heap Property** (the new value might be smaller than its parent).

### Step B: The "Property" Fix (Heapify Up / Percolate Up)
We compare the new element with its **parent**.
* **Formula:** Parent is at `(index - 1) / 2`.
* **The Rule:** In a Min-Heap, if `newKey < parent`, they are in the wrong order.
* **The Action:** **Swap** them.
* **Repeat:** Continue comparing and swapping until the new key reaches the root or finds a parent that is smaller than it.

---

## 2. Visual Walkthrough
Imagine we have a Min-Heap and we want to insert the value **5**.



1.  **Placement:** We put **5** at the very last position in the array.
2.  **Comparison 1:** We check its parent. If the parent is **12**, we swap them because $5 < 12$.
3.  **Comparison 2:** Now **5** is where **12** used into be. We check its *new* parent. If that parent is **8**, we swap again because $5 < 8$.
4.  **Final Stop:** Now **5** is at the root (or its parent is **2**). Since $5 > 2$, we stop. The heap is restored.

---

## 3. Complexity Analysis

| Metric | Complexity | Why? |
| :--- | :--- | :--- |
| **Time Complexity** | $O(\log n)$ | In the worst case (inserting a new minimum), we swap the element from the leaf to the root. The height of a balanced tree is $\log n$. |
| **Space Complexity** | $O(1)$ | We perform the swaps "in-place" within the array, requiring no extra storage. |

---

## 4. Quick Code Logic (Java Style)
Since you prefer modern Java practices, here is how that logic looks conceptually:

```java
public void insert(int key) {
    // 1. Add to the end
    heap.add(key);
    int current = heap.size() - 1;

    // 2. Heapify Up
    while (current > 0 && heap.get(current) < heap.get(parent(current))) {
        swap(current, parent(current));
        current = parent(current);
    }
}
```

This "upward" movement ensures that even if you insert a very small value, it eventually finds its rightful place at the top of the hierarchy.


The `heapify()` operation (specifically **Heapify Down** or **Sinking**) is the standard way to fix a heap when the root or a parent node is larger than its children in a Min-Heap. 

While `insert()` pushes a value **up**, `heapify()` pushes a value **down** until it finds its correct level.

---

## 1. The Core Logic: "The Sinking Process"
Imagine the node at index $i$ is "too heavy" (its value is too large). To fix this, you don't just swap it with any child—you swap it with the **smallest** of its two children. This ensures that after the swap, the new parent is smaller than both of its children.

### The Algorithm Steps:
1.  **Identify Candidates:** Look at the node at index $i$, its left child ($2i + 1$), and its right child ($2i + 2$).
2.  **Find the Smallest:** Compare these three values to find the absolute minimum.
3.  **The Decision:**
    * If the parent is already the smallest, **Stop**. The property is satisfied.
    * If a child is smaller, **Swap** the parent with that smallest child.
4.  **Recurse:** Because you moved a "large" value down to a new index, that specific subtree might now be violated. Call `heapify()` again on that child's index.

---

## 2. Visual Example
Suppose we have a node **30** at the root, but its children are **10** and **20**.



1.  **Compare:** Parent (30), Left (10), Right (20). 
2.  **Smallest:** The Left child (**10**) is the smallest.
3.  **Swap:** Swap 30 and 10. Now 10 is at the root, and 30 is at the left child's position.
4.  **Next Step:** Check the new position of 30. If it has children (say 40 and 50), compare again. Since $30 < 40$ and $30 < 50$, we stop.

---

## 3. Complexity & Performance

| Feature | Detail |
| :--- | :--- |
| **Time Complexity** | $O(\log n)$ |
| **Depth** | In the worst case, the node "sinks" from the root all the way to a leaf. |
| **Space Complexity** | $O(\log n)$ for recursion stack (or $O(1)$ if implemented iteratively). |

---

## 4. Implementation Tip (Java)
Given your preference for `ArrayDeque` and modern Java, you’ll find that while recursion is intuitive, an **iterative** approach is often used in production for `heapify()` to avoid `StackOverflowError` on extremely deep trees:

```java
void minHeapify(int i) {
    int smallest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    // Check if left child is smaller than current smallest
    if (left < size && heap[left] < heap[smallest]) {
        smallest = left;
    }

    // Check if right child is smaller than current smallest
    if (right < size && heap[right] < heap[smallest]) {
        smallest = right;
    }

    // If smallest is not the parent, swap and continue
    if (smallest != i) {
        swap(i, smallest);
        minHeapify(smallest); // Recursive call
    }
}
```

## Why is this important?
You’ll use this exact logic when you perform **Extract-Min** (deleting the root). When you remove the smallest element, you move the last element of the array to the root, which almost always violates the heap property. `heapify()` is the tool that restores order.


Exactly right. Since the core rule of a **Min-Heap** is that every parent must be smaller than its children, the absolute smallest value is mathematically forced to the top.

## The `getMin()` Operation

In a Min-Heap, the root is always at **index 0** of your array. This operation is the most efficient part of using a heap.

* **Logic:** Access the first element of the internal array.
* **Time Complexity:** $O(1)$ — This is a constant time operation because you are simply performing an array lookup by index.
* **Space Complexity:** $O(1)$ — No extra memory is used.

### Why this beats a Binary Search Tree (BST)
In a **BST**, finding the minimum requires you to traverse the left-most branch, which takes $O(\log n)$ time. In a **Min-Heap**, you have immediate access.

### Important Guardrail
When implementing this in your Java project, you should always check if the heap is empty before accessing index 0 to avoid an `ArrayIndexOutOfBoundsException`.

```java
public int getMin() {
    if (size == 0) {
        throw new NoSuchElementException("Heap is empty");
    }
    return heap[0];
}
```

---

## ExtractMin():
Since you've mastered `getMin()` and `heapify()`, the next logical step is **ExtractMin()**, which actually *removes* that minimum value.

1.  **Save the root:** Store `arr[0]` so you can return it at the end.
2.  **Replace:** Take the **last** element in the array and move it to `arr[0]`.
3.  **Decrease size:** Shrink your pointer/array size.
4.  **Fix:** Call `heapify(0)` to sink that "too-large" element down to its correct spot.



This removal process takes **$O(\log n)$** because of the heapify step.

Are you planning to implement this heap logic manually for your banking app project, or are you preparing these concepts specifically for your upcoming mock interviews?

You’ve hit on the most critical part of managing a Priority Queue. While `getMin()` only peeks at the value, `extractMin()` is a destructive operation that requires careful reorganization to keep the tree balanced and ordered.

Since you are preparing for your **5th mock interview** on Exponent this April, being able to explain *why* we swap with the last element is key—it’s the only way to delete a node while maintaining the **Complete Binary Tree** property without leaving "holes" in the array.

---

## 1. The Step-by-Step Breakdown

### Step 1: Secure the Result
The value at `arr[0]` is the one we need to return. We save this in a temporary variable.

### Step 2: The "Tail-to-Head" Swap
We take the very last element in the array (at `arr[size - 1]`) and move it to `arr[0]`. 
* **Why?** In an array-based tree, removing the last element is $O(1)$. Removing the first element would require shifting every other element, which is $O(n)$. By moving the last element to the top, we keep the operation efficient.

### Step 3: Shrink the Heap
We decrease the `size` variable. The old root is now effectively gone.

### Step 4: Restore Order (The "Sink")
The new root is almost certainly too large for its position. We call `heapify(0)` to push this value down to its correct level.



---

## 2. Walkthrough Example
Suppose we have a Min-Heap: `[5, 10, 15, 20, 25]`.

1.  **Extract 5:** We save **5** to return later.
2.  **Move Last:** The last element is **25**. We move it to the root.
    * Array becomes: `[25, 10, 15, 20]` (Size is now 4).
3.  **Heapify(0):** * Compare **25** with its children **10** and **15**.
    * **10** is the smallest child. Swap **25** and **10**.
    * Array becomes: `[10, 25, 15, 20]`.
4.  **Repeat:**
    * Compare **25** with its new child **20**.
    * **20** is smaller. Swap **25** and **20**.
    * Array becomes: `[10, 20, 15, 25]`.
5.  **Done:** The Min-Heap property is restored.

---

## 3. Complexity Summary

| Component | Complexity |
| :--- | :--- |
| **Swapping Root** | $O(1)$ |
| **Heapify Down** | $O(\log n)$ |
| **Total Time** | **$O(\log n)$** |
| **Space** | $O(1)$ (In-place) |

---

## 4. Interview Tip: Handling the "Edge Case"
During an interview, always mention how you handle a heap with **only one element**. 
* If `size == 1`, you simply remove the element and don't call `heapify()`, as there are no children to compare against.

Since you've been working on your **Banking Application** using Java, are you planning to use a custom Heap implementation for a specific feature—like processing the highest-priority pending transactions—or are you sticking with the built-in `PriorityQueue`?


`decreaseKey()` is an essential operation when you need to change the priority of an element that is already inside the heap. 

Think of it like a **"Priority Promotion."** In your banking application, if a transaction suddenly becomes "High Priority," you would use this to move it up the queue.

---

## 1. Why "Decrease"?
In a **Min-Heap**, a smaller number represents a **higher priority** (like being #1 in line). So, "decreasing" the value actually "increases" its importance, forcing it to move toward the root.

---

## 2. The Logic: "The Bubble Up"
Since you are replacing a value with something **smaller**, you don't need to worry about the children below it. If the old value was already smaller than its children, the new, even smaller value will definitely be smaller than them.

The only risk is that the new value might now be smaller than its **parent**.

### The Steps:
1.  **Update:** Change the value at `arr[i]` to `new_val`.
2.  **Compare with Parent:** Check the parent at index `(i - 1) / 2`.
3.  **The Violation:** If `arr[i] < arr[parent]`, the Min-Heap property is broken.
4.  **Fix (Percolate Up):** Swap the node with its parent.
5.  **Repeat:** Keep swapping upward until the new value is either at the root or its parent is smaller than it.



---

## 3. Complexity & Performance

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $O(\log n)$ | In the worst case, the updated value becomes the new minimum and travels from the bottom level to the root. |
| **Space Complexity** | $O(1)$ | It is an in-place swap operation within the array. |

---

## 4. Real-World Use Case: Dijkstra’s Algorithm
You’ll likely encounter this during your DSA prep for the Amazon interview. In **Dijkstra’s Shortest Path algorithm**, we often find a "shorter" distance to a node we've already seen. When that happens, we call `decreaseKey()` to update that node's distance in the priority queue so it gets processed sooner.

## 5. Summary of Your Journey
So far, you’ve covered the entire lifecycle of a Binary Heap:
* **Representation:** Using an array for $O(1)$ navigation.
* **Insertion:** Add to end $\rightarrow$ Heapify Up.
* **ExtractMin:** Replace root with last $\rightarrow$ Heapify Down.
* **DecreaseKey:** Update value $\rightarrow$ Heapify Up.

Everything you’ve shared aligns perfectly with standard heap theory. Is there a specific implementation detail—like how to handle this in **Java** without a built-in `decreaseKey` in the `PriorityQueue` class—that you'd like to dive into?

(Note: Java's `java.util.PriorityQueue` doesn't actually have a `decreaseKey` method; you usually have to `remove()` and `add()` again, which is $O(n)$. For a true $O(\log n)$ `decreaseKey`, you'd have to build the custom heap we've been discussing!)



That is a clever and standard way to handle deletion! Instead of trying to shift elements around manually, you are essentially "hacking" the system by using the operations you’ve already built (**DecreaseKey** and **ExtractMin**).

By turning the target element into the smallest possible value ($-\infty$), you force it to float to the top where it can be easily removed.

---

## 1. The Strategy: "Float and Remove"

### Step 1: Assign `INT_MIN`
You change the value at the specific index $i$ to the smallest possible integer.
* **In Java:** Since you're using Java, you'd use `Integer.MIN_VALUE`.
* This makes that node the "new king" of the heap because no other value can be smaller.

### Step 2: `decreaseKey(i, Integer.MIN_VALUE)`
As we discussed, `decreaseKey` uses **Heapify Up**. Since the value is now the absolute minimum, it will swap with its parents until it arrives at the **Root** (Index 0).

### Step 3: `extractMin()`
Now that the unwanted element is sitting at the root, you call `extractMin()`.
* It takes the last element, puts it at the root.
* It removes the `INT_MIN` (your target node).
* It runs **Heapify Down** to fix the order.

---

## 2. Visual Walkthrough
Imagine you want to delete the value **15** at **Index 2** in this heap: `[5, 10, 15, 20, 25]`

1.  **Target found:** `arr[2] = 15`. Change it to `-∞`. 
    * Heap: `[5, 10, -∞, 20, 25]`
2.  **DecreaseKey:** `-∞` is smaller than its parent (**5**). Swap them.
    * Heap: `[-∞, 10, 5, 20, 25]`
3.  **ExtractMin:**
    * Save `-∞`.
    * Move last element (**25**) to root: `[25, 10, 5, 20]`
    * **Heapify Down:** **25** is larger than children **10** and **5**. Smallest is **5**. Swap.
    * Final Heap: `[5, 10, 25, 20]`

The value **15** is gone, and the heap is valid.

---

## 3. Complexity Analysis

| Operation | Complexity |
| :--- | :--- |
| **DecreaseKey** | $O(\log n)$ |
| **ExtractMin** | $O(\log n)$ |
| **Total Deletion** | **$O(\log n)$** |

---

## 4. Pro-Tip for your Mock Interview
If an interviewer asks, "Can we delete a node without using `INT_MIN`?" the answer is **yes**.
1.  Swap the node at index $i$ with the **last element**.
2.  Decrease the size.
3.  Now, the element that was moved to index $i$ might be too small (needs to move **up**) or too big (needs to move **down**). So you would call `heapifyUp(i)` **or** `heapifyDown(i)`.

Using your `INT_MIN` method is often preferred in exams and quick implementations because it reuses existing functions without needing extra logic to decide which way to heapify.

Does this "Float and Remove" logic feel clear, or should we look at how to handle this if you don't have access to a constant like `INT_MIN`?


This implementation is a solid, classic representation of a **Min-Heap** in Java. It perfectly translates the theoretical steps you've shared into working code.

Here is a breakdown of why this code works well and a few "professional developer" observations to keep in mind for your upcoming mock interviews or your work at Cognizant.

---

## 1. Code Logic Highlights
* **The Math:** Your `parent`, `left`, and `right` methods correctly implement the 0-based indexing formulas ($2i+1$, $2i+2$).
* **Safety Guards:** In `ExtractMin`, you correctly handle the `size <= 0` and `size == 1` edge cases. This prevents `ArrayIndexOutOfBounds` or unnecessary recursion.
* **The Delete "Trick":** Your `Delete` method correctly utilizes the `Integer.MIN_VALUE` strategy we discussed, making the logic clean and reusing `Decreasekey` and `ExtractMin`.

---

## 2. Technical Observations & Tips
Since you are a software developer working on high-performance trading platforms (Fidessa), here are a few ways to sharpen this specific implementation:

### Static vs. Instance Variables
In your code, `capacity`, `size`, and `arr[]` are marked as **`static`**. 
* **Current Issue:** If you created two different heaps (`BinaryHeap h1 = new BinaryHeap(10);` and `BinaryHeap h2 = new BinaryHeap(20);`), they would both share the same array and size. `h2` would overwrite `h1`.
* **Fix:** Remove the `static` keyword from those variables so each heap object has its own data.

### The Swap Utility
You perform the "swap" logic manually in `Insert`, `Heapify`, and `Decreasekey`. 
* **Clean Code Tip:** Create a private helper method `swap(int i, int j)`. This reduces code duplication and makes your main methods easier to read.

### Dynamic Resizing
Your current heap has a fixed `capacity`. 
* **Pro Tip:** In a real-world banking app, you might not know the number of transactions beforehand. You could use `Arrays.copyOf()` to double the size of the array when `size == capacity`, similar to how `ArrayList` works.

---

## 3. Dry Run of your `main` method
Let’s look at the most interesting part of your test:

1.  **Initial Inserts:** After inserting `4, 1, 2, 6, 7, 3, 8, 5`, the root (`getMin`) will be **1**.
2.  **`h.Insert(-1)`:** The value `-1` is added to the end and bubbles up to the root. New min: **-1**.
3.  **`h.Decreasekey(3, -2)`:** You took the element at index 3 and made it **-2**. Since -2 is now the smallest value in the entire heap, it bubbles up to index 0. New min: **-2**.
4.  **`h.ExtractMin()`:** This removes **-2**. The next smallest value (which was **-1**) will move to the root. New min: **-1**.
5.  **`h.Delete(0)`:** This targets the root specifically, turns it into `MIN_VALUE`, floats it (it's already at the top), and extracts it. The next smallest value takes over.

---

## 4. Performance Summary

| Method | Time Complexity | Space Complexity |
| :--- | :--- | :--- |
| `Insert` | $O(\log n)$ | $O(1)$ |
| `ExtractMin` | $O(\log n)$ | $O(\log n)$ (due to recursion stack) |
| `Decreasekey` | $O(\log n)$ | $O(1)$ |
| `Delete` | $O(\log n)$ | $O(\log n)$ |
| `getMin` | $O(1)$ | $O(1)$ |

This code is a great study tool for your Amazon prep. Are you planning to implement a **Max-Heap** version as well, or are you moving on to heap-related problems like "Kth Largest Element" next?


This implementation is a solid, classic representation of a **Min-Heap** in Java. It perfectly translates the theoretical steps you've shared into working code.

Here is a breakdown of why this code works well and a few "professional developer" observations to keep in mind for your upcoming mock interviews or your work at Cognizant.

---

## 1. Code Logic Highlights
* **The Math:** Your `parent`, `left`, and `right` methods correctly implement the 0-based indexing formulas ($2i+1$, $2i+2$).
* **Safety Guards:** In `ExtractMin`, you correctly handle the `size <= 0` and `size == 1` edge cases. This prevents `ArrayIndexOutOfBounds` or unnecessary recursion.
* **The Delete "Trick":** Your `Delete` method correctly utilizes the `Integer.MIN_VALUE` strategy we discussed, making the logic clean and reusing `Decreasekey` and `ExtractMin`.

---

## 2. Technical Observations & Tips
Since you are a software developer working on high-performance trading platforms (Fidessa), here are a few ways to sharpen this specific implementation:

### Static vs. Instance Variables
In your code, `capacity`, `size`, and `arr[]` are marked as **`static`**. 
* **Current Issue:** If you created two different heaps (`BinaryHeap h1 = new BinaryHeap(10);` and `BinaryHeap h2 = new BinaryHeap(20);`), they would both share the same array and size. `h2` would overwrite `h1`.
* **Fix:** Remove the `static` keyword from those variables so each heap object has its own data.

### The Swap Utility
You perform the "swap" logic manually in `Insert`, `Heapify`, and `Decreasekey`. 
* **Clean Code Tip:** Create a private helper method `swap(int i, int j)`. This reduces code duplication and makes your main methods easier to read.

### Dynamic Resizing
Your current heap has a fixed `capacity`. 
* **Pro Tip:** In a real-world banking app, you might not know the number of transactions beforehand. You could use `Arrays.copyOf()` to double the size of the array when `size == capacity`, similar to how `ArrayList` works.

---

## 3. Dry Run of your `main` method
Let’s look at the most interesting part of your test:

1.  **Initial Inserts:** After inserting `4, 1, 2, 6, 7, 3, 8, 5`, the root (`getMin`) will be **1**.
2.  **`h.Insert(-1)`:** The value `-1` is added to the end and bubbles up to the root. New min: **-1**.
3.  **`h.Decreasekey(3, -2)`:** You took the element at index 3 and made it **-2**. Since -2 is now the smallest value in the entire heap, it bubbles up to index 0. New min: **-2**.
4.  **`h.ExtractMin()`:** This removes **-2**. The next smallest value (which was **-1**) will move to the root. New min: **-1**.
5.  **`h.Delete(0)`:** This targets the root specifically, turns it into `MIN_VALUE`, floats it (it's already at the top), and extracts it. The next smallest value takes over.

---

## 4. Performance Summary

| Method | Time Complexity | Space Complexity |
| :--- | :--- | :--- |
| `Insert` | $O(\log n)$ | $O(1)$ |
| `ExtractMin` | $O(\log n)$ | $O(\log n)$ (due to recursion stack) |
| `Decreasekey` | $O(\log n)$ | $O(1)$ |
| `Delete` | $O(\log n)$ | $O(\log n)$ |
| `getMin` | $O(1)$ | $O(1)$ |

This code is a great study tool for your Amazon prep. Are you planning to implement a **Max-Heap** version as well, or are you moving on to heap-related problems like "Kth Largest Element" next?

Since you're working in **Java**, I've refined the implementation to follow professional coding standards. This version removes the `static` keywords so you can create multiple independent heap objects, and I've added a `swap` helper to keep the code clean—something interviewers at companies like Amazon really look for.

### **Refined BinaryHeap.java**

```java
import java.util.*;

class BinaryHeap {
    private int[] arr;
    private int capacity;
    private int size;

    public BinaryHeap(int cap) {
        this.capacity = cap;
        this.size = 0;
        this.arr = new int[capacity];
    }

    // Helper Methods for Indexing
    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i)   { return 2 * i + 1; }
    private int right(int i)  { return 2 * i + 2; }

    // Utility swap method to keep code DRY
    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void insert(int x) {
        if (size == capacity) {
            System.out.println("Heap Overflow");
            return;
        }

        // Insert at the end
        int k = size;
        arr[k] = x;
        size++;

        // Heapify Up (Percolate Up)
        while (k != 0 && arr[parent(k)] > arr[k]) {
            swap(k, parent(k));
            k = parent(k);
        }
    }

    public void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;

        if (l < size && arr[l] < arr[smallest]) smallest = l;
        if (r < size && arr[r] < arr[smallest]) smallest = r;

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    public int extractMin() {
        if (size <= 0) return Integer.MAX_VALUE;
        if (size == 1) {
            size--;
            return arr[0];
        }

        int root = arr[0];
        arr[0] = arr[size - 1]; // Move last to root
        size--;
        heapify(0); // Heapify Down (Sinking)

        return root;
    }

    public void decreaseKey(int i, int newVal) {
        arr[i] = newVal;
        // Since value decreased, it can only move UP
        while (i != 0 && arr[parent(i)] > arr[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public void delete(int i) {
        // Force element to root then extract
        decreaseKey(i, Integer.MIN_VALUE);
        extractMin();
    }

    public int getMin() {
        return (size > 0) ? arr[0] : Integer.MAX_VALUE;
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        BinaryHeap h = new BinaryHeap(15);
        h.insert(3);
        h.insert(2);
        h.delete(1); // Deletes element at index 1
        h.insert(15);
        h.insert(5);
        h.insert(4);
        h.insert(45);

        System.out.println("Min: " + h.extractMin());
        System.out.println("New Min: " + h.getMin());
        
        h.decreaseKey(2, 1);
        System.out.println("Min after decreaseKey: " + h.getMin());
    }
}
```

---

### **Key Improvements in this Version:**
1.  **Encapsulation:** Used `private` for the array and fields. This mimics how you’d build a real library or component in your banking app.
2.  **`swap()` Method:** Notice how much cleaner `insert` and `heapify` look without the 3-line temp swap logic repeated everywhere.
3.  **Recursion vs. Iteration:** Your `heapify` remains recursive, which is perfect for understanding the concept. For your Amazon OA prep, remember that `insert` is iterative here ($O(1)$ extra space) while `heapify` is recursive ($O(\log n)$ stack space).

This code is now ready to be plugged into any project. Are you planning to try a LeetCode problem with this, like **"Kth Largest Element in an Array"**?