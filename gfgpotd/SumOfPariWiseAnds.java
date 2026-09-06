class Solution {
    public long pairAndSum(int[] arr) {
        // code here
        int n = arr.length;
        long totalSum = 0;
        
        for(int i=0;i<32;i++){
            long count = 0;
            //counting the set bits. 
            //becase and operations only result in one
            //when the set bits are there.
            for(int j=0;j<n;j++){
                if((arr[j]&(1<<i))!=0){
                    count++;
                }
            }
            //once we find out how any set bits are 
            //there we have to find how many pairs
            //can be formed with the set bits present.
            // ncr //n(n-1)/2;
            if(count>=2){
                long pairs = (count*(count-1))/2;
                //since the AND operation can only result
                //in one when both bits are set. 
                //we need to find out the contribution of this sum
                totalSum += (pairs*(1L<<i));
            }
        }
        return totalSum;
    }
}