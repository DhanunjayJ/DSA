import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortestPathInUG {
    class Solution {
    public int[] shortestPath(int V, int[][] edges, int src) {
        // code here
        int [] shortDist = new int[V];
        
        Arrays.fill(shortDist,-1);
        
        List<List<Integer>> adj = new ArrayList<>();
        
        for(int i=0;i<V;i++) adj.add(new ArrayList<>());
        
        for(int i=0;i<edges.length;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        shortDist[src] = 0;
        
        while(!q.isEmpty()){
            int par = q.remove();
            for(int nbr:adj.get(par)){
                if(shortDist[nbr]==-1){
                    shortDist[nbr] = shortDist[par]+1;
                    q.add(nbr);
                }
            }
        }
        
        return shortDist;
    }
}

}
