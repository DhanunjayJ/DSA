//Brute Force using dfs and checking all the possible paths

// User function Template for Java
class Solution {

    public int[] shortestPath(int V, int E, int[][] edges) {
        // Code here
        int [][] costMatrix = new int[V][V];
        
        List<List<Integer>> graph = new ArrayList<>();
        
        for(int i=0;i<V;i++) graph.add(new ArrayList<>());
        
        for(int i=0;i<E;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            int cost = edges[i][2];
            graph.get(u).add(v);
            costMatrix[u][v] = cost;
        }
        
        int [] ans = new int[V];
        Arrays.fill(ans,Integer.MAX_VALUE);
        
        dfs(costMatrix,graph,ans,0,0);
        
        for(int i=0;i<V;i++){
            if(ans[i]==Integer.MAX_VALUE){
                ans[i] = -1;
            }
        }
        return ans;
    }
    
    public void dfs(int [][] costMatrix,List<List<Integer>> graph,int [] ans,int src,int sum){
        ans[src] = Math.min(ans[src],sum);
        for(int nbr : graph.get(src)){
            dfs(costMatrix,graph,ans,nbr,sum+costMatrix[src][nbr]);
        }
    }
}





//using topological sort which gurantess by the time we reach the child nodes
//we have reached the parent with all the possible nodes. and parent node has the absolute minimum distance to 
//reach it. so we just calcuate the child based on the parent node.

// User function Template for Java
class Pair {
	int node;
	int weight;
	Pair(int node, int weight) {
		this.node = node;
		this.weight = weight;
	}
}

class Solution {
	
	public int[] shortestPath(int V, int E, int[][] edges) {
		// Code here
		// construct graph.
		/*
		Here doing topological sort what it gunrantees
		is that, when we have two noes parent (u) and child (v)
		before we reach chilld v we will have reach parent u in all the possible ways.
		because this is a topological sort.
		
		so what we do here. we do standartd khans algo with a weighted graph in a
		list of pairs.
		
		*/
		
		List<List<Pair>> adj = new ArrayList<>();
		
		for (int i = 0; i<V; i++) {
			adj.add(new ArrayList<>());
		}
		
		int [] indegree = new int[V];
		
		for (int i = 0; i<E; i++) {
			
			int u = edges[i][0];
			int v = edges[i][1];
			int weight = edges[i][2];
			
			adj.get(u).add(new Pair(v, weight));
			
			indegree[v]++;
		}
		
		Queue<Integer> q = new LinkedList<>();
		
		for (int i = 0; i<V; i++) {
			if (indegree[i] == 0)
				q.add(i);
		}
		
		int [] ans = new int[V];
		// fill the graph all nodes absulte minimum is the max except the
		// the zeroth node.
		Arrays.fill(ans, Integer.MAX_VALUE);
		// since we are finding the min path from the zero.
		ans[0] = 0;
		
		while (!q.isEmpty()) {
			
			int par = q.remove();
			
			// only go this node, if this node is reachable from the zero.
			// which can be said by he Integer.MAX_VALUE. becuae
			// in khans algo we add all the values that have indegree equals zero.
			// becuase of that we need to check explicitly is this node is reachable from the
			// zero or not.
			
			if (ans[par] != Integer.MAX_VALUE) {
				
				for (Pair neigh : adj.get(par)) {
					// if the current distance to reach the node is greater
					// then update the distance
					if (ans[par]+neigh.weight < ans[neigh.node]) {
						ans[neigh.node] = ans[par]+neigh.weight;
					}
					
				}
				
			}
			// reduce the indegree and add to the queue.
			for (Pair neigh:adj.get(par)) {
				indegree[neigh.node]--;
				if (indegree[neigh.node] == 0)
					q.add(neigh.node);
			}
			
		}
		
		for (int i = 0; i<V; i++) {
			if (ans[i] == Integer.MAX_VALUE)
				ans[i] = -1;
		}
		
		return ans;
		
	}
}
