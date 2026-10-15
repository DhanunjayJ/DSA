class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        int n = barcodes.length;
        /*
        given that the number of the times a number exist will never be 
        more than n/2 if more than n/2 we can't make the adjacent values 
        distinct. becasue of peigeon hole principle.
        */
        int [] ans = new int[n];
        int [] count = new int[10001];
        int maxCount = 0;
        int maxVal = 0;
        for(int code : barcodes){
            count[code]++;
            if(count[code]>maxCount){
                maxCount = count[code];
                maxVal = code;
            }
        }
        int i = 0;
        while(count[maxVal]>0){
            ans[i] = maxVal;
            i+=2;
            count[maxVal]--;
        }
        for(int k=0;k<10001;k++){
            while(count[k]>0){
                if(i>=n){
                    i=1;
                }
                ans[i] = k;
                i+=2;
                count[k]--;
            }
        }
        return ans;

    }
}


class Solution {
    public int[] rearrangeBarcodes(int[] barcodes) {
        int n = barcodes.length;

        int [] ans = new int[n];
        int [] count = new int[10001];
        for(int code : barcodes){
            count[code]++;
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> Integer.compare(count[b],count[a]));
        for(int i=0;i<10001;i++){
            if(count[i]!=0){
                pq.add(i);
            }
        }
        int i = 0;
        while(pq.size()>=2){
            int val1 = pq.poll();
            int val2 = pq.poll();
            ans[i++] = val1;
            ans[i++] = val2;
            count[val1]--;
            count[val2]--;
            if(count[val1]>0) pq.add(val1);
            if(count[val2]>0) pq.add(val2);
        }
        if(!pq.isEmpty())
        ans[i++] = pq.poll();
        return ans;
    }
}