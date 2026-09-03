public class IsoimorphicStrings {
    //lastseenapproach
    class Solution {
    public boolean isIsomorphic(String s, String t) {
        int [] sLs = new int[256];
        int [] tLs = new int[256];
        for(int i=0;i<s.length();i++){
            char a = s.charAt(i);
            char b = t.charAt(i);
            if(sLs[a]!=tLs[b]){
                return false;
            }
            sLs[a] = i+1;
            tLs[b] = i+1;
        }
        return true;
    }
}

  // map appoach
  class Solution {
    public boolean isIsomorphic(String s, String t) {
        char[] smap = new char[257];
        char[] tmap = new char[257];
        for(int i=0;i<s.length();i++){
            char sc = s.charAt(i);
            char tc = t.charAt(i);
            if(smap[sc]==0 && tmap[tc]==0){
                smap[sc] = tc;
                tmap[tc] = sc; 
            }else if((smap[sc]!=0 && smap[sc]!=tc)|| 
            (tmap[tc]!=0 && tmap[tc]!=sc )){
                return false;
            }
        }
        return true;
    }
}
}
