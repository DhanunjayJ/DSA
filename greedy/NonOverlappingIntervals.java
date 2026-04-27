import java.util.Arrays;

public class NonOverlappingIntervals {
    class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        /*
        Minimum number of intervals to make rest of the intervals non overlapping.
        so we need to keep the maximum number of intervals. 
        to keep the maximum number of intervals. we need to sort them based on the 
        the end time. so if a inteval ends early then we can accomodate 
        more intervals in the future. the shorter the span of the interval. the more
        we can include.
        so we sort the intervals based on the end time. now. 
        while that is done. check for the intervals that are overlapping with the ones
        that are shorter or already done. then remove them.
        count the non overlapping ones and subtract it from the length of the intervals.
        */

        int n = intervals.length;
        Arrays.sort(intervals,(a,b) -> Integer.compare(a[1],b[1]));
        int count = 1;
        int currEnd = intervals[0][1];
        for(int i=1;i<n;i++){
            if(intervals[i][0] >= currEnd){
                currEnd = intervals[i][1];
                count++;
            }
        }
        return n-count;
    }
}
}
