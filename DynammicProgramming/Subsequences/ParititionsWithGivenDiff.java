import java.util.Arrays;

public class ParititionsWithGivenDiff {

    class Solution {
    public int countPartitions(int[] arr, int diff) {
        // code here
        /*
        diff of the two subsets sum should be equal to 
        Math.abs(sum-sum2) = diff.
        totalSum = sum+sum2;
        
        we have two equations. 
        
        sum1 + sum2 = totalSum
        sum1-sum2 = diff;
        
        if we substrat both
        sum2 = totalSum-diff/2;
        
        so we have to check how many ways we can select subsets 
        so that sum of the subset is sum2. 
        */
        
        int n = arr.length;
        int totalSum = 0;
        for(int num : arr) totalSum += num;
        int target = (totalSum-diff)/2;
        
        //the edge cases
        if(totalSum<diff || (totalSum-diff)%2==1) return 0;
        
        int [][] dp = new int[n][target+1];
        for(int i=0;i<n;i++) Arrays.fill(dp[i],-1);
        
        return helper(arr,target,0,dp);
    }
    
    public int helper(int [] arr,int target,int i,int [][] dp){
        
        if(i==arr.length){
            return target == 0 ? 1 : 0;
        }
        
        if(dp[i][target]!=-1) return dp[i][target];
        
        int skip = helper(arr,target,i+1,dp);
        
        int pick = 0;
        
        if(arr[i]<=target)
        pick = helper(arr,target-arr[i],i+1,dp);
        
        dp[i][target] = pick+skip;
        
        return dp[i][target];
    }
}


//bottom up

class Solution {
    public int countPartitions(int[] arr, int diff) {
        // code here
        /*
        diff of the two subsets sum should be equal to 
        Math.abs(sum-sum2) = diff.
        totalSum = sum+sum2;
        
        we have two equations. 
        
        sum1 + sum2 = totalSum
        sum1-sum2 = diff;
        
        if we substrat both
        sum2 = totalSum-diff/2;
        
        so we have to check how many ways we can select subsets 
        so that sum of the subset is sum2. 
        */
        
        int n = arr.length;
        int totalSum = 0;
        for(int num : arr) totalSum += num;
        int target = (totalSum-diff)/2;
        
        //the edge cases
        if(totalSum<diff || (totalSum-diff)%2==1) return 0;
        
        int [][] dp = new int[n+1][target+1];
        
        dp[n][0] = 1;
        
        for(int i=n-1;i>=0;i--){
            for(int t=0;t<=target;t++){
                //exclude
                dp[i][t] = dp[i+1][t];
                //include
                if(arr[i]<=t){
                    dp[i][t] += dp[i+1][t-arr[i]];
                }
            }
        }
        
        return dp[0][target];
    }
}


// 1d optimization

class Solution {
    public int countPartitions(int[] arr, int diff) {
        // code here
        /*
        diff of the two subsets sum should be equal to 
        Math.abs(sum-sum2) = diff.
        totalSum = sum+sum2;
        
        we have two equations. 
        
        sum1 + sum2 = totalSum
        sum1-sum2 = diff;
        
        if we substrat both
        sum2 = totalSum-diff/2;
        
        so we have to check how many ways we can select subsets 
        so that sum of the subset is sum2. 
        */
        
        int n = arr.length;
        int totalSum = 0;
        for(int num : arr) totalSum += num;
        int target = (totalSum-diff)/2;
        
        //the edge cases
        if(totalSum<diff || (totalSum-diff)%2==1) return 0;
        
        int [] dp = new int[target+1];
        
        dp[0] = 1;
        
        for(int i=n-1;i>=0;i--){
            for(int t=target;t>=arr[i];t--){
                dp[t] += dp[t-arr[i]];
            }
        }
        
        return dp[target];
    }
}

class Solution {
    public int countPartitions(int[] arr, int diff) {
        int n = arr.length;
        int totalSum = 0;
        for(int num : arr) totalSum += num;
        
        // Edge cases check
        if(totalSum < diff || (totalSum - diff) % 2 != 0) return 0;
        
        int target = (totalSum - diff) / 2;
        int MOD = 1_000_000_007;
        
        // 1D DP Array
        int[] dp = new int[target + 1];
        
        // Base case: There is 1 way to make a sum of 0 (using an empty subset)
        dp[0] = 1;
        
        for (int i = 0; i < n; i++) {
            // Loop backwards all the way to 0 to catch zero values correctly
            for (int t = target; t >= 0; t--) {
                if (t >= arr[i]) {
                    dp[t] = (dp[t] + dp[t - arr[i]]) % MOD;
                }
            }
        }
        
        return dp[target];
    }
}

}
