class Solution {
    public int findPages(int[] arr, int k) {
        // code here
        int n = arr.length;
        if(n<k) return -1;
        long low = Integer.MIN_VALUE;
        long high = 0;
        for(int book : arr){
            low = Math.max(low,book);
            high += book;
        }
        while(low<=high){
            long mid = low+(high-low)/2;
            if(canAllocate(arr,k,mid)){
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return (int) low;
    }
    public boolean canAllocate(int [] arr,int k,long maxPages){
        int students = 1;
        int pages = 0;
        for(int i=0;i<arr.length;i++){
            if(pages+arr[i]>maxPages){
                pages = 0;
                students++;
            }
            pages+=arr[i];
        }
        return students<=k;
    }
}