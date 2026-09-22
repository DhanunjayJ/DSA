//https://takeuforward.org/practice/dsa/count-prefix-appearances

class Solution {
    public List<Integer> countPrefixOccurrences(String s) {
        class Node {
            Node child [] = new Node[26];
            int count = 0;
        }
        Node root = new Node();
        int n = s.length();
        for(int i=0;i<n;i++){
            Node curr = root;
            for(int j=i;j<n;j++){
                int idx = s.charAt(j)-'a';
                if(curr.child[idx]==null){
                    curr.child[idx] = new Node();
                }
                curr = curr.child[idx];
                curr.count++;
            }
        }
        List<Integer> ans = new ArrayList<>();
        int count = 0;
        Node curr = root;
        for(int i=0;i<n;i++){
            int idx = s.charAt(i)-'a';
            curr = curr.child[idx];
            ans.add(curr.count);
        }
        return ans;
    }
}