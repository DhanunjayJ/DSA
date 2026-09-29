class Solution {
    public int celebrity(int mat[][]) {
        // code here
        int n = mat.length;
        
        int [] indegree = new int[n];
        int [] outdegree = new int[n];
        
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i != j && mat[i][j]==1){
                    indegree[j]++;
                    outdegree[i]++;
                }
            }
        }
        for(int i=0;i<n;i++){
            if(indegree[i]==n-1 && outdegree[i]==0){
                return i;
            }
        }
        return -1;
    }
}


// using stacks.

class Solution {
    public int celebrity(int mat[][]) {
        // code here
        Deque<Integer> st = new ArrayDeque<>();
        
        int n = mat.length;
        
        for(int i=0;i<n;i++){
            st.push(i);
        }
        
        while(st.size()>1){
            int a = st.pop();
            int b = st.pop();
            
            //check if a knows b?
            if(mat[a][b]==1){
                //if it is...
                st.push(b);
            }else{
                st.push(a);
            }
        }
        
        int rem = st.pop();
        
        for(int i=0;i<n;i++){
            // If he even know one person or even one person don't know him 
            // then he is not a celebrity!!
            if(rem!=i && (mat[rem][i]==1) || mat[i][rem]==0){
                return -1;
            }
        }
        return rem;
    }
}