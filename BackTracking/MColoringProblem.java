class Solution {
    boolean graphColoring(int v, int[][] edges, int m) {
        // code here
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<v;i++){
            adj.add(new ArrayList<>());
        }
        for(int [] edge:edges){
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        int [] colors = new int[v];
        return isColorPossible(adj,colors,m,0,v);
    }
    public boolean isColorPossible(List<List<Integer>> adj,int [] colors,int m,int src,int v){
        //if visited all the nodes sequentially and reached the end return true.
        if(v==src) return true;
        //try all possile colors and check which is safe
        //using only colors from 1 to m not zero.
        for(int i=1;i<=m;i++){
            if(isSafe(i,adj,colors,src)){
                colors[src] = i;
                //using this colors gives makes it true we retrrn true;
                if(isColorPossible(adj,colors,m,src+1,v)) return true;
                colors[src] = 0;
            }
        }
        return false;
    }
    public boolean isSafe(int c,List<List<Integer>> adj,int [] colors,int src){
        //check if any of the neigh bours have the same color
        for(int nbr : adj.get(src)){
            if(colors[nbr]==c){
                return false;
            }
        }
        return true;
    }
}