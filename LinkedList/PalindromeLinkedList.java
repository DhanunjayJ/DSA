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
    public boolean isPalindrome(ListNode head) {
        ListNode curr = head;
        ListNode s = curr;
        ListNode f = curr;
        while(f.next!=null && f.next.next!=null){
            s = s.next;
            f = f.next.next;
        }
        //the start of the new list is from the 
        //s.next;
        ListNode newStart = s.next;
        s.next = null;
        ListNode rev = reverse(newStart);
        curr = head;
        ListNode curr2 = rev;
        while(curr2!=null){
            if(curr.val!=curr2.val) return false;
            curr = curr.next;
            curr2 = curr2.next;
        }
        return true;
    }
    public ListNode reverse(ListNode curr){
        ListNode prev = null;
        while(curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}

