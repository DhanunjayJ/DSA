class Solution {
    class Pair {
        int dSum;
        int num;
        int orgIdx;

        Pair(int dSum, int num, int orgIdx) {
            this.dSum = dSum;
            this.num = num;
            this.orgIdx = orgIdx;
        }
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(calcDSum(nums[i]), nums[i], i);
        }
        Arrays.sort(pairs, (a, b) -> {
            if (a.dSum == b.dSum)
                return Integer.compare(a.num, b.num);
            return Integer.compare(a.dSum, b.dSum);
        });

        boolean [] vis = new boolean[n];
        int swaps = 0;
        for(int i=0;i<n;i++){
            if(vis[i] || pairs[i].orgIdx==i) continue;
            int len = 0;
            int j = i;
            while(!vis[j]){
                vis[j]=true;
                j = pairs[j].orgIdx;
                len++;
            }
            //for a cycle size of length the minimum swaps 
            //is always len-1.
            //if dsu was used then lets say 
            // there will k cycles in those k cycles we need to remove
            // 1 k times so we k = number of componetns so we remove
            // number of components from the total len. 
            if(len>0){
                swaps += len-1;
            }
        }
        return swaps;

    }

    public int calcDSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}