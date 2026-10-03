class Solution {
    public int shortestSubarray(int[] nums, int k) {
        //[84,-37,32,40,95] 167
        int n = nums.length;
        int [] psum = new int[n+1];
        //psum[i] = it is the sum of everything from index 0 up to index i - 1:
        for(int i=0;i<n;i++){
            psum[i+1] = nums[i]+psum[i];
        }
        Deque<Integer> q = new ArrayDeque<>();
        int min = Integer.MAX_VALUE;
        for(int j=0;j<=n;j++){
            // check if the if current prefix create a valid sub array. 
            while(!q.isEmpty() && psum[j]-psum[q.peekFirst()]>=k){
                //we poll this value from the array, since it won't
                //be giving us much better answer in the future if we 
                // keep this index. 
                min = Math.min(j-q.pollFirst(),min);
            }
            //if the currsum <= psum[last]
            //then for the future sum say x.
            //will always yeild better higher chances
            // of p[x]-p[j]>=x over the last
            // and alos it will give better shorter length.
            while(!q.isEmpty() && psum[j]<=psum[q.peekLast()]){
                q.pollLast();
            }
            q.addLast(j);
        }
        return min==Integer.MAX_VALUE ? -1 : min;
    }
}

// using priorityQueue nlogn

class Solution {
    public int shortestSubarray(int[] nums, int k) {
        //p[j]-p[i]>=k
        //j-i = minimum possible.
        PriorityQueue<long[]> pq = new PriorityQueue<>((a,b) -> Long.compare(a[0],b[0]));
        pq.add(new long[]{0L,-1});
        long prefix = 0;
        int minLen = Integer.MAX_VALUE;
        for(int j=0;j<nums.length;j++){
            prefix += nums[j];
            while(!pq.isEmpty() && prefix-pq.peek()[0]>=k){
                minLen = Math.min(minLen,j-(int)pq.poll()[1]);
            }
            pq.offer(new long[]{prefix,j});
        }
        return minLen==Integer.MAX_VALUE ? -1  : minLen;
    }
}