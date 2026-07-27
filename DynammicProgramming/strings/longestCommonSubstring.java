import java.util.Arrays;

public class longestCommonSubstring {
    class Solution {
    public int longCommSubstr(String s1, String s2) {
        // code here
        int n = s1.length();
        int m = s2.length();
        
        int [][] dp = new int [n+1][m+1];
        
        int max = 0;
        
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(s1.charAt(i-1)==s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                    max = Math.max(dp[i][j],max);
                }else{
                    dp[i][j] = 0;
                }
            }
        }
        
        return max;
    }
}

class Solution {
    int max;
    public int longCommSubstr(String s1, String s2) {
        // code here
        max = 0;
        int n = s1.length();
        int m = s2.length();
        int [][] dp = new int[n+1][m+1];
        for(int i=0;i<n;i++) Arrays.fill(dp[i],-1);
        helper(dp,s1,s2,0,0);
        return max;
    }
    public int helper(int [][] dp,String s1,String s2,int i,int j){
        if(i==s1.length() || j==s2.length()) return 0;
        if(dp[i][j]!=-1) return dp[i][j];
        
        //uncondtionally branch and check.
        
        helper(dp,s1,s2,i+1,j);
        helper(dp,s1,s2,i,j+1);
        
        if(s1.charAt(i)==s2.charAt(j)){
            dp[i][j] = helper(dp,s1,s2,i+1,j+1)+1;
            max = Math.max(max,dp[i][j]);
        }else{
             dp[i][j] = 0;   
        }
        return dp[i][j];
    }
    
}
}
