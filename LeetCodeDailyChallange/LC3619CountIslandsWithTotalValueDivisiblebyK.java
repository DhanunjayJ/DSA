class Solution {
    public int countIslands(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != 0) {
                    // IO.println("Calling i " + i + " j " + j + " val " + grid[i][j]);
                    long sum = dfs(grid, i, j);
                    if (sum % k == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    public long dfs(int[][] grid, int i, int j) {
        long sum = 0;
        sum = grid[i][j];
        // IO.println("Setting i"+i+" j"+j+" val"+grid[i][j]);
        grid[i][j] = 0;
        for (int d = 0; d < 4; d++) {
            int x = dirs[d][0] + i;
            int y = dirs[d][1] + j;
            if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && grid[x][y] != 0) {
                sum += dfs(grid, x, y);
            }
        }
        return sum;
    }
}