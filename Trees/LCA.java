/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null) return null;
        // when ever we find a node that is equal to the node 
        // of p or q we return the node then and there
        //if not then we go left and right. 
        if(root.val==p.val || root.val==q.val) return root;
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        //after going left and right check if the below
        //is true then it the case that the current node 
        //the lowest common ancestor.
        if(left!=null && right!=null) return root;
        //if not we only found the left!=null then we 
        //can say that since right doesn't has any 
        //then both might be on the left. else left is the lca
        if(left!=null && right==null) return left;
        //same for right.
        if(left==null && right!=null) return right;
        //if we found none then we return null;
        return null;
    }
}




//Refactored ones

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null || p==root || q==root) return root;
        
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        
        if(left!=null && right!=null) return root;
      
        return left!=null ? left : right;
    }
}