While `Stack` and `ArrayDeque` can both do the job of a "Stack," they are built on completely different foundations. Since you are preparing for product-based company interviews, understanding the "why" behind `ArrayDeque` is essential.

---

### 1. Architectural Difference: Inheritance vs. Interface
The biggest difference is in how they were designed in the Java Collections Framework.

* **`Stack` is a Class:** It inherits from `Vector`. This was a design mistake in early Java because it inherited many methods that a stack shouldn't have (like `insertElementAt(index)`), which violates the LIFO principle.
* **`ArrayDeque` is an Implementation:** It implements the `Deque` (Double Ended Queue) interface. This means it is designed to be a "Linear Collection" that supports element insertion and removal at both ends.

---

### 2. Why you should use `ArrayDeque` (The "Extras")

#### **No Locking (Performance)**
`Stack` is **synchronized**. Every time you call `push()` or `pop()`, Java checks for "locks" to ensure thread safety. If you are writing a standard algorithm (like in a LeetCode problem), you don't need this. `ArrayDeque` is **unsynchronized**, making it significantly faster.

#### **Memory Efficiency**
`Stack` (via `Vector`) grows by a fixed amount or doubles, but it often carries the overhead of the legacy `Vector` object. `ArrayDeque` is a "Low Overhead" resizable array. It uses bitwise operations to calculate the next index, making it very memory-efficient.

#### **Double-Ended Flexibility**
Because it's a `Deque`, you get "extra" methods that a standard `Stack` doesn't have:
* `addFirst()` / `addLast()`
* `removeFirst()` / `removeLast()`
* `peekFirst()` / `peekLast()`

This allows you to use the same object as a **Stack**, a **Queue**, or a **Deque** depending on which methods you call.



---

### 3. Code Comparison: Writing the Code

Here is how the code differs in practice. Notice that with `ArrayDeque`, we use the **Interface** as the reference type—this is a "Best Practice" in Java.

| Feature | Using `java.util.Stack` | Using `java.util.ArrayDeque` |
| :--- | :--- | :--- |
| **Declaration** | `Stack<Integer> s = new Stack<>();` | `Deque<Integer> s = new ArrayDeque<>();` |
| **Nulls** | Allows `null` values. | **Throws Exception** if you try to add `null`. |
| **Iteration** | Iterates from bottom to top (oldest first). | Iterates from top to bottom (newest first). |
| **Speed** | Slower (due to synchronization). | **Fastest** implementation for a stack in Java. |

---

### Summary: Why switch?
For your DSA prep and your work at Cognizant, you should prefer `ArrayDeque` because:
1.  It is **faster** for single-threaded tasks.
2.  It follows **modern Java practices** (coding to an interface).
3.  It prevents the accidental use of "non-stack" methods that `Stack` inherits from `Vector`.

> **Note for your interviews:** If an interviewer asks you to implement a Stack, they are usually looking for the `ArrayDeque` or a manual implementation like the one you wrote. Using `java.util.Stack` is often seen as "outdated."

Yes, absolutely! **`ArrayDeque`** includes all the same method names you are used to with a standard `Stack`. 

In fact, when you use `ArrayDeque` as a stack, you are using the exact same method signatures: `push(e)`, `pop()`, and `peek()`.

---

### The "Stack" Methods in ArrayDeque
Behind the scenes, `ArrayDeque` maps these stack-specific names to its "Double-Ended" logic:

| Stack Method | ArrayDeque Equivalent | What it does |
| :--- | :--- | :--- |
| **`push(e)`** | `addFirst(e)` | Adds the element to the front (top) of the deque. |
| **`pop()`** | `removeFirst()` | Removes and returns the element from the front (top). |
| **`peek()`** | `peekFirst()` | Returns the element at the front (top) without removing it. |



---

### Why this is better than your `myStack` implementation
Your `myStack` had a fixed size. If you pushed too many items, it would simply stop working (or throw an error if you added that logic).

**`ArrayDeque` is "Dynamic":**
1. It starts with a default capacity (usually 16).
2. When the array gets full, it automatically **doubles** its size.
3. It uses **bitwise logic** ($head = (head - 1) \& (elements.length - 1)$) to wrap around the array, making it incredibly fast.

---

### Important: How to write it in your code
In modern Java (especially for the product-based interviews you're preparing for), you should declare it using the **`Deque` interface** but instantiate it as an **`ArrayDeque`**:

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) {
        // Use the Interface (Deque) as the type
        Deque<Integer> stack = new ArrayDeque<>();

        // Works exactly like your myStack!
        stack.push(10);
        stack.push(20);
        
        System.out.println(stack.peek()); // Returns 20
        System.out.println(stack.pop());  // Returns 20 and removes it
        System.out.println(stack.isEmpty()); // Returns false
    }
}
```

### One major "Gotcha"
Unlike the old `java.util.Stack`, **`ArrayDeque` does not allow `null` elements**. If you try to do `stack.push(null)`, it will throw a `NullPointerException`. This is actually considered a safety feature in modern Java.


