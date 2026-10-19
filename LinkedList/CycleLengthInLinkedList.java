/* Structure of Linked List Node
class Node {
    int data;
    Node next;

    Node(int x) {
        data = x;
        next = null;
    }
}*/

class Solution {
    public int lengthOfLoop(Node head) {
        // code here
        Node s = head;
        Node f = head;
        while(f.next!=null && f.next.next!=null){
            s = s.next;
            f = f.next.next;
            if(s==f){
                break;
            }
        }
        if(f.next==null || f.next.next==null) return 0;
        s = head;
        while(s!=f){
            s = s.next;
            f = f.next;
        }
        
        Node lps = s;
        int len  = 1;
        s = s.next;
        while(s!=lps){
            s = s.next;
            len++;
        }
        return len;
    }
}

// other wya

class Solution {
    public int lengthOfLoop(Node head) {
        if (head == null || head.next == null) return 0;

        Node slow = head;
        Node fast = head;

        // Step 1: Detect if a loop exists using Tortoise and Hare
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // Step 2: Loop detected, calculate its length
                int length = 1;
                Node current = slow.next;
                while (current != slow) {
                    length++;
                    current = current.next;
                }
                return length;
            }
        }

        // No loop found
        return 0;
    }
}