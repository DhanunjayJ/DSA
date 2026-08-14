class Solution {
    int count;
    public int inversionCount(int arr[]) {
        // code here
        count = 0;
        mergeSort(arr,0,arr.length-1);
        return count;
    }
    public void mergeSort(int [] arr,int start,int end){
        if(start>=end) return;
        int mid = (start+end)/2;
        mergeSort(arr,start,mid);
        mergeSort(arr,mid+1,end);
        merge(arr,start,mid,end);
    }
    public void merge(int [] arr,int start,int mid,int end){
        int len = end-start+1;
        int [] newArray = new int[len];
        int p1 = start;
        int p2 = mid+1;
        int p3 = 0;
        while(p1<=mid && p2<=end){
            if(arr[p1]<=arr[p2]){
                newArray[p3++] = arr[p1++];
            }else{
                
                //you are asked to count the i's that are
                //> j so when the i is greater than j value
                //it means that since all the i'sar eint eh sorted
                //orders all the oters i will be tgreater to so we coun
                //then to the inversion count;
                count += (mid-p1+1);
                newArray[p3++] = arr[p2++];
            }
        }
        while(p1<=mid) newArray[p3++] = arr[p1++];
        while(p2<=end) newArray[p3++] = arr[p2++];
        
        for(int i=0;i<len;i++){
            arr[i+start] = newArray[i];
        }
    }
}