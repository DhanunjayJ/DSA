class Solution {
    public ArrayList<Integer> rabinKarp(String text, String pattern) {
        // Code here
        long patternHash = 0;
        long windowHash = 0;
        long B = 31;
        long MOD = 1_000_000_007L;
        long H = 1;
        int n = text.length();
        int m = pattern.length();
        for(int i=0;i<m-1;i++){
            H = (H*B)%MOD;
        }
        for(int i=0;i<m;i++){
            patternHash = ((patternHash*B)+(pattern.charAt(i)-'a'))%MOD;
            windowHash = ((windowHash*B)+(text.charAt(i)-'a'))%MOD;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        if(patternHash==windowHash) ans.add(0);
        int sp = 0;
        for(int ep=m;ep<n;ep++){
            long spVal = text.charAt(sp)-'a';
            sp++;
            long removed = (((windowHash - (spVal*H))%MOD)+MOD)%MOD;
            windowHash = ((removed*B)+(text.charAt(ep)-'a'))%MOD;
            if(windowHash==patternHash) ans.add(sp);
        }
        return ans;
    }
}