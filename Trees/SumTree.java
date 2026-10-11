/* Definition for Node
class Node {
    int data;
    Node left, right;

    Node(int val) {
        data = val;
        left = right = null;
    }
}
*/

class Solution {
     public boolean isSumTree(Node node) {
        // code here
        return sumTree(node)!=-1;
    }
    public int sumTree(Node node){
        if(node==null) return 0;
        //the base of the leaf nodes are neeeded. fi not we have to check
        // if the the left and right is zero. this will work bt not good way
        // having the base casse of the leaf is better. 
        if(node.left==null && node.right==null){
            return node.data;
        }
        int left = sumTree(node.left);
        if(left==-1) return -1;
        int right = sumTree(node.right);
        if(right==-1) return -1;
        if(node.data != (left+right)) return -1;
        return node.data+left+right;
    }
}