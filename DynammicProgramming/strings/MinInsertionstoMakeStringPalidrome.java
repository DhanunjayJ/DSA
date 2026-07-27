public class MinInsertionstoMakeStringPalidrome {
    class Solution {
    public int minInsertions(String s) {
        /*
        find the longest common subsqeuce between the two string
        normla and reverse strig and subtract it from the total length
        Why this approach worksTo make a string a palindrome with the minimum number of insertions, you want to keep as many existing characters in their relative order as possible.The characters that are already part of a palindromic pattern don't need to be duplicated—this is simply the Longest Palindromic Subsequence (LPS).The LPS of a string $s$ is identical to the Longest Common Subsequence (LCS) between $s$ and its reversed version $s^R$.Any character not part of this longest palindromic subsequence will need a matching partner inserted.
        */
        int n = s.length();
        String r = new StringBuilder(s).reverse().toString();
        int [][] dp = new int[n+1][n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                char c1 = s.charAt(i-1);
                char c2 = r.charAt(j-1);
                if(c1==c2){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return n-dp[n][n];
    }
}
}
