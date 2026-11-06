class Pair {
    int node;
    int price;
    Pair(int node,int price){
        this.node = node;
        this.price = price;
    }
}
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<Pair>> adj = new ArrayList<>();

        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }

        for(int i=0;i<flights.length;i++){
            int u = flights[i][0];
            int v = flights[i][1];
            int price = flights[i][2];
            adj.get(u).add(new Pair(v,price));
        }

        int [] minPrice = new int[n];
        Arrays.fill(minPrice,Integer.MAX_VALUE);

        Queue<int[]> q = new LinkedList<>();

        q.add(new int[]{src,0,0});

        while(!q.isEmpty()){

            int [] rem = q.remove();

            int currNode = rem[0];
            int currPrice = rem[1];
            int currStops = rem[2];

            for(Pair nbr : adj.get(currNode)){
                //if currStops == k -> the this nbr needs to the dist.
                //if we jsut update but don't add it the queue. 
                //if currstops <k we update and add to the queue. 
                int nextPrice = currPrice + nbr.price;
                if(currStops<k){
                    if(minPrice[nbr.node]>nextPrice){
                        minPrice[nbr.node] = nextPrice;
                        q.add(new int[]{nbr.node,nextPrice,currStops+1});
                    }
                }else if(currStops==k){
                    if(nbr.node==dst && minPrice[nbr.node]>nextPrice){
                        minPrice[nbr.node] = nextPrice;
                    }
                }
            }
        }

        return minPrice[dst] == Integer.MAX_VALUE ? -1 : minPrice[dst];
    }
}



//BFS Level order Traversal

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        /*traverse level by level once we reach the 
        dist we stops k we stop and check if we have reached the distination or not.
        
        Here bfs helps in go and update the value level by level and at each level
        we do update if this is min dist that previos and only add if it is the case. 
        */

        List<List<int[]>> adj = new ArrayList<>();

        for(int i=0;i<n;i++) adj.add(new ArrayList<>());

        for(int f[] : flights) adj.get(f[0]).add(new int[]{f[1],f[2]});

        int [] minDist = new int[n];
        Arrays.fill(minDist,Integer.MAX_VALUE);
        minDist[src] = 0;

        int stops = 0;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{src,0});

        while(stops<=k && !q.isEmpty()){
            int size = q.size();
            //iterate on the current level
            while(size-- > 0){
                int [] rem = q.poll();
                int node = rem[0];
                int dist = rem[1];

                for(int[] nbr : adj.get(node)){
                    int v = nbr[0];
                    int cost = nbr[1];
                    if(minDist[v]>dist+cost){
                        minDist[v] = dist+cost;
                        q.add(new int[]{v,minDist[v]});
                    }
                }
            }
            stops++;
        }

        return minDist[dst]==Integer.MAX_VALUE ? -1 : minDist[dst];
    }
}


//Dijstra Appraoch with 1D space

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        //dijkstra
        List<List<int[]>> adj = new ArrayList<>();
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
        
        for(int [] f : flights) adj.get(f[0]).add(new int[]{f[1],f[2]});
        
        int [] minStops = new int[n];
        
        Arrays.fill(minStops,Integer.MAX_VALUE);
        minStops[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[1],b[1]));
        //src,dist,stops
        pq.add(new int[]{src,0,0});

        while(!pq.isEmpty()){
            
            int [] rem = pq.remove();
            
            int u = rem[0];
            int dist = rem[1];
            int stops = rem[2];

            //if we already has a path that is min stops
            //and since pq is gurantee that path is min cost.
            //then skip or if the stops are >k+1
            if(stops>minStops[u] || stops>k+1)
            continue;

            minStops[u] = stops;

            if(u==dst) return dist;

            for(int [] nbr : adj.get(u)){
                pq.add(new int[]{nbr[0],nbr[1]+dist,stops+1});
            }

        }

        return -1;
    }
}


//dijstra 2d state space approahc

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        List<List<int[]>> adj = new ArrayList<>();
        
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
        
        for(int [] f : flights){
            adj.get(f[0]).add(new int[]{f[1],f[2]});
        }
        
        //state space
        //becase the stops ranges from 0 to k+1
        
        int [][] dist = new int[n][k+2];
        
        for(int i=0;i<n;i++) Arrays.fill(dist[i],Integer.MAX_VALUE);
        //node,dst,stops
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[1],b[1]));
        
        pq.add(new int[]{src,0,0});
        
        dist[src][0] = 0;

        while(!pq.isEmpty()){

            int [] rem = pq.remove();

            int node = rem[0];
            int cost = rem[1];
            int stops = rem[2];

            if(node==dst) return cost;
            if(stops>k) continue;
            if(cost>dist[node][stops]) continue;

            for(int [] nbr : adj.get(node)){

                int nnode = nbr[0];
                int ncost = nbr[1]+cost;
                int nstops = stops+1;

                if(ncost<dist[nnode][nstops]){
                    dist[nnode][nstops] = ncost;
                    pq.add(new int[]{nnode,ncost,nstops});
                }
            }
        }

        return -1;
    }
}

//BellmonFord Approach

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        /*
        Using bell man ford, Bell mon ford using n-1 stps to find the shortest path
        from the source destiation
        since we are here having only k, we can go till k+1 stops. 

        here. 
        */

        int [] dist = new int[n];
        Arrays.fill(dist,Integer.MAX_VALUE);
        dist[src] = 0;
        for(int i=0;i<=k;i++){
            //if we don't create a copy here it will go to infinate
            //loop
            int [] temp = Arrays.copyOf(dist,n);
            for(int [] f : flights){
               if(dist[f[0]]!=Integer.MAX_VALUE && temp[f[1]]>dist[f[0]]+f[2]){
                //we only update temp first.
                //not allowing the same level edges to use the previous 
                //answer. while comparing
                temp[f[1]] = dist[f[0]]+f[2];
               }
            }
            dist = temp;
        }

        return dist[dst]==Integer.MAX_VALUE ? -1 : dist[dst];
    }
}

