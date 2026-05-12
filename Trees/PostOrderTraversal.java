package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class PostOrderTraversal {
 public class TreeNode {
  int val;
      TreeNode left;
      TreeNode right;
     TreeNode() {}
      TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
         this.left = left;
         this.right = right;
     }
 }
class Solution {

    public List<Integer> postorderTraversalRecursive(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        traversal(root,ans);
        return ans;
    }
    public void traversal(TreeNode root,List<Integer> ans){
        if(root==null) return;
        traversal(root.left,ans);
        traversal(root.right,ans);
        ans.add(root.val);
    }

    public List<Integer> IterativepostorderTraversalUsing2Stacks(TreeNode root) {
        /*
        Post order is of the form left -> right -> root.
        we did preorder which is like root -> left -> right. 
        so if we modify it root -> right -> left. if we get this one we can just reverse it. 
        this is the pattern we will keep it in the stack. so when poping we will the values
        in the order of left -> right -> root.

        How we are going to achive this?
        First, we take the root -> then gets it's left and right. 
        take the root and push it the second stack. 
        and push the left and right to the same stack. 
        and again take the top, which is the right. then push it to the s2 which is the order
        we want node -> right. 
        then add it's child. -> do it untill its finished.
        then add left....
        untill s1 is empty.!!!
        */

        if(root==null) return new ArrayList<>();
        Deque<TreeNode> s1 = new ArrayDeque<>();
        Deque<TreeNode> s2 = new ArrayDeque<>();

        List<Integer> ans = new ArrayList<>();

        s1.push(root);

        while(!s1.isEmpty()){

            TreeNode node = s1.pop();
            s2.push(node);
            if(node.left!=null) s1.push(node.left);
            if(node.right!=null) s1.push(node.right);
        }
        while(!s2.isEmpty())
        ans.add(s2.pop().val);
        return ans;
    }
}
}
