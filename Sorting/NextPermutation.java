class Solution {
    public void nextPermutation(int[] nums) {
        //[ 1, 3, 5, 4, 8, 7, 6, 3, 2, 1 ]
        int n = nums.length;
        //find the index that is first decreasing becasue
        // that will help us in creating the next permutation. 
        //with given values.
        int rIdx = n-2;
        while(rIdx>=0){
            if(nums[rIdx]<nums[rIdx+1]){
                break;
            }
            rIdx--;
        }
        //check if the rIdx is 0 meaning thee is not next permuation.
        //then we return the sorted array
        if(rIdx==-1){
            // Arrays.sort(nums);
            reverse(nums,0,n-1);
            return;
        }
        //now from the current rIdx find the next greater value
        //just greater.
        int nIdx = rIdx+1;
        //since the values are in the sorted in decreasing order
        //the moment the values are becomes lesser then teh values 
        //that we have he before it the index we need to replace it with.
        while(nIdx<n){
            //when the values are <= we have to stop.
            //we have to consdier the == case as well!!
            if(nums[nIdx]<=nums[rIdx]){
                break;
            }
            nIdx++;
        }
        //if goes out of bounds or just met he lesser values
        //we could get the next greater by just doing the nIdx--;

        nIdx--;

        ///swap the nIdx and rIdx

        int temp = nums[nIdx];
        nums[nIdx] = nums[rIdx];
        nums[rIdx] = temp;
        //we could just reverse the array but sorting could be easier if reverse also works.
        // Arrays.sort(nums,rIdx+1,n);
        reverse(nums,rIdx+1,n-1);
    }

    public void reverse(int [] nums,int i,int j){
        while(i<j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }
}