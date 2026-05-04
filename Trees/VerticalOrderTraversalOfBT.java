import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class VerticalOrderTraversalOfBT {
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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        TreeMap<Integer,TreeMap<Integer,PriorityQueue<Integer>>> map = new TreeMap<>();
        dfs(root,0,0,map);
        List<List<Integer>> ans = new ArrayList<>();
        for(TreeMap<Integer,PriorityQueue<Integer>> col : map.values()){
            List<Integer> currCol = new ArrayList<>();
            for(PriorityQueue<Integer> pq : col.values()){
                while(!pq.isEmpty()){
                    currCol.add(pq.poll());
                }
            }
            ans.add(currCol);
        }
        return ans;
    }
    public void dfs(TreeNode root,int x,int y,TreeMap<Integer,TreeMap<Integer,PriorityQueue<Integer>>> map)
    {
        if(root==null) return;
        if(!map.containsKey(x)) map.put(x,new TreeMap<>());
        if(!map.get(x).containsKey(y)) map.get(x).put(y, new PriorityQueue<>());
        map.get(x).get(y).offer(root.val);
        dfs(root.left,x-1,y+1,map);
        dfs(root.right,x+1,y+1,map);
    }
}
}
