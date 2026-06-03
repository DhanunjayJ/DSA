import java.util.LinkedList;
import java.util.Queue;

public class MinimumMultiplicationsToReachEnd {
    class Solution {
    public int minSteps(int[] arr, int start, int end) {
        // code here
        //if the start == end return 
        if(start==end) return 0;
        
        int MOD = 1000;
        //we need visited array to track if all possiblites are
        //done/
        //to avaoid infinate lopps.
        boolean [] visited = new boolean [MOD];
        Queue<Integer> q = new LinkedList<>();
        
        q.add(start);
        visited[start] = true;
        
        int steps = 0;
        
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                int rem = q.remove();
                for(int j=0;j<arr.length;j++){
                    
                    int val = (int)((long) rem * arr[j])%1000;
                    
                    //return the moment we found the val
                    if(val==end){
                        return steps+1;
                    }
                    
                    if(!visited[val]){
                        visited[val] = true;
                        q.add(val);
                    }
                    
                }
            }
            steps++;
        }
        //if queue is empty the number is unreachable. 
        return -1;
    }
}
}
