class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

       int n = nums1.length;
       int m = nums2.length;

       if(n>m) return findMedianSortedArrays(nums2,nums1);

       // if the n+m is even or odd
       // 12 13 the medina for 12 is 2 vales and for oddd one.
       // what position to find out?
       // n+m+1/2 or n+m/2
       //12 -> 6 13 -> 7

       int median = (n+m+1)/2;

       //here are we finding the cuts.
       //which cut is givin the left1<=right2
       //and left2<=right1.
       int low = 0;
       int high = n;

       while(low<=high){

        int mid = (low+high)/2;

        int mid2 = median-mid;

        //if the cut is at the end or begining left will be min and right will be max
        int left1 = (mid==0) ? Integer.MIN_VALUE : nums1[mid-1];
        int right1 = (mid==n) ? Integer.MAX_VALUE : nums1[mid];
        int left2 = (mid2==0) ? Integer.MIN_VALUE : nums2[mid2-1];
        int right2 = (mid2==m) ? Integer.MAX_VALUE : nums2[mid2];

        if(left1<=right2 && left2<=right1){
            if((n+m)%2==0){
                // if minimum we want the max of both lefts and min of the both max.
                return (Math.max(left1,left2) + Math.min(right1,right2))/2.0;
            }else{
                return Math.max(left1,left2)*1.0;
            }
        }else if(left1>right2){
            //then we have to shrink the left1
            high = mid-1;
        }else{
            low = mid+1;
        }
       }

       return 0.0;
    }
}