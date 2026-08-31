import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {
    
    class Solution {

    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> ans = new ArrayList<>();

        List<Integer> base = new ArrayList<>();

        base.add(1);

        ans.add(base);

        if(numRows==1) return ans;

        for(int i=1;i<numRows;i++){

            List<Integer> last = ans.get(ans.size()-1);

            List<Integer> curr = new ArrayList<>();

            curr.add(1);

            for(int k=1;k<i;k++){

                curr.add(last.get(k-1)+last.get(k));

            }

            curr.add(1);

            ans.add(curr);

        }

        return ans;
    }
}
}
