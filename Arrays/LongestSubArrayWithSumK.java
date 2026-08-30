class Solution {
    public int longestSubarray(int[] arr, int k) {
        // code here
        HashMap<Integer,Integer> hm = new HashMap<>();
        int psum = 0;
        hm.put(0,-1);
        int maxLen = 0;
        for(int i=0;i<arr.length;i++){
            int num = arr[i];
            psum+=num;
            if(hm.containsKey(psum-k)){
                maxLen = Math.max(i-hm.get(psum-k),maxLen);
            }
            //since we want the maxlen, we keep the earliest one
            hm.putIfAbsent(psum,i);
        }
        return maxLen;
    }
}
