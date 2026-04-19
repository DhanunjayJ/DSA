import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class MaxSumCombination {
    class Solution {
    public ArrayList<Integer> topKSumPairs(int[] a, int[] b, int k) {
        // code here
        /*
        First we sort the both the arrays in descedndign order
        once first add 0,0 to the max pritoryQueue since we need max ones
        in the pq we input a val like sum,i,j and sort it based on the sum
        and we maintain a hashset of i,j node. using Arrays.asList Set<List<Integer>> 
        in list<Integer> the hashcode and equals are already implenmnted correclty
        so don't need to impplmetn them again.!!
        
        */
        
        Arrays.sort(a);
        Arrays.sort(b);
        int n = a.length;
        
        PriorityQueue<int[]> maxPq = new PriorityQueue<>((a1,b1) -> Integer.compare(b1[0],a1[0]));
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        Set<List<Integer>> visited = new HashSet<>();
        
        maxPq.offer(new int[]{a[n-1]+b[n-1],n-1,n-1});
        visited.add(Arrays.asList(n-1,n-1));
        
        while(!maxPq.isEmpty() && ans.size()<k){
            
            int [] curr = maxPq.poll();
            ans.add(curr[0]);
            //check for the i+1,j
            if(curr[1]-1>=0){
                if(!visited.contains(Arrays.asList(curr[1]-1,curr[2]))){
                    maxPq.offer(new int[]{a[curr[1]-1]+b[curr[2]],curr[1]-1,curr[2]});
                    visited.add(Arrays.asList(curr[1]-1,curr[2]));
                }
            }
            
            if(curr[2]-1>=0){
                if(!visited.contains(Arrays.asList(curr[1],curr[2]-1))){
                    maxPq.offer(new int[]{a[curr[1]]+b[curr[2]-1],curr[1],curr[2]-1});
                    visited.add(Arrays.asList(curr[1],curr[2]-1));
                }
            }
        }
        
        return ans;
        
    }
}
}
