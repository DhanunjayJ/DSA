class Solution {
    /*
    We can't directly find the number subarrays using the sliding window but we can find a 
    length of window whose sum is valid. so number os subarrays in that whose sum<=goal
    is end-start+1;
     */
    public int numSubarraysWithSum(int[] nums, int goal) {
        return atMostK(nums,goal) - atMostK(nums,goal-1);

    }
    public int atMostK(int [] nums,int goal){
        int count = 0;
        //maintain a window whole sum<=goal
        int start = 0;
        int currSum = 0;
        for(int end=0;end<nums.length;end++){
            currSum+=nums[end];
            while(start<=end && currSum>goal){
                currSum-=nums[start];
                start++;
            }
            //if the sum<=goal
            //add the values
            //If a window [start...end] is valid (sum <= goal),
            //then every subarray ending at end within that window is also valid.
            count += (end-start+1);
        }
        return count;
    }
}

//using hashmap

class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        HashMap<Integer,Integer> hm = new HashMap<>();
        int psum = 0;
        hm.put(0,1);
        int count = 0;
        for(int i=0;i<nums.length;i++){
            psum+=nums[i];
            if(hm.containsKey(psum-goal)){
                count+=hm.get(psum-goal);
            }
            hm.put(psum,hm.getOrDefault(psum,0)+1);
        }
        return count;
    }
}