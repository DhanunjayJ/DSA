//using Queue

class Solution {
    public int timeRequiredToBuy(int[] tickets, int k) {

        // t.c : n*tickets[k]
        //sc : O(n);

        Queue<Integer> q = new LinkedList<>();

        int time = 0;

        for(int i=0;i<tickets.length;i++){
            q.add(i);
        }

        while(tickets[k]>0){
            int rem = q.remove();
            tickets[rem]--;
            if(tickets[rem]>0){
                q.add(rem);
            }
            time++;
        }
        return time;
    }
}

//using math

class Solution {
    public int timeRequiredToBuy(int[] tickets, int k) {
        int time = 0;
        for(int i=0;i<tickets.length;i++){
            if(i<=k){
                time += Math.min(tickets[i],tickets[k]);
            }else{
                time += Math.min(tickets[i],tickets[k]-1);
            }
        }
        return time;
    }
}