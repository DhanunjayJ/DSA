It looks like you are referring to the **`computeIfAbsent()`** and **`putIfAbsent()`** methods in Java's `HashMap`. Typos happen to the best of us, but these two methods are absolute lifesavers when you want to avoid overwriting existing data or writing tedious `if-else` checks.

While they sound similar, they behave differently and are used for different scenarios. Here is the breakdown of how they work.

---

## 1. `putIfAbsent(K key, V value)`

This method adds the key-value pair to the map **only if** the specified key is not already associated with a value (or is mapped to `null`).

* **If the key exists:** It leaves the current value alone and returns the *existing* value.
* **If the key doesn't exist:** It puts the new value in and returns `null`.

### Code Example

```java
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> stock = new HashMap<>();
        stock.put("Apples", 10);

        // "Apples" already exists, so it will NOT change to 50
        stock.putIfAbsent("Apples", 50); 

        // "Oranges" does not exist, so it will be added
        stock.putIfAbsent("Oranges", 20); 

        System.out.println(stock); // Output: {Apples=10, Oranges=20}
    }
}

```

---

## 2. `computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction)`

This method is a bit more dynamic and uses a **lambda expression** (a function). It computes a new value **only if** the key is not already present (or is mapped to `null`).

The key difference? The value isn't created beforehand. It is only calculated *on the fly* if it's actually needed. This is great for performance if calculating the value is resource-heavy (like fetching from a database).

* **If the key exists:** It does nothing and returns the *existing* value.
* **If the key doesn't exist:** It runs the function, inserts the result, and returns the *new* value.

### Code Example

```java
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> wordLengths = new HashMap<>();

        // "Banana" is absent, so it calculates the length (6) and inserts it
        wordLengths.computeIfAbsent("Banana", key -> key.length());

        // "Banana" is now present, so this won't change anything
        wordLengths.computeIfAbsent("Banana", key -> 100); 

        System.out.println(wordLengths); // Output: {Banana=6}
    }
}

```

---

## Quick Comparison

| Feature | `putIfAbsent()` | `computeIfAbsent()` |
| --- | --- | --- |
| **Value Argument** | Takes a **concrete value** directly. | Takes a **function/lambda** to compute the value. |
| **Evaluation** | The value is evaluated *before* calling the method. | The value is evaluated *only if* the key is missing. |
| **Best Used For** | Passing simple, pre-existing values or constants. | Expensive operations (DB queries, complex math) or initializing collections (like a `List` inside a Map). |
| **Return Value** | Returns the **existing** value (or `null` if it was absent). | Returns the **current (existing or newly computed)** value. |

> 💡 **Common Use Case for `computeIfAbsent`:** It's incredibly popular for creating a `HashMap` of `Lists` (a Multi-map).
> `map.computeIfAbsent("category", k -> new ArrayList<>()).add("item");`


---

When you are grinding Data Structures and Algorithms (DSA) problems on platforms like LeetCode or HackerRank, `HashMap` is easily one of your most powerful weapons.

Beyond the standard `.put()` and `.get()`, Java’s `Map` interface has several built-in methods specifically designed to handle common DSA patterns (like counting frequencies, tracking coordinates, or building graphs) with much less code.

Here are the most helpful `HashMap` operations you should memorize for DSA, categorized by how you'll use them.

---

## 1. Frequency Counting & Updating Values

Frequency counting is arguably the most common use case for a HashMap in DSA (e.g., finding anagrams, subarray sums, or the most frequent element).

### `getOrDefault(Object key, V defaultValue)`

Instead of checking `if (map.containsKey(key))` before grabbing a value, this method lets you fetch the value or fall back to a default (usually `0`) if the key doesn't exist yet.

* **DSA Use Case:** Incrementing counts seamlessly.

```java
// The standard way to count frequencies in DSA
map.put(num, map.getOrDefault(num, 0) + 1);

```

### `merge(K key, V value, BiFunction remappingFunction)`

This is a highly optimized, elegant way to update an existing key or insert it if it’s missing. It takes a key, a default value, and a rule on how to combine them if the key already exists.

* **DSA Use Case:** An even shorter way to count frequencies or accumulate totals.

```java
// If 'num' doesn't exist, sets it to 1. If it does exist, adds 1 to the old value.
map.merge(num, 1, Integer::sum);

```

---

## 2. Conditional Modifications

In many sliding window or graph problems, you need to clean up your map dynamically to keep track of valid states.

### `containsKey(Object key)`

Returns a boolean indicating if the key exists.

* **DSA Use Case:** Checking if a complement exists in the **Two Sum** problem (`map.containsKey(target - nums[i])`).

### `remove(Object key)` & `remove(Object key, Object value)`

The standard `.remove(key)` deletes the key and returns its value. However, the two-argument version only removes the key **if it is currently mapped to a specific value**.

* **DSA Use Case:** In sliding window problems, when a frequency count drops to `0`, you must remove the key entirely so `.size()` reflects only unique elements in the current window.

```java
map.put(leftChar, map.get(leftChar) - 1);
map.remove(leftChar, 0); // Only removes from the map if the count hit exactly 0

```

---

## 3. Graph Traversal & Iteration

When a HashMap represents an Adjacency List for a graph, or when you need to find the maximum frequency after counting, you have to loop through the map efficiently.

### `entrySet()`

Returns a collection of `Map.Entry<K, V>` objects, giving you access to both keys and values simultaneously without doing extra `.get()` lookups (which wastes time).

* **DSA Use Case:** Finding the element with the highest frequency.

```java
int maxFreq = 0;
int mostFrequentElement = -1;

for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
    if (entry.getValue() > maxFreq) {
        maxFreq = entry.getValue();
        mostFrequentElement = entry.getKey();
    }
}

```

### `keySet()` and `values()`

* `keySet()`: Gives you a Set of all keys (great if you only care about the unique elements you've seen).
* `values()`: Gives you a Collection of all values (great if you need to pass all frequencies into a Heap/PriorityQueue).

---

## Cheat Sheet Summary for DSA

| Problem Pattern | The Method to Use | Why it saves time |
| --- | --- | --- |
| **Two Sum / Subarray Sum** | `containsKey(complement)` | $O(1)$ lookup to see if a past state matches your current state. |
| **Character/Element Counting** | `getOrDefault(key, 0) + 1` | Eliminates annoying `if-else` null checks. |
| **Graph Adjacency List Creation** | `computeIfAbsent(node, k -> new ArrayList<>())` | Automatically initializes a neighbor list for a new node. |
| **Sliding Window Clean-up** | `remove(key, 0)` | Keeps the map size accurate by wiping out zero-count keys. |
| **Top 'K' Frequent Elements** | `map.values()` | Easily dumps all counts straight into a PriorityQueue. |