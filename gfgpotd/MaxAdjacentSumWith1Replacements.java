class Solution {
    public int maxDiffSum(int[] arr) {
        // code here
        int n = arr.length;
        int [][] dp = new int[n][2];
        for(int i=1;i<n;i++){
            //we have two chioces for the current index
            //unmodifed.
            //here dp[i][0] -> maximum if the current elemen is unmodified
            // dp[i][1] -> maximum if the current element is modifed. 
            //if current is unmodified then the we have to check the preivous
            //if modifed and stayed as it is. 
            dp[i][0] = Math.max(
                Math.abs(arr[i-1]-arr[i]) + dp[i-1][0],
                Math.abs(1-arr[i]) + dp[i-1][1]
                    );
            // same for the modified. 
            dp[i][1] = Math.max(
                    Math.abs(arr[i-1]-1) + dp[i-1][0],
                    Math.abs(1-1) + dp[i-1][1]
                    );
        }
        return Math.max(dp[n-1][0],dp[n-1][1]);
    }
}