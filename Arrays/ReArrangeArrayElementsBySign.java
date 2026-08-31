public class ReArrangeArrayElementsBySign {
    class Solution {
    public int[] rearrangeArray(int[] nums) {
        int evenPos = 0;
        int oddPos = 1;
        int n = nums.length;
        int [] arr = new int[n];
        for(int i=0;i<n;i++){
            if(nums[i]<0){
                arr[oddPos] = nums[i];
                oddPos+=2;
            }else{
                arr[evenPos] = nums[i];
                evenPos+=2;
            }
        }
        return arr;
    }
}
}
