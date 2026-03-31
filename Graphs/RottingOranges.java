class Solution {
    public int orangesRotting(int[][] grid) {
        //first See the patten here the time and each minute adjacent orange gets rotten.
        // this is a pattern of the breadth first search. where we do one distance to another 
        //step by step in all the directions.

        int n = grid.length;
        int m = grid[0].length;

        Deque<int[]> queue = new ArrayDeque<>();

        //check if there are rotten oragnes in the grid.
        //add them to the queue.
        //while checking calculate the rotten oranges and frresh ornages. 
        //so that while rotting if we find a fresh orange we decrement it;s count. 
        //so that we odn't need to count check the whle matrix if there is any fresh remaining. 
        
        int rottenOrangesCount = 0;
        int freshOrangesCount = 0;

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==2){
                    rottenOrangesCount++;
                    queue.add(new int[]{i,j,0});
                }else if(grid[i][j]==1){
                    freshOrangesCount++;
                }
            }
        }
        //if there is no rotten orange and there is atleast one fresh orange then
        // return -1
        if(rottenOrangesCount==0 && freshOrangesCount>0){
            return -1;
        }
        // untill there is not rotten orange in the queue we need to bfs. 

        int finalTime = 0;
        // to go through all the four directions easily we make a 2d array
        // that will help use easily go to the each one direction without using muliple loops
        // and complex checks.

        int [][] dirs = {{1,0},{0,-1},{-1,0},{0,1}};

        while(queue.size()>0){
            int [] rem = queue.pollFirst();
            finalTime = Math.max(rem[2],finalTime);
            for(int i=0;i<dirs.length;i++){
                int nrow = dirs[i][0] + rem[0];
                int ncol = dirs[i][1] + rem[1];
                if(nrow>=0 && ncol<m && nrow<n && ncol>=0 && grid[nrow][ncol]==1){
                    grid[nrow][ncol] = 2;
                    queue.offerLast(new int[]{nrow,ncol,rem[2]+1});
                    freshOrangesCount--;
                }
            }
        }

        return freshOrangesCount==0 ? finalTime : -1 ;
    }
}