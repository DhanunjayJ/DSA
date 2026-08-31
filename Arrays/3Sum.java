public class 3Sum {
    class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        //sorting to find the distinct values easily by comparing adjacent values.
        Arrays.sort(nums);
        int n = nums.length;
        for(int i=0;i<n-2;i++){
            //removing the duplicate values whent he adjacent values are same
            if(i>0 && nums[i]==nums[i-1]) continue;
            //optimization: since the array is sorted. when the i value is 
            //postive j and k will also be postive there is not point in 
            // continuing because none of them will sum = 0;
            if(nums[i]>0) break;
            //since the array is sorted we could use two pointers
            int j = i+1;
            int k = n-1;
            while(j<k){
                int sum = nums[i]+nums[j]+nums[k];
                if(sum==0){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(nums[i]);temp.add(nums[j]);temp.add(nums[k]);
                    ans.add(temp);
                    j++;
                    k--;
                    //if duplicates present then remove them by comparing. 
                    while(j<k && nums[j]==nums[j-1]) j++;
                    //one of these is enough
                    while(k>j && nums[k]==nums[k+1]) k--;
                }else if(sum<0){
                    j++;
                }else{
                    k--;
                }
            }
        }
        return ans;
    }
}
}
