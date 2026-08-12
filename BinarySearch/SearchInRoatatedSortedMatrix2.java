class Solution {
    public boolean search(int[] nums, int target) {
        int n = nums.length;
        int low = 0;
        int high = n-1;
        while(low<=high){
            int mid = (low+high)/2;
            if(nums[mid]==target) return true;
            //check if this thie case/
            // 3 1 2 3 3 3 3
            //if ths is the case then is equal to the left and right
            if(nums[mid]==nums[low] && nums[mid]==nums[high]){
                low++;
                high--;
                continue;
                //since the current mid is not valid.
            }
            //if left side is sorted then go left.
            //we do <= when the size of the array becomees lesstahn 2 then
            //the low<=mid
            if(nums[low]<=nums[mid]){
                //check if the left is sorted.
                if(nums[low]<=target && nums[mid]>target){
                    //if sorted go left, check if the target is presne tin that sorted part;
                    high = mid-1;
                }else{
                    //go right;
                    low = mid+1;
                }
            }else {
                //if right is sorted
                if(nums[mid]<target && nums[high]>=target){
                    //go right, if the target is present in the sorted part;;
                    low = mid+1;
                }else{
                    // else go left;
                    high = mid-1;
                }
            }
        }
        return false;
    }
}