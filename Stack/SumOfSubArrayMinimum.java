// Three pass Approach
class Solution {
    final long MOD = 1_000_000_007;
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        long [] nser = new long[n];
        long [] nsel = new long[n];
        // nse(nser,arr);
        Deque<Integer> st = new ArrayDeque<>();
        // if there is not smaller elemtn to the left then the n will the next smaller elemnt.
        Arrays.fill(nser,n);
        for(int i=0;i<arr.length;i++){
            while(!st.isEmpty() && arr[i]<arr[st.peek()]){
                nser[st.pop()] = i;
            }
            st.push(i);
        }
        st = new ArrayDeque<>();
        //if there is not smaller elemet to the right then the -1 will be the next smaller element.
        Arrays.fill(nsel,-1);
        for(int i=n-1;i>=0;i--){
            // handling the edgecase
            //If your array is [2, 1, 2, 1], both 1s will try to claim the subarray [1, 2, 1]. To fix this, one side must be strictly smaller (<) and the other side must be smaller or equal (<=).
            while(!st.isEmpty() && arr[i]<=arr[st.peek()]){
                nsel[st.pop()] = i;
            }
            st.push(i);
        }

        long totalSum = 0;

        for(int i=0;i<n;i++){
            long startPoints = i-nsel[i];
            long endPoints  = nser[i] - i;
            totalSum += ((startPoints*endPoints)%MOD*arr[i])%MOD;
        }
        return (int) (totalSum%MOD);
    }
}


// Single Pass Approach Using the Monotonic Stack in Increasing order.

class Solution {
    final long MOD = 1_000_000_007;
    public int sumSubarrayMins(int[] arr) {
        // first approach is that we can find it by checking all the subarrays. that is ofO(n3) 
        // but ask differnt quesiotn , how many subarrays does the current element is minimum.
        // we can find that out by checking the boudnary of the left and right. when we find the number
        // that is less that the current one, then we found the boudnary.
        // To do that easily we use te nse on left and right. 
        // if we maintain a montonic increasing stack when ever we find a element
        // tat is less than the top of the stack. then it the right boundary.
        // since the stack is incresing oder the left boundary is just the below it. 
        //once we found the boundaries then fid the subarrays count and mulitply it with
        // the one that we just pop from the stack.

        Deque<Integer> st = new ArrayDeque<>();
        long ans = 0;
        int n = arr.length;

        for(int i=0;i<=n;i++){
            //to handle the case of the last one where all are in the incresing order
            // we use this logic
            long val = (i==n) ? 0 : arr[i];

            while(!st.isEmpty() && val<arr[st.peek()]){
                int mid = st.pop();
                long leftB = st.isEmpty() ? -1 : st.peek();
                long rightB = i;
                long countSubarrays = (mid-leftB)*(rightB-mid)%MOD; 
                long sum = (countSubarrays*arr[mid])%MOD;
                ans += sum;
            }
            st.push(i);
        }
        return (int) (ans%MOD);
    }
}
