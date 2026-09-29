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