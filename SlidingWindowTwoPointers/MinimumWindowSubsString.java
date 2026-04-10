class Solution {
    public String minWindow(String s, String t) {
        //first wee need to count the t.
        HashMap<Character,Integer> hmt = new HashMap<>();
        HashMap<Character,Integer> hms = new HashMap<>();
        for(int i=0;i<t.length();i++){
            hmt.put(t.charAt(i),hmt.getOrDefault(t.charAt(i),0)+1);
        }
        int ansspidx = -1;
        int ansepidx = -1;
        int sp = 0,ep;
        int minlen = Integer.MAX_VALUE;
        int mc = 0;
        for(ep=0;ep<s.length();ep++){
            char c = s.charAt(ep);
            hms.put(c,hms.getOrDefault(c,0)+1);

            if(hms.get(c)<=hmt.getOrDefault(c,0)){
                mc++;
            }

            while(mc==t.length()){
                if(minlen>ep-sp+1){
                    minlen = ep-sp+1;
                    ansspidx = sp;
                    ansepidx = ep;
                }
                //make the window min
                c = s.charAt(sp);
                hms.put(c,hms.get(c)-1);
                if(hms.get(c)<hmt.getOrDefault(c,0)){
                    mc--;
                }
                sp++;
            }
        }
        return ansspidx==-1 ? "" : s.substring(ansspidx,ansepidx+1);
    }
}