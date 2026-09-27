class Solution {
    ArrayList<Integer> search(String pat, String txt) {
        // code here
        StringBuilder st = new StringBuilder();
        st.append(pat);
        st.append("#");
        st.append(txt);
        int m = pat.length();
        int n = st.length();
        int [] lps = new int[n];
        int len = 0;
        //KMP - algorithm
        for(int i=1;i<n;i++){
            //if values don't match fall back to lps[len-1];
            while(len>0 && st.charAt(len)!=st.charAt(i)){
                len = lps[len-1];
            }
            //if match do len++;
            if(st.charAt(len)==st.charAt(i)){
                len++;
            }
            //store the lps value len.
            lps[i] = len;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=0;i<n;i++){
            if(lps[i]==pat.length()){
                ans.add(i-2*m);
            }
        }
        return ans;
    }
}