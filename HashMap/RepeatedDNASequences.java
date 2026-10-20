class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        HashMap<String,Integer> seq = new HashMap<>();
        int n = s.length();
        List<String> ans = new ArrayList<>();
        if(n<10) return ans;
        StringBuilder st = new StringBuilder();
        for(int i=0;i<10;i++){
            st.append(s.charAt(i));
        }
        seq.put(st.toString(),1);
        for(int i=10;i<n;i++){
            st.deleteCharAt(0);
            st.append(s.charAt(i));
            String temp = st.toString();
            if(seq.getOrDefault(temp,0)==1){
                ans.add(temp);
            }
            seq.put(temp,seq.getOrDefault(temp,0)+1);
        }
        return ans;
    }
}


//using the Rolling Hash
class Solution {
    private int getVal(char c) {
        switch (c) {
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default: return 0;
        }
    }
    public List<String> findRepeatedDnaSequences(String s) {
         int n = s.length();
        int sp = 0;
        List<String> ans = new ArrayList<>();
        if(n<10) return ans;
        long rollingHash = 0;
        long MOD = 1_000_000_007;
        long B = 7;
        long H = 1;
        for(int i=0;i<9;i++){
            H = (H*B)%MOD;
        }
        HashMap<Long,Integer> count = new HashMap<>();
        for(int i=0;i<10;i++){
            rollingHash = (rollingHash*B + getVal(s.charAt(i)))%MOD;
        }
        count.put(rollingHash,1);
       
        for(int i=10;i<n;i++){
            long removed = (H*getVal(s.charAt(sp)))%MOD;
            sp++;
            rollingHash = (((((rollingHash-removed)+MOD)%MOD)*B)+getVal(s.charAt(i)))%MOD;
            if(count.getOrDefault(rollingHash,0)==1){
                ans.add(s.substring(sp,sp+10));
            }
            count.put(rollingHash,count.getOrDefault(rollingHash,0)+1);
        }
        return ans;
    }
}

// using bit manipulation

class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        HashSet<Integer> seen = new HashSet<>();
        HashSet<String> repeated = new HashSet<>();
        int [] map = new int[26];
        //since the values are only 4 we can use two bits
        //01,10,11,00 as a bit and set them in the interger and use them
        //as a bit mask and check if that appread previsoly. 
        //we only care about the first 20 bits from the msb we left shift
        //the previos one and add new ones.
        map['A'-'A'] = 0;
        map['C'-'A'] = 1;
        map['G'-'A'] = 2;
        map['T'-'A'] = 3;
        int mask = 0;
        int n = s.length();
        if(n<10) return new ArrayList<>();
        for(int i=0;i<n;i++){
            // Shift left by 2 bits, add the new character, and keep only the last 20 bits
            mask = (mask<<2 | map[s.charAt(i)-'A']) & 0xFFFFF;
            if(i>=9){
                if(!seen.add(mask)){
                    repeated.add(s.substring(i-9,i+1));
                }
            }
        }
        return new ArrayList<>(repeated);
    }
}