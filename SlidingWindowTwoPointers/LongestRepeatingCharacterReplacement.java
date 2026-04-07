class Solution {
    public int characterReplacement(String s, int k) {
        /* should we maintain two counters for the unique ness?
        should we do any kind of cancellations? of the a-b? frequencies?
        finding longest subarray with onlly one unique element and
         k extra number of other element?
         should we mainain a frequency of each character of the string in an array?
         we can have atmost k+1 distinct elements in the window. 
         get the maximum frequency and subtract the length - maxfreq of the window.
         if it is equal to k then it is valid. if not it is not valid.
         */
         HashMap<Character,Integer> hm = new HashMap<>();
         int start = 0,end;
         int maxfreq = 0;
         for(end=0;end<s.length();end++){
            char c = s.charAt(end);
            hm.put(c,hm.getOrDefault(c,0)+1);
            maxfreq = Math.max(hm.get(c),maxfreq);
            if((end-start+1)-maxfreq>k){
                hm.put(s.charAt(start),hm.get(s.charAt(start))-1);
                start++;
            }
         }
         return end-start;
    }
}