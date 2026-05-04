import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class TreeBoundaryTravesal {
    /*
class Node {
    int data;
    Node left, right;

    public Node(int d) {
        data = d;
        left = right = null;
    }
}
*/

class Solution {
    ArrayList<Integer> boundaryTraversal(Node root) {
      ArrayList<Integer> ans = new ArrayList<>();
      //do pre order to add all he nodes that are at left.
      if(!isLeaf(root)) ans.add(root.data);
      
      if(root.left!=null)
      preorder(root.left,ans);
      
      inorderLeaf(root,ans);
      
      if(root.right!=null)
      postOrder(root.right,ans);
      
      return ans;
    }
    public void preorder(Node root,ArrayList<Integer> ans){
        if(isLeaf(root)){
            return;
        }
        
        ans.add(root.data);
        
        if(root.left!=null){
            preorder(root.left,ans);
        }else{
            preorder(root.right,ans);
        }
        
    }
    
    public void inorderLeaf(Node root,ArrayList<Integer> ans){
        
        if(isLeaf(root)){
            ans.add(root.data);
            return;
        }
        
        if(root.left!=null){
            inorderLeaf(root.left,ans);
        }
        
        if(root.right!=null){
            inorderLeaf(root.right,ans);
        }
    }
    
    public void postOrder(Node root,ArrayList<Integer> ans){
        
        Deque<Node> st = new ArrayDeque<>();
        Node curr = root;
        
        while(curr!=null){
            if(isLeaf(curr)) break;
            st.push(curr);
            if(curr.right!=null){
                curr = curr.right;
            }else{
                curr = curr.left;
            }
        }
        
        while(!st.isEmpty()){
            ans.add(st.pop().data);
        }
    }
    
    
    public boolean isLeaf(Node root){
        
        return (root.left==null) && (root.right==null);
        
    }
}

/*
class Node {
    int data;
    Node left, right;

    public Node(int d) {
        data = d;
        left = right = null;
    }
}
*/

class Solution {
    ArrayList<Integer> boundaryTraversal(Node root) {
        
      ArrayList<Integer> ans = new ArrayList<>();
      
      if(root==null) return ans;
      
      ans.add(root.data);
      //do pre order to add all he nodes that are at left.
      if(isLeaf(root)) return ans;
      
      if(root.left!=null)
      preorder(root.left,ans);
      
      inorderLeaf(root,ans);
      
      if(root.right!=null)
      postOrder(root.right,ans);
      
      return ans;
    }
    public void preorder(Node root,ArrayList<Integer> ans){
        while(root!=null){
            if(!isLeaf(root)) ans.add(root.data);
            if(root.left!=null) root = root.left;
            else root = root.right;
        }
        
    }
    
    public void inorderLeaf(Node root,ArrayList<Integer> ans){
        
        if(isLeaf(root)){
            ans.add(root.data);
            return;
        }
        
        if(root.left!=null){
            inorderLeaf(root.left,ans);
        }
        
        if(root.right!=null){
            inorderLeaf(root.right,ans);
        }
    }
    
    public void postOrder(Node root,ArrayList<Integer> ans){
        if(root==null) return;
        if(isLeaf(root)){
            return;
        }
        
        if(root.right!=null)
        postOrder(root.right,ans);
        else postOrder(root.left,ans);
        
        ans.add(root.data);
    }
    
    
    public boolean isLeaf(Node root){
        
        return (root.left==null) && (root.right==null);
        
    }
}

}
