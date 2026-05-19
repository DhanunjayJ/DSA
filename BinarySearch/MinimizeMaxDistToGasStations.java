class Solution {
    public double minMaxDist(int[] stations, int K) {
        // code here
        int n = stations.length;
        
        double low = 0.0;
        double high = stations[n-1]-stations[0];
        double ans = 0;
        
        while((high-low)>1e-7){
            
            double mid = low+(high-low)/2.0;
            
            if(isPossibleK(stations,K,mid)){
                ans = mid;
                high = mid;
            }else{
                low = mid;
            }
            
        }
        return ans;
    }
    
    public boolean isPossibleK(int [] stations,int K,double dist){
        int count = 0;
        for(int i=1;i<stations.length;i++){
            int gap = stations[i]-stations[i-1];
            count += ((gap-1e-9)/dist);
        }
        return count<=K;
    }
}
