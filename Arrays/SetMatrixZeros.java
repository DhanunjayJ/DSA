class Solution {
    public void setZeroes(int[][] matrix) {

        boolean firstColHasZero = false;
        boolean firstRowHasZero = false;

        int numRows = matrix.length;
        int numCols = matrix[0].length;

        for(int r=0;r<numRows;r++){
            if(matrix[r][0]==0){
                firstColHasZero=true;
            }
        }

        for(int c=0;c<numCols;c++){
            if(matrix[0][c]==0){
                firstRowHasZero=true;
            }
        }

        for(int r=1;r<numRows;r++){
            for(int c=1;c<numCols;c++){
                if(matrix[r][c]==0){
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }

        for(int r=1;r<numRows;r++){
            if(matrix[r][0]==0){
                Arrays.fill(matrix[r],0);
            }
        }

        for(int c=1;c<numCols;c++){
            if(matrix[0][c]==0){
                for(int r=1;r<numRows;r++){
                    matrix[r][c]=0;
                }
            }
        }

        if(firstColHasZero){
            for(int r=0;r<numRows;r++){
                matrix[r][0]=0;
            }
        }
        
        if(firstRowHasZero){
            Arrays.fill(matrix[0],0);
        }
    }
}