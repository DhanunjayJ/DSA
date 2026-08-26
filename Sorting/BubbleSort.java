class Solution {
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        
        for(int i = 0; i < n - 1; i++) {
            swapped = false; // Reset the flag for each new pass
            
            for(int j = 0; j < n - i - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    // Swap elements
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    
                    swapped = true; // A swap occurred
                }
            }
            
            // If no elements were swapped during this pass, the array is already sorted
            if(!swapped) {
                break;
            }
        }
    }
}