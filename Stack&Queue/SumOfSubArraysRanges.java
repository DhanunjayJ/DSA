class Solution {
    public long subArrayRanges(int[] nums) {
        // here instead of thinking in the way of subarray
        // we think in the way of like
        // In how many subarrays the current elemtn min is min and 
        // how many subarrays the current elemtn max is max.
        // once we find that out we can subtract both of them. 
        return sumRange(nums,true) - sumRange(nums,false);
    }
    public long sumRange(int [] nums, boolean isMax){
        // we us the same function to do this problem. 
        Deque <Integer> st = new ArrayDeque<>();
        long sum = 0;
        int n = nums.length;
        // here to handle the edge case (int case of maximum finding) then the values 
        // will be int he decresing vlaues. to handle all of them we add Max_value at the last
        // same for the min -> where the stack will be int eh increasing order we add min_value;
        for(int i=0; i<=n; i++){
            long val = i==n ? (isMax ? Integer.MAX_VALUE : Integer.MIN_VALUE) : nums[i];
            // if it is is max -> we maintain the Monotonic Decreasing Stack
            // if it is min -> we maintain the  Monotonic Increasing Stack. 
            // the condidiont of the max amd min.
            while(!st.isEmpty() && (isMax ? val>nums[st.peek()] : val<nums[st.peek()])){
                int mid = st.pop();
                // if there is not boundary then -1.
                long leftBoundary = st.isEmpty() ? -1 : st.peek();
                long rightBoundary = i;
                // no of startpoits * noof end points -> subarrays.
                long subarrays = (mid-leftBoundary) * (rightBoundary-mid);
                long contribution = subarrays*nums[mid];
                sum += contribution;
            }
            st.push(i);
        }
        return sum;
    }
}