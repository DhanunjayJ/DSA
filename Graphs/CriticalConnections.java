class Solution {
    boolean [] vis;
    int [] disc;
    int [] low;
    List<List<Integer>> conns;
    List<List<Integer>> ans;
    int time = 0;
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        vis = new boolean[n];
        disc = new int[n];
        low = new int[n];

        ans = new ArrayList<>();

        conns = new ArrayList<>();
        for(int i=0;i<n;i++) conns.add(new ArrayList<>());

        for(List<Integer> connection : connections){
            conns.get(connection.get(0)).add(connection.get(1));
            conns.get(connection.get(1)).add(connection.get(0));
        }
        //src,par
        dfs(0,-1);

        return ans;
    }

    public void dfs(int src,int par){
        vis[src] = true;
        //make the disc and low as the currnet time
        disc[src] = time;
        low[src] = time;
        time++;

        //go thorught all the neighbours and try to update the low [src]
        for(int nbr : conns.get(src)){
            //if nbr == par we skip.
            if (nbr == par) {
                continue; // Skip the edge back to the immediate parent
            }

            if(!vis[nbr]){
                dfs(nbr,src);
                //check if we can reach the min disc node from the nbrs
                low[src] = Math.min(low[nbr],low[src]);
                //if we can't get the low nbr.
                //then this is the critical edge
                if(low[nbr]>disc[src]){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(src);
                    temp.add(nbr);
                    ans.add(temp);
                }
            }else{
                //if the nbr is alreayd visited just go a back edge and updte it with the disc[nbr];
                //low is meaning that that what is the low we can reach. so disc[nbr] that.
                low[src] = Math.min(low[src],disc[nbr]);
            }
        }
    }
}