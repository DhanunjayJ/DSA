Here are your comprehensive revision notes for **89. Gray Code**, structured specifically for rapid review and long-term retention.

---

### 1. Core Problem Breakdown

* **What we are computing:** A sequence of $2^n$ integers ranging from $0$ to $2^n - 1$ where every integer appears exactly once.
* **The strict constraint:** The binary representation of every pair of adjacent integers (including the wrap-around between the last and first integers) must differ by **exactly one bit** (a Hamming distance of 1).

---

### 2. Naive Approach & Why It Fails

* **The Brute-Force Method:** Model the problem as finding a Hamiltonian path or cycle in an $n$-dimensional hypercube. Use backtracking to explore unvisited numbers, where from any current number you have $n$ choices (flipping each of the $n$ bit positions one by one).
* **Why it hits TLE:** The search space is exponential ($2^n$). For $n = 16$, $2^{16} = 65,536$ states. Backtracking without memoization or pruning incurs heavy recursive overhead and explores invalid branch paths, resulting in a **Time Limit Exceeded (TLE)** error.

---

### 3. The Core Intuition / Key Insight

* **The Mathematical Shortcut:** Gray codes can be generated directly using a closed-form bitwise formula without any search or recursion.
* **The Formula:** For any index $i$ from $0$ to $2^n - 1$, its corresponding Gray code is computed as:

$$\text{Gray}(i) = i \oplus (i \gg 1)$$


* **Why it works:** The right shift (`i >> 1`) aligns bit $b_{k+1}$ underneath bit $b_k$. The XOR operation (`^`) compares them simultaneously across all bit positions, perfectly replicating the recursive "reflection" property of Gray codes in $O(1)$ time per element.

---

### 4. Step-by-Step Algorithm & Dry Run

1. **Initialize:** Create a dynamic list to store the sequence. Calculate the total elements as $2^n$ using the left-shift operator (`1 << n`).
2. **Iterate:** Loop an index `i` from `0` up to `(1 << n) - 1`.
3. **Transform & Store:** Compute `i ^ (i >> 1)` for each step and append it to the result list.
4. **Return:** Return the completed list.

#### Dry Run for $n = 3$ ($2^3 = 8$ numbers):

| Index (`i`) | Binary (`i`) | `i >> 1` | `i ^ (i >> 1)` (Binary) | Decimal Output |
| --- | --- | --- | --- | --- |
| **0** | `000` | `000` | `000` | **0** |
| **1** | `001` | `000` | `001` | **1** |
| **2** | `010` | `001` | `011` | **3** |
| **3** | `011` | `001` | `010` | **2** |
| **4** | `100` | `010` | `110` | **6** |
| **5** | `101` | `010` | `111` | **7** |
| **6** | `110` | `011` | `101` | **5** |
| **7** | `111` | `011` | `100` | **4** |

* **Result sequence:** `[0, 1, 3, 2, 6, 7, 5, 4]`

---

### 5. Clean Code Solution (in Java)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> grayCode(int n) {
        // Total numbers in an n-bit gray code sequence is 2^n
        int totalElements = 1 << n;
        List<Integer> result = new ArrayList<>(totalElements);
        
        // Generate each Gray code in O(1) time using bitwise operations
        for (int i = 0; i < totalElements; i++) {
            result.add(i ^ (i >> 1));
        }
        
        return result;
    }
}

```

---

### 6. Revision Summary Checklist

* [ ] **Hypercube Equivalence:** Recognizing that adjacency with single-bit flips maps directly to traversing a hypercube graph.
* [ ] **Complexity Trap:** Avoiding backtracking for exponential constraints ($n \le 16$) where brute-force search times out.
* [ ] **The Magic Formula:** Memorizing the binary-to-gray conversion shortcut: `i ^ (i >> 1)`.
* [ ] **Bitwise Efficiency:** Utilizing `1 << n` for fast calculation of $2^n$ without integer overflow risks.