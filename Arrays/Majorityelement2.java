class Solution {
    public List<Integer> majorityElement(int[] nums) {
        /*
        we need to find all the elements that are appearing more than n/3 times
        how many elements can be there in an array with n/3 times more than?
        not more than 2 so we stick with 2 vlaues.
        and do the boyer mooree alaogrihm.

        this algorighm states that when there is 

        If you pair up every occurrence of the majority element with a different element, the majority element will still have at least one uncancelled instance left over because it occupies more than half of the total slots.


        here it does work we need to take two varibles here for the majority check.

        */

        int count1 = 0;
        int val1 = -1;
        int count2 = 0;
        int val2 = -1;

        for(int num : nums){
            if(num==val1){
                count1++;
            }else if(num==val2){
                count2++;
            }else if(count1==0){
                val1 = num;
                count1++;
            }else if(count2==0){
                val2 = num;
                count2++;
            }else{
                //we have to cancel out the majority elements with the differnt element. 
                count1--;
                count2--;
            }
        }

        //now we need to check is the val1 and val2 eleemnts that are remaingaing are acuatlly majority or not.

        //for this we count it. 
        int vCount1 = 0;
        int vCount2 = 0;

        for(int num : nums){
            if(num==val1) vCount1++;
            else if(num==val2) vCount2++;
        }
        int n = nums.length;
        List<Integer> ans = new ArrayList<>();
        if(vCount1>n/3) ans.add(val1);
        if(vCount2>n/3) ans.add(val2);
        return ans;
       
    }
}