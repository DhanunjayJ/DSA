public class ShortestPalindrome {
    //using Z algorithm

    class Solution {
    public String shortestPalindrome(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        String combined = s+"&"+rev;
        int n = combined.length();
        int m = s.length();
        
        int [] z = new int[n];

        int l = 0;
        int r = 0;
    
        for(int i=1;i<n;i++){
           if(i<=r){
            z[i] = Math.min(r-i+1,z[i-l]);
           }
           while(z[i]+i<n && combined.charAt(z[i])==combined.charAt(z[i]+i)){
            z[i]++;
           }
           if(i+z[i]-1>r){
            l = i;
            r = i+z[i]-1;
           }
        }

        //// Find the longest match that extends to the end of `combined`
        int longestPrefix = 0;
        for(int i=m+1;i<n;i++){
            if(z[i]+i==n){
                longestPrefix = z[i];
                break; //the first mathc that is extending till the end is the match. 
            }
        }
        
        
        StringBuilder st = new StringBuilder();
        st.append(new StringBuilder(s.substring(longestPrefix,m)).reverse());
        //append the acutal string to the answer. 
        st.append(s);
        return st.toString();
    }
}


//using KMP

class Solution {
    public String shortestPalindrome(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        String combined = s+"&"+rev;
        int n = combined.length();
        int m = s.length();
        int [] lps = new int[n];
        int len = 0;
        int maxMatch = 0;
        for(int i=1;i<n;i++){
            while(len>0 && combined.charAt(len)!=combined.charAt(i)){
                len = lps[len-1];
            }
            if(combined.charAt(len)==combined.charAt(i)){
                len++;
            }
            lps[i] = len;
        }
        // if(maxMatch==m) return s;
        // System.out.println(maxMatch);
        // System.out.println(combined);
        // for(int i=0;i<n;i++){
        //     System.out.print(lps[i]);
        // }
        StringBuilder st = new StringBuilder();
        //we take the lps[n-1] as the max match
        st.append(new StringBuilder(s.substring(lps[n-1],m)).reverse());
        //append the acutal string to the answer. 
        st.append(s);
        return st.toString();
    }
}
}
