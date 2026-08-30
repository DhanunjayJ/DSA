This problem can be solved using **Backtracking** very similar to standard permutations, with one extra rule added to prevent duplicate branches.

### The Core Challenge

If your input is `[1, 1, 2]` and you treat both `1`s as identical choices, a naive backtracking algorithm will try to place the *second* `1` first, and then the *first* `1` later, generating identical duplicate permutations (e.g., generating `[1, 1, 2]` twice).

### The Strategy

1. **Sort the Array:** Sorting puts identical numbers next to each other (e.g., `[1, 1, 2]`). This allows us to easily spot and skip duplicates.
2. **Use a `used` array:** Keep track of which indices are currently part of your building permutation.
3. **The Skip Condition (The Magic Rule):** When looping through elements, skip an element if:
* It has already been used (`used[i] == true`).
* **Or**, it is the same as the previous element (`nums[i] == nums[i-1]`), **and** the previous element was *not* used (`!used[i-1]`). This ensures that duplicate numbers are always processed in their strict, original relative order, avoiding duplicate branches entirely.



---

### Java Solution

```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        // Step 1: Sort the array to group duplicates together
        Arrays.sort(nums);
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), ans);
        return ans;
    }

    private void backtrack(int[] nums, boolean[] used, List<Integer> temp, List<List<Integer>> ans) {
        // Base case: if temp matches the size of nums, we found a complete permutation
        if (temp.size() == nums.length) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // If already used, skip it
            if (used[i]) continue;
            
            // Skip duplicates: if this element is the same as the previous one,
            // and the previous one was NOT used in this current branch, skip it.
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;

            // Mark as used, add to path, and recurse
            used[i] = true;
            temp.add(nums[i]);
            
            backtrack(nums, used, temp, ans);
            
            // Backtrack (clean up state)
            temp.remove(temp.size() - 1);
            used[i] = false;
        }
    }
}

```

---

### Complexity

* **Time Complexity:** $O(N!)$ in the worst case where all numbers are distinct, but heavily optimized and reduced when duplicates are present since whole subtrees are pruned.
* **Space Complexity:** $O(N)$ for the recursion stack and the `used` array.

Would you like a line-by-line dry run of how the duplicate-skipping condition handles `[1, 1, 2]`?

---

This problem can be solved using **Backtracking** very similar to standard permutations, with one extra rule added to prevent duplicate branches.

### The Core Challenge

If your input is `[1, 1, 2]` and you treat both `1`s as identical choices, a naive backtracking algorithm will try to place the *second* `1` first, and then the *first* `1` later, generating identical duplicate permutations (e.g., generating `[1, 1, 2]` twice).

### The Strategy

1. **Sort the Array:** Sorting puts identical numbers next to each other (e.g., `[1, 1, 2]`). This allows us to easily spot and skip duplicates.
2. **Use a `used` array:** Keep track of which indices are currently part of your building permutation.
3. **The Skip Condition (The Magic Rule):** When looping through elements, skip an element if:
* It has already been used (`used[i] == true`).
* **Or**, it is the same as the previous element (`nums[i] == nums[i-1]`), **and** the previous element was *not* used (`!used[i-1]`). This ensures that duplicate numbers are always processed in their strict, original relative order, avoiding duplicate branches entirely.



---

### Java Solution

```java
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        // Step 1: Sort the array to group duplicates together
        Arrays.sort(nums);
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), ans);
        return ans;
    }

    private void backtrack(int[] nums, boolean[] used, List<Integer> temp, List<List<Integer>> ans) {
        // Base case: if temp matches the size of nums, we found a complete permutation
        if (temp.size() == nums.length) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // If already used, skip it
            if (used[i]) continue;
            
            // Skip duplicates: if this element is the same as the previous one,
            // and the previous one was NOT used in this current branch, skip it.
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;

            // Mark as used, add to path, and recurse
            used[i] = true;
            temp.add(nums[i]);
            
            backtrack(nums, used, temp, ans);
            
            // Backtrack (clean up state)
            temp.remove(temp.size() - 1);
            used[i] = false;
        }
    }
}

```

---

### Complexity

* **Time Complexity:** $O(N!)$ in the worst case where all numbers are distinct, but heavily optimized and reduced when duplicates are present since whole subtrees are pruned.
* **Space Complexity:** $O(N)$ for the recursion stack and the `used` array.

Would you like a line-by-line dry run of how the duplicate-skipping condition handles `[1, 1, 2]`?