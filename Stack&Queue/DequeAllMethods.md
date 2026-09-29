In Java, the `Deque` (Double-Ended Queue) interface is the modern, preferred way to implement both **Stacks** and **Queues**. While you might be tempted to use the old `Stack` class, it is actually considered legacy (and slower due to synchronization). 

A `Deque` allows you to add or remove elements from both the front and the back.

---

## 1. Deque as a Stack (LIFO)
When using a `Deque` as a stack, you only interact with the **head** (the front) of the deque. 

### Key Methods
| Stack Operation | Deque Method | Description |
| :--- | :--- | :--- |
| **Push** | `push(e)` | Adds element to the front. Throws exception if full. |
| **Pop** | `pop()` | Removes and returns the front element. Throws exception if empty. |
| **Peek** | `peek()` | Returns the front element without removing it. Returns `null` if empty. |

### Time Complexity
All standard Stack operations using a Deque (like `ArrayDeque`) are **$O(1)$** (amortized for `push`).

---

## 2. Deque as a Queue (FIFO)
When using a `Deque` as a queue, you insert at the **back** and remove from the **front**.

### Key Methods
Java provides two sets of methods: those that throw exceptions and those that return a special value (`null` or `false`).

| Operation | Throws Exception | Returns Special Value |
| :--- | :--- | :--- |
| **Insert (Back)** | `addLast(e)` | `offerLast(e)` |
| **Remove (Front)** | `removeFirst()` | `pollFirst()` |
| **Examine (Front)** | `getFirst()` | `peekFirst()` |

### Time Complexity
All standard Queue operations are **$O(1)$**.

---

## 3. Common Implementations
There are two main classes you’ll use at Cognizant or for your Amazon OA preparation:

### ArrayDeque
* **Performance:** Faster than `Stack` and `LinkedList` for most scenarios.
* **Memory:** More efficient as it doesn't create node objects for every insertion.
* **Restriction:** Does **not** allow `null` elements.


### LinkedList
* **Performance:** Slower due to node allocation, but better if you are doing constant-time removals while iterating.
* **Flexibility:** Implements both `List` and `Deque`.
* **Nulls:** Allows `null` elements (though generally discouraged).

---

## 4. Useful "Double-Ended" Methods
Since it's a Deque, you can mix and match to solve complex DSA problems (like the "Sliding Window Maximum" problem):

* **`descendingIterator()`**: To iterate from back to front.
* **`removeFirstOccurrence(Object o)`**: Removes the first instance of an element.
* **`removeLastOccurrence(Object o)`**: Removes the last instance of an element.

---

## Summary Comparison

| Feature | ArrayDeque | LinkedList |
| :--- | :--- | :--- |
| **Internal Structure** | Resizable Array | Doubly Linked List |
| **Head/Tail Access** | $O(1)$ | $O(1)$ |
| **Random Access** | Not supported ($O(n)$) | Not supported ($O(n)$) |
| **Memory Overhead** | Low | High (Node objects) |

Given that you are practicing for the **Amazon SDE1 Work Style Assessment and OA**, `ArrayDeque` is almost always the superior choice for implementing a Stack or Queue because of its lower overhead and cache-friendliness.




When you use a `Deque` (Double-Ended Queue) in its full capacity, you are treating it as a structure where you can **insert, remove, and examine** elements from both the **Head** (Front) and the **Tail** (Back) with equal efficiency.

In Java, `Deque` methods are categorized into two groups: those that **throw an exception** if the operation fails (e.g., adding to a full deque or removing from an empty one) and those that return a **special value** (`null` or `false`).

---

## 1. Summary of Double-Ended Methods

| Operation | Position | Throws Exception | Returns Special Value |
| :--- | :--- | :--- | :--- |
| **Insert** | Front | `addFirst(e)` | `offerFirst(e)` |
| | Back | `addLast(e)` | `offerLast(e)` |
| **Remove** | Front | `removeFirst()` | `pollFirst()` |
| | Back | `removeLast()` | `pollLast()` |
| **Examine** | Front | `getFirst()` | `peekFirst()` |
| | Back | `getLast()` | `peekLast()` |



---

## 2. Specialized Utility Methods
Beyond the standard insertion and removal, `Deque` provides several methods that are extremely useful for complex Data Structures and Algorithms (DSA) problems:

* **`descendingIterator()`**: Returns an iterator that traverses the deque from back to front.
* **`removeFirstOccurrence(Object o)`**: Removes the first instance of the specified element (searching from head to tail).
* **`removeLastOccurrence(Object o)`**: Removes the last instance of the specified element (searching from head to tail).
* **`size()`**: Returns the number of elements in $O(1)$ time.
* **`contains(Object o)`**: Checks if an element exists in $O(n)$ time.

---

## 3. Time Complexity
Regardless of whether you use `ArrayDeque` or `LinkedList`, the primary operations remain highly efficient:

| Operation | Complexity | Note |
| :--- | :--- | :--- |
| **Head/Tail Access** | $O(1)$ | Constant time for add, remove, and peek. |
| **Search** | $O(n)$ | Linear time to find a specific value. |
| **Deletion (by value)**| $O(n)$ | Must traverse the list/array to find the element first. |

---

## 4. When to use a Double-Ended Queue?
In your **LeetCode** practice and **Amazon SDE1** preparation, you will most likely use a `Deque` for:

1.  **Sliding Window Maximum:** Keeping track of the "useful" elements in a window.
2.  **Palindrome Checking:** Comparing the `removeFirst()` and `removeLast()` elements iteratively.
3.  **Zig-Zag Tree Traversal:** Switching between stack-like and queue-like behavior as you traverse levels.
