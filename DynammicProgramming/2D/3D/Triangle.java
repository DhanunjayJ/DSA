class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int [][] dp = new int[n][n];
        //since the probelm contains the negative path sum. we need
        //to add Integer.MAX_VALUE. instead of -1.
        for(int i=0;i<n;i++) Arrays.fill(dp[i],Integer.MAX_VALUE);
        return helper(0,0,triangle,dp);
    }
    public int helper(int i,int j,List<List<Integer>> triangle ,int [][] dp){
        if(i==triangle.size()-1) return triangle.get(i).get(j);
        if(dp[i][j]!=Integer.MAX_VALUE) return dp[i][j];
        int nextCol = helper(i+1,j,triangle,dp);
        int nextColp1 = helper(i+1,j+1,triangle,dp);
        dp[i][j] = Math.min(nextCol,nextColp1)+triangle.get(i).get(j);
        return dp[i][j];
    }
}


// Bottom Up
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int [] dp = new int[n];
        
        for(int i=0;i<n;i++){
            dp[i] = triangle.get(n-1).get(i);
        }

        //we going from the bottom to top.
        //for that i we need to check the already calcuated i,i+1 values
        //in the next row. 
        //when calcuating for the current row we only need the row
        // that is on one step ahead. so one row is enough here. 
        for(int i=n-2;i>=0;i--){
            for(int j=0;j<=i;j++){
                dp[j] = Math.min(dp[j],dp[j+1])+triangle.get(i).get(j);
            }
        }

        return dp[0];

    }
}