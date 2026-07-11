import java.util.ArrayList;

public class ArticulationPoint {
    class Solution {
	// Function to return Breadth First Traversal of given graph.
	int [] disc;
	int [] low;
	boolean [] vis;
	boolean [] isArticulation;
	int time;
	
	public ArrayList<Integer> articulationPoints(int V,
	ArrayList<ArrayList<Integer>> adj) {
		// Code here
		time = 0;
		disc = new int[V];
		low = new int[V];
		vis = new boolean[V];
		isArticulation = new boolean[V];
		
		ArrayList<Integer> ans = new ArrayList<>();
		
		for (int i = 0; i<V; i++) {
			if (!vis[i]) {
				bridges(i, adj, -1);
			}
		}
		
		for (int i = 0; i<V; i++) {
			if (isArticulation[i])
				ans.add(i);
		}
		
		if (ans.size() == 0)
			ans.add(-1);
		
		return ans;
	}
	
	public void bridges(int src, ArrayList<ArrayList<Integer>> adj, int par) {
		
		vis[src] = true;
		disc[src] = time;
		low[src] = time;
		
		time++;
		
		int count = 0;
		
		for (int nbr : adj.get(src)) {
			
			if (nbr == par)
				continue;
			
			if (!vis[nbr]) {
				
				count++;
				
				bridges(nbr, adj, src);
				
				low[src] = Math.min(low[nbr], low[src]);
				
				if (par == -1) {
				    //if the par is -1 it must have more than one child.
					if (count>1) {
						isArticulation[src] = true;
					}
				} else {
				    //of all other non-roots it should satisfy this condition.
					if (low[nbr] >= disc[src]) {
						isArticulation[src] = true;
					}
				}
				
			} else {
				
				low[src] = Math.min(low[src], disc[nbr]);
			}
			
		}
		
	}
	
}

}
