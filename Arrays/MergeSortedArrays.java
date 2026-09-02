public class MergeSortedArrays {
    class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m-1;
        int p2 = n-1;
        int p3 = nums1.length-1;
        //why p2>=0 since the nums1 is already sorted 
        //once the length p2 is done the array will will be sorted after no changed needed. 
        while(p2>=0){
            //doing the p1 first check if it is with the bounds.
            //if it is then do it.
            if(p1>=0 && nums1[p1]>=nums2[p2]){
                nums1[p3] = nums1[p1];
                p1--;
            }else{
                nums1[p3] = nums2[p2];
                p2--;
            }
            p3--;
        }
    }
}
}
