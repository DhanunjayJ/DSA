package BST;

public class ConstructBSTFromPreorder {
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
    int preIdx = 0;
    public TreeNode bstFromPreorder(int[] preorder) {
        return construct(preorder,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }
    public TreeNode construct(int [] preorder,int min,int max){
        if(preIdx==preorder.length) return null;
        if(preorder[preIdx]<min || preorder[preIdx]>max) return null;
        int val = preorder[preIdx];
        preIdx++;
        TreeNode root = new TreeNode(val);
        root.left = construct(preorder,min,val-1);
        root.right = construct(preorder,val+1,max);
        return root;
    }
}
}
