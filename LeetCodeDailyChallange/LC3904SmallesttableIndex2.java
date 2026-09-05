public class LC3904SmallesttableIndex2 {
    class Solution {
        // same code as the stable index one but, the constraints are strict
        // o(n2) approach wonn't work. 
        // so we use only sufffix min array for the in and max we get 
        //as we move toward right.
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        int [] Smin = new int[n];
        Smin[n-1] = nums[n-1];
        for(int i=n-2;i>=0;i--){
            Smin[i] = Math.min(Smin[i+1],nums[i]);
        } 
        int pMax = 0;
        for(int i=0;i<n;i++){
            pMax = Math.max(pMax,nums[i]);
            if(pMax-Smin[i]<=k) return i;
        }
        return -1;
    }
}
}
