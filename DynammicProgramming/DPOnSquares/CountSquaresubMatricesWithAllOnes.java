class Solution {
    public int countSquares(int[][] matrix) {
        /*
        here we try to build the maximal square that could be possible with each
        i,j value and then we try to add all the values of the dp values.
        which gives the count of all the square submatrices with all ones. 
        */
        int n = matrix.length;
        int m = matrix[0].length;
        int [][] dp = new int[n+1][m+1];
        int sum = 0;
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(matrix[i-1][j-1]==1){
                    dp[i][j] = Math.min(dp[i-1][j],Math.min(dp[i-1][j-1],dp[i][j-1]))+1;
                    sum += dp[i][j];
                }else{
                    dp[i][j] = 0;
                }
            }
        }
        return sum;
    }
}