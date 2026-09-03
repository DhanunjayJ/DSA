public class LC3876ConstructUniformParityII {
    class Solution {
    public boolean uniformArray(int[] nums1) {
        /*
        Recalling the previous problem conditions as this problem same as that
        but the values are should be in a way that nums1[i]-nums1[j]>=1;

        if that is the case, first

        odd - even = odd;
        even - odd = odd;
        even - even = even;
        odd - odd =  odd;

        now we need to conver the array all odd or all even such that, nums[i]-nums[j]>=1;
        so, the formula sugests that the j value should be the minimum value.

        so we get the abosolute minimum value in the array.

        we check the whole array and check if the values are all odd or all even. 

        if the smallest value is odd we don't need any thing else we just return true.
        if the smallest vlaue is even, then all the eleemnts after it should be even becuase
        to make the next element even it needs to take the value that is lessthan the current value that result value >= 1, since the smallest element is even, how could the next element could be odd? so that is the final approach. 


        1. check all the values get the min value.
        2. while getting min check if all are even or not. 
        3. once you get the min, chekc if that is odd or even.
        4. if odd return true.
        5. if even , check if the boolean flag all even is true. if it is return true else false.
        */

        int min = Integer.MAX_VALUE;

        boolean isAllEven = true;

        for(int num : nums1){
            min = Math.min(num,min);
            if((num&1)!=0)isAllEven = false;
        }

        return (min%2==0) ? isAllEven : true;
    }
}
}
