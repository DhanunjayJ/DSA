package BST;

public class DeleteNodeInBST {
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
    public TreeNode deleteNode(TreeNode root, int key) {
        /*
        So first we need to think of deletion as a node
        value should be replaced with other. 
        what node value to replace with?

        if we go the right most right of the target values
        . we get the largest value but we that value cant replace.
        since that value is larget than the taget we have to restructre 
        the whole  right child and that won't work. 

        If we go to the left child and get the max on the left child 
        by going as right as possbile.

        we find a value that is satisfying the bothe the node left and right 
        first rhe left value will be less than that value and also.
        the right value will be greater than that. 

        since this satisfies the bst conditions we do this replacement 
        and atlast we delete the node. 

        How do we delete the node??

        we need parent access for that... so what we do is we 
        start start construting the tree from the root. 
        when we found the target we return null.
        so 
        */

        if(root==null) return null;

        if(root.val>key){

            root.left = deleteNode(root.left,key);

        }else if(root.val<key){

            root.right = deleteNode(root.right,key);

        }else{

            //if we are at the target!!
            // check if that node is leaf

            if(root.left==null && root.right==null){

                return null;

            }else if(root.left!=null && root.right==null){

                return root.left;

            }else if(root.left==null && root.right!=null){

                return root.right;

            }else{
                //if both are not null

                int max = findMax(root.left);
                root.val = max;
                root.left = deleteNode(root.left,max);
                
            }

        }
        return root;
    }

    public int findMax(TreeNode root){
        if(root==null) return Integer.MIN_VALUE;
        return Math.max(root.val,findMax(root.right));
    }
}
}
