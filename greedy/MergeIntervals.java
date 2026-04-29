import java.util.ArrayList;
import java.util.Arrays;

public class MergeIntervals {
    class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals,(a,b) -> Integer.compare(a[0],b[0]));
        int [] currentInterval = intervals[0];
        ArrayList<int[]> ans = new ArrayList<>();
        for(int i=1;i<intervals.length;i++){
            if(intervals[i][0] <= currentInterval[1]){
                currentInterval[1] = Math.max(intervals[i][1],currentInterval[1]);
            }else{
                ans.add(currentInterval);
                currentInterval = intervals[i];
            }
        }
        ans.add(currentInterval);
        return ans.toArray(new int[ans.size()][]);
    }
}
}
