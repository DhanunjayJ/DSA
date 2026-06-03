import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BSSandDFS {
    class Solution {
    public List<Integer> dfsOfGraph(int V, List<List<Integer>> edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<edges.size();i++){
            int u = edges.get(i).get(0);
            int v = edges.get(i).get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
      boolean [] vis = new boolean [V+1];
      List<Integer> ans = new ArrayList<>();
      dfs(adj,0,ans,vis);
      return ans;
    }

    public void dfs(List<List<Integer>> adj,int src,List<Integer> ans,boolean [] vis){
        ans.add(src);
        vis[src] = true;
        List<Integer> nbrs = adj.get(src);
        for(int n : nbrs){
            if(vis[n]==false){
                dfs(adj,n,ans,vis);
            }
        }
    }
    
    public List<Integer> bfsOfGraph(int V, List<List<Integer>> edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<edges.size();i++){
            int u = edges.get(i).get(0);
            int v = edges.get(i).get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        Queue<Integer> q = new LinkedList<>();
        boolean[] vis = new boolean[V+1];
        
        List<Integer> ans = new ArrayList<>();
        q.add(0);
        vis[0] = true;
        
        while(!q.isEmpty()){
            int rem = q.remove();
            ans.add(rem);
            for(int n : adj.get(rem)){
                if(vis[n]==false){
                    q.add(n);
                    vis[n] = true;
                }
            }
        }
        
        return ans;
    }
}


}
