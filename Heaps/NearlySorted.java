class Solution {
    //https://www.geeksforgeeks.org/problems/nearly-sorted-1587115620/1?track=amazon-heap&batchId=192
    public void nearlySorted(int[] arr, int k) {
        // code here
        int n = arr.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int i=0;i<=k;i++) pq.add(arr[i]);
        int ans [] = new int[n];
        int j = 0;
        for(int i=k+1;i<n;i++){
            arr[j++] = pq.remove();
            pq.add(arr[i]);
        }
        while(j<n)arr[j++] = pq.remove();
    }
}