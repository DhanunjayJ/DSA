class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        /*
        We just need only one state. and when we are done we are all left with
        111 which are hacing infinate paths and 2 are safe nodes.

        */

        int V = graph.length;
        
        int [] state = new int[V];

        for(int i=0;i<V;i++){
            if(state[i]==0){
               dfs(graph,state,i);
            }
        }

        List<Integer> ans = new ArrayList<>();
        for(int i=0;i<state.length;i++){
            if(state[i]==2)ans.add(i);
        }
        return ans;
    }

    public boolean dfs(int [][] graph,int [] state,int src){
        
        if(state[src]==1) return true;

        if(state[src]==2) return false;

        state[src] = 1;

        for(int nbr : graph[src]){
            if(dfs(graph,state,nbr)){
                return true; 
            }
        }

        state[src] = 2;

        return false;
    }
}