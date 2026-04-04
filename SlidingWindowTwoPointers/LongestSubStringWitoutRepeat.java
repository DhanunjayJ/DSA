//using HashSet
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int start = 0;
        int n = s.length();
        HashSet<Character> uniqueEle = new HashSet<>();
        int maxlen = 0;
        for(int end=0;end<n;end++){
            while(uniqueEle.contains(s.charAt(end))){
                uniqueEle.remove(s.charAt(start));
                start++;
            }
            maxlen = Math.max(end-start+1,maxlen);
            uniqueEle.add(s.charAt(end));
        }
        return maxlen;
    }
}

// the hasmap appraoch
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int start = 0;
        int n = s.length();
        HashMap<Character,Integer> uniqueEle = new HashMap<>();
        int maxlen = 0;
        for(int end=0;end<n;end++){
            if(uniqueEle.containsKey(s.charAt(end))){
                start = Math.max(uniqueEle.get(s.charAt(end))+1,start);
            }
            maxlen = Math.max(end-start+1,maxlen);
            uniqueEle.put(s.charAt(end),end);
        }
        return maxlen;
    }
}