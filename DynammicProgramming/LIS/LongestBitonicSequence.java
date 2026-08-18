class Solution {
    public static int longestBitonicSequence(int n, int[] nums) {
        // code here
        int [] leftInc = new int[n];
        int [] rightInc = new int[n];
        Arrays.fill(leftInc,1);
        Arrays.fill(rightInc,1);
        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j] && leftInc[j]+1>leftInc[i]){
                    leftInc[i] = leftInc[j]+1;
                }
            }
        }
        
        for(int i=n-1;i>=0;i--){
            for(int j=n-1;j>i;j--){
                if(nums[i]>nums[j] && rightInc[j]+1>rightInc[i]){
                    rightInc[i] = rightInc[j]+1;
                }
            }
        }
        
        int maxLen = 0;
        
        for(int i=0;i<n;i++){
            //to have a valid bitonic sequcne we need to have both
            //>1
            if(leftInc[i]>1 && rightInc[i]>1){
                //remove the common peak value between the two.
                int bitonicLen = leftInc[i]+rightInc[i]-1;
                maxLen = Math.max(maxLen,bitonicLen); 
            }
        }
        
        return maxLen;
    }
}