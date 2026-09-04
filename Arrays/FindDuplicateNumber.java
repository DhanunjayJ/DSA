class Solution {
    public int findDuplicate(int[] nums) {

        int slow = nums[0];
        int fast = nums[0];

        slow = nums[slow];
        
        fast = nums[nums[fast]];
       
        while(slow!=fast){
        slow = nums[slow];
        fast = nums[nums[fast]];
        }

        int slow2 = nums[0];
        while(slow!=slow2){
            slow = nums[slow];
            slow2 = nums[slow2];
        }

        return slow;
    }
}


//pigeon Princple approach uisng the binary search

class Solution {
    public int findDuplicate(int[] nums) {
        //using pegion hole principle
        //if more number are then <= mid then the value the duplicate is 
        //int he lower half.
        //else upper half.
        int low = 1;
        int high = nums.length-1; // since the values are between 1 to n-1.
        // while length is n+1;
        //we are not searching in the array here for the value.
        //we are searhing in the 1 to n and for count calucation only we use the 
        //array. 
        // IMP. we only use the array to count the values <=mid. 
        // If the vlaues in the array are <=mid meaning, that values are less 
        // more holes and less numbers. 
        // so we right. 
        //if > less holes and more number we go left. and do this untill we find the value. 
        
        while(low<high){

            int mid = low+(high-low)/2;
            int count = 0;

            for(int num : nums){
                if(num<=mid) count++;
            }

            if(count > mid){
                high = mid;
            }else{
                low = mid+1;
            }

        }

        return low;
    }
} 


//Bit Manipulation count set bits approach

class Solution {
    public int findDuplicate(int[] nums) {
        //Bit manipulation approach
        // We have the ideal range 1 to n and acutal array elements
        // now we need to find out the values that are duplicate.
        // so, what we could do is, 

        // the approahc is counting the setbits.

        // so we ahve two ranges 1 to n 
        // and the actual number.

        /* we count the set bits itn eh acuatl array and also 
        we count the set bits in the ideal array.

        if count of the acuatl set bits in the array are > meaning the duplciate is the one that i causingthis.
        if lesser thent he diplciat is the one that is cauing this we set the valeus with thezero.

        we do this for eeach bit int eh 32 bits. 
        */

        int duplicate = 0;
        int n = nums.length-1;
        //the vlaues will be between 1 to n.
        for(int bit=0;bit<32;bit++){

            int arrayCount = 0;
            int idealCount = 0;

            for(int j=0;j<=n;j++){

                if((nums[j]&(1<<bit))!=0){
                    arrayCount++;
                }
                //if the j is >1 get the count.
                if(j>0 && (j&(1<<bit))!=0){
                    idealCount++;
                }
            }

            //if array count is greater then set the bit.
            if(arrayCount>idealCount){
                duplicate |=(1<<bit);
            }
        }

        return duplicate;
    }
}