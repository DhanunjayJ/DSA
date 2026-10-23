The **Hash Map approach** is the most intuitive and widely accepted optimal solution for the Palindrome Pairs problem. It reduces the time complexity from a sluggish $O(N^2 \cdot K)$ brute-force check to a fast $O(N \cdot K^2)$ by cleverly breaking down how palindromes are formed through concatenation.

Here is a detailed, step-by-step breakdown of how this approach works.

---

### 1. The Core Intuition: The Split Technique

Suppose we have two words, `word1` and `word2`, and their concatenation `word1 + word2` forms a palindrome.

Instead of checking every possible pair, we can look at every single `word` in our array and imagine splitting it into two parts: a **prefix ($L$)** and a **suffix ($R$)** such that `word = L + R`.

When we append a second word (`word2`) to `word`, the combined string looks like:


$$\text{word} + \text{word2} = L + R + \text{word2}$$

For this combined string to be a valid palindrome, **one of two scenarios** must happen:

#### Scenario A: The Suffix ($R$) is a Palindrome

If the right part $R$ of our current word is already a palindrome, then for the entire string to be a palindrome, the left part $L$ must match the reverse of `word2`.

* In other words: `word2 = reverse(L)`.
* If `reverse(L)` exists in our word list, then `word + word2` will form a palindrome!

#### Scenario B: The Prefix ($L$) is a Palindrome

If the left part $L$ of our current word is a palindrome, then for the entire string to be a palindrome, the right part $R$ must match the reverse of `word2`.

* In other words: `word2 = reverse(R)`.
* If `reverse(R)` exists in our word list, then `word2 + word` will form a palindrome! *(Notice that `word2` comes first here, so the pair order is reversed).*

---

### 2. Step-by-Step Algorithm Execution

1. **Pre-populate a Hash Map**:
Store every word in a Hash Map where the **key** is the word and the **value** is its original index in the array. This allows $O(1)$ lookups.
* Example: For `["bat", "tab", "cat"]`, our map looks like `{"bat": 0, "tab": 1, "cat": 2}`.


2. **Iterate Through Every Word**:
For each word at index `i`, generate **all possible split points** from index $0$ to $n$ (where $n$ is the length of the word). This splits the word into `left` ($L$) and `right` ($R$).
3. **Check Scenario A ($L$ is a palindrome)**:
* Check if `left` is a palindrome.
* If it is, find the reverse of `right` (`rev_right`).
* Check if `rev_right` exists in our Hash Map. If it does, and it's not the exact same word index (`word_map.get(rev_right) != i`), we have found a valid palindrome pair: `[index of rev_right, i]`.


4. **Check Scenario B ($R$ is a palindrome)**:
* Check if `right` is a palindrome.
* If it is, find the reverse of `left` (`rev_left`).
* Check if `rev_left` exists in our Hash Map. If it does, and it's not the same word index, we have found a valid palindrome pair: `[i, index of rev_left]`. *(Note: We restrict this check slightly or handle empty strings carefully to avoid duplicate pairs when the split point is at the very beginning or end).*



---

### 3. Concrete Walkthrough with Example

Let's trace `words = ["bat", "tab"]`:

* **Map**: `{"bat": 0, "tab": 1}`
* **For `word = "bat"` (index 0)**:
* Split at `j = 0`: `left = ""`, `right = "bat"`
* Is `left` ("") a palindrome? Yes. `rev_right` = `"tab"`. Is `"tab"` in map? Yes (index 1). $\rightarrow$ Add pair `[1, 0]` (`"tab" + "bat"` = `"tabbat"`).


* Split at `j = 1`: `left = "b"`, `right = "at"` -> Neither condition met.
* Split at `j = 2`: `left = "ba"`, `right = "t"` -> Neither condition met.
* Split at `j = 3`: `left = "bat"`, `right = ""`
* Is `right` ("") a palindrome? Yes. `rev_left` = `"tab"`. Is `"tab"` in map? Yes (index 1). $\rightarrow$ Add pair `[0, 1]` (`"bat" + "tab"` = `"battab"`).





Result: `[[1, 0], [0, 1]]`.

---

### Why is this efficient?

* **Time Complexity**: For each of the $N$ words of maximum length $K$, we test $K$ split points. Checking if a substring is a palindrome takes up to $O(K)$ time, and reversing/looking up takes $O(K)$. Thus, the total time complexity is $O(N \cdot K^2)$, which is extremely fast and easily passes LeetCode constraints ($N \le 5000, K \le 300$).
* **Space Complexity**: $O(N \cdot K)$ to store the strings in the hash map.

---

To solve the **Palindrome Pairs** problem in Java, we need to carefully consider the constraints ($N \le 5000$ words, $K \le 300$ maximum length). A naive approach will time out, so we need optimized strategies.

Below is a detailed breakdown of **three possible approaches**, ranging from the brute force method to optimal data-structure solutions, complete with Java implementations.

---

### Approach 1: Brute Force (Time Limit Exceeded)

#### Concept

The most straightforward approach is to check every possible pair of words $(i, j)$ where $i \neq j$. For each pair, we concatenate `words[i] + words[j]` and check whether the resulting string is a palindrome.

#### Complexity

* **Time Complexity:** $O(N^2 \cdot K)$ where $N$ is the number of words and $K$ is the maximum length of a word. With $N = 5000$, $N^2 = 25,000,000$, which easily exceeds the time limit.
* **Space Complexity:** $O(K)$ to store the concatenated string.

---

### Approach 2: Optimized Hash Map Approach (Recommended)

#### Concept

Instead of testing all pairs, we can split each word into two parts: a prefix $L$ and a suffix $R$ (`word = L + R`).
When we concatenate `words[i] + words[j]` to form a palindrome, one of two scenarios occurs:

1. **$L$ is a palindrome**, and the reverse of $R$ (`reverse(R)`) exists elsewhere in our array. In this case, the pair is `[reverse(R), i]`.
2. **$R$ is a palindrome**, and the reverse of $L$ (`reverse(L)`) exists elsewhere in our array. In this case, the pair is `[i, reverse(L)]`.

We store all words in a Hash Map for $O(1)$ lookups and iterate through every possible split point for every word.

#### Java Implementation

```java
import java.util.*;

class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        if (words == null || words.length < 2) return result;
        
        // Map each word to its original index
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int n = word.length();
            
            // Try all possible split points into L and R
            for (int j = 0; j <= n; j++) {
                String left = word.substring(0, j);
                String right = word.substring(j);
                
                // Case 1: left is palindrome, check if reverse(right) exists in map
                if (isPalindrome(left)) {
                    String revRight = new StringBuilder(right).reverse().toString();
                    if (map.containsKey(revRight) && map.get(revRight) != i) {
                        result.add(Arrays.asList(map.get(revRight), i));
                    }
                }
                
                // Case 2: right is palindrome, check if reverse(left) exists in map
                // (j < n prevents duplicate processing when left is empty and right is full word)
                if (j < n && isPalindrome(right)) {
                    String revLeft = new StringBuilder(left).reverse().toString();
                    if (map.containsKey(revLeft) && map.get(revLeft) != i) {
                        result.add(Arrays.asList(i, map.get(revLeft)));
                    }
                }
            }
        }
        
        return result;
    }
    
    private boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
}

```

* **Time Complexity:** $O(N \cdot K^2)$, where $N$ is the number of words and $K$ is the max length. Checking substrings and reversals takes proportional to length, running well within time limits.
* **Space Complexity:** $O(N \cdot K)$ to store the hash map.

---

### Approach 3: Trie (Prefix Tree) Approach (Most Advanced)

#### Concept

We can store the **reverse** of each word in a Trie. Each node in the Trie can also track:

1. The index of a word that ends at this node.
2. A list of indices of words whose remaining suffix is a palindrome.

As we traverse the Trie using a word from our array, we can check matching branches to instantly identify valid palindrome pairs without checking every single substring permutation.

#### Java Trie Implementation Structure

```java
class Solution {
    class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int index = -1;
        List<Integer> palindromeIndexes = new ArrayList<>();
    }

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        TrieNode root = new TrieNode();

        // 1. Build the Trie with reversed words
        for (int i = 0; i < words.length; i++) {
            insert(root, words[i], i);
        }

        // 2. Search for matches using the Trie
        for (int i = 0; i < words.length; i++) {
            search(words[i], i, root, result);
        }

        return result;
    }

    private void insert(TrieNode root, String word, int index) {
        TrieNode curr = root;
        for (int i = word.length() - 1; i >= 0; i--) {
            int c = word.charAt(i) - 'a';
            if (curr.children[c] == null) {
                curr.children[c] = new TrieNode();
            }
            if (isPalindrome(word, 0, i)) {
                curr.palindromeIndexes.add(index);
            }
            curr = curr.children[c];
        }
        curr.index = index;
        if (isPalindrome(word, 0, word.length() - 1)) {
            curr.palindromeIndexes.add(index);
        }
    }

    private void search(String word, int i, TrieNode root, List<List<Integer>> result) {
        TrieNode curr = root;
        for (int j = 0; j < word.length(); j++) {
            // If the remaining part of the word is a palindrome and current node has a valid word index
            if (curr.index != -1 && curr.index != i && isPalindrome(word, j, word.length() - 1)) {
                result.add(Arrays.asList(i, curr.index));
            }
            int c = word.charAt(j) - 'a';
            curr = curr.children[c];
            if (curr == null) return;
        }

        // Check if the current node terminates a valid word
        if (curr.index != -1 && curr.index != i) {
            result.add(Arrays.asList(i, curr.index));
        }

        // Check remaining palindrome indexes stored at this node
        for (int j : curr.palindromeIndexes) {
            if (j != i) {
                result.add(Arrays.asList(i, j));
            }
        }
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) return false;
        }
        return true;
    }
}

```

* **Time Complexity:** $O(N \cdot K^2)$ to build and search the Trie.
* **Space Complexity:** $O(N \cdot K)$ for node allocations.

---

### Comparison Summary

| Approach | Time Complexity | Space Complexity | Practical Ease |
| --- | --- | --- | --- |
| **Brute Force** | $O(N^2 \cdot K)$ | $O(K)$ | TLE on LeetCode |
| **Hash Map** | $O(N \cdot K^2)$ | $O(N \cdot K)$ | Clean, concise, preferred for interviews |
| **Trie** | $O(N \cdot K^2)$ | $O(N \cdot K)$ | Highly optimized, complex implementation |


---

The **Trie (Prefix Tree) approach** is an advanced and highly optimized way to solve the Palindrome Pairs problem. While the Hash Map approach checks every possible split point for every word, the Trie approach allows us to find matching pairs dynamically as we traverse a tree structure, significantly cutting down redundant substring checks.

Here is a detailed, step-by-step breakdown of how the Trie approach works.

---

### 1. The Core Idea: Reversing Words and Prefix Matching

When we want to check if `word1 + word2` is a palindrome, we are looking for a situation where one word is the reverse of another, or where parts of one word match the reverse of another with a leftover palindrome piece in the middle.

To make this efficient, we can **reverse every word** before inserting it into a Trie. Because the words are reversed, searching for a prefix in the Trie corresponds to searching for a suffix in the original word.

---

### 2. The Trie Node Structure

Each node in our Trie needs to hold a bit more information than a standard dictionary Trie. Specifically, a `TrieNode` contains:

1. **`children`**: An array of size 26 (for lowercase English letters `a-z`) pointing to child nodes.
2. **`index`**: An integer storing the original index of the word if a complete word ends at this node (defaults to `-1` if no word ends here).
3. **`palindromeIndexes`**: A list of integers that store the indices of words in our original array whose **remaining suffix** is a palindrome. This is the secret sauce that handles words of unequal lengths!

---

### 3. Phase 1: Building the Trie

When we insert a word into the Trie, we do it in **reverse order** (from the last character of the word to the first). As we traverse down the tree, we check for palindromes:

* **Step-by-step insertion**:
1. For a given `word` at index `i`, loop backwards through its characters.
2. At each character, check if the prefix of the word (from index `0` up to the current character's position) forms a **palindrome**.
3. If it is a palindrome, add the current word's index `i` to the current Trie node's `palindromeIndexes` list.
4. Move to the next child node (creating it if it doesn't exist).
5. At the final node of the word, set its `index = i`, and also check if the entire word is a palindrome to update its `palindromeIndexes` if necessary.



---

### 4. Phase 2: Searching the Trie

For every word in our array, we traverse the Trie from the root to find matching palindrome pairs. During this search, we look for three distinct matching scenarios:

#### Scenario 1: The Search Word is Longer than the Trie Branch

If we finish traversing characters of our search word `word`, but we land on a Trie node that represents a valid completed word (`node.index != -1` and `node.index != i`), then `word` is longer than the Trie word, and the remaining part of `word` matches the Trie word. If that remaining part is a palindrome, we have a match!

#### Scenario 2: The Trie Branch is Longer than the Search Word

If we run out of characters in our search word while traversing the Trie, we look at the **`palindromeIndexes`** stored at our current Trie node. Any word index stored in that list means the remaining part of the Trie word is a palindrome, making it a valid pair.

#### Scenario 3: Exact Match

If our search word ends exactly at a Trie node that marks the end of another word (`node.index != -1`), they are exact reverses of each other, forming a palindrome.

---

### 5. Pros and Cons of the Trie Approach

* **Pros**:
* Extremely elegant for prefix/suffix matching problems.
* Avoids explicit string slicing and substring creation for every single split point, which can reduce overhead in lower-level languages.


* **Cons**:
* Much harder to implement and debug compared to the Hash Map approach due to the custom node structure and multiple edge cases (`palindromeIndexes`).
* Time and space complexities remain asymptotically similar ($O(N \cdot K^2)$ time and $O(N \cdot K)$ space), but the constant factor overhead for building tree nodes can sometimes make it slower in practice in languages like Python or Java compared to a well-optimized Hash Map.

---
That specific part—**`palindromeIndexes`**—is definitely the trickiest concept in the Trie approach. Let's break it down with a concrete example so it clicks completely.

---

### The Problem It Solves

Imagine you have two words of **different lengths**:

* `word1 = "ll"` (length 2)
* `word2 = "sssll"` (length 5)

If you combine them as `word1 + word2` (`"ll" + "sssll"`), you get `"llsssll"`, which is a palindrome!

Notice what is happening here:

1. `word1` matches the *end* (suffix) of `word2`.
2. The *remaining part* of `word2` (`"sss"`) is sitting in the middle, and it **happens to be a palindrome on its own**.

When we are searching the Trie using `word1`, we will run out of characters in our search word before we finish traversing down the Trie branch for `word2` (because `word2` is longer). How do we know if the extra characters left over in the Trie are allowed?

That's where **`palindromeIndexes`** comes in.

---

### What `palindromeIndexes` Actually Stores

As we are **building** the Trie and inserting words in reverse, every single node in our tree asks a question:

> *"As I look at the characters of this word from the beginning up to this point, is the remaining suffix a palindrome?"*

If the answer is yes, that node stores the word's index in its `palindromeIndexes` list.

#### Let's Trace It:

Take the word `"sssll"` (stored in reverse in the Trie as `l -> l -> s -> s -> s`).

1. As we build/traverse it, we look at different prefix/suffix cuts.
2. At the node where we finish processing `"ll"`, we check the remaining part of the original word (`"sss"`).
3. Is `"sss"` a palindrome? **Yes!**
4. Therefore, the node representing `"ll"` will store the index of `"sssll"` in its `palindromeIndexes` list.

---

### How It Works During the Search

Later, when we search the Trie using our short word `word1 = "ll"`:

1. We walk down the Trie for `l` then `l`.
2. We run out of characters in `"ll"`, but we are currently sitting at the Trie node for `"ll"`.
3. We check that node's **`palindromeIndexes`** list.
4. We see the index for `"sssll"` sitting right there!
5. We instantly know: *"Ah, because the leftover part (`"sss"`) is a palindrome, combining `word1` with `sssll` will form a valid palindrome."*

Without `palindromeIndexes`, our search algorithm would prematurely stop or fail when encountering words of unequal lengths because the characters wouldn't match 1:1. It acts as a shortcut flag saying: *"Everything remaining down this branch is a valid palindrome wrapper, so you have a match!"*

---

Optimizing the Hash Map approach involves tackling its primary performance bottleneck: **repeatedly checking if substrings are palindromes**.

In the standard hash map implementation, for every word of length $K$, we test $K$ split points. At each split point, we check whether a substring is a palindrome, which takes $O(K)$ time. Doing this across all split points creates $O(K^2)$ work per word.

We can optimize this by **precomputing palindrome states** using Dynamic Programming (DP) for each word. This reduces individual palindrome verification from $O(K)$ to an instant $O(1)$ lookup.

---

### The Precomputation Strategy (2D DP Table)

For any given word of length $K$, we can build a 2D boolean table `boolean[][] dp` of size $K \times K$, where `dp[i][j]` is `true` if the substring from index `i` to `j` is a palindrome, and `false` otherwise.

#### How the DP Table Works:

1. **Base Cases**:
* A single character (`i == j`) is always a palindrome (`dp[i][i] = true`).
* Two adjacent identical characters (`j == i + 1` and `word[i] == word[j]`) form a palindrome.


2. **Inductive Step**:
* A substring from `i` to `j` is a palindrome if `word.charAt(i) == word.charAt(j)` **and** the inner substring `dp[i + 1][j - 1]` is also a palindrome.



Once this table is built in $O(K^2)$ time *once* per word, we can answer any substring palindrome query in **$O(1)$ time** during our split loop.

---

### Optimized Java Implementation

Here is how you integrate the precomputed DP table into the Hash Map approach:

```java
import java.util.*;

class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        if (words == null || words.length < 2) return result;
        
        // Map each word to its original index
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int n = word.length();
            
            // Precompute the palindrome table for this word
            boolean[][] dp = getPalindromeTable(word);
            
            // Try all possible split points into left and right parts
            for (int j = 0; j <= n; j++) {
                
                // Case 1: Left part is a palindrome (j == 0 means empty left part)
                if (j == 0 || dp[0][j - 1]) {
                    String revRight = new StringBuilder(word.substring(j)).reverse().toString();
                    if (map.containsKey(revRight) && map.get(revRight) != i) {
                        result.add(Arrays.asList(map.get(revRight), i));
                    }
                }
                
                // Case 2: Right part is a palindrome (j == n means empty right part)
                if (j == n || dp[j][n - 1]) {
                    String revLeft = new StringBuilder(word.substring(0, j)).reverse().toString();
                    if (map.containsKey(revLeft) && map.get(revLeft) != i) {
                        result.add(Arrays.asList(i, map.get(revLeft)));
                    }
                }
            }
        }
        
        return result;
    }
    
    // Helper function to build the O(K^2) palindrome lookup table
    private boolean[][] getPalindromeTable(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }
        return dp;
    }
}

```

---

### Benefits of this Optimization

* **Eliminates Redundancy**: Instead of re-scanning characters or repeatedly calling a palindrome checker function for every split, the algorithm checks pre-calculated boolean flags in constant time.
* **Cleaner Split Logic**: Checking bounds like `j == 0` or `dp[0][j - 1]` makes handling empty strings and edge boundaries explicit and clean.

--

This high-performance solution is a clever twist on the standard Hash Map approach. Instead of checking every possible split length from $0$ to $K$, it **only checks lengths that actually exist in your input array**.

Here is a step-by-step breakdown of how this optimized algorithm works under the hood.

---

### Phase 1: Preparation

```java
HashMap<String, Integer> wordMap = new HashMap<>();
Set<Integer> set = new TreeSet<>();
int n = words.length;

for(int i = 0; i < n; i++) {
    wordMap.put(words[i], i);
    set.add(words[i].length()); // Store unique lengths in a sorted set
}

```

1. **`wordMap`**: Maps every word to its index for $O(1)$ lookups.
2. **`TreeSet` (`set`)**: Stores all **unique word lengths** present in your array and keeps them automatically sorted in ascending order (e.g., if you have words of lengths `[3, 1, 5, 3]`, the set stores `[1, 3, 5]`). This is the key to skipping unnecessary work.

---

### Phase 2: Processing Each Word

For every word `words[i]`, the algorithm does two main things:

#### Step A: Check if the exact reverse exists

```java
String reverse = new StringBuilder(words[i]).reverse().toString();

if (wordMap.containsKey(reverse) && wordMap.get(reverse) != i) {
    ans.add(Arrays.asList(i, wordMap.get(reverse)));
}

```

* It immediately checks if the exact reverse of the current word exists in the map (e.g., if the word is `"abc"`, it checks if `"cba"` is in the map). If it is, they combine to form a palindrome (`"abccba"`), so it records the pair.

#### Step B: Check splits using *only* existing word lengths (`TreeSet`)

Instead of checking every possible integer split from $0$ to `length`, it loops through the unique lengths $k$ stored in the `TreeSet`:

```java
for (Integer k : set) {
    if (k == length) break; // Stop if the split length is equal to or greater than the word
    
    // 1. Check if the remaining part on the left is a palindrome
    if (isPalindrome(reverse, 0, length - 1 - k)) {
        String s1 = reverse.substring(length - k); // Extract the matching suffix
        if (wordMap.containsKey(s1)) {
            ans.add(Arrays.asList(i, wordMap.get(s1)));
        }
    }
    
    // 2. Check if the remaining part on the right is a palindrome
    if (isPalindrome(reverse, k, length - 1)) {
        String s2 = reverse.substring(0, k); // Extract the matching prefix
        if (wordMap.containsKey(s2)) {
            ans.add(Arrays.asList(wordMap.get(s2), i));
        }
    }
}

```

---

### Why is this so much faster?

1. **Skips non-existent lengths**: If your array contains words of lengths `2` and `300`, a standard loop would check all 301 split points. This optimized code looks at the `TreeSet`, sees that only lengths `2` and `300` exist, and **only checks those two split sizes**.
2. **`if (k == length) break;`**: Because the `TreeSet` is sorted, the moment $k$ matches or exceeds the current word's length, it stops the loop entirely. You can't match a word using a split size larger than or equal to the word itself.
3. **Pre-reversed string optimization**: By reversing the word *once* at the beginning (`String reverse`), checking prefixes and suffixes becomes a simple substring operation on the reversed string, aligning mathematically with how words match during concatenation.
---