import java.util.Arrays;

public class JobSequencingUsingDSU {
    /*
struct Job
{
   int id;	 // Job Id
   int deadline; // Deadline of job
   int profit; // Profit if job is over before or on deadline
};
*/
class Solution {
    int [] par;
    
    public int find(int x){
        if(x==par[x]) return x;
        int temp = find(par[x]);
        par[x] = temp;
        return temp;
    }
    
    int[] JobScheduling(Job arr[], int n) {
        // code here
        par = new int[n+1];
        
        Arrays.sort(arr,(a,b) -> Integer.compare(b.profit,a.profit));
        
        for(int i=0;i<=n;i++){
            par[i] = i;
        }
        
        int profit = 0;
        int jobs = 0;
        
        for(Job job : arr){
            
            int availableSlot = find(job.deadline);
            
            if(availableSlot>0){
                profit += job.profit;
                jobs++;
                par[availableSlot] = find(availableSlot-1);
            }
            
        }
        
        return new int[]{jobs,profit};
        
    }
}
}
