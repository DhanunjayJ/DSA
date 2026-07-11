import java.util.HashSet;

public class MakeALargeIsLand {
    class Solution {

    int[] par;
    int[] size;
    int maxIsland = 0;

    public int largestIsland(int[][] grid) {
        /*
        Here we maintain the size of the each componenet in a array. 
        then we go throug the every possible zero in the grid. 

        and make it one. by making it one. 
        can we get a large island.

        if we get it we update it. 
        and atlast we return the value. 
        */
        int n = grid.length;
        int as = n * n;
        par = new int[as];
        size = new int[as];

        for (int i = 0; i < as; i++) {
            par[i] = i;
            size[i] = 1;
        }

        int[][] nbrs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

        boolean hasZero = false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    for (int k = 0; k < nbrs.length; k++) {
                        int row = i + nbrs[k][0];
                        int col = j + nbrs[k][1];
                        if (row >= 0 && col >= 0 && row < n && col < n && grid[row][col] == 1) {
                            union(i * n + j, row * n + col);
                        }
                    }
                }else {
                    hasZero = true;
                }
            }
        }

        if(!hasZero) return n*n;

        //go throught every zero in the grid and try to make it one.
        //if we make it one then who are the neibours. 
        //get the nebhors parents adn then add the sizes;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    //for unique parents.
                    HashSet<Integer> parents = new HashSet<>();
                    for (int k = 0; k < 4; k++) {
                        int row = i + nbrs[k][0];
                        int col = j + nbrs[k][1];
                        if (row >= 0 && col >= 0 && row < n && col < n && grid[row][col] == 1) {
                            parents.add(find(row * n + col));
                        }
                    }
                    //adding the 1 here to flip that one cell.
                    int cummSize = 1;
                    for (int p : parents) {
                        cummSize += size[p];
                    }
                    maxIsland = Math.max(cummSize, maxIsland);
                }
            }
        }

        return maxIsland;
    }

    public int find(int x) {
        if (x == par[x])
            return x;
        int temp = find(par[x]);
        par[x] = temp;
        return temp;
    }

    public void union(int x, int y) {

        int px = find(x);
        int py = find(y);

        if (px == py) {
            return;
        }

        if (size[px] >= size[py]) {
            par[py] = px;
            size[px] += size[py];
            maxIsland = Math.max(size[px], maxIsland);

        } else {
            par[px] = py;
            size[py] += size[px];
            maxIsland = Math.max(size[py], maxIsland);
        }
    }

}
}
