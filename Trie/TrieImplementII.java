import java.util.* ;
import java.io.*; 

public class Trie {

    public class Node {
    int eowCount = 0;
    int prefixCount = 0;
    Node [] childern = new Node[26];
    }

    public final Node root;

    public Trie() {
        // Write your code here.
        root = new Node();
    }

    public void insert(String word) {
        // Write your code here.
        Node curr = root;
        for(int i=0;i<word.length();i++){
            int idx = word.charAt(i)-'a';
            if(curr.childern[idx]==null){
                curr.childern[idx] = new Node();
            }
            curr = curr.childern[idx];
            curr.prefixCount++;
        }
        curr.eowCount++;
    }

    public int countWordsEqualTo(String word) {
        // Write your code here.
        Node endWord = findWord(word,false);
        return endWord==null ? 0 : endWord.eowCount;
    }

    public int countWordsStartingWith(String word) {
        // Write your code here.
        Node node = findWord(word,false);
        return node==null ? 0 : node.prefixCount;
    }

    public void erase(String word) {
        // Write your code here.
        findWord(word,true);
    }

    public Node findWord(String word,boolean isEarse){
        Node curr = root;
        for(int i=0;i<word.length();i++){
            int idx = word.charAt(i)-'a';
            if(curr.childern[idx]==null){
                return null;
            }
            curr = curr.childern[idx];
            if(isEarse) curr.prefixCount--;
        }
        if(isEarse) curr.eowCount--;
        return curr;
    }

}



//other way

public class Trie {

    static class Node {
        Node[] children = new Node[26];
        int endsCount = 0;
        int prefixCount = 0;
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    // 1. Insert: O(|WORD|) time
    public void insert(String word) {
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (curr.children[idx] == null) {
                curr.children[idx] = new Node();
            }
            curr = curr.children[idx];
            curr.prefixCount++; // word passes through this node
        }
        curr.endsCount++; // word terminates here
    }

    // 2. Count exact matches: O(|WORD|) time
    public int countWordsEqualTo(String word) {
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (curr.children[idx] == null) {
                return 0;
            }
            curr = curr.children[idx];
        }
        return curr.endsCount;
    }

    // 3. Count words with prefix: O(|PREFIX|) time
    public int countWordsStartingWith(String prefix) {
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            int idx = prefix.charAt(i) - 'a';
            if (curr.children[idx] == null) {
                return 0;
            }
            curr = curr.children[idx];
        }
        return curr.prefixCount;
    }

    // 4. Erase one occurrence: O(|WORD|) time
    public void erase(String word) {
        // Guaranteed to exist per problem constraints
        Node curr = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            curr = curr.children[idx];
            curr.prefixCount--; // decrement passing count
        }
        curr.endsCount--; // decrement word completion count
    }
}