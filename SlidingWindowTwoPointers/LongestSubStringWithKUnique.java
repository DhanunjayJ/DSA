class Solution {
    public int longestKSubstr(String s, int k) {
        // code here

        /* Expand the window untill we have the k disticnt characters
        in the window. untill <=k and update the maxlen
        when the window becomes >k then reduce the length;
        to only get if the length when excalty equals k then
        we keep a condition that if ==k then consider
        updating the length.
        */
        
        int n = s.length();
        int start = 0,end;
        int maxlen = -1;

        HashMap<Character,Integer> unique = new HashMap<>();

        for(end=0;end<n;end++){

            unique.put(s.charAt(end),unique.getOrDefault(s.charAt(end),0)+1);
            //shrink if the window is not valid
            while(unique.size()>k){
                unique.put(s.charAt(start),unique.get(s.charAt(start))-1);
                if(unique.get(s.charAt(start))==0) unique.remove(s.charAt(start));
                start++;
            }
            // only update the maxlen if the unique elements are == k
            if(unique.size()==k)
            maxlen = Math.max(end-start+1,maxlen);

        }

        return maxlen;
    }
}