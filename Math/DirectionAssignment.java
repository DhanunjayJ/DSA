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



class Solution {
    // The total ways we can arrange the values such that k people becomes visible alwasy
    // how to make the people visible out of n-1 and we don't care about the order of arrangement. 
    // since that is the case we use combinatiotns here.
    // atlast we multiply it with the 2 becaus the pos can be R or L.

    final long MOD = 1_000_000_007;

    public int countVisiblePeople(int n, int pos, int k) {
        long [] fact = new long[n+1];
        long [] invfact = new long[n+1];

        int N = n-1;
        fact[0] = 1;

        for(int i=1;i<=n;i++) fact[i] = (fact[i-1]*i)%MOD;
        
        invfact[n] = power(fact[n],MOD-2);

        for(int i=n-1;i>=0;i--) invfact[i] = (invfact[i+1]*(i+1))%MOD;

        long combinations = (((fact[N]*invfact[k])%MOD)*invfact[N-k])%MOD;

        return (int)((2*combinations)%MOD);
    }

    public long power(long base,long exp){
        long ans = 1;
        while(exp>0){
            if((exp&1)==1){
                ans = (ans*base) % MOD;
            }
            base = (base*base)%MOD;
            exp>>=1;
        }
        return ans;
    }
}