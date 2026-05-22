class Solution {
    /*
    Here insead of checking trhoguth the we start from teh zero . to avoid the cyclic depency and infiniate loop
    */
    public int[][] updateMatrix(int[][] mat) {

        int n = mat.length;
        int m = mat[0].length;

        Queue<int[]> q = new LinkedList<>();

        int [][] dp = new int[n][m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(mat[i][j]==0){
                    q.add(new int[]{i,j});
                    dp[i][j] = 0;
                }else{
                    dp[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        int [][] dirs = {{1,0},{0,1},{-1,0},{0,-1}};

        while(!q.isEmpty()){
            int [] rem = q.remove();
            for(int i=0;i<dirs.length;i++){

                int nrow = dirs[i][0]+rem[0];
                int ncol = dirs[i][1]+rem[1];

                if(nrow>=0 && ncol>=0 && ncol<mat[0].length && nrow<mat.length && dp[nrow][ncol]>dp[rem[0]][rem[1]]+1){
                    dp[nrow][ncol] = dp[rem[0]][rem[1]]+1;
                    q.add(new int[]{nrow,ncol});
                }
            }
        }
        return dp;
    }
}