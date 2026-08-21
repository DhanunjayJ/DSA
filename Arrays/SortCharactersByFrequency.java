class Solution {
    public String frequencySort(String s) {

        int n = s.length();

        if(n<3) return s;
        
        //HashMap for counting characters frequencies.
        HashMap<Character,Integer> hm = new HashMap<>();
        for(int i=0;i<s.length();i++){
            hm.put(s.charAt(i),hm.getOrDefault(s.charAt(i),0)+1);
        }

        //sameFreqCharacters
        //buckets to store the characters with same frequencies.
        List<Character>[] sFChars = new ArrayList[n+1];
        for(Map.Entry<Character,Integer> entry : hm.entrySet()){
            int freq = entry.getValue();
            if(sFChars[freq]==null) sFChars[freq] = new ArrayList<>();
            sFChars[freq].add(entry.getKey());
        }

        StringBuilder st = new StringBuilder();

        //building the final string. 
        for(int i=n;i>=0;i--){
            if(sFChars[i]!=null){
                for(char c : sFChars[i]){
                    int freq = i;
                    while(freq>0){
                        st.append(c);
                        freq--;
                    }
                }
            }
        }
        return st.toString();
    }
}