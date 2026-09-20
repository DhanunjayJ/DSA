class Solution {
    public int largestSubsquare(char mat[][]) {
        // code here
        int n = mat.length;
        int [][] row = new int[n+1][n+1];
        int [][] col = new int[n+1][n+1];
        int max = 0;
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(mat[i-1][j-1]=='X'){
                    row[i][j] = row[i][j-1]+1;
                    col[i][j] = col[i-1][j]+1;
                }
            }
        }
        
        //we treat every value as a bottom right corner
         for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                //we get the minimum possible square that can be 
                //formed from each cell.
               int small = Math.min(row[i][j],col[i][j]);
               ///since the values could be greater or smaller
               //we hav to chec if the top-right and bottom-left
               //also have the all ones in them, and we check if
               //that is not the case, the we try for smaller value. 
               while(small>max){
                   if(row[i-small+1][j]>=small && col[i][j-small+1]>=small){
                       max = small;
                       break;
                   }
                   small--;
               }
            }
        }
        
        return max;
    }
};