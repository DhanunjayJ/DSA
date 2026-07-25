//https://www.geeksforgeeks.org/problems/row-with-max-1s0023/1

class Solution {
    public int rowWithMax1s(int[][] arr) {
        // code here
        /*
        We need to find the max ones. we could first do it using hashmaps.
        and count the values that would take on(n2) tc and sc.
        
        to make it optimize to nlogm
        we could do binary search in sinc eht each row is in ascending rder
        we could find out the first one zero element. for each row.
        
        and update the one if that minIndex we found in this row is the less
        than the previuls ones.
        
        */
        
        int n = arr.length;
        int m = arr[0].length;
        
        int jIndexTillNow = m-1;
        int minRow = -1;
        
        for(int i=0;i<n;i++){
            int low = 0;
            int high = m-1;
            while(low<=high){
                int mid = (low+high)/2;
                if(arr[i][mid]!=0){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }
            if(low<jIndexTillNow){
                minRow = i;
                jIndexTillNow = low;
            }
        }
        
        return minRow;
    }
};