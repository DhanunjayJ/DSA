class Solution {
    public int leastInterval(char[] tasks, int n) {
        /* Since there can be duplicates we can count 
        the number of times a job is present in the tasks. 

        to get the minum numnber of times, we need to first keep the 
        maximum task that is repeating more first. so that in between 
        we can finish the other less repeating tasks. 

        we need to maintain a global timer that counts the time?

        we also need to maintain last occurance time of the each element
        since all the elements are alphabets. we can just use. an array
        where we store for each alphabet last occurance freuqncey

        when the currenttime - lastappeartime > n then we reduce the 
        frequency of the particular task. 

        final Idea
        1. First we need to count the numbe of times the task is repeating. 
        2. sort the Tasks based on the number of times it appreated in
        the decending order (so the ones that has more will require more time so if we start them first we can get the minimum time).
        3. Start Iterating through the tasks in the queue or arraylist
        untill every task in the queue are zero. 
        4. While iterating we maintiant he lastseen time of the particular  ele ment in a array so that we can easily find out can we place assign task to that particular task now or not. 
        5. if current-lastseen > n then we reduce (assign that ele and update the lastseen) then move forward. 

        we need to iterate thorught he array muiltli times untill it
        all the elements becomes empty. the worst case -> (26*n*n) 

        so if we just use heap here that would give us of n log 26 time complexity.

        so if we use heap then how we are going to keep the get the which one is next?? so what we need to do is... to get what next. 
         */

        int [] count = new int[26];
        
        for(char c: tasks){
            count[c-'A']++;
        }

        PriorityQueue <Integer> maxFreqPq = new PriorityQueue<>(Collections.reverseOrder());

        for(int f:count){
            if(f>0) maxFreqPq.add(f);
        }

        int time = 0;

        Deque<int[]> remTimeQ = new ArrayDeque<>();

        while(!maxFreqPq.isEmpty() || !remTimeQ.isEmpty()){
            time++;
            if(!maxFreqPq.isEmpty())
            {int remFreq = maxFreqPq.poll();
            remFreq--;
            if(remFreq>0) remTimeQ.addLast(new int[]{remFreq,time+n});
            }
            if(!remTimeQ.isEmpty() && remTimeQ.peekFirst()[1]==time){
                int [] qPoll = remTimeQ.pollFirst();
                maxFreqPq.offer(qPoll[0]);
            }
        }

        return time++;
    }
}



// Optimized
class Solution {
    public int leastInterval(char[] tasks, int n) {
        int [] freq = new int[26];
        int maxFreq = 0;
        for(int task:tasks){
            freq[task-'A']++;
            maxFreq = Math.max(freq[task-'A'],maxFreq);
        }
        int count = 0;
        for(int f:freq){
            if(maxFreq==f){
                count++;
            }
        }
        //If there is zero idle time, then the total time taken is simply the total number of tasks.
        return Math.max((maxFreq-1)*(n+1)+count,tasks.length);
    }
}