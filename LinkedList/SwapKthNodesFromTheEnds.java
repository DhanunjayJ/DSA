/* Linked List Node Structure
class Node {
    int data;
    Node next;

    Node(int x) {
        data = x;
        next = null;
    }
}
*/
class Solution {
    public Node swapKth(Node head, int k) {
        // code here
        int n = 1;
        //first count how many nodes are present in the list. 
        Node curr = head;
        while(curr!=null){
            curr = curr.next;
            n++;
        }
        //if k > n then it is not possible to swap.
        if(k>n) return head;
        /*
        we have to swap two values one from the front and 
        one from the back. so while iterating from the front 
        check i == k put in the one node and
        n-i==k put in the two pointer. 
        n-i gives the index from the back. 
        so after that just swap the values
        */
        Node one = null;
        Node two = null;
        curr = head;
        int i = 1;
        while(curr!=null){
            if(i==k){
                one = curr;
            }
            if(n-i==k){
                two = curr;
            }
            curr = curr.next;
            i++;
        }
        int temp = one.data;
        one.data = two.data;
        two.data = temp;
        return head;
    }
}
