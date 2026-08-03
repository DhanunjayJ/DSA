public class WildCardMatching {
    class Solution {
    public boolean isMatch(String s, String p) {
        int n = s.length();
        int m = p.length();
        boolean [][] dp = new boolean[n+1][m+1];
        //base cases
        //both empty stirngs will always be true
        dp[0][0] = true;
        //if p is empty and s is not empty then it will always be false
        for(int i=1;i<=n;i++) dp[i][0] = false;
        //if s is empty and p is only true if it has a range of ** 
        // if it has a* then it is not valid mathc. it should have 
        //* or sequcne of ** for that we check the current string and previous
        for(int j=1;j<=m;j++){
            if(p.charAt(j-1)=='*' && dp[0][j-1]){
                dp[0][j] = true;
            }
        }

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                char c1 = s.charAt(i-1);
                char c2 = p.charAt(j-1);
                if(c1==c2 || c2=='?'){
                    dp[i][j] = dp[i-1][j-1];
                }else if(c2=='*'){
                    //we check of sequence of characters or
                    //emptying it by doing j-1
                    dp[i][j] = dp[i-1][j] || dp[i][j-1];
                }else{
                    dp[i][j] = false;
                }

            }
        }

        return dp[n][m];
    }
}
}
