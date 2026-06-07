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



    //another way 

    class Solution {

    private static final int MOD = 1_000_000_007;

    public int countPaths(int n, int[][] roads) {
        long[][][] dp = new long[n][n][2];

        // dp[src][dest][0] stores the minimum time between src and dest
        // dp[src][dest][1] stores the number of ways to reach dest from src
        // with the minimum time

        // Initialize the dp table
        for (int src = 0; src < n; src++) {
            for (int dest = 0; dest < n; dest++) {
                if (src != dest) {
                    // Set a large initial time
                    dp[src][dest][0] = (long) 1e12;
                    // No paths yet
                    dp[src][dest][1] = 0;
                } else {
                    // Distance from a node to itself is 0
                    dp[src][dest][0] = 0;
                    // Only one trivial way (staying at the node)
                    dp[src][dest][1] = 1;
                }
            }
        }

        // Initialize direct roads from the input
        for (int[] road : roads) {
            int startNode = road[0], endNode = road[1], travelTime = road[2];
            dp[startNode][endNode][0] = travelTime;
            dp[endNode][startNode][0] = travelTime;
            // There is one direct path
            dp[startNode][endNode][1] = 1;
            // Since the roads are bidirectional
            dp[endNode][startNode][1] = 1;
        }

        // Apply the Floyd-Warshall algorithm to compute shortest paths
        // Intermediate node
        for (int mid = 0; mid < n; mid++) {
            // Starting node
            for (int src = 0; src < n; src++) {
                // Destination node
                for (int dest = 0; dest < n; dest++) {
                    // Avoid self-loops
                    if (src != mid && dest != mid) {
                        long newTime = dp[src][mid][0] + dp[mid][dest][0];

                        if (newTime < dp[src][dest][0]) {
                            // Found a shorter path
                            dp[src][dest][0] = newTime;
                            dp[src][dest][1] =
                                (dp[src][mid][1] * dp[mid][dest][1]) % MOD;
                        } else if (newTime == dp[src][dest][0]) {
                            // Another way to achieve the same shortest time
                            dp[src][dest][1] =
                                (dp[src][dest][1] +
                                    dp[src][mid][1] * dp[mid][dest][1]) %
                                MOD;
                        }
                    }
                }
            }
        }

        // Return the number of shortest paths from node (n-1) to node 0
        return (int) dp[n - 1][0][1];
    }
}
}