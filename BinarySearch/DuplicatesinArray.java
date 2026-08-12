class Solution {
    public ArrayList<Integer> findDuplicates(int[] arr) {
        // code here
        int n = arr.length;
        int [] count = new int[n+1];
        for(int i=0;i<n;i++){
            count[arr[i]]++;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=1;i<=n;i++){
            if(count[i]==2){
                ans.add(i);
            }
        }
        return ans;
    }
}


class Solution {
    public ArrayList<Integer> findDuplicates(int[] arr) {
        // code here
        /*
        Since the the values in the array are atmost 2 not more than 2 we can use the 
        the index neagation pricicple here.
        
        sinc ethe values are from 1 to n. we can map each value to the num-1.
        
        so we could just mark what ever the value that is present in that index to neagetie
        
        now when the same number comes again in the array. we chould check the 
        index. and if it already negative then we make this as duplicate.
        
        this is how we use the current array as a hasmap without using the extra
        array.
        */
        ArrayList<Integer> ans = new ArrayList<>();
        for(int num : arr){
            int index = Math.abs(num)-1;
            if(arr[index]<0){
                //if the value is already negative at the end.
                //then do this.
                //then we can say this is the duplcate.
                ans.add(Math.abs(num));
            }else{
                //if not then mark it as -ve.
                
                arr[index] = -arr[index];
            }
        }
        return ans;
    }
}

//cyclic sort

class Solution {
    public ArrayList<Integer> findDuplicates(int[] arr) {
        // code here
        /*
        Cyclic sort. sinc ethe values in the array are between the 1 to n;
        we try to place each element at each corresponsing eindex that is arr[i]-1
        if that is aready placed then we move on to next.
        now after this, sorting what ever the element that is not in the 
        corresponding index we add to the list.
        */
        
        int n = arr.length;
        int i = 0;
        while(i<n){
            int index = arr[i]-1;
            //check if the value is already mapped. 
            if(arr[index]!=arr[i]){
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }else{
                i++;
            }
        }
        
        ArrayList<Integer> ans = new ArrayList<>();
        for(int j=0;j<n;j++){
            if(arr[j]!=j+1){
                ans.add(arr[j]);
            }
        }
        return ans;
    }
}