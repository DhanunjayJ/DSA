class Solution {
    public int splitArray(int[] nums, int k) {
        long low = 0;
        long high = 0;
        for(int num : nums){
            low = Math.max(low,num);
            high += num;
        }
        while(low<=high){
            long mid = low+(high-low)/2;
            if(canSplit(nums,k,mid)){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return (int) low;
    }
    public boolean canSplit(int [] nums,int k,long mid){
        int currSum = 0;
        int seg = 1;
        for(int num : nums){
            if(num+currSum>mid){
                currSum = 0;
                seg++;
            }
            currSum+=num;
        }
        return seg<=k;
        /*
        
        The condition is seg <= k because if you can split the array into fewer than or equal to k subarrays without exceeding the sum limit mid, you can always split it into exactly k subarrays without exceeding mid.
        
        Conversely, if seg > k, you are forced to use more than k parts, meaning mid is too small and strictly impossible.

        */
    }
}