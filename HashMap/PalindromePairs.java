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


//using trie

class Node {
    Node [] child = new Node[26];
    int index = -1;
    List<Integer> palindromeIdx = new ArrayList<>();
}

class Solution {

    public void insert(String word,int index,Node root){
        Node curr = root;
        for(int i=word.length()-1;i>=0;i--){
            int idx = word.charAt(i)-'a';
            if(curr.child[idx]==null){
                curr.child[idx] = new Node();
            }
            /*
            since we are insering the strings in the reverse order
            w1 + w2 what we need. 
            so if we have the w2 and we need to check for reverse of w2 which is w1 to make it palidrome. 
            
            so for each w2 we check if that reverse is present in the trie. 

            if the one we are search is w2, if w2 length < what we are seaching for. 

            w1 + w2 -> if we divided the w1 to two parts matching one and unmattthcing 
            match + unmacth + w2. -> now we want the unmathc part to be a palidrome. 

            to hadle thisc ase of words where the w2 is shorter than the trie branch itself. 
            we need to check how many indexes in the current node if we stop here are palindrome. 
            and add them. so it will be easier to find the match. 
            */
            if(isPali(word,0,i)){
                curr.palindromeIdx.add(index);
            }
            curr = curr.child[idx];
        }
        //thsis shows the ending of a word and 
        // if that totla word is a palindrome we add it to the list. 
        curr.index = index;
        if(isPali(word,0,word.length()-1)){
            curr.palindromeIdx.add(index);
        }

    }

    public void search(String word,int index,Node root,List<List<Integer>> ans){
        Node curr = root;
        int n = word.length();
        for(int i=0;i<n;i++){
            // If the remaining part of the word is a palindrome and current node has a valid word index
            if(curr.index!=-1 && curr.index!=index && isPali(word,i,n-1)){
                ans.add(Arrays.asList(index,curr.index));
            }

            int idx = word.charAt(i)-'a';
            curr = curr.child[idx];
            if(curr==null) return;
        }
        
        //check if the current index terminate at a valid word
        if(curr.index!=-1 && curr.index!=index){
            ans.add(Arrays.asList(index,curr.index));
        }

        //check remainging palidrome indexes stored in this node.
        for(int j: curr.palindromeIdx){
            if(index!=j)
            ans.add(Arrays.asList(index,j));
        }
    }

    public boolean isPali(String st,int start,int end){
        while(start<=end){
            if(st.charAt(start)!=st.charAt(end)) return false;
            start++;
            end--;
        }
        return true;
    }

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = words.length;
        Node root = new Node();
        for(int i=0;i<n;i++){
            insert(words[i],i,root);
        }
        for(int i=0;i<n;i++){
            search(words[i],i,root,ans);
        }
        return ans;
    }
}


//hashmap approach with dp

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
            boolean [][] dp = getPalindromeTable(word);

            for(int j=0;j<=wn;j++){
            
                // Case 1: left is a palindrome, check if reverse(right) exists in map
                if(j==0 || dp[0][j-1]){
                    String revRight = new StringBuilder(word.substring(j)).reverse().toString();
                    if(hm.containsKey(revRight) && hm.get(revRight)!=i){
                        ans.add(Arrays.asList(hm.get(revRight),i));
                    }
                }

                if(j<wn && dp[j][wn-1]){
                    String revLeft = new StringBuilder(word.substring(0,j)).reverse().toString();
                    if(hm.containsKey(revLeft) && hm.get(revLeft)!=i){
                        ans.add(Arrays.asList(i,hm.get(revLeft)));
                    }
                }
            }
        }
        return ans;
    }
    public boolean[][] getPalindromeTable(String st){
       int n = st.length();
       boolean [][] dp = new boolean[n][n];
       for(int len=1;len<=n;len++){
        for(int i=0;i<=n-len;i++){
            int j = i+len-1;
            if(st.charAt(i)==st.charAt(j)){
                dp[i][j] = (len<=2) ? true : dp[i+1][j-1];
            }
        }
       }
       return dp;
    }
}