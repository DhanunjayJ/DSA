class Solution {
    static int countWays(String s) {
        // code here
        int n = s.length();
        //dp[i][j][0] = false ways
        //1 = true ways. 
        int [][][] dp = new int[n][n][2];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Arrays.fill(dp[i][j],-1);
            }
        }
        return helper(dp,s,0,n-1,1);
    }
    static int helper(int[][][]dp,String s,int i,int j,int isTrue){
        
        if(i>j) return 0;
        
        if(i==j){
            //we have to check if that is true or not
           if(isTrue==1) return s.charAt(i)=='T' ? 1 : 0;
           else return s.charAt(i)=='F' ? 1 : 0;
        }
        
        if(dp[i][j][isTrue]!=-1) return dp[i][j][isTrue];
        
        long ways = 0;
        
        for(int k=i+1;k<j;k++){
            
            long LT = helper(dp,s,i,k-1,1);
            long LF = helper(dp,s,i,k-1,0);
            long RT = helper(dp,s,k+1,j,1);
            long RF = helper(dp,s,k+1,j,0);
            
            char op = s.charAt(k);
            
            if(op=='&'){
                if(isTrue==1){
                    ways += (LT*RT);
                }else{
                    ways += ((LT*RF)+(LF*RT)+(LF*RF));
                }
            }else if(op=='|'){
                if(isTrue==1){
                    ways += ((LT*RF)+(LF*RT)+(LT*RT));
                }else{
                    ways += (LF*RF);
                }
            }else if(op=='^'){
                if(isTrue==1){
                    ways += ((LT*RF)+(LF*RT));
                }else{
                    ways += ((LT*RT)+(LF*RF));
                }
            }
        }
        
        return dp[i][j][isTrue] = (int) ways;
    }
}