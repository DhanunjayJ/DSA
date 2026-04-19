// User function Template for Java

class Solution {
    static void convertMinToMaxHeap(int N, int arr[]) {
        // code here
        // the non leaf nodes start from the N/2 because half of the tree usually has the leaf nodes.
        for(int i=(N/2)-1;i>=0;i--){
            maxHepify(arr,i,N);
        }
    }
    
    static void maxHepify(int [] arr,int i,int N){
        
        int largest = i;
        int left = 2*i+1;
        int right = 2*i+2;
        
        if(left<N && arr[left]>arr[largest]){
            largest = left;
        }
        
        if(right<N && arr[right]>arr[largest]){
            largest = right;
        }
        
        //if parent is the largest we don't need to do anything
        //if not. then we need to sink in!!!
        if(i!=largest){
            int temp = arr[largest];
            arr[largest] = arr[i];
            arr[i] = temp;
            //now the values swapeed the parent has the largest 
            //value 
            //now we need to call the maxhepify on the child we
            //just swaped
            maxHepify(arr,largest,N);
        }
        
    }
}
