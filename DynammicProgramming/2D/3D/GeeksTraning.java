class Solution {
    public int maximumPoints(int mat[][]) {
        // code here
        /*
        The top down approach. 
        
        if we start from the day n-1 then the max will be eqaul to the 
        what ever the max till now. 
        */
        int n = mat.length;
        int [][] dp = new int[n][4];
        for(int i=0;i<n;i++){
            Arrays.fill(dp[i],-1);
        }
        return helper(n-1,3,dp,mat);
    }
    public int helper(int day,int lastChoice,int [][] dp,int [][] mat){
        //if this hte lastchoice what is the maximum. we could get.
        if(dp[day][lastChoice]!=-1) return dp[day][lastChoice];
        
        if(day==0){
            int max = 0;
            for(int i=0;i<3;i++){
                //since we can't chose the same which we chose the next day.
                if(i!=lastChoice){
                    max = Math.max(mat[day][i],max);
                }
            }
            dp[0][lastChoice] = max;
            return max;
        }
        
        //if last choice is x then we check for the values
        //that are not x now. and find the max for the current day.
        //we put that in the mat[day][lastchoice] because last choice effect
        //the current day max.
        int max = 0;
        for(int i=0;i<3;i++){
            if(i!=lastChoice){
                //if the lastchoice is that then if we should chose it, we chose othere
                //we chose to do i which is not last choice
                //and call for the next one which is not chpsing i wha tis the max
                max = Math.max(max, mat[day][i] + helper(day-1,i,dp,mat));
            }
        }
        
        return dp[day][lastChoice] = max;
    }
}