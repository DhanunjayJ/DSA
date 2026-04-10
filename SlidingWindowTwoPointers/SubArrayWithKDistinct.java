class Solution {
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostk(nums,k) - atMostk(nums,k-1);
    }
    public int atMostk(int [] nums,int k){
        HashMap<Integer,Integer> hm = new HashMap<>();
        int start = 0,end;
        int count = 0;
        for(end=0;end<nums.length;end++){
            hm.put(nums[end],hm.getOrDefault(nums[end],0)+1);
            while(hm.size()>k){
                hm.put(nums[start],hm.getOrDefault(nums[start],0)-1);
                if(hm.get(nums[start])==0) hm.remove(nums[start]);
                start++;
            }
            //we are counting every valid subarray that ends specifically at the current end index.
            count += (end-start+1);
        }
        return count;
    }
}