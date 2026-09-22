Forget the fancy terms "suffix" and "prefix" for a minute. Let's look at how you generate **every single substring** in normal brute force.

---

### 1. How you normally find all substrings

If you have the string `"aba"`:

* **Start at index 0** (`'a'`):
* length 1: `"a"`
* length 2: `"ab"`
* length 3: `"aba"`


* **Start at index 1** (`'b'`):
* length 1: `"b"`
* length 2: `"ba"`


* **Start at index 2** (`'a'`):
* length 1: `"a"`



That is **literally every substring** that can ever exist in `"aba"`.

Notice what you did: you picked a starting point, and walked character-by-character to the right.

---

### 2. Now, push them into a Trie step-by-step

Let's do that exact process, but walking down a Trie.
We start with an empty Trie with just a `Root`:

```
   Root

```

Our substring count starts at **1** (for the empty string `""` represented by `Root`).

---

#### Round 1: Start at index 0 (`'a'`)

Walk to the end: `'a'`, then `'b'`, then `'a'`.

1. Read `'a'`:
* Look at `Root`. Does child `'a'` exist? **No.**
* Create node `[a]`.
* **Count + 1** (we just discovered substring `"a"`).


2. Read `'b'`:
* From `[a]`, does child `'b'` exist? **No.**
* Create node `[b]`.
* **Count + 1** (we just discovered substring `"ab"`).


3. Read `'a'`:
* From `[b]`, does child `'a'` exist? **No.**
* Create node `[a]`.
* **Count + 1** (we just discovered substring `"aba"`).



**Trie right now:**

```
     Root
      | 'a'  (count = 2) -> substring "a"
     [N1]
      | 'b'  (count = 3) -> substring "ab"
     [N2]
      | 'a'  (count = 4) -> substring "aba"
     [N3]

```

---

#### Round 2: Start at index 1 (`'b'`)

Go back to `Root`. Walk to the end: `'b'`, then `'a'`.

1. Read `'b'`:
* Look at `Root`. Does child `'b'` exist? **No.**
* Create node `[b]`.
* **Count + 1** (we just discovered substring `"b"`).


2. Read `'a'`:
* From that `[b]`, does child `'a'` exist? **No.**
* Create node `[a]`.
* **Count + 1** (we just discovered substring `"ba"`).



**Trie right now:**

```
            Root
           /    \
     'a'  /      \ 'b'  (count = 5) -> substring "b"
        [N1]     [N4]
         | 'b'     | 'a' (count = 6) -> substring "ba"
        [N2]     [N5]
         | 'a'
        [N3]

```

---

#### Round 3: Start at index 2 (`'a'`)

Go back to `Root`. Walk to the end: just `'a'`.

1. Read `'a'`:
* Look at `Root`. Does child `'a'` exist? **YES! (It's `[N1]` from Round 1).**
* Do we create a node? **NO.**
* Do we increase count? **NO.**
* Why? Because we already saw the substring `"a"` in Round 1! The Trie naturally recognized: *"Hey, I already have a path for 'a', this is a duplicate!"*



We reached the end of the string. We stop.

---

### The Final Result

Look at the tree we built:

* Total nodes created = 5 nodes (`N1, N2, N3, N4, N5`).
* Plus the empty string (`Root`) = 1.
* **Total = 6 distinct substrings**:
`""`, `"a"`, `"ab"`, `"aba"`, `"b"`, `"ba"`.

---

### Why the Trie is doing the magic:

* A Trie **merges duplicates automatically**.
* Every single node you create in the Trie represents a path from `Root` to that node.
* That path spells out **one specific substring**.
* If a substring was already seen, the path already exists, so **no new node is created**.
* If a substring is brand new, you are forced to make a **new node**.

Therefore:


$$\text{Distinct Substrings} = \text{Total Nodes Created} + 1 \text{ (for empty string)}$$

That is the entire trick. You just run a nested loop (starting index $i$ from $0$ to $n-1$, ending index $j$ from $i$ to $n-1$), step down the Trie, and increment your counter whenever `curr.children[ch] == null`.