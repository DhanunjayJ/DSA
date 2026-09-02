public class MissingAndRepeating {
  
//modifying array approach. 
public class MissingAndRepeating {
    //missing and repeated values.
    // we try to use the array itself as a hashmap here.
    //if a number is occured. then we hve to make it;s correspsoind index negative.
    // and and if alreyad negative  when trying to chagne to negative that number is repeating.
    // after all the changes are done we check the one that is having the postive value and return 
    //index +1. 
    class Solution {
    ArrayList<Integer> findTwoElement(int arr[]) {
        // code here
        int repeated = 0;
        for(int num : arr){
            int idx = Math.abs(num)-1;
            if(arr[idx]<0){
                repeated = Math.abs(num);
            }else{
                arr[idx] = - arr[idx];
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            if(arr[i]>0){
                ans.add(repeated);
                ans.add(i+1);
            }
        }
        return ans;
    }
}

}

//bit manipulation without modifying the array
class Solution {
    ArrayList<Integer> findTwoElement(int arr[]) {
        // code here
        /*
        so we need to find the missing and repeating using 
        bits without modifying the array. 
        
        then we know the values int eh array should be from 
        1 to n.
        and the values in the array are x is missing and y is repeating.
        
        so now, if we do xor of from 1 to n with th array eelements
        
        the array has no x and 2 y's and 1 to n has 1 x and 1 y.
        
        when we do xor the 3 y's become 1y and 1x will remaing and final
        value is x^y.
        
        (x^x=0)
        
        now, if that is the case, now we have to find out. 
        
        what is x and y, we have x^y, to differencet and find out what
        is missing and what is repeating.
        
        for xor if two bits are differcnt 0 and 1 we get 1.
        
        so based on this condition we could seperate the all the values
        1ton and all array eleemtns.
        
        to zero group and one group.
        
        and do check if that bit is set with zero then we put in the zero
        by doing the xor for that group.
        
        and do he same for the one group once that is done. 
        
        at last we are left with two group and to know which is repeating 
        and which is not we check if one group value is preent in the values
        if is there then it is repeating
        else it is mising. 
        */
        
        ArrayList<Integer> nums = new ArrayList<>();
        
        int n = arr.length;
        
        //creating one array with all the vlaues. for easer processing. 
        for(int i=0;i<n;i++){
            nums.add(i+1);
            nums.add(arr[i]);
        }
        
        int xor = 0;
        
        for(int num : nums) xor^=num;
        
        int setbit = xor&-xor; // getting the right most set bit.
        
        int fGroup = 0;
        int sGroup = 0;
        
        for(int num : nums){
            if((num&setbit)!=0) fGroup ^=num;
            else sGroup ^=num;
        }
        
        boolean isFGroupP = false;
        
        for(int num : arr){
            if(fGroup==num){
                isFGroupP = true;
                break;
            }
        }
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        if(isFGroupP) {
            ans.add(fGroup);
            ans.add(sGroup);
        }else{
            ans.add(sGroup);
            ans.add(fGroup);
        }
        
        return ans;
        
    }
}
}
