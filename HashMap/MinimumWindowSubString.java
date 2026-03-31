class Solution {
    public String minWindow(String s, String t) {
        // the brute force appraoch is checking all the subarrays 
        // and count the t chars and check if they are present in the s
        // or use hashmap to count the t characters and count of each character.
        // then manintain a window for start and end, where start and end represnt
        // the possible window. then count the values. if they are same as t if they are
        //same then try to minimize the size of the window. return sring. 
        //everytime the string found with minlen udpate the start index and length. get the substring

        HashMap<Character,Integer> hms = new HashMap<>();
        HashMap<Character,Integer> hmt = new HashMap<>();

        for(int i=0;i<t.length();i++){
            char c = t.charAt(i);
            hmt.put(c,hmt.getOrDefault(c,0)+1);
        }

        int mc = 0;
        //for the substring 
        int ansspidx = -1;
        int ansepidx = -1;
        //for smallest length
        int anslen = Integer.MAX_VALUE;
        //window
        int start = 0;

        for(int end=0; end<s.length(); end++){

            char c = s.charAt(end);

            hms.put(c,hms.getOrDefault(c,0)+1);
            // incement untill <=
            if(hms.get(c)<=hmt.getOrDefault(c,0)){
                mc++;
            }

            while(mc == t.length()){
                //update the start and end points 
                // when len is < len we have.
                if(end-start+1 < anslen){
                    ansspidx = start;
                    ansepidx = end;
                    anslen = end-start+1;
                }

                c = s.charAt(start);

                hms.put(c,hms.get(c)-1);
                //decrement if <
                if(hms.get(c)<hmt.getOrDefault(c,0)){
                    mc--;
                }

                start++;
            }
        }

        return ansspidx == -1 ? "" : s.substring(ansspidx,ansepidx+1);
    }
}