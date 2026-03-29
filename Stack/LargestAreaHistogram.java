class Solution {
    public int largestRectangleArea(int[] heights) {
        Deque <Integer> st = new ArrayDeque<>();
        int maxArea = 0;
        int n = heights.length;
        for(int i=0;i<=n;i++){
            int val = i==n ? 0 : heights[i];
            while(!st.isEmpty() && val<heights[st.peek()]){
                int height = st.pop();
                int leftBoundary = st.isEmpty() ? -1 : st.peek();
                int rightBoundary = i;
                int width = (rightBoundary-leftBoundary-1);
                int area = width*heights[height];
                maxArea = Math.max(area,maxArea);
            }
            st.push(i);
        }
        return maxArea;
    }
}