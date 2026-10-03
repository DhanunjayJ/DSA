class Solution {
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {

        Arrays.sort(tasks);
        Arrays.sort(workers);

        int n = tasks.length;
        int m = workers.length;

        int left = 0;
        int right = Math.min(n,m);

        int ans = 0;

        while(left<=right){
            int mid = left+(right-left)/2;
            if(canComplete(tasks,workers,pills,strength,mid)){
                ans = mid;
                left = mid+1;
            }else{
                right = mid-1;
            }
        }

        return ans;
    }

    public boolean canComplete(int [] tasks,int [] workers,int pills,int strength,int k){
        
        if(k==0) return true;

        int m = workers.length;
        // deque will store the workers who can at least beat the current TASK with a pill.
        //values in the queue will be store in ascending order of strength;
        Deque<Integer> q = new ArrayDeque<>();
        
        int wPtr = m-1;
        int pillsLeft = pills;

        //iterate fromt the kth largest requirement to the smallest.
        for(int i=k-1;i>=0;i--){

            int taskReq = tasks[i];

            //add all the workers who can meet the requirment with a pill
            // we limit to k strongest workers.
            while(wPtr>=m-k && workers[wPtr]+strength>=taskReq){
                q.addFirst(workers[wPtr]); //keeping the smaller values
                // to the front. 
                wPtr--;
            }

            //if not worker can satisfy this task even with a pill
            // we return false;
            if(q.isEmpty()){
                return false;
            }

            // Greedy decision:
            // 1. If the strongest available worker can do it WITHOUT a pill, use them.
            if(q.peekLast()>=taskReq){
                q.pollLast();
            }else{
               // 2. Otherwise, a pill is mandatory.
                // Give it to the WEAKEST eligible worker (front of deque) to preserve stronger ones.
                if(pillsLeft<=0){
                    return false;
                }
                q.pollFirst();
                pillsLeft--;
            }
        }
        return true;
    }
}