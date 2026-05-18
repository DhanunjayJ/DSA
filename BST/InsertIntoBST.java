package BST;

public class InsertIntoBST {
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
    public TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode node = new TreeNode(val);
        if(root==null) return node;
        TreeNode curr = root;
        TreeNode prev = null;
        while(curr!=null){
            prev = curr;
            if(curr.val<val){
                curr = curr.right;
                if(curr==null){
                    prev.right = node;
                }
            }else{
                curr = curr.left;
                if(curr==null)
                {
                    prev.left = node;
                }
            }
        }
        return root;
    }
}
}
