class Solution {
    public int minCost(int n, int[] cuts) {

        /*
        We add 0 and n as padding because the cost of making a cut is equal to the length of the segment being cut. If we only used the given cuts array, we couldn't easily calculate the length of segments at the boundaries—like before the first cut or after the last cut—without writing extra if-else edge cases.By adding 0 (the start of the stick) and n (the end of the stick) and sorting the array, every sub-stick segment is fully defined by two indices, i and j. This allows us to: Calculate the cost of any segment as simply cuts[j] - cuts[i].Define base cases cleanly: when j = i + 1, there are no cut points between them, so the cost is 0.
        */

        int m = cuts.length;
        int [] newCuts = new int[m+2];
        
        newCuts[0] = 0;
        newCuts[m+1] = n;

        Arrays.sort(cuts);
        for(int i=0;i<m;i++){
            newCuts[i+1] = cuts[i];
        }

        int totalSize = m+2;

        int [][] dp = new int[totalSize][totalSize];
        for(int i=0;i<totalSize;i++) Arrays.fill(dp[i],-1);
        return helper(newCuts,dp,0,totalSize-1);
    }
    
    public int helper(int [] cuts,int [][] dp,int i,int j){
        //cut will not be possible.
        if(j-i<=1) return 0;

        if(dp[i][j]!=-1) return dp[i][j];

        int minCost = Integer.MAX_VALUE;

        //try to cut the stick from i+1 to j-1 values.
        //then try check the sizes of it. 
        // the cost of each cut is the cut[j] - cut[i]
        
        for(int k=i+1;k<j;k++){
            int currentCost = helper(cuts,dp,i,k) + helper(cuts,dp,k,j) +
                                cuts[j]-cuts[i];
            minCost = Math.min(minCost,currentCost);
        }

        return dp[i][j] = minCost;
    }
}

