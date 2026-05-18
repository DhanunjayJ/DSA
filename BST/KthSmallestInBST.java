package BST;

import java.util.ArrayList;
import java.util.List;

public class KthSmallestInBST {

    //O(n)
    public int kthSmallest(TreeNode root, int k) {
        /*
        We know that the inorder of a bst is always sorted. 
        we can do bst of inorder and assign a each node a value of ranking.
        or we can just do the inorder and store the values in the array and get the kthe smllest
        one which is at the kth -1 index of the arraylist.
        */
       List<Integer> inorder = new ArrayList<>();
       inorder(inorder,root);
       return inorder.get(k-1);
    }

    public void inorder(List<Integer> inorder, TreeNode root){
        if(root==null) return;
        inorder(inorder,root.left);
        inorder.add(root.val);
        inorder(inorder,root.right);
    }

//o(h+k) approach

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
    
    int count = 0;
    int result = -1;

    public int kthSmallest(TreeNode root, int k) {
        /*
        We know that the inorder of a bst is always sorted. 
        we can do bst of inorder and assign a each node a value of ranking.
        or we can just do the inorder and store the values in the array and get the kthe smllest
        one which is at the kth -1 index of the arraylist.
        */
       inorder(root,k);
       return result;
    }

    public void inorder(TreeNode root,int k){
        if(root==null) return;

        inorder(root.left,k);
        count++;
        if(count==k){
            result = root.val;
            return;
        }
        inorder(root.right,k);
    }
}

}
