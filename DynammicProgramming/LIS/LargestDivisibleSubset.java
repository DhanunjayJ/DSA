class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        /*

        Here we need to find out the answer array subset of the nums,
        such that every pair in the ans aarray shoul be divisble by eah other eithr one way. to do that 
        one values should be in powers of the base base like 
        two values should be two doule of the other. and 
        then 

        for that we need to sort the array. to get the values
        in the increasing order that is the only way to get the conditions mentioend.

        and then, we need to return the lnges one. 

        so we just do longest increains subsequcne but now with
        the % condition. which ensures the increasing. ones.

        and we do track of the parent in the array and then 
        get the final subset of the nums;

        */
        Arrays.sort(nums);
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        int [] dp = new int[n];
        int [] parent = new int[n];
        Arrays.fill(dp,1);
        Arrays.fill(parent,-1);
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i]%nums[j]==0 && dp[j]+1>dp[i]){
                   dp[i] = dp[j]+1;
                   parent[i] = j; 
                }
            }
        }
        int maxLen = 0;
        int maxIdx = 0;
        for(int i=0;i<n;i++){
            if(dp[i]>maxLen){
                maxLen = dp[i];
                maxIdx = i;
            }
        }
        int currIdx = maxIdx;
        while(currIdx!=-1){
            ans.add(nums[currIdx]);
            currIdx = parent[currIdx];
        }
        Collections.reverse(ans);
        return ans;
    }
}