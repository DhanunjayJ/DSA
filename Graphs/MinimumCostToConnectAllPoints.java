import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimumCostToConnectAllPoints {

    /// O(n2 logn)
    class Solution {
    public int minCostConnectPoints(int[][] points) {
        /*
        We need to get the min cost to connect all the ndoes. this is the problem
        of minimum spanning tree. 

        so, what we do here? 

        We start from a one un visited node. and add all the possible edeges that it could
        make from the avaible points.

        using the priorityqueue we get the in edge out of all of them.

        once we have it then, get the it by removing it and the add the weight of that 
        edge to the ans varible and mark that node as visited. 

        then repeat the same steps for the all the other points in the graph untill either 
        the priorty qeue becomes empty or all the edges are connected.

        oce done get out to of the loop and return the min connecting weight;

        here since the points are x,y and are there directly  in the input.
        we take the index of the points as a node. 
        */

        int n = points.length;
        boolean [] isInMST = new boolean[n];
        //a priority queue for the src(index), edge weight.
        //sort it based on the weight
        PriorityQueue<int[]> pq = new  PriorityQueue<>((a,b) -> Integer.compare(a[1],b[1]));
        pq.add(new int[]{0,0});
        //we start from the zeroth index.
        int edgesConnected = 0;
        //we need to do this looop untill edgesConnected==n-1;
        int mstCost = 0;

        while(!pq.isEmpty() && edgesConnected<n){
            int [] curr = pq.remove();
            
            int node = curr[0];

            //if this node is already visited we don't consider this
            //we skip
            if(isInMST[node]) continue;

            isInMST[node] = true;
            mstCost += curr[1];
            edgesConnected++;

            for(int i=0;i<n;i++){
                //we only check for the nodes that are not in the mst.
                // we calculte the distance dynamically. we don't construct the graph. here. 
                if(!isInMST[i]){
                    int weight = Math.abs(points[node][0]-points[i][0]) +
                                Math.abs(points[node][1]-points[i][1]);
                    pq.add(new int[]{i,weight});
                }
            }
        }
        return mstCost;
    }
}


//o(n2)

class Solution {
    public int minCostConnectPoints(int[][] points) {
        /*
        Since using th priorityQueue is a massive addition of edges that are redunant and alreayd
        connected that give time complexity of O(n^2 logn) we can do this in n^2 with a
        min cost to a node from a current contrcutred mintree. 

        by doing so, we are just maintaing the min cost to edges and no extra edges
        or grah. 

        we try to update that min cost every iteration.

        and we will try to pick the min cost out of all to start the mst.

        this way we will build the graph what i needed.

        */

        int v = points.length;
        //this stores the min cost to rewach the currently viited mst.
        int [] minCost = new int[v];
        Arrays.fill(minCost,Integer.MAX_VALUE);
        minCost[0] = 0;
        boolean [] isInMst = new boolean[v];
        int mstCost = 0;

        //there will be exactly v-1 edges so we just need to iteravte that 
        for(int step=0;step<v;step++){
            int u = -1;
            //we are try to find the min cost edge over all the edges 
            //ehre if not thing there only one we take the fist unvisited node.
            for(int i=0;i<v;i++){
                if(!isInMst[i] && (u==-1 || minCost[i]<minCost[u])){
                    u = i;
                }
            }

            isInMst[u] = true;
            mstCost += minCost[u];

            //we loop through all the availble bnodes that are not viited and
            //update the edges length if possible.
            for(int j=0;j<v;j++){
                if(!isInMst[j]){
                    int dist = Math.abs(points[j][0]-points[u][0]) + 
                                Math.abs(points[j][1]-points[u][1]);
                    if(dist<minCost[j]){
                        minCost[j] = dist;
                    }
                }
            }
        }

        return mstCost;
    }
}
}
