class Solution {
    public int dominantPairs(int[] arr) {
        // Code here
        //2,10 1,2
        //[10, 8, 2, 1, 1, 2]
        //2,8,10  1,1,2
        int n = arr.length;
        int mid = n/2;
        Arrays.sort(arr,0,mid);
        Arrays.sort(arr,mid,n);
        int i = 0;
        int j = mid;
        int count = 0;
        for(i=0;i<mid;i++){
            while(j<n && arr[i]>=5*arr[j]){
                j++;
            }
            count += (j-mid);
        }
        return count;
    }
}