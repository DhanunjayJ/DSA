import java.util.Arrays;

public class ShortestJobFirst {

    class Solution {
    static int solve(int bt[]) {
        /*
        we just sort the vals first based on the burst time. once that is done. then
        we can just do the accumulate the prefix sum of all the vals and accumulate the
        waiting time for each job and just do the divsion at the end.
        */
        // code here
        Arrays.sort(bt);
        long wt = 0;
        long totalWt = 0;
        int n = bt.length;
        for(int i=0;i<n-1;i++){
            wt += bt[i];
            totalWt += wt;
        }
        return (int)(totalWt/n);
    }
}

}
