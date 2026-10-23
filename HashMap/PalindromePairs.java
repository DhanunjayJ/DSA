class Solution {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> ans = new ArrayList<>();
        HashMap<String,Integer> hm = new HashMap<>();
        int n = words.length;
        for(int i = 0;i<n;i++){
            hm.put(words[i],i);
        }
        for(int i=0;i<n;i++){
            /*
            Spliting the each strig to L and R
            if lef tis palindrome then valid sring would be
            word2 + L + R so reverse the right and check if it there in
            the hashmap.

            else if right is palidrome. 

            L + R + word2 reverse L and check if word2 is present int he 
            hashmap. 
            */
            String word = words[i];
            int wn = word.length();
            for(int j=0;j<=wn;j++){
                String left = word.substring(0,j);
                String right = word.substring(j);
                // Case 1: left is a palindrome, check if reverse(right) exists in map
                if(isPalindrome(left)){
                    String revRight = new StringBuilder(right).reverse().toString();
                    if(hm.containsKey(revRight) && hm.get(revRight)!=i){
                        ans.add(Arrays.asList(hm.get(revRight),i));
                    }
                }
                // Add j > 0 to prevent duplicate checking when left is empty (handled by Case 1)
                if(j<wn && isPalindrome(right)){
                    String revLeft = new StringBuilder(left).reverse().toString();
                    if(hm.containsKey(revLeft) && hm.get(revLeft)!=i){
                        ans.add(Arrays.asList(i,hm.get(revLeft)));
                    }
                }
            }
        }
        return ans;
    }
    public boolean isPalindrome(String st){
       int start = 0;
       int end = st.length()-1;
       while(start<end){
        if(st.charAt(start++)!=st.charAt(end--)) return false;
       }
       return true;
    }
}