// nlogn approach

class Solution {
    public int maxSubArray(int[] nums) {
        //divided and concure approach nlogn approach.
        return divideAndConqure(nums,0,nums.length-1);
    }
    public int divideAndConqure(int []nums,int left,int right){

        if(left==right) return nums[left];

        int mid = left+(right-left)/2;

        int leftMax = divideAndConqure(nums,left,mid);

        int rightMax = divideAndConqure(nums,mid+1,right);

        int crossMax = crossMax(nums,left,mid,right);

        return Math.max(leftMax,Math.max(rightMax,crossMax));
    }

    public int crossMax(int [] nums,int left,int mid,int right){

        //find the sum of the continous leftSum.
        int leftSum = Integer.MIN_VALUE;
        //store the max in leftSum

        int currSum = 0;

        for(int i=mid;i>=left;i--){
            currSum += nums[i];
            if(currSum>leftSum){
                leftSum = currSum;
            }
        }

        //find the sume Of the continous RightSum
        int rightSum = Integer.MIN_VALUE;
        //store the maxIn the rightSum;

        currSum = 0;

        for(int i=mid+1;i<=right;i++){
            currSum += nums[i];
            if(currSum>rightSum){
                rightSum = currSum;
            }
        }

        return rightSum+leftSum;
    }
}

// o(n) kadanes algo

class Solution {
    public int maxSubArray(int[] nums) {
      int maxSum = Integer.MIN_VALUE;
      int currSum = 0;
      for(int i=0;i<nums.length;i++){
        currSum = Math.max(currSum+nums[i],nums[i]);
        maxSum = Math.max(currSum,maxSum);
      }  
      return maxSum;
    }
}