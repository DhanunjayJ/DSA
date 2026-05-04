import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {
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
    public List<String> binaryTreePaths(TreeNode root) {
        /*
        First check if the node is leaft or not if yes return
        if not check if the left!=null then expore the left
        check if right!=null then explore right;
        return ans;
        */
        List<String> ans = new ArrayList<>();
        traverse(root,ans,"");
        return ans;
    }
    public void traverse(TreeNode root,List<String> ans,String path){
       path += root.val;
       if(root.left==null && root.right==null){
        ans.add(path);
        return;
       }
       if(root.left!=null) traverse(root.left,ans,path+"->");
       if(root.right!=null) traverse(root.right,ans,path+"->");
    }
}


//using string builder
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
    public List<String> binaryTreePaths(TreeNode root) {
        /*
        First check if the node is leaft or not if yes return
        if not check if the left!=null then expore the left
        check if right!=null then explore right;
        return ans;
        */
        List<String> ans = new ArrayList<>();
        traverse(root,ans,new StringBuilder());
        return ans;
    }
    public void traverse(TreeNode root,List<String> ans,StringBuilder st){
        int len = st.length();

        if(isLeaf(root)){
            st.append(root.val);
            ans.add(st.toString());
            st.setLength(len);
            return;
        }
        
        st.append(root.val).append("->");
        if(root.left!=null){
            traverse(root.left,ans,st);
        }
        if(root.right!=null){
            traverse(root.right,ans,st);
        }
        st.setLength(len);
    }
    public boolean isLeaf(TreeNode root){
        return root.left==null && root.right==null;
    }
}
}
