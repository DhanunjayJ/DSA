It is completely normal to second-guess this when you are deep in coding problems. Mixing these two up is one of the most common ways to get a frustrating off-by-one error or an incorrect count.

The short answer: You use **`ep - sp + 1`** when you are looking at a *specific, fixed window* that you already know is valid. You use **`ep * sp`** (or a variation of it) when you are treating the start and end points as *independent choices* across a larger array.

Here is a simple breakdown to help you instinctively choose the right one next time.

---

## 1. Use `ep - sp + 1` for Contiguous Windows

**The Rule:** Use this when you have a **single, specific valid subarray** from index `sp` to index `ep`, and you want to count how many *new* or *total* valid subarrays end exactly at `ep`.

### Why it works:

If your current window from `sp` to `ep` is valid, then **every single subarray that starts anywhere between `sp` and `ep` and ends exactly at `ep` is also valid.**

Let's say your window is from index 1 to index 3 (`sp = 1`, `ep = 3`).

* The length of this window is $3 - 1 + 1 = 3$.
* The 3 subarrays ending at `ep` are:
1. `[ep]` (starts at index 3)
2. `[ep-1, ep]` (starts at index 2)
3. `[sp, ..., ep]` (starts at index 1)



**Common Patterns:** * Sliding Window / Two Pointers (e.g., "Find the number of subarrays where the sum is less than $K$"). Every time you expand `ep` and shrink `sp` until the window is valid, you add `ep - sp + 1` to your total.

---

## 2. Use Combinations (`ep * sp` style) for Independent Choices

**The Rule:** Use this when you are treating `sp` and `ep` as **independent decision points** to form subarrays across the entire array, usually when a specific "turning point" or "element of interest" sits between them.

Instead of looking at a single valid block, you are asking: *"How many ways can I pick a starting point on the left, and how many ways can I pick an ending point on the right?"*

### Why it works:

By the fundamental counting principle, if you have $X$ choices for where a subarray can start, and $Y$ choices for where it can end, the total number of subarrays you can form is:


$$\text{Total Subarrays} = X \times Y$$

### A Classic Example:

Suppose you need to count how many subarrays contain a specific target element at index `i`.

* To include index `i`, your subarray must start at or before `i`, and end at or after `i`.
* **Number of choices for the start point (`sp`):** `i - 0 + 1` (all indices from 0 up to `i`).
* **Number of choices for the end point (`ep`):** `n - 1 - i + 1` (all indices from `i` up to the end of the array).
* **Total subarrays containing `i`:** `(i + 1) * (n - i)`.

**Common Patterns:**

* Contribution technique (e.g., "Sum of all subarray minimums" or "Count subarrays with an odd number of elements"). You find an element that satisfies a condition, count how many valid starting points are to its left ($X$), how many valid ending points are to its right ($Y$), and multiply them.

---

## Quick Decision Matrix

| If you are asking... | Formula Type | Mental Model |
| --- | --- | --- |
| "How many valid subarrays **end exactly at my current right pointer**?" | `ep - sp + 1` | **Expansion:** Counting shrinking variations of the current window. |
| "How many ways can I **pair a starting chunk with an ending chunk**?" | `left_choices * right_choices` | **Combination:** Multiplying independent options on the left and right. |

Next time you are stuck, just ask yourself: *Am I counting variants of a single window I'm looking at right now, or am I combining independent left and right choices?*