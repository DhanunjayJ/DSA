class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        /*
        just do bfs and visit all the availeble nodes and update them. 
        and add all the zero states to the matrix. 
        only visit that cell if that is not alreayd visited. if visied neglect it. 
        atlast return the last n-1 vlaues. 
        */

        int n = grid.length;

        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
            return -1;
        }

        int [][] nbrs = {{-1,0},{0,-1},{1,0},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};

        Queue<int[]> q = new LinkedList<>();

        q.add(new int[]{0,0,1});
        //using the grid itself as vis graph
        grid[0][0] = 1;
        

        while(!q.isEmpty()){

            int [] rem = q.remove();
            int r = rem[0];
            int c = rem[1];
            int dist = rem[2];

            if(r==n-1 && c==n-1) return dist;

            for(int i=0;i<nbrs.length;i++){
                int row = nbrs[i][0]+r;
                int col = nbrs[i][1]+c;
                if(row>=0 && col>=0 && row<n && col<n && grid[row][col]==0){
                    grid[row][col] = 1;
                    q.add(new int[]{row,col,dist+1});
                }
            }
        }

        return -1;
    }
}