class Solution {

  public List<Integer> spiralOrder(int[][] matrix) {

    List<Integer> result = new ArrayList<>();

    if (matrix == null || matrix.length == 0) return result;

    int rowsLeft = matrix.length;
    int colsLeft = matrix[0].length;

    // r and c represent our current "head" position in the matrix

    int r = 0;
    int c = 0;

    // We process the matrix in "outer shells" or layers.
    // Each full shell reduces the remaining rows and columns by 2.
    while (rowsLeft > 1 && colsLeft > 1) {
      // 1. Move Right
      for (int k = 0; k < colsLeft - 1; k++) {
        result.add(matrix[r][c++]);
      }
      // 2. Move Down
      for (int k = 0; k < rowsLeft - 1; k++) {
        result.add(matrix[r++][c]);
      }
      // 3. Move Left
      for (int k = 0; k < colsLeft - 1; k++) {
        result.add(matrix[r][c--]);
      }
      // 4. Move Up
      for (int k = 0; k < rowsLeft - 1; k++) {
        result.add(matrix[r--][c]);
      }
      // Move to the start of the next inner shell (diagonal jump)
      r++;
      c++;
      // We've processed the outermost shell, so 2 rows and 2 columns are gone
      rowsLeft -= 2;
      colsLeft -= 2;
    }

    // Final Piece: If there's a single row or column left in the center
    if (rowsLeft == 1) {
      for (int k = 0; k < colsLeft; k++) {
        result.add(matrix[r][c++]);
      }
    } else if (colsLeft == 1) {
      for (int k = 0; k < rowsLeft; k++) {
        result.add(matrix[r++][c]);
      }
    }
    
    return result;
}
}