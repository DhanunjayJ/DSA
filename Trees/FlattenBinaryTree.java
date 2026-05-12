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
    public void flatten(TreeNode root) {
        flattenh(root);
    }

    public TreeNode flattenh(TreeNode root){
        /*

        To get access to the last node so that it will be easy to connect other nodes
        we return the last node everytime.
        since every time we have the access to the left and right node
        we can change the pointers and get the nodes.

        */
        
        if(root==null) return null;

        TreeNode leftSub = flattenh(root.left);
        TreeNode rightSub = flattenh(root.right);

        if(leftSub==null && rightSub == null){

            return root;

        }else if(leftSub==null && rightSub!=null){

            return rightSub;

        }else if(leftSub!=null && rightSub==null){

            TreeNode left = root.left;
            root.right = left;
            root.left = null;
            //here leftSub Contains the end pointer of the linkedlist.
            return leftSub;

        }else if(leftSub!=null && rightSub!=null){
            
            TreeNode left = root.left;
            TreeNode right = root.right;

            root.right = left;
            root.left = null;
            leftSub.right = right;
            return rightSub;
        }
        return root;
    }
}