import java.util.LinkedList;
import java.util.Queue;

public class PathWithMinimumEffort {
    class Solution {
    public int minimumEffortPath(int[][] heights) {
        /*
        Do binary search between the lowest value and highest value int he grid.
        and find the mid and do bfs from start . 
        check if he mid path exist. then store it in ans.
        if do mid-1. 
        so we minininze the max distacne and do it untill we reach 
        the low<=high.

        tc : (n*m) * log MaxHeight
        */

        int low = 0;
        int high = Integer.MIN_VALUE;

        int n = heights.length;
        int m = heights[0].length;

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                high = Math.max(high,heights[i][j]);
            }
        }

        int ans = 0;

        while(low<=high){
            int mid = low+(high-low)/2;
            if(pathExist(heights,mid)){
                ans = mid;
                high = mid-1;
            }else{
                low = mid+1;
            }
        }

        return ans;
    }

    int [][] nbrs = {{1,0},{0,1},{-1,0},{0,-1}};

    public boolean pathExist(int[][] heights,int maxDiff){
        /*
        If you are just checking if any valid path exists under maxDiff, 
        the moment you visit a cell, you know it's reachable. 
        If a path continuing from that cell fails to reach the end, 
        no other path reaching that cell later will magically succeed either.
        */
        
        int n = heights.length;
        int m = heights[0].length;

        boolean [][] vis = new boolean[n][m];
        Queue<int[]> q = new LinkedList<>();

        q.add(new int[]{0,0});
        vis[0][0] = true;

        while(!q.isEmpty()){

            int [] rem = q.remove();

            int r = rem[0];
            int c = rem[1];

            if(r==n-1 && c==m-1){
                return true;
            }

            for(int i=0;i<nbrs.length;i++){
                int row = r+nbrs[i][0];
                int col = c+nbrs[i][1];
                if(row>=0 && col>=0 && row<n && col<m && !vis[row][col] && Math.abs(heights[r][c]-heights[row][col])<=maxDiff){
                    vis[row][col] = true;
                    q.add(new int[]{row,col});
                }
            }
        }

        return false;
    }
}
}
