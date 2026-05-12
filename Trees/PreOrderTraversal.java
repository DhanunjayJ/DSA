package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class PreOrderTraversal {


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
    public List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> ans = new ArrayList<>();

        if(root==null) return ans;

        ArrayDeque<TreeNode> st = new ArrayDeque<>();

        st.push(root);

        while(!st.isEmpty()){

            TreeNode rem = st.pop();

            ans.add(rem.val);

            if(rem.right!=null){
                st.push(rem.right);
            }

            if(rem.left!=null){
                st.push(rem.left);
            }
        }
    return ans;
        }
}

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
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        traversal(ans,root);
        return ans;
    }
    public void traversal(List<Integer> ans, TreeNode root){
        if(root==null) return;
        ans.add(root.val);
        if(root.left!=null) traversal (ans,root.left);
        if(root.right!=null) traversal (ans,root.right);
    }
}

}
