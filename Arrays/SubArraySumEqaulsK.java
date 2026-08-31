public class SubArraySumEqaulsK {
    class Solution {
    public int subarraySum(int[] nums, int k) {
        /*
        Here we use the prefix sum and hashmap.

        we know whhat the subarray sum of the array starting at sp and ending at ep is
        psum[ep]-psum[sp-1] = k;
        so we need to find out how many of them.
        psum[sp-1] = psum[ep]-k;
        meaning that if we found psum[ep]-k that is equals to psum[sp-1] that means that subarray has sum equals to k. 
        we count that subarray as one. 
        this way we keep all subarray sums in to a hashmap.
        and count the subarrays. when sum == k is present/
        */

        HashMap<Integer,Integer> pSumCount = new HashMap<>();
        int pSum = 0;
        pSumCount.put(0,1); //there is only one way to get sum equal to zero.
        int count = 0;
        for(int num : nums){
            pSum += num;
            if(pSumCount.containsKey(pSum-k)){
                count += pSumCount.get(pSum-k);
            }
            pSumCount.put(pSum,pSumCount.getOrDefault(pSum,0)+1);
        }
        return count;
    }
}
}
