class Solution {
    public int minRemovals(int[] nums, int target) {
        // doing backtracking.
        // 2^40 -> more give TLE
        // we try to make them in two parts.
        //do backtracking on the left and right.
        // using the xor property of a^b = target
        // b = target^a;
        //we will check for the b in right.
        // to do that we need an hashmap for the each entry.
        int n = nums.length;
        HashMap<Integer,Integer> hmr = new HashMap<>();
        HashMap<Integer,Integer> hml = new HashMap<>();
        int mid = n/2;
        generate(nums,0,mid,hml,0,0);
        generate(nums,mid,n,hmr,0,0);
        int maxEle = -1;
        for(int rightXor : hmr.keySet()){
            int rightCount = hmr.get(rightXor);
            int leftReqXor = rightXor^target;
            if(hml.containsKey(leftReqXor)){
                maxEle = Math.max(hml.get(leftReqXor)+rightCount,maxEle);
            }
        }
        return maxEle == -1 ? -1 : nums.length-maxEle;
    }
    public void generate (int [] nums,int currIdx,int end,HashMap<Integer,Integer> hm,int xor,int countEle){
        if(currIdx==end) {
            //if the xor is already there in the map, then we try to keep the one
            //which has included the maximum numbers.
            hm.put(xor,Math.max(hm.getOrDefault(xor,-1),countEle));
            return;
        }
        //we want to stor the all the xor possbilites when it reaches end?
         
        //incldue the currelement.
        generate(nums,currIdx+1,end,hm,xor^nums[currIdx],countEle+1);
        //exclude
        generate(nums,currIdx+1,end,hm,xor,countEle);
        
    }
}