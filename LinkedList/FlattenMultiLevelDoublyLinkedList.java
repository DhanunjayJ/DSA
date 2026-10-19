/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
    public Node flatten(Node head) {
        flattenH(head);
        return head;
    }
    //this function will return the tail of each ending linkedlist. 
    public Node flattenH(Node head) {
        Node curr = head;
        Node prev = null;
        //need to iterate untill curr!=null curr.next will not work for single
        //not lists. 
        // so we need to maintain two pointers prev and curr. 
        while (curr != null) {
            //if child is present. 
            if (curr.child != null) {
                //store the next pointer in the next var
                Node next = curr.next;
                //flattern the child.
                Node nextTail = flattenH(curr.child);
                //chagne the child from child to next. 
                curr.next = curr.child;
                //make the child to point the prev to curr. 
                curr.next.prev = curr;
                //make the child null.
                curr.child = null;
                //if the next val is not null.
                //make the tail to point to next.
                //and next to point to tail. 
                if (next != null) {
                    nextTail.next = next;
                    nextTail.next.prev = nextTail;
                }
            }
            //make the curr to next and prev to curr. 
            prev = curr;
            curr = curr.next;
        }
        //return the prev node not the curr!!
        return prev;
    }
}