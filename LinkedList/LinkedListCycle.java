/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if(head==null) return false;
        ListNode s = head;
        ListNode f = head;
        while(f.next!=null && f.next.next!=null){
            f = f.next.next;
            s = s.next;
            if(s==f){
                break;
            }
        }
        if(f.next==null || f.next.next==null) return false;
        return true;
    }
}