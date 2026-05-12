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
    public boolean isSymmetric(TreeNode root) {
       return isEqual(root.left,root.right);
    }
    public boolean isEqual(TreeNode left,TreeNode right){
        if(left==null && right==null) return true;
        if(left==null || right==null || left.val!=right.val) return false;
        return isEqual(left.left,right.right) && isEqual(left.right,right.left);
    }
}


//Iterative Appraoch
//can be solved the same using the Stack. using the old Stack not Deque.
//because it don't allow null values in it

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
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;

        /*
        Deque doen't allow null values!!!
        */
        Queue<TreeNode> q = new LinkedList<>();
        
        q.offer(root.left);
        q.offer(root.right);
        
        while(!q.isEmpty()){
            
            TreeNode t1 = q.poll();
            TreeNode t2 = q.poll();
            
            if(t1==null && t2==null) continue;
            if(t1==null || t2==null || t1.val!=t2.val) return false;

            q.offer(t1.left);
            q.offer(t2.right);

            q.offer(t1.right);
            q.offer(t2.left);
        }
        return true;
    }
}