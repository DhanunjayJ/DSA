The DSU approach you just completed is excellent and runs in near-linear time, but when interviewers ask for the **most optimal way** to solve **Making A Large Island**, they are often looking for a **DFS/BFS Color-Labeling (or ID-Mapping) approach**.

While both methods have a time complexity of $O(n^2)$, the DFS/BFS approach is faster in practice and uses less memory. It completely avoids the overhead of managing a parent tree array, performing recursive path compressions, or executing union rank updates.

---

## The DFS Color-Labeling Strategy

Instead of building a dynamic DSU data structure, you pass through the grid using standard Depth-First Search (DFS) to find distinct components. You "paint" each island with a unique ID (starting from 2, since 0 and 1 are already taken) and save its total area in a hash map or array.

### The Step-by-Step Process:

1. **Phase 1: Label and Measure Islands (DFS)**
* Scan the grid. When you find a `1`, start a DFS.
* Assign this entire island a unique integer **`islandId`** (e.g., `2`, `3`, `4`, ...).
* Change every `1` in that island to its assigned `islandId` directly in the grid.
* Count the number of cells visited during the DFS to find the total area, and store it in a map: `{islandId : islandSize}`.


2. **Phase 2: Evaluate Flips**
* Scan the grid again looking for `0` cells.
* For each `0`, look at its 4 neighbors. Collect their unique `islandId` values using a `HashSet` (to avoid adding the same island twice if the zero touches it from multiple sides).
* Sum the sizes of those unique IDs from your map, add `1` (for the flipped cell itself), and update your maximum area.



---

## Clean Java Implementation

```java
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class Solution {
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        Map<Integer, Integer> islandSizes = new HashMap<>();
        int islandId = 2; // Start IDs from 2 to avoid conflicting with 0 and 1
        int maxIsland = 0;

        // Step 1: Label each distinct island with a unique ID and store its size
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int size = dfs(grid, i, j, islandId, n);
                    islandSizes.put(islandId, size);
                    maxIsland = Math.max(maxIsland, size); // Handle case with no 0s
                    islandId++;
                }
            }
        }

        // Step 2: Evaluate every 0 to see what size we get by converting it
        int[][] nbrs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    HashSet<Integer> uniqueIslands = new HashSet<>();
                    
                    for (int[] dir : nbrs) {
                        int r = i + dir[0];
                        int c = j + dir[1];
                        
                        if (r >= 0 && c >= 0 && r < n && c < n && grid[r][c] > 1) {
                            uniqueIslands.add(grid[r][c]);
                        }
                    }

                    int currentSize = 1; // Count the flipped '0' cell itself
                    for (int id : uniqueIslands) {
                        currentSize += islandSizes.get(id);
                    }
                    maxIsland = Math.max(maxIsland, currentSize);
                }
            }
        }

        return maxIsland;
    }

    // Standard DFS to flood-fill and size the island
    private int dfs(int[][] grid, int r, int c, int id, int n) {
        if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] != 1) {
            return 0;
        }

        grid[r][c] = id; // Paint the cell with the unique ID
        
        return 1 + dfs(grid, r + 1, c, id, n)
                 + dfs(grid, r - 1, c, id, n)
                 + dfs(grid, r, c + 1, id, n)
                 + dfs(grid, r, c - 1, id, n);
    }
}

```

---

## Why this is preferred over DSU for this specific problem

| Feature | Disjoint Set Union (DSU) | DFS / BFS Color Labeling |
| --- | --- | --- |
| **Time Complexity** | $O(n^2 \cdot \alpha(n^2))$ | **$O(n^2)$ strictly** |
| **Space Complexity** | $O(n^2)$ (Two full helper arrays) | **$O(n^2)$** (In-place grid modification + tiny map/stack space) |
| **Real-world Runtime** | Slower due to pointer-chasing (`find`) and multiple conditional assignments. | **Significantly faster** because it scans the grid exactly twice with zero structural overhead. |

If you are dealing with a **static matrix** given all at once, the DFS Color-Labeling pattern is the cleaner, faster production-ready approach. If islands were being added dynamically one-by-one via external queries (like in *Number of Islands II*), that is where DSU becomes the undisputed choice.