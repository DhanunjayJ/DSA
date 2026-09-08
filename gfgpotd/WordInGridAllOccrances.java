class Solution {
    public ArrayList<ArrayList<Integer>> searchWord(char[][] mat, String word) {
        // code here
        //check all 8 dirs
        int [][] dirs = {{1,0},{0,1},{-1,0},{0,-1},{-1,-1},{-1,1},{1,1},{1,-1}};

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int n = mat.length;
        int m = mat[0].length;

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                //if first character matched
                if(mat[i][j]==word.charAt(0)){
                    ArrayList<Integer> temp = new ArrayList<>();
                    //if the word length only one then add that index
                    if(word.length()==1){
                        temp.add(i);temp.add(j);
                        ans.add(temp);
                        continue;
                    }
                    //since we need to return the unique indexes.
                    //the moment we found a match we dont' check other directions. 
                    //we add that index and break;
                    for(int k=0;k<8;k++){
                        if(isMatch(mat,word,dirs[k][0],dirs[k][1],i,j,n,m)){
                            temp.add(i);
                            temp.add(j);
                            ans.add(temp);
                            break;
                        }
                    }
                }
            }
        }
        return ans;
    }
    
    public boolean isMatch(char [][] mat,String word,int dr,int dc,int i,int j, int n,int m){
      //fixed direction vector and dr and dc and scaling it with the k value till k = len of the word. 
      //and also within the boundds. 
        for(int k=1;k<word.length();k++){
            
            int x = dr*k+i;
            int y = dc*k+j;
            
            if(x>=n || y>=m || x<0 || y<0 || word.charAt(k)!=mat[x][y]){
                return false;
            }
            
        }
        return true;
    }
}