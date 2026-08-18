class Solution {
    public int findMin(int[] nums) {
        //finding minimum in roatated sorted array 2
        /*
        here we do the same thing as rotated sorted array 2,
        but we find out the min values by checking which one is min.
        and once we ge tthe min of the current sorted part.
        then we go tht enext part and chck if that part min repleases the 
        current min. 
        */

        int low = 0;
        int high = nums.length-1;
        int min = 50001;
        while(low<=high){
            int mid = (low+high)/2;
            //check if there is conflict.
            //ans alos we have to check if the mid could be the smallest
            if(nums[mid]==nums[low] && nums[high]==nums[mid])
            {
                min = Math.min(nums[mid],min);
                low++;
                high--;
                continue;
            }

            if(nums[low]<=nums[mid]){
                min = Math.min(min,nums[low]);
                low = mid+1;
            }else{
                //to check mid also we take the mid as samllest here.
                min = Math.min(min,nums[mid]);
                high = mid-1;
            }
        }
        return min;
    }
}