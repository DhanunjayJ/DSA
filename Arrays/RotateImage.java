class Solution {
    public void rotate(int[][] matrix) {
        int numRows = matrix.length;
        //transpose
        for(int r=1;r<numRows;r++){
            for(int c=0;c<r;c++){
                int temp = matrix[r][c];
                matrix[r][c] = matrix[c][r];
                matrix[c][r] = temp;
            }
        }
        //reverse each row
        for(int r=0;r<numRows;r++){
            reverse(matrix[r]);
        }
    }
    public void reverse(int [] arr){
        int left = 0;
        int right = arr.length-1;
        while(left<right){
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right]=temp;
            left++;
            right--;
        }
    }
}