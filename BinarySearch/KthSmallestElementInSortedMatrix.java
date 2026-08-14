  public int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
     int low = Integer.MAX_VALUE;
     int high = Integer.MIN_VALUE;
     for(int i=0;i<n;i++){
        low = Math.min(matrix[i][0],low);
        high = Math.max(matrix[i][n-1],high);
     }
     int ans = 0;
     while(low<=high){
        int mid = low+(high-low)/2;
        //find how many elements are less than the mid;
        int count = 0;
        for(int i=0;i<n;i++){
            int indx = -1;
            int rLow = 0;
            int rHigh = n-1;
            while(rLow<=rHigh){
                int rMid = (rLow+rHigh)/2;
                if(matrix[i][rMid]<=mid){
                    indx = rMid;
                    rLow = rMid+1;
                }else{
                    rHigh = rMid-1;
                }
            }
            if(indx!=-1) count += indx+1;
        }

        if(count<k){
            low = mid+1;
        }else{
            ans = mid;
            high = mid-1;
        }
     }
     return ans;
    }
}