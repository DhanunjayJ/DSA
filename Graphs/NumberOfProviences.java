class Solution {
    public int findCircleNum(int[][] isConnected) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int v = isConnected.length;
        for(int i=0;i<v;i++){
            adj.add(new ArrayList<>());
        }

        for(int i=1;i<v;i++){
            for(int j=0;j<i;j++){
                if(isConnected[i][j]==1){
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }

        boolean [] vis = new boolean[v];
        int count = 0;
        for(int i=0;i<vis.length;i++){
            if(vis[i]==false){
                count++;
                dfs(adj,i,vis);
            }
        }
        return count;
    }

    public void dfs(ArrayList<ArrayList<Integer>> adj,int src,boolean [] vis){
        vis[src] = true;
        for(int i:adj.get(src)){
            if(vis[i]==false){
                dfs(adj,i,vis);
            }
        }
    }
}