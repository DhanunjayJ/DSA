public class KoKoEatingBananas {
    class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        /*
        minimum bananas koko can eat is min of the all the piles
        and max is sum of all the piles.?
        no,max is the max pile. because for one hour we can't eat more than one pile.
        */
        int low = 1;
        int high = 1_000_000_000;
        while(low<=high){
            int mid = low+(high-low)/2;
            if(canEat(mid,piles,h)){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return low;
    }
    public boolean canEat(int b,int [] piles,int h){
        long cH = 0;
        for(int i=0;i<piles.length;i++){
            //ceil division
            cH += (piles[i] + b - 1) / b;
        }
        return (cH<=h);
    }
}
}
