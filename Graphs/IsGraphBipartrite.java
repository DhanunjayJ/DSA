//using Union Find

class Solution {
    int [] par;
    public boolean isBipartite(int[][] graph) {
        // u and v1,v2,v3 are the neighbours of u.
        //then all v's sould be in one group and u in another
        //so we check this in the graph. 
        int n = graph.length;
        par = new int[n];
        for(int i=0;i<n;i++) par[i] = i;
        for(int u=0;u<n;u++){
            int firstN = -1;
            for(int nbr : graph[u]){
                if(find(u)==find(nbr)) return false;
                if(firstN==-1) firstN = nbr;
                else union(nbr,firstN);
            }
        }
        return true;
    }
    public void union(int u,int v){
        int pu = find(u);
        int pv = find(v);
        if(pu!=pv){
            par[pu] = pv;
        }
    }
    public int find(int u){
        if(u==par[u]) return u;
        return par[u] = find(par[u]);
    }
}