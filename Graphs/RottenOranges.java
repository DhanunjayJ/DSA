import java.util.LinkedList;
import java.util.Queue;

public class RottenOranges {
    class Solution {
    public int orangesRotting(int[][] grid) {
        /*
        We nee a queue to add all the rotten oranges. 
        if there are no rotten orages then the time is zero.
        we have to check if there are any fresh orages to rot. if the fresh orages
        are zero then time is also zero.

        and once we rot all the orages instead of counting all the oranges after rotten
        we will just decrement the count of frehs oragnes if fresh oragnes are zero
        at then end that means. we didn't rot all oranges then we return -1.
        */

        Queue<int[]> q = new LinkedList<>();
        int time = 0;
        int [][] nbrs = {{-1,0},{1,0},{0,1},{0,-1}};

        int n = grid.length;
        int m = grid[0].length;
        int fresh = 0;

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==2){
                    q.add(new int[]{i,j});
                }else if(grid[i][j]==1){
                    fresh++;
                }
            }
        }

        if(fresh==0){
            return 0;
        }

        if(q.isEmpty()){
            return -1;
        }

        while(!q.isEmpty()){
           boolean rottedAnyThisMinute = false;
            int size = q.size();
            for(int i=0;i<size;i++){
                int[] rem = q.remove();
                for(int j=0;j<nbrs.length;j++){
                    int row = rem[0]+nbrs[j][0];
                    int col = rem[1]+nbrs[j][1];
                    if(row>=0 && col>=0 && row<n && col<m && grid[row][col]==1){
                        grid[row][col] = 2;
                        fresh--;
                        q.add(new int[]{row,col});
                        rottedAnyThisMinute = true;
                    }
                }
            }
            if(rottedAnyThisMinute){
                time++;
            }
        }
        
        return fresh==0 ? time : -1;
    }
}

// another way

class Pair{
    int i;
    int j;
    int t;
    Pair(int i,int j,int t)
    {
        this.i = i;
        this.j = j;
        this.t = t;
    }
}

class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<Pair> q = new LinkedList<>();
        int n = grid.length;
        int m = grid[0].length;
        int count1 = 0;
        int count2 = 0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]==2)
                {
                    q.add(new Pair(i,j,0));
                    count2++;
                }else if(grid[i][j]==1)
                {
                    count1++;
                }
            }
        }
        if(count1==0){
            return 0;
        }else if(count2==0)
        {
            return -1;
        }
        int ans = -1;
        while(q.size()>0)
        {
            Pair rem = q.remove();
            int cr = rem.i;
            int cl = rem.j;
            int ct = rem.t;
            ans = ct;
            if(cr-1>=0 && grid[cr-1][cl]==1){
                q.add(new Pair(cr-1,cl,ct+1));
                grid[cr-1][cl]=2;
            }
            if(cl+1<m && grid[cr][cl+1]==1)
            {
                q.add(new Pair(cr,cl+1,ct+1));
                grid[cr][cl+1]=2;
            }
            if(cr+1<n && grid[cr+1][cl]==1)
            {
                q.add(new Pair(cr+1,cl,ct+1));
                grid[cr+1][cl]=2;
            }
            if(cl-1>=0 && grid[cr][cl-1]==1)
            {
                q.add(new Pair(cr,cl-1,ct+1));
                grid[cr][cl-1]=2;
            }
        }
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(grid[i][j]==1){
                    return -1;
                }
            }
        }
        return ans;
    }
}


}
