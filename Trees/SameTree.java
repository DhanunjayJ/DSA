class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        //if both are null then return true;
        if(p==null && q==null) return true;
        //if one is no null and other is not, or if both are not null
        // and if the values are not same. 
        if(p==null || q==null || p.val!=q.val) return false;
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }
}