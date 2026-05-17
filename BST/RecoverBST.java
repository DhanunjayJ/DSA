public class RecoverBST {
    /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    /*
    so If we think in terms of a array of increasing vlaues. then 
    the when ever there is a violation of the nodes 
    or currval<prevval then we need to swap both of them. 
    else if the there is anot ther violation then the secnd on will be the 
    next on to be swap with the first. 

    there are two potential violation of nodes in the bst.
    when he first time happens, the second one was just next to the prev ones.
    the possible second one. if not wait untill the second violation. 

    once the second violaation is done then then we update the second one. 
    */
    TreeNode first,second,prev;
    public void recoverTree(TreeNode root) {
        first=second=prev = null;
        inorder(root);
        int t = first.val;
        first.val = second.val;
        second.val = t;
    }
    public void inorder(TreeNode root){
        if(root==null) return;
        inorder(root.left);
        if(prev!=null && root.val<prev.val && first==null){
            first = prev;
            second = root;
        }else if(prev!=null && root.val<prev.val && first!=null){
            second=root;
        }
        prev = root;
        inorder(root.right);
    }
}
}
