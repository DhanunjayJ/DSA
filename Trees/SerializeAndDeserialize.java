import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SerializeAndDeserialize {
    /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder st = new StringBuilder();
        serializeHelper(root,st);
        return st.toString();
    }
    
    public void serializeHelper(TreeNode root,StringBuilder st){
        if(root==null){st.append("#,"); return;}
        st.append(root.val+",");
        serializeHelper(root.left,st);
        serializeHelper(root.right,st);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(q);
    }

    public TreeNode deserializeHelper(Queue<String> q){
        
        String val = q.poll();

        if(val.equals("#")){
            return null;
        }

        TreeNode root = new TreeNode(Integer.valueOf(val));
        root.left = deserializeHelper(q);
        root.right = deserializeHelper(q);
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
}
