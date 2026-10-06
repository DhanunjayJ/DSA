/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isSame(TreeNode root,TreeNode subRoot){
        if(root==null && subRoot==null) return true;
        if(root==null || subRoot==null || root.val!=subRoot.val) return false;
        return isSame(root.left,subRoot.left) && isSame(root.right,subRoot.right);
    }
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if(root==null) return false;
        if(isSame(root,subRoot)) return true;
        return isSubtree(root.left,subRoot) || isSubtree(root.right,subRoot);
    }
}

//using preorder and string mathching algorithms

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    //using kmp algo
    public boolean isSubString(String pat,String text){
        String lp = pat+"$"+text;
        int n = lp.length();
        int [] lps = new int[n];
        int len = 0;
        for(int i=1;i<n;i++){
            while(len>0 && lp.charAt(i)!=lp.charAt(len)){
                len = lps[len-1];
            }
            if(lp.charAt(i)==lp.charAt(len)){
                len++;
            }
            lps[i] = len;
            if(lps[i]==pat.length()) return true;
        }
        return false;
    }
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        StringBuilder bigString = new StringBuilder();
        StringBuilder smString = new StringBuilder();
        //preorder and post order produce the unique stirng over the inorder
        preorder(root,bigString);
        preorder(subRoot,smString);
        return isSubString(smString.toString(),bigString.toString());
    }
    public void preorder(TreeNode root,StringBuilder st){
        if(root==null){
            st.append(",#");
            return;
        }
        //need to add delimiters to for values with mutliple digits won't get concated with others
        st.append(',').append(root.val);
        preorder(root.left,st);
        preorder(root.right,st);
    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    //using z algo
    public boolean isSubString(String pat,String text){
        String lp = pat+"$"+text;
        int n = lp.length();
        int [] z = new int[n];
        int len = 0;
        int l = 0;
        int r = 0;
        for(int i=1;i<n;i++){
            if(i<=r){
                z[i] = Math.min(r-i+1,z[i-l]);
            }
            while(z[i]+i<n && lp.charAt(z[i])==lp.charAt(z[i]+i)){
                z[i]++;
            }
            if(i+z[i]-1>r){
                l = i;
                r = i+z[i]-1;
            }
            if(z[i]==pat.length()) return true;
        }
        return false;
    }
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        StringBuilder bigString = new StringBuilder();
        StringBuilder smString = new StringBuilder();
        //preorder and post order produce the unique stirng over the inorder
        preorder(root,bigString);
        preorder(subRoot,smString);
        return isSubString(smString.toString(),bigString.toString());
    }
    public void preorder(TreeNode root,StringBuilder st){
        if(root==null){
            st.append(",#");
            return;
        }
        //need to add delimiters to for values with mutliple digits won't get concated with others
        st.append(',').append(root.val);
        preorder(root.left,st);
        preorder(root.right,st);
    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    //using Hash Function to calculate stirng matching
    public boolean isSubString(String pat,String text){
        int n = pat.length();
        int m = text.length();
        if(n>m) return false;
        long patternHash = 0;
        long windowHash = 0;
        long B = 263;
        long H = 1;
        long MOD = 1_000_000_009;
        
        for(int i=0;i<n-1;i++){
            H = (H*B)%MOD;
        }

        for(int i=0;i<n;i++){
            patternHash  = (patternHash*B+pat.charAt(i))%MOD;
            windowHash = (windowHash*B+text.charAt(i))%MOD;
        }

        if (windowHash == patternHash) {
            return true; 
        }

        for(int i=n;i<text.length();i++){
            int sp = i-n;
            long removed = (text.charAt(sp)*H)%MOD;
            windowHash = (((((windowHash-removed)+MOD)%MOD)*B)+text.charAt(i))%MOD;
            if(windowHash==patternHash) return true;
        }
        return false;
    }
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        StringBuilder bigString = new StringBuilder();
        StringBuilder smString = new StringBuilder();
        //preorder and post order produce the unique stirng over the inorder
        preorder(root,bigString);
        preorder(subRoot,smString);
        return isSubString(smString.toString(),bigString.toString());
    }
    public void preorder(TreeNode root,StringBuilder st){
        if(root==null){
            st.append(",#");
            return;
        }
        //need to add delimiters to for values with mutliple digits won't get concated with others
        st.append(',').append(root.val);
        preorder(root.left,st);
        preorder(root.right,st);
    }
}