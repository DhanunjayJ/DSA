package CyclicPattern;

public class FindMissingAndRepeatedValuesInMatrix {
    class Solution {
    public int[] findMissingAndRepeatedValues(int[][] grid) {
        // here we are implementing the negation appraohc
        //we try to map all the values to the correspoding indexes.
        // value -> index = value-1, row -> index/m (length of row)
        //col -> index%m , once mapped, to make it mark as mapped. 
        // we negate the values, since the value input is all postive.
        // while negating if we find a value that is already mapped
        //having negative in the mapping place. then it is repeated.
        //once found repeted then do that for all the values. 
        // at last do one more scanning, then the one with with positve value is the one that is missing. 
        int n = grid.length;
        int m = grid[0].length;

        int repeatedvalue = 0;

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                // checking if the index is mapped or not
                //Cyclic Sort untill the current value is not postive (not mapped)
                //or untill it is equal to the repeated value. 
                while(grid[i][j]>0 && grid[i][j]!=repeatedvalue){
                    int value = grid[i][j];
                    int index = value-1;
                    int row = index/m;
                    int col = index%m;
                    //checking if the mapping index is alredy mapped?
                    if(grid[row][col]>0){
                        int temp = grid[i][j];
                        grid[i][j] = grid[row][col];
                        grid[row][col]= -temp;
                    }else{
                        repeatedvalue = grid[i][j];
                    }
                }
            }
        }
        int missingvalue = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]>0){
                    missingvalue = (i*m+j)+1;
                }
            }
        }
        return new int[]{repeatedvalue,missingvalue};
    }
}
}


//doing with mathematical approach

class Solution {
    public int[] findMissingAndRepeatedValues(int[][] grid) {
        //A. Casting & Consistency
        //n*n -> total numbers
        long n = grid.length;
        long total = n*n;

        long actualSum = 0;
        long actualSqSum = 0;

        for(int i=0; i<n ; i++){
            for(int j = 0;j<n ;j++){
                actualSum += grid[i][j];
                actualSqSum += (long) grid[i][j]*grid[i][j];
            }
        }

        //using sum of first n natual numbers formula
        long perfectSum = (total*(total+1))/2;
        //using the sum of first n2 natural numbers fomula
        long perfectSqSum = ((total)*(2*total+1)*(total+1))/6;

        long sDiff = actualSum-perfectSum;
        long sqDiff = actualSqSum-perfectSqSum;

        int duplicate = (int) (sqDiff/sDiff+sDiff)/2;
        int missing =(int) (sqDiff/sDiff-sDiff)/2;

        return new int[]{duplicate,missing};
    }
}