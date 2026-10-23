class Solution {
    public int[] singleNumber(int[] nums) {
        int xor = 0;
        for(int num : nums) xor ^= num;
        int setbit = xor & -xor;
        int set1 = 0;
        int set2 = 0;
        for(int num : nums){
            if((num&setbit)!=0){
                set1 ^= num;
            }else{
                set2 ^= num;
            }
        }
        return new int[]{set1,set2};
    }
}