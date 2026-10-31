class Node {
	boolean isEnd;
	Node [] child = new Node[260];
}

class Solution {
	
	Node root;
	
	public void insert(String word) {
		Node curr = root;
		for (int i = 0; i<word.length(); i++) {
			int idx = word.charAt(i);
			if (curr.child[idx] == null) {
				curr.child[idx] = new Node();
			}
			curr = curr.child[idx];
		}
		curr.isEnd = true;
	}
	
	public ArrayList<String> wordBoggle(char[][] board, String[] dictionary) {
		// code here
		
		root = new Node();
		
		for (String word : dictionary) {
			insert(word);
		}
		
		int n = board.length;
		int m = board[0].length;
		
		ArrayList<String> ans = new ArrayList<>();
		
		boolean [][] vis = new boolean[n][m];
		
		for (int i = 0; i<n; i++) {
			for (int j = 0; j<m; j++) {
				int idx = board[i][j];
				if (!vis[i][j] && root.child[idx] != null) {
					dfs(board, vis, root.child[board[i][j]], i, j, ans, new StringBuilder());
				}
			}
		}
		
		return ans;
	}
	
	int [][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {-1, 1}, {1, -1}, {-1, -1}, {1, 1}};
	
	public void dfs(char [][] board, boolean [][] vis, Node root, int i,
	int j, ArrayList<String> ans, StringBuilder st) {
		
		// marking the node as visited.
		
		vis[i][j] = true;
		st.append(board[i][j]);
		
		if (root.isEnd) {
			ans.add(st.toString());
			// remove this as end so that we won't be doing any dupicate
			// entries in the answer.
			root.isEnd = false;
			
		}
		
		for (int [] d : dirs) {
			int dx = d[0]+i;
			int dy = d[1]+j;
			if (dx >= 0 && dy >= 0 && dx<board.length && dy<board[0].length && !vis[dx][dy]
			 && root.child[board[dx][dy]] != null) {
				int idx = board[dx][dy];
				dfs(board, vis, root.child[idx], dx, dy, ans, st);
			}
		}
		
		// backtrack the vis too
		// since we don't want the each cell to be used more than once
		// we use the vis array
		// if both are differnct word then it needs to be bracktracked.
		vis[i][j] = false;
		st.deleteCharAt(st.length() - 1);
		
	}
}
