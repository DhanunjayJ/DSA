// User function Template for Java

class Solution {
    static void convertMinToMaxHeap(int N, int arr[]) {
        // we only start from the level that is not a leaf node.
        // then we move forward up. the number of  
        for(int i = (N/2)-1;i>=0;i--){
            maxHepify(arr,i,N);
        }
    }
    
    static void maxHepify(int [] arr,int i,int N){
        int largest = i;
        int left = 2*i+1;
        int right = 2*i+2;
        
        if(left<N && arr[left] > arr[largest]){
            largest = left;
        }
        
        if(right<N && arr[right] > arr[largest]){
            largest = right;
        }
        
        if(largest!=i){
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            maxHepify(arr,largest,N);
        }
    }
}
