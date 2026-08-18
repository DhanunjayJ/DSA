class nthUglyNumber3 {
    public int nthUglyNumber(int n, int a, int b, int c) {
        /*

        here we do bs on answer, we get the mid, and check the count of the ugly numbers >=mid.
        if equal to n. then we go rigth untill the count == mid;
        we want to go toward the equal to == n.

        to get the count of ugly numbers between the values.
        we need to do mid/a+mid/b+mid/c 
        remove the common ones out by lcm of ab,bc,ca and 
        add the common between three ones 
        */
        long low = 0;
        long high = 2 * (long) 1e9;

        long ab = lcm(a,b);
        long bc = lcm(b,c);
        long ca = lcm(c,a);
        long abc = lcm(a,bc);

        long ans = 0;
        
        while(low<=high){
            long mid = low+(high-low)/2;
            long count = mid/a+mid/b+mid/c 
                        -mid/ab-mid/bc-mid/ca
                        +mid/abc;
                        //adding the count
                        //removing the over count
                        //adding the common 
            if(count>=n){
                ans = mid;
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return (int) ans;
    }

    public long gcd(long a,long b){
        return a==0 ? b : gcd(b%a,a);
    }

    public long lcm (long a,long b){
        return (a/gcd(a,b)*b);
    }
}