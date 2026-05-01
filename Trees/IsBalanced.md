

### The "Sentinel Value" Strategy
Instead of trying to return two different data types, think about how you can use **one specific integer value** to signal that the tree is already unbalanced. 

In your current `depth` function, you return the height (0, 1, 2...). What if you reserved a special number—like **-1**—to represent "this subtree is not balanced"?

1.  When you call `depth(root.left)`, if it returns **-1**, you immediately know the whole tree is unbalanced.
2.  If it returns a valid height, you check the right side.
3.  If both sides are valid heights but their difference is **> 1**, you return **-1** to the caller above you.

This way, the "boolean" information is hidden inside the integer return value. If the final result is -1, it's `false`; otherwise, it's `true`.

Does using a "error code" return value help you see how to merge the height calculation and the balance check into one pass?


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

 ```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        /*
        If we calculate in the root node is that enough?
        do we need to calculate at every node? no need;
        */
        return depth(root)==-1 ? false : true ;
    }
    public int depth(TreeNode root){
        if(root==null) return 0;
        int left = depth(root.left);
        int right = depth(root.right);
        if(left==-1 || right==-1) return -1;
        if(Math.abs(left-right)>1){
            return -1;
        }else{
            return 1+Math.max(left,right);
        }
    }
}
```

Using a **sentinel value** (like -1 or a null object) is an extremely common pattern in recursive algorithms, particularly when you need to "short-circuit" a process.

In the context of LeetCode and tree problems, this is often considered the **optimal (standard)** approach because it allows you to solve the problem in $O(n)$ time (one pass) without creating extra objects.

### Why it's common in Recursion
In recursive calls, you are essentially "bubbling up" information from the bottom of the tree to the top. Sentinels are used to communicate two things at once:

1.  **Success Data:** "Here is the calculation you asked for" (e.g., the height of the tree).
2.  **Failure State:** "Stop everything; we found a violation" (e.g., this tree is unbalanced).

### Other "Standard" Ways
While the sentinel value is the most memory-efficient way in languages like Java or C++, there are two other common ways people handle returning "two things at once":

*   **The Result Object:** You create a small helper class like `class Result { int height; boolean isBalanced; }`. This is "cleaner" from an OOP perspective but slightly slower due to object allocation.
*   **Global/Member Variable:** Using a `boolean isBalanced = true` outside the function and just updating it if you find a height difference $>1$. This is simple but generally discouraged in professional software because it makes the function "stateful" (it depends on a variable outside itself).

### Is it common elsewhere?
Yes! You’ll see this logic in many areas of computer science:
*   **Search Functions:** `indexOf()` returns the index if found, or **-1** if not.
*   **System Calls:** Many C-based APIs return the result on success, or **-1** to signal an error.

By using `-1` in your tree problem, you are effectively treating an unbalanced tree as an "error" that propagates all the way to the root immediately.

Since you're unstuck on the return type, do you want to try refactoring your `depth` function to handle that -1 logic?