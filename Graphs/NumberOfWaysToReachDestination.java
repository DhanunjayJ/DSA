import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class NumberOfWaysToReachDestination {
    
class Solution {

    public class Pair {
        int node;
        long time;

        Pair(int node, long time) {
            this.node = node;
            this.time = time;
        }
    }

    int MOD = 1_000_000_007;

    public int countPaths(int n, int[][] roads) {
        /*
        Here we maintain a two arrays 
        dist
        and ways.
        
        First we do find the shortest dist from 0 to i. 
        then once find it. using dijkstra
        
        if(dist[v]>dist[u]+time) update the time and ways of parnet will be assigned
        if(dist[v]==dist[u]+time) update the ways = curr + parent ways. 
        
        */

        long[] dist = new long[n];
        long[] ways = new long[n];

        //bild the graph. since this is a bidirectional grpah.
        //if we build a bidirectional. we only move from the parent to child
        //since we reach like a dag. and only update if dist[v] > currnt
        //it won't affect

        List<List<Pair>> adj = new ArrayList<>();

        for(int i=0;i<n;i++) adj.add(new ArrayList<>());

        for (int i = 0; i < roads.length; i++) {
            int u = roads[i][0];
            int v = roads[i][1];
            int time = roads[i][2];
            adj.get(u).add(new Pair(v, time));
            adj.get(v).add(new Pair(u, time));
        }

        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        ways[0] = 1;

        //[destination,cummulative_time]
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));

        pq.add(new long[] {0, 0});

        while (!pq.isEmpty()) {

            long[] rem = pq.remove();

            int u = (int) rem[0];
            long ctime = rem[1];

            if(ctime > dist[u]) continue;

            for (Pair nbr : adj.get(u)) {
                int v = nbr.node;
                long time = nbr.time;

                if(dist[v]>dist[u]+time){
                    dist[v] = dist[u] + time;
                    ways [v] = ways[u];
                    pq.add(new long[]{v,dist[v]});
                }else if(dist[v]==dist[u]+time){
                    ways[v] = (ways[v]+ways[u])%MOD;
                }
            }

        }

        return (int) ways[n - 1];

    }
}
}
