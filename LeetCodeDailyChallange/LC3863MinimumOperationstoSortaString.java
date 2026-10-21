class Solution {
    public int minOperations(String s) {
        int n = s.length();
        boolean sorted = true;
        int firstMin = 0, lastMax = 0;
        for(int i=1;i<n;i++){
            if(s.charAt(i-1)>s.charAt(i)){
                sorted = false;
            }
            if(s.charAt(i)<s.charAt(firstMin))firstMin = i;
            if(s.charAt(i)>=s.charAt(lastMax))lastMax = i;
        }
        //if the arrray is osrted then return 0
        if(sorted) return 0;
        if(n==2) return -1;
        //if the min is at the first index or max is at the alst index 
        //then we we only need 1 operatoins.
        if(firstMin==0 || lastMax==n-1) return 1;
        //or else if the firstMin is in the middle ohter than first 
        //or the max is at the middle other than that right most.
        //then we need two operations
        if(firstMin<n-1 || lastMax>0) return 2;
        //if the firstMin is at the last index and the lastMax is at the first index then we need three op. 
        return 3;
    }
}