class Solution {
    public int minOperations(int[] nums) {
        int zeroIdx = -1;
        int n = nums.length;
        for(int i=0;i<n;i++){
            if(nums[i]==0){
                zeroIdx = i;
            }
        }
        int minOps = Integer.MAX_VALUE;

        if(sortedClockWise(nums,zeroIdx)){
            //do the rotate to the left. zero idx times
            minOps = Math.min(minOps,zeroIdx);
            //do reverse and roatate and reverse 
            minOps = Math.min(n-zeroIdx+2,minOps);
        }

        if(sortedAntiClockWise(nums,zeroIdx)){
            //since sorted counter clock then we need 
            //rotate left by n-zeroIdx reverse and rotate
            minOps = Math.min(minOps,n-zeroIdx);
            //if not, then we have other choice.
            //rotate and reverse. 
            minOps = Math.min(minOps,zeroIdx+2);
        }

        return minOps == Integer.MAX_VALUE ? -1 : minOps;
    }
    public boolean sortedClockWise(int []nums,int zi){
        int n = nums.length;
        for(int i=0;i<n;i++){
            int currentVal = nums[(zi+i)%n];
            if(currentVal!=i){
                return false;
            }
        }
        return true;
    }
    
    public boolean sortedAntiClockWise(int [] nums,int zi){
        int n = nums.length;
        for(int i=0;i<n;i++){
            //since the values are in the range of 0 to n-1.
            //we can check ift the values are same with the indexes. 
            int currentVal = nums[(zi-i+n)%n];
            if(currentVal!=i){
                return false;
            }
        }
        return true;
    }
}