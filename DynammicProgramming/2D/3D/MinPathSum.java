class Solution {
    public int minPathSum(int[][] grid) {
        /*
        we can only move right and down
        if we reach n-1. then we have to find out the min way to reach i-1 and j-1;
        once that is done then we could add the current cell value. 
        */
        int m = grid.length;
        int n = grid[0].length;
        int [][] dp = new int[m][n];
        for(int i=0;i<m;i++)Arrays.fill(dp[i],-1);
        return helper(grid,m-1,n-1,dp);
    }
    public int helper(int [][] grid,int i,int j,int [][] dp){
        //here the base case needs to be the max, so that it will take what ever 
        //that is there to the right or top of the it which is the min value
        //comared to the max.
        if(i<0 || j<0) return Integer.MAX_VALUE;
        if(i==0 && j==0) return grid[i][j];
        if(dp[i][j]!=-1) return dp[i][j];
        int x = helper(grid,i-1,j,dp);
        int y = helper(grid,i,j-1,dp);
        dp[i][j] = Math.min(x,y)+grid[i][j];
        return dp[i][j];
    }
}


//Iterative
class Solution {
    public int minPathSum(int[][] grid) {
        /*
        we can only move right and down
        if we reach n-1. then we have to find out the min way to reach i-1 and j-1;
        once that is done then we could add the current cell value. 
        */
        int m = grid.length;
        int n = grid[0].length;
        int [][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i==0 && j==0){
                    dp[i][j] = grid[i][i];
                }else if(i==0|| j==0){
                    dp[i][j] = i==0 ? dp[0][j-1]+grid[i][j] : dp[i-1][0]+grid[i][j];
                }else{
                   dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
                }
            }
        }
        return dp[m-1][n-1];
    }
}