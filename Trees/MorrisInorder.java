import java.util.ArrayList;
import java.util.List;

public class MorrisInorder {
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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        //we iterate untill curr is not null
        while(curr!=null){
            //if curr.left == null then we have to add the value
            // now. and once we add the value
            // we need to go the right.
            if(curr.left==null){
                ans.add(curr.val);
                curr = curr.right;
            }else{
                //if curr.left!=null meaning
                //we need to traverse the left befroe we add the node itself.
                //we make a path to comeback to the current once the 
                //left subtree is traversed. so to do that
                // we use a currp1 node.
                TreeNode currp1 = curr.left;
                //now we try to find out the right most node
                //in the tree because it the last one to processed.
                // and once we found it. the right most node in the left subtree
                // then we make the right node right == current one. 
                while(currp1.right!=null && currp1.right!=curr){
                    currp1 = currp1.right;
                }
                //once we reached the right most node. 
                //check if the right most node is null again because
                // if it is not null or the right child is current itself.
                if(currp1.right==null){
                    //if it equals to null
                    //make the connection
                    currp1.right = curr;
                    //and then make the curr to jump to the left node
                    curr = curr.left;
                }else{
                    // we came out of the loop because we have traversed all the 
                    //nodes of the tree. and we came back to the same right node
                    // meaning we need to remove the connection 
                    //first make the current right back to the node. 
                    //we don't need this becuase while adding he upper code
                    // adds the node and go to the right.
                    // curr = currp1.right;
                    //remove the connection.
                    currp1.right=null;
                    //add the value
                    ans.add(curr.val);
                    // go the right. 
                    curr = curr.right;
                }
            }
        }
        return ans;
    }
}
}
