class Solution {
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length-1;
        while(low<=high){
            int mid = (low+high)/2;
            if(nums[mid]==target) return mid;
            //check if the left part is sorted.
            //this should be equals to low<=mid;
            //low==mid when the left shrinks.
            else if(nums[low]<=nums[mid]){
                //check if the target lies int he range of low and midl
                if(nums[low]<=target && nums[mid]>target){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }else {
                //the right side was sorted
                if(nums[mid]<target && target<=nums[high]){
                    low = mid+1;
                }else{
                    high = mid-1;
                }
            }
        }
        return -1;
    }
}