class Solution {
    public int minimumDeletions(int[] nums) {

       int minIdx = 0;
       int maxIdx = 0;

       int n = nums.length;

       for(int i=0;i<n;i++){
        if(nums[minIdx]>nums[i]){
            minIdx = i;
        }
        if(nums[maxIdx]<nums[i]){
            maxIdx = i;
        }
       } 

       // the first senario. 
       // all elements delete from the left untill both the elements are deleted.

       int minOfBoth = Math.min(minIdx,maxIdx);
       int maxOfBoth = Math.max(maxIdx,minIdx);

       //first senario where both the elements neeed to be deleted from the left;
       int leftDelete = maxOfBoth+1;
       //second from right. 
       int rightDelete = n-minOfBoth;
       //combine both 
       int combineDelete = minOfBoth+1+n-maxOfBoth;
       //return the minimum.
       return Math.min(leftDelete,Math.min(rightDelete,combineDelete));
    }
}