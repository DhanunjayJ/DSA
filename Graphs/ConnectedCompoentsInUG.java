class Solution {
    public ArrayList<ArrayList<Integer>> getComponents(int V, int[][] edges) {
        // code here
        boolean [] vis = new boolean[V+1];
        
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
        
        for(int i=0;i<edges.length;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for(int i=0;i<V;i++){
            if(vis[i]==false){
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(i);
                vis[i] = true;
                dfs(adj,i,temp,vis);
                ans.add(temp);
            }
        }
        return ans;
    }
    
    public void dfs(ArrayList<ArrayList<Integer>> adj,int src,ArrayList<Integer> temp,boolean [] vis){
        //first get all the nbrs of the src.
        ArrayList<Integer> nbrs = adj.get(src);
        for(int n:nbrs){
            if(vis[n]==false){
                temp.add(n);
                vis[n] = true;
                dfs(adj,n,temp,vis);
            }
        }
    }
}