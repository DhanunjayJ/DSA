/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/

class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> ans = new ArrayList<>();
        Deque<Node> q = new ArrayDeque<>();
        if(root==null) return ans;
        q.add(root);
        while(!q.isEmpty()){
            int size = q.size();
            List<Integer> temp = new ArrayList<>();
            for(int i=0;i<size;i++){
                Node rem = q.pollFirst();
                temp.add(rem.val);
                for(Node n : rem.children){
                    q.add(n);
                }
            }
            ans.add(temp);
        }
        return ans;
    }
}