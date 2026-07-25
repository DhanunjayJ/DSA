class Solution {
    public int uniquePaths(int m, int n) {
        int [][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            Arrays.fill(dp[i],-1);
        }
        return helper(dp,m-1,n-1);
    }
    public int helper(int [][] dp,int i,int j){
        if(i<0 || j<0) return 0;
        if(i==0 && j==0) return 1;
        if(dp[i][j]!=-1) return dp[i][j];
        //we need to get the number of ways to reach
        //x-1 and y-1 and add the both the ways.
        int x = helper(dp,i-1,j);
        int y = helper(dp,i,j-1);
        dp[i][j] = x+y;
        return dp[i][j];
    }
}


//ITeratve approach
class Solution {
    public int uniquePaths(int m, int n) {
        int [][] dp = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i==0 || j==0) dp[i][j] = 1;
                else dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
}