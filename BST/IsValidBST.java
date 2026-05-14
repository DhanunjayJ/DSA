package BST;

public class IsValidBST {
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
    public boolean isValidBST(TreeNode root) {
        /*
        We will pass two vairbales min and max
        to the each node we have to check for each index 
        if the val of that nodes should be in that range.
        if not we return false;
        */
        return isValidBSTH(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    public boolean isValidBSTH(TreeNode root,Long min,Long max){
        if(root==null) return true;
        if(root.val<min || root.val>max) return false;
        boolean left = isValidBSTH(root.left,min,(long)root.val-1);
        boolean right = isValidBSTH(root.right,(long)root.val+1,max);
        return left && right;
    }
}
}
