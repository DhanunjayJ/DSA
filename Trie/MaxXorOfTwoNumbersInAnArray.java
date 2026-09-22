class Node {
    Node zero = null;
    Node one = null;
}

class Solution {

    public Node root;

    public void insert(int num) {
        Node curr = root;
        for (int i = 31; i >= 0; i--) {
            if ((num & (1 << i)) != 0) {
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
    }

    public int findMaximumXOR(int[] nums) {
        root = new Node();
        for (int num : nums)
            insert(num);
        int maxXor = 0;
        for (int num : nums) {
            Node curr = root;
            int maxNum = 0;
            for (int i = 31; i >= 0; i--) {
                int bit = (num & (1 << i)) != 0 ? 1 : 0;
                int wantBit = 1 - bit;
                if (wantBit == 0) {
                    if (curr.zero != null) {
                        curr = curr.zero;
                    } else {
                        curr = curr.one;
                        maxNum |= (1 << i);
                    }
                } else {
                    if (curr.one != null) {
                        curr = curr.one;
                        maxNum |= (1 << i);
                    } else {
                        curr = curr.zero;
                    }
                }
            }
            maxXor = Math.max(maxXor, maxNum ^ num);
        }
        return maxXor;
    }
}