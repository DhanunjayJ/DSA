// n*k Approach

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> (b-a));
        int n = nums.length;
        int sp = 0;
        int [] ans = new int[n-k+1];
        int i = 0;
        for(int ep=0;ep<n;ep++){ //O(n)
            pq.add(nums[ep]); //O(logn)
            if(pq.size()>k){
                pq.remove(nums[sp]); //o(n)
                sp++;
            }
            if(ep-sp+1==k){
                ans[i++] = pq.peek();
            }
        }
        return ans;
    }
}

//nlogn approach

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        //Store index and value
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(b[1],a[1]));

        int n = nums.length;
        int [] ans = new int[n-k+1];
        int i = 0;
        for(int ep=0;ep<n;ep++){
            pq.add(new int[]{ep,nums[ep]});
            if(ep>=k-1){
                int sp = ep-k+1;
                while(!pq.isEmpty() && pq.peek()[0]<sp){
                    pq.poll();
                }
                ans[i++] = pq.peek()[1];
            }
        }
        return ans;
    }
}


// Block Decomposition (Prefix/Suffix Maxima within Blocks O(n) with O(n) space
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(k==1) return nums;
        int n = nums.length;
        //left[i] = Max from the START of each block going RIGHT
        int [] left = new int[n];
        //right[i] = Max from the End of each block going left
        int [] right = new int[n];

        for(int i=0;i<n;i++){
            if(i%k==0){
                left[i] = nums[i];
            }else{
                left[i] = Math.max(left[i-1],nums[i]);
            }
        }

        for(int j=n-1;j>=0;j--){
            // take the example of the k=3
            // 0 1 2 | 3 4 5 | 6 7 
            // end of the block can easily recognized by j+1%k so we do j+1 
            if(j==n-1 || (j+1)%k==0){
                right[j] = nums[j];
            }else{
                right[j] = Math.max(right[j+1],nums[j]);
            }
        }

        int [] ans = new int[n-k+1];

        for(int i=0;i<=n-k;i++){
            int j = i+k-1;
            // right [i] will give us the maximum from the right of the 
            //block to the i
            //left[j] willl give us the maximum of the block from the left. to j.
            //so taking the maximum of both will give us the maximum
            //of the block.
            ans[i] = Math.max(right[i],left[j]);
        }

        return ans;
    }
}



class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;

        int [] ans = new int[n-k+1];

        int j = 0;

        Deque<Integer> q = new ArrayDeque<>();

        q.add(0);

        for(int i=1;i<k;i++){

            //Monotonic Decreasing Queue
            while(q.size()>0 && nums[q.getLast()]<nums[i]){
                q.removeLast();
            }

            q.addLast(i);

        }
        ans[0] = nums[q.getFirst()];

        j++;
        int start = 0;

        for(int end=k;end<n;end++){
            //remove if this index is not valid.
            if(q.getFirst()==start){
                q.removeFirst();
            }
            start++;

            //Monotonic Decreasing Queue
            while(q.size()>0 && nums[q.getLast()]<nums[end]){
                q.removeLast();
            }

             q.addLast(end);

            ans[j++] = nums[q.getFirst()];
        }
        return ans;
    }
}





//refactored code
public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] ans = new int[n - k + 1];
    Deque<Integer> q = new ArrayDeque<>();
    
    for (int i = 0; i < n; i++) {
        // 1. Remove indices that are out of the current window range
        if (!q.isEmpty() && q.peekFirst() < i - k + 1) {
            q.pollFirst();
        }
        
        // 2. Maintain monotonic property (remove smaller elements from back)
        while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
            q.pollLast();
        }
        
        q.offerLast(i);
        
        // 3. Start adding to results once we've hit the window size k
        if (i >= k - 1) {
            ans[i - k + 1] = nums[q.peekFirst()];
        }
    }
    return ans;
}