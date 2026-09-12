class Solution {
    HashMap<Integer,String> map = new HashMap<>();
    public List<String> letterCombinations(String digits) {
        map.put(2,"abc");
        map.put(3,"def");
        map.put(4,"ghi");
        map.put(5,"jkl");
        map.put(6,"mno");
        map.put(7,"pqrs");
        map.put(8,"tuv");
        map.put(9,"wxyz");
        List<String> ans = new ArrayList<>();
        generate(digits,0,ans,new StringBuilder());
        return ans;
    }
    public void generate(String digits,int i,List<String> ans,StringBuilder st){
        if(i==digits.length() && st.length()==digits.length()){
            ans.add(st.toString());
            return;
        }
        String cStr = map.get(digits.charAt(i)-'0');
        for(int j=0;j<cStr.length();j++){
            st.append(cStr.charAt(j));
            generate(digits,i+1,ans,st);
            st.deleteCharAt(st.length()-1);
        }
    }
}