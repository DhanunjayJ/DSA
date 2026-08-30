class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        //sorting to group the duplicates togehter. 
        Arrays.sort(nums);
        //using boolean array to mention which one to used
        //so that we don't use it again.
        helper(ans,nums,new ArrayList<>(),new boolean[nums.length]);
        return ans;
    }
    public void helper(List<List<Integer>> ans,int [] nums,List<Integer> temp,boolean [] visited){

        if(temp.size()==nums.length){
            ans.add(new ArrayList<>(temp));
            return;
        }

        //we have to always start from the zero because the order matters
        //and we cant use the same values again. 
        for(int i=0;i<nums.length;i++){
            if(visited[i]) continue;
            //skipping the duplicates
            //if the elememnt is same as the previous and the 
            //previous one was NOT used in this current branch, skip it.
            if(i>0 && nums[i-1]==nums[i] && !visited[i-1]) continue;

            visited[i] = true;
            temp.add(nums[i]);

            helper(ans,nums,temp,visited);

            visited[i] = false;
            temp.remove(temp.size()-1);
        }
    }
}