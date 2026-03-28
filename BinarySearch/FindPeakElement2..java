class Solution {
    public int[] findPeakGrid(int[][] mat) {
        //find the mid row and get max -> which will be the greater than top and botton
        //now check the neighour which if it greater if not , go the greater side and do the same. which is 
        // find ing the amx on that row. and check the 4 sides. 
        int n = mat.length;
        int m = mat[0].length;

        int low = 0;
        int high = m-1;
        int maxRow = 0;

        while(low<=high){
            int mid = (low+high)/2;
            int max = 0;
            for(int j=0;j<n;j++){
                if(mat[j][mid]>max){
                    maxRow = j;
                    max = mat[j][mid];
                }
            }

            int leftValue = (mid==0) ? -1 : mat[maxRow][mid-1];
            int rightValue = (mid==m-1) ? -1 : mat[maxRow][mid+1];

            if(leftValue<mat[maxRow][mid] && rightValue<mat[maxRow][mid]){
                return new int[]{maxRow,mid};
            }else{
                if(leftValue>mat[maxRow][mid]){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }
        }
        return new int[]{};
    }
}