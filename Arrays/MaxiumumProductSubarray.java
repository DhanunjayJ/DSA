class Solution {
    public int maxProduct(int[] nums) {
        /*

        The core part of this problem is that the maximum problem is that 
        the maximum product subarray will never lie int eh middle.
        it will always lie from the start or from the end. 

        if the middle was +ve

        left right
        -    +     -> this case we go right
        +   +   -> both sides can be expanded
        -   -   -> b going both sides we can get +value
        +   -    -> we could go the left side. 

        so the conlusiion is that the valu of the maximum subarray sum will ways lies the start of the array or at the end of the array will not lie in the
        center. 

        the edgecase 
        is the when the value of the prev product becomes zero, then we 
        reset it back to the current element. not the previous one. 
        */


        int n = nums.length;

        int maxLeft = nums[0];
        int prefProduct = nums[0];

        for(int i = 1;i<n;i++){
            if(prefProduct==0){
                prefProduct = nums[i];
            }else{
                prefProduct *= nums[i];
            }
            maxLeft = Math.max(prefProduct,maxLeft);
        }

        int maxRight = nums[n-1];
        int suffixProduct = nums[n-1];
        for(int i=n-2;i>=0;i--){
            if(suffixProduct == 0){
                suffixProduct = nums[i];
            }else{
                suffixProduct *= nums[i];
            }
            maxRight = Math.max(suffixProduct,maxRight);
        }

        return Math.max(maxLeft,maxRight);
    }
}