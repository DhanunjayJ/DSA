/*
class Node{
    int data;
    Node next;

    Node(int x){
        data = x;
        next = null;
    }
}
*/

class Solution {
    public Node addOne(Node head) {
        // code here.
        //find the right most number that is not nine
        //then add one to it.
        //make all the numbers that are after it that are 9
        //to zero.
        
        Node rightMostNonNine = null;
        
        Node curr = head;
        
        while(curr!=null){
            if(curr.data!=9){
                rightMostNonNine = curr;
            }
            curr = curr.next;
        }
        
        //if rightMostNonNine == null 
        //meaning that all elements in the linkedlist are 999
        // 9, 99,99 edge cases 
        if(rightMostNonNine==null){
            Node node = new Node(1);
            node.next = head;
            
            curr = head;
            while(curr!=null){
            curr.data = 0;
            curr = curr.next;
            }
        
            return node;
        }
        
        rightMostNonNine.data++;
        
        curr = rightMostNonNine.next;
        
        while(curr!=null){
            curr.data = 0;
            curr = curr.next;
        }
        
        return head;
    }
}