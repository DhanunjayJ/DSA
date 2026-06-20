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
    public ListNode oddEvenList(ListNode head) {
        if(head==null || head.next==null || head.next.next==null) return head;

        ListNode head2 = head.next;

        ListNode curr = head;
        ListNode curr2 = head2;

        while(curr2!=null && curr2.next!=null){
            ListNode next1 = curr.next.next;
            ListNode next2 = curr2.next.next;
            curr.next = next1;
            curr2.next = next2;
            curr = next1;
            curr2 = next2;
        }

        curr.next = head2;
        return head;
    }
}