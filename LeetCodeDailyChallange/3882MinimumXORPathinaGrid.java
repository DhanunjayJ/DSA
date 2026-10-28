class Solution {
    // int min = Integer.MAX_VALUE;
    int m,n;
    //Doing with 3d dp because just 2d dp will allows
    //us to pick on the minimu xor but picking minimum xor
    //will not always gurnattes to give us the mininmum xor in teh final anwer. 
    /*
    grid values typically satisfy 0 \le \text{grid}[i][j] < 1024 (values fit within 10 bits, i.e., 0 to 1023).
Since XORing any two 10-bit numbers (0 to 1023) always yields another 10-bit number, the value of currXor can never exceed 1023.
    */
    int [][][] dp;
    public int minCost(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        dp = new int[m][n][1024];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                Arrays.fill(dp[i][j],-1);
            }
        }
        return dfs(grid,0,0,0);
    }
    public int dfs(int [][] grid,int i,int j,int currXor){
        if(i>=m || j>=n || i<0 || j<0) return Integer.MAX_VALUE;
        currXor ^= grid[i][j];
        if(i==m-1 && j==n-1){
            return currXor;
        }
        if(dp[i][j][currXor]!=-1) return dp[i][j][currXor];
        int down = dfs(grid,i+1,j,currXor);
        int right = dfs(grid,i,j+1,currXor);
        dp[i][j][currXor] = Math.min(down,right);
        return dp[i][j][currXor];
    }
}