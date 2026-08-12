class Solution {
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length-1;
        int min = 50001;
        /*

        here we are dividing the array into two parts.

        left sorted and right sorted.

        if left sorted from left...mid-1
        then mid is the left.
        get the current min and go the right. because htere is a possbibilty of 
        lower min on the right;

        if right is sorted. mid...right was sorted.
        go to left. and update the min and go to left for postential min
        on the left. 
        */
        while(low<=high){
            int mid = (low+high)/2;
            //if the left is sorted. 
            if(nums[low]<=nums[mid]){
                min = Math.min(nums[low],min);
                low = mid+1;
            // if the right is sorted.
            }else{
                min = Math.min(nums[mid],min);
                high = mid-1;
            }
        }
        return min;
    }
}