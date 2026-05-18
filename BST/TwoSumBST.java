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
    // O(n) and O(h) appraoch
    public boolean findTarget(TreeNode root, int k) {

        /*
        First approach is use a hasmap and store all the values of 
        the nodes in it and for each node check if there exist a node
        in the hashmap. the compliment one k-node.val 
        if yes then return true. else
        do the same for the root.left
        and root.right. 
        untill you find the true. 

        that is O(n) and s.c:o(n)

        second approah is just do inorder and store the values in a list
        and do two pointers to find if the two sum value is there or not. 

        third approach is 
        for each node we do searchon the left and right. 
        nlogn approach. with space. o(1)

        optimal
        o(n) with o(h) space.

        WE maintain two stack one for the inorder and one for the 
        reverse inorer.

        inorder iterator we keep the smallest elemtn always on the top
        and reverse inorder we keep the largest elemtn always on the top of the
        stack. 
        we peek the both the values
        if the sum==k 
        then we return true
        else if sum>k then we pop the larger stack
        if sum<k we pop the smaller stack 
        we do this untill val1>=va2 if that is the case meaning 
        there is no pair exists. here.
        */

        Stack<TreeNode> st1 = new Stack<>();
        Stack<TreeNode> st2 = new Stack<>();

        pushAllLeftNodes(st1,root);
        pushAllRightNodes(st2,root);

        int val1 = st1.peek().val;
        int val2 = st2.peek().val;

        while(val1<val2){

            int sum = val1+val2;

            if(sum==k) return true;
            else if(sum>k){
                //first we have to pop the right stack
                //if the right stack has a left nodes add the right nodes
                TreeNode rem2 = st2.peek();
                st2.pop();
                if(rem2.left!=null){
                    pushAllRightNodes(st2,rem2.left);
                }

            }else{
                TreeNode rem1 = st1.peek();
                st1.pop();
                if(rem1.right!=null){
                    pushAllLeftNodes(st1,rem1.right);
                }
            }

            if(!st1.isEmpty()) val1 = st1.peek().val;
            if(!st2.isEmpty()) val2 = st2.peek().val;
        }
        return false;
    }

    public void pushAllLeftNodes(Stack<TreeNode> st,TreeNode root){
        while(root!=null){
            st.push(root);
            root = root.left;
        }
    }

    public void pushAllRightNodes(Stack<TreeNode> st,TreeNode root){
        while(root!=null){
            st.push(root);
            root = root.right;
        }
    }
}