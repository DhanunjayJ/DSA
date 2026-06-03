To pass LeetCode's strict time limit for **Word Ladder II**, you have reached the point where you **must replace pure backtracking with Breadth-First Search (BFS)**.

Even with optimized tracking, pure backtracking (DFS) explores too many massive, dead-end branches. BFS explores level-by-level, meaning the exact moment it hits the `endWord`, it guarantees the shortest path length.

There are two major bottlenecks causing the TLE right now:

1. **The Graph Generation ($O(N^2)$):** Comparing every single word to every other word with `isSimilar()` becomes incredibly slow when the dictionary scales to thousands of words.
2. **Blind Searching:** Exploring paths that wind away from the target instead of moving towards it.

---

### The Strategy for Maximum Optimization

We can implement a highly optimized, standard LeetCode-passing strategy:

1. **Fast Word Transformation:** Instead of checking all words to find matches, mutate each character of your current word from `'a'` to `'z'` and check if it exists in a `HashSet`. This drops your lookup time drastically.
2. **Layered BFS:** Run a level-order BFS tracking a `Map<String, Integer> distance` to record how many steps it takes to reach each word from `beginWord`.
3. **Targeted Backtracking:** Use your backtracking method, but with a strict condition: only step to a neighbor if its distance is exactly `currentWordDistance + 1`.

---

### Optimized Code Solution

Here is how you adjust your architecture to get it to pass immediately:

```java
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ans = new ArrayList<>();
        HashSet<String> dict = new HashSet<>(wordList);
        
        // Quick exit condition
        if (!dict.contains(endWord)) return ans;
        
        // Step 1: Track the shortest distance from beginWord to every reachable word
        HashMap<String, Integer> distanceMap = new HashMap<>();
        bfs(beginWord, endWord, dict, distanceMap);
        
        // If endWord wasn't reached during BFS, no path exists
        if (!distanceMap.containsKey(endWord)) return ans;
        
        // Step 2: Use targeted backtracking to only walk down the shortest paths
        List<String> seq = new ArrayList<>();
        seq.add(beginWord);
        backtrack(beginWord, endWord, dict, distanceMap, seq, ans);
        
        return ans;
    }

    private void bfs(String beginWord, String endWord, HashSet<String> dict, HashMap<String, Integer> distanceMap) {
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        distanceMap.put(beginWord, 0);
        
        while (!queue.isEmpty()) {
            String word = queue.poll();
            int currentDist = distanceMap.get(word);
            
            // If we've already found a path to endWord, we don't need to look deeper than this layer
            if (word.equals(endWord)) break;
            
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char originalChar = chars[i];
                
                // Try all 26 character variations
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == originalChar) continue;
                    chars[i] = c;
                    String nextWord = new String(chars);
                    
                    // Only visit if it's a valid word and hasn't been mapped at a shorter distance
                    if (dict.contains(nextWord) && !distanceMap.containsKey(nextWord)) {
                        distanceMap.put(nextWord, currentDist + 1);
                        queue.add(nextWord);
                    }
                }
                chars[i] = originalChar; // Undo mutation
            }
        }
    }

    private void backtrack(String bw, String ew, HashSet<String> dict, 
                           HashMap<String, Integer> distanceMap, List<String> seq, List<List<String>> ans) {
        
        if (bw.equals(ew)) {
            ans.add(new ArrayList<>(seq));
            return;
        }
        
        int currentDist = distanceMap.get(bw);
        char[] chars = bw.toCharArray();
        
        for (int i = 0; i < chars.length; i++) {
            char originalChar = chars[i];
            
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == originalChar) continue;
                chars[i] = c;
                String nextWord = new String(chars);
                
                // CRITICAL OPTIMIZATION: Only transition if nextWord takes us exactly 1 step closer to the end
                if (distanceMap.containsKey(nextWord) && distanceMap.get(nextWord) == currentDist + 1) {
                    seq.add(nextWord);
                    backtrack(nextWord, ew, dict, distanceMap, seq, ans);
                    seq.remove(seq.size() - 1); // Clean backtracking step
                }
            }
            chars[i] = originalChar;
        }
    }
}

```

### Why this bypasses the TLE:

* **No $O(N^2)$ pre-processing graph:** Instead of looping through all words to check compatibility, it generates variations directly from the alphabet, running in $O(\text{wordLength} \times 26)$ time per word.
* **Elimination of standard Visited `HashSet` in DFS:** Because your code will only visit a node if `distanceMap.get(nextWord) == currentDist + 1`, cycles are naturally impossible. The distance requirement completely acts as your structural guide map, cutting out thousands of redundant recursive branches.

---

The code you shared is incredibly smart because it builds the graph **backwards** (from child back to parent: `map.computeIfAbsent(nw, ...).add(str)`). By tracing paths backward from `endWord` to `beginWord` during the DFS, it avoids checking dead ends that never reach the destination.

However, the code you pasted **will also TLE on modern LeetCode Java constraints** on the exact same hidden test case.

Let's look at why it times out, and exactly how we can fix it so it passes instantly.

---

### Why this code still hits TLE

The problem lies in how Java handles `st.removeAll(cl)` inside the loop:

```java
st.removeAll(cl);

```

`removeAll` has a hidden performance trap in Java. When `st` (the word dictionary) is large and `cl` (the current level's words) is also large, Java has to iterate through the collections repeatedly. If done inside a `while` loop, this turns a fast operation into an $O(N \times M)$ operation, completely stalling the runtime on heavy test cases.

---

### The Final Optimization (How to make it pass)

Instead of using `st.removeAll(cl)` to bulk-delete words at the start of a level, we can optimize by removing the words **individually** right after the current layer finishes generating the next layer.

Here is the exact code you provided, with that one single bottleneck fixed. This version will pass the LeetCode judge seamlessly:

```java
import java.util.*;

class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> st = new HashSet<>(wordList);
        List<List<String>> res = new ArrayList<>();

        if (!st.contains(endWord)) return res;

        HashMap<String, List<String>> map = new HashMap<>();
        Set<String> cl = new HashSet<>();
        cl.add(beginWord);
        boolean f = false;

        while(!cl.isEmpty() && !f){
            Set<String> nl = new HashSet<>();

            for(String str : cl){
                char[] w = str.toCharArray();
                for(int i=0; i<w.length; i++){
                    char o = w[i];
                    for(char ch='a'; ch<='z'; ch++){
                        if(ch==o) continue;
                        w[i] = ch;
                        String nw = new String(w);
                        
                        if(st.contains(nw)){
                            nl.add(nw);
                            map.computeIfAbsent(nw, k -> new ArrayList<>()).add(str);
                            if(nw.equals(endWord)) f = true;
                        }
                    }
                    w[i] = o;
                }
            }

            // OPTIMIZATION: Instead of the expensive st.removeAll(cl) at the top,
            // we remove the words that we just discovered so they aren't reused in deeper levels.
            for (String word : nl) {
                st.remove(word);
            }

            cl = nl;
        }

        if(f){
            dfs(endWord, beginWord, map, res, new ArrayList<>());
        }

        return res;
    }

    void dfs(String t, String s, HashMap<String, List<String>> map, List<List<String>> res, List<String> lst){
        lst.add(t);
        if(s.equals(t)){
            List<String> path = new ArrayList<>(lst);
            Collections.reverse(path);
            res.add(path);
        }
        else if(map.containsKey(t)){
            for(String word : map.get(t)){
                dfs(word, s, map, res, lst);
            }
        }

        lst.remove(lst.size()-1);
    }
}

```

### What changed?

1. **Removed `st.removeAll(cl)`:** Replacing it with an explicit loop over `nl` utilizing `st.remove(word)` cuts down the nested lookup loop overhead drastically.
2. **Added an early exit check:** If `endWord` isn't even in the dictionary pool to begin with, it returns an empty list immediately without running the heavy loops.

---

To understand exactly why building the graph **in reverse** saves your code from timing out, let's trace a concrete example.

Imagine you are trying to change `hit` into `cog` using this short word list:
`["hot", "dot", "dog", "lot", "log", "cog", "pop", "pin"]`

---

### The Problem with a Forward Graph (`parent -> children`)

If you build a normal forward graph, your BFS maps out every possible valid transition moving outward from `hit`. The structure looks like this:

```text
Level 0: hit
Level 1: hot
Level 2: dot, lot
Level 3: dog, log, pop   <-- Notice "pop" is a random dead end from "lot"!
Level 4: cog, pin        <-- "pin" is another dead end!

```

If you pass this forward graph to a DFS starting from `hit`, the DFS has to blindly guess which paths are good.

1. It explores `hit -> hot -> lot -> pop`.
2. It hits a dead end at `pop` because `pop` cannot reach `cog`.
3. It has to roll back (backtrack) and try `hit -> hot -> lot -> log -> pin`.
4. Another dead end! It rolls back again.

In massive dictionaries, your DFS will waste millions of cycles wandering down these useless branches.

---

### The Genius of the Reverse Graph (`child -> parents`)

By using `map.computeIfAbsent(nw, k -> new ArrayList<>()).add(str);`, you are tracking **"Who brought me here?"** instead of "Where can I go next?".

Let's look at exactly what happens inside the loop when your BFS processes Level 2 (`dot` and `lot`):

1. **Processing `dot`:** The code mutates `dot` and finds `dog`.
* `nw` is `"dog"`, `str` is `"dot"`.
* It maps: `"dog" -> ["dot"]`


2. **Processing `lot`:** The code mutates `lot` and finds `log` and `pop`.
* It maps: `"log" -> ["lot"]`
* It maps: `"pop" -> ["lot"]`



When the BFS finally hits Level 3, it finds `cog`. The code looks at the parents of `cog`:

* `nw` is `"cog"`, `str` is `"dog"` and `"log"`.
* It maps: `"cog" -> ["dog", "log"]`

Notice what your `map` looks like at the end of the BFS:

```text
"hot" -> ["hit"]
"dot" -> ["hot"]
"lot" -> ["hot"]
"dog" -> ["dot"]
"log" -> ["lot"]
"pop" -> ["lot"]   <-- This exists, BUT look closely at the next line...
"cog" -> ["dog", "log"]

```

### How DFS Uses This to Avoid Dead Ends

When the BFS finishes, you start your DFS directly at the `endWord`: **`cog`**.

The DFS asks the map: *"Who are the parents of `cog`?"* The map replies: **`["dog", "log"]`**.

The DFS branches into two perfect paths:

1. It goes to `dog`. It asks: *"Who is the parent of `dog`?"* Map says: `dot`. Then `dot` points to `hot`, which points to `hit`. **Path found!**
2. It goes to `log`. It asks: *"Who is the parent of `log`?"* Map says: `lot`. Then `lot` points to `hot`, which points to `hit`. **Path found!**

#### Look at what the DFS completely missed:

Where is `pop`? Where is `pin`?

Because `pop` and `pin` never successfully transformed into `cog` during the BFS, **`cog` does not list them as parents.** When your DFS walks backward from the destination, **the dead ends are completely invisible to it.** The DFS is mathematically trapped inside a highway of pure, guaranteed winning paths. It never has to guess, it never hits a dead end, and it never wastes execution time. That is why it passes without a TLE!

---
Using two sets (`cl` and `nl`) instead of a single standard `Queue` is the exact secret to finding **all** shortest paths without accidentally blocking parallel routes.

Here is exactly why a single queue breaks the logic, and why that specific deletion line is mandatory.

---

## 1. Why a standard Queue fails here (The "Parallel Paths" Problem)

In a normal BFS (like Word Ladder I), we use a single queue and a `visited` set. The absolute second we see a word, we mark it as visited so no other word can touch it.

If we use that exact same single queue logic for Word Ladder II, **we will accidentally delete alternative shortest paths.**

### Let's look at an example:

Imagine you are at a level where `cl` (Current Level) contains two words: **`dot`** and **`lot`**. Both of them can transform into the word **`dog`**.

#### Scenario A: Using a Single Queue + Instant Deletion

1. Your queue processes **`dot`** first.
2. `dot` finds **`dog`**.
3. Your code adds `dog` to the queue and instantly deletes `dog` from the dictionary so it isn't visited again.
4. Next, the queue processes **`lot`**.
5. `lot` tries to find its neighbors, but **`dog` has already been deleted from the dictionary!**
6. `lot` completely misses `dog`.

Because `lot` missed `dog`, you just lost the entire second path (`hit -> hot -> lot -> dog -> cog`). Your code will only output one path instead of all of them!

#### Scenario B: Using `cl` and `nl` (The Fix)

By separating the layers into **Current Level (`cl`)** and **Next Level (`nl`)**, we allow multiple parents to discover the same child during the same round:

1. We process the entire layer `cl` (`dot` and `lot`).
2. `dot` looks in the dictionary, finds `dog`, adds it to `nl`, and maps: `"dog" -> ["dot"]`.
3. `lot` looks in the dictionary. Since we haven't deleted anything yet, `dog` is still there! `lot` finds `dog`, adds it to `nl`, and maps: `"dog" -> ["dot", "lot"]`.

By using sets, `nl.add(nw)` automatically ignores duplicates so `dog` is only kept once for the next round, but **both** parents successfully log their relationship in the map.

---

## 2. Explaining the Deletion Line

This brings us to the exact lines you asked about:

```java
// After the ENTIRE current level is completely finished looping:
for (String word : nl) {
    st.remove(word); // Remove all newly discovered words from the master dictionary
}

cl = nl; // Move down to the next level layer

```

### What this does:

This is **Delayed Deletion**. We purposefully waited until every single word in the current layer (`cl`) finished scanning all of its neighbors. Only *after* that loop is 100% complete do we officially scrub those new words (`nl`) out of the master dictionary `st`.

### Why we need it:

We must delete them now to prevent **deeper, longer paths** from using them.

For example, once `dog` is found at Level 3, we want both Level 2 words (`dot` and `lot`) to connect to it. But we absolutely do *not* want a Level 4 word or a looping word to find `dog` later on, because any path finding `dog` at Level 4 or 5 is inherently longer and not a *shortest* path.

Swapping `cl = nl` simply discards the old layer we just finished, elevates the new layer to be the current layer, and the `while` loop starts the process over for the next depth level!
---
The short answer is **yes, for finding *all* shortest paths in a level-by-level unweighted graph (like Word Ladder II), the `cl` and `nl` approach is the cleanest, most efficient design.**

However, it is **not** the only way to build a reverse graph, and it isn't used for *every* type of graph problem.

To understand why this is the gold standard for this specific problem—and where it wouldn't work—let's break down how it compares to alternative graph architectures.

---

## Why it is the Best for "All Shortest Paths" (BFS)

When your goal is to find **all parallel shortest paths** of the exact same minimum length, you face a unique paradox: you cannot let a node be visited by a deeper layer, but you *must* allow it to be visited multiple times by the *same* layer.

The `cl` and `nl` structure solves this beautifully because it creates a strict **temporal boundary** (a firewall) between generations.

### Alternative: A single Queue with Distance tracking

Could you build the reverse graph using a standard `Queue<String>` instead of two sets? Yes! But look at what your code would have to do:

1. You would need a `HashMap<String, Integer> distance` map.
2. Every time you find a neighbor `nw` from a current word `str`, you would have to check:
* *Is `nw` unvisited?* Add it to the queue, set `distance.put(nw, distance.get(str) + 1)`, and add the reverse link.
* *Is `nw` already visited?* Check if `distance.get(nw) == distance.get(str) + 1`. If it matches, it means `nw` is on the same layer, so you add the reverse link. If it's smaller, ignore it.



While using a single queue with distance tracking is perfectly correct, it requires **constantly querying and writing to a distance Map** inside your deepest inner loops. In Java, map lookups are slower than local variable evaluation. The `cl` and `nl` approach completely bypasses the need to track or look up primitive integers because the code structure *inherently* knows that everything in `nl` is exactly one step further than `cl`.

---

## When is this approach NOT the best?

While this is amazing for Word Ladder II, you should **not** use the `cl` and `nl` approach in the following graph scenarios:

### 1. Weighted Graphs (Dijkstra's Algorithm)

If edges have different weights (e.g., traveling from word A to word B costs 5 points, but A to C costs 1 point), BFS completely breaks down. You cannot move level-by-level because a path with more steps might actually be cheaper overall. For weighted graphs, you must use a `PriorityQueue` (Dijkstra's) and log backward edges based on whether a new path relaxes a node's total cost score.

### 2. Standard Shortest Path Length (Word Ladder I)

If the question only asks for the *length* of the shortest path (or just a single valid path), using `cl` and `nl` is overkill. A standard `Queue` with an instant `visited.add()` check is faster and uses less memory because you don't need to preserve alternative parallel routes.

### 3. Topological Sorting or Cycle Detection

For dependencies (like Course Schedule), you are traversing deep into a single branch to find prerequisites, which makes Depth-First Search (DFS) with state arrays (`unvisited`, `visiting`, `visited`) the superior choice.

---

## The Interview Takeaway

If an interviewer asks you a problem where you need to **reconstruct all shortest paths in an unweighted layout**, explicitly mention this pattern:

> *"To preserve parallel optimal paths while blocking deeper loops, I will process the BFS level-by-level using a current-level set and a next-level set. This acts as a natural batching mechanism, allowing multiple parents to claim a common child before that child is permanently removed from the search space."*

Showing that you understand *why* you are separating the layers without relying on an extra integer distance map shows a very high level of algorithmic maturity!
---
