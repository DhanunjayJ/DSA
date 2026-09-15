/* Binary Tree Node Structure
class Node {
    int data;
    Node left, right;

    public Node(int data){
        this.data = data;
    }
}
*/
class Solution {
    ArrayList<Integer> leafNodeCost;
    public int getCount(Node root, int k) {
        // code here
        //left cost + number of nodes and right cost + number of nodes. 
        // check if the both the costs are within the range?
        // if they are then take both or else take 
        //what we could do isthat we just viste all the nodes and put all 
        // the leadf nodes vleuas in an array and then, we sort them based on that 
        //cost of each node snad then pick the first vlaues that are withing the range. 
        leafNodeCost = new ArrayList<>();
        helper(root,1);
        Collections.sort(leafNodeCost);
        int currCost = 0;
        for(int i=0;i<leafNodeCost.size();i++){
            currCost+=leafNodeCost.get(i);
            if(currCost>k){
                return i;
            }
        }
        return leafNodeCost.size();
    }
    public void helper(Node root,int cost){
        if(root==null) return;
        if(root.left==null && root.right==null){
            leafNodeCost.add(cost);
            return;
        }
        helper(root.left,cost+1);
        helper(root.right,cost+1);
    }
}