A **Trie** (derived from re**trie**val, pronounced either "try" or "tree") is a tree-like data structure used to store and search collections of strings by sharing common prefixes.

---

### Why We Need It Over Hash Maps and BSTs

While a Hash Map provides average $O(L)$ lookups (where $L$ is string length), it falls short in critical real-world string operations:

* **Prefix Searching:** A Hash Map cannot find all words starting with `"app"` without scanning every single key in the map ($O(N \cdot L)$). A Trie reaches the prefix node in $O(L)$ and immediately traverses only matching branches.
* **Predictable Worst-Case Time:** Hash Maps degrade to $O(N \cdot L)$ under high collisions or adversarial inputs. A Trie guarantees exact $O(L)$ time for search, insertion, and deletion, independent of how many total words ($N$) are stored.
* **Lexicographical Ordering:** Unlike Hash Maps, an in-order traversal of a Trie yields words in alphabetical order automatically.
* **Common Prefix Compression:** Storing `"apple"`, `"apply"`, and `"application"` in a list duplicates the string `"appl"` three times. A Trie represents shared prefixes along a single path.

---

### Core Applications

* **Autocomplete & Search Suggestions:** Search engines and input forms walk down to the prefix node as the user types, then collect all downstream branches.
* **Spell Checkers:** Detecting whether a sequence of letters forms a valid word, or finding nearest matches by exploring neighboring branches.
* **IP Routing (Longest Prefix Match):** Routers use Bitwise Tries to match incoming destination IP addresses to routing table prefixes.
* **T9 Predictive Text & Contacts Search:** Looking up names or dictionary terms based on numeric keypad sequences.
* **Word Game Solvers:** Boggle, Scrabble, or Boggle-like grid traversals (such as LeetCode's *Word Search II*), where pruning paths that don't match any prefix drastically cuts down the search space.

---

### Trade-offs

| Advantage | Limitation |
| --- | --- |
| Guaranteed $O(L)$ lookups | **Memory overhead:** Each node holds an array or map of pointers (e.g., 26 pointers for lowercase English letters), leading to sparse memory usage. |
| Instant prefix matching | Cache locality is lower than contiguous structures like arrays or flat hash tables due to pointer chasing. |

To mitigate the memory overhead in production systems, variants like **Compressed Tries (Radix Trees / Patricia Tries)** merge single-child node chains into single edges (e.g., collapsing `a -> p -> p -> l -> e` into `"appl" -> "e"`).