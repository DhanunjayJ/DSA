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
    HashMap<Integer,Integer> nodeIdxMap;
    int postIdx;
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        /*
        Construction happens when we do root.left = (recursefuntion)
        root.right = recurse() that recurse returns a treenode. 
        inorder -> left node right
        postorder -> left right node
        so we traverse throught the post order from the right side
        of the array. 
        when we get a node in the postorder then we get the node from the 
        inorder and do the right and left. 
        this time first we do the right and then we do left.
        becuase in the postorder the values are aranged in this way.
        */
        postIdx = postorder.length-1;
        nodeIdxMap = new HashMap<>();

        for(int i=0;i<inorder.length;i++){
            nodeIdxMap.put(inorder[i],i);
        }

        return construct(postorder,0,inorder.length-1);
    }
    //traverse throught the inorder array and create the tree.
    public TreeNode construct(int [] postorder,int left,int right){
        
        if(left>right) return null;

        int nodeVal = postorder[postIdx--];

        TreeNode root = new TreeNode(nodeVal);

        int mid = nodeIdxMap.get(nodeVal);

        root.right = construct(postorder,mid+1,right);
        root.left = construct(postorder,left,mid-1);

        return root;
    }
}