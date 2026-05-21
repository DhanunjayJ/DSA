import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class UndirectedGraphCycle {
    class Solution {
    public boolean isCycle(int V, int[][] edges) {
        // Code here
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        
        for(int i=0;i<edges.length;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
        
        boolean [] vis = new boolean[V];
        for(int i=0;i<V;i++){
            if(vis[i]==false){
                if(dfs(vis,adj,i,-1))
                {return true;}
            }
        }
        return false;
    }
    
    public boolean dfs(boolean[] vis,ArrayList<ArrayList<Integer>> adj,int src,int p){
        vis[src] = true;
        for(int n:adj.get(src)){
            if(!vis[n]){
                if(dfs(vis,adj,n,src)){
                    return true;
                }
            }else if(n!=p){
                return true;
            }
        }
        return false;
    }
}

//using BFS
class Solution {
    public boolean isCycle(int V, int[][] edges) {
        // Code here
        /*
        In an undirected graph a cycle is there when we vist
        a node twice and that visited node is not the parent.
        */
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        
        for(int i=0;i<edges.length;i++){
            adj.get(edges[i][0]).add(edges[i][1]);
            adj.get(edges[i][1]).add(edges[i][0]);
        }
        
        boolean [] vis = new boolean[V];
        //need to loop throgh all the nodes in the case of the
        // the disconnected component. 
       for(int i=0;i<V;i++){
           if(!vis[i]){
               if(bfs(adj,vis,i))
               return true;
           }
       }
       return false;
        
    }
    public boolean bfs(ArrayList<ArrayList<Integer>> adj,boolean [] vis,int src){
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{src,-1});
        vis[src] = true;
        
        while(!q.isEmpty()){
            int [] rem = q.remove();
            for(int n:adj.get(rem[0])){
                if(!vis[n]){
                    vis[n] = true;
                    q.add(new int[]{n,rem[0]});
                }else if(n!=rem[1]){
                    return true;
                }
            }
        }
        
        return false;
    }
}
}
