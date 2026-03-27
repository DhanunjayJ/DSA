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
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        Deque<Integer> st = new ArrayDeque<>();
        final long MOD = 1_000_000_007L;
        long totalSum = 0;
        for(int i=0;i<=n;i++){
            long currentVal = (i==n) ? 0 : arr[i];
            while(!st.isEmpty() && currentVal<arr[st.peek()]){
                int mid = st.pop();
                long leftBoundary = st.isEmpty() ? -1 : st.peek();
                long rightBoundary = i;
                long countOfSubarrays = (mid-leftBoundary)*(rightBoundary-mid)%MOD;
                long contribution = (countOfSubarrays*arr[mid])%MOD;
                totalSum += contribution;
            }
            st.push(i);
        }
        return (int)(totalSum%MOD);
    }
}

