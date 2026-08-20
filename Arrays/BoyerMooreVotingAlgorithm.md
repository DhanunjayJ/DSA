The **Boyer-Moore Majority Voting Algorithm** is an optimal algorithm designed to find the **majority element** in a sequence—an element that appears strictly more than $\lfloor n/2 \rfloor$ times.

It processes the array in linear time ($O(n)$) while using constant extra space ($O(1)$).

---

### Core Intuition: The "Cancellation" Principle

If you pair up every occurrence of the majority element with a different element, the majority element will still have at least one uncancelled instance left over because it occupies more than half of the total slots.

---

### How It Works

The algorithm consists of two passes (Pass 2 is only required if a majority element is not guaranteed to exist):

**Pass 1: Find Candidate**

1. Maintain two variables: `candidate` and `count = 0`.
2. Iterate through each element $x$ in the array:
* If `count == 0`, set `candidate = x` and `count = 1`.
* Else if $x == \text{candidate}$, increment `count += 1`.
* Else, decrement `count -= 1`.



**Pass 2: Verification (Optional)**

* Traverse the array once more to count the exact occurrences of `candidate`.
* If $\text{occurrences} > \lfloor n/2 \rfloor$, the candidate is the majority element; otherwise, no majority element exists.

---

### Python Implementation

```python
def find_majority_element(nums: list[int]) -> int | None:
    # Phase 1: Identify candidate
    candidate = None
    count = 0
    for num in nums:
        if count == 0:
            candidate = num
            count = 1
        elif num == candidate:
            count += 1
        else:
            count -= 1

    # Phase 2: Verify candidate
    if nums.count(candidate) > len(nums) // 2:
        return candidate
    return None

```

---

### Complexity Analysis

* **Time Complexity:** $O(n)$ — at most two passes over an array of size $n$.
* **Space Complexity:** $O(1)$ — only tracking two variables (`candidate` and `count`).

---

visiualization - https://share.gemini.google/h022gSn9SEvo