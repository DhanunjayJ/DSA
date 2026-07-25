// N! * n2

class Solution {
    public List<List<String>> solveNQueens(int n) {
        char [][] board = new char[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                board [i][j] = '.';
            }
        }
        List<List<String>> ans  = new ArrayList<>();
        helper(ans,board,0,0);
        return ans;
    }

    public void helper(List<List<String>> ans, char [][] board, int i,int j){
        if(i>=board.length) {
            convert(ans,board);
            return;
        }

        for(int k=j;k<board.length;k++){
        if(check(board,i,k)){
            board[i][k] = 'Q';
            helper(ans,board,i+1,0);
            board[i][k] = '.';
         }
        }
    }

    public void convert(List<List<String>> ans, char[][] board) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            temp.add(new String(board[i]));
        }
        ans.add(temp);
    }

    public boolean check (char [][]board, int i,int j){
        //check the col
        for(int r=0;r<i;r++){
            if(board[r][j]=='Q'){
                return false;
            }
        }

        int diag = i-j;
        int adiag = i+j;

        for(int r=0;r<i;r++){
            for(int c=0;c<board.length;c++){
                if((r-c)==diag || (r+c)==adiag){
                    if(board[r][c]=='Q'){
			            return false;
                    }
                }

            }
        }
        return true;
    }
}

//N!

class Solution {
    boolean [] cols;
    boolean [] diag;
    boolean [] adiag;
    public List<List<String>> solveNQueens(int n) {

        cols = new boolean [n];
        diag = new boolean [2*n];
        adiag = new boolean [2*n];

        char [][] board = new char[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                board [i][j] = '.';
            }
        }

        List<List<String>> ans  = new ArrayList<>();
        helper(ans,board,0,0,n);
        return ans;
    }

    public void helper(List<List<String>> ans, char [][] board, int i,int j,int n){
        if(i>=board.length) {
            convert(ans,board);
            return;
        }

        for(int k=j;k<board.length;k++){
            if(cols[k] || diag[i-k+n] || adiag[i+k]){
                continue;
            }
            cols[k] = diag[i - k + n] = adiag[i + k] = true;
            board[i][k] = 'Q';
            helper(ans,board,i+1,0,n);
            cols[k] = diag[i - k + n] = adiag[i + k] = false;
            board[i][k] = '.';
        }
    }

    public void convert(List<List<String>> ans, char[][] board) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            temp.add(new String(board[i]));
        }
        ans.add(temp);
    }
}