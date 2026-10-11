class Solution {
    long MOD = 1_000_000_007L;
    long [][] nCr;

    public long findWays(List<Integer> vals){

        int n = vals.size();
        //if lessthan 3 element then there is only one way.
        if(n<3) return 1;

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        int root = vals.get(0);

        for(int i=1;i<vals.size();i++){
            int val = vals.get(i);
            if(val<root){
                left.add(val);
            }else{
                right.add(val);
            }
        }

        long currWays = nCr[left.size()+ right.size()][left.size()];

        long leftWays = findWays(left);
        long rightWays = findWays(right);

        long result = (currWays*leftWays)%MOD;

        result = (result*rightWays)%MOD;

        return result;
    }

    public int numOfWays(int[] nums) {
        int n = nums.length;

        nCr = new long[n+1][n+1];
        
        nCr[0][0] = 1;

        for(int i=0;i<=n;i++){
            nCr[i][0] = 1;
            for(int j=1;j<=i;j++)
            {
                nCr[i][j] = (nCr[i-1][j-1] + nCr[i-1][j])%MOD;
            }
        }

        List<Integer> vals = new ArrayList<>();
        for(int num : nums) vals.add(num);
        long totalWays = findWays(vals);
        return (int)(((totalWays-1)+MOD)%MOD);
    }
}