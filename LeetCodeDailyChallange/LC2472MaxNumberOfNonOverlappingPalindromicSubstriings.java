class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean [][] isPali = new boolean[n][n];
        for(int len=1;len<=n;len++){
            for(int i = 0;i<=n-len;i++){
                int j = i+len-1;
                if(s.charAt(i)==s.charAt(j)){
                    isPali[i][j] = (len<=2) || isPali[i+1][j-1];
                }
            }
        }
        //dp[i] -> reperents the number of non overlapping palidromic substrings
        //of length atleast k.
        //that can be selected from i to s[0...i-1]
        int [] dp = new int[n+1];
        for(int i=1;i<=n;i++){
            //don't select a plaindromic substring ending at i-1.
            //just keep it as it is. as the preivous ones.
            dp[i] = dp[i-1];
            //endurmerat the length of k values values from the index i 
            for(int j=0;j+k<=i;j++){
                if(isPali[j][i-1]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
        }
        return dp[n];
    }
}