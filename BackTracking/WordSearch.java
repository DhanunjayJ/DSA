public class WordSearch {
    //Appraoch 1
    class Solution {
    public boolean exist(char[][] board, String word) {

        int n = board.length;
        int m = board[0].length;

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(board[i][j]==word.charAt(0) && backtrack(board,word,i,j,n,m,0)){
                    return true;
                }
            }
        }

        return false;
    }
    public boolean backtrack(char [][] board, String word, int i,int j,int n,int m,int idx){
        if(idx==word.length()) return true;
        if(i>=n || j>=m || j<0 || i<0 || board[i][j]=='0' || board[i][j]!= word.charAt(idx)){
            return false;
        }
        board[i][j]='0';
        boolean isP = (backtrack(board,word,i,j+1,n,m,idx+1)) || backtrack(board,word,i+1,j,n,m,idx+1) || backtrack(board,word,i,j-1,n,m,idx+1) || backtrack(board,word,i-1,j,n,m,idx+1);
        board[i][j] = word.charAt(idx);

        return isP;
    }
}

//Appraoch 2
class Solution {
    public boolean exist(char[][] board, String word) {
        /*
        WE start the search by check if the board character mathces with the start of the
        word. if it is, the we srtart searching the board,
        and make the current cell as visited. 
        and do i++;
        we check all th neighbours.
        athe edge case when we retuch the end of the word reutrn true;
        */
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==word.charAt(0)){
                    if(isExist(board,word,1,i,j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    int [][] nbrs = {{1,0},{0,1},{-1,0},{0,-1}};

    public boolean isExist(char [][] board,String word,int indx,int i,int j){
        if(indx==word.length()){
            return true;
        }
        
        char originalChar = board[i][j];

        board[i][j] = '0';

        for(int k=0;k<nbrs.length;k++){
            int row = i+nbrs[k][0];
            int col = j+nbrs[k][1];
            if(row>=0 && col>=0 && row<board.length && col<board[0].length && board[row][col]==word.charAt(indx)){
                if(isExist(board,word,indx+1,row,col)){
                    return true;
                }
            }
        }
        
        board[i][j] = originalChar;

        return false;
    }
}
}
