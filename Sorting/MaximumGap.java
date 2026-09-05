class Solution {
    public int maximumGap(int[] nums) {
        
        int n = nums.length;
        
        if(n<2) return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for(int num : nums){
            min = Math.min(num,min);
            max = Math.max(num,max);
        }

        //min bucket size if the n elements are kept 
        //evenly kept withing the gap max-min.
        int bucketSize = Math.max(1,(max-min)/(n-1));
        //get how many buckets are needed + 1 for saftey when the 
        //max-min/bucketSize is non integer. + 1 
        int bucketCount = (max-min)/bucketSize + 1;
        //According to pigeion hole principle, since the gap
        //is spaced evenly and the values are unevenly spreaded
        //this makes atleast one bucket to left out. 
        //If you have n numbers and you divide their total range into
        //n - 1 buckets, at least one bucket is GUARANTEED to be
        //completely empty.
        int [] minBucket = new int[bucketCount];
        int [] maxBucket = new int[bucketCount];

        Arrays.fill(minBucket,Integer.MAX_VALUE);
        Arrays.fill(maxBucket,Integer.MIN_VALUE);

        for(int num : nums){
            int bucketIdx = (num-min)/bucketSize;
            minBucket[bucketIdx] = Math.min(minBucket[bucketIdx],num);
            maxBucket[bucketIdx] = Math.max(maxBucket[bucketIdx],num);
        }

        int maxGap = 0;
        int prevMax = min;

        for(int i=0;i<bucketCount;i++){
            if(minBucket[i]==Integer.MAX_VALUE) continue;
            maxGap = Math.max(maxGap,minBucket[i]-prevMax);
            prevMax = maxBucket[i];
        }

        return maxGap;
    }
}