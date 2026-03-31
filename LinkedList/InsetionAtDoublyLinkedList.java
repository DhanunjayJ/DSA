//https://www.geeksforgeeks.org/problems/insert-a-node-in-doubly-linked-list/1

/*
class Node
{
    int data;
    Node next;
    Node prev;
    Node(int data)
    {
        this.data = data;
        next = prev = null;
    }
}
*/

class Solution {
    Node insertAtPos(Node head, int p, int x) {
        // code here
        if(head==null) return null;
        
        Node curr = head;
        
        Node node = new Node(x);
        
        // go til the position one before it.
        
        for(int i=0;i<p;i++){
            curr = curr.next;
        }
        
        // add the node
        //first get the next node
        //second get the prev node
        
        Node next = curr.next;
        Node prev = curr;
        
        // once we get it. the based on the values that 
        // are there we set them.
        
        if(next!=null && prev!=null){
        prev.next = node;
        node.next = next;
        next.prev = node;
        node.prev = prev;
        }else if(next==null){
            prev.next = node;
            node.prev = prev;
        }else if(prev==null){
            node.next = head;
            head.prev = node;
            head = node;
        }
        
        return head;
    }
}