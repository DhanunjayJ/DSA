class Solution {
    public int countServers(int[][] grid) {
        //we count how many are isolated and remove them from total count
        int n = grid.length;
        int m = grid[0].length;
        int [] rowCount = new int[n];
        int [] colCount = new int[m];
        int computers = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    rowCount[i]++;
                    colCount[j]++;
                    computers++;
                }
            }
        }
        int isolated = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    if(rowCount[i]==1 && colCount[j]==1){
                        isolated++;
                    }
                }
            }
        }
        return computers-isolated;
    }
}