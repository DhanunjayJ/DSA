class Solution {
    public boolean check(int[] nums) {
     //if the array is sorted and rotated.
     //then there will be case of arr[i-1]>arr[i] at most once.
     //or none. we count the flip count;
     //and also along with that we also need to chceck
     //if rotated then start and edn of the array are
     //in this realation arr[0]>=arr[n-1] = case for the duplicates
     int flipCount = 0;
     for(int i=1;i<nums.length;i++){
        if(nums[i-1]>nums[i]) flipCount++;
        if(flipCount>1) return false;
     }   
     // if flipcount==0 return true if ==1 then check if this is true.
     return flipCount==0 ? true : nums[0]>=nums[nums.length-1];
    }
}