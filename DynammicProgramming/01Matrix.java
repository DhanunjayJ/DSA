class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int INF = m*n;

        int [][] dist = new int[n][m];

        //first pass dp
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(mat[i][j]==1){
                    int top = (i>0) ? dist[i-1][j] : INF;
                    int left = (j>0) ? dist[i][j-1] : INF;
                    dist[i][j] = Math.min(top,left)+1;
                }
            }
        }

        //second pass
        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=0;j--){
                if(mat[i][j]==1){
                    int bottom = (i<n-1) ? dist[i+1][j] : INF;
                    int right = (j<m-1) ? dist[i][j+1] : INF;
                    //pick the best of the bottom, right and previous pass
                    dist[i][j] = Math.min(dist[i][j],Math.min(right,bottom)+1); 
                }
            }
        }

        return dist;

    }
}