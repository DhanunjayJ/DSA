//recursive approach without reversing. 


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
    //maintina a global prev pointer to keep building the answer
    // recursively and add to the prev list. 
    ListNode prev = null;
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //count how many nodes are present in the l1 and l2.
        int n1 = 1;
        int n2 = 1;

        ListNode curr1 = l1;
        ListNode curr2 = l2;

        while(curr1!=null){
            curr1 = curr1.next;
            n1++;
        }

        while(curr2!=null){
            curr2 = curr2.next;
            n2++;
        }

        //get the diff of the both the vals.
        int diff = Math.abs(n1-n2);

        //create an empty zero linkedlist of size diff and attach that
        //to the shorter linkedlist. 
        int diff1 = diff;
        ListNode ad = null;
        ListNode curr = ad;
        while(diff1>0){
            ListNode n = new ListNode(0);
            if(ad==null){
                ad=n;
                curr=n;
            }
            curr.next = n;
            curr = curr.next;
            diff1--;
        }
        // attach it to the smaller linkedlist. 
        if(diff>0){
            if(n1<n2){
                curr.next = l1;
                l1 = ad;
            }else if(n2<n1){
                curr.next = l2;
                l2 = ad;
            }
        }
        //call the add funtion recursively. 
        int carry = add(l1,l2);

        //if there is a carry add it to first.
        if(carry==1){
            ListNode k1 = new ListNode(1);
            k1.next = prev;
            prev = k1;
        }
        return prev;
    }

    //this fucntion return if there is carry int he next function.
    public int add(ListNode l1,ListNode l2){
        //if both are null return 0
        if(l1==null && l2==null) return 0;
        //get the carry.
        int carry = add(l1.next,l2.next);
        //add it to the current sum. 
        int sum = l1.val+l2.val+carry;
        //create a new node. 
        ListNode n = new ListNode(sum%10);
        //link it to the previous node. 
        n.next = prev;
        prev = n;
        //return the carry to next node. 
        return sum/10;
    }
}


/// Stack Based Approach
/// 
/// import java.util.Stack;

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        while (l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        }

        while (l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }

        ListNode head = null;
        int carry = 0;

        while (!s1.isEmpty() || !s2.isEmpty() || carry > 0) {
            int sum = carry;
            if (!s1.isEmpty()) sum += s1.pop();
            if (!s2.isEmpty()) sum += s2.pop();

            carry = sum / 10;
            
            // Insert at the beginning of the result list
            ListNode newNode = new ListNode(sum % 10);
            newNode.next = head;
            head = newNode;
        }

        return head;
    }
}


/// by reversing
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Step 1: Reverse both linked lists
        l1 = reverse(l1);
        l2 = reverse(l2);

        ListNode dummyHead = new ListNode(0);
        ListNode current = dummyHead;
        int carry = 0;

        // Step 2: Add the numbers digit by digit
        ListNode p1 = l1;
        ListNode p2 = l2;
        while (p1 != null || p2 != null || carry > 0) {
            int sum = carry;
            if (p1 != null) {
                sum += p1.val;
                p1 = p1.next;
            }
            if (p2 != null) {
                sum += p2.val;
                p2 = p2.next;
            }

            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }

        // Step 3: Reverse the result list to get it back into most-significant-first order
        ListNode result = reverse(dummyHead.next);

        // Optional (Good Practice): Restore input lists to their original order
        // l1 = reverse(l1);
        // l2 = reverse(l2);

        return result;
    }

    // Helper function to reverse a linked list
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }
} 