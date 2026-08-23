Here is a comprehensive breakdown of the most useful `StringBuilder` methods in Java.

Because `StringBuilder` is designed to be a **mutable** (modifiable) sequence of characters, its functions are best understood by what they do to the underlying text.

### Adding and Modifying Content

These methods physically change the characters stored in the `StringBuilder`. Most of these methods return the `StringBuilder` instance itself, which allows you to chain them together (e.g., `sb.append("A").append("B").reverse();`).

| Method | Description |
| --- | --- |
| **`append(X)`** | Adds the string representation of `X` (can be boolean, char, int, String, Object, etc.) to the end of the sequence. |
| **`insert(int offset, X)`** | Inserts the string representation of `X` starting at the specified `offset` index. |
| **`replace(int start, int end, String str)`** | Replaces the characters from `start` to `end - 1` with the provided `str`. |
| **`setCharAt(int index, char ch)`** | Replaces the single character at the specified `index` with `ch`. |
| **`reverse()`** | Reverses the entire sequence of characters. |

---

### Removing Content

| Method | Description |
| --- | --- |
| **`delete(int start, int end)`** | Removes the characters in a substring from `start` up to `end - 1`. |
| **`deleteCharAt(int index)`** | Removes the single character at the specified `index`. |

---

### Extracting and Searching

These methods do not modify the `StringBuilder`. They are used to read data from it, similar to standard `String` methods.

| Method | Description |
| --- | --- |
| **`charAt(int index)`** | Returns the character at the specified `index`. |
| **`substring(int start)`** | Returns a new `String` containing characters from `start` to the end. |
| **`substring(int start, int end)`** | Returns a new `String` containing characters from `start` to `end - 1`. |
| **`indexOf(String str)`** | Returns the index of the first occurrence of `str` (returns -1 if not found). |
| **`lastIndexOf(String str)`** | Returns the index of the last occurrence of `str`. |
| **`toString()`** | Converts the `StringBuilder` object into an immutable, standard Java `String`. |

---

### Size and Capacity Management

Java dynamically allocates a memory buffer (capacity) for `StringBuilder`. If you exceed it, Java automatically creates a larger array behind the scenes. These methods let you manage that memory manually.

| Method | Description |
| --- | --- |
| **`length()`** | Returns the actual number of characters currently in the sequence. |
| **`capacity()`** | Returns the current total allocated memory size (number of characters it can hold before resizing). |
| **`setLength(int newLength)`** | Forces the length of the sequence. If smaller, it truncates. If larger, it pads with null characters. |
| **`ensureCapacity(int min)`** | Ensures the internal capacity is at least equal to the specified `min` value. |
| **`trimToSize()`** | Reduces the allocated memory capacity to exactly match the current `length()`, saving RAM. |