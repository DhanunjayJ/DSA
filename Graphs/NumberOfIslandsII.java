import java.util.ArrayList;
import java.util.List;

public class NumberOfIslandsII {
    /**
 * Definition for a point.
 * class Point {
 *     int x;
 *     int y;
 *     Point() { x = 0; y = 0; }
 *     Point(int a, int b) { x = a; y = b; }
 * }
 */

public class Solution {
    /**
     * @param n: An integer
     * @param m: An integer
     * @param operators: an array of point
     * @return: an integer array
     */
     int [] par;
     int [] rank;
     int components = 0;
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        // write your code here
        /*
        First we convert the givne matrix n and m into a single matrix for the 
        parent array so that we could acutaully do the union find in the array. 

        To get the count of components every time without iterating through the array we use the union function 
        as our helper.

        intailly when the island is formed we count it as island. 

        then we check the neighbours if there are any ilsands if it is then we call the union. 

        and in the union if the parx and pary are same we reduce the coponet count. 
        else we keep as it si. 

        edge case : if the input has the value that is alredy a island we skip it keep the components as 
        same as before. 


        */
        int size = n*m;
        
        par = new int[size];
        rank = new int[size];

        for(int i=0;i<size;i++){
            par[i] = i;
        }

        int [][] mat = new int[n][m];

        List<Integer> ans = new ArrayList<>();
        for(Point p : operators){
            //if that cell is not being marked as island. only then continue else no.
            if(mat[p.x][p.y]!=1){
            components++;
            mat[p.x][p.y] = 1;
            for(int i =0;i<nbrs.length;i++){
                int row = p.x+nbrs[i][0];
                int col = p.y+nbrs[i][1];
                if(row>=0 && col>=0 && row<n && col<m && mat[row][col]==1){
                    union(row*m+col,p.x*m+p.y);
                }
            }
            }
            ans.add(components);
        }
        return ans;
    }

    public int find(int x){
        if(x==par[x]) return x;
        int temp = find(par[x]);
        par[x] = temp;
        return temp;
    }

    public void union(int x,int y){
        int px = find(x);
        int py = find(y);
        if(px==py) return;
        components--;
        if(rank[px]>rank[py]){
            par[px] = py;
        }else if(rank[py]>rank[px]){
            par[py] = px;
        }else{
            par[py] = px;
            rank[py]++;
        }
    }

    int [][] nbrs = {{1,0},{0,1},{-1,0},{0,-1}};

}
}
