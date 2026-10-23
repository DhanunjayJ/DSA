class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        /*
        iterate trough the all the possible values of zero to n and add all
        the values what are hcaing the set bits it the answer.
        */
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        int totalNums = (1<<n);
        for(int i=0;i<totalNums;i++){
            List<Integer> temp = new ArrayList<>();
            for(int j=0;j<n;j++){
                if((1&(i>>j))==1){
                    temp.add(nums[j]);
                }
            }
            ans.add(temp);
        }
        return ans;
    }
}