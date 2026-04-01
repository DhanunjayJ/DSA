//https://leetcode.com/problems/direction-assignments-with-exactly-k-visible-people/description/

class Solution {
    final long MOD = 1_000_000_007;
    public int countVisiblePeople(int n, int pos, int k) {
        int N = n-1;
        if(k>N ||k<0) return 0;
        //precompute the factorials
        long [] fact = new long[N+1];
        fact[0] = 1;
        for(int i=1;i<=N;i++){
            fact[i] = (fact[i-1] * i) % MOD; 
        }
        long numerator = fact[N];
        long denominator = (fact[k] * fact[N-k]) % MOD;
        long invDeno = power(denominator,MOD-2);
        long combinations = (numerator*invDeno)%MOD;
        long finalCombination = (2*combinations) % MOD;
        return (int)finalCombination;
    }
    public long power(long base,long exp){
        long res = 1;
        base %= MOD;
        while(exp>0){
        if(exp%2==1) res = (res*base)%MOD;
        base = (base*base) % MOD;
        exp /=2;
        }
        return res;
    }
}