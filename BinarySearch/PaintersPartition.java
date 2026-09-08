class Solution {
    //https://www.geeksforgeeks.org/problems/the-painters-partition-problem1535/1
    public int minTime(int[] arr, int k) {
        // code here
        long low = 0;
        long high = 0;
        // we split the work, so the low is the max.
        for(int num : arr){
            low = Math.max(num,low);
            high += num;
        }
        while(low<=high){
            long mid = low+(high-low)/2;
            if(canPaint(arr,k,mid)){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return (int) low;
    }
    public boolean canPaint(int [] arr,int k,long time){
        int painter = 1;
        int currTime = 0;
        for(int b : arr){
            if(currTime+b>time){
                currTime = 0;
                painter++;
            }
            currTime+=b;
        }
        //if lessthan k painters cand do the work in the bounds of the time, then we can further split it if we want
        //we have excess painters.
        //if taking this time, we need more than k painters then the time is not valid. 
        return painter<=k;
    }
}