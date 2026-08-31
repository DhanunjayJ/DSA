public class 4Sum {
    class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        //sort the array to keep the duplicates in one place
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        if(n<4) return ans;
        for(int i=0;i<n-3;i++){
            //if the index is not zero and if the previous element is
            //equal the current element to avoid duplicates
            //we do i++ because the i-1 value is already computed.
            if(i>0 && nums[i]==nums[i-1])continue;

            //Early Pruning. taking teh sorted array as advantage.
            // If the smallest possible sum with nums[i] is too large, break completely
            if((long) nums[i]+nums[i+1]+nums[i+2]+nums[i+3]>target) break;
            //If the largest possible sum with nums[i] is too small, skip this i
            if((long) nums[i]+nums[n-1]+nums[n-2]+nums[n-3]<target) continue;

            for(int j=i+1;j<n-2;j++){ 
                // here we are checking if he j-1 is not equal to the 
                // i value itself, just that value that is not i, now we
                // now j is not i then we are in the j range. 
                // now we check if this is duplicate if it is then skip it. 
                if(j-1>i && nums[j]==nums[j-1]) continue;

                //same pruning techniques as for i.
                if((long) nums[i]+nums[j]+nums[j+1]+nums[j+2]>target) break;
                if((long) nums[i]+nums[j]+nums[n-1]+nums[n-2]<target) continue;

                int k = j+1;
                int l = n-1;
                while(k<l){
                    long sum = nums[i]+nums[j]+nums[k]+nums[l];
                    if(sum==target){
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[k]); temp.add(nums[l]);
                        ans.add(temp);
                        k++;
                        l--;
                        //remove all the duplicates from k.
                        while(k<l && nums[k]==nums[k-1]) k++;
                        while(k<l && nums[l]==nums[l+1]) l--;
                    }else if(sum<target){
                        k++;
                    }else{
                        l--;
                    }
                }
            }
        }
        return ans;
    }
}
}
