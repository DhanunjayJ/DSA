import java.util.*;

class Solution {
    static class Node {
        Node[] children = new Node[26];
        boolean isEnd = false;
    }

    private static void insert(Node root, String s) {
        Node curr = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (curr.children[idx] == null) {
                curr.children[idx] = new Node();
            }
            curr = curr.children[idx];
        }
        curr.isEnd = true;
    }

    private static boolean hasAllPrefixes(Node root, String s) {
        Node curr = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            curr = curr.children[idx];
            if (curr == null || !curr.isEnd) {
                return false;
            }
        }
        return true;
    }

    public static String completeString(int n, String[] a) {
        Node root = new Node();

        // 1. Build Trie
        for (String s : a) {
            insert(root, s);
        }

        String longest = "";

        // 2. Check each word
        for (String s : a) {
            if (hasAllPrefixes(root, s)) {
                if (s.length() > longest.length()) {
                    longest = s;
                } else if (s.length() == longest.length() && s.compareTo(longest) < 0) {
                    longest = s;
                }
            }
        }

        return longest.isEmpty() ? "None" : longest;
    }
}



class Solution {
  static class Node {
    boolean eow;
    Node [] child = new Node[26];
  }
  public static String completeString(int n, String[] a) {
    // Write your code here.
    List<String> ans = new ArrayList<>();
    Node root = new Node();
    int maxLen = 0;
    Arrays.sort(a,(c,b) -> Integer.compare(c.length(),b.length()));
    for(String st : a){
      Node curr = root;
      int eowCount = 0;
      for(int i=0;i<st.length();i++){
        int idx = st.charAt(i)-'a';
        if(curr.child[idx]==null){
          curr.child[idx] = new Node();
        }
        if(curr.eow) eowCount++;
        curr = curr.child[idx];
      }
      curr.eow = true;
      eowCount++;
      if(eowCount==st.length()){
        if(st.length()>=maxLen){
        ans.add(st);
        maxLen = st.length();
        }
      }
    }
    Collections.sort(ans);
    for(int i=0;i<ans.size();i++){
      // System.out.print(ans.get(i)+" ");
      if(ans.get(i).length()==maxLen){
        return ans.get(i);
      }
    }
    return "None";
  }
}