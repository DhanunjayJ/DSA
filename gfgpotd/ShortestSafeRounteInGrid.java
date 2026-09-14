class Solution {
    int shortestPath(int[][] mat) {
        // code here
        int n = mat.length;
        int m = mat[0].length;
        int [][] grid = new int[n][m];
        //marking unsafe cells.
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                grid[i][j] = mat[i][j];
            }
        }
        
        int [][] dirs = {{1,0},{0,1},{-1,0},{0,-1}};
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(mat[i][j]==0){
                    grid[i][j] = -1;
                    for(int k=0;k<4;k++){
                        int nr = i+dirs[k][0];
                        int nc = j+dirs[k][1];
                        if(nr>=0 && nc>=0 && nr<n && nc<m){
                            grid[nr][nc] = -1;
                        }
                    }
                }
            }
        }
        
        //we do bfs from every valid first column.
        //row,col,dist
        Queue<int[]> q = new LinkedList<>();
        boolean [][] vis = new boolean[n][m];
        
        for(int i=0;i<n;i++){
            if(grid[i][0]==1){
                q.add(new int[]{i,0,1});
                vis[i][0] = true;
            }
        }
        
        while(!q.isEmpty()){
            int [] rem = q.remove();
            
            if(rem[1]==m-1){
                return rem[2];
            }
            
            for(int i=0;i<4;i++){
                int nr = rem[0]+dirs[i][0];
                int nc = rem[1]+dirs[i][1];
                if(nr>=0 && nc>=0 && nc<m && nr<n && grid[nr][nc]==1 && !vis[nr][nc]){
                    vis[nr][nc] = true;
                    q.add(new int[]{nr,nc,rem[2]+1});
                }
            }
        }
        
        return -1;
    }
}
