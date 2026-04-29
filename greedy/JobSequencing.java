import java.util.Arrays;

public class JobSequencing {
    /*
struct Job
{
   int id;	 // Job Id
   int deadline; // Deadline of job
   int profit; // Profit if job is over before or on deadline
};
*/
class Solution {
    int[] JobScheduling(Job arr[], int n) {
        // code here
        /*
        First we need to sort it based ont he profit. because
        we want the max profit. 
        
        once we found the one with the max profit. we try to
        put that job as late as possible. because of the 
        the deadline. so check for the lastest deadline that is
        availble to schedule this task. if we found a place to schudeule
        then we schdule it before the slot if the slot>=1 if not we don't do it
        
        */
        Arrays.sort(arr,(a,b) -> Integer.compare(b.profit,a.profit));
        
        boolean [] allocated = new boolean[n+1];
        
        int jobs = 0;
        int profit = 0;
        
        for(int i=0;i<n;i++){
            int dl = arr[i].deadline;
            while(dl>=1 && allocated[dl]) dl--;
            if(dl>=1){
                allocated[dl] = true;
                jobs++;
                profit += arr[i].profit;
            }
        }
        
        return new int[]{jobs,profit};
    }
}
}


// Using priority Queue (sorting based on deadline nlogn)


class Job
{
   int id;	 // Job Id
   int deadline; // Deadline of job
   int profit; // Profit if job is over before or on deadline
};

class Solution {
    int[] JobScheduling(Job arr[], int n) {
        // code here
        /*
        Using heap.
        First we maintain a current time. and sort the jobs based 
        on the deadline. 
        Maintian all the profits of the job we found till now in
        a heap. 
        When ever we don't have a room like when the job deadline <
        current time check if we can maximize the profits of the jobs
        by checking it with the vals we have till now. if the min the top
        of the stack is min.<profit. 
        then we can swap it. becauase we processing the jobs in chronological
        order we can acutally swap the values.
        and replace the old jobs with a new ones.
        */
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        // int time = 1;
        
        Arrays.sort(arr,(a,b) -> Integer.compare(a.deadline,b.deadline));
        //but technically, pq.size() is your time - 1. You can actually simplify the if condition slightly
        for(int i=0;i<arr.length;i++){
            // if(arr[i].deadline>=time){
            if(arr[i].deadline>pq.size()){
                pq.offer(arr[i].profit);
                // time++;
            }else if(!pq.isEmpty() && arr[i].profit>pq.peek()){
                pq.poll();
                pq.offer(arr[i].profit);
            }
        }
        
        int profit = 0;
        int jobs = pq.size();
        
        while(!pq.isEmpty()){
            profit+=pq.poll();
        }
        
        return new int[]{jobs,profit};
    }
}



