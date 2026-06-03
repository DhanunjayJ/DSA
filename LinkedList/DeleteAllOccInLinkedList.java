/* Structure of Doubly Linked List
class Node {
    int data;
    Node next;
    Node prev;

    Node(int x) {
        data = x;
        next = null;
        prev = null;
    }
}
*/
class Solution {
    static Node deleteAllOccurOfX(Node head, int x) {
        // code here
        Node curr = head;
        
        while(curr!=null){
            
            if(curr.data==x){
                
                if(curr==head){
                    head = head.next;
                }
                
                Node prev = curr.prev;
                Node next = curr.next;
                
                if(prev!=null){
                    prev.next = next;
                }
                
                if(next!=null){
                    next.prev = prev;
                }
            }
            
            curr = curr.next;
        }
        
        return head;
    }
}