// https://www.geeksforgeeks.org/problems/minimum-sum-partition3317/1


//Top down approach 
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



//Bottom Up

class Solution {
    public int minDifference(int arr[]) {
        // code here
        int n = arr.length;
        int totalSum = 0;
        for(int num:arr) totalSum += num;
        int target = totalSum/2;
        
        boolean [][] dp = new boolean [n+1][target+1];
        
        dp[0][0] = true; //with zero elements it is always possible so true.
        //we start with 1 and since we need need handle if the case of i-1 gets
        //out of bounds
        for(int i=1;i<=n;i++){
            int currentEle = arr[i-1]; // since the array is zeor vbase
            //now with the current elements till i-1
            //we have to check it is possible to get sum s 
            for(int s=0;s<=target;s++){
                //if we don't pick the element
                //check we already achived this in the previous 
                //elements
                dp[i][s] = dp[i-1][s];
                //if not first
                //check if the current element is < the sum we want to achive
                if(currentEle<=s){
                    //if it is then get the answer from the not pick value
                    //and check if we can reach the sum-current elemtn 
                    //if we can reach it then we can reach this too.
                    dp[i][s] = dp[i][s] || dp[i-1][s-currentEle];
                }
            }
        }
        
        for(int i=target;i>=0;i--){
            if(dp[n][i]){
                int otherSum = totalSum-i;
                return Math.abs(otherSum-i);
            }
        }
        
        return 0;
    }
}


//Bottom up optimized
class Solution {
    public int minDifference(int arr[]) {
        // code here
        int n = arr.length;
        int totalSum = 0;
        for(int num:arr) totalSum += num;
        int target = totalSum/2;
        
        boolean [] dp = new boolean [target+1];
        
        dp[0] = true; 
        
        //to avoid miscalcuain the values for the vals
        //that are already calcuated we do back ward iteration
        //over forwward.
        for(int currNum : arr){
            for(int s=target;s>=currNum;s--){
                dp[s] = dp[s] || dp[s-currNum];
            }
        }
        
        for(int i=target;i>=0;i--){
            if(dp[i]){
                int otherSum = totalSum-i;
                return Math.abs(otherSum-i);
            }
        }
        
        return 0;
    }
}
