In Java's Collection Framework, `TreeMap` and `TreeSet` are the go-to choices when you need your data to be **sorted naturally** or by a custom comparator. Both are backed by a **Red-Black Tree** (a self-balancing binary search tree), which ensures consistent performance even as they grow.

---

## 1. TreeMap
A `TreeMap` stores data in **key-value pairs**. It sorts the entries based on the natural ordering of the keys or a `Comparator` provided at creation.

### Key Characteristics
* **Sorted:** Keys are always in ascending order.
* **No Null Keys:** It does not allow `null` keys (will throw `NullPointerException`), though `null` values are allowed.
* **Not Synchronized:** It is not thread-safe.

### Time Complexity
Since it uses a Red-Black Tree, most operations take **logarithmic time**.

| Operation | Time Complexity |
| :--- | :--- |
| `put(key, value)` | $O(\log n)$ |
| `get(key)` | $O(\log n)$ |
| `remove(key)` | $O(\log n)$ |
| `containsKey(key)` | $O(\log n)$ |

### Useful Methods
* **`firstKey()` / `lastKey()`:** Returns the lowest and highest keys.
* **`headMap(K toKey)`:** Returns a view of the portion of the map whose keys are strictly less than `toKey`.
* **`tailMap(K fromKey)`:** Returns a view of the portion of the map whose keys are greater than or equal to `fromKey`.
* **`ceilingKey(K key)`:** Returns the least key greater than or equal to the given key.
* **`floorKey(K key)`:** Returns the greatest key less than or equal to the given key.

---

## 2. TreeSet
A `TreeSet` is an implementation of the `Set` interface that uses a `TreeMap` internally to store its elements. It contains **unique elements** only.

### Key Characteristics
* **Sorted:** Elements are stored in ascending order.
* **Unique:** No duplicate elements allowed.
* **Performance:** Slightly slower than `HashSet` (which is $O(1)$) but provides the benefit of order.

### Time Complexity


| Operation | Time Complexity |
| :--- | :--- |
| `add(element)` | $O(\log n)$ |
| `remove(element)` | $O(\log n)$ |
| `contains(element)` | $O(\log n)$ |
| `size()` | $O(1)$ |

### Useful Methods
* **`first()` / `last()`:** Returns the first (lowest) and last (highest) elements.
* **`higher(E e)` / `lower(E e)`:** Returns the least element strictly greater than `e`, or greatest element strictly less than `e`.
* **`pollFirst()` / `pollLast()`:** Retrieves and removes the first or last element.
* **`subSet(E fromElement, E toElement)`:** Returns a view of the portion of the set ranging from `fromElement` to `toElement`.
* **`descendingSet()`:** Returns a reverse order view of the elements.

---

## Quick Comparison Table

| Feature | TreeMap | TreeSet |
| :--- | :--- | :--- |
| **Interface** | `NavigableMap`, `SortedMap` | `NavigableSet`, `SortedSet` |
| **Storage** | Key-Value pairs | Individual Objects |
| **Internal Data Structure** | Red-Black Tree | TreeMap (internally) |
| **Duplicates** | Duplicate values allowed, keys must be unique | No duplicates allowed |

Since you're working with **Java and Spring Boot** at Cognizant, you'll likely find these useful when you need to return sorted data from a service layer or when implementing ranking systems.

