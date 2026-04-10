class Solution {
    public int maxScore(int[] cardPoints, int k) {
        /* If we try to find the ciruclar array with maximum of size k 
        we will reach values that can't be reched and reutrn the maximum. so instead. of the maximum. 
        
        The window that is in the middle was continous and fixed size of length n-k. so we do that. ?? 

        we need to find the minimum window of size n-k. and subtract that minimum sum from the totalSum. 
        */
        int n = cardPoints.length;
        //here taking th minSum.
        long minSum = Integer.MAX_VALUE;
        //the fixed size length
        int minWLen = n-k;
        long sum = 0;
        int start = 0;
        for(int end=0;end<n;end++){
            //if the window is greater then we just increment the start pointer.
            if(end-start+1>minWLen){
                sum -= cardPoints[start];
                start++;
            }
            //add the end
            sum+=cardPoints[end];
            // here the only if the if the window is 4 we only consider the window sum if end is 3 or greater. 
            if(end>=minWLen-1){
                minSum = Math.min(sum,minSum);
            }
        }
        long tSum = 0;
        for(int points:cardPoints){
            tSum += points;
        }
        return (int)(tSum-minSum);
    }
}