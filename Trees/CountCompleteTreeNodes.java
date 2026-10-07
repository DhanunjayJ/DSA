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
    public int countNodes(TreeNode root) {
        /*

        The core : Intution is When we have a complete binary tree. 
        then atleast one subtrees of the left and right is perfect binary tree.

        so we count the the nodes of the perfect one and reject the half of it. 
        and go to the other tree wich is not perfect. 

        this binary search on the binary tree. 

        so there are two cases on how we check the heights.

        we check the height based on the how left we could go in a tree. 

        if we do that on the root it would give use the left hieght. 

        if we do that on the root.right it will be one level lower thatn the left 
        because we are strting with one level less which is root.right. 

        to check which tree is perfect we need to compare the heights of the both
        substrees. 

        if height of the left == height of the right -1 
        meaning that the leaf that are not filled are on the right. 

        so that left tree was perfect so we count it. 

        the maximum number of noes the tree could have is 2^(h+1)-1.

        so if we only consider the left substree the height would be (h-1) where h 
        is the current tree height. 

        so to get all the nodes of he left substrees
        then math will be

        2^((h-1)+1)-1 -> 2^h - 1

        now we need to add the root to the left subtree that makes it -> 2^h - 1 + 1

        that imples -> 2^h -> can be calcualted using this (1<<h)

        this give all the nodes on the left + node and we need to count the 
        same way for the right tree.

        if height of the left != height of the right -1
        meanign the hegith of the left and right are not same. 
        the incomplete leaf nodes are on the left tree. so 
        the hieght of the right becomees h-2.

        this makes the right ones as the perfect tree so we calcualte the nodes on the right. 

        we do the same calculation and find the total nodes. 

        here it would be 2^(h-1) + nodes on the left. 

        */
        int height = leftHeight(root);
        if(height<0) return 0;
        int totalNodes = 0;
        // left subtree is a pefect binary tree. 
        if(leftHeight(root.right)==height-1){
            totalNodes += (1<<height) + countNodes(root.right);
        }else{
            //right subtree is a perfect binary tree. 
            totalNodes += (1<<height-1) + countNodes(root.left);
        }

        return totalNodes;
    }

    public int leftHeight(TreeNode root){
        if(root==null) return -1;
        return leftHeight(root.left)+1;
    }
}