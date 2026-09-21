/* Structure of binary tree Node
class Node {
    int data;
    Node left, right;

    Node(int x) {
        data = x;
        left = right = null;
    }
}
*/

class Solution {
    public boolean areAnagrams(Node root1, Node root2) {
        // code here
        // doing bfs level by level
        //store the values in a queue.
        //and while gettint he values and going to next leveluse hashamp.
        //to keep track of the values at each level and one both level traversla completese
        // comapre the counts if btoh counts are eual if not return false.
        Queue<Node> q1 = new LinkedList<>();
        Queue<Node> q2 = new LinkedList<>();
        q1.add(root1);
        q2.add(root2);
        while(!q1.isEmpty() && !q2.isEmpty()){
            
            int size1 = q1.size();
            int size2 = q2.size();
            
            HashMap<Integer,Integer> hm = new HashMap<>();
            
            for(int i=0;i<size1;i++){
                Node rem = q1.remove();
                hm.put(rem.data,hm.getOrDefault(rem.data,0)+1);
                if(rem.left!=null) q1.add(rem.left);
                if(rem.right!=null) q1.add(rem.right);
            }
            
            for(int i=0;i<size2;i++){
                Node rem = q2.remove();
                hm.put(rem.data,hm.getOrDefault(rem.data,0)-1);
                if(rem.left!=null) q2.add(rem.left);
                if(rem.right!=null) q2.add(rem.right);
            }
            
            for(int val : hm.values()){
                if(val!=0) return false;
            }
            
            if(q1.size()!=q2.size()) return false;
        }
        return true;
    }
}