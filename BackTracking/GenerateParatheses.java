class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        generate(ans,0,0,new StringBuilder(),n);
        return ans;
    }
    public void generate(List<String> ans,int leftCount,int rightCount,StringBuilder st,int n){
        
        if(st.length()==2*n){
            ans.add(st.toString());
            return;
        }

        if(leftCount<n){
            st.append('(');
            generate(ans,leftCount+1,rightCount,st,n);
            st.deleteCharAt(st.length()-1);
        }

        if(leftCount>rightCount){
            st.append(')');
            generate(ans,leftCount,rightCount+1,st,n);
            st.deleteCharAt(st.length()-1);
        }
    }
}