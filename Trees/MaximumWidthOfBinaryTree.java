import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumWidthOfBinaryTree {
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
class vNode{
    TreeNode node;
    int index;
    vNode(TreeNode node,int index){
        this.node = node;
        this.index = index;
    }
}

class Solution {
    public int widthOfBinaryTree(TreeNode root) {

        /*
        for a width to exist there must me atleast two nodes in that index.
        we can traverse throght the array index order and get the last one vt index and 
        right one vt index and check if that index width is maximum thatn the previos ones
        if it is then we update the max widht.
        BFS index order works.
        */
        int maxWidth = 0;
        Deque<vNode> q = new ArrayDeque<>();
        q.add(new vNode(root,0));
        while(!q.isEmpty()){
            vNode first = q.getFirst();
            vNode last = q.getLast();
            maxWidth = Math.max(last.index-first.index+1,maxWidth);
            int size = q.size();
            for(int i=0;i<size;i++){
                vNode rem = q.poll();
                TreeNode node = rem.node;
                int index = rem.index;
                if(node.left!=null){q.addLast(new vNode(node.left,2*index));}
                if(node.right!=null){q.addLast(new vNode(node.right,2*index+1));}
            }
        }
        return maxWidth;
    }
}
}
