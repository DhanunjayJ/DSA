class Solution {
    public int repeatedStringMatch(String a, String b) {
        /*
        to make a equal to b, a should be atleas the size of 
        b. so we increment a or multiply a untill a becomes the 
        size atleast eqaul to size of b.

        so the minimum possible values is always k or k+1.

        not more than that then we need to check if it is possible 
        for the b to be a substring of a if yes. 

        we return k else check of k+1 if it is the case then return k+1.

        if none return -1;
        */

        int n = a.length();
        int m = b.length();

        //ceil division
        int k = (m+n-1)/n;

        StringBuilder st = new StringBuilder();
        for(int i=0;i<k;i++){
            st.append(a);
        }
        if(checkSubString(st.toString(),b)) return k;
        st.append(a);
        if(checkSubString(st.toString(),b)) return k+1;
        return -1;
    }

    public boolean checkSubString(String a,String b){
        long MOD = 1_000_000_007L;
        long B = 31;
        long H = 1;
        long patternHash = 0;
        long windowHash = 0;
        int n = a.length();
        int m = b.length();
        for(int i=0;i<m-1;i++){
            H = (H*B)%MOD;
        }
        for(int i=0;i<m;i++){
            patternHash = ((patternHash*B)+(b.charAt(i)-'a'))%MOD;
            windowHash = ((windowHash*B)+(a.charAt(i)-'a'))%MOD;
        }
        if(patternHash==windowHash) return true;
        int sp = 0;
        for(int ep=m;ep<n;ep++){
            long remove = (((windowHash-(H*(a.charAt(sp)-'a'))%MOD)+MOD)%MOD);
            sp++;
            windowHash = ((remove*B)+(a.charAt(ep)-'a'))%MOD;
            if(windowHash==patternHash) return true;
        }
        return false;
    }
}