class Solution {
    public int longestOnes(int[] nums, int k) {

        // we can have up to k zeros in the window. 
        // if a window has atmost k zero that is the valid window to consider. 
        // so we use two pointers start and end, untill the window is valid.
        // when the window becomes invalid we increment the start untill it becomes invalid;
        // we always maintain the length of the the longest window in a vairbale.

        int maxLen = 0;
        int start = 0;
        int validZeros = 0;
        int n = nums.length;

        for(int end=0;end<n;end++){

            if(nums[end]==0){
                validZeros++;
            }

            while(validZeros>k){
                if(nums[start]==0){
                    validZeros--;
                }
                start++;
            }

            maxLen = Math.max(maxLen,end-start+1);
        }

        return maxLen;
    }
}