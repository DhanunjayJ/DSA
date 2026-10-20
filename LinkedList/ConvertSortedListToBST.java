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
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */

// nlogn approach - finding the mid multiple times
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null)
            return null;
        return buildBst(head, null);
    }

    public TreeNode buildBst(ListNode head, ListNode tail) {
        if (head == tail)
            return null;
        ListNode s = head;
        ListNode f = head;
        while (f != tail && f.next != tail) {
            f = f.next.next;
            s = s.next;
        }
        TreeNode node = new TreeNode(s.val);
        node.left = buildBst(head, s);
        node.right = buildBst(s.next, tail);
        return node;
    }
}

// logn approach

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
    ListNode curr;

    public TreeNode sortedListToBST(ListNode head) {
        int n = 0;
        ListNode temp = head;
        while (temp != null) {
            n++;
            temp = temp.next;
        }
        this.curr = head;
        return buildBst(0, n - 1);
    }

    public TreeNode buildBst(int left, int right) {
        if (left > right)
            return null;
        int mid = left + (right - left) / 2;
        TreeNode leftNode = buildBst(left, mid - 1);
        TreeNode node = new TreeNode(curr.val);
        curr = curr.next;
        TreeNode rightNode = buildBst(mid + 1, right);
        node.left = leftNode;
        node.right = rightNode;
        return node;
    }
}