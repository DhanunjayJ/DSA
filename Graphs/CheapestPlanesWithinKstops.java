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

