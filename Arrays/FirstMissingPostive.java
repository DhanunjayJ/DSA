public class FirstMissingPostive {
    class Solution {
    public int firstMissingPositive(int[] nums) {
        /*
        we need to return the first missing postive number here.

        Observation :
        1. given an array of lenth n, the first missing postive numebr will be in the range
        of 0 to n-1. if the array contains all the values from 0 to n-1 then first missing postive (fmp)
        is n;

        witht that in mind if we try to solve this we could use a hashmap and put all the elemetns
        to it and check for all the n values. the fist missing is the missing one. that is o(n) tc and o(n) s.c

        if we want to optimize this using o(1) space.

        then we need to check if we can place these things in side the same array. 

        since the array index starts with 0, what we can say is, that 1 will be palce at 0
        which is arr[i]-1 is the index of the eleemnt that too if it is there in the array. 
        
        so we try to mutate the inplace values of the array. and try to map the value to the corresponding index.

        once that is done. we loop throught the array the find missing i.e, i!=arr[i]-1, then i+1 is the first missing postive.

        if we haven't found anyting then n is the fmp.

        edge cases :
        what if the array contains duplciate vlaues. 
        if it then it will give us a case of values arlready mapped to the index. so we need to handle that. 

        psuedocode :
        1. loop through the values in the array and check we can acually map the value to a partivualr index
        in the array.
        a. the values has to be >0
        b. the value has to be <n
        c. if the value is already mappend we don't need to care about it. 
        2. now, we found a value that need to be mapped.
        a. then we need to get the index by doing arr[i]-1.
        b. then we have the edge case of the duplicate values. if it the case we just go to the next ele
        c. we swap the values.
        d. once swapping is done, and check if the current value in the current index is corrreclty mappend.
        if it then we move fowrad elsse we swa it. 
        3. we iterate the arrya ad fing first i!=arr[i]-1 case and return i+1
        else we return n+1;
        */

        int n = nums.length;
        int i = 0;

        while(i<n){
            if(nums[i]>=n || nums[i]<1 || nums[i]-1==i){
                i++;
            }else{
                int index = nums[i]-1;
                if(nums[index]==nums[i]){
                    i++;
                    continue;
                }
                int temp = nums[i];
                nums[i] = nums[index];
                nums[index] = temp;
            }
        }

        for(int j=0;j<n;j++){
            if(nums[j]-1!=j){
                return j+1;
            }
        }
        return n+1;
    }
}
}
