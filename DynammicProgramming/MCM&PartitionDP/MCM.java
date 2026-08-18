class Solution {
    static int matrixMultiplication(int arr[]) {
        // code here
        /*
        to solve a matrix chain we need to split the matrix chain in two 
        parts.
        left and right. and cost of adding the left and right. 
        now, once done, we don't know which slipt is best amoung all the values
        between the i and j. so try all the slipts and return the minium.
        
        if two matrix a*b X b*d is multiplied
        then the resulting matrix is a*d and for ever eleent in the resultant
        matrix to form it takes the innder dimension times multiplications which is b.
        so the cost of the matrix chain here is a*d gives the totla element and b is
        the times each elements in the resultant matrix needed to mutliply.
        a*b*d is the cost. 
        */
        
        int n = arr.length;
        int [][] dp = new int[n][n];
        for(int i=0;i<n;i++) Arrays.fill(dp[i],-1);
        return solve(arr,dp,1,n-1);
    }
    static public int solve(int [] arr,int [][]dp,int i,int j){
        if(i==j) return 0;
        if(dp[i][j]!=-1) return dp[i][j];
        int min = Integer.MAX_VALUE;
        for(int k=i;k<j;k++){
            int cMin = solve(arr,dp,i,k)+solve(arr,dp,k+1,j)+
                        arr[i-1]*arr[k]*arr[j];
            min = Math.min(cMin,min);
        }
        dp[i][j] = min;
        return dp[i][j];
    }
}


