class Solution {
    public int sortableIntegers(int[] nums) {
        List<Integer> factors = new ArrayList<>();
        int n = nums.length;
        for(int i=1;i*i<=n;i++){
            if(n%i==0){
                if(i==n/i){
                    factors.add(i);
                }else{
                    factors.add(i);
                    factors.add(n/i);
                }
            }
        }
        // for(int i:factors) System.out.println(i);
        int [] prefixMax = new int[n];
        int [] suffixMin = new int[n];
        prefixMax[0] = nums[0];
        suffixMin[n-1] = nums[n-1];
        for(int i=1;i<n;i++){
            prefixMax[i] = Math.max(prefixMax[i-1],nums[i]);
            suffixMin[n-1-i] = Math.min(suffixMin[n-i],nums[n-1-i]);
        }
        int sum = 0;
        for(int k : factors){
            if(k==1){
                // System.out.println("checking fact "+k);
                if(isRotated(nums,0,n-1,true)) sum+=1;
            }else{
                boolean isPossible = isSubArraySortedIfRotated(nums,k,prefixMax,suffixMin);
                boolean isRotatedK = true;
                for(int i=0;i<n;i+=k){

                    if(!isRotated(nums,i,i+k-1,false)){
                        isRotatedK = false;
                    }
                }
                if(isPossible && isRotatedK) {
                    // System.out.println("Adding Fact "+k);
                    sum += k;
                }
            }
        }
        return sum;
    }
    public boolean isRotated(int [] nums,int start,int end,boolean checkSorted){
        int breaks = 0;
        for(int i=start;i<end;i++){
            if(nums[i]>nums[i+1]){
                breaks++;
            }
            if(breaks>1) return false;
        }
        if(checkSorted){
            return breaks==0;
        }
        if(breaks==0 || (breaks==1 && nums[start]>=nums[end])) return true; 
        return false;
    }

    public boolean isSubArraySortedIfRotated(int [] nums,int k,int [] pMax,int [] sMin){
        int n = nums.length;
        for(int i=k-1;i<n;i+=k){
            if(i!=n-1){
                if(pMax[i]>sMin[i+1]){
                    // System.out.println("k "+k+" pmax i "+pMax[i]+" sMin i+1 "+sMin[i+1]);
                    return false;
                }
            }
        }
        return true;
    }
}