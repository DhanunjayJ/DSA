public class ChildernSum {

    //https://www.geeksforgeeks.org/problems/children-sum-parent/1?utm=codolio
    /*
class Node{
    int data;
    Node left,right;

    Node(int key)
    {
        data = key;
        left = right = null;
    }
}
*/
class Solution {
    public boolean isSumProperty(Node root) {
        //  code here
        return isFollow(root) != -1;
    }
    public int isFollow(Node root){
        
        if(root==null ) return 0;
        
        if (root.left == null && root.right == null) return root.data;
        
        int left = isFollow(root.left);
        int right = isFollow(root.right);
        
        if(left==-1 || right==-1) return -1;
        
        if(root.data != left+right) return -1;
        
        return root.data;
    }
}
}
