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

//recursive way
class Solution {
    public ListNode reverseList(ListNode head) {
        //the base case
        //if the list is empty or only one node it was already reversed;
        // you just need to return it.
        if(head==null || head.next==null) return head;

        ListNode nhead = reverseList(head.next);
        // assuming that all the nodes after the 1 are reversed.
        // 1 -> 2 <- 3 <- 4 <- 5
        //1.next.next -> 2.next;
        //make it equal to head;
        head.next.next = head;
        //making the 1 -> null
        head.next = null;
        return nhead;
    }
}

//iterative way

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
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next==null)return head;
        ListNode prev = null;
        ListNode curr = head;
        while(curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}