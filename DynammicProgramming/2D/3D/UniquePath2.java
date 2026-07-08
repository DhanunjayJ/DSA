import java.util.Arrays;

public class UniquePath2 {
    
    class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int [][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            Arrays.fill(dp[i],-1);
        }
        return helper(dp,obstacleGrid,m-1,n-1);
    }

    public int helper(int [][] dp,int [][] grid,int i,int j){
        if(i<0 || j<0 || grid[i][j]==1) return 0;
        if(i==0 && j==0) return 1;
        if(dp[i][j]!=-1) return dp[i][j];
        int x = helper(dp,grid,i-1,j);
        int y = helper(dp,grid,i,j-1);
        dp[i][j] = x+y;
        return dp[i][j];
    }
}
}
