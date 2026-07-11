public class FindtheCity {
    /*
    In this we need to find a city that can reach the minimum number of cities within a 
    threshold distance. 

    so that means we need to find the distance from every node to every node. 

    once we have it we can easily find out if we can a paricular dissatnc by checking if 
    we can reach that or by just checking if dist to reach <= threshold dist. 

    and count and maitain the minimum and reurn the max index if we have two cities with equal min distances. 
    */
    class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int [][] dist = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i==j) dist[i][j] = 0;
                else dist[i][j] = Integer.MAX_VALUE;
            }
        }
        
        for(int i=0;i<edges.length;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            int distance = edges[i][2];
            dist[u][v] = distance;
            dist[v][u] = distance;
        }

        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(dist[i][k]!=Integer.MAX_VALUE && dist[k][j]!=Integer.MAX_VALUE
                    && dist[i][j] > dist[i][k] + dist[k][j])
                    {
                        dist[i][j] = dist[i][k]+dist[k][j];
                    }
                }
            }
        }

        int minReachable = Integer.MAX_VALUE;
        int maxIndex = Integer.MAX_VALUE;

        for(int i=0;i<n;i++){
            int reachable = 0;
            for(int j=0;j<n;j++){
                if(dist[i][j]<=distanceThreshold){
                    reachable++;
                }
            }
            if(reachable<=minReachable){
                maxIndex = i;
                minReachable = reachable;
            }
        }

        return maxIndex;
    }
}
}
