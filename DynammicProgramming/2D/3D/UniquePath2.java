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


//Iterative Apporach s.c O(n2) approach

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int [][] dp = new int[m][n];
        dp[0][0]=1;
        for(int i=0;i<m;i++){
            if(obstacleGrid[i][0]==1){
                break;
            }
            dp[i][0] = 1;
        }
        for(int j=0;j<n;j++){
            if(obstacleGrid[0][j]==1) break;
            dp[0][j] = 1;
        }

        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(obstacleGrid[i][j]!=1){
                    dp[i][j] = dp[i-1][j]+dp[i][j-1];
                }
            }
        }

        return dp[m-1][n-1];
    }
}

//Opitmized code  o(n) space
 
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        // Edge case: if the starting point is blocked, no paths are possible
        if (obstacleGrid == null || obstacleGrid[0][0] == 1) {
            return 0;
        }

        int n = obstacleGrid[0].length;
        int[] dp = new int[n];
        
        // Base case: there is 1 way to reach the starting cell
        dp[0] = 1; 

        for (int[] row : obstacleGrid) {
            for (int j = 0; j < n; j++) {
                if (row[j] == 1) {
                    // If there's an obstacle, 0 ways to reach or pass through this cell
                    dp[j] = 0;
                } else if (j > 0) {
                    // dp[j] (from previous row) + dp[j-1] (from left cell in current row)
                    dp[j] += dp[j - 1];
                }
            }
        }

        return dp[n - 1];
    }
}