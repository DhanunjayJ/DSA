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


// In the above appraoch we visited each node once and also we create
//a undirected graph. how to do it without creating undirectd grah. 

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

    int maxDistance = Integer.MIN_VALUE;

    public int amountOfTime(TreeNode root, int start) {
        traverse(root,start);
        return maxDistance;
    }

    public int traverse(TreeNode root,int start){
        if(root==null) return 0;

        //we have found the start node we have to 
        // to find the max distance to down. based on what is max
        //we update the max.
        //and we return -1 saying that the infected node is 
        // 1 nodes away from you. 
        //-ve is a indecation that the this substree was infected.

        int left = traverse(root.left,start);
        int right = traverse(root.right,start);

        if(root.val == start){
            maxDistance = Math.max(Math.max(left,right),maxDistance);
            return -1;
        }

        if(left<0){
            //the start is in the left substree
            // then we need to ge tthe distance to the node 
            //to the start node
            int leftDist = Math.abs(left);
            maxDistance = Math.max(leftDist+right,maxDistance);
            return left-1;
        }
        if(right<0){
            int rightDist = Math.abs(right);
            maxDistance = Math.max(rightDist+left,maxDistance);
            return right-1;
        }

        return Math.max(left,right)+1;
    }
}