public class MaximumXorWithAnElementFromAnArray {
    class Solution {

    class Node {
        Node one = null;
        Node zero = null;
        //we add minVal variable to the Node so we can easily check if the val <= m
        int minVal = Integer.MAX_VALUE;
    }

    public final Node root = new Node();

    public void insert(int num) {
        Node curr = root;
        for (int i = 31; i >= 0; i--) {
            int bit = (num & (1 << i)) != 0 ? 1 : 0;
            //update the minVal when inserting
            curr.minVal = Math.min(curr.minVal, num);
            if (bit == 1) {
                if (curr.one == null) {
                    curr.one = new Node();
                }
                curr = curr.one;
            } else {
                if (curr.zero == null) {
                    curr.zero = new Node();
                }
                curr = curr.zero;
            }
        }
        //at last bit update the minVal
        curr.minVal = Math.min(curr.minVal,num);
    }

    public int[] maximizeXor(int[] nums, int[][] queries) {

        int n = queries.length;

        for (int num : nums) {
            insert(num);
        }

        int[] ans = new int[n];

        for (int k=0;k<n;k++) {

            int x = queries[k][0];
            int m = queries[k][1];

            //if the root's minVal itself is >m then we dont value 
            //<=m in the tree so we make the ans[k] = -1;
            if(root.minVal > m){
                ans[k] = -1;
                continue;
            }

            Node curr = root;
            int num = 0;

            for (int i = 31; i >= 0; i--) {

                int bit = (x & (1 << i)) != 0 ? 1 : 0;

                int wantBit = 1 - bit;

                if (wantBit == 1) {
                    //if checkif the child we are going to is having the value <=m
                    if (curr.one != null && curr.one.minVal<=m) {
                        curr = curr.one;
                        num |= (1 << i);
                    } else {
                        curr = curr.zero;
                    }
                } else {
                    //check if the node is having value <= m
                    if (curr.zero != null && curr.zero.minVal<=m) {
                        curr = curr.zero;
                    } else {
                        num |= (1 << i);
                        curr = curr.one;
                    }
                }
            }

            ans[k] = num^x;
        }
        return ans;
    }
}
}
