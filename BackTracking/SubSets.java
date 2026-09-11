class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        generate(0,ans,nums,new ArrayList<>());
        return ans;
    }
    public void generate(int i,List<List<Integer>> ans,int [] nums,List<Integer> temp){
        if(i==nums.length){
            ans.add(new ArrayList<>(temp));
            return;
        }
        temp.add(nums[i]);
        generate(i+1,ans,nums,temp);
        temp.remove(temp.size()-1);
        generate(i+1,ans,nums,temp);
    }
}