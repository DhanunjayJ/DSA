public class DeleteOpetarationsTwoString {
    class Solution {
    public int minDistance(String word1, String word2) {
        /*
        Find the longes common substring between the two and 
        substract the remaining values from the total lengths of the two
        if n1+n2 total sum  then substract the logest common substring *2 
        from the total length.

        Here we can delete the characeter at any palce so we need to consider
        the subsequence not substring
        */

        int n = word1.length();
        int m = word2.length();
        int [][] dp = new int[n+1][m+1];

        int max = 0;
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j]= Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }

        return n+m-2*dp[n][m];
    }
}
}
