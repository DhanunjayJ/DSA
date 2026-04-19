import java.util.HashMap;
import java.util.PriorityQueue;

public class TopKFrequentElements {
    class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        HashMap<Integer,Integer> hm = new HashMap<>();

        for(int num:nums){
            hm.put(num,hm.getOrDefault(num,0)+1);
        }

        // since we need max ones if we keep the max ones only the max queue we need to sort it
        // if we use min queue then we can get the max ones directly and we only push it tif the
        //current one is greater that the top one. 

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> Integer.compare(hm.get(a),hm.get(b)));

        for(int num:hm.keySet()){
            pq.offer(num);
            if(pq.size()>k){
                pq.poll();
            }
        }

        int [] ans = new int[k];
        int i = 0;
        while(pq.size()>0){
            ans[i++] = pq.poll();
        }
        return ans;
    }
}
}
