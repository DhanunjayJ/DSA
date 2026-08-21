class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        /*
        Here we use the array itself as a hashmap if that vlaue occuring twice.

        so here, we have 1 to n elements where n is the length of the array. 

        so the n vaue itself wil e there in the array we need to map each value 
        to the arr[i]-1 index.

        so instread of mapping we could just mark them visited by making the vlaues
        -ve so that we could check that vlaues have occured previoulsy if it dit occur then we just add that value to the array. 

        another appoahc is do the ccyclick sort.

        we try to map ecah value at each index till the value at the index is equal to the arr[i]-1 == i untill then we map it. once tha tis the cause then mwe move foread. if the vlaue is alreayd mappeed then we just do i++;
        */
        List<Integer> ans = new ArrayList<>();
        for(int num : nums){
            num = Math.abs(num);
            int idx = num-1;
            //if the value at the index is alreayd negative then the value is arleayd visited. 
            if(nums[idx]<0){
                ans.add(num);
            }else{
                //or we make it negative.
                nums[idx] = -nums[idx];
            }
        }
        return ans;
    }
}

//cyclic sort

class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        int i = 0;
        
        // Phase 1: Cyclic Sort
        while (i < nums.length) {
            // The index where nums[i] actually belongs
            int correctIndex = nums[i] - 1;
            
            // If the number is not at its correct index, AND the target spot 
            // doesn't already have the correct number, swap them!
            if (nums[i] != nums[correctIndex]) {
                // Swap nums[i] and nums[correctIndex]
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            } else {
                // The number is either at the correct index, OR we found a duplicate
                i++;
            }
        }
        
        // Phase 2: Find the numbers trapped in the wrong indices
        List<Integer> duplicates = new ArrayList<>();
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != index + 1) {
                duplicates.add(nums[index]);
            }
        }
        
        return duplicates;
    }
}
