class Solution {
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int n = patience.length;
        int [] dist = new int[n];
        Arrays.fill(dist,-1);
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
        for(int [] e : edges){
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0,0});
        while(!q.isEmpty()){
            int [] rem = q.remove();
            if(dist[rem[0]]!=-1) continue;
            dist[rem[0]] = rem[1];
            for(int nbr : adj.get(rem[0])){
                if(dist[nbr]==-1){
                    q.add(new int[]{nbr,rem[1]+1});
                }
            }
        }
        int lastMaxArrival = 0;
        for(int i=1;i<n;i++){
            int roundTrip = dist[i]*2;
            int p = patience[i];
            //last sent message is largest multiple of p strctly < roundtrip
            int lastSent = ((roundTrip-1)/p)*p;
            int lastArrival = lastSent + roundTrip;
            lastMaxArrival = Math.max(lastMaxArrival,lastArrival);
        }
        return lastMaxArrival+1;
    }
}