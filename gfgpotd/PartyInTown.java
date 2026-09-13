
class Solution {
    public int partyHouse(ArrayList<ArrayList<Integer>> adj) {
        int n = adj.size();
        if (n <= 1) return 0;

        // Step 1: Find the farthest node from an arbitrary node (e.g., node 1)
        int[] firstBfs = getFarthestAndDist(adj, 1, n);
        int u = firstBfs[0];

        // Step 2: Find the farthest node and maximum distance (diameter) from u
        int[] secondBfs = getFarthestAndDist(adj, u, n);
        int diameter = secondBfs[1];

        // Step 3: Minimum maximum distance is (diameter + 1) / 2
        return (diameter + 1) / 2;
    }

    private int[] getFarthestAndDist(ArrayList<ArrayList<Integer>> adj, int start, int n) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);

        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        dist[start] = 0;

        int farthestNode = start;
        int maxDist = 0;

        while (!q.isEmpty()) {
            int curr = q.poll();

            if (dist[curr] > maxDist) {
                maxDist = dist[curr];
                farthestNode = curr;
            }

            for (int nbr : adj.get(curr - 1)) {
                if (dist[nbr] == -1) {
                    dist[nbr] = dist[curr] + 1;
                    q.add(nbr);
                }
            }
        }

        return new int[]{farthestNode, maxDist};
    }
}