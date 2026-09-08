class Solution {
    public int kthElement(int a[], int b[], int k) {
        // code here
        int n = a.length;
        int m = b.length;
        
        if(n>m) return kthElement (b,a,k);
        

        //If  k > m : Array b does not even have enough elements to satisfy  k  on its own.
        //so we need atleast k-m elements from the first array.  to avoid over flow to b
        int low = Math.max(0,k-m);
        //If k < n : You only need  k  total elements in the left partition. Even if array a has  n = 10 
        // elements, if  k = 3 , you will never take more than 3 elements from a. to avaoid underflow to b.
        int high = Math.min(n,k);
        
        while(low<=high){
            int i = low+(high-low)/2;
            int j = k-i;
            
            int left1 = (i==0) ? Integer.MIN_VALUE : a[i-1];
            int right1 = (i==n) ? Integer.MAX_VALUE : a[i];
            int left2 = (j==0) ? Integer.MIN_VALUE  : b[j-1];
            int right2 = (j==m) ? Integer.MAX_VALUE : b[j];
            
            if(left1<=right2 && left2<=right1){
                return Math.max(left1,left2);
            }else if(left1>right2){
                high = i-1;
            }else{
                low = i+1;
            }
        }
        return -1;
    }
}