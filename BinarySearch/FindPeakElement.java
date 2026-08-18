class Solution {
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        //edgecases
        if(n==1) return 0;
        //if 0 is greater than 1 or n-1 is greater than n-2 then those are peaks.
        if(nums[0]>nums[1]) return 0;
        if(nums[n-1]>nums[n-2]) return n-1;
        
        int low = 1;
        int high = n-2;

        while(low<=high){
            int mid = (low+high)/2;
            //check for the postienal peak/
            if(nums[mid]>nums[mid-1] && nums[mid]>nums[mid+1]){
                return mid;
            }
            //if left is greater go to left.
            if(nums[mid]<nums[mid-1]){
                high = mid-1;
            }else{
                //or elase go to the right
                low = mid+1;
            }
        }
        return -1;
    }
}