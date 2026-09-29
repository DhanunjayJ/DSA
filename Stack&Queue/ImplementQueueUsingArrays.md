A naive array implementation of a queue that shifts elements left on every dequeue takes $O(N)$ time. To achieve strict **$O(1)$** time for both enqueue and dequeue, an array-based queue is implemented as a **circular buffer** (ring buffer) using two pointers (`front` and `rear`) and the modulo operator (`%`).

---

### Circular Queue Design

* **`front`**: Points to the index of the first valid element.
* **`rear`**: Points to the index where the next element will be inserted.
* **`size`**: Tracks the current number of elements (simplifies full/empty checks).
* **Wrap-around**: When pointers hit `capacity - 1`, the modulo operation `(index + 1) % capacity` wraps them back to index `0`.

```
Initial (Capacity = 5):
[ null, null, null, null, null ]
  ▲
front = 0, rear = 0, size = 0

Enqueue 10, 20, 30:
[  10,   20,   30,  null, null ]
   ▲                 ▲
 front = 0         rear = 3, size = 3

Dequeue (removes 10), then Enqueue 40, 50, 60 (wraps around to index 0):
[  60,   20,   30,   40,   50  ]
         ▲     ▲
      front=1 rear=1, size = 5 (FULL)

```

---

### Java Implementation

```java
import java.util.NoSuchElementException;

public class ArrayQueue {
    private final int[] data;
    private final int capacity;
    private int front;
    private int rear;
    private int size;

    public ArrayQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.data = new int[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    // Insert at rear: O(1)
    public void enqueue(int element) {
        if (isFull()) {
            throw new IllegalStateException("Queue Overflow: Queue is full");
        }
        data[rear] = element;
        rear = (rear + 1) % capacity;
        size++;
    }

    // Remove from front: O(1)
    public int dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue Underflow: Queue is empty");
        }
        int value = data[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    // Inspect the front item: O(1)
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return data[front];
    }
}

```

---

### Key Edge Cases & Common Pitfalls

1. **Distinguishing Full vs. Empty Without `size**`:
* If you don't maintain a separate `size` variable, `front == rear` could mean **either** empty or full.
* To solve this without a `size` counter, allocate an array of size $N+1$ and reserve one blank slot, defining full as `(rear + 1) % capacity == front`. Tracking `size` explicitly is cleaner and avoids wasting space.


2. **Object References in Generics**:
* If storing objects (`Object[]`), set `data[front] = null` during `dequeue()` before incrementing `front` so the Garbage Collector can reclaim dead references.


3. **Resizing (Dynamic Circular Queue)**:
* To make the queue unbounded, allocate an array with double the capacity when full, then unroll the elements using `System.arraycopy`:
```java
// Unroll circular segments into the new array
System.arraycopy(data, front, newData, 0, capacity - front);
System.arraycopy(data, 0, newData, capacity - front, front);
front = 0;
rear = capacity;

```