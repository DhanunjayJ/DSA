public class NumberOfProviencesUsingDSU {
    class Solution {
        int [] par ;
        int [] rank;
    public int findCircleNum(int[][] isConnected) {
        //getting the number nodes
        int n = isConnected.length;
        
        par = new int[n];
        rank = new int[n];

        for(int i=0;i<n;i++){
            par[i] = i;
        }

        for(int i=1;i<n;i++){
            for(int j=0;j<i;j++){
                if(isConnected[i][j]==1){
                    union(i,j);
                }
            }
        }

        int components = 0;
        for(int i=0;i<n;i++){
            if(i==par[i]) components++;
        }

        return components;
    }

    public void union(int x,int y){
        int parx = find(x);
        int pary = find(y);

        if(parx==pary) return;
        // Union By rank
        else if(rank[parx]>rank[pary]){
            par[pary] = parx;
        }else if(rank[parx]<rank[pary]){
            par[parx] = pary;
        }else{
            par[parx] = pary;
            rank[pary]++;
        }
    }

    public int find(int x){
        if(par[x]==x) return x;
        int temp = find(par[x]);
        //path compression making the parent to be the root;
        par[x] = temp;
        return temp;
    }
}
}
