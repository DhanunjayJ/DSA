import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathWithMinEffor {
    //only DFS -> gives TLE Appraoch
    class Solution {
    public int minimumEffortPath(int[][] heights) {
        
        int n = heights.length;
        int m = heights[0].length;

        int [][] distFromSrc = new int[n][m];

        for(int i=0;i<n;i++){
            Arrays.fill(distFromSrc[i],Integer.MAX_VALUE);
        }

        dfs(distFromSrc,heights,0,0,0);

        return distFromSrc[n-1][m-1];
    }

    int[][] nbrs = {{1,0},{0,1},{-1,0},{0,-1}};

    public void dfs(int [][] distFromSrc,int [][] heights,int i,int j,int maxTillNow){
        distFromSrc[i][j] = maxTillNow;
        if(i==heights.length-1 && j==heights[0].length-1){
           return;   
        }
        for(int k=0;k<nbrs.length;k++){
            int row = nbrs[k][0]+i;
            int col = nbrs[k][1]+j;
            if(row>=0 && col>=0 && row<heights.length && col<heights[0].length){
                int maxDiff = Math.abs(heights[i][j]-heights[row][col]);
                int nextMaxEffot = Math.max(maxTillNow,maxDiff);
                if(nextMaxEffot<distFromSrc[row][col]){
                    dfs(distFromSrc,heights,row,col,nextMaxEffot);
                }
            }
        }
    }
}

// Using Binary search and BFS
//l (O(N.M.log(MaxHeight})
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

//using shortest path algo - Dijkastra and explore all the min differnce nodes. 
//o(n*n(log(n*m)))

class Solution {
    public int minimumEffortPath(int[][] heights) {
        
        int n = heights.length;
        int m = heights[0].length;

        int [][] maxDistDiffFromSrc = new int[n][m];

        for(int i=0;i<n;i++){
            Arrays.fill (maxDistDiffFromSrc[i],Integer.MAX_VALUE);
        }

        int[][] nbrs = {{1,0},{0,1},{-1,0},{0,-1}};

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[2],b[2]));

        pq.add(new int[]{0,0,0});
        maxDistDiffFromSrc[0][0] = 0;

        while(!pq.isEmpty()){

            int [] rem = pq.poll();
            
            int r = rem[0];
            int c = rem[1];

            int currMaxDiff = rem[2];

            if(r==n-1 && c==m-1){
                return currMaxDiff;
            }

           if(currMaxDiff>maxDistDiffFromSrc[r][c]){
            continue;
           }

            for(int [] nbr : nbrs){
                
                int row = nbr[0]+r;
                int col = nbr[1]+c;

                if(row>=0 && col>=0 && row<n && col<m){
                    int nextDiff = Math.max(currMaxDiff,Math.abs(heights[r][c]-heights[row][col]));
                    if(nextDiff<maxDistDiffFromSrc[row][col]){
                         maxDistDiffFromSrc[row][col] = nextDiff;
                        pq.add(new int[]{row,col,nextDiff});
                    }
                }

            }


        }

        return 0;

    }

    
}


}
