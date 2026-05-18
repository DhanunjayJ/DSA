class Solution {
    
    // Helper class to pass information up the tree
    static class BSTInfo {
        boolean isBST;
        int size;
        int min;
        int max;

        BSTInfo(boolean isBST, int size, int min, int max) {
            this.isBST = isBST;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }

    // Main tracking variable for the largest BST size found so far
    private int maxBSTSize = 0;

    public int largestBST(Node root) {
        maxBSTSize = 0;
        helper(root);
        return maxBSTSize;
    }

    private BSTInfo helper(Node root) {
        // Base case: An empty node is a valid BST of size 0
        if (root == null) {
            return new BSTInfo(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        // 1. Get information from Left and Right subtrees (Post-order)
        BSTInfo left = helper(root.left);
        BSTInfo right = helper(root.right);

        // 2. Validate if the current node forms a valid BST
        // Condition: Left is BST, Right is BST, and current data fits strictly between them
        if (left.isBST && right.isBST && root.data > left.max && root.data < right.min) {
            
            // Calculate total size of this new valid BST
            int currentSize = left.size + right.size + 1;
            
            // Update our global maximum
            maxBSTSize = Math.max(maxBSTSize, currentSize);

            // Calculate new min and max bounds for this valid subtree
            int currentMin = Math.min(root.data, left.min);
            int currentMax = Math.max(root.data, right.max);

            return new BSTInfo(true, currentSize, currentMin, currentMax);
        }

        // 3. If it fails the validation, it cannot be a BST. 
        // We pass 'false' up, and min/max don't matter anymore.
        return new BSTInfo(false, 0, 0, 0);
    }
}