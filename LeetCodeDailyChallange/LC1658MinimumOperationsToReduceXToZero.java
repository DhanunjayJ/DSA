class Solution {
    //sliding window approach
    public int minOperations(int[] nums, int x) {
        int sum = 0;
        for(int num : nums){
            sum += num;
        }
        int n = nums.length;
        int target = sum - x;
        //if target == 0 then we have to use all ele to make x zero.
        if(target==0) return n;
        //if target < 0 we can't make x zero.
        if(target<0) return -1;
        // now we have to find out the maximum length subarray
        // that is equals to target.

        int maxLen = 0;
        int sp = 0;
        int currSum = 0;
        
        for(int ep=0;ep<n;ep++){
            currSum += nums[ep];
            while(sp<=ep && currSum>target){
                currSum -= nums[sp];
                sp++;
            }
            if(currSum==target){
                maxLen = Math.max(ep-sp+1,maxLen);
            }
        }
        
        return n-maxLen;
    }
}