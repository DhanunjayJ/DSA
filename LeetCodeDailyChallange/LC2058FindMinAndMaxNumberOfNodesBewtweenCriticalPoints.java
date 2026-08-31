public class LC2058FindMinAndMaxNumberOfNodesBewtweenCriticalPoints {
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
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        ListNode prev = null;
        ListNode curr = null;
        ListNode next = null;

        int idx = 2;

        int firstIdx = -1;
        int prevIdx = -1;

        int maxDistance = -1;
        int minDistance = Integer.MAX_VALUE;

        prev = head;
        curr = prev.next;
        if (curr.next == null)
            return new int[] { -1, -1 };
        next = curr.next;

        //check if the value is local min or max
        if (isLocalMinMax(prev, curr, next)) {
            firstIdx = 2;
            prevIdx = 2;
        }

        while (next.next != null) {
            prev = curr;
            curr = next;
            next = next.next;
            idx++;
            if (isLocalMinMax(prev, curr, next)) {
                //if this is the first index?
                if (firstIdx == -1) {
                    firstIdx = idx;
                    prevIdx = idx;
                } else {
                    minDistance = Math.min(idx - prevIdx, minDistance);
                    maxDistance = Math.max(maxDistance, idx - firstIdx);
                    prevIdx = idx;
                }
            }
        }
        //no crital points or only one critial point
        if (minDistance == Integer.MAX_VALUE && maxDistance == -1)
            return new int[] { -1, -1 };
        return new int[] { minDistance, maxDistance };

    }

    public boolean isLocalMinMax(ListNode prev, ListNode curr, ListNode next) {
        return (curr.val > prev.val && curr.val > next.val) || (curr.val < prev.val && curr.val < next.val);
    }
}
}
