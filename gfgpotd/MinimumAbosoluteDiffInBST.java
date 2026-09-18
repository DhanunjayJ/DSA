/* The Node structure is defined as
 class Node {
    int data;
    Node left;
    Node right;
    Node(int data){
        this.data = data;
        left = null;
        right = null;
    }
}
*/

class Solution {
    int prev;
    int min;
    public int absDiff(Node root) {
        // code here
        //inorder traversal of a bst is always in sorted order.
        //so if we do inorder traversal and we find the diff between
        // the previous and current we get the absolute minimum. because
        //the minimum always lies between the sorted values. 
        prev = -1;
        min = Integer.MAX_VALUE;
        helper(root);
        return min;
    }
    public void helper(Node root){
        if(root==null) return;
        helper(root.left);
        if(prev!=-1){
        min = Math.min(Math.abs(root.data-prev),min);
        }
        prev = root.data;
        helper(root.right);
        return;
    }
}
