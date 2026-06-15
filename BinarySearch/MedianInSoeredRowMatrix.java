class Solution {
    public int median(int[][] mat) {
        // code here
        /*
        Here the rows are sorted and the median will always be 
        the one since the number of elements are odd.
        */
        
        int n = mat.length;
        int m = mat[0].length;
        
        int median = (n*m+1)/2;
        
        //now we need to find the values that are less than equal to the median
        //number of times.
        
        int low = 20001;
        int high = 0;
        
        int ans = 0;
        
        for(int i=0;i<n;i++){
            low = Math.min(mat[i][0],low);
            high = Math.max(mat[i][m-1],high);
        }
        
        while(low<=high){
            
            int mid = (low+high)/2;
            
            //find all the values that are less than the mid.
            int countLess = 0;
            
            for(int i=0;i<n;i++){
            int rlow = 0;
            int rhigh = m-1;
            
            while(rlow<=rhigh){
                int rmid = (rlow+rhigh)/2;
                if(mat[i][rmid]<=mid){
                  rlow = rmid+1;
                }else{
                  rhigh = rmid-1;
                }
            }
            
            countLess += rlow;
            }
            
            
            //now check if the found mid is possible median
            //find the minimum possible number that satifies the condition.
            //monotonically decresing side. 
            
            if(countLess >= median){
               high = mid-1;
            }else{
               low = mid+1;
            }
            
        }
        
        return low;
    }
}