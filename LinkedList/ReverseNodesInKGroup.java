class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        ListNode curr = head;
        ListNode nhead = null;
        ListNode prevTail = null; // Tracks the end of the last reversed group
        
        while (curr != null) {
            ListNode start = curr;
            ListNode end = curr;
            
            // Check if there are at least k nodes remaining
            boolean hasK = true;
            for (int i = 0; i < k - 1; i++) {
                if (end.next != null) {
                    end = end.next;
                } else {
                    hasK = false;
                    break;
                }
            }
            
            // If fewer than k nodes remain, attach them as-is and break
            if (!hasK) {
                if (prevTail != null) {
                    prevTail.next = start;
                }
                break;
            }
            
            ListNode nextGroupStart = end.next;
            end.next = null; // Cut off the group for reversal
            
            reverse(start); // Reverses in-place (start becomes the tail)
            
            if (nhead == null) {
                nhead = end; // The k-th node becomes the new head of the whole list
            }
            if (prevTail != null) {
                prevTail.next = end; // Connect previous group's tail to the new group's head
            }
            
            prevTail = start; // The old start is now the tail of this reversed group
            curr = nextGroupStart;
        }
        
        return nhead == null ? head : nhead;
    }
    
    public ListNode reverse(ListNode curr) {
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}


//other ways 

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
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;

        // Dummy node to simplify handling of the new head
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            // 1. Find the k-th node from the current group start
            ListNode kth = getKthNode(groupPrev, k);
            if (kth == null) {
                break; // Less than k nodes left, leave them as is
            }
            
            ListNode groupNext = kth.next;

            // 2. Reverse the k-group
            ListNode prev = kth.next;
            ListNode curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode nextTemp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
            }

            // 3. Connect the reversed group with the previous part
            ListNode tailOfReversedGroup = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = tailOfReversedGroup;
        }

        return dummy.next;
    }

    // Helper method to find the k-th node ahead of curr
    private ListNode getKthNode(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }
}