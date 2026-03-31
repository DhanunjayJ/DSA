/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        // Trying to copy each of the pointer and make a new list 
        //using hashmap we can store the randome pointer but how can we acutally get the same
        //randome pointer int he newly created Linkedlist.

        // so to avoid that confusion what we do is.we create that new list just after each
        //node of the linkedlist. so that the radome pointer points ill always be just next
        // to the randome pointer of the current one. 

        //so lets first create a new node for each node in the linkedlist just adjacent to it. 
        Node curr = head;

        if(head==null) return null;

        while(curr!=null){
            Node node = new Node(curr.val);
            Node next = curr.next;
            curr.next = node;
            node.next = next;
            curr = next;
        }

        Node head2 = head.next;

        // copy the randome pointer
        curr = head;

        while(curr!=null){
            Node random = curr.random;
            if(random!=null){
                curr.next.random = random.next;
            }
            curr = curr.next.next;
        }

        //sepearte the two lists.

        curr = head;

        Node curr2 = head2;

        while(curr!=null){
            curr.next = curr.next.next;
            if(curr2.next!=null) {
                // the edge case for the last element. 
                curr2.next = curr2.next.next;
            }
            curr = curr.next;
            curr2 = curr2.next;
        }

        return head2;
    }
}