
class Solution {
    // Please change the array in-place
    public void insertionSort(int arr[]) {
        // code here
        int n = arr.length;
      //here we increase the sorted range from the left to right.
      //and we try to incldue the new element in the sorted part.
      for(int i=1;i<n;i++){
          //given range 0 to i
          // start from i-1 and compare untill 
          // untill i value is in the current position.
          for(int j=i-1;j>=0;j--){
              
              if(arr[j]>arr[j+1]){
                  int temp = arr[j];
                  arr[j] = arr[j+1];
                  arr[j+1] = temp; 
              }else{
                  break;
              }
          }
      }
    }
}


//other way

class Solution {
    // Please change the array in-place
    public void insertionSort(int arr[]) {
        // code here
        sort(arr,1);
    }
    public void sort(int [] arr,int n){
        if(n==arr.length)return;
        for(int j=n;j>0;j--){
            if(arr[j]<arr[j-1]){
                int temp = arr[j-1];
                arr[j-1] = arr[j];
                arr[j] = temp;
            }else{
                break;
            }
        }
        sort(arr,n+1);
    }
}