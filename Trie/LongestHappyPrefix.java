class Solution {
    //using LPS Array
    public String longestPrefix(String s) {
        int n = s.length();
        int [] lps = new int[n];
        int len = 0;
        for(int i=1;i<n;i++){
            while(len>0 && s.charAt(len)!=s.charAt(i)){
                len = lps[len-1];
            }
            if(s.charAt(len)==s.charAt(i)){
                len++;
            }
            lps[i] = len;
        }
        // System.out.println(s);
        // for(int i:lps) System.out.print(i);
        return s.substring(0,lps[n-1]);
    }


    //using z algorithm
    class Solution {
    public String longestPrefix(String s) {
        int n = s.length();

        int [] z = new int[n];

        int l = 0;
        int r = 0;

        for(int i=1;i<n;i++){
            if(i<=r){
                z[i] = Math.min(r-i+1,z[i-l]);
            }
            while(z[i]+i<n && s.charAt(z[i])==s.charAt(z[i]+i)){
                z[i]++;
            }
            if(z[i]+i-1>r){
                l = i;
                r = z[i]+i-1;
            }
        }

        for(int i=0;i<n;i++){
            if(z[i]+i==n){
                return s.substring(i);
            }
        }
        
        return "";
    }
}
}