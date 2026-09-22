
import java.util.ArrayList;


public class Solution 
{
	;

	
	public static int countDistinctSubstrings(String s) 
	{
		//Trie Node
		class Node {
		Node [] child = new Node[26];
	    }
		//root node
	    Node root = new Node();
		int n = s.length();
		int count = 0;
		//generate all substrings.
		for(int i=0;i<n;i++){
			Node curr = root;
			//so while iterating thorught the jthe loop
			//add the charactes at index j. 
			// build all possible prefixes of all suffixes.
			//which is nothing but all subtrings. 
			for(int j=i;j<n;j++){
				int idx = s.charAt(j)-'a';
				if(curr.child[idx]==null){
					curr.child[idx]=new Node();
					//count when ever a new node is created.
					//it repesetns a new substirng. 
					count++;
				}
				curr = curr.child[idx];
			}
		}
		//return count +1 // where +1 is counted for the empty string.
		return count+1;
	}
}