import java.util.PriorityQueue;

public class KthLargestInStream {
    class KthLargest {

    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int kl = 0;
    public KthLargest(int k, int[] nums) {
        pq = new PriorityQueue<>();
        kl = k;
        for(int num:nums){
        if(pq.size()<k){
            pq.add(num);
            continue;
        }
        if(pq.peek()<num){
            pq.poll();
            pq.offer(num);
        }
        }
    }
    
    public int add(int val) {
        if(pq.size()<kl){
            pq.add(val);
        }else if (pq.peek()<val){
            pq.poll();
            pq.offer(val);
        }
        return pq.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
}


// DRY way
class KthLargest {

    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int kl = 0;
    public KthLargest(int k, int[] nums) {
        pq = new PriorityQueue<>();
        kl = k;
        for(int num:nums){
           add(num);
        }
    }
    
    public int add(int val) {
        pq.offer(val);
        if (pq.size()>kl){
            pq.poll();
        }
        return pq.peek();
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */