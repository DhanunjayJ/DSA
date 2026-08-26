class Solution {
    public boolean predictTheWinner(int[] nums) {
        int n = nums.length;
        //at first the player one has two choices pick the n-1 or 0.
        //nums, range, current player,onesum,twosum
        if(n==1) return true;
        boolean pickZero = helper(nums,1,nums.length-1,2,nums[0],0);
        boolean pickLast = helper(nums,0,nums.length-2,2,nums[n-1],0);
        return pickZero || pickLast;
    }
    public boolean helper(int [] nums,int i,int j,int player,int oSum,int tSum){
        if(i>j){
            return oSum>=tSum;
        }
        // when player 2 turn comes he wants to let the player 1 lose, so he only return true when the both are 
        // true other wise if the player two wins in either of the cases it return as the player 1 will fail. since
        //both the player are try to win and playing optimally.
        if(player==2) return helper(nums,i+1,j,1,oSum,tSum+nums[i]) && helper(nums,i,j-1,1,oSum,tSum+nums[j]);
        else return helper(nums,i+1,j,2,oSum+nums[i],tSum) || helper(nums,i,j-1,2,oSum+nums[j],tSum);
    }
}
// Dp Appraoch

class Solution {
    public boolean predictTheWinner(int[] nums) {
        /*
        DP Appraoch. here we couldn't track the scores in a dp table, 
        here store the net score differance an player can get hen picking a subarray. 

        dp[i][j] = represetns teh maximum socre advantage the current player cna secure on thier opponent using only numbers from index i to j. 

        so, wehn the player comes to take a turan, picking from the left ro tirhgt
        if pick the left then tier nt score becomes 
        nums[i]-dp[i-1][j] //dp[i-1][j] is their maximum advantage.
        your Score - opponnet Score.
        if we pick the right then
        nums[j]-dp[i][j-1].
        get the max of both the values. because we want to win the game by maximing the score diff.  

        base case is when the len is one the maxdiff is the number itself.
        */

        int n = nums.length;
        int [][] dp = new int[n][n];
        for(int i=0;i<n;i++){
            dp[i][i] = nums[i];
        }
        for(int len=2;len<=n;len++){
            for(int i=0;i<=n-len;i++){
                int j = i+len-1;
                dp[i][j] = Math.max(nums[i]-dp[i+1][j],nums[j]-dp[i][j-1]);
            }
        }
        //if the diff is postive ore equal to zero then the player 1 wins
        return dp[0][n-1]>=0;
    }
}