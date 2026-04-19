Yes, this is the **optimal** way to solve this problem! You are using the **Min-Heap** pattern to maintain the $k$ largest elements.

By keeping the size of the Min-Heap capped at $k$, the "smallest" element in that heap (which sits at the top/peek) is naturally the **$k$-th largest** element of the entire stream.

### Why this is the "Right" Way
* **Space Efficiency:** You only store $k$ elements, regardless of how many millions of numbers are added to the stream ($O(k)$ space).
* **Time Efficiency:** Each `add` operation is $O(\log k)$. If you sorted the list every time, it would be $O(N \log N)$, which would be too slow for a streaming data scenario.

---

### Minor Code Clean-up
Your logic is 100% correct, but you can make it more concise. You don't actually need the `if/else` checks in the `add` method if you simply `offer` first and then `poll` if the size exceeds $k$. 

Here is a slightly "cleaner" version of your same logic:

```java
class KthLargest {
    private final PriorityQueue<Integer> pq;
    private final int k;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.pq = new PriorityQueue<>();
        for (int num : nums) {
            add(num); // Reuse the add logic to keep it DRY
        }
    }
    
    public int add(int val) {
        pq.offer(val);
        // If we have more than k elements, the smallest one can't be the kth largest
        if (pq.size() > k) {
            pq.poll();
        }
        return pq.peek();
    }
}
```

### Key Differences in the Cleaned Version:
1.  **DRY (Don't Repeat Yourself):** By calling `add(num)` inside the constructor, you ensure the logic for maintaining the heap size is only written once.
2.  **Simplified Logic:** `pq.offer(val)` followed by `if (size > k) pq.poll()` handles both cases (when the heap is under-filled and when it's full) without needing nested `if` statements.
3.  **Final Variable:** Marking `k` and `pq` as `private final` is good practice in Java to prevent accidental reassignment.

### The Visualization
To visualize why a **Min-Heap** finds the **Largest** element, imagine a room that only holds $k$ people. To find the $k$ tallest people, every time a new person walks in, the shortest person currently in the room has to leave. After everyone has entered, the shortest person **left in the room** is the $k$-th tallest overall.



Does this "Shortest person in the room" analogy help make the Min-Heap choice feel more intuitive?