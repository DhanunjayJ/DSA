/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    public Node connect(Node root) {
        /*
        The core Intitution.
        Intead of thinking in terms of Recursion first set the 
        left node next pointer with right one. 

        and check if the next pointer of the currnet is null.
        if not null then go that next and do the same. 

        this is utilizing the nodes next pointer that is already built before. 

        go the parent and set the child nodes. 
        
        */

        if(root==null) return null;
        Node curr = root;
        //untill we reach the leaf node.
        while(curr.left!=null){
            //create a temp node from where we traverse horizinatlly
            //untilll that next pointer becoems null;
            Node temp = curr;
            while(temp!=null){
                //set the left to the right.
                temp.left.next = temp.right;
                //if next is not null. we could easily set
                // the right pointer next. based 
                //on the parent pointer.
                if(temp.next!=null){
                    temp.right.next = temp.next.left;
                }
                //go to the next parent that is adjacnet.
                //set child nodes. 
                temp = temp.next;
            }
            curr = curr.left;
        }
        return root;
    }
}