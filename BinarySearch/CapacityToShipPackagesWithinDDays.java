public class CapacityToShipPackagesWithinDDays {
    class Solution {
    public int shipWithinDays(int[] weights, int days) {
        
        long low = 1;
        long high = 0;
        
        for(int wt:weights){
            low = Math.max(low,wt);
            high += wt;
        }

        while(low<=high){
            long mid = low+(high-low)/2;
            if(canShip(weights,days,mid)){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }

        return (int) low;
    }
    
    public boolean canShip(int [] weights,int days,long wt){
        long currDays = 1;
        long currWt = 0;
        for(int i=0;i<weights.length;i++){
            //count it when the weight exceed and set the weight to zero
            //so we don't consideer equal case. sepeartly 
            if(currWt+weights[i]>wt){
                currDays++;
                currWt=0;
            }
            currWt+=weights[i];
        }
        return currDays<=days;
    }
}
}
