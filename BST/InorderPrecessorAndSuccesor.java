package BST;

import java.util.ArrayList;

import org.w3c.dom.Node;

public class InorderPrecessorAndSuccesor {
    /*
class Node {
    int data;
    Node left, right;
    Node(int x) {
        data = x;
        left = right = null;
    }
}
*/

class Solution {
    public ArrayList<Node> findPreSuc(Node root, int key) {
        // code here
        
        Node predecessor = null;
        Node successor = null;
        
        while(root!=null){
            
            if(root.data>key){
                
                successor = root;
                root = root.left;
                
            }else if(root.data<key){
                
                predecessor = root;
                root = root.right;
                
            }else{
                //first let's find the predecessor 
                Node pf = root;
                if(pf.left!=null){
                    pf = pf.left;
                    while(pf!=null){
                        //go as right as posible
                        predecessor = pf;
                        pf = pf.right;
                    }
                }
                
                Node sf = root;
                //successor
                if(sf.right!=null){
                    sf = sf.right;
                    // go as left as possbile
                    while(sf!=null){
                        successor = sf;
                        sf = sf.left;
                    }
                }
                
                break;
            }
        }
        ArrayList<Node> ans = new ArrayList<>();
        ans.add(predecessor);
        ans.add(successor);
        return ans;
        
    }
}
}
