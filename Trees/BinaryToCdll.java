public class BinaryToCdll {
    /*
class Node {
    int data;
    Node left, right;

    Node(int val) {
        data = val;
        left = null;
        right = null;
    }
}
*/

class Solution {
    Node bTreeToCList(Node root) {
        // code here
        if(root==null) return null;
        
        Node left = bTreeToCList(root.left);
        Node right = bTreeToCList(root.right);
        
        root.left = root.right = root;
        
        Node result = concatnate(left,root);
        result = concatnate(result,right);
        
        return result;
    }
    
    Node concatnate(Node h1,Node h2){
        
        if(h1==null) return h2;
        if(h2==null) return h1;
        
        Node t1 = h1.left;
        Node t2 = h2.left;
        
        t1.right = h2;
        h2.left = t1;
        t2.right = h1;
        h1.left = t2;
        
        return h1;
    }
}

}
