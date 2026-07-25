class Solution {
    public int cutRod(int[] price) {
        // code here
        /*
        
        given rod of lengthn n,
        array with price and 
        we need to cut in suchway taht maxmizes the 
        price.
        
        so, dp, state. i,i+1,i+2.
        pick,no pick, and pick and stay
        what uniqey defined a state
        i value
        n length too.
        we can only pick tll n length. size.
        
        so, dp state will ne dp(i,n);
        */
        
        int n = price.length;
        int [][] dp = new int[n][n+1];
        for(int i=0;i<n;i++) Arrays.fill(dp[i],-1);
        return helper(price,dp,0,n);
    }
    public int helper(int [] price,int [][]dp,int i,int n){
        if(i==price.length || n==0){
            return 0;
        }
        if(dp[i][n]!=-1) return dp[i][n];
        
        int pick = 0;
        
        if(i+1<=n)
        pick = helper(price,dp,i,n-i-1)+price[i];
        
        int nopick = helper(price,dp,i+1,n);
        
        dp[i][n] = Math.max(pick,nopick);
        
        return dp[i][n];
    }
}

//itetive way like unbounded knapsack

class Solution {
    public int cutRod(int[] price) {
        int n = price.length;
        
        // dp[j] will store the maximum profit for a rod of length 'j'
        int[] dp = new int[n + 1];
        
        // i represents the index in the price array. 
        // The length of the piece is (i + 1).
        for (int i = 0; i < n; i++) {
            int pieceLength = i + 1;
            
            // Traverse forwards because we can reuse the same piece length multiple times
            for (int j = pieceLength; j <= n; j++) {
                dp[j] = Math.max(dp[j], dp[j - pieceLength] + price[i]);
            }
        }
        
        return dp[n];
    }
}