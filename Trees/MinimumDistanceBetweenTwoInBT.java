/* A binary tree node
class Node {
    public int data;
    public Node left;
    public Node right;

    public Node(int val) {
        data = val;
        left = null;
        right = null;
    }
}
*/

class Solution {
    public int findDist(Node root, int a, int b) {
        // code here
        /*
        here we find the lca of the two nodes and find he ditance of the both nodes
        from the lca. 
        */
        Node lca = lca(root,a,b);
        int ad = find(lca,a);
        int bd = find(lca,b);
        return ad+bd;
    }
    public Node lca(Node root,int a,int b){
        if(root==null || root.data==a || root.data==b) return root;
        Node left = lca(root.left,a,b);
        Node right = lca(root.right,a,b);
        if(left!=null && right!=null) return root;
        return left!=null ? left : right;
    }
    public int find(Node root,int val){
        if(root==null) return -1;
        if(root.data==val) return 0;
        int left = find(root.left,val);
        if(left!=-1) return left+1;
        int right = find(root.right,val);
        if(right!=-1) return right+1;
        return -1;
    }
}