// 9^81 * 27 appraoch

class Solution {
    public void solveSudoku(char[][] board) {
      solve(board);
    }
    public boolean solve(char [][]board){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]=='.'){
                    for(char k='1';k<='9';k++){
                        if(isValid(board,k,i,j)){
                            board[i][j]=k;
                            if(solve(board)){
                                return true;
                            }
                            board[i][j]='.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isValid(char[][]board, int k,int i,int j){
        for(int c=0;c<9;c++){
            if(board[i][c]==k)return false;
        }
        for(int r=0;r<9;r++)
        {
            if(board[r][j]==k) return false;
        }
        int topLeftRow = (i/3)*3;
        int topLeftCol = (j/3)*3;
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                if(board[topLeftRow+r][topLeftCol+c]==k)return false;
            }
        }
        return true;
    }
}


//using boolena array

class Solution {
    // State tracking arrays
    boolean[][] rowUsed = new boolean[9][10];
    boolean[][] colUsed = new boolean[9][10];
    boolean[][] boxUsed = new boolean[9][10];


    public void solveSudoku(char[][] board) {
        // 1. Pre-fill the state based on the initial board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    toggleState(i, j, num, true);
                }
            }
        }
        solve(board, 0, 0);
    }


    private boolean solve(char[][] board, int r, int c) {
        // Move to next row if we reach end of columns
        if (c == 9) return solve(board, r + 1, 0);
        // Base case: All rows filled
        if (r == 9) return true;


        if (board[r][c] != '.') return solve(board, r, c + 1);


        for (int num = 1; num <= 9; num++) {
            if (isSafe(r, c, num)) {
                board[r][c] = (char) (num + '0');
                toggleState(r, c, num, true);


                if (solve(board, r, c + 1)) return true;


                // Backtrack
                toggleState(r, c, num, false);
                board[r][c] = '.';
            }
        }
        return false;
    }


    private boolean isSafe(int r, int c, int num) {
        int boxIdx = (r / 3) * 3 + (c / 3);
        return !rowUsed[r][num] && !colUsed[c][num] && !boxUsed[boxIdx][num];
    }


    private void toggleState(int r, int c, int num, boolean state) {
        int boxIdx = (r / 3) * 3 + (c / 3);
        rowUsed[r][num] = state;
        colUsed[c][num] = state;
        boxUsed[boxIdx][num] = state;
    }
}




// 9*81 apprach with 3*81 length

class Solution {
    // the average time complxity of the code if we don't use the space then it would be of 9^81 * 27 (rowcheck, colcheck, and quad check)
    //we can opitmize this in to a 2d boolean arrays. for quad idx we conver the topleft cell of the each quad. convert to in the inedex
    // using the formula (i/3)*3 + j/3 -> this will give us the values. in integer format.
    // using bit masking we use for each number of the 1 to 9 we use the 2^num bit to chekc if it set or not. 
    // if it not set it means that number is not yet present. by doing xor we set or unset the bit. 
    // we use the bit mask of the val but left shifting the value based on the char digit.
    
    int [] row;
    int [] col;
    int [] quad;

    public void solveSudoku(char[][] board) {
        row = new int[9];
        col = new int[9];
        quad = new int[9];

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]!='.'){
                    toggleState(i,j,board[i][j]);
                }
            }
        }
        generate(board,0,0);
    }
    public boolean generate(char [][]board,int i,int j){

        if(i==9) return true;

        if(j==9) return generate(board,i+1,0);

        if(board[i][j]!='.') return generate(board,i,j+1);

        for(char k='1';k<='9';k++){
            int val = k -'0';
            int mask = 1<<val;
            // 
            int res = row[i] | col[j] | quad[(((i/3)*3)+(j/3))];
            if((res&mask)==0){
                //if the numner is not there in the same row,col or same box
                //put that number
                board[i][j] = k;

                toggleState(i,j,k);

                if(generate(board,i,j+1)){
                    return true;
                }

                board[i][j] = '.';
                toggleState(i,j,k);
            }
        }
        return false;
    }

    public void toggleState(int i,int j,char c){
        int boxIdx = ((i/3)*3)+(j/3);
        int val = c-'0';
        int mask = 1<<val;
        row[i] ^= mask;
        col[j] ^=mask;
        quad[boxIdx] ^=mask;
    }
}