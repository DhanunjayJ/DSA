class Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int [] dp = new int[n];
        int [] count = new int[n];
        Arrays.fill(dp,1);
        Arrays.fill(count,1);
        /*
        Here while finding the longest increasing subsequnce we also need to find out
        how many ways we could get it.
        */
        int maxLen = 1;
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    if(dp[j]+1>dp[i]){
                        //if it greater than the current ones then update the 
                        //current length and max len.
                        //get the count as same as count of the j
                        dp[i] = dp[j]+1;
                        maxLen = Math.max(dp[i],maxLen);
                        count[i] = count[j];
                    }else if(dp[j]+1==dp[i]){
                        //if the the number is greater but the count is same
                        //then we have found out annother way to get the lis.
                        //so we just add the current count with the count of the 
                        count[i] += count[j];
                    }
                }
            }
        }
        int ans = 0;
        //count how many ways we can recach the lis for each maxlen list index
        for(int i = 0;i<n;i++){
            if(dp[i]==maxLen){
                ans += count[i];
            }
        }
        return ans;
    }
}