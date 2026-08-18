class Solution {
    public int findKthNumber(int m, int n, int k) {
        /*
        to get how many values are <= mid.
        we could do this.
        Math.min(mid/i,n) 
        if mid/i is smaller we take it or n is smallet we take it
        this is how we count thee values.
        for i =1 we get more values than n, so limit it we get the min of the n and mid/i.
        */

        int low = 1;
        int high = m*n;
        while(low<=high){
            int mid = low+(high-low)/2;

            int count = 0;
            for(int i=1;i<=m;i++){
                count += Math.min(mid/i,n);
            }

            if(count>=k){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }

        return low;
    }

    
}