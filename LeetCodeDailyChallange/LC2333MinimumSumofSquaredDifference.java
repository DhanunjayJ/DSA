class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        /*
        1. find the sum diffs of all the indexes and store them
        int an array. 
        2. sort the differcances. since her ethe diff are the result of the k Inc or dec. 
        3. we can always use the k steps to take it down towards zero.
         but we must always target the ones that are having the high diff. so that the minSum squares gets reduced. 
        4. for that we can use the prirorityQueue to get the max one. and 
        reduce it by one k value. 
        5. and push it back to the pq. then again we get the next greater diff. 
        6. if we do this for the k values we wil ge tthe answer. 
        but here the k value is 10^9 it will give tle. 
        7. what would be the better appraoch. 
        */

        int n = nums1.length;
        int [] diff = new int[n];
        //find the diffs of all the i values.

        for(int i=0;i<n;i++){
            diff[i] = Math.abs(nums1[i]-nums2[i]);
        }

        //bucket with same diffs are stored here. 
        long [] countDiff = new long[1_000_05];
        for(int diffVal : diff){
            countDiff[diffVal]++;
        }

        int k = k1+k2;

        for(int i=countDiff.length-1;i>0;i--){
            if(countDiff[i]!=0){
                long count = countDiff[i];
                // all the elements of the current Diff can be made to zero. 
                if(k>=count){
                    k-=count;
                    countDiff[i] = 0;
                    countDiff[i-1] += count;
                    //if can't be made then do only the once that 
                    //can be done. leave as it is. 
                }else if(k<count){
                    long decCount = k;
                    long remCount = count-k;
                    countDiff[i] = remCount;
                    countDiff[i-1] += decCount;
                    // System.out.println("countDiff "+i+" "+countDiff[i]);
                    // System.out.println("countDiff "+(i-1)+" "+countDiff[i-1]);
                    break;
                }
            }
        }

        long ans = 0L;

        for(int i=1;i<1_000_05;i++){
            //each count need to be a square so we do it untill
            // untill current count diff count is equal to zero. 
            if(countDiff[i]!=0){
                while(countDiff[i]>0){
                    ans += ((long)i*i);
                    countDiff[i]--;
                }
            }
        }

        return ans;
    }
}


//binary search appraoch

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        int maxDiff = 0;
        long totalDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            totalDiff += diff[i];
        }

        long k = (long) k1 + k2;

        // If we can reduce all differences to 0
        if (totalDiff <= k) {
            return 0;
        }

        // Binary search for the smallest achievable ceiling T
        int low = 0, high = maxDiff;
        int bestT = maxDiff;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (getCost(diff, mid) <= k) {
                bestT = mid;       // Achievable, try to go lower
                high = mid - 1;
            } else {
                low = mid + 1;     // Too expensive, must raise the ceiling
            }
        }

        // Apply the ceiling bestT and deduce cost from k
        long spent = 0;
        for (int i = 0; i < n; i++) {
            if (diff[i] > bestT) {
                spent += (diff[i] - bestT);
                diff[i] = bestT;
            }
        }

        long leftoverK = k - spent;

        // Distribute leftover k to decrement elements that are at bestT down to bestT - 1
        for (int i = 0; i < n && leftoverK > 0; i++) {
            if (diff[i] == bestT && diff[i] > 0) {
                diff[i]--;
                leftoverK--;
            }
        }

        // Compute total squared sum
        long ans = 0;
        for (int d : diff) {
            ans += (long) d * d;
        }

        return ans;
    }

    private long getCost(int[] diff, int T) {
        long cost = 0;
        for (int d : diff) {
            if (d > T) {
                cost += (d - T);
            }
        }
        return cost;
    }
}

