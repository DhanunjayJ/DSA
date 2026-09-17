class Solution {
    public int minimumEdgeReversal(int[][] edges, int n, int src, int dst) {
        // code here
        ArrayList<ArrayList<int[]>> graph = new ArrayList<>();
        for(int i=0;i<=n;i++){
            graph.add(new ArrayList<>());
        }
        for(int [] edge : edges){
            graph.get(edge[0]).add(new int[]{edge[1],0});
            //we do add the reverse edge with weight of one!!
            //so that at the end if we rech the destination we have the total length. 
            graph.get(edge[1]).add(new int[]{edge[0],1});
        }
        boolean [] vis = new boolean[n+1];

        //do dijkstra sort based on the weight. 
        PriorityQueue<int[]> q = new PriorityQueue<>((a,b) -> Integer.compare(a[1],b[1]));
        q.add(new int[]{src,0});
        
        while(!q.isEmpty()){
            
            int [] rem = q.remove();
            
            if(vis[rem[0]]) continue;
            vis[rem[0]] = true;
            
            //once we rewach the destination we returrn the wieght needed. 
            if(rem[0]==dst){
                return rem[1];
            }
            
            for(int [] nbr : graph.get(rem[0])){
                if(!vis[nbr[0]]){
                    q.add(new int[]{nbr[0],rem[1]+nbr[1]});
                }
            }
            
        }
       return -1;
    }
}