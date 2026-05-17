package BST;

import java.util.ArrayDeque;
import java.util.Deque;

public class BSTIterator {
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
class BSTIterator1 {
    /*
    To get the complexity of the O(H) s.c and t.c is o(1) avg. 

    First we take a stack where we will store all the left nodes. 

    wehn poping it we check if it has right. 
    if it is then add right and go till untill the left is not null. 

    once we add all the nodes then return the popped element. 

    do this untill not element left on the stack.
    */

    Deque<TreeNode> st = new ArrayDeque<>();

    public BSTIterator1(TreeNode root) {
        st = new ArrayDeque<>();
        st.push(root);
        while(root.left!=null){
            st.push(root.left);
            root = root.left;
        }
    }
    
    public int next() {
        // first pop the element.
        TreeNode rem = st.pop();
        TreeNode curr = rem;
        //after poping check if the right!=null
        //if not equal to null. go to right and
        //add all the left nodes. 
        if(curr.right!=null){
            st.push(curr.right);
            curr = curr.right;
            while(curr.left!=null){
                st.push(curr.left);
                curr = curr.left;
            }
        }
        return rem.val;
    }
    
    public boolean hasNext() {
        return !st.isEmpty();
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */


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
class BSTIterator2 {
    /*
    To get the complexity of the O(H) s.c and t.c is o(1) avg. 

    First we take a stack where we will store all the left nodes. 

    wehn poping it we check if it has right. 
    if it is then add right and go till untill the left is not null. 

    once we add all the nodes then return the popped element. 

    do this untill not element left on the stack.
    */

    Deque<TreeNode> st;

    public BSTIterator2(TreeNode root) {
        st = new ArrayDeque<>();
        pushAllLeft(root);
    }
    
    public int next() {
        TreeNode rem = st.pop();

        if(rem.right!=null)
        pushAllLeft(rem.right);

        return rem.val;
    }

    public void pushAllLeft(TreeNode root){
        while(root!=null){
            st.push(root);
            root = root.left;
        }
    }
    
    public boolean hasNext() {
        return !st.isEmpty();
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */
}
