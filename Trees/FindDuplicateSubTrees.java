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
    /*
    Here we try to store all the preorder of the paths of each node in the hashmap. 
    when ever we find smae preorder traversal in the the hashmap, we add that node to the answer. 

    perorder only repersents the the tree in the unique way or post.
    */
    HashMap<String,Integer> paths = new HashMap<>();
    List<TreeNode> ans;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        ans = new ArrayList<>();
        find(root);
        return ans;
    }
    public String find(TreeNode root){
        if(root==null) return "#,";
        String curr = root.val+","+find(root.left)+find(root.right);
        if(paths.getOrDefault(curr,0)==1){
            ans.add(root);
        }
        paths.put(curr,paths.getOrDefault(curr,0)+1);
        return curr;
    }
}