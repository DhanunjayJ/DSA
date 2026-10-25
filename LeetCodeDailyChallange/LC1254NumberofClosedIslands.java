class Solution {
    public int closedIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean [][] vis = new boolean[n][m];
        //mark all ones
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1 && !vis[i][j]){
                    dfs(grid,vis,i,j,1);
                }
            }
        }
        //mark all boundary zero and do dfs as much reachable as possbble.
        for(int i=0;i<n;i++){
            if(grid[i][0]==0 && !vis[i][0]) dfs(grid,vis,i,0,0);
            if(grid[i][m-1]==0 && !vis[i][m-1]) dfs(grid,vis,i,m-1,0);
        }

        for(int j=0;j<m;j++){
            if(grid[0][j]==0 && !vis[0][j]) dfs(grid,vis,0,j,0);
            if(grid[n-1][j]==0 && !vis[n-1][j]) dfs(grid,vis,n-1,j,0);
        }

        //now still any cells left unvisited.
        //they are mostly surrouneded by ones becuase wanything that is not surrounded
        //will be marked visited preivolsy.
        int components = 0;
        for(int i = 0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==0 && !vis[i][j]){
                    components++;
                    dfs(grid,vis,i,j,0);
                }
            }
        }
        return components;
    }

    int [][] dirs = {{1,0},{0,1},{-1,0},{0,-1}};
    
    public void dfs(int [][] grid,boolean [][] vis,int i,int j,int src){
        vis[i][j] = true;
        for(int d = 0;d<dirs.length;d++){
            int x = i+dirs[d][0];
            int y = j+dirs[d][1];
            if(x>0 && y>0 && x<grid.length && y<grid[0].length && grid[x][y]==src && !vis[x][y]){
                dfs(grid,vis,x,y,src);
            }
        }
    }
}

// optimmal way

class Solution {
    public int closedIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        // Step 1: Flood fill all 0s connected to the 4 boundaries (turn them into 1s)
        for (int i = 0; i < n; i++) {
            if (grid[i][0] == 0) dfs(grid, i, 0);
            if (grid[i][m - 1] == 0) dfs(grid, i, m - 1);
        }
        for (int j = 0; j < m; j++) {
            if (grid[0][j] == 0) dfs(grid, 0, j);
            if (grid[n - 1][j] == 0) dfs(grid, n - 1, j);
        }
        
        // Step 2: Count remaining unvisited 0s (these are strictly closed islands)
        int closedIslands = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 0) {
                    closedIslands++;
                    dfs(grid, i, j); // Sink the entire closed island
                }
            }
        }
        
        return closedIslands;
    }
    
    private final int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    
    private void dfs(int[][] grid, int i, int j) {
        // Base case: check bounds and if current cell is water (1)
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == 1) {
            return;
        }
        
        // Mark as visited by turning land (0) into water (1)
        grid[i][j] = 1;
        
        // Explore all 4 directions
        for (int[] dir : dirs) {
            dfs(grid, i + dir[0], j + dir[1]);
        }
    }
}