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
    int maxDia = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        maxDia = 0;
        /*
        the largest diameter will not every time need to pass through the
        root node. it can be found it anywhere in theh nodes. 
        at each node we need to get the max depth of the left and max depth of the
        right and pass that 
        */
        if(root==null) return 0;
        depth(root);
        return maxDia;
    }
    public int depth(TreeNode root){
        if(root==null) return 0;
        int left = depth(root.left);
        int right = depth(root.right);
        maxDia = Math.max(maxDia,left+right);
        return 1+Math.max(left,right);
    }
}



// using passing state by refrence

class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        /*
        the largest diameter will not every time need to pass through the
        root node. it can be found it anywhere in theh nodes. 
        at each node we need to get the max depth of the left and max depth of the
        right and pass that 
        */
        if(root==null) return 0;
        int [] res = new int[1];
        depth(root,res);
        return res[0];
    }
    public int depth(TreeNode root,int [] res){
        if(root==null) return 0;
        int left = depth(root.left,res);
        int right = depth(root.right,res);
        res[0] = Math.max(res[0],left+right);
        return 1+Math.max(left,right);
    }
}