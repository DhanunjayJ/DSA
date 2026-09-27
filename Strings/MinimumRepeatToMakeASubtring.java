//same as repeated string match but here done in the z function

class Solution {
    public int minRepeats(String s1, String s2) {
        // code here
        int n = s1.length();
        int m = s2.length();

        int k = (m+n-1)/n;

        StringBuilder word = new StringBuilder();
        for(int i=0;i<k;i++){
            word.append(s1);
        }
        if(checkSubstring(s2,word.toString()))return k;
        word.append(s1);
        if(checkSubstring(s2,word.toString()))return k+1;
        return -1;
    }
    public boolean checkSubstring(String pattern,String word){
        String combined = pattern+"&"+word;
        int n = combined.length();
        //z[i] store the length of the longes prefix starting 
        //at index i that matches prefix of s.
        int [] z = new int[n];
        int l = 0;
        int r = 0;
        for(int i=1;i<n;i++){
            if(i<=r){
                z[i] = Math.min(r-i+1,z[i-l]);
            }
            while(i+z[i]<n && combined.charAt(z[i])==combined.charAt(z[i]+i)){
                z[i]++;
            }
            if(i+z[i]-1>r){
                l = i;
                r = i+z[i]-1;
            }
        }
        for(int i=0;i<n;i++){
            if(z[i]==pattern.length()){
                return true;
            }
        }
        return false;
    }
};