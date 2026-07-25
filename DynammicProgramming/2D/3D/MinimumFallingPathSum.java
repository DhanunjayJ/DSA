class Solution {
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int [][] dp = new int[n][n];
        for(int i=0;i<n;i++) Arrays.fill(dp[i],Integer.MAX_VALUE);
        for(int i=0;i<n;i++){
            dp[0][i] = calcMin(dp,0,i,matrix);
        }
        int min = Integer.MAX_VALUE;

        for(int i=0;i<n;i++){
            min = Math.min(dp[0][i],min);
        }

        return min;
    }
    public int calcMin(int [][] dp,int i,int j, int [][] matrix){
        if(j<0 || j>=matrix.length) return Integer.MAX_VALUE;
        if(i==matrix.length-1) return matrix[i][j];
        if(dp[i][j]!=Integer.MAX_VALUE) return dp[i][j];
        int down = calcMin(dp,i+1,j,matrix);
        int downLeft = calcMin(dp,i+1,j-1,matrix);
        int downRight = calcMin(dp,i+1,j+1,matrix);
        dp[i][j] = Math.min(down,Math.min(downLeft,downRight))+matrix[i][j];
        return dp[i][j];
    }
}


// Iterative 

class Solution {
    public int minFallingPathSum(int[][] matrix) {

        int n = matrix.length;
        int [] dp = new int[n];

        for(int i=0;i<n;i++)
        {
            dp[i] = matrix[n-1][i];
        }

        for(int i=n-2;i>=0;i--){
            int [] temp = new int[n];
            for(int j=0;j<n;j++){
                int down = dp[j];
                int downLeft = j-1<0 ? Integer.MAX_VALUE : dp[j-1];
                int downRight = j+1>=n ? Integer.MAX_VALUE : dp[j+1];
                temp[j] = matrix[i][j]+Math.min(down,Math.min(downLeft,downRight));
            }
            dp = temp;
        }
        
        int max = Integer.MAX_VALUE;
        for(int val : dp) max = Math.min(max,val);
        return max;
    }
}

