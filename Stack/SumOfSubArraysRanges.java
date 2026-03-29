class Solution {
    public long subArrayRanges(int[] nums) {
        return sumRange(nums,true) - sumRange(nums,false);
    }
    public long sumRange(int [] nums, boolean isMax){
        Deque <Integer> st = new ArrayDeque<>();
        long sum = 0;
        int n = nums.length;
        for(int i=0; i<=n; i++){
            long val = i==n ? (isMax ? Integer.MAX_VALUE : Integer.MIN_VALUE) : nums[i];
            // if it is is max -> we maintain the Monotonic Decreasing Stack
            // if it is min -> we maintain the  Monotonic Increasing Stack. 
            while(!st.isEmpty() && (isMax ? val>nums[st.peek()] : val<nums[st.peek()])){
                int mid = st.pop();
                long leftBoundary = st.isEmpty() ? -1 : st.peek();
                long rightBoundary = i;
                long subarrays = (mid-leftBoundary) * (rightBoundary-mid);
                long contribution = subarrays*nums[mid];
                sum += contribution;
            }
            st.push(i);
        }
        return sum;
    }
}