class Solution {
    public int numberOfSubstrings(String s) {
        //First get the substring that has atleast one of the abc, then 
        //multiply with start and endpoints to get the numer os fusbtrings. 
        int [] freq = new int[3];
        int start = 0;
        int count = 0;
        int n = s.length();
        for(int end=0;end<s.length();end++){
            freq[s.charAt(end)-'a']++;
            // adding the subarray count Which is n-end.
            while(freq[0]>=1 && freq[1]>=1 && freq[2]>=1){
                count += n-end;
                freq[s.charAt(start)-'a']--;
                start++;
            }
        }
        return count;
    }
}