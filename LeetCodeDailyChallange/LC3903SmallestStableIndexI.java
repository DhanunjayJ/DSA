class Solution {
    public int firstStableIndex(int[] nums, int k) {
        //n2 approach works sinc the length of the max array is only 100
        int n = nums.length;
        int maxTillNow = 0;
        for(int i=0;i<n;i++){
            maxTillNow = Math.max(nums[i],maxTillNow);
            int minFromI = Integer.MAX_VALUE;
            for(int j=i;j<n;j++){
                minFromI = Math.min(nums[j],minFromI);
            }
            if(maxTillNow-minFromI<=k) return i;
        }
        return -1;
        //o(n) approach // with o(n) space
    }
}

class Solution {
    public int firstStableIndex(int[] nums, int k) {
        /*
        Do prefix max and suffix min for each index and calculate the 
        instabily for each index. keep a boolean array that tells if the index
        is stable or not. 
        or just return the first stable index from the left.

        here using the suffix min is enough we can keep the prefix max as running max
        as wo move further.
        */
        
        int n = nums.length;
        
        int [] prefixMax = new int[n];
        int [] suffixMin = new int[n];

        prefixMax[0] = nums[0];
        for(int i=1;i<n;i++){
            prefixMax[i] = Math.max(prefixMax[i-1],nums[i]);
        }
        suffixMin[n-1] = nums[n-1];
        for(int i=n-2;i>=0;i--){
            suffixMin[i] = Math.min(suffixMin[i+1],nums[i]);
        }
        for(int i=0;i<n;i++){
            if(prefixMax[i]-suffixMin[i]<=k){
                return i;
            }
        }
        return -1;
    }
}

