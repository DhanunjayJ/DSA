### Intuition Behind the Solution

To solve the **Rat Maze With Multiple Jumps** problem, we need to guide a rat from the top-left corner `(0, 0)` to the bottom-right corner `(n - 1, n - 1)` using a set of rules:

1. **Variable Jump Lengths:** The number at each cell `mat[i][j]` tells us the *maximum* steps the rat can jump either **Right** or **Down**.
2. **Shortest Jumps & Rightward Preference:** We must prefer smaller jump sizes first, and for the same jump size, moving **Right** is preferred over moving **Down**.
3. **Obstacles:** Cells with a value of `0` are blocked.

#### Why Backtracking with DFS?

Because we need to find *a* valid path that satisfies strict priority orders (smallest jumps first, right before down), **Depth-First Search (DFS) with Backtracking** is a natural fit. We try moving in our preferred directions, and if we hit a dead end, we step back (backtrack) and try the next available option.

#### The Role of the Visited Array (`vis`)

In standard backtracking, when a path fails, we usually unmark the visited cells (`vis[i][j] = false`) so other paths can use them. However, doing that here causes an exponential explosion of states (**Time Limit Exceeded**).

Instead, by keeping `vis[i][j] = true` permanently once a cell is processed in a failed branch, we ensure we **never re-explore a dead-end cell**, turning a slow exponential search into an efficient traversal.

---

### Step-by-Step Code Explanation

#### 1. The Wrapper Function (`shortestDist`)

```java
class Solution {
    public ArrayList<ArrayList<Integer>> shortestDist(int[][] mat) {
        int n = mat.length;
        int[][] path = new int[n][n];
        boolean[][] vis = new boolean[n][n];
        
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        // Start DFS from the top-left corner (0, 0)
        if (pathExists(mat, 0, 0, path, vis)) {
            // If a valid path is found, copy the 'path' matrix into the required ArrayList format
            for (int i = 0; i < n; i++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    temp.add(path[i][j]);
                }
                ans.add(temp);
            }
            return ans;
        }
        
        // If no path exists, return [[-1]]
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(-1);
        ans.add(temp);
        return ans;
    }

```

* **Initialization:** Sets up the `path` matrix (to record our final routing with `1`s) and the `vis` matrix (to track visited cells).
* **Result Formatting:** Converts the raw 2D array into Java's nested `ArrayList<ArrayList<Integer>>` structure required by the problem statement.

---

#### 2. The Recursive DFS Function (`pathExists`)

```java
    public boolean pathExists(int[][] mat, int i, int j, int[][] path, boolean[][] vis) {
        int n = path.length;
        
        // Base Case: If we reach the bottom-right destination cell
        if (i == n - 1 && j == n - 1) {
            path[i][j] = 1;
            return true;
        }
        
        // Mark current cell as visited and include it in the current path
        vis[i][j] = true;
        path[i][j] = 1;
        
        int maxJump = mat[i][j];
        
        // Try jump lengths 's' from 1 to maxJump (Shortest jumps first!)
        for (int s = 1; s <= maxJump; s++) {
            
            // Priority 1: Try moving Right with jump size 's'
            int nextRow = i;
            int nextCol = j + s;
            if (nextCol < n && !vis[nextRow][nextCol]) {
                if (pathExists(mat, nextRow, nextCol, path, vis)) {
                    return true;
                }
            }
            
            // Priority 2: Try moving Down with jump size 's'
            int nR = i + s;
            int nC = j;
            if (nR < n && !vis[nR][nC]) {
                if (pathExists(mat, nR, nC, path, vis)) {
                    return true;
                }
            }
        }
        
        // Backtrack: If no valid path could be formed from this cell, 
        // unmark it in the path matrix so it doesn't taint future alternate route checks.
        path[i][j] = 0;
        return false;
    }
}

```

* **Base Case:** If `i == n - 1` and `j == n - 1`, we've arrived! We mark the final cell as `1` and return `true` to bubble up the success signal.
* **Jump Loop (`s`):** Iterates from `1` up to `maxJump`. This guarantees we explore smaller jumps before larger ones.
* **Directional Check:** Inside the loop, it checks **Right** (`j + s`) first, and then **Down** (`i + s`). This completely enforces the tie-breaker rule.
* **Backtracking Reset:** If all possible jumps from the current cell lead to dead ends, `path[i][j]` is reset back to `0`, signaling that this cell isn't part of the final successful path.