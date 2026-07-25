package DynammicProgramming.Subsequences;

public class paritionEqualSubsetSum {
    class Solution {
    public boolean canPartition(int[] nums) {
        /*
        we need to check if we can parition given array in to two half
        of eqaual sum elements. 

        so we pick and not pick an ement to be in the eqaul half. 

        we could maintin two things here running of picked elemnt , 
        sum of non picked elements.

        then dp state would be d[i][pickedsum][nopickedsum]

        but we can optimize this.

        if we ge tthe sum of the total array upfront. then
        we could simply cehck for each picked element sum is it equal to 
        == total sum - picked sum.

        we could even optimize this to, picked elemnt sum == totalsum/2;

        if totalsum is odd we return false. we cna't split a sum that is having the
        sum as odd.
        */

        int sum = 0;
        for(int num : nums) sum += num;
        if((sum&1)==1) return false;
        int n = nums.length;
        Boolean [][] dp = new Boolean[n][sum+1];
        int target = sum/2;
        //not the problem becomes the find subsubseque with target sum.
        return helper(dp,0,target,nums);
    }
    public boolean helper(Boolean [][] dp,int i,int k,int [] nums){
        if(k==0) return true;
        //this case only works here since there is not neagtive numbers.
        if(k<0) return false;
        if(i>=nums.length) return false;
        if(dp[i][k]!=null) return dp[i][k];
        dp[i][k] = helper(dp,i+1,k-nums[i],nums) || helper(dp,i+1,k,nums);
        return dp[i][k];
    }
}
}
