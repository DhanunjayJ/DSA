class Solution {
    
    int [][] dp;
    int n;
    int totalSum;
    
    public int minDifference(int arr[]) {
        // code here
        this.n = arr.length/2;
        totalSum = 0;
        for(int num : arr) totalSum += num;
        //the sum we only ever need to check for
        int target = totalSum/2;
        
        dp = new int[arr.length][target+1];
        
        for(int i=0;i<arr.length;i++){
            Arrays.fill(dp[i],-1);
        }
        
        return helper(0,0,arr,target);
    }
    
    public int helper(int i,int sum,int [] arr,int target){
       //if we have reached the end of the array. then we have to check 
       //how much is the sum till now.
        if(i==arr.length){
            int otherSum = totalSum - sum;
            return Math.abs(otherSum-sum);
        }
        
        if(dp[i][sum]!=-1) return dp[i][sum];
        
        int pick = (int)1e9;
        
        //if the addition of the currnet value is <= target
        //we add it if not we don't because. we don't need
        //to since the values repeat after target. sum.
        if(sum+arr[i]<=target)
        pick = helper(i+1,sum+arr[i],arr,target);
        
        int skip = helper(i+1,sum,arr,target);
        
        dp[i][sum] = Math.min(pick,skip);
        
        return dp[i][sum];
    }
}


