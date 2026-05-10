import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PrintAllNodesKDistance {
    /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {

        HashMap <TreeNode,TreeNode> parentMap = new HashMap<>();

        dfs(root,null,parentMap);

        Queue<TreeNode> levelNodes = new LinkedList<>();
        HashSet<TreeNode> visited = new HashSet<>();

        levelNodes.add(target);
        visited.add(target);

        int currentLevel = 0;

        while(!levelNodes.isEmpty()){

            if(currentLevel==k){
                List<Integer> ans = new ArrayList<>();
                for(TreeNode node:levelNodes){
                    ans.add(node.val);
                }
                return ans;
            }

            int size = levelNodes.size();

            for(int i=0;i<size;i++){
                TreeNode rem = levelNodes.remove();
                TreeNode[] neighbours = {rem.left,rem.right,parentMap.get(rem)};
                for(TreeNode n:neighbours){
                    if(n!=null && !visited.contains(n)){
                        levelNodes.add(n);
                        visited.add(n);
                    }
                }
            }

            currentLevel++;
        }

        return new ArrayList<>();
    }
    public void dfs(TreeNode node,TreeNode parent,HashMap<TreeNode,TreeNode> map){
        if(node==null) return;
        map.put(node,parent);
        dfs(node.left,node,map);
        dfs(node.right,node,map);
    }
}
}


//DFS Appraoch s.c O(H)

class Solution {
    List<Integer> result = new ArrayList<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        findTarget(root, target, k);
        return result;
    }

    private int findTarget(TreeNode node, TreeNode target, int k) {
        if (node == null) return -1;

        if (node == target) {
            collectSubtreeNodes(node, k);
            return 1; 
        }


        int leftDist = findTarget(node.left, target, k);
        if (leftDist != -1) {
            if (leftDist == k) result.add(node.val);
            else {
                collectSubtreeNodes(node.right, k - leftDist - 1);
            }
            return leftDist + 1;
        }

        int rightDist = findTarget(node.right, target, k);
        if (rightDist != -1) {
            if (rightDist == k) result.add(node.val);
            else {
                collectSubtreeNodes(node.left, k - rightDist - 1);
            }
            return rightDist + 1;
        }

        return -1;
    }

    private void collectSubtreeNodes(TreeNode node, int d) {
        if (node == null || d < 0) return;
        
        if (d == 0) {
            result.add(node.val);
            return;
        }

        collectSubtreeNodes(node.left, d - 1);
        collectSubtreeNodes(node.right, d - 1);
    }
}