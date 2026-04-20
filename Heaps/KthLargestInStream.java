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



//Using quick Select 3 way parittion dutch national flag algo for handling duplicates

class Solution {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        int target = n-k;
        return quickSelect(nums,0,n-1,target);
    }
    public int quickSelect(int []nums,int left,int right,int target){
        if(left==right)return nums[left];

        int [] range  = partition(nums,left,right);

        int lt = range[0];
        int gt = range[1];

        if(target>=lt && target<=gt){
            return nums[target];
        }else if(target>lt){
            return quickSelect(nums,gt+1,right,target);
        }else{
           return quickSelect(nums,left,lt-1,target);
        }
    }

    public int[] partition(int []nums,int left,int right){
        int pivotIndx = left+(int)(Math.random()*(right-left+1));
        int pivot = nums[pivotIndx];

        int lt = left;
        int gt = right;
        int i = left;

        while(i<=gt){
            if(nums[i]<pivot){
                swap(nums,i++,lt++);
            }else if(nums[i]>pivot){
                swap(nums,i,gt--);
            }else{
                i++;
            }
        }
        return new int[]{lt,gt};
    }

    public void swap(int [] nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}