package BST;

import org.w3c.dom.Node;

public class CeilofBST {
    /*
     * Definition for Node
     * class Node {
     * int data;
     * Node left, right;
     * 
     * Node(int val) {
     * this.data = val;
     * left = right = null;
     * }
     * }
     */

    class Solution {
        int findCeil(Node root, int x) {
            // code here
            /*
             * First check if the current node is < or > the
             * x. if < x then try to find greater or eqaul ones.
             * the moment we find the greater one that is the best values.
             * try to check the left of tree that is < the current best
             * and >= x. if it is that is the current best.
             * if <x then seach throguth the right.
             */
            if (root == null)
                return -1;
            // If we found the exact match.
            if (root.data == x)
                return x;
            // if roots data is smaller than x
            // then ceil will always be there on the right.
            if (root.data < x) {
                return findCeil(root.right, x);
            }

            // if the root value is greater than the x
            // then root is the potential val. so we first check
            // the left and then if left ==-1 then we return the
            // root else we return the left.
            int res = findCeil(root.left, x);

            return res == -1 ? root.data : res;

        }
    }
}

// Iterative Approach
/*
 * Definition for Node
 * class Node {
 * int data;
 * Node left, right;
 * 
 * Node(int val) {
 * this.data = val;
 * left = right = null;
 * }
 * }
 */

class Solution {
    int findCeil(Node root, int x) {
        // code here
        int ans = -1;

        Node curr = root;

        while (curr != null) {
            if (curr.data == x) {
                return curr.data;
            }

            if (curr.data > x) {
                ans = curr.data;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        return ans;
    }
}