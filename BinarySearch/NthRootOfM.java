public class NthRootOfM {
    class Solution {
    public int nthRoot(int n, int m) {
        // code here
        int low = 0;
        int high = m;
        while(low<=high){
            int mid = (low+high)/2;
            long powerVal = power(mid,n);
            if(powerVal==m){
                return mid;
            }else if(powerVal<m){
                low = mid+1;
            }else{
               high = mid-1;
            }
        }
        return -1;
    }
    public long power(int x,int n){
        long val = 1;
        for(int i=0;i<n;i++){
            val*=x;
        }
        return val;
    }
}
}
