import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        
        // ==========================================
        // 1. WHAT & HOW: Collect coordinates of all '1's
        // ==========================================
        // WHY: Instead of sliding the entire matrix grid and checking every cell 
        // (which is slow because of all the empty '0's), we focus ONLY on the 
        // coordinates where '1's are located.
        List<int[]> img1V = new ArrayList<>();
        List<int[]> img2V = new ArrayList<>();
        int n = img1.length;
        
        // HOW: Scan both matrices completely to gather the positions (row, col) of all '1's.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1)
                    img1V.add(new int[]{i, j});
                if (img2[i][j] == 1)
                    img2V.add(new int[]{i, j});
            }
        }
        
        // ==========================================
        // 2. WHY: Find which shift vector is most common
        // ==========================================
        // WHY: If multiple '1's in img1 can all align with '1's in img2 using the 
        // exact same movement (shift vector), they will all overlap at the same time. 
        // We use a HashMap to count how many pairs share the exact same shift.
        HashMap<String, Integer> shiftDiffCount = new HashMap<>();
        int max = 0;
        
        // HOW: Compare every single '1' in img1 with every single '1' in img2.
        for (int[] v1 : img1V) {
            for (int[] v2 : img2V) {
                
                // Calculate the row and column difference required to move v1 to match v2
                int xDiff = v1[0] - v2[0];
                int yDiff = v1[1] - v2[1];
                
                // Convert the shift into a unique string key (e.g., "1,-2")
                String shiftDiff = xDiff + "," + yDiff;
                
                // Increment the frequency count for this specific shift vector
                shiftDiffCount.put(shiftDiff, shiftDiffCount.getOrDefault(shiftDiff, 0) + 1);
                
                // Keep track of the highest frequency (maximum overlap) seen so far
                max = Math.max(shiftDiffCount.get(shiftDiff), max);
            }
        }
        
        // Return the maximum overlap found
        return max;
    }
}