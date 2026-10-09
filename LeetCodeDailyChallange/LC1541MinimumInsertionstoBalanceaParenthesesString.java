class Solution {
    public int minInsertions(String s) {
        int openCount = 0;
        int insertions = 0;
        int n = s.length();
        for(int i=0;i<n;i++){
            if(s.charAt(i)=='('){
                openCount++;
            }else{
                //check if the ) is in pairs. 
                //if it is skip it. to 
                if(i+1<n && s.charAt(i+1)==')'){
                    i++;
                }else{
                    //or else insert )
                    insertions++;
                }
                //so to match up with the )) we need atleast one )
                // check if is there . if not ther insertions ++;
                if(openCount>0){
                    openCount--;
                }else{
                    //else insert ( so we do insertions ++.
                    insertions++;
                }
            }
        }
        //at last if there still openCount we need *2 of close so we return that too.
        return insertions + (openCount*2);
    }
}