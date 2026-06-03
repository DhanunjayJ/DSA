/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteMiddle(ListNode head) {
        if(head.next==null) return null;
        //we make the fast and slow at one pointer differnace. 
        ListNode slow = head;
        ListNode fast = head.next;

        while(fast.next!=null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode d = slow.next;

        //if the delete node is not null then change the pointer.
        if(d!=null)
        slow.next = d.next;

        return head;
    }
}