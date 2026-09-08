public class MinimumNumberOfDaysToMakeMBoquets {
    class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        int low = 1;
        int high = 1_000_000_000;
        while(low<=high){
            int mid = low+(high-low)/2;
            if(canMake(bloomDay,mid,m,k)){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return low==1000000001 ? -1 : low;
    }
    public boolean canMake(int [] f,int day,int m,int k){
        int cM = 0;
        int cF = 0;
        for(int i=0;i<f.length;i++){
            if(f[i]<=day){
                cF++;
            }else{
                cF=0;
            }
            if(cF==k){
                cM++;
                cF=0;
            }
        }
        return (cM>=m);
    }
}
}
