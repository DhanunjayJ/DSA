package greedy;

import java.util.Arrays;

public class FractionalKnapSack {

    class Item {
    double val;
    double wt;
    double ratio;
    
    Item(double val,double wt){
        this.val = val;
        this.wt = wt;
        this.ratio = (double) val/wt;
    }
}


class Solution {
    public double fractionalKnapsack(int[] val, int[] wt, int capacity) {
        // code here
        int n = val.length;
        
       
        Item [] items = new Item[n];
        
        for(int i=0;i<n;i++){
            items[i] = new Item(val[i]*1.0,wt[i]*1.0);
        }
        
        Arrays.sort(items,(a,b) -> Double.compare(b.ratio,a.ratio));
        
        double maxVal = 0;
        
        for(int i=0;i<n;i++){
            
            double fval = items[i].val;
            double fwt = items[i].wt;
            double ff = items[i].ratio;
            
            if(fwt<=capacity){
                maxVal += fval;
                capacity -= fwt;
            }else{
                maxVal += (ff*capacity);
               break;
            }
        }
        return maxVal;
    }
}
    
}
