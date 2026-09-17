class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        //Prefix DP + Sliding window
        int sp = 0;
        long [] prefix = new long[n];
        prefix[0] = Integer.MAX_VALUE;
        int currSum = 0;
        //Store the min window length from the prefix 
        //use the sliding window when we have the sum > target 
        //to reduce the start point.
        //prefix[i] = minimum length of the subarray with sum == target untill i-1. 
        for(int ep=1;ep<n;ep++){
            //same as previous
            prefix[ep] = prefix[ep-1];
            //get the previous value
            int currVal = arr[ep-1];
            //add 
            currSum += currVal;
            //if more adjust the start point
            while(currSum>target){
                int spValue = arr[sp];
                currSum -= spValue;
                sp++;
            }
            //if sum == target then update the len if it is less. 
            if(currSum==target){
                int len = ep-sp;
                prefix[ep] = Math.min(prefix[ep],len);
            }
        }
       
        int ep = n-1;
        long [] suffix = new long[n];
        //same as prefix but , 
        // suffix[i] = minimum length of the subarray where sum == target at i and till n-1. 
        // 
        suffix[n-1] = Integer.MAX_VALUE;
        currSum = 0;
        for(sp=n-1;sp>=0;sp--){
            suffix[sp] = sp==n-1 ? Integer.MAX_VALUE : suffix[sp+1];
            currSum += arr[sp];
            while(sp<ep && currSum>target){
                int epValue = arr[ep];
                currSum -= epValue;
                ep--;
            }
            if(currSum==target){
                int len = ep-sp+1;
                suffix[sp] = Math.min(suffix[sp],len);
            }
        }
        //now we iterate and store the minlen value. 
        long minLen = Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            minLen = Math.min(minLen,prefix[i]+suffix[i]);
        }
        //return the minLen value value is actaully present. 
        return (minLen>=Integer.MAX_VALUE) ? -1 : (int)minLen;
    }
}