//https://leetcode.com/problems/construct-uniform-parity-array-ii/description/

class Solution {
    public boolean uniformArray(int[] nums1) {
        // we want the values such tat j!=i and also nums1[i] = nums2[j] >=1 
        // so to make it always greater than one we need smallest number that is less than
        // the current one. 
        //to get the smallest number we sort.
        // even - odd = odd;
        // odd - odd = even;
        // anything - even = same parity.
        // so when we have the first element odd, which means is that
        // even if we get the even element after the first element we can change it to odd. 
        // by subtracting the smallest odd element.
        // but if the first one is even, we can't change the smallest one odd because the answer 
        // should be postive. so the first elemtn decides. what the whole array should be. 
        // if the first ele is even then we can say that, we need to make all the elements even.
        // to make all the even we need odd, but we can't have one. so when we find a odd in the smallest
        // even number array then we just return false;
        Arrays.sort(nums1);
        if(nums1[0]%2!=0) return true;
        for(int i:nums1){
            if(i%2!=0){
                return false;
            }
        }
        return true;
    }
}