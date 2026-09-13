class Solution {
    public int countPairs(int[] arr, int k) {
        // Code here
        HashMap<Integer,Integer> hm = new HashMap<>();
        for(int i=0;i<arr.length;i++){
            hm.put(arr[i],hm.getOrDefault(arr[i],0)+1);
        }
        int count = 0;
        for(int key : hm.keySet()){
            if(k==0){
                if(hm.get(key)>1){
                    count++;
                }
            }else{
                if(hm.containsKey(key+k)){
                    count++;
                }
            }
        }
        return count;
    }
}

//Two pointers approach
class Solution {
    public int countPairs(int[] arr, int k) {
        // Code here
        Arrays.sort(arr);
        int n = arr.length;
        //placing two pointers adjacent.
        int i = 0;
        int j = 1;
        int count = 0;
        while(j<n){
            //if both are same place do j++;
             if(i==j){
                j++;
                continue;
            }
            
            int diff = arr[j]-arr[i];
            
            if(diff==k){
               
                count++;
                 //if duplicate elements are there then 
                 // remove them
                int num1 = arr[i];
                int num2 = arr[j];
                
                //
                
                while(i<n && arr[i]==num1){
                    i++;
                }
                
                while(j<n && arr[j]==num2){
                    j++;
                }
                
            }else if(diff<k){
                
                j++;
                
            }else{
                
                i++;
                
            }
        }
        return count;
    }
}