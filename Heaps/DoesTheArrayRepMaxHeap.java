class Solution {

    public boolean countSub(long arr[], long n) {
        // Your code goes here
        for(int i=0;i<arr.length;i++){
            /*
            Check if the left child or the right child violates the rule of the maxheap
            which is greater the current index in for each ith index.
            */
            int leftchild = 2*i+1;
            int rightchild = 2*i+2;
            if(leftchild<n && arr[leftchild]>arr[i]){
                return false;
            }
            if(rightchild<n && arr[rightchild]>arr[i]){
                return false;
            }
        }
        return true;
    }
}