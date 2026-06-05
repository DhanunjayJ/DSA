import java.util.*;

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
        long[] dist = new long[n];
        long[] ways = new long[n];

        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            long time = road[2];
            adj.get(u).add(new Pair(v, time));
            adj.get(v).add(new Pair(u, time));
        }

        // Initialize with a safe, true infinity
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        ways[0] = 1;

        // Priority Queue stores: [node, current_accumulated_distance]
        // Sorted cleanly by accumulated distance (64-bit safe)
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.add(new long[] { 0, 0 });

        while (!pq.isEmpty()) {
            long[] rem = pq.remove();
            int u = (int) rem[0];
            long d = rem[1];

            // Stale check: If we already found a strictly shorter path to u, 
            // discard this older, longer path extraction.
            if (d > dist[u]) continue;

            for (Pair nbr : adj.get(u)) {
                int v = nbr.node;
                long time = nbr.time;

                // Scenario A: Found a strictly shorter path to neighbor 'v'
                if (dist[v] > dist[u] + time) {
                    dist[v] = dist[u] + time;
                    ways[v] = ways[u]; 
                    pq.add(new long[] { v, dist[v] }); // Push the new shorter accumulated distance
                } 
                // Scenario B: Found an alternative path to 'v' with the exact same shortest time
                else if (dist[v] == dist[u] + time) {
                    ways[v] = (ways[v] + ways[u]) % MOD;
                    // NO need to push to PQ again here, as the minimum distance didn't change!
                }
            }
        }

        return (int) ways[n - 1];
    }
}