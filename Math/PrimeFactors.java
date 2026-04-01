class Solution {
    public static ArrayList<Integer> primeFac(int n) {
        // code here
        // First the smallest prime factor is 2 
        // so remove 2 from the given n all of the times it has.
        ArrayList<Integer> ans = new ArrayList<>();
        
        if(n%2==0) ans.add(2);
        
        while(n%2==0){
            n /= 2;
        }
        
        //now 2 done now check for all the odd numbers.
        // that could divide the n.
        
        for(int i=3;i*i<=n;i+=2){
            if(n%i==0) ans.add(i);
            while(n%i==0){
                n/=i;
            }
        }
        // if any number left, that would be a prime number
        if(n>2){
            ans.add(n);
        }
        
        return ans;
    }
}



// Using sieve Of Erasthosis

class Solution {
    public static ArrayList<Integer> primeFac(int n) {
        // code here
        // we do the same thing using sieve of erastorietis :-) 
        // since the contraints are within the range we could do this
        int [] prime = new int [n+1];
        for(int i=0;i<=n;i++){
            prime[i] = i;
        }
        prime[0] = prime[1] = 1;
        // we start from 2 since it is the smallest prime number
        // and we only need to go till i*i<=n becuase
        // since the facotrs will always appear in pairs.
        // all the factors will be covered in root n range.
        
        for(int i=2;i*i<=n;i++){
            // when i == prime[i] - meaning it is prime!!
            if(prime[i]==i){
                // we set the all the mulitples of i to mininum possible prime number
                for(int j=i;j<=n;j+=i){
                    prime[j] = Math.min(i,prime[j]);
                }
            }
        }
        
        HashSet<Integer> hs = new HashSet<>();
        // hashset for the unique values only.
        while(n>1){
            hs.add(prime[n]);
            n/=prime[n];
        }
        
        return new ArrayList<>(hs);
    }
}