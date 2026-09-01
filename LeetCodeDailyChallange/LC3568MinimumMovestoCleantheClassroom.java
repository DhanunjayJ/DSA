class Solution {

    static int [][] dirs = {{1,0},{0,1},{-1,0},{0,-1}};

    public int minMoves(String[] classroom, int energy) {

        int n = classroom.length;
        int m = classroom[0].length();

        // 1. Pre-processing the grid and mapping litters to a bitmask.
        // Why a 2D array (lp) instead of a list of coordinates? 
        // It allows O(1) direct lookup during BFS when checking a cell's litter status,
        // rather than linearly searching through a coordinate list on every single step.
        //A simple count cannot tell you which specific locations still have uncollected 
        // litter. The bitmask uniquely identifies every single individual litter item.
        int[][] lp = new int[n][m];
        //simple count like a tally mark on a scorecard: it only tells you how many you have collected total (e.g., "I have 2 items"), 
        //but it doesn't tell you which ones they are.
        //A bitmask, on the other hand, acts like a digital checklist 
        //where every single litter item has its own dedicated checkbox.
        int count = 0;// Tracks the total number of 'L' cells found

        char[][] cr = new char[n][m];

        int sx = 0, sy = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = classroom[i].charAt(j);
                cr[i][j] = c;
                if (c == 'L') {
                    // Store pre-shifted bitmask value (e.g., 1, 2, 4...) directly in the grid cell
                    lp[i][j] = 1 << count;
                    count++;
                } else if (c == 'S') {
                   sx = i;
                   sy = j;
                }
            }
        }

        // full mask represents all litters collected (e.g., if count = 3, full = 8, full - 1 = 7 or 111 in binary)
        int full = 1<<count;
        //each state is repesented by (x, y, mask, e)
        

        // 2. Visited Pruning & State Management: 
        // Why a 3D bestEnergy array instead of a simple boolean visited array?
        // A standard visited array fails because the same cell can be revisited under different conditions 
        // (e.g., different remaining energy or a different subset of litter collected).
        // bestEnergy[x][y][mask] stores the maximum energy ever seen at this position with this specific mask.
        // If we reach a state with less or equal energy, we prune (skip) it because a prior path did it better or equal.
        int [][][]bestEnergy = new int[n][m][full];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                Arrays.fill(bestEnergy[i][j],-1);
            }
        }
        // Initialize starting position with full initial energy and 0 litter collected (mask 0)
        bestEnergy[sx][sy][0] = energy;

        // State object representing the student's complete configuration: (x, y, mask, energy, steps)
        class State {
            int x,y,mask,e,steps;
            State(int x,int y,int mask,int e,int steps){
                this.x = x;
                this.y = y;
                this.mask = mask;
                this.e = e;
                this.steps = steps;
            }
        }

        Deque<State> q = new ArrayDeque<>();

        q.add(new State(sx,sy,0,energy,0));

        while(!q.isEmpty()){
            
            State t = q.remove();

            // 3. Victory Condition: 
            // Why a bitmask instead of a simple count? 
            // A simple count only tracks *how many* items are picked up, not *which* ones. 
            // The bitmask acts as a digital checklist. t.mask == full - 1 ensures every 
            // single specific litter item has been gathered.

            if(t.mask==full-1){
                return t.steps;
            }

            // If energy hits 0, we cannot make any further moves.

            if(t.e==0){
                continue;
            }
            
            // Explore all 4 directions
            for(int d=0;d<4;d++){
                int nx = t.x + dirs[d][0];
                int ny = t.y + dirs[d][1];

                // Boundary check & obstacle check ('X' cannot be passed through)
                if(nx<0 || nx>=n || ny<0 || ny>=m || cr[nx][ny]=='X') continue;

                // Energy mechanics: reset to max capacity if cell is 'R', otherwise decrement by 1 per step
                int ne = cr[nx][ny]=='R' ? energy : t.e-1;

                int nmask = t.mask | lp[nx][ny];

                // Pruning check: only proceed if this path reaches the state with strictly more energy
                if(ne > bestEnergy[nx][ny][nmask]){
                    bestEnergy[nx][ny][nmask] = ne;
                    q.add(new State(nx,ny,nmask,ne,t.steps+1));
                }
            }
        }

        // Return -1 if it's impossible to collect all litter
        return -1;
    }
}

/*


To truly understand why a bitmask is necessary to track **which** specific litters are collected rather than just **how many**, we have to look at how pathfinding algorithms like BFS make decisions.

Using a simple count (like keeping a tally of "2 out of 3 items collected") fundamentally breaks the algorithm for two major reasons: **State Collision** and **Routing Dependency**.

---

### 1. The Danger of State Collisions (Why Pruning Fails)

The biggest performance saver in your code is the `bestEnergy` tracking array, which prunes redundant paths. It assumes that if you reach the same cell with the same progress and equal-or-less energy, you can safely throw that path away.

Imagine a classroom with **3 litter items**: Litter A, Litter B, and Litter C.

* **Path 1** collects **Litter A and Litter B**, and ends up standing at cell `(2, 2)` with **3 units of energy**. (Count = 2)
* **Path 2** collects **Litter B and Litter C**, and ends up standing at the **exact same cell `(2, 2)**` with **3 units of energy**. (Count = 2)

If your algorithm only tracked a **simple count** (meaning both paths register as `count = 2` at cell `(2, 2)`):

1. The BFS would look at Path 2, see that it arrived at `(2, 2)` with the same energy and the same count (`2`), and **prune (delete) Path 2** because it thinks Path 1 already covered it.
2. **The Disaster:** Path 1 still needs to collect **Litter C**, while Path 2 still needs to collect **Litter A**. By pruning Path 2, the algorithm permanently deletes the only route that was close to Litter A, and your program will either fail to find a solution or return `-1` incorrectly!

A **bitmask** prevents this collision. Path 1's mask is `110` (A and B collected), and Path 2's mask is `011` (B and C collected). Because their bitmasks are entirely different, the algorithm treats them as completely separate, valid states and keeps both alive.

### Summary

A simple count treats all progress as identical. A bitmask treats progress as a **precise checklist**. Because different subsets of collected items leave you with entirely different remaining destinations and routing requirements, tracking the exact identity of every item via a bitmask is the only way to guarantee the shortest, correct path.


*/