import java.util.List;
import java.util.ArrayList;

public class Solution {
    public static List< String > generateString(int N) {
        // Write your code here.
       List<String> ans = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        generate(N,st,ans);
        return ans;
    }
    public static void generate(int n,StringBuilder st,List<String> ans){
        if(st.length()==n){
            ans.add(st.toString());
            return;
        }
        generate(n,st.append('0'),ans);
        st.deleteCharAt(st.length()-1);
        if(st.length()==0 || st.charAt(st.length()-1)!='0'){
        generate(n,st.append('1'),ans);
        st.deleteCharAt(st.length()-1);
        }
    }
}
