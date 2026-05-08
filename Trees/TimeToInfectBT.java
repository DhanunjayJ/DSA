import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TimeToInfectBT {
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
    /*
    If we do the bfs approach. we need to have the point to the parent
    we can build a hashmap where each node is having a parent.
    not once we are there. we start keeping the child nodes and parents
    on to the queue. and for level we add a time. 
    we can count time outside while queue is not empty we could 
    get the size of the current queue and poll all the elemetns. 
    and add the other elements if not visited before. 
    and return the time at last. 
    */
    TreeNode target = null;
    int start = 0;

    public int amountOfTime(TreeNode root, int start) {
        
        this.start = start;

        HashMap<TreeNode,TreeNode> parentMap = new HashMap<>();
        dfs(root,null,parentMap);
        
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> nodes = new LinkedList<>();

        nodes.add(target);
        visited.add(target);

        int time = -1;

        while(!nodes.isEmpty()){
            int size = nodes.size();
            for(int i=0;i<size;i++){
                TreeNode rem = nodes.remove();
                TreeNode [] neighbours = {rem.left,rem.right,parentMap.get(rem)};
                for(TreeNode n:neighbours){
                    if(n!=null && !visited.contains(n)){
                        visited.add(n);
                        nodes.add(n);
                    }
                }
            }
            time++;
        }
        return time;
    }

    public void dfs(TreeNode root, TreeNode parent,HashMap<TreeNode,TreeNode> map){
        if(root==null) return;
        if(root.val == start ) target = root;
        map.put(root,parent);
        dfs(root.left,root,map);
        dfs(root.right,root,map);
    }
}
}
