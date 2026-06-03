import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinationSum2 {
    //Recusive and Hashed
    class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        Set<List<Integer>> ans = new HashSet<>();
        helper(candidates,0,target,ans,new ArrayList<>(),0);
        return new ArrayList<>(ans);
    }
    public void helper(int [] candidates, int sum,int target, Set<List<Integer>> ans, List<Integer> temp,int i){
       if(sum==target){
        ans.add(new ArrayList<>(temp));
        return;
       } 
       if(sum>target|| i==candidates.length){
        return;
       }
       //pick and move forward
       temp.add(candidates[i]);
       helper(candidates,sum+candidates[i],target,ans,temp,i+1);
       temp.remove(temp.size()-1);
       helper(candidates,sum,target,ans,temp,i+1);
    }

}

//sorted and Duplicates removed approahc

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ans = new ArrayList<>();
        helper(candidates,0,target,ans,new ArrayList<>(),0);
        return ans;
    }
    public void helper(int [] candidates, int sum,int target, List<List<Integer>> ans, List<Integer> temp,int i){
       if(sum==target){
        ans.add(new ArrayList<>(temp));
        return;
       } 
       if(sum>target|| i==candidates.length){
        return;
       }
       //pick and move forward
       for(int j=i;j<candidates.length;j++){
        if(sum+candidates[j]>target) break;
        //to skip the duplicate elements and allow the first duplicate if i==j coz it creates new unique combination
        if(j>i && candidates[j]==candidates[j-1])continue;

        temp.add(candidates[j]);
        helper(candidates,sum+candidates[j],target,ans,temp,j+1);
        temp.remove(temp.size()-1);
       }
    //    helper(candidates,sum,target,ans,temp,i+1);
    }

}

}
