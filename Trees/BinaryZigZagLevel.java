import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BinaryZigZagLevel {
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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        /*
        do level order and after each level just revese the list.
        */
        List<List<Integer>> ans = new ArrayList<>();
        if(root==null) return ans;
        Deque<TreeNode> dq = new ArrayDeque<>();
        dq.add(root);
        boolean isReverse = false;
        while(!dq.isEmpty()){
            int size = dq.size();
            LinkedList<Integer> temp = new LinkedList<>();
            for(int i=0;i<size;i++){
                TreeNode rem = dq.pollFirst();
                if(isReverse){
                    temp.addFirst(rem.val);
                }else{
                    temp.addLast(rem.val);
                }
                if(rem.left!=null) dq.addLast(rem.left);
                if(rem.right!=null) dq.addLast(rem.right);
            }
            ans.add(temp);
            isReverse = !isReverse;
        }
        return ans;
    }
}
}
