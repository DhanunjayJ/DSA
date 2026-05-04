import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public class TopView {
    /*
class Node {
    int data;
    Node left, right;

    Node(int val) {
        this.data = val;
        this.left = null;
        this.right = null;
    }
}
*/
class vNode{
    Node node;
    int level;
    vNode(Node node,int level){
        this.node = node;
        this.level = level;
    }
}
class Solution {
    public ArrayList<Integer> topView(Node root) {
        // code here
        
        /*
        We need to add the node in the hashmap if there is no
        node for that level previosly. 
        if that is the first node in that level.
        */
        
        HashMap<Integer,vNode> hm = new HashMap<>();
        Deque<vNode> q = new ArrayDeque<>();
        q.add(new vNode(root,0));
        
        int max = 0;
        int min = 0;
        
        while(!q.isEmpty()){
                vNode rem = q.pollFirst();
                int level = rem.level;
                max = Math.max(max,level);
                min = Math.min(min,level);
                
                if(!hm.containsKey(level)){
                    hm.put(level,rem);
                }
                
                if(rem.node.left!=null) q.add(new vNode(rem.node.left,level-1));
                if(rem.node.right!=null) q.add(new vNode(rem.node.right,level+1));
        }
        
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i=min;i<=max;i++){
            ans.add(hm.get(i).node.data);
        }
        
        return ans;
    }
}
}
