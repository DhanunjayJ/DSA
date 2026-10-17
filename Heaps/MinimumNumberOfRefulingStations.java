class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        //we maitain a additive fuel varibale that will 
        //help in telling us what is the maximum possible
        // reachable stations with the current fuel.
        int currentMaxReachable = startFuel;
        int i = 0;
        int n = stations.length;
        int ans = 0;
        //loop untill <target
        while(currentMaxReachable < target){
            //add all possible stations that can be reached
            //from withing the max reach. 
            // then add them to priorityQueue.
            while(i<n && stations[i][0]<=currentMaxReachable){
                pq.add(stations[i][1]);
                i++;
            }
            //if pq is empty and we hanv't reached the target 
            // yet then we return -1.
            if(pq.isEmpty()) return -1;
            //take the largest feul that is availabel within in
            // the reach so we will minimze the taken fuel count. 
            currentMaxReachable += pq.poll();
            ans++;
        }
        return ans;
    }
}