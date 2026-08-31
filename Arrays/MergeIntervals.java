import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {
    class Solution {
    public int[][] merge(int[][] intervals) {
        List<int[]> ans = new ArrayList<>();
        //sort the intervals to merge them 
        Arrays.sort(intervals,(a,b) -> Integer.compare(a[0],b[0]));
        //we merge intervals when the the current interval
        // and next interval begin is < current interval end.
        //so we merge with the maximum end value and put in
        //the currentInterval. 
        // when it is no longer the case we just add it as the interval. 
        int [] currentInterval = intervals[0];
        for(int i=1;i<intervals.length;i++){
            int [] nextInterval = intervals[i];
            if(nextInterval[0]<=currentInterval[1]){
                currentInterval[1] = Math.max(currentInterval[1],nextInterval[1]);
            }else{
                ans.add(currentInterval);
                currentInterval=nextInterval;
            }
        }
        ans.add(currentInterval);
        return ans.toArray(new int[ans.size()][]);
    }
}
}
