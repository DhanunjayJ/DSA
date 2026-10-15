class Solution {
    public int[] kWeakestRows(int[][] mat, int k) {
        int n = mat.length;
        int m = mat[0].length;
        int [] count = new int[n];
        for(int i=0;i<n;i++){
            int c = 0;
            for(int j=0;j<m;j++){
                if(mat[i][j]==0) c++;
            }
            count[i]=c;
        }
        //priorityQueue to maintain what to remove first if the
        //the values become more than k.
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> {
            //if the count[a]==count[b] then we need to remove
            // the larger index so we sort in decending order.
            if(count[a]==count[b]) return Integer.compare(b,a);
            //if the count not same we always remove the k strongest (less c value)
            //if the values of the pq are more than k. 
            // meaning the count haivng the value less. so we sort it
            // in ascending order. 
            return Integer.compare(count[a],count[b]);
        });
        for(int i=0;i<n;i++){
            pq.add(i);
            if(pq.size()>k) pq.poll();
        }
        int [] ans = new int[k];
        //once we have the values we have the kth weakest first.
        //since the values int the pq are in ascending order of the weakest. 
        //we reverse and keep the kth strongest the first. 
        for(int i=k-1;i>=0;i--){
            ans[i] = pq.poll();
        }
        return ans;
    }
}