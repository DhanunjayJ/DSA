// User function Template for Java

class Solution {
    static int[] replaceWithRank(int arr[], int N) {
        // code here
        int [][] valToIdx = new int[N][2];
        for(int i=0;i<N;i++){
            valToIdx [i][0] = arr[i];
            valToIdx [i][1] = i;
        }
        Arrays.sort(valToIdx, (a, b) -> Integer.compare(a[0], b[0]));
        
        //to handle the case of the dupicate values we use the varibale rank
        
        int rank = 0;
        int [] ans = new int[N];
        int prev = 0;
        for(int i=0;i<valToIdx.length;i++){
            if(valToIdx[i][0]>prev){
                rank++;
            }
            prev = valToIdx[i][0];
            int idx = valToIdx[i][1];
            ans[idx] = rank;
        }
        return ans;
    }
}
