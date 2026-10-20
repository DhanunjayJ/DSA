class Solution {
	static Node subLinkedList(Node head1, Node head2) {
		// code here
		// revome the leading zeros.
		while (head1 != null && head1.data == 0)
			head1 = head1.next;
		while (head2 != null && head2.data == 0)
			head2 = head2.next;
		
		//handle edge cases
		if (head1 == null && head2 == null)
			return new Node(0);
		if (head1 == null)
			return head2;
		if (head2 == null)
			return head1;
		
		int n1 = getLength(head1);
		int n2 = getLength(head2);
		
		boolean swapNeeded = false;
		
		//if n1 is less then swap needd
		if (n1<n2) {
			swapNeeded = true;
		} else if(n1==n2){
			Node t1 = head1;
			Node t2 = head2;
			//if not, check each node value 
			while (t1 != null) {
				if (t1.data<t2.data) {
					swapNeeded = true;
					break;
				} else if (t1.data>t2.data) {
					break;
				}
				t1 = t1.next;
				t2 = t2.next;
			}
		}
		
		if (swapNeeded) {
			Node temp = head2;
			head2 = head1;
			head1 = temp;
		}
		
		Node rev1 = reverse(head1);
		Node rev2 = reverse(head2);
		
		Node curr1 = rev1;
		Node curr2 = rev2;
		
		Node ansH = new Node(0);
		Node curr3 = ansH;
		
		while (curr2 != null) {
			
			int d1 = curr1.data;
			int d2 = curr2.data;
			
			if (d1<d2) {
				int carry = getCarry(curr1.next);
				if (carry != 0) {
					curr1.data = curr1.data + 10;
				}
				d1 = curr1.data;
			}
			
			int diff = d1 - d2;
			
			Node node = new Node(diff);
			
			curr3.next = node;
			curr3 = curr3.next;
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		
		// if curr1 is not empty then point to the next rem nodes.
		if (curr1 != null)
			curr3.next = curr1;
		
		// remvoing the dummy zero.
		ansH = ansH.next;
		
		// reverse the total ans list
		
		ansH = reverse(ansH);
		
		// remove any trailing zeros.
		while (ansH != null && ansH.data == 0) {
			ansH = ansH.next;
		}
		
		return ansH==null ? new Node(0) : ansH;
	}
	
	static Node reverse(Node head) {
		if (head.next == null)
			return head;
		Node prev = null;
		Node curr = head;
		while (curr != null) {
			Node next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}
	
	static int getLength(Node node) {
		int n = 0;
		while (node != null) {
			n++;
			node = node.next;
		}
		return n;
	}
	
	static int getCarry(Node node) {
		if (node == null)
			return 0;
		if (node.data <= 0) {
			int carry = getCarry(node.next);
			if (carry != 0) {
				node.data = 10 + node.data;
			}
		}
		node.data = node.data - 1;
		return 10;
	}
}
