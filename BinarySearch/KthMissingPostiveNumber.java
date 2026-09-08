class Solution {
    public int findKthPositive(int[] arr, int k) {
        //since the array is sorted. if we subtract the curr value in the 
        // in the index with the index+1 - we get the missing numbers.
        //if missing numbers >= k then we found a point. form where we can 
        //find out the what is the kth missing number. 
        // to find out the steps to take from the where missing numbers >= k.
        // we need to subtract the value - kth -1. to get the steps. 
        // the subtract the steps form the value.
        // if >=k is not present. then take n-1 value and get the missing elemnts and find the reminaing steps to get the kth missing elemnt by subtracitons. 
        // to do this in logn time complexity.
        // we use binary search.
        // high will end up. at missingnumber <k. 
        // get the missing numbers at high =
        // (arr[high]-high+1)
        // subtract it from the k, to get the steps to take from the high.
        // k-(arr[high]-(high+1))
        // to get actual value we need to add these steps to the arr[high]
        //arr[high] + k - arr[high] + (high+1)
        //k+high+1
        //high+1 = low
        //k+low!!
        int n = arr.length;
        int low = 0;
        int high = n-1;
        while(low<=high){
            int mid = (low+high)/2;
            if((arr[mid]-(mid+1))>=k){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return low+k;

    }
}