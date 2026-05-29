// 3^n Appraoch

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Set<List<Integer>> ans = new HashSet<>();
        helper(candidates,target,0,ans,new ArrayList<>(),0);
        return new ArrayList<>(ans);
    }
    public void helper(int [] candidates, int target,int sum,Set<List<Integer>> ans, List<Integer> temp, int i){
        if(sum==target){
         ans.add(new ArrayList<>(temp));
         return;
        }
        if(sum>target || i==candidates.length){
            return;
        }
        //pick the element and increment;
        temp.add(candidates[i]);
        helper(candidates,target,sum+candidates[i],ans,temp,i+1);
        //backtrack and don't increment;
        temp.remove(temp.size()-1);
        temp.add(candidates[i]);
        helper(candidates,target,sum+candidates[i],ans,temp,i);
        temp.remove(temp.size()-1);
        //don't pick any element
        helper(candidates,target,sum,ans,temp,i+1);
    }
}

//2^n Approach
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        helper(candidates,target,0,ans,new ArrayList<>(),0);
        return ans;
    }
    public void helper(int [] candidates, int target,int sum,List<List<Integer>> ans, List<Integer> temp, int i){
        if(sum==target){
         ans.add(new ArrayList<>(temp));
         return;
        }
        if(sum>target || i==candidates.length){
            return;
        }
        //pick the element and increment;
        // temp.add(candidates[i]);
        // helper(candidates,target,sum+candidates[i],ans,temp,i+1);
        // //backtrack and don't increment;
        // temp.remove(temp.size()-1);
        // the above call will be included in the next recursive call becauses i+1
        //will be done in the next call so that we don't need above cuz it will be covered next.
        //this allows us to get unique values only!!
        temp.add(candidates[i]);
        helper(candidates,target,sum+candidates[i],ans,temp,i);
        temp.remove(temp.size()-1);
        //don't pick any element
        helper(candidates,target,sum,ans,temp,i+1);
    }
}

//sorting and pruning approach

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ans = new ArrayList<>();
        helper(candidates,target,0,ans,new ArrayList<>(),0);
        return ans;
    }
    public void helper(int [] candidates, int target,int sum,List<List<Integer>> ans, List<Integer> temp, int i){
        if(sum==target){
         ans.add(new ArrayList<>(temp));
         return;
        }
        for(int j = i;j<candidates.length;j++){
        if(sum+candidates[j]>target) break;
        temp.add(candidates[j]);
        // we don't do the j+1 because the j will be automatically increment
        // in the next iteration. 
        helper(candidates,target,sum+candidates[j],ans,temp,j);
        temp.remove(temp.size()-1);
        }
    }
}
