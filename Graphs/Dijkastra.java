import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkastra {
    class Pair {
    int node;
    int weight;
    Pair(int node,int weight){
        this.node = node;
        this.weight = weight;
    }
}

class Solution {
    public int[] dijkstra(int V, int[][] edges, int src) {
        // code here
        //This is the priorityQueue
        //to always go the shortest distance node first. based on the weight
        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b) -> Integer.compare(a.weight,b.weight));
        
        List<List<Pair>> adj = new ArrayList<>();
        
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        
        for(int i=0;i<edges.length;i++){
            
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];
            
            adj.get(u).add(new Pair(v,weight));
            adj.get(v).add(new Pair(u,weight));
        }
        
        int [] ans = new int[V];
        Arrays.fill(ans,Integer.MAX_VALUE);
        
        pq.add(new Pair(src,0));
        
        while(!pq.isEmpty()){
            Pair par = pq.remove();
            //if we already found out the shortest 
            //path to this node we just continue we don't explore
            //this node. 
            if(ans[par.node]!=Integer.MAX_VALUE) continue;
            //if not visited then visit the node and update
            //the value with the minimum weight. 
            //which we are gunrated to get from the pq.
            //for the first time. 
            ans[par.node] = par.weight;
            //now we need to visit all the nbrs of this node.
            for(Pair nbr : adj.get(par.node)){
                //if the nbr shortest path is already found?
                if(ans[nbr.node]!=Integer.MAX_VALUE) continue;
                //else add it the queu
                pq.add(new Pair(nbr.node,nbr.weight+par.weight)); 
            }
        }
        
        return ans;
        
    }
}
}
