class Solution {
    public int maximalRectangle(char[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        int [] maxheights = new int[m];

        for(int i=0;i<m;i++){
            if(matrix[0][i]=='1'){
                maxheights[i] = 1;
            }
        }

        int maxArea = maximumHist(maxheights);

        for(int i=1;i<n;i++){

            for(int j=0;j<m;j++){
                if(matrix[i][j]=='1'){
                    maxheights[j]++;
                }else{
                    maxheights[j] = 0;
                }
            }

            maxArea = Math.max(maximumHist(maxheights),maxArea);
        }

        return maxArea;
    }

    public int maximumHist(int [] arr){
        Deque<Integer> st = new ArrayDeque<>();
        int maxArea = 0;
        int n = arr.length;
        for(int i=0;i<=n;i++){
            int val = i==n ? 0 : arr[i];
            while(!st.isEmpty() && val<arr[st.peek()]){
                int valIdx = st.pop();
                int leftB = st.isEmpty() ? -1 : st.peek();
                int rightB = i;
                int width = rightB-leftB-1;
                int area = width*arr[valIdx];
                maxArea = Math.max(area,maxArea);
            }
            st.push(i);
        }
        return maxArea;
    }
}