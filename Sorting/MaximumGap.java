class MaximumGap {
    public int maximumGap(int[] nums) {

        /*
        here the core idea is to store the values in buckets.

        the minimum bucket size is max-min/gap 

        this give us the min gap.

        since this si the min gap now the max gap between the numbers
        will never lie in the bucket it self it lies
        between the buckets so we only sotre the max and min value
        of each bucket and get the max vlaues. of them. 

        and compare the min and max vleus to get the max gap. 
        
        */

        int n = nums.length;

        if(n<2) return 0;

        int minVal = nums[0];
        int maxVal = nums[0];

        for(int num : nums){
            minVal = Math.min(num,minVal);
            maxVal = Math.max(num,maxVal);
        }

        if(minVal==maxVal) return 0;
        //get the min bucket size
        int bucketSize = Math.max(1,(maxVal-minVal)/(n-1));
        //how many buckets are needed is the gap/busket size +1 for the 
        // non integer vallues safety.
        int bucketCount = (maxVal-minVal)/bucketSize+1;

        int [] bucketsMin = new int[bucketCount];
        int [] bucketsMax = new int[bucketCount];
        Arrays.fill(bucketsMin,Integer.MAX_VALUE);
        Arrays.fill(bucketsMax,Integer.MIN_VALUE);

        //we distribute numbers in to buckets since the the max
        //gap will never be in side the bucket we only store the max
        //min value of each bucket and then compare the neighbours buckets for the maxgap;

        for(int num : nums){
            int bucketIdx = (num-minVal)/bucketSize;
            bucketsMin[bucketIdx] = Math.min(bucketsMin[bucketIdx],num);
            bucketsMax[bucketIdx] = Math.max(bucketsMax[bucketIdx],num);
        }

        //scanning the buckets for the maxGap
        int maxGap = 0;
        int prevMax = minVal;

        for(int i=0;i<bucketCount;i++){
            //if there are no numbers in this bucket then skip
            if(bucketsMin[i]==Integer.MAX_VALUE){
                continue;
            }

            maxGap = Math.max(maxGap,bucketsMin[i]-prevMax);
            prevMax = bucketsMax[i];
        }

        return maxGap;

    }
}