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

class SubInfo{
    int count = 0;
    int sum = 0;
    SubInfo(int count,int sum){
        this.count = count;
        this.sum = sum;
    }
}

class Solution {
    int count;
    public int averageOfSubtree(TreeNode root) {
        /*
        1.Leaf nodes are always counted becase their avearge is same as the value 
        it them.
        2. we need to find out the average of the left and average of the right. 
        once we have them then we have to calculate the average fo the node it self
        and check if it is equal tot he value of ht enode. 

        but, there could be a problem of what if the node value of the left and right is
        rounded down? so that becase of it the values some times may not progate correctly.
        so instread of returing average to the top, i thinkg we must pass the count of 
        nodes and the sum of all the nodes from the left and right. 

        this way, we don't get the rounded down number but exact sum, so that we can 
        easily count the average at each node. 

        3. we need a global varible that increments when the avaerge equal tot eh value itself. 

        4. base case is what do we return? may be return an object with vlaue sof the count and sum.

        5. this way we count acuatlly get the count and sum easily. 
        */
        count = 0;
        helper(root);
        return count;
    }

    public SubInfo helper(TreeNode root){
        if(root==null) return new SubInfo(0,0);
        SubInfo left = helper(root.left);
        SubInfo right = helper(root.right);
        //check if avearge is equal to node itself.
        int sum = left.sum + right.sum + root.val;
        int ncount = left.count + right.count + 1;
        
        int average = sum/ncount;
        if(average==root.val) count++;
        return new SubInfo(ncount,sum);
    }
}