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
    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        if(k==0) return null;
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> Integer.compare(a.val,b.val));
        for(int i=0;i<k;i++){
            if(lists[i]!=null){
                pq.offer(lists[i]);
            }
        }
        // no nodes in the pq then return null
        ListNode head = new ListNode(0);
        ListNode curr1 = head;
        if(pq.size()==0) return null;
        while(!pq.isEmpty()){
            ListNode curr = pq.poll();
            if(curr.next!=null){
                pq.add(curr.next);
            }
            curr.next = null;
            curr1.next = curr;
            curr1 = curr1.next;
        }
        return head.next;
    }
}