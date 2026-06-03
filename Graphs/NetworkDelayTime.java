class Pair {
    int node;
    int time;

    Pair(int node, int time) {
        this.node = node;
        this.time = time;
    }
}

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<Pair>> graph = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        int[] indegree = new int[n + 1];

        for (int[] time : times) {
            int u = time[0];
            int v = time[1];
            int ctime = time[2];
            indegree[v]++;
            graph.get(u).add(new Pair(v, ctime));
        }

        int[] ans = new int[n + 1];
        Arrays.fill(ans, Integer.MAX_VALUE);
        //topological sort will not work if there is a cycle
        //we need to do dijstra and only visit the nodes that are having minium
        // once we visit all the nodes where the visited count == n
        // we stop.
       // Use a proper boolean array to know when a node's shortest path is finalized
        boolean[] visited = new boolean[n + 1];
        int visitedCount = 0;

        // Initialize source
        ans[k] = 0;

        PriorityQueue<int[]> q = new PriorityQueue<>((a,b) -> Integer.compare(a[1],b[1]));

        q.add(new int[]{k,0});

        while(!q.isEmpty()){
            int [] rem = q.remove();
            
            int node = rem[0];
            int time = rem[1];

            // BUG FIX 1: If we've already finalized this node, ignore duplicate older entries in PQ
            if (visited[node]) continue;
            
            // Finalize this node
            visited[node] = true;
            visitedCount++;

            // OPTIMIZATION: If we've finalized all N nodes, we can stop early
            if (visitedCount == n) break;

            for(Pair nbr : graph.get(node)){
// BUG FIX 2: Only queue the neighbor if this new path is shorter than what we found before
                if (!visited[nbr.node] && time + nbr.time < ans[nbr.node]) {
                    ans[nbr.node] = time + nbr.time;
                    q.add(new int[]{nbr.node, ans[nbr.node]});
                }
            }

        }

        int max = 0;
        for(int i=1;i<=n;i++){
            max = Math.max(max,ans[i]);
        }
        return max==Integer.MAX_VALUE ? -1 : max;
    }
}