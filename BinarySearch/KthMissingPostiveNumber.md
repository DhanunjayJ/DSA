The intuition comes from shifting your perspective from **"skipping missing numbers"** to **"how many existing numbers pushed our target forward."**

---

### The Shift Perspective

Imagine there were **no numbers in the array at all**.

* The $k$-th positive integer would simply be **$k$**.

Now, introduce the numbers from the array one by one:

* Every time a number in `arr` is **smaller than or equal to** the target missing number, it "steals" a spot in the sequence of positive integers.
* Because that spot is taken, our desired missing number gets pushed forward by **$+1$**.

Therefore:


$$\text{Target} = k + (\text{count of array elements that appear before it})$$

---

### What `low` Actually Represents

When your binary search finishes:

* `arr[mid] - (mid + 1) < k` moved `low = mid + 1`.
* `arr[mid] - (mid + 1) >= k` moved `high = mid - 1`.

Because of how the search space narrows, binary search stops when `low` lands precisely at the **number of elements in `arr` that are smaller than the $k$-th missing number**.

* There are exactly `low` elements in `arr` (from index `0` to `low - 1`) that appear before the $k$-th missing number.
* Each of those `low` elements pushes our target value forward by $1$.

Starting from the baseline $k$:


$$\text{Target} = k + \text{low}$$

---

### A Concrete Walkthrough

Take `arr = [2, 3, 4, 7, 11]`, `k = 5`.

1. **If `arr` were completely empty:**
* The 5th missing number is simply **$5$**.


2. **Observe the actual numbers:**
* Missing numbers sequence: `1, 5, 6, 8, 9, ...`
* The 5th missing number is **$9$**.


3. **Count how many array elements are $< 9$:**
* Elements less than $9$: `[2, 3, 4, 7]` $\rightarrow$ exactly **$4$ elements**.


4. **Binary search result:**
* At index `3` (`arr[3] = 7`): missing count is $7 - 4 = 3 < 5$, so `low` moves to `4`.
* At index `4` (`arr[4] = 11`): missing count is $11 - 5 = 6 \ge 5$, so `high` moves to `3`.
* Loop ends with **`low = 4`**.



Since exactly **$4$ numbers** from the array sit before the 5th missing number, they shift the baseline $5$ forward by $4$:


$$\text{Result} = 5 + 4 = 9$$

You never actually need to project forward from `arr[high]`; `low` directly counts the number of roadblocks that bumped your target downstream.

----

Let’s throw away the binary search code for a second and just look at a simple visual line of numbers.

---

### The Queue Analogy

Imagine people with jersey numbers standing in an infinite queue:
`1, 2, 3, 4, 5, 6, 7, 8, 9, 10, ...`

You are told: **"Find the 5th person who is NOT in `arr = [2, 3, 4, 7, 11]`."**

If **nobody** was in `arr` (nobody was skipped):

* The 5th missing person is just jersey **#5**.

Now, walk along the queue from left to right:

1. You look at person **1**: Not in `arr`. That's your 1st missing person.
2. You look at person **2**: In `arr`! Skip them.
3. You look at person **3**: In `arr`! Skip them.
4. You look at person **4**: In `arr`! Skip them.

Notice what happened here:
Because **2, 3, and 4** were skipped, you couldn't stop at jersey **5**. Those 3 people pushed you **3 steps further down the line**.

5. Person **5**: Not in `arr` (2nd missing).
6. Person **6**: Not in `arr` (3rd missing).
7. Person **7**: In `arr`! Skip them.

Skipping **7** pushes you **1 more step** further down the line.

8. Person **8**: Not in `arr` (4th missing).
9. Person **9**: Not in `arr` (5th missing). **Found it!**

---

### The Big Realization

How did we reach jersey **9**?

$$\text{Final Jersey} = 5 \text{ (desired missing count)} + 4 \text{ (number of skips)}$$

Every time you encounter a number that is in `arr` before your target, your target number gets shifted by $+1$.

So the formula in real life is always:


$$\text{Answer} = k + (\text{how many elements of arr are smaller than the answer})$$

---

### Where does `low` come in?

When the binary search finishes:

* `arr[0 ... low-1]` are all **smaller** than the answer.
* `arr[low ... end]` are all **larger** than the answer.

How many elements are in `arr[0 ... low-1]`?
There are exactly **`low`** elements!

So:

* Skips made = **`low`**
* Answer = $k + \text{skips} = k + \text{low}$.