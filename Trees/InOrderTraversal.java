package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InOrderTraversal {
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
        traversal(root,ans);
        return ans;
    }
    public void traversal(TreeNode root,List<Integer> ans){
        if(root==null) return;
        traversal(root.left,ans);
        ans.add(root.val);
        traversal(root.right,ans);
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
    public List<Integer> inorderTraversalIterative (TreeNode root) {
        Deque<TreeNode> st = new ArrayDeque<>();
        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        while(curr!=null || !st.isEmpty()){
            //first we need to go as left as possible
            while(curr!=null){
                st.push(curr);
                curr = curr.left;
            }
            //once we reach the null
            // we are left with the node on the top so we add it
            curr = st.pop();
            ans.add(curr.val);
            //expore the right side.
            curr = curr.right;
        }
        return ans;
    }
}

}
