package BST;

public class InorderSuccessor {
   /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */


public class Solution {
    /*
     * @param root: The root of the BST.
     * @param p: You need find the successor node of p.
     * @return: Successor of p.
     */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        // write your code here
        TreeNode successor = null;
        while(root!=null){
            if(root.val>p.val){
                successor = root;
                root = root.left;
            }else{
                root = root.right;
            }
        }
        return successor;
    }
} 
}
