class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        /* Since we need to return number of subarraus who have odd vlaue ==k 
        we will first try to find out the number of subarrays who has the odd nos <=k
        then we subtract the val with <=k-1 we got the exact k ones
         */
         return atMostKOdd(nums,k) - atMostKOdd(nums,k-1);
    }
    public int atMostKOdd(int []nums,int k){
        int start = 0;
        int count = 0;
        for(int end=0;end<nums.length;end++){
            // decrement k if the num is odd
            if((nums[end]&1)==1) k--;
            //if the window has more than k odds then 
            // remove the extra odds
            while(k<0){
                if((nums[start]&1)==1) k++;
                start++;
            }
            //count all the subarrys which has odd values <=k
            count+= end-start+1;
        }
        return count;
    }
}