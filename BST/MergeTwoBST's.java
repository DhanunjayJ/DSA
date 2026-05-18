package BST;

public class MergeTwoBST's {
    /*
class Node {
    int data;
    Node left, right;

    public Node(int val)
    {
        data = val;
        left = right = null;
    }
}
*/

class Solution {
    public ArrayList<Integer> merge(Node root1, Node root2) {
        // code here
        
        /*
        WE maintain two stacks same as merge iterator 
        while popping we add the one that is smaller 
        
        By Maintaining always the smallest vals of the both
        the trees we can compare and push the vals in order. 
        
        we only pop when the element is smaller and add the 
        next smaller by chekcing the right most.
        */
        
        Stack <Node> st1 = new Stack<>();
        Stack <Node> st2 = new Stack<>();
        
        pushAllLeft(root1,st1);
        pushAllLeft(root2,st2);
        
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        
        while(!st1.isEmpty() && !st2.isEmpty()){
            
            Node rem1 = st1.peek();
            Node rem2 = st2.peek();
            
            if(rem1.data<=rem2.data){
                st1.pop();
                ans.add(rem1.data);
                if(rem1.right!=null){
                    pushAllLeft(rem1.right,st1);
                }
            }else{
                st2.pop();
                ans.add(rem2.data);
                if(rem2.right!=null){
                    pushAllLeft(rem2.right,st2);
                }
            }
            
        }
        
        while(!st1.isEmpty()){
            Node rem1 = st1.pop();
            if(rem1.right!=null){
                pushAllLeft(rem1.right,st1);
            }
            ans.add(rem1.data);
        }
        
        while(!st2.isEmpty()){
            Node rem2 = st2.pop();
            if(rem2.right!=null){
                pushAllLeft(rem2.right,st2);
            }
            ans.add(rem2.data);
        }
        
        return ans;
    }
    
    public void pushAllLeft(Node root,Stack<Node> st){
        while(root!=null){
            st.push(root);
            root = root.left;
        }
    }
}


//Refatored code

import java.util.ArrayList;
import java.util.Stack;

class Solution {
    public ArrayList<Integer> merge(Node root1, Node root2) {
        Stack<Node> st1 = new Stack<>();
        Stack<Node> st2 = new Stack<>();
        
        pushAllLeft(root1, st1);
        pushAllLeft(root2, st2);
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        // Loop as long as AT LEAST ONE tree has elements remaining
        while (!st1.isEmpty() || !st2.isEmpty()) {
            
            // If st2 is empty, we must pick from st1.
            // Otherwise, if both have elements, pick from st1 only if it's smaller.
            if (st2.isEmpty() || (!st1.isEmpty() && st1.peek().data <= st2.peek().data)) {
                Node rem1 = st1.pop();
                ans.add(rem1.data);
                pushAllLeft(rem1.right, st1);
            } 
            // Otherwise, st1 is empty or st2 has the smaller element
            else {
                Node rem2 = st2.pop();
                ans.add(rem2.data);
                pushAllLeft(rem2.right, st2);
            }
        }
        
        return ans;
    }
    
    private void pushAllLeft(Node root, Stack<Node> st) {
        while (root != null) {
            st.push(root);
            root = root.left;
        }
    }
}
}
