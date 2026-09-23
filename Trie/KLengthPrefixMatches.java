class Solution {
    class Node {
        Node [] child = new Node[26];
        int prefixCount = 0;
    }
    public Node root;
    public void insert(String s){
        Node curr = root;
        for(int i=0;i<s.length();i++){
            int idx = s.charAt(i)-'a';
            if(curr.child[idx]==null){
                curr.child[idx] = new Node();
            }
            curr = curr.child[idx];
            curr.prefixCount++;
        }
    }
	public ArrayList<Integer> kLengthPref(String[] arr, String[] queryStr,
	int[] queryK) {
		// code here
		ArrayList<Integer> ans = new ArrayList<>();
		root = new Node();
		for(String st : arr) insert(st);
		for(int i=0;i<queryStr.length;i++){
		    String st = queryStr[i];
		    int k = queryK[i];
		    Node curr = root;
		    int count = 0;
		    for(int j=0;j<k;j++){
		        int idx = st.charAt(j)-'a';
		        if(curr.child[idx]!=null){
		            curr = curr.child[idx];
		            count = curr.prefixCount;
		        }else{
		            count = 0;
		            break;
		        }
		    }
		    ans.add(count);
		}
		return ans;
	}
}
