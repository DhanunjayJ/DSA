class Solution {
    public void solve(char[][] board) {

        int n = board.length;
        int m = board[0].length;

        if(n==1 && m==1 && board[0][0]=='O') {
            return;
        }

        boolean [][] isVisited = new boolean[n][m];

        //visit every zero in the boundary.
        for(int j=0;j<m-1;j++){
            if(board[0][j]=='O'){
                dfs(board,isVisited,0,j);
            }
        } 

        for(int i=0;i<n-1;i++){
            if(board[i][m-1]=='O'){
                dfs(board,isVisited,i,m-1);
            }
        } 

        for(int j=m-1;j>0;j--){
            if(board[n-1][j]=='O'){
                dfs(board,isVisited,n-1,j);
            }
        } 

        for(int i=n-1;i>0;i--){
            if(board[i][0]=='O'){
                dfs(board,isVisited,i,0);
            }
        } 
        //for each zero that is on the boundary 
        //do dfs for that boundary o.
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(board[i][j]=='O' && !isVisited[i][j]){
                    board[i][j] = 'X';
                }
            }
        }
        //mark all the cells as true. 
    }

    int [][] nbrs = {{1,0},{0,1},{-1,0},{0,-1}};

    public void dfs(char [][]board,boolean [][]isVisited,int i,int j){
        isVisited[i][j] = true;
        for(int k=0;k<nbrs.length;k++){
            int row = i+nbrs[k][0];
            int col = j+nbrs[k][1];
            if(row>=0 && col>=0 && row<board.length && col<board[0].length
            && board[row][col]=='O' && isVisited[row][col]==false
            ){
                dfs(board,isVisited,row,col);
            }
        }
    }
}


//another way
class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;

        int n = board.length;
        int m = board[0].length;

        boolean[][] isVisited = new boolean[n][m];

        // 1. Check Top and Bottom Boundaries
        for (int j = 0; j < m; j++) {
            if (board[0][j] == 'O') {
                dfs(board, isVisited, 0, j);
            }
            if (board[n - 1][j] == 'O') {
                dfs(board, isVisited, n - 1, j);
            }
        }

        // 2. Check Left and Right Boundaries
        for (int i = 0; i < n; i++) {
            if (board[i][0] == 'O') {
                dfs(board, isVisited, i, 0);
            }
            if (board[i][m - 1] == 'O') {
                dfs(board, isVisited, i, m - 1);
            }
        }

        // 3. Post-process the grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 'O' && !isVisited[i][j]) {
                    // This 'O' was never reached by a boundary DFS -> CAPTURE IT
                    board[i][j] = 'X';
                }
            }
        }
    }

    int[][] nbrs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public void dfs(char[][] board, boolean[][] isVisited, int i, int j) {
        isVisited[i][j] = true;
        
        for (int k = 0; k < nbrs.length; k++) {
            int row = i + nbrs[k][0];
            int col = j + nbrs[k][1]; // FIXED: Changed 'i' to 'j'

            if (row >= 0 && col >= 0 && row < board.length && col < board[0].length
                && board[row][col] == 'O' && !isVisited[row][col]) {
                
                dfs(board, isVisited, row, col);
            }
        }
    }
}