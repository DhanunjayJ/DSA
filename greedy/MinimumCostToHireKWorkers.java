 class Solution {
    class Worker {

        int quality;
        double ratio;

        Worker(int quality, int wage) {
            this.quality = quality;
            ratio = (double) wage / quality;
        }
    }

    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {

        /*
        we need to find that one rate setter base do on that other people 
        rate will be set. 

        so we calcuate the ratio.

        now having the minimum person ratio not always satisfy others
        but, having maximum always satisfies the min. 

        so we sort the values based on the rations. 

        and take each worker as rate setter and check if the q has the 
        values ==k if that is hte case. chec if the current rate setter gives
         the minimum cost so far. 

         if there are more than k then, we need to pop the high quality ones. 
         becase hvaing high quality requires more cost. 

         this way we but the final answer with minimum k. and the constraints. 
        */

        int n = quality.length;

        Worker [] workers = new Worker[n];

        for(int i=0;i<n;i++){
            workers[i] = new Worker(quality[i],wage[i]);
        }

        //sorr the array base don the ratio.
        Arrays.sort(workers,(a,b) -> Double.compare(a.ratio,b.ratio));
        //max priority queue to poll the ma quaity person when >k
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        
        // maitnatin te quality sum to easily calcuate the 
        // cost. 
        int qualitySum = 0;
        double minimumCost = Double.MAX_VALUE;

        for(Worker worker : workers){
            pq.add(worker.quality);
            qualitySum+=worker.quality;

            if(pq.size()>k){
                qualitySum -= pq.remove();
            }

            //when equals add cal cost and take the min. 
            if(pq.size()==k){
                double currCost = qualitySum * worker.ratio;
                minimumCost = Math.min(minimumCost,currCost);
            }
        }

        return minimumCost;
    }
}