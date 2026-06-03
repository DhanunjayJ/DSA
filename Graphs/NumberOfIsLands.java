class Solution {
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        boolean [][] isVisited = new boolean[n][m];
        int count = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]=='1' && !isVisited[i][j]){
                    dfs(grid,isVisited,i,j);
                    count++;
                }
            }
        }
        return count;
    }
    int [][] nbrs = {{1,0},{0,1},{-1,0},{0,-1}};
    public void dfs(char [][] grid,boolean[][] vis,int i,int j){

        vis[i][j] = true;

        for(int k=0;k<nbrs.length;k++){

            int row = nbrs[k][0]+i;
            int col = nbrs[k][1]+j;

            if(row>=0 && col>=0 && row<grid.length && col<grid[0].length &&  grid[row][col]=='1' && !vis[row][col]){
                dfs(grid,vis,row,col);
            }
        }
    }
}