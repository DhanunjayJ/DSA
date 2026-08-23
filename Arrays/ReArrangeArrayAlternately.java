class Solution {
    public void rearrange(int arr[]) {
        // code here
        int n = arr.length;
        
        Arrays.sort(arr);
        
        int minIdx = 0;
        int maxIdx = n-1;
        
        //choose the number gerater than the max element in the array
        int maxEle = arr[n-1]+1;
        
        for(int i=0;i<n;i++){
            if(i%2==0){
                //  original Val    +   the value we need to keep * maxEle
                arr[i] += (arr[maxIdx]%maxEle)*maxEle;
                maxIdx--;
            }else {
                arr[i] += (arr[minIdx]%maxEle)*maxEle;
                minIdx++;
            }
        }
        
        for(int i=0;i<n;i++){
            arr[i] = arr[i]/maxEle;
        }
    }
}
