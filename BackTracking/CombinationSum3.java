//https://leetcode.com/problems/combination-sum-iii/


class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
       /* k numbers sum = n
        1 to 9 is only the numbers use. one once or none. 
        all the list of numbers are needed. 
        no duplicates allowed. -> since the numebers from the 1 to 9 are 
        are already sorted while doing the backtracking it won't give the duplicate values
        any order.

        Iterate from 1 to 9 and for each number call the next one recursively.
        and pass the current i to the next recursive function so taht we don't calculate the
        duplicate values.
        when the target is reached or when i == 9, check if the target is reached. 
        if it is the case then return . 
        a optimization we could do is before even entering the loop 
        we could check if the current number addition would case < 0. then return. */

        //First we need list<list<Integer> to store

        List<List<Integer>> ans = new ArrayList<>();

        // we will just use the target for the sum checking if target== 0 then we know 
        // we reached the sum. 

        generate(ans,k,n,1,new ArrayList<>());
        return ans;
    }
    public void generate(List<List<Integer>> ans,int k,int n,int start,List<Integer> ele){
        //base case
        if(k==0 && n==0){
            ans.add(new ArrayList<>(ele));
            return;
        }
        if(k==0) return;
        for(int i=start;i<=9;i++){
            if(n-i<0) return;
            ele.add(i);
            generate(ans,k-1,n-i,i+1,ele);
            ele.remove(ele.size()-1);
        }
    }
}